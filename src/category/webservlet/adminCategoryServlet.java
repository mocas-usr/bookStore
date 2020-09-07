package category.webservlet;

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
 * @Date: 2020/5/28 16:21
 * @email: wangyuhang_mocas@163.com
 */
@WebServlet(name = "adminCategoryServlet",urlPatterns = {"/adminCategoryServlet"})
public class adminCategoryServlet extends BaseServlet {

    private categoryService categoryService=new categoryService();


    public String edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        /*封装表单数据
        * 调用service
        * 调用findall*/

        category category= CommonUtils.toBean(request.getParameterMap(), category.class);
        categoryService.edit(category);
        return findAll(request,response);
    }
    public String editPre(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String cid=request.getParameter("cid");
        request.setAttribute("category",categoryService.load(cid));
        return "/adminjsps/admin/category/mod.jsp";

    }

    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        /*调用service方法，得到所有分类*/
        request.setAttribute("categoryList",categoryService.findAll());

        return "f:/adminjsps/admin/category/list.jsp";

    }

    public String add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        /*封装表单数据
        * 补全cid
        * 调用service
        * 调用findall*/
        category category= CommonUtils.toBean(request.getParameterMap(), category.class);
        category.setCid(CommonUtils.uuid());

        categoryService.add(category);
        return findAll(request,response);



    }

    /*删除分类*/
    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        /*获取参数cid
        * 调用service
        *   如果异常，保存异常信息
        * 调用findall*/
        String cid=request.getParameter("cid");
        try
        {
            categoryService.delete(cid);
            return findAll(request,response);

        }
        catch (Exception e)
        {
            request.setAttribute("msg",e.getMessage());
            return "f:/adminjsps/msg.jsp";
        }


    }
}
