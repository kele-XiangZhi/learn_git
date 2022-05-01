package com.example.zhi.mysqldemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 *
 *  // 考你们一个问题：如果jdk11 G1GC下，young区(eden+survivor)一直没有对象，那么jvm会不会产生young gc？
 *     // 如果你的回答是会，那么我甚至进一步问：如果old和young，都是空的呢，会不会触发young gc？
 *     // java -version: openjdk version "11.0.9" 2020-10-20
 *     // java -Xmx4g -Xms4g '-Xlog:gc*=info,heap*=info:HumongousRegions-gc.log:time,level,tags' g1gc.HumongousRegions 1000
 *     // 可以验证:
 *     // 1. 去掉第一个和第三个循环，可以验证HumongousRegions占比45%的时候，其他区域(Young+Old)都是0，也会 Pause Young (Concurrent Start) (G1 Humongous Allocation)
 *     // 2. 使用前两个循环，可以验证，HumongousRegions+Old Regions合起来为45%，也会 Pause Young (Concurrent Start) (G1 Humongous Allocation)
 *     // 3. 使用不同h_count,可以验证 HumongousRegions 最大可以占满整个堆，也就是把old和young都挤占没有了
 *     // 4. 可以验证G1 Young GC的最小间隔为10ms，即Humogous+Old大于45%以后，不是每次分配都会YGC，而是要等10ms以后触发下次YGC
 * @Author
 * @Data 2022/4/19 16:18
 */
public class HumGCTest {
    public static void main(String[] args) throws IOException {
        // XMX:4G => region:2M => big object 1M+
        int h_size = 1024*1024+1; //1M+1
        int h_count = Integer.parseInt(args[0]);
        List<byte[]> list = new ArrayList();

        // 第一个循环
        for (int i = 0;i<h_count/2;i++) {
            list.add(createHumongousBytes(h_size/2));
        }

        // 第二个循环
        for (int i = 0;i<h_count;i++) {
            list.add(createHumongousBytes(h_size));
        }

        // 第三个循环
        for (int i = 0;i<h_count;i++) {
            createHumongousBytes(h_size/2);
        }

        // keep running for tuning
        System.out.println("wait any keys...");
        int c = 0;
        while((c = System.in.read()) > 0){
            System.out.println("list size:" + list.size());
            if (c == 'q') {
                System.out.println("quit with q!"); break;
            } else {
                System.out.println("read a:" + c + "/" + (char) c);
            }
        }
    }

    static byte[] createHumongousBytes(final int size) {
        return new byte[size];
    }
}
