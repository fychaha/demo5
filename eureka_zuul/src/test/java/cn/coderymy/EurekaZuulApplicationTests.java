package cn.coderymy;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EurekaZuulApplicationTests {

    @Test
    public void contextLoads() {
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("username","password");

        SecurityUtils.getSubject().hasRole("xxx");
    }

}
