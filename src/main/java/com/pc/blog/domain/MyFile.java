package com.pc.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_file")
public class MyFile {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String suffix;
    private String size;
    private String type;
    private Date uploadTime;
}
