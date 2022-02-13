package com.example.annotateP;

/**
 * 自动装配 starter
 */
public class TestStarter{
    private String name;
    private String id;

    public TestStarter(String name, String id){
        this.name= this.name;
        this.id= this.id;
    }


    public int getUserInfo(String msg) {
        // 调用http服务并发送消息，返回结果
        return UserInfoUtils.sendInfo(name, id, msg);
    }
}
