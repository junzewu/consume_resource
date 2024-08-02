import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.util.GlobalConfig;

import java.text.DecimalFormat;

/**
 * @ClassName Demo
 * @Description TODO
 * @Author jzwu5
 * @Date 2024/7/25 10:06
 * @Version 1.0
 */
@Slf4j
public class ConsumeCPU {
    private static final long SLEEP_TIME = 1000; // Sleep time in milliseconds

    public static void main(String[] args) throws InterruptedException {
        GlobalConfig.set(GlobalConfig.OSHI_OS_WINDOWS_CPU_UTILITY, true);
        long i = 1000000000;
        double result = 0.0;
        while (true) {
            double cpuPercent = getCPUUsage();
//            System.out.println(cpuPercent);
            if(i % 10 == 0){
                log.info("CPU使用率为{}", cpuPercent);
            }
            if (cpuPercent > 80) {
                log.info("CPU占用率大于80%,其使用率为{}", cpuPercent);
                Thread.sleep(SLEEP_TIME);
            }
            result += Math.sqrt(i) * Math.sin(i++);

//            consumeCPU();
        }
    }


    public static void consumeCPU() {
        int i = 1000000;
        double result = 0.0;
        for (; i < i + 1000; i++) {
            result += Math.sqrt(i) * Math.sin(i);
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
//            System.out.println("CPU利用率"+format);
//        System.out.println(Double.parseDouble(format.substring(0,format.length() - 1)));
//            log.info("CPU总数 = {},CPU利用率 ={}", processor.getLogicalProcessorCount(), new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu)));
//        return Double.parseDouble(format.substring(0, format.length() - 1));

        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        double free = cpuInfo.getFree();
        DecimalFormat format = new DecimalFormat("#.00");
        return Double.parseDouble(format.format(100.0D - free));

    }


}
