/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scene;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Redis 全局ID生成
 *
 * @author lw1243925457
 */
public class GenerateId {

    private enum EnumSingleton {
        /**
         * 懒汉枚举单例
         */
        INSTANCE;
        private GenerateId instance;

        EnumSingleton(){
            instance = new GenerateId();
        }
        public GenerateId getSingleton(){
            return instance;
        }
    }

    public static GenerateId getInstance(){
        return EnumSingleton.INSTANCE.getSingleton();
    }

    private JedisPool jedisPool = new JedisPool();

    private String generateOrderId() {
        LocalDateTime now = LocalDateTime.now();
        String orderIdPrefix = getOrderIdPrefix(now);
        return orderIdPrefix + String.format("%1$06d", generate(orderIdPrefix, 5));
    }

    private String getOrderIdPrefix(LocalDateTime now) {
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    private long generate(String key, int timeout) {
        try(Jedis jedis = jedisPool.getResource()) {
            Long id = jedis.incr(key);
            if (timeout > 0) {
                jedis.expire(key, timeout);
            }
            return id;
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            System.out.println(GenerateId.getInstance().generateOrderId());
        }
    }
}
