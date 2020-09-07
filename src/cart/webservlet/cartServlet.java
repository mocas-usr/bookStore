package cart.webservlet;

import book.domain.book;
import book.service.bookService;
import cart.domain.cart;
import cart.domain.cartItem;
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
 * @Date: 2020/5/27 16:37
 * @email: wangyuhang_mocas@163.com
 */
@WebServlet(name = "cartServlet",urlPatterns = {"/cartServlet"})
public class cartServlet extends BaseServlet {


    public String add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*得到购物车*/
        cart cart= (cart) request.getSession().getAttribute("cart");
        /*得到条目
        *   得到数量和图书
        *   得到图书的bid，需要通过bid得到图书    */
        String bid=request.getParameter("bid");
        bookService bo=new bookService();
        book book=bo.load(bid);

        int count=Integer.parseInt(request.getParameter("count"));
        cartItem cartItem=new cartItem();
        /*设置得到的图书情况，购物车情况*/
        cartItem.setBook(book);
        cartItem.setCount(count);

        cart.add(cartItem);//把条目加入购物车

        return "f:/jsps/cart/list.jsp";
    }
    public String clear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*得到购物车*/
        cart cart= (cart) request.getSession().getAttribute("cart");
        cart.clear();
            return  "f:/jsps/cart/list.jsp";
    }

    /*删除购物条目*/
    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*得到购物车*/
        cart cart= (cart) request.getSession().getAttribute("cart");
        String bid=request.getParameter("bid");
        cart.delete(bid);
        return  "f:/jsps/cart/list.jsp";
    }
}
