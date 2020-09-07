package user.service;

import user.dao.userDao;
import user.domain.user;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/26 14:47
 * @email: wangyuhang_mocas@163.com
 */
public class userService {
    private userDao userDao=new userDao();
    public void  regist(user form) throws userException {
        /*查询操作*/
        /*校验用户名*/
        user user=userDao.findByUsername(form.getUsername());
        if (user!=null)
        {
            throw new userException("该用户名已经被注册");

        }
        /*校验用户名*/
        user=userDao.findByUsername(form.getEmail());
        if (user!=null)
        {
            throw new userException("该email已经被注册");

        }

        /*插入用户到数据库中*/
        userDao.add(form);

    }

    /*service业务层，操作user的基本语法，完成查询操作*/
    public void active(String code) throws userException {
        /*利用激活码查询*/
        user user=userDao.findByCode(code);
        if (user==null)
        {
            throw new RuntimeException("激活码错误");

        }
        /*检测用户是否为激活状态*/
        if (user.isState())
        {
            throw new userException("您已经激活了，不需要再激活了");

        }
        /*  修改用户状态*/
        userDao.updateState(user.getUid(),true);

    }


    public user login(user form) throws userException {
        /*1. 使用username查询得到user
            2.如果user为null，抛出异常
            3.比较form和user的密码若不同，抛出异常
            4.查看用户的状态，若为false（尚未激活）
            5.返回user

        *   */
        /*通过username查询user*/
        user user=userDao.findByUsername(form.getUsername());
        if (user==null)throw new userException("用户名不存在");
        if (!user.getPassword().equals(form.getPassword()))
        {
            throw new userException("密码错误");

        }
        if (!user.isState())
        {
            throw new userException("尚未激活");
        }
        return user;


    }

}
