package com.example.spring_test;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.example",
exclude = {DataSourceAutoConfiguration.class})
public class SpringTestNameApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTestNameApplication.class, args);
    }

}
