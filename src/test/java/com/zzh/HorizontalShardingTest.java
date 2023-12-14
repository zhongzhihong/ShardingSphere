package com.zzh;

import com.zzh.entity.Dict;
import com.zzh.entity.Order;
import com.zzh.entity.OrderItem;
import com.zzh.mapper.DictMapper;
import com.zzh.mapper.OrderItemMapper;
import com.zzh.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * 水平分片测试
 */
@SpringBootTest
public class HorizontalShardingTest {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private DictMapper dictMapper;

    /**
     * 分库策略（暂时定死插入到t_order0表中）
     * 结果：`user_id`为奇数的数据插入到了`server-order1`数据库下的`t_order0`表
     * 为偶数的数据插入到了`server-order0`数据库下的`t_order0`表
     */
    @Test
    public void insertByDatabaseStrategy() {
        for (long i = 4; i < 8; i++) {
            Order order = new Order();
            order.setOrderNo("ATGUIGU001");
            order.setUserId(i + 1);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
    }

    /**
     * 分表策略
     */
    @Test
    public void insertByTableStrategy() {
        for (long i = 1; i < 5; i++) {
            Order order = new Order();
            order.setOrderNo("ATGUIGU00" + i);
            order.setUserId(1L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
        for (long i = 5; i < 9; i++) {
            Order order = new Order();
            order.setOrderNo("ATGUIGU00" + i);
            order.setUserId(2L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
    }

    /**
     * ShardingSphere 自动根据UNION ALL集成查询了两个数据库下的两张表
     * Actual SQL: server-order0 ::: SELECT  id,order_no,user_id,amount  FROM t_order0 UNION ALL SELECT  id,order_no,user_id,amount  FROM t_order1
     * Actual SQL: server-order1 ::: SELECT  id,order_no,user_id,amount  FROM t_order0 UNION ALL SELECT  id,order_no,user_id,amount  FROM t_order1
     */
    @Test
    public void selectAll() {
        List<Order> orders = orderMapper.selectList(null);
        orders.forEach(System.out::println);
    }

    @Test
    public void insertToOrderItemByTableStrategy() {
        for (long i = 1; i < 5; i++) {
            Order order = new Order();
            order.setOrderNo("ATGUIGU00" + i);
            order.setUserId(1L);
            orderMapper.insert(order);
            for (int j = 0; j < 3; j++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo("ATGUIGU00" + i);
                orderItem.setUserId(1L);
                orderItem.setPrice(new BigDecimal(10));
                orderItem.setCount(10);
                orderItemMapper.insert(orderItem);
            }
        }
        for (long i = 5; i < 9; i++) {
            Order order = new Order();
            order.setOrderNo("ATGUIGU00" + i);
            order.setUserId(2L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
            for (int j = 0; j < 3; j++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo("ATGUIGU00" + i);
                orderItem.setUserId(2L);
                orderItem.setPrice(new BigDecimal(20));
                orderItem.setCount(20);
                orderItemMapper.insert(orderItem);
            }
        }
    }

    @Test
    public void selectOrderVo() {
        orderMapper.selectOrderVo().forEach(System.out::println);
    }

    @Test
    public void insertBroadCast() {
        Dict dict = new Dict();
        dict.setDictType("Type1");
        dictMapper.insert(dict);
    }
}
