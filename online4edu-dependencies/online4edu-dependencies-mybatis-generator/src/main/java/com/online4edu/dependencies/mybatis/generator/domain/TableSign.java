package com.online4edu.dependencies.mybatis.generator.domain;

import com.google.common.base.CaseFormat;
import com.online4edu.dependencies.mybatis.generator.util.AutoUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * table 表信息
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/30 20:59
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TableSign {

    @ApiModelProperty("数据库表名")
    private String tableName;

    @ApiModelProperty("映射实体类名称")
    private String domainName;

    @ApiModelProperty("数据库主键字段")
    private String pkColumn;

    @ApiModelProperty("数据库主键映射Java类型")
    private String pkJavaType;

    @ApiModelProperty("数据库表说明信息")
    private String description;

    /*===========================扩展信息, 无需手动设置===========================*/

    @ApiModelProperty("作者信息")
    private String author;

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("实体类小驼峰")
    private String domainNameUpperCamel;

    @ApiModelProperty("实体类大驼峰")
    private String domainNameLowerCamel;

    @ApiModelProperty("基础包")
    private String basePackage;

    public String getDomainName() {
        if (StringUtils.isEmpty(domainName)) {
            String stripPrefix = ProjectProperties.getInstance().getStripPrefix();
            if (StringUtils.isNotBlank(stripPrefix) && tableName.startsWith(stripPrefix)) {
                domainName = tableName.substring(stripPrefix.length() - 1);
                domainName = AutoUtil.tableNameConvertUpperCamel(domainName);
            } else {
                domainName = AutoUtil.tableNameConvertUpperCamel(tableName);
            }
        }
        return domainName;
    }

    public String getAuthor() {
        if (StringUtils.isBlank(author)) {
            author = JdbcProperties.getInstance().getAuthor();
        }
        return author;
    }

    public String getDate() {
        if (StringUtils.isBlank(date)) {
            date = (new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(new Date());
        }
        return date;
    }

    public String getDomainNameUpperCamel() {
        if (StringUtils.isBlank(domainNameUpperCamel)) {
            domainNameUpperCamel = domainName;
        }
        return domainNameUpperCamel;
    }

    public String getDomainNameLowerCamel() {
        if (StringUtils.isBlank(domainNameLowerCamel)) {
            domainNameLowerCamel = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, domainNameUpperCamel);
        }
        return domainNameLowerCamel;
    }

    public String getBasePackage() {
        if (StringUtils.isBlank(basePackage)) {
            basePackage = ProjectProperties.getInstance().getProjectPackage();
        }
        return basePackage;
    }
}
