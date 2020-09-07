package user.webservlet;

import cart.domain.cart;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import org.dom4j.io.SAXReader;
import user.domain.user;
import user.service.userException;
import user.service.userService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/26 14:49
 * @email: wangyuhang_mocas@163.com
 */
@WebServlet(name = "userServlet",urlPatterns = {"/userServlet"})
public class userServlet extends BaseServlet {
   private userService userService=new userService();

      /*退出，利用session*/
      public String quit(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
         request.getSession().invalidate();//销毁session，即结束了session_user
         return "r:/index.jsp";


      }

   public String login(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      /*
      1.封装表单数据到form中
      2.调用service完成激活
         保存错误信息，form到request，转发到login.jsp
       4.保存用户信息到session中，转发到index.jsp
      */

      user form=CommonUtils.toBean(request.getParameterMap(),user.class);
      try
      {
         user user=userService.login(form);
         request.getSession().setAttribute("session_user",user);//登录成功

         /*像session中保存一个购物车，即cart
         * */

         request.getSession().setAttribute("cart",new cart());
         return "r:/index.jsp";//登录成功跳转主页

      } catch (userException e) {//错误信息记录。原账号密码在页面显示
         request.setAttribute("msg",e.getMessage());
         request.setAttribute("form",form);
         return "f:/jsps/user/login.jsp";//登录失败，跳转登录界面
      }


   }
   /*激活功能*/
   /*servlet处理的是激活信息显示，错误信息显示，并且操作激活业务*/
   public String active(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      System.out.println("您激活了");
      String  code= (String) request.getAttribute("code");
      try {
         userService.active(code);
         request.setAttribute("msg","恭喜您激活了，请马上登陆");
      } catch (userException e) {
         request.setAttribute("msg",e.getMessage());
      }
      return "f:/jsps/msg.jsp";

   }

   public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//      super.service(request, response);
      /*封装表单到form
      * 补全uid
      * 输入校验
      * 调用service
      * 发邮件
      * */

      /*封装表单数据*/
      user form= CommonUtils.toBean(request.getParameterMap(),user.class);
      /*补全*/
      form.setUid(CommonUtils.uuid());
      form.setCode(CommonUtils.uuid()+CommonUtils.uuid());

      /*输入校验用户名*/
      Map<String,String> errors=new HashMap<String,String>();
      String username=form.getUsername();
      if (username==null || username.trim().isEmpty())
      {
         errors.put("username","用户名不能为空");

      }
      else if(username.length()<3 ||username.length()>10)
      {
         errors.put("username","用户名长度要在3-10之间");

      }

      /*校验密码*/
      String password=form.getPassword();
      if (password==null || password.trim().isEmpty())
      {
         errors.put("password","密码不能为空");

      }
      else if(password.length()<3 ||password.length()>10)
      {
         errors.put("password","密码长度要在3-10之间");

      }


      /*校验密码*/
      String Email=form.getEmail();
      if (Email==null || Email.trim().isEmpty())
      {
         errors.put("email","Email不能为空");

      }
      else if(!Email.matches("\\w+@\\w+\\.\\w+"))
      {
         errors.put("email","Email格式错误");

      }

      /*判断是否存在错误信息*/
      if (errors.size()>0)
      {
         request.setAttribute("errors",errors);
         request.setAttribute("form",form);
         return "f:/jsps/user/regist.jsp";
      }
      try
      {
         userService.regist(form);
         /*执行到这里，说明执行成功，没有抛出异常
         * 保存成功信息，发邮件*/




      } catch (userException e) {
         request.setAttribute("msg",e.getMessage() );
         request.setAttribute("form",form);
         return "f:/jsps/user/regist.jsp";
      }

      /*获取配置文件内容*/
      Properties props=new Properties();
      props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
      /*邮箱服务*/
      String  host=props.getProperty("host");
      String uname=props.getProperty("uname");
      String pwd=props.getProperty("pwd");
      String from=props.getProperty("from");
      String to=form.getEmail();
      String subject=props.getProperty("subject");
      String content=props.getProperty("content");
      content= MessageFormat.format(content,form.getCode());

      /*发邮件*/
      Session session= MailUtils.createSession(host,uname,pwd);
      Mail mail=new Mail(from,to,subject,content);
      try
      {
         MailUtils.send(session,mail);
      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
      request.setAttribute("code",form.getCode());
      active(request,response);
      request.setAttribute("msg","恭喜注册成功，请马上到邮箱激活");
      return "f:/jsps/msg.jsp";

   }
}
