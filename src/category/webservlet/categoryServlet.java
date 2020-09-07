package category.webservlet;

import category.service.categoryService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 10:32
 * @email: wangyuhang_mocas@163.com
 */
@WebServlet(name = "categoryServlet",urlPatterns = {"/categoryServlet"})
public class categoryServlet extends BaseServlet {
    private categoryService categoryService=new categoryService();
    /**/
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("hello");
        request.setAttribute("categoryList",categoryService.findAll());
        return "f:/jsps/left.jsp";


    }
}
