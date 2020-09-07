package cart.domain;

import book.domain.book;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 15:54
 * @email: wangyuhang_mocas@163.com
 */
/*购物车条目*/
public class cartItem {

    private book book;
    private int count;


    /*计算金额*/
    public double getSubtotal()//小计方法，没有对应方法
    {
        return book.getPrice()*count;

    }

    public book getBook() {
        return book;
    }

    public void setBook(book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
