package category.service;

import book.dao.bookDao;
import category.dao.categoryDao;
import category.domain.category;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 10:32
 * @email: wangyuhang_mocas@163.com
 */
public class categoryService {
    private categoryDao categoryDao=new categoryDao();
    private bookDao bookDao=new bookDao();

    public List<category> findAll() {
        return categoryDao.findAll();

    }

    public void add(category category) {
        categoryDao.add(category);

    }

    /*删除分类*/
    public void delete(String cid) {
        int count=bookDao.getCountByCid(cid);
        if (count>0) throw new RuntimeException("该图书下还有图书，不能删除");
        categoryDao.delete(cid);
    }

    public category load(String cid) {
        return categoryDao.load(cid);
    }

    public void edit(category category) {
        categoryDao.edit(category);
    }
}
