# 需求文档

## 简介

GuideOverlay 是 `com.qw.framework` 模块中 `guide` 包下的自定义引导工具组件。该组件用于在目标 View 的上、下、左、右四个方向显示引导内容（如提示文字、箭头等），引导内容与目标 View 居中对齐。组件采用 Builder 模式构建，方便后续扩展。

## 术语表

- **GuideOverlay**：引导浮层组件，负责在目标 View 附近展示引导内容的容器
- **TargetView**：目标视图，即需要被引导高亮或指向的 View
- **GuideContent**：引导内容视图，由调用方提供的自定义 View，用于展示引导信息
- **Direction**：引导内容相对于 TargetView 的显示方向，包含 TOP、BOTTOM、LEFT、RIGHT 四个枚举值
- **Builder**：构建器，采用链式调用模式配置 GuideOverlay 的各项参数
- **DecorView**：Activity 的根装饰视图，GuideOverlay 将作为子 View 添加到 DecorView 上
- **OverlayBackground**：引导浮层的背景遮罩层，覆盖整个屏幕用于聚焦用户注意力

## 需求

### 需求 1：Builder 模式构建

**用户故事：** 作为开发者，我希望通过 Builder 模式创建 GuideOverlay 实例，以便灵活配置各项参数并保持良好的可扩展性。

#### 验收标准

1. THE Builder SHALL 提供 `setTargetView(view: View)` 方法用于设置目标视图
2. THE Builder SHALL 提供 `setDirection(direction: Direction)` 方法用于设置引导内容的显示方向
3. THE Builder SHALL 提供 `setGuideContent(view: View)` 方法用于设置自定义引导内容视图
4. THE Builder SHALL 提供 `setOverlayBackgroundEnabled(enabled: Boolean)` 方法用于控制是否显示遮罩背景，默认值为 true（显示遮罩）
5. THE Builder SHALL 提供 `setBackgroundColor(color: Int)` 方法用于设置遮罩背景颜色，默认值为半透明黑色（0x80000000）
6. THE Builder SHALL 提供 `setDismissOnTouchOutside(enabled: Boolean)` 方法用于控制点击 GuideContent 之外区域是否自动关闭引导浮层，默认值为 false（不自动关闭）
7. THE Builder SHALL 提供 `setOnDismissListener(listener: OnDismissListener)` 方法用于设置引导关闭回调
8. THE Builder SHALL 提供 `build()` 方法返回配置完成的 GuideOverlay 实例
8. IF TargetView 未设置, THEN THE Builder SHALL 在调用 `build()` 时抛出 IllegalArgumentException
9. IF GuideContent 未设置, THEN THE Builder SHALL 在调用 `build()` 时抛出 IllegalArgumentException

### 需求 2：方向定位

**用户故事：** 作为开发者，我希望引导内容能够在目标 View 的上、下、左、右四个方向显示，以便根据界面布局选择合适的引导位置。

#### 验收标准

1. THE Direction 枚举 SHALL 包含 TOP、BOTTOM、LEFT、RIGHT 四个值
2. WHEN Direction 为 TOP 时, THE GuideOverlay SHALL 将 GuideContent 放置在 TargetView 的正上方
3. WHEN Direction 为 BOTTOM 时, THE GuideOverlay SHALL 将 GuideContent 放置在 TargetView 的正下方
4. WHEN Direction 为 LEFT 时, THE GuideOverlay SHALL 将 GuideContent 放置在 TargetView 的正左方
5. WHEN Direction 为 RIGHT 时, THE GuideOverlay SHALL 将 GuideContent 放置在 TargetView 的正右方
6. IF Direction 未设置, THEN THE GuideOverlay SHALL 默认使用 BOTTOM 方向

### 需求 3：居中对齐

**用户故事：** 作为开发者，我希望引导内容与目标 View 居中对齐，以便引导指向清晰准确。

#### 验收标准

1. WHEN Direction 为 TOP 或 BOTTOM 时, THE GuideOverlay SHALL 将 GuideContent 的水平中心点与 TargetView 的水平中心点对齐
2. WHEN Direction 为 LEFT 或 RIGHT 时, THE GuideOverlay SHALL 将 GuideContent 的垂直中心点与 TargetView 的垂直中心点对齐
3. IF GuideContent 居中对齐后超出屏幕左边界, THEN THE GuideOverlay SHALL 将 GuideContent 的左边缘贴齐屏幕左边界
4. IF GuideContent 居中对齐后超出屏幕右边界, THEN THE GuideOverlay SHALL 将 GuideContent 的右边缘贴齐屏幕右边界
5. IF GuideContent 居中对齐后超出屏幕上边界, THEN THE GuideOverlay SHALL 将 GuideContent 的上边缘贴齐屏幕上边界
6. IF GuideContent 居中对齐后超出屏幕下边界, THEN THE GuideOverlay SHALL 将 GuideContent 的下边缘贴齐屏幕下边界

### 需求 4：显示与关闭

**用户故事：** 作为开发者，我希望能够控制引导浮层的显示和关闭，以便在合适的时机展示引导。

#### 验收标准

1. THE GuideOverlay SHALL 提供 `show()` 方法将引导浮层添加到 DecorView 上
2. THE GuideOverlay SHALL 提供 `dismiss()` 方法将引导浮层从 DecorView 上移除
3. WHEN `show()` 被调用且 OverlayBackground 已启用时, THE GuideOverlay SHALL 在 DecorView 上添加一个全屏的 OverlayBackground
4. WHEN `show()` 被调用且 OverlayBackground 未启用时, THE GuideOverlay SHALL 不添加 OverlayBackground，仅显示 GuideContent
5. WHEN `show()` 被调用时, THE GuideOverlay SHALL 根据 TargetView 的屏幕位置计算 GuideContent 的放置坐标
6. WHEN `dismiss()` 被调用时, THE GuideOverlay SHALL 从 DecorView 上移除引导浮层
7. WHEN `dismiss()` 被调用且 OnDismissListener 已设置时, THE GuideOverlay SHALL 回调 OnDismissListener
8. WHEN 用户点击 OverlayBackground 区域且 OverlayBackground 已启用时, THE GuideOverlay SHALL 调用 `dismiss()` 关闭引导浮层
9. WHEN dismissOnTouchOutside 已启用且用户点击 GuideContent 之外的区域时, THE GuideOverlay SHALL 调用 `dismiss()` 关闭引导浮层
10. WHEN dismissOnTouchOutside 未启用时, THE GuideOverlay SHALL 不响应 GuideContent 之外区域的点击事件

### 需求 5：TargetView 位置获取

**用户故事：** 作为开发者，我希望组件能够准确获取目标 View 在屏幕上的位置，以便正确定位引导内容。

#### 验收标准

1. THE GuideOverlay SHALL 通过 `getLocationOnScreen()` 获取 TargetView 在屏幕上的绝对坐标
2. THE GuideOverlay SHALL 使用 TargetView 的 `width` 和 `height` 计算 TargetView 的边界矩形
3. WHEN TargetView 尚未完成布局时, THE GuideOverlay SHALL 等待 TargetView 布局完成后再执行定位计算

### 需求 6：TargetView 可见性监听与自动关闭

**用户故事：** 作为开发者，我希望当目标 View 或其父容器变为不可见时，引导浮层能够自动消失，以避免引导内容指向一个不可见的区域。

#### 验收标准

1. WHEN `show()` 被调用时, THE GuideOverlay SHALL 开始监听 TargetView 的可见性变化
2. WHEN TargetView 的 visibility 变为 GONE 时, THE GuideOverlay SHALL 自动调用 `dismiss()` 关闭引导浮层
3. WHEN TargetView 的 visibility 变为 INVISIBLE 时, THE GuideOverlay SHALL 自动调用 `dismiss()` 关闭引导浮层
4. WHEN TargetView 的任意父容器的 visibility 变为 GONE 或 INVISIBLE 时, THE GuideOverlay SHALL 自动调用 `dismiss()` 关闭引导浮层
5. WHEN `dismiss()` 被调用时, THE GuideOverlay SHALL 停止监听 TargetView 的可见性变化
