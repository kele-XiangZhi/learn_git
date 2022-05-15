# 基于Redis的PubSub实现订单异步处理
***
## 作业要求
&ensp;&ensp;&ensp;&ensp;基于Redis的PubSub实现订单异步处理

## 作业思路
&ensp;&ensp;&ensp;&ensp;使用jedis的相关函数实现,简单创建两个类，一个用于处理订单，一个发布订单


### 订单订阅处理
&ensp;&ensp;&ensp;&ensp;生成一个订单订阅处理类：SubscribeOrder，使用多线程进行启动，不然会阻塞，当收到消息为空时结束

```java
public class SubscribeOrder {

    public SubscribeOrder(final JedisPool jedisPool, final String channelName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("start subscriber order");
                try(Jedis jedis = jedisPool.getResource()) {
                    jedis.subscribe(setupSubscriber(), channelName);
                }
            }
        }, "subscriberThread").start();
    }

    private JedisPubSub setupSubscriber() {
        return new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                if (message.isEmpty()) {
                    System.out.println("SubPub End");
                    System.exit(0);
                }
                System.out.printf("Receive message from %s :: %s\n", channel, message);
            }
        };
    }
}
```

### 订单发布
&ensp;&ensp;&ensp;&ensp;PublishOrder,生成随机数，发布十次订单

```java
public class PublishOrder {

    public PublishOrder(JedisPool jedisPool, String channelName) {
        System.out.println("Start publisher order");
        try (Jedis jedis = jedisPool.getResource()) {
            int sleepTime = 0;
            for (int i = 0; i < 10; i++) {
                sleepTime = new Random().nextInt(10) + 1;

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                jedis.publish(channelName, "order sleep " + sleepTime);
            }
            jedis.publish(channelName, "");
        }
    }
}
```

### 运行
&ensp;&ensp;&ensp;&ensp;运行Main类，看到下面结果：

```shell script
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
start subscriber order
Start publisher order
Receive message from ORDER :: order sleep 9
Receive message from ORDER :: order sleep 7
Receive message from ORDER :: order sleep 10
Receive message from ORDER :: order sleep 10
Receive message from ORDER :: order sleep 10
Receive message from ORDER :: order sleep 3
Receive message from ORDER :: order sleep 2
Receive message from ORDER :: order sleep 2
Receive message from ORDER :: order sleep 9
SubPub End

Process finished with exit code 0
```