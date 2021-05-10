package com.xiaozhi;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 线程池
 *
 * @author zhangzy
 * @date 2021/3/17-20:57
 * @since v1.0
 */
public class ThreadPool {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 6000,
                TimeUnit.MICROSECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        for(int i=0;i<11;i++) {
            threadPoolExecutor.execute(new ThreadTest());
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> submit = executorService.submit(new ThreadTest());
        System.out.println(submit);
        executorService.shutdown();
        boolean terminated = executorService.isTerminated();

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();


    }

}
