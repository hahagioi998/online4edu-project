package com.online4edu.app.gateway.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.online4edu.dependencies.utils.converter.Convert;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_administrative_region")
public class SysAdministrativeRegion extends Convert implements Serializable {
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private String id;

    @ApiModelProperty("区域名称")
    private String name;

    @ApiModelProperty("区域代码")
    private Long code;

    @ApiModelProperty("父级行政区域")
    private String parents;

    @ApiModelProperty("国家(cn: 中国, en: 外国)")
    private String country;

    @ApiModelProperty("区域级别")
    private Integer level;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("状态(1:启用, 0禁用)")
    private Integer status;

    @ApiModelProperty("删除状态(0未删除, 1已删除)")
    private Integer deleted;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("描述说明")
    private String description;

    private static final long serialVersionUID = 1L;
}