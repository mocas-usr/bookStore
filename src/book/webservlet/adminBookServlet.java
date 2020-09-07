package book.webservlet;

import book.domain.book;
import book.service.bookService;
import category.domain.category;
import category.service.categoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/29 11:17
 * @email: wangyuhang_mocas@163.com
 */
@WebServlet(name = "adminBookServlet",urlPatterns = {"/adminBookServlet"})
public class adminBookServlet extends BaseServlet {
    private bookService bookService=new bookService();
    private categoryService categoryService=new categoryService();

    public String edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        book book= CommonUtils.toBean(request.getParameterMap(),book.class);
        category category=CommonUtils.toBean(request.getParameterMap(), category.class);
        book.setCategory(category);
        bookService.edit(book);
        return findAll(request,response);

    }


    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
       try
       {
           String bid=request.getParameter("bid");
           bookService.delete(bid);
           return findAll(request,response);
       }
       catch (Exception e)
       {
           request.setAttribute("msg","这个可不能随便删，删了就没了");
           return "f:/adminjsps/msg.jsp";
       }
    }

    /*查询所有分类，转发到jsp*/
    public String addPre(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        request.setAttribute("categoryList",categoryService.findAll());
        return  "f:/adminjsps/admin/book/add.jsp";

    }
    /*管理员查看所有图书*/
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        request.setAttribute("bookList",bookService.findAll());
        return "f:/adminjsps/admin/book/list.jsp";
    }

    public String load(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        request.setAttribute("book",bookService.load(request.getParameter("bid")));
        request.setAttribute("categoryList",categoryService.findAll());
        return "f:/adminjsps/admin/book/desc.jsp";
    }

}
