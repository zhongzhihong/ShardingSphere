package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_order")
public class Order {
    // 当配置了ShardingSphere的分布式序列时，自动使用ShardingSphere的分布式序列
    // 当没有配置ShardingSphere的分布式序列时，自动依赖数据库的主键自增策略
    @TableId(type = IdType.AUTO)
    // @TableId(type = IdType.ASSIGN_ID) // 依赖MyBatis-Plus的分布式ID，优先级高于ShardingSphere的分布式序列配置
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal amount;
}
