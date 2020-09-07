package cart.domain;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 15:54
 * @email: wangyuhang_mocas@163.com
 */
public class cart {
    private Map<String,cartItem> map=new LinkedHashMap<String,cartItem>();


    public double getTotal()
    {
        /*合计=所有条目的小计之和*/
        BigDecimal total=new BigDecimal("0");
//        double total=0;
        for (cartItem cartItem:map.values())//循环结算条目
        {
            BigDecimal Subtotal=new BigDecimal(""+cartItem.getSubtotal());
            total=total.add(Subtotal);
        }
        return total.doubleValue();
    }
    /*添加条目到车中*/
    public void add(cartItem cartItem)
    {
        if (map.containsKey(cartItem.getBook().getBid()))//判断原来车上是否含有该条目
        {
            cartItem cartItem1=map.get(cartItem.getBook().getBid());//如果存在原条目，返回原条目
            cartItem1.setCount(cartItem.getCount()+cartItem1.getCount());//设置老条目的数量为新旧相加
            map.put(cartItem.getBook().getBid(),cartItem1);
        }
        else
        {
            map.put(cartItem.getBook().getBid(),cartItem);//如果不存在条目，则增加条目
        }

    }
    /*清空所有条目*/
    public void clear()
    {
        map.clear();
    }
    /*删除指定条目*/
    public void  delete(String bid)//bid是书的主键
    {
        map.remove(bid);
    }
    /*获取所有条目*/
    public Collection<cartItem> getcartItem()
    {
        return map.values();
    }

}
