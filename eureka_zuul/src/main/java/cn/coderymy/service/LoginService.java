package cn.coderymy.service;

import cn.coderymy.dao.*;
import cn.coderymy.po.*;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoginService {
    @Autowired
    private JwtUsersDAO jwtUsersDAO;
    @Autowired
    private JwtUsersRolesDAO jwtUsersRolesDAO;
    @Autowired
    private JwtRolesPermissionsDAO jwtRolesPermissionsDAO;
    @Autowired
    private JwtRolesDAO jwtRolesDAO;
    @Autowired
    private JwtPermissionsDAO jwtPermissionsDAO;


    public Map<String, Set<String>> Login(String username, String password) {
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> roleNameSet = new HashSet<>();
        Set<String> permissionNameSet = new HashSet<>();

        /*
         * 1. 先进行认证,也就是根据username和password判断是否有这个人
         * */
        JwtUsersExample usersExample = new JwtUsersExample();
        usersExample.createCriteria().andUsernameEqualTo(username);
        List<JwtUsers> jwtUsers = jwtUsersDAO.selectByExample(usersExample);
        if (jwtUsers != null) {
            /*
             * 这里获取数据库中的权限，将权限放到set中，将set放到权限中
             * */
            JwtUsersRolesExample jwtUsersRolesExample = new JwtUsersRolesExample();
            jwtUsersRolesExample.or().andUserIdEqualTo(jwtUsers.get(0).getId());
            List<JwtUsersRoles> roles = jwtUsersRolesDAO.selectByExample(jwtUsersRolesExample);

            for (JwtUsersRoles role : roles) {
                JwtRolesExample jwtRolesExample = new JwtRolesExample();
                jwtRolesExample.or().andIdEqualTo(role.getRoleId());
                List<JwtRoles> jwtRoles = jwtRolesDAO.selectByExample(jwtRolesExample);
                if (jwtRoles.size() != 0 && jwtRoles != null) {
                    roleNameSet.add(jwtRoles.get(0).getRole());
                }
                JwtRolesPermissionsExample jwtRolesPermissionsExample = new JwtRolesPermissionsExample();
                jwtRolesPermissionsExample.or().andRoleIdEqualTo(role.getRoleId());
                List<JwtRolesPermissions> jwtRolesPermissions = jwtRolesPermissionsDAO.selectByExample(jwtRolesPermissionsExample);
                for (JwtRolesPermissions jwtRolesPermissions1 : jwtRolesPermissions) {
                    JwtPermissionsExample jwtPermissionsExample = new JwtPermissionsExample();
                    jwtPermissionsExample.or().andIdEqualTo(jwtRolesPermissions1.getId());
                    List<JwtPermissions> jwtPermissions = jwtPermissionsDAO.selectByExample(jwtPermissionsExample);
                    if (jwtPermissions.size() != 0 && jwtPermissions != null) {
                        permissionNameSet.add(jwtPermissions.get(0).getPermission());
                    }
                }
            }

        }
        map.put("roleNameSet", roleNameSet);
        map.put("permissionNameSet", permissionNameSet);
//TODO 这个地方还需要加上redis的验证，也就是login的时候，向redis创建一个字段，保存登录的用户，然后在验证的时候需要验证这个redis中是否还存在这个用户
        //以判断用户是否登出
        return map;
    }

}
