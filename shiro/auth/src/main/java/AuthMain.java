import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 可可可可可是
 */

public class AuthMain {

    private static final String USR = "root";
    private static final String PWD= "000000";

    private static Logger log = LoggerFactory.getLogger(AuthMain.class);

    public AuthMain(){

        //1.加载配置文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        //2.实例化工厂
        SecurityManager securityManager = factory.getInstance();

        //3.为SecurityUtils设定安全管理器
        SecurityUtils.setSecurityManager(securityManager);

        //4.获取当前用户对象
        Subject currentUser = SecurityUtils.getSubject();

        //5.如果未认证则去认证
        if(!currentUser.isAuthenticated()){

            log.info("身份验证！");
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(USR,PWD);
            try{
                currentUser.login(usernamePasswordToken);
            }catch (UnknownAccountException e){
                //捕捉到未知用户异常
                log.info(USR+"不存在！");
            }catch (AuthenticationException e){
                //捕捉到验证错误异常
                log.info(PWD+"是错误的！");
            }finally {
                //再度取得当前
                currentUser = SecurityUtils.getSubject();
                //判断是否通过验证
                if(currentUser.isAuthenticated())
                    log.info(USR+"身份验证成功！");
            }
        }

        //6.授权信息查询
        if(currentUser.hasRole("admin")&&currentUser.isPermitted("*"))
            log.info(USR+"是管理员！");

    }

    public static void main(String[] args){

        new AuthMain();
    }
}
