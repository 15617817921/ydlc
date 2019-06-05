package com.liu.mypop.net;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManger {
    public static ThreadPoolManger threadPoolManger = new ThreadPoolManger();


    public static ThreadPoolManger getInstance() {
        return threadPoolManger;
    }

    //创建队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    //将异步任务添加到队列中
    public void addTask(Runnable runnable) {
        if (runnable != null) {
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //创建线程池
    private ThreadPoolExecutor mThreadPoolExecutor;

    public ThreadPoolManger() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                        addTask(runnable);
                    }
                });
        mThreadPoolExecutor.execute(coreThread);
        mThreadPoolExecutor.execute(delayThread);
    }

    //创建叫号员，让队列和线程池进行交换
    public Runnable coreThread = new Runnable() {
        Runnable runn = null;

        @Override
        public void run() {
            while (true) {
                try {
                    runn = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runn);
            }
        }
    };

    //创建延迟队列（重试机制是要延迟一定时间才会进行重试）
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

    public void addDelayTask(HttpTask ht) {
        if (ht != null) {
            ht.setDelayTime(3000);
            mDelayQueue.offer(ht);
        }
    }
    //创建延迟线程，不停的从延迟队列中获取请求，并提交给线程池
    public Runnable delayThread=new Runnable() {
        HttpTask ht=null;
        @Override
        public void run() {
            while (true){
                try {
                  ht=  mDelayQueue.take();
                  if(ht.getRetryConut()<3){
                      mThreadPoolExecutor.execute(ht);
                      ht.setRetryConut(ht.getRetryConut()+1);
                      Log.e("重试机制",ht.getRetryConut()+"  "+System.currentTimeMillis());
                  }else {
                      Log.e("重试机制","总是不成功，直接放弃");
                  }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
