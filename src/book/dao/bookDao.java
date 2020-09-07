package book.dao;

import book.domain.book;
import category.domain.category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 14:24
 * @email: wangyuhang_mocas@163.com
 */
public class bookDao {
    private QueryRunner qr=new TxQueryRunner();


    /*查询所有图书*/
    public List<book> findAll()
    {
        try {
            String sql="select * from book";
            return qr.query(sql,new BeanListHandler<book>(book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*按分类查询*/
    public List<book> findByCategory(String cid) {
        try{
            String sql="select * from book where cid=?";
            return qr.query(sql,new BeanListHandler<book>(book.class),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public book findById(String bid) {
        try{
            /*
             * 我们需要在Book对象中保存Category的信息
             */
            String sql = "select * from book where bid=?";
            Map<String,Object> map = qr.query(sql, new MapHandler(), bid);
            /*
             * 使用一个Map，映射出两个对象，再给这两个对象建立关系！
             */
            category category = CommonUtils.toBean(map, category.class);
            book book = CommonUtils.toBean(map, book.class);
            book.setCategory(category);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCountByCid(String cid) {
        try{
            String sql="select count(*) from book where cid=?";
            Number cnt= (Number) qr.query(sql,new ScalarHandler(),cid);
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(book book) {
        try {
            String sql = "insert into book values(?,?,?,?,?,?)";
            Object[] params = {book.getBid(), book.getBname(), book.getPrice(),
                    book.getAuthor(), book.getImage(), book.getCategory().getCid()};
            qr.update(sql, params);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 删除图书
     * @param bid
     */
    public void delete(String bid) {
        try {
            String sql = "update book set del=true where bid=?";
            qr.update(sql, bid);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public void edit(book book) {
            try {
                String sql = "update book set bname=?,price=?,author=?,image=?,cid=?  where bid=?";
                Object[] params = { book.getBname(), book.getPrice(),
                        book.getAuthor(), book.getImage(), book.getCategory().getCid(),book.getBid()};
                qr.update(sql, params);
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
        }
}
