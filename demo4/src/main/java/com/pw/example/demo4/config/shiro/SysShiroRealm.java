package com.pw.example.demo4.config.shiro;
import com.pw.example.demo4.UserService;
import com.pw.example.demo4.model.RoleInfo;
import com.pw.example.demo4.model.UserInfo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import java.util.List;

/**
 * Created by Administrator on 2019/3/18 0018.
 */

public class SysShiroRealm  extends AuthorizingRealm{

    @Autowired
    @Lazy
    UserService userService;
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo  = (UserInfo) principalCollection.getPrimaryPrincipal();
        List<RoleInfo> roleList=userService.getUserRoleList(userInfo);
        for (RoleInfo roleInfo:roleList){
            authorizationInfo.addRole(roleInfo.getRole());
        }
        return authorizationInfo;
    }
    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // String username = (String)token.getPrincipal();
        UsernamePasswordToken upt = (UsernamePasswordToken) token;

        UserInfo userInfo = userService.findUser(upt.getUsername());
        if(userInfo==null){
            //如果帐号不存在，输出
            throw new UnknownAccountException();
        }
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo,userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),getName());
        return authenticationInfo;
    }

}

