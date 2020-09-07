package user.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import user.domain.user;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/26 14:42
 * @email: wangyuhang_mocas@163.com
 */
public class userDao {
    private QueryRunner qr=new TxQueryRunner();

    /*修改用户的指定状态*/
    public void  updateState(String  uid,boolean state)
    {
        /**/
        try
        {
            String sql="update tb_user set state=? where uid=?";
            qr.update(sql,state,uid);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*按激活码查询*/
    public user findByCode(String  code)
    {
        /**/
        try
        {
            String sql="select * from tb_user where code=?";
            return qr.query(sql,new BeanHandler<user>(user.class),code);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*按用户名查询*/
    public user findByUsername(String  username)
    {
        /**/
        try
        {
            String sql="select * from tb_user where username=?";
            return qr.query(sql,new BeanHandler<user>(user.class),username);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*按email查询*/
    public user findByEmail(String  email)
    {
        /**/
        try
        {
            String sql="select * from tb_user where email=?";
            return qr.query(sql,new BeanHandler<user>(user.class),email);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*插入user*/
    public void add(user user)
    {
        /**/
        try
        {
            String sql="insert into tb_user values (?,?,?,?,?,?)";
            Object[] params={user.getUid(),user.getUsername(),
                    user.getPassword(),user.getEmail(),user.getCode(),user.isState()};
            qr.update(sql,params);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
