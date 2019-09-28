package cn.coderymy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurekaProDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaProDemo2Application.class, args);
    }

}
