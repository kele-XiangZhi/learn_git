package com.example.mysql_demo2;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;



@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
class MysqlDemo2ApplicationTests {
    private static Logger logger = LoggerFactory.getLogger(MysqlDemo2ApplicationTests.class);

    @Resource
    private DataSource dataSource;

    /**
     * 数据分片
     * t_user在同一个库里分表(t_user_0,t_user_1)
     * t_dept分库
     */
    @Test
    public void fragmentation() throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            Statement st = con.createStatement();

            //插入ds0.t_user_0表
            st.executeUpdate("insert into t_user(user_id,user_name,age) values(10,'赵云', 30)");
            //插入ds0.t_user_1表
            st.executeUpdate("insert into t_user(user_id,user_name,age) values(11,'张飞', 31)");

            //插入ds0.t_dept表
            st.executeUpdate("insert into t_dept(dept_id,dept_name) values(10,'dept10')");
            //插入ds1.t_dept表
            st.executeUpdate("insert into t_dept(dept_id,dept_name) values(11,'dept11')");

            ResultSet rs = st.executeQuery("select user_id,user_name from t_user where user_id in(10,11)");
            while (rs.next()) {
                logger.info("user_id={},user_name={}", rs.getString("user_id"), rs.getString("user_name"));
            }

            rs = st.executeQuery("select dept_id,dept_name from t_dept where dept_id in(10,11)");
            while (rs.next()) {
                logger.info("dept_id={},dept_name={}", rs.getString("dept_id"), rs.getString("dept_name"));
            }

            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
    }


    /**
     * 读写分离，主库写，从库读
     * 同一线程且同一数据库连接内，如有写入操作，以后的读操作均从主库读取，用于保证数据一致性
     */
    @Test
    public void readWrite() throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            Statement st = con.createStatement();

            //从slave0读数据
            ResultSet rs = st.executeQuery("select * from t_student");
            while (rs.next()) {
                System.out.println(rs.getString("id") + "|" + rs.getString("name"));
            }

            //写入master
            st.executeUpdate("insert into t_student(id,name) values(100,'测试')");

            //从master读数据
            rs = st.executeQuery("select * from t_student");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "|" + rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    /**
     * 读写分离，强制主库路由
     */
    @Test
    public void hintMasterRouteOnly() throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            Statement st = con.createStatement();
            HintManager hintManager = HintManager.getInstance();
            //主库路由
            hintManager.setMasterRouteOnly();

            //从master读数据
            ResultSet rs = st.executeQuery("select * from a_orm");
            while (rs.next()) {
                logger.info(rs.getString(1) + "|" + rs.getString(2));
            }
            hintManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

}
