package com.xiaozhi;

import java.util.concurrent.*;

/**
 * 线程池
 *
 * @author zhangzy
 * @date 2021/3/17-20:57
 * @since v1.0
 */
public class ThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 6000,
                TimeUnit.MICROSECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//        for(int i=0;i<11;i++) {
//            threadPoolExecutor.execute(new ThreadTest());
//        }
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<String> futureTask =
                new FutureTask<String>(new ThreadTest());
        Future<?> submit = threadPoolExecutor.submit(futureTask);
        System.out.println("submit: " + submit.get());
        threadPoolExecutor.shutdown();
        boolean terminated = threadPoolExecutor.isShutdown();
        System.out.println(terminated);
        threadPoolExecutor.shutdownNow();
        System.out.println(threadPoolExecutor.isTerminated());
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();


    }

}
