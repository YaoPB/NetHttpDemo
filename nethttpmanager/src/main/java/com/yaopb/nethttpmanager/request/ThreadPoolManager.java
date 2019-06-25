package com.yaopb.nethttpmanager.request;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//线程池管理类
public class ThreadPoolManager {
    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    // 线程池
    private ThreadPoolExecutor mThreadPoolExecutor;

    public static ThreadPoolManager getInstance() {
        return threadPoolManager;
    }

    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                try {
                    addTask(runnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mThreadPoolExecutor.execute(coreThread);
        mThreadPoolExecutor.execute(delayThread);

    }

    //创建正常队列
    private LinkedBlockingDeque<Runnable> mQueue = new LinkedBlockingDeque<>();

    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

    public void addDelayTask(HttpTask httpTask) {
        if (httpTask != null) {
            httpTask.setDelayTime(3);
            mDelayQueue.put(httpTask);
        }

    }

    public void addTask(Runnable runnable) throws InterruptedException {
        if (runnable != null) {
            mQueue.put(runnable);
        }
    }

    // 创建核心线程，去队列中获取请求，提交给线程池处理
    public Runnable coreThread = new Runnable() {
        Runnable runnable = null;

        @Override
        public void run() {
            while (true) {
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runnable);
            }
        }
    };

    public Runnable delayThread = new Runnable() {
        @Override
        public void run() {
            // 判断网络是否连接成功

            HttpTask ht = null;
            while (true) {
                try {
                    ht = mDelayQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ht.getRetryCount() < 3) {
                    mThreadPoolExecutor.execute(ht);
                    ht.setRetryCount(ht.getRetryCount() + 1);
                    Log.e("==重拾机制===", ht.getRetryCount() + " " + System.currentTimeMillis());
                } else {
                    Log.e("==重拾机制过多===", "重拾过多");

                }
            }
        }
    };
}
