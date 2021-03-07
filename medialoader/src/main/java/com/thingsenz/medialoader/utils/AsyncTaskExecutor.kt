package com.thingsenz.medialoader.utils

import android.os.AsyncTask
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

object AsyncTaskExecutor {

    val CORE_POOL_SIZE=18
    val MAX_POOL_SIZE=128
    val KEEP_ALIVE=1
    val TIME_UNIT=TimeUnit.SECONDS
    val concurrentPoolWorkQueue = LinkedBlockingQueue<Runnable>(10)
    val concurrentThreadFactory = AsyncTaskThreadFactory()
    val concurrentExecutor = ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE.toLong(), TIME_UNIT,
        concurrentPoolWorkQueue, concurrentThreadFactory)

    fun <Params,Progress,Result> executeConcurrently(task: AsyncTask<Params,Progress,Result>,vararg params: Params): AsyncTask<Params,Progress,Result> {
        task.executeOnExecutor(concurrentExecutor, *params)
        return task
    }

    class AsyncTaskThreadFactory: ThreadFactory {
        val count=AtomicInteger(1)
        override fun newThread(p0: Runnable): Thread {
            return Thread(p0,"AsyncTask #"+count.getAndIncrement())
        }
    }



}