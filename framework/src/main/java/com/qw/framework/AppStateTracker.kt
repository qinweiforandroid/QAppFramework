package com.qw.framework

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.SystemClock
import java.util.LinkedList

/**
 * app状态跟踪工具，职责：
 * 1、存储activity栈
 * 2、获取栈顶activity
 * 3、提供前后台状态变化监听
 * 4、提供前后台判断函数
 * Created by qinwei on 2016/9/24 21:35
 * email:qinwei_it@163.com
 */
object AppStateTracker {
    private val activityList = LinkedList<Activity>()
    private val appStateListener = mutableListOf<OnAppStateListener>()
    private val activityLifecycleCallbacks = mutableListOf<OnActivityLifecycleCallbacks>()

    private var mStartedCounter = 0

    /**
     * App是否在前台
     */
    val isForeground: Boolean
        get() = mStartedCounter > 0


    /**
     * do listener ActivityLifecycle
     */
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : AbsActivityLifecycleCallbacks() {
            override fun onActivityStarted(activity: Activity) {
                activityLifecycleCallbacks.forEach {
                    it.onActivityStarted(activity)
                }
            }

            override fun onActivityResumed(activity: Activity) {
                super.onActivityResumed(activity)
                activityLifecycleCallbacks.forEach {
                    it.onActivityResumed(activity)
                }
            }

            override fun onActivityPaused(activity: Activity) {
                super.onActivityPaused(activity)
                activityLifecycleCallbacks.forEach {
                    it.onActivityPaused(activity)
                }
            }

            override fun onActivityStopped(activity: Activity) {
                activityLifecycleCallbacks.forEach {
                    it.onActivityStopped(activity)
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activityLifecycleCallbacks.forEach {
                    it.onActivityCreated(activity, savedInstanceState)
                }
            }

            override fun onActivityDestroyed(activity: Activity) {
                activityLifecycleCallbacks.forEach {
                    it.onActivityDestroyed(activity)
                }
            }
        })
        registerActivityLifecycleCallbacks(object : OnActivityLifecycleCallbacks {
            //进入后台时间戳
            private var backgroundTimeStamp = 0L

            //进入前台时间戳
            private var foregroundTimeStamp = 0L

            private fun notifyOnForeground(activity: Activity) {
                //后台停留时间
                val timestamp = SystemClock.elapsedRealtime()
                val during = if (backgroundTimeStamp == 0L) {
                    0
                } else {
                    timestamp - backgroundTimeStamp
                }
                foregroundTimeStamp = timestamp
                appStateListener.forEach { it.onForeground(activity, during) }
            }

            private fun notifyOnBackground(activity: Activity) {
                //前台停留时间
                val timestamp = SystemClock.elapsedRealtime()
                val during = timestamp - foregroundTimeStamp
                backgroundTimeStamp = timestamp
                appStateListener.forEach { it.onBackground(activity, during) }
            }

            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                activityList.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                if (mStartedCounter == 0) {
                    notifyOnForeground(activity)
                }
                mStartedCounter++
            }

            override fun onActivityStopped(activity: Activity) {
                mStartedCounter--
                if (mStartedCounter == 0) {
                    notifyOnBackground(activity)
                }
            }

            override fun onActivityDestroyed(activity: Activity) {
                activityList.remove(activity)
                if (activityList.isEmpty()) {
                    //退出app
                    backgroundTimeStamp = 0L
                    foregroundTimeStamp = 0L
                }
            }
        })
    }


    fun registerActivityLifecycleCallbacks(callback: OnActivityLifecycleCallbacks) {
        activityLifecycleCallbacks.add(callback)
    }

    fun unregisterActivityLifecycleCallbacks(callback: OnActivityLifecycleCallbacks) {
        activityLifecycleCallbacks.remove(callback)
    }


    fun top(): Activity? {
        return activityList.lastOrNull()
    }

    fun registerAppStateListener(listener: OnAppStateListener) {
        appStateListener.add(listener)
    }

    fun unRegisterAppStateListener(listener: OnAppStateListener): Boolean {
        return appStateListener.remove(listener)
    }

    /**
     * App前后台状态发生改变监听器
     */
    interface OnAppStateListener {
        fun onBackground(activity: Activity, during: Long)

        /**
         * @param during if 0  代表冷启动进前台
         */
        fun onForeground(activity: Activity, during: Long)
    }

    interface OnActivityLifecycleCallbacks {
        fun onActivityCreated(activity: Activity, p1: Bundle?)

        fun onActivityStarted(activity: Activity)

        fun onActivityStopped(activity: Activity)

        fun onActivityDestroyed(activity: Activity)
        fun onActivityResumed(activity: Activity) {}
        fun onActivityPaused(activity: Activity) {}
    }

    private open class AbsActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }
    }
}