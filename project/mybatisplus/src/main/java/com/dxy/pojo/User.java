package com.dxy.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString  // 注解在类上, 为类提供 toString() 方法
@EqualsAndHashCode //注解在类上, 为类提供 equals() 和 hashCode() 方法

public class User {

    @TableId(type = IdType.AUTO )
    private Long id;
    private  String name;
    private Integer age;
    private  String email;

    @Version //乐观锁
    private String version;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private  Date updateTime;

    @TableLogic  //逻辑删除
    private  Integer deleted;
}


