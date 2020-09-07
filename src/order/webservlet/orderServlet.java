package order.webservlet;

import cart.domain.cart;
import cart.domain.cartItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import order.domain.order;
import order.domain.orderItem;
import order.service.orderException;
import order.service.orderService;
import user.domain.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 19:34
 * @email: wangyuhang_mocas@163.com
 */
@WebServlet(name = "orderServlet",urlPatterns = {"/orderServlet"})
public class orderServlet extends BaseServlet {
   private orderService  orderService=new orderService();


   /*支付去银行  */
   public String zhiFu(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException, SQLException {
      String oid=request.getParameter("oid");
      orderService.zhiFu(oid);
      request.setAttribute("msg", "支付成功！等待卖家发货！你慢慢等~");
      return "f:/jsps/msg.jsp";
   }

   public String load(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      /*
       * 1. 得到oid参数 2. 使用oid调用service方法得到Order 3.
       * 保存到request域，转发到/jsps/order/desc.jsp
       */
      request.setAttribute("order",
              orderService.load(request.getParameter("oid")));
      return "f:/jsps/order/desc.jsp";
   }
   /*确认收货   */
   public String confirm(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException, SQLException {
      /*获取oid参数*/
      String oid=request.getParameter("oid");
      try
      {
         orderService.confirm(oid);
         request.setAttribute("msg","恭喜您，交易成功！");
      } catch (orderException e) {
         request.setAttribute("msg",e.getMessage());

      }
      return "f:/jsps/msg.jsp";

   }

   public String myOrders(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException, SQLException {
      user user= (user) request.getSession().getAttribute("session_user");
      List<order> orderList=orderService.myOrders(user.getUid());
      request.setAttribute("orderList",orderList);
      return "f:/jsps/order/list.jsp";


   }
   /*添加订单
   * 把session中的车用来生成order对象*/
   public String add(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException, SQLException {

      /*从session中获取cart*/
      cart  cart= (cart) request.getSession().getAttribute("cart");
      /*把cart转成order对象*/
      order order=new order();
      order.setOid(CommonUtils.uuid());
      order.setOrderTime(new Date());
      order.setState(1);//状态为1，表示未付款
      user user= (user) request.getSession().getAttribute("session_user");
      order.setOwer(user);
      order.setTotal(cart.getTotal());//设置订单合计，从购物车中获取
      /*创建订单条目*/
      List<orderItem> orderItemList=new ArrayList<orderItem>();
// 循环遍历Cart中的所有CartItem，使用每一个CartItem对象创建OrderItem对象，并添加到集合中
      for(cartItem cartItem : cart.getcartItem()) {
         orderItem oi = new orderItem();//创建订单条目

         oi.setIid(CommonUtils.uuid());//设置条目的id
         oi.setCount(cartItem.getCount());//设置条目的数量
         oi.setBook(cartItem.getBook());//设置条目的图书
         oi.setSubtotal(cartItem.getSubtotal());//设置条目的小计
         oi.setOrder(order);//设置所属订单

         orderItemList.add(oi);//把订单条目添加到集合中
      }
      /*把所有的订单条目添加到订单中*/
      order.setOrderItemList(orderItemList);

      /*调用orderservice添加对象*/
      //清空购物车
      cart.clear();
      orderService.addOrder(order);

/*保存order到request域中，转发到jsp*/
      request.setAttribute("order",order);
      return "f:/jsps/order/desc.jsp";

   }
}
