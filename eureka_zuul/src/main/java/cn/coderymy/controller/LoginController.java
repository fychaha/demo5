package cn.coderymy.controller;

import cn.coderymy.service.LoginService;
import cn.coderymy.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @RequestMapping("/login")
    public ResponseEntity<Map<String, String>> login(String username, String password) {
        Map<String, Set<String>> login = loginService.Login(username, password);
        log.info("login="+login);
        log.info("username:{},password:{}", username, password);
        Map<String, String> map = new HashMap<>();
        if (login==null) {
            map.put("msg", "用户名密码错误");
            return ResponseEntity.ok(map);
        }
        JwtUtil jwtUtil = new JwtUtil();
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("username", username);
        chaim.put("roleNameSet",login.get("roleNameSet"));
        chaim.put("permissionNameSet",login.get("permissionNameSet"));
        String jwtToken = jwtUtil.encode(username, 5 * 60 * 1000, chaim);
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set(username,jwtToken,5*60, TimeUnit.SECONDS);
        map.put("chain",String.valueOf(chaim));
        map.put("msg", "登录成功");
        map.put("token", jwtToken);
        return ResponseEntity.ok(map);
    }

    @RequestMapping("/testdemo")
    public ResponseEntity<String> testdemo() {


        return ResponseEntity.ok("我爱蛋炒饭");
    }
    @RequestMapping("/testdemo1")
    public ResponseEntity<String> testdemo1() {
        return ResponseEntity.ok("我爱蛋炒饭");
    }
}

