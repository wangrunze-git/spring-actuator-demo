package com.example.spring_test;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.example",
exclude = {DataSourceAutoConfiguration.class})
public class SpringTestNameApplication extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //编码器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //使用内存存储
        auth.inMemoryAuthentication()
                //设置密码编码器
            .passwordEncoder(passwordEncoder)
                //注册用户admin，密码为abc，并赋予USER和ADMIN的角色权限
            .withUser("admin")
                //可通过passwordEncoder.encode("abc")得到加密后的密码
            .password(passwordEncoder.encode("abc"))
                //赋予角色ROLE_USER和ROLE_ADMIN
            .roles("USER", "ADMIN")
                //连接方法and
            .and()
                //注册用户myUser，密码为123加密后的密码
            .withUser("myuser")
            .password(passwordEncoder.encode("123"))
                //赋予角色ROLE_USER
            .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //需要Spring Security保护的端点
        String[] endPoints = {"auditevents", "beans", "conditions", "configprops",
        "env", "flyway", "httptrace", "loggers", "liquibase", "metrics", "mappings", "scheduledtasks",
        "sessions", "shutdown", "threaddump"};
        //定义需要验证的端点
        /*http.
                authorizeRequests()
                .antMatchers("/test/page1", "/test/page2").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
            .and().anonymous()
            .and().formLogin()
                .and().httpBasic();*/

        http
                //签名登录后
            .authorizeRequests().antMatchers(endPoints)
                //要求登录用户拥有myuser角色
            .hasRole("ADMIN")
            .and()
                //请求关闭页面需要ROLE_ADMIN角色
            .antMatcher("/close")
            .authorizeRequests().anyRequest()
            .hasRole("ADMIN")
            .and()
                //启动HTTP基础验证
            .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringTestNameApplication.class, args);
    }

}
