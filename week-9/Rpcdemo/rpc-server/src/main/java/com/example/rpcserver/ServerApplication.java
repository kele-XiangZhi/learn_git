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

package com.example.rpcserver;

import com.rpc.core.demo.filter.server.BackListFilter;
import com.rpc.core.demo.netty.server.RpcNettyServer;
import com.rpc.core.demo.proxy.ProviderServiceManagement;

/**
 * @author lw1243925457
 */
public class ServerApplication {

    public static void main(String[] args) throws Exception {
        // 添加黑名单
//        BackListFilter.addBackAddress("172.21.16.1");
        BackListFilter.addBackAddress("172.21.16.2");

        final int port = 8080;
        ProviderServiceManagement.init("com.rpc.server.demo.service.impl", port);

        final RpcNettyServer rpcNettyServer = new RpcNettyServer(port);

        try {
            rpcNettyServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rpcNettyServer.destroy();
        }
    }
}
