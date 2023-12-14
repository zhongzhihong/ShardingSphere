package com.zzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.entity.Order;
import com.zzh.entity.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select({"select o.order_no, sum(i.price * i.count) as money\n" +
            "from t_order o join t_order_item i on o.user_id = i.user_id\n" +
            "group by o.order_no"})
    List<OrderVo> selectOrderVo();
}
