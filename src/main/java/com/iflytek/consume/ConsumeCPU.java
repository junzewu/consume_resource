package com.iflytek.consume;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @ClassName ConsumeCPU
 * @Description TODO
 * @Author jzwu5
 * @Date 2024/7/25 14:51
 * @Version 1.0
 */
@Slf4j
public class ConsumeCPU {
    private static final long SLEEP_TIME = 5000; // Sleep time in milliseconds

    private static final int CPU_UP_LIMIT = 60;

    private static final int CPU_DOWN_LIMIT = 40;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 创建任务列表
        List<Future<Double>> futures = new ArrayList<>();
        Random random = new Random();

        while (true) {
            try {
                double cpuPercent = getCPUUsage();
                log.info("CPU使用率为{}", cpuPercent);
                //随机数,实现资源占用率的波动
                int randomNum = random.nextInt(5) - 3;
                if (cpuPercent > CPU_UP_LIMIT + randomNum && futures.size() > 1) {
                    futures.remove(0);
                    log.info("当前线程池有{}个线程",futures.size());
                    log.info("CPU占用率大于{}%,其使用率为{}", CPU_UP_LIMIT+ randomNum,cpuPercent);
                } else if (cpuPercent < CPU_DOWN_LIMIT + randomNum) {
                    Future<Double> future = executorService.submit(new CpuIntensiveTask());
                    futures.add(future);
                    log.info("当前线程池有{}个线程",futures.size());
                    log.info("CPU占用率小于{}%,其使用率为{}", CPU_DOWN_LIMIT+ randomNum,cpuPercent);
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
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        double free = cpuInfo.getFree();
        DecimalFormat format = new DecimalFormat("#.00");
        return Double.parseDouble(format.format(100.0D - free));
    }
}
