package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_dict")
public class Dict {
    // 可以使用MyBatis-Plus的雪花算法
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String dictType;
}
