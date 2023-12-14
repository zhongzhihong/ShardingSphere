package com.zzh;

import com.zzh.entity.Order;
import com.zzh.entity.User;
import com.zzh.mapper.OrderMapper;
import com.zzh.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * 垂直分片测试
 */
@SpringBootTest
public class VerticalShardingTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("LuoQi");
        userMapper.insert(user);
        Order order = new Order();
        order.setOrderNo("ATGUIGU001");
        order.setUserId(user.getId());
        order.setAmount(new BigDecimal(100));
        orderMapper.insert(order);
    }
}
