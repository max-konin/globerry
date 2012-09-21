/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.utils;

import com.globerry.project.service.UserCityService;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author max
 */
public class ExecuteQueryTimer
{
    private static volatile ExecuteQueryTimer instance;
    
    private int requestCount;
    
    private long allTime;
    
    private long newStartTime;
    
    private long newEndTime;
    
    protected static final Logger logger = Logger.getLogger(ExecuteQueryTimer.class);
    
    protected ExecuteQueryTimer()
    {
       
    }
    public static ExecuteQueryTimer getInstanse()
    {
        ExecuteQueryTimer localInstance = instance;
        if (localInstance == null) {
            synchronized (ExecuteQueryTimer.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ExecuteQueryTimer();
                }
            }
        }
        return localInstance;
    }
    
    public void start()
    {
        newStartTime = System.nanoTime();
        requestCount++;
    }
    public void stop()
    {
        newEndTime = System.nanoTime();
        logger.info(String.format("%d: %f", requestCount, (float)(newEndTime - newStartTime)/1000000f));
        allTime += newEndTime - newStartTime;
    }
    public void closeSpy()
    {
        logger.info(String.format("All time: %f", (float)allTime/1000000f));
        logger.info(String.format("Average time: %f", (float)allTime/(float)requestCount/1000000f));
        logger.info("******END_SESSION*******");
        allTime = 0;
        requestCount = 0;
    }
}
