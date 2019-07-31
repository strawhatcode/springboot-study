package com.example.shiro.config;

import com.example.shiro.bean.Permissions;
import com.example.shiro.bean.Roles;
import com.example.shiro.bean.User;
import com.example.shiro.service.PermissionService;
import com.example.shiro.service.RolesService;
import com.example.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class ShiroConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ShiroFilterFactoryBean.class);

    @Autowired
    UserService userService;
    @Autowired
    RolesService rolesService;
    @Autowired
    PermissionService permissionService;

//    @Value("${spring.redis.port}")
//    private String port;
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.database}")
//    private String database;
//    @Value("${spring.redis.timeout}")
//    private String timeout;


    /**
     * shiro过滤器工厂
     * @param securityManager  安全管理器
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        log.info("【进入shiroFilterFactoryBean()】");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>(); //过滤链是有顺序的，所以用LinkedHashMap，因为HashMap是无序的
        filterChainDefinitionMap.put("/logout","logout");      //退出过滤器，shiro已经写好了
        filterChainDefinitionMap.put("/favicon.ico","anon");   //图片资源所有用户都可以访问
        //使用注解方式或者这种，两种方式都可以配置路径权限
//        filterChainDefinitionMap.put("/manager/**","authc,perms[manager:test]");
        filterChainDefinitionMap.put("/**","anon");            //所有用户都可以看到，这个一定要放在最后
        shiroFilterFactoryBean.setLoginUrl("/login");          //登录的页面，不设置shiro会去[web]目录下找[login.jsp]
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");     //没有权限则跳转到[403]页面
        shiroFilterFactoryBean.setSuccessUrl("/main");         //认证成功转到[main]页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);//设置过滤链
        return shiroFilterFactoryBean;
    }


    /**
     * 配置SecurityManager
     * 安全管理器，用来管理所有subject
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        log.info("【进入securityManager()】");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    /**
     * Realm类
     * @return
     */
    @Bean
    public MyRealm myRealm(){
        log.info("【进入myRealm()】");
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher()); //设置认证匹配器，使用加密
        return myRealm;
    }

    /**
     * 使用哈希算法加密密码
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        log.info("【进入hashedCredentialsMatcher()】");
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5"); //散列算法使用md5加密方式
        hashedCredentialsMatcher.setHashIterations(2); //散列两次（使用两次md5加密）
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);//使用Hex编码，默认是false编码
        return hashedCredentialsMatcher;
    }



    /**
     * 开启shiro注解，与【AuthorizationAttributeSourceAdvisor】一起使用
     * 开启后才可以使用@RequiresPermissions()、@RequiresRoles()、@RequiresUser()等注解
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    /**
     * 开启注解支持
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        log.info("【进入authorizationAttributeSourceAdvisor()】");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 自定义realm类，可以有多个realm
     */
    class MyRealm extends AuthorizingRealm {

        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
            log.info("----------------------这里是权限管理----------------------");
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();           //权限管理信息
            User principal = (User) principalCollection.getPrimaryPrincipal();      //获取当前用户对象
            Roles role = rolesService.selectByUserName(principal.getUserName());    //根据当前用户的用户名称查找有什么角色
            System.out.println("【role】======》"+role);
            List<Permissions> perms = permissionService.selectByRolesId(role.getId());//根据角色id查找有什么权限
            log.info("【perms】======》",perms);
            Set<String> perm = new HashSet<>();
            for(Permissions permissions :perms){
                log.info("【permissions】======》",permissions);
                perm.add(permissions.getPerms());
            }
            log.info("【perm】======>",perm);
            info.setStringPermissions(perm);   //添加权限
            info.addRole(role.getRoleName());  //添加角色
            return info;
        }

        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            log.info("----------------------这里是用户验证----------------------");
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken; //用户、密码令牌
            String username = token.getUsername();//获取当前用户，返回的是String，getPrincipal()返回的是Object
            String pwd = new String(token.getPassword()); //获取当前用户的密码,返回的是String，getCredentials()返回的是Object
            log.info("【username】=====>",username);
            log.info("【password】=====>",pwd);
            System.out.println("【token.getUsername()】=====》"+token.getCredentials());
            User user = userService.selectByUserName(username);

            if (user == null){
                log.info("*********用户[{}]不存在*********",username);
                throw new UnknownAccountException("用户不存在");
            }

            SimpleAuthenticationInfo authenticationInfo =
                    new SimpleAuthenticationInfo(user,
                            user.getPassword(),
                            ByteSource.Util.bytes(user.getSalt()),  //使用盐值加密
                            getName());

            return authenticationInfo;
        }
    }



    /**
     * @return
     */
//    @Bean
//    public RedisCacheManager redisCacheManager(){
//        log.info("【进入redisCacheManager()】");
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManage());
//        return redisCacheManager;
//    }
    /**
     * @return
     */
//    public RedisManager redisManage(){
//        log.info("【进入redisManage()】");
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(Integer.parseInt(port));
//        redisManager.setDatabase(Integer.parseInt(database));
//        redisManager.setTimeout(Integer.parseInt(timeout));
//        return redisManager;
//    }
    /**
     * @return
     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO(){
//        log.info("【进入redisSessionDAO()】");
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManage());
//        redisSessionDAO.setExpire(18000);//180秒后删除缓存
//        return redisSessionDAO;
//    }
    /**
     * @return
     */
//    @Bean
//    public DefaultWebSessionManager defaultWebSessionManager(){
//        log.info("【进入defaultWebSessionManager()】");
//        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
//        defaultWebSessionManager.setSessionDAO(redisSessionDAO());
//        return defaultWebSessionManager;
//    }
}
