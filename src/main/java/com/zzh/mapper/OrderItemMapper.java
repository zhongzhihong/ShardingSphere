package com.zzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
