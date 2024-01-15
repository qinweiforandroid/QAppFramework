package com.qw.framework

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * app线程池
 */
object AppExecutors {
    private var diskIO: Executor = Executors.newSingleThreadExecutor()
    private var networkIO: Executor = Executors.newFixedThreadPool(10)
    private val mainThread: Executor = MainThreadExecutor()

    fun init(diskIO: Executor, networkIO: Executor) {
        AppExecutors.diskIO = diskIO
        AppExecutors.networkIO = networkIO
    }

    /**
     * IO处理
     */
    fun diskIO(): Executor {
        return diskIO
    }

    /**
     * 网络处理
     */
    fun networkIO(): Executor {
        return networkIO
    }

    /**
     * 切到主线程
     */
    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}