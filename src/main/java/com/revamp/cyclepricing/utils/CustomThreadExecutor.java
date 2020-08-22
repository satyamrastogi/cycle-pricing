package com.revamp.cyclepricing.utils;

import java.util.concurrent.*;

public class CustomThreadExecutor extends ThreadPoolExecutor {

    public CustomThreadExecutor(int corePoolSize, int maximumPoolSize,
                                    long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }


}