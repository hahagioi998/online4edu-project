package com.online4edu.dependencies.mybatis.generator.config;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

/**
 * 扩展 JDBC => Java 类型解析
 *
 * <p>
 * 建表时通常使用合适的字符存储长度, 以便于节约数据库存储空间、结果索引存储, 提升检索速度.
 * 通常会使用 tinyint 代替 int 数据类型. 比如字段 <code>is_deleted</code> 通常设置
 * 值为 0/1, 即 false/true. 只是用 1bit 存储大小.
 * 如果使用 int 存储将会造成空间浪费(int 占 4bit), 所以使用 tinyint 更加合适(tinyint 占 1bit).
 *
 * <p>
 * 而 tinyint 有个缺陷, 在进行类型映射时会出现如下问题:
 * <pre>
 *     tinyint(1) => Boolean
 *     tinyint(n > 1) => Byte
 * </pre>
 *
 * <p>
 * 在[官方文档](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html)中有
 * 个 jdbc 连接配置 <code>tinyInt1isBit</code>:
 * <pre>
 *     java.lang.Boolean if the configuration property tinyInt1isBit is set to true (the default)
 *     and the storage size is 1, or java.lang.Integer if not.
 * </pre>
 *
 * <p>
 * 译过来就是:
 *
 * <pre>
 *     当字符类型为 tinyint 且长度为 1 时, 会被转换成布尔类型.
 *     而虽然文档上说 tinyInt1isBit 取false 时,转Integer.实际上转过来还是 Byte.
 * </pre>
 *
 *
 * <p>
 * 为此, 我们大多需要将 tinyint(n > 1) 映射为java 的 Integer 类型. 所以我们需要扩展
 * {@link JavaTypeResolverDefaultImpl} 进行自定义类型映射, 如将 <code>tinyint(n > 1)</code>
 * 映射为 Java 的 Integer:
 * <pre>
 *     typeMap.put(Types.TINYINT,
 *         new JavaTypeResolverDefaultImpl.JdbcTypeInformation(
 *             "TINYINT", new FullyQualifiedJavaType(Integer.class.getName())
 *         ));
 * </pre>
 *
 * <p>
 * 如下数据表:
 * <pre>
 *     CREATE TABLE `test_tinyint` (
 *     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
 *     `is_deleted` tinyint(1) unsigned DEFAULT NULL,
 *     `age` tinyint(2) unsigned DEFAULT NULL,
 *     PRIMARY KEY (`id`)
 *     );
 * </pre>
 *
 * <p>
 * 默认转换得到的 Java 实体类为:
 * <pre>
 *      public class TestTinyint implements Serializable {
 *
 *          private Long id;
 *
 *          private Boolean isDeleted;
 *
 *          private Byte age; // => tinyint(2) 被映射为 Byte
 *
 *          private static final long serialVersionUID = 1L;
 *      }
 * </pre>
 *
 * <p>
 * 所以, 对于年龄显然不合适. 因此我们需要将其映射为 Integer.
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 14/08/2020 16:13
 */
public class SimpleJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

    public SimpleJavaTypeResolverImpl() {
        super();

        // 将 tinyint(1) 被映射为 Boolean, 将 tinyint(n) n>1 映射为 Integer
        typeMap.put(Types.TINYINT,
                new JavaTypeResolverDefaultImpl.JdbcTypeInformation(
                        "TINYINT", new FullyQualifiedJavaType(Integer.class.getName())
                ));
    }
}
