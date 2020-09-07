package category.dao;

import category.domain.category;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 10:31
 * @email: wangyuhang_mocas@163.com
 */
public class categoryDao {
     private QueryRunner qr= new  TxQueryRunner();


     /*查询所有分类*/
     public List<category> findAll() {
          try
          {
               String sql="select * from category";
               return qr.query(sql,new BeanListHandler<category>(category.class));
          }
          catch (SQLException e)
          {
               throw new RuntimeException(e);

          }

     }

     /*添加分类*/
    public void add(category category) {
         try
         {
              String sql="insert into category values(?,?)";
              qr.update(sql,category.getCid(),category.getCname());
         }
         catch (SQLException e)
         {
              throw new RuntimeException(e);

         }
    }

     public void delete(String cid) {
          try
          {
               String sql="delete from category where cid=?";
               qr.update(sql,cid);

          }
          catch (SQLException e)
          {
               throw new RuntimeException(e);

          }


     }

     public category load(String cid) {
          try
          {
               String sql="select * from category where cid=?";
               return qr.query(sql,new BeanHandler<category>(category.class),cid);


          }
          catch (SQLException e)
          {
               throw new RuntimeException(e);

          }
     }

     /*修改分类*/
     public void edit(category category) {
          try
          {
               String sql="update category set cname=? where cid=?";
               qr.update(sql,category.getCname(),category.getCid());
          }
          catch (SQLException e)
          {
               throw new RuntimeException(e);

          }
     }
}
