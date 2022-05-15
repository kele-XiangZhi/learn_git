package com.example.rpcclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RpcClientApplication {

    public static void main(String[] args) {
        // fastjson auto setting
        ParserConfig.getGlobalInstance().addAccept("com.rpc.demo.model.Order");
        ParserConfig.getGlobalInstance().addAccept("com.rpc.demo.model.User");

        // set retry time
        Retry.setRetryLimit(3);

        RpcClient client = new RpcClient();
        RpcClient.setBalanceAlgorithmName(ConsistentHashBalance.NAME);

        UserService userService = client.create(UserService.class, "group2", "v2");
        User user = userService.findById(1);
        if (user == null) {
            log.info("Clint service invoke Error");
        } else {
            System.out.println("\n\nuser1 :: find user id=1 from server: " + user.getName());
        }

        OrderService orderService = client.create(OrderService.class);
        Order order = orderService.findById(1992129);
        if (order == null) {
            log.info("Clint service invoke Error");
        } else {
            System.out.println("\n\n" + String.format("find order name=%s, user=%d", order.getName(), order.getUserId()));
        }

        UserService userService2 = client.create(UserService.class, "group2", "v2", Arrays.asList("tag1", "tag2"));
        User user2 = userService2.findById(1);
        if (user2 == null) {
            log.info("Clint service invoke Error");
        } else {
            System.out.println("\n\nuser2 :: find user id=1 from server: " + user2.getName());
        }

        UserService userService3 = client.create(UserService.class, "group2", "v2", Arrays.asList("tag3", "tag4"));
        User user3 = userService3.findById(1);
        if (user3 == null) {
            log.info("Clint service invoke Error");
        } else {
            System.out.println("\n\nuser3 :: find user id=1 from server: " + user3.getName());
        }

        // test load balance
        System.out.println("\n=============== test load balance =======================\n");
        UserService userService4 = client.create(UserService.class);
        List<User> userList = new ArrayList<>(10);
        for(int i = 0; i < 10; i++) {
            userList.add(userService4.findById(1));
        }
        for (User item: userList) {
            System.out.println(item);
        }

       ;
    }

}
