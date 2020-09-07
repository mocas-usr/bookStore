package book.webservlet;

import book.domain.book;
import book.service.bookService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 14:25
 * @email: wangyuhang_mocas@163.com
 */
@WebServlet(name = "bookServlet",urlPatterns = {"/bookServlet"})
public class bookServlet extends BaseServlet {
    private bookService bookService=new bookService();




    public String load(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    /*1.得到参数bid
    * 2.查询得到book
    * 3.保存转发到dec.jsp
    * */

    request.setAttribute("book",bookService.load(request.getParameter("bid")));
    return "f:/jsps/book/desc.jsp";
    }
/*查询所有图书*/
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("bookList",bookService.findAll());
        return "f:/jsps/book/list.jsp";

    }
    /*按分类查询*/
    public String findByCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cid=request.getParameter("cid");
        request.setAttribute("bookList",bookService.findByCategory(cid));
        return "f:/jsps/book/list.jsp";



    }





}
