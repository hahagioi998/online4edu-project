package com.online4edu.dependencies.mybatis.generator.plugin;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * Mapper 注释生成
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/12 10:21
 */
public class MapperCommentGenerator implements CommentGenerator {

    /**
     * 开始的分隔符,例如mysql为`,sqlServer为[
     */
    private String beginningDelimiter = "";

    /**
     * 结束的分隔符,例如mysql为`,sqlServer为]
     */
    private String endingDelimiter = "";

    public MapperCommentGenerator() {
        super();
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }

    /**
     * xml中的注释
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));
        String content = "  WARNING - " + MergeConstants.NEW_ELEMENT_TAG;
        xmlElement.addElement(new TextElement(content));
        xmlElement.addElement(new TextElement("-->"));
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        String beginningDelimiter = properties.getProperty("beginningDelimiter");
        if (StringUtility.stringHasValue(beginningDelimiter)) {
            this.beginningDelimiter = beginningDelimiter;
        }
        String endingDelimiter = properties.getProperty("endingDelimiter");
        if (StringUtility.stringHasValue(endingDelimiter)) {
            this.endingDelimiter = endingDelimiter;
        }
    }

    public String getDelimiterName(String name) {
        return beginningDelimiter + name + endingDelimiter;
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    }

    /**
     * 给字段添加数据库注释、注解
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        // 添加 @Transient 注解
        if (field.isTransient()) {
            field.addAnnotation("@Transient");
        }

        // 添加 @ApiModelProperty 注解
        field.addAnnotation("@ApiModelProperty(\"" + introspectedColumn.getRemarks() + "\")");

        // 主键
        if (introspectedColumn.isIdentity()) {
            field.addAnnotation("@TableId(type = IdType.AUTO)");
        } /*else if (introspectedColumn.isSequenceColumn()) {
            // 在 Oracle 中,如果需要是 SEQ_TABLENAME, 那么可以配置为 select SEQ_{1} from dual
        }*/
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        // 字段需要增加注释? 使用 Swagger 直接使用 @ApiModelProperty 注解即可
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 在类上导入包
        topLevelClass.addImportedType("com.baomidou.mybatisplus.annotation.*");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        topLevelClass.addImportedType("lombok.*");

        // 增加注解
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");
        topLevelClass.addAnnotation("@ToString");
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = false)");

        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        //如果包含空格,或者需要分隔符,需要完善
        if (StringUtility.stringContainsSpace(tableName)) {
            tableName = beginningDelimiter + tableName + endingDelimiter;
        }

        topLevelClass.addAnnotation("@TableName(\"" + tableName.toLowerCase() + "\")");

        String remarks = introspectedTable.getRemarks();
        if (StringUtils.isNotBlank(remarks)) {
            topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
            topLevelClass.addAnnotation("@ApiModel(\"" + remarks.trim() + "\")");
        }

        // 实体类继承 Convert
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.online4edu.dependencies.utils.converter.Convert"));
        topLevelClass.setSuperClass(new FullyQualifiedJavaType("Convert"));
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    /**
     * getter方法注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // 使用 lombok 后直接使用 @Getter 注解即可
    }

    /**
     * setter方法注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // 使用 lombok 后直接使用 @Setter 注解即可
    }


    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }
}
