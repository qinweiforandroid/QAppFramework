# 实现计划：GuideOverlay 引导浮层组件

## 概述

基于需求文档和设计文档，将 GuideOverlay 组件拆分为增量式编码任务。代码使用 Kotlin 编写，放置在 `framework/src/main/java/com/qw/framework/guide/` 目录下。测试使用 Kotest Property Testing 框架。

## 任务

- [x] 1. 创建基础类型定义
  - [x] 1.1 创建 Direction 枚举和 OnDismissListener 接口
    - 在 `framework/src/main/java/com/qw/framework/guide/` 下创建 `Direction.kt`，定义 TOP、BOTTOM、LEFT、RIGHT 四个枚举值
    - 在同目录下创建 `OnDismissListener.kt`，定义 `fun interface OnDismissListener` 包含 `onDismiss()` 方法
    - _需求: 2.1, 1.7_

- [x] 2. 实现核心定位算法
  - [x] 2.1 实现 calculatePosition 和 clampToBounds 纯函数
    - 在 `framework/src/main/java/com/qw/framework/guide/` 下创建 `GuidePositionCalculator.kt`
    - 实现 `calculatePosition(targetX, targetY, targetW, targetH, contentW, contentH, direction): Point` 方法
    - 实现 `clampToBounds(x, y, contentW, contentH, screenW, screenH): Point` 方法
    - 两个方法设计为 `object` 中的纯函数，便于独立测试
    - _需求: 2.2, 2.3, 2.4, 2.5, 2.6, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6_

  - [ ]* 2.2 编写属性测试：方向定位与居中对齐
    - **属性 1：方向定位与居中对齐**
    - 生成随机 TargetView 坐标、尺寸和 GuideContent 尺寸，验证 `calculatePosition()` 对四个方向的输出满足设计文档中的公式
    - 测试文件：`framework/src/test/java/com/qw/framework/guide/GuidePositionCalculatorPropertyTest.kt`
    - **验证需求: 2.2, 2.3, 2.4, 2.5, 3.1, 3.2**

  - [ ]* 2.3 编写属性测试：边界钳制保证屏幕内显示
    - **属性 2：边界钳制保证屏幕内显示**
    - 生成随机坐标和尺寸，验证 `clampToBounds()` 输出始终在屏幕范围内
    - 测试文件：`framework/src/test/java/com/qw/framework/guide/GuidePositionCalculatorPropertyTest.kt`
    - **验证需求: 3.3, 3.4, 3.5, 3.6**

  - [ ]* 2.4 编写属性测试：边界钳制的最小偏移性
    - **属性 3：边界钳制的最小偏移性**
    - 生成已在屏幕内的随机坐标，验证 `clampToBounds()` 不改变坐标（幂等性）
    - 测试文件：`framework/src/test/java/com/qw/framework/guide/GuidePositionCalculatorPropertyTest.kt`
    - **验证需求: 3.3, 3.4, 3.5, 3.6**

- [x] 3. 实现 GuideOverlay 主类与 Builder
  - [x] 3.1 实现 GuideOverlay 类和 Builder 内部类
    - 在 `framework/src/main/java/com/qw/framework/guide/` 下创建 `GuideOverlay.kt`
    - 实现 `GuideOverlay` 私有构造函数，接收所有配置参数
    - 实现 `Builder` 内部类，包含所有 setter 方法和默认值（direction=BOTTOM, overlayBackgroundEnabled=true, dismissOnTouchOutside=false, backgroundColor=0x80000000）
    - 实现 `build()` 方法，校验 targetView 和 guideContent 必填参数，未设置时抛出 `IllegalArgumentException`
    - _需求: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9_

  - [ ]* 3.2 编写属性测试：Builder 必填参数校验
    - **属性 4：Builder 必填参数校验**
    - 生成随机 Builder 配置组合（targetView 和 guideContent 的有无），验证 build() 的成功/失败行为
    - 测试文件：`framework/src/test/java/com/qw/framework/guide/GuideOverlayBuilderPropertyTest.kt`
    - **验证需求: 1.8, 1.9**

  - [ ]* 3.3 编写单元测试：Builder 默认值和异常
    - 验证 Builder 未设置 Direction 时默认为 BOTTOM
    - 验证 Builder 未设置 overlayBackgroundEnabled 时默认为 true
    - 验证 Builder 未设置 backgroundColor 时默认为 0x80000000
    - 验证未设置 TargetView 时 build() 抛出 IllegalArgumentException
    - 验证未设置 GuideContent 时 build() 抛出 IllegalArgumentException
    - 测试文件：`framework/src/test/java/com/qw/framework/guide/GuideOverlayBuilderTest.kt`
    - _需求: 1.8, 1.9, 2.6, 1.4, 1.5_

- [x] 4. 检查点 - 确保所有测试通过
  - 确保所有测试通过，如有问题请向用户确认。

- [x] 5. 实现显示与关闭逻辑
  - [x] 5.1 实现 show() 方法
    - 获取 DecorView，创建 overlayView（FrameLayout，MATCH_PARENT）
    - 若 overlayBackgroundEnabled 为 true，设置背景色并注册点击事件调用 dismiss()
    - 若 dismissOnTouchOutside 为 true，在 overlayView 上注册触摸事件，点击 GuideContent 之外区域时调用 dismiss()
    - 等待 TargetView 布局完成（若 width==0 使用 post{} 延迟）
    - 测量 GuideContent，调用 calculatePosition() 和 clampToBounds() 计算坐标
    - 通过 FrameLayout.LayoutParams 的 leftMargin/topMargin 定位 GuideContent
    - 将 GuideContent 添加到 overlayView，overlayView 添加到 DecorView
    - 处理重复调用 show() 的情况（先 dismiss 再重新显示）
    - 检查 Activity 是否已销毁，若无效则静默返回
    - _需求: 4.1, 4.3, 4.4, 4.5, 4.8, 4.9, 4.10, 5.1, 5.2, 5.3_

  - [x] 5.2 实现 dismiss() 方法
    - 停止可见性监听
    - 从 DecorView 移除 overlayView
    - 回调 onDismissListener
    - 置空 overlayView，处理重复调用 dismiss() 的情况
    - _需求: 4.2, 4.6, 4.7_

- [x] 6. 实现 TargetView 可见性监听
  - [x] 6.1 实现 startVisibilityMonitoring 和 stopVisibilityMonitoring
    - 在 show() 中调用 startVisibilityMonitoring()，注册 ViewTreeObserver.OnGlobalLayoutListener
    - 实现 isTargetVisible() 方法，遍历 TargetView 及其所有父容器检查 visibility
    - 当检测到 TargetView 或其父容器不可见时自动调用 dismiss()
    - 在 dismiss() 中调用 stopVisibilityMonitoring()，移除监听器
    - _需求: 6.1, 6.2, 6.3, 6.4, 6.5_

- [x] 7. 集成验证与最终检查点
  - [ ]* 7.1 编写单元测试：dismiss 回调验证
    - 设置 OnDismissListener 后调用 dismiss()，验证回调被触发
    - 测试文件：`framework/src/test/java/com/qw/framework/guide/GuideOverlayTest.kt`
    - _需求: 4.7_

  - [x] 7.2 添加 Kotest 测试依赖到 build.gradle
    - 在 `framework/build.gradle` 的 dependencies 中添加 `testImplementation "io.kotest:kotest-runner-junit5:5.8.0"` 和 `testImplementation "io.kotest:kotest-property:5.8.0"`
    - 在 android 块中添加 `testOptions { unitTests.all { it.useJUnitPlatform() } }` 以支持 Kotest
    - _需求: 测试基础设施_

- [x] 8. 最终检查点 - 确保所有测试通过
  - 确保所有测试通过，如有问题请向用户确认。

## 备注

- 标记 `*` 的任务为可选任务，可跳过以加速 MVP 开发
- 每个任务引用了具体的需求条目，确保可追溯性
- `calculatePosition()` 和 `clampToBounds()` 设计为纯函数，可在不依赖 Android 环境的情况下测试
- 属性测试验证通用正确性属性，单元测试验证具体示例和边界条件
