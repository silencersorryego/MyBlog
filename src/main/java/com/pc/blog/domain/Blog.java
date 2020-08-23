package com.pc.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_blog")
@Document(indexName = "blog")
public class Blog implements Serializable {

    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String firstPicture;

    @Transient
    private String flag;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;
    @Field(type = FieldType.Integer)
    private Integer view;


    @Transient
    private boolean appreciation;

    @Transient
    private boolean shareStatement;

    @Transient
    private boolean commentabled;

    @Transient
    private boolean published;

    @Transient
    private boolean recommended;

    @Transient
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = "uuuu-MM-dd HH:mm:ss")
    private Date updateTime;
    @Field(type = FieldType.Integer)
    private int userId;
    @Field(type = FieldType.Integer)
    private int typeId;

    @Transient
    @TableField(exist = false)
    private String tagIds;

    @Transient
    @TableField(exist = false)
    private User user;

    @Transient
    @TableField(exist = false)
    private List<Tag> tags;

    @Transient
    @TableField(exist = false)
    private Type type;


}
