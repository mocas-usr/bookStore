package book.service;

import book.dao.bookDao;
import book.domain.book;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 14:25
 * @email: wangyuhang_mocas@163.com
 */
public class bookService {
    bookDao bookDao=new bookDao();


    public void add(book book) {
        bookDao.add(book);
    }
    /*按分类查询*/
    public List<book>findByCategory(String cid)
    {
        return  bookDao.findByCategory(cid);
    }

    public List<book> findAll()
    {
        return bookDao.findAll();
    }

    public book load(String bid) {
        return bookDao.findById(bid);
    }

    public void delete(String bid)
    {
        bookDao.delete(bid);
    }

    public void edit(book book) {
        bookDao.edit(book);
    }
}
