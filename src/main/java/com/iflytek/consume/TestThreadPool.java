package com.iflytek.consume;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName TestThreadPool
 * @Description TODO
 * @Author jzwu5
 * @Date 2024/7/25 14:51
 * @Version 1.0
 */
@Slf4j
public class TestThreadPool {
    private static final long SLEEP_TIME = 5000; // Sleep time in milliseconds

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 创建任务列表
        List<Future<Double>> futures = new ArrayList<>();

        while (true) {
            try {
                double cpuPercent = getCPUUsage();
                log.info("CPU使用率为{}", cpuPercent);
                if (cpuPercent > 40 && futures.size() > 1) {
                    futures.remove(0);
                    log.info("当前线程池有{}个线程",futures.size());
                    log.info("CPU占用率大于50%,其使用率为{}", cpuPercent);
                } else if (cpuPercent < 30) {
                    Future<Double> future = executorService.submit(new CpuIntensiveTask());
                    futures.add(future);
                    log.info("当前线程池有{}个线程",futures.size());
                    log.info("CPU占用率小于30%,其使用率为{}", cpuPercent);
                }
                Thread.sleep(SLEEP_TIME); // 等待1秒
            } catch (InterruptedException e) {
                executorService.shutdown();
                e.printStackTrace();
            }
        }


    }

    static class CpuIntensiveTask implements Callable<Double> {
        @Override
        public Double call() {
            int i = 1000;
            Double result = 0.0;
            while (true){
                result += Math.sqrt(i) * Math.sin(i++);
            }
        }
    }

    public static double getCPUUsage() throws InterruptedException {

//        SystemInfo systemInfo = new SystemInfo();
//        CentralProcessor processor = systemInfo.getHardware().getProcessor();
//        long[] prevTicks = processor.getSystemCpuLoadTicks();
//        long[] ticks = processor.getSystemCpuLoadTicks();
//        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
//        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
//        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
//        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
//        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
//        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
//        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
//        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
//        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
//        String format = new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu));
//        return Double.parseDouble(format.substring(0, format.length() - 1));
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        double free = cpuInfo.getFree();
        DecimalFormat format = new DecimalFormat("#.00");
        return Double.parseDouble(format.format(100.0D - free));
    }
}
