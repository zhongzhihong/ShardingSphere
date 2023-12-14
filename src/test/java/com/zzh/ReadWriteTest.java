package com.zzh;

import com.zzh.entity.User;
import com.zzh.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class ReadWriteTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * 测试
     */
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("WangWu");
        userMapper.insert(user);
    }

    /**
     * 事务测试
     * 未开启事务注解之前：插入数据的SQL打印输出插入的是主库的数据源、查询数据的SQL打印输出查询的是从库slave1的数据源
     * 开启事务注解之后：插入数据、查询数据的SQL打印输出插入、查询的均是主库的数据源，但是由于是test测试，所以默认回滚了
     */
    @Test
    @Transactional
    public void testTrans() {
        User user = new User();
        user.setUsername("ZhaoLiu");
        userMapper.insert(user);
        userMapper.selectList(null);
    }

    /**
     * 负载均衡测试
     */
    @Test
    public void testLoadBalance() {
        List<User> users1 = userMapper.selectList(null);
        List<User> users2 = userMapper.selectList(null);
        List<User> users3 = userMapper.selectList(null);
        List<User> users4 = userMapper.selectList(null);
    }
}
