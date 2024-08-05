package com.iflytek.consume;

import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * @ClassName Demo2
 * @Description TODO
 * @Author jzwu5
 * @Date 2024/7/25 10:30
 * @Version 1.0
 */
@Slf4j
public class ConsumeMem {
    private static final long SLEEP_TIME = 2000; // Sleep time in milliseconds

    private static final int MEM_UP_LIMIT = 80;

    private static final int MEN_DOWN_LIMIT = 80;

    //每一次新建的byte数组的大小
    private static final int CONSUME_MEM_SIZE = 1024 * 1024 * 512;


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        ArrayList<byte[]> bytes = new ArrayList<>();
        while (true){
            double memPercent = getMemUsage();
            System.out.println(memPercent);
            //随机数,实现资源占用率的波动
            int randomNum = random.nextInt(5) - 3;
            if(memPercent < MEN_DOWN_LIMIT + randomNum){
                log.info("内存占用率小于{}%,其使用率为{}",MEN_DOWN_LIMIT + randomNum,memPercent);
                System.out.println("增加一个数组后bytes的大小为"+bytes.size());
                byte[] bytes1 = new byte[CONSUME_MEM_SIZE];
                bytes.add(bytes1);
            }
            if (memPercent > MEM_UP_LIMIT + randomNum && bytes.size()>1){
                bytes.remove(0);
//                bytes = null;
//                bytes = new ArrayList<>();
                System.out.println("删除一个数组后bytes的大小为"+bytes.size());

                System.gc();
                log.info("内存占用率大于{}%,其使用率为{}",MEM_UP_LIMIT + randomNum,memPercent);
            }
            Thread.sleep(SLEEP_TIME);
        }

    }

    public static double getMemUsage() throws InterruptedException {

        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long acaliableByte = memory.getAvailable();

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
