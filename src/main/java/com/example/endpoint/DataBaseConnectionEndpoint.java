package com.example.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//让Spring扫描类
@Component
//定义端点
@Endpoint(
        //端点id
        id = "dbcheck",
        //是否在默认情况下启用端点
        enableByDefault = true
)
public class DataBaseConnectionEndpoint {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    @Value("${spring.datasource.url}")
    private String url = null;
    @Value("${spring.datasource.username}")
    private String username = null;
    @Value("${spring.datasource.password}")
    private String password = null;
    //一个端点只能存在一个@ReadOperation标注的方法
    //它代表的是HTTP的GET请求
    @ReadOperation
    /*在Actuator中除了可以使用ReadOpertation，还可以使用@WriteOperation和@DeleteOperation，
    其中@WriteOperation代表HTTP的POST请求，@DeleteOperation代表HTTP的DELETE请求
    @WriteOperation只能接收请求类型为application/vnd.spring-boot.actuator.v2+json、application/json类型的请求*/
    public Map<String, Object> test(){
        Connection conn = null;
        HashMap<String, Object> msgMap = new HashMap<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url, username, password);
            msgMap.put("success", true);
            msgMap.put("message", "测试数据库连接成功");
        }catch (Exception ex){
            msgMap.put("success", false);
            msgMap.put("message", ex.getMessage());
        }finally {
            if (conn != null){
                try {
                    conn.close();//关闭数据库连接
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return msgMap;
    }
}
