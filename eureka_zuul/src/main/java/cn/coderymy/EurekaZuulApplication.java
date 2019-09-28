package cn.coderymy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@MapperScan("cn.coderymy.dao")
@EnableZuulProxy
@SpringBootApplication
@EnableEurekaClient
public class EurekaZuulApplication {

    public static void main(String[] args) {

        SpringApplication.run(EurekaZuulApplication.class, args);
    }

//    @Bean
//    public JwtFilter jwtFilter() {
//        return new JwtFilter();
//    }
}
