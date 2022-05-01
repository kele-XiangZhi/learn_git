package com.example.zhi.mysqldemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import javax.sql.DataSource;

/**
 * @Description
 * @Author
 * @Data 2022/4/15 22:59
 */
@RestController
public class HelloText {

    @Autowired
    DataSource dataSource;

    @RequestMapping("/restHello")
    public Object restHello() throws Exception {
        Connection connect = dataSource.getConnection();
        PreparedStatement pre = connect.prepareStatement("select * from order_info");
        ResultSet result = pre.executeQuery();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        while (result.next()) {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("id", result.getObject("id"));
            map.put("user_id", result.getObject("user_id"));
            map.put("address_get", result.getObject("address_get"));
            map.put("iphone_user", result.getObject("iphone_user"));
            list.add(map);
        }
        if(result!= null ) result.close();
        if(pre!= null ) pre.close();
        if(connect!= null ) connect.close();
        return list;
    }
}
