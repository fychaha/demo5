package cn.coderymy.realm;

import cn.coderymy.dao.*;
import cn.coderymy.po.*;
import cn.coderymy.shiro.JwtToken;
import cn.coderymy.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("进行授权");
        String jwt = (String) principals.getPrimaryPrincipal();
        if (jwt == null)
            throw new NullPointerException("jwt token 不允许为空");

        JwtUtil jwtUtil = new JwtUtil();
        if (!jwtUtil.isVerify(jwt))
            throw new UnknownAccountException();
        Claims decode = jwtUtil.decode(jwt);
        Set<String> roleNames = (Set<String>) decode.get("roleNameSet");
        Set<String> permissionNameSet = (Set<String>) decode.get("permissionNameSet");
        log.info("roleName:[{}]", roleNames);
        log.info("permissionNameSet:[{}]", permissionNameSet);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleNames);
        info.setStringPermissions(permissionNameSet);
        return info;
    }

    //认证
    //这个token就是从过滤器中传入的jwtToken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String jwt = (String) token.getPrincipal();
        if (jwt == null) {
            throw new NullPointerException("jwtToken 不允许为空");
        }
        //判断
        JwtUtil jwtUtil = new JwtUtil();
        if (!jwtUtil.isVerify(jwt)) {
            throw new UnknownAccountException();
        }
        //这个地方进行redis的存在的校验
        if(redisTemplate.opsForValue().get(jwtUtil.decode(jwt).get("username"))==null){

            throw new UnknownAccountException();
        }
        //下面是验证这个user是否是真实存在的
        String username = (String) jwtUtil.decode(jwt).get("username");//判断数据库中username是否存在
        log.info("在使用token登录" + username);
        return new SimpleAuthenticationInfo(jwt, jwt, "JwtRealm");
        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名

    }

}
