package com.iflytek.consume;

import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @ClassName Demo2
 * @Description TODO
 * @Author jzwu5
 * @Date 2024/7/25 10:30
 * @Version 1.0
 */
@Slf4j
public class ConsumeMem {
    private static final long SLEEP_TIME = 1000; // Sleep time in milliseconds

    public static void main(String[] args) throws InterruptedException {

        ArrayList<byte[]> bytes = new ArrayList<>();
        while (true){
            double memPercent = getMemUsage();
            System.out.println(memPercent);
            if(memPercent < 80){
                log.info("内存占用率小于80%,其使用率为{}",memPercent);
                System.out.println("bytes的大小为"+bytes.size());
                byte[] bytes1 = new byte[1024 * 1024*256];
                bytes.add(bytes1);
            }
            if (memPercent > 80 && bytes.size()>1){
                bytes = null;
                bytes = new ArrayList<>();
                System.out.println("bytes的大小为"+bytes.size());

                Runtime.getRuntime().gc();
                log.info("内存占用率大于80%,其使用率为{}",memPercent);
            }
            Thread.sleep(SLEEP_TIME);
        }

    }

    public static double getMemUsage() throws InterruptedException {

        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long acaliableByte = memory.getAvailable();

//        log.info("内存大小 = {},内存使用率 ={}", formatByte(totalByte), new DecimalFormat("#.##%").format((totalByte - acaliableByte) * 1.0 / totalByte));
//        Thread.sleep(1000);
        String format = new DecimalFormat("#.##%").format((totalByte - acaliableByte) * 1.0 / totalByte);
        return Double.parseDouble(format.substring(0, format.length() - 1));
    }

    public static String formatByte(long byteNumber) {
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

}
