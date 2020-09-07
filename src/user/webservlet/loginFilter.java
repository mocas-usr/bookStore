package user.webservlet;

import user.domain.user;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/29 16:43
 * @email: wangyuhang_mocas@163.com
 */
@WebFilter(filterName = "loginFilter",urlPatterns = {"/jsps/cart/*","/jsps/order/*","/cartServlet","/orderServlet"})
public class loginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /*从session中获取用户信息
        * 判断用户信息，放行
        * 否则保存错误信息*/
        HttpServletRequest httprequest= (HttpServletRequest) req;
        user user= (user) httprequest.getSession().getAttribute("session_user");
        if (user!=null)
        {
            chain.doFilter(req, resp);
        }
        else
        {
            httprequest.setAttribute("msg","您还没有登录");
            httprequest.getRequestDispatcher("/jsps/user/login.jsp").forward(req,resp);


        }


        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
