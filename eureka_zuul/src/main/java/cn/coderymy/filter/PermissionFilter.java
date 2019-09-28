package cn.coderymy.filter;

import cn.coderymy.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.omg.CORBA.portable.UnknownException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class PermissionFilter extends ZuulFilter {
    @Override
    public String filterType() {

        return "pre" ;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        System.out.println("这里是zuul过滤器");
        //共享RequestContext，上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request=requestContext.getRequest();
        String jwt = request.getHeader("Authorization");
//
//        if(jwt==null){
//            return new Exception();
//        }这个地方不需要判空
        System.out.println(request.getRequestURI());
        Claims decode = new JwtUtil().decode(jwt);
        List<String> roleNames = (List<String>) decode.get("roleNameSet");
        System.out.println(roleNames);

        List<String> permissionNameSet = (List<String>) decode.get("permissionNameSet");
/*
* 下面的逻辑就是，首先判断是否是具体的请求类型，比如说请求demo1，需要角色“角色一”，
* 如果有这个角色，就返回false，表示这个过滤器不执行操作
* 如果没有需要的角色，就返回false，让这个过滤器执行
* 在执行过滤器的run方法的时候，执行响应代码，向前台返回错误，401错误，并告诉前台，权限不足
* */
        if ((request.getRequestURI()).contains("/demo1")) {
            if (roleNames.contains("角色1")){
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(401);
        requestContext.getResponse().setCharacterEncoding("UTF-8");
        try {
            requestContext.getResponse().getWriter().write("权限不够");
        } catch (IOException e) {
            return null;
        }

        return null;
    }
}
