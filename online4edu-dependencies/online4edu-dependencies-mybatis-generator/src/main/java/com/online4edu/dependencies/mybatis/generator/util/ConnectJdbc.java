package com.online4edu.dependencies.mybatis.generator.util;

import com.online4edu.dependencies.mybatis.generator.domain.Column;
import com.online4edu.dependencies.mybatis.generator.domain.Table;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 新版驱动url默认地址为127.0.0.1:3306,所以访问本机mysql数据库地址可以用 /// 表示;
 * eg:
 * jdbc.url=jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=UTC
 * jdbc.url=jdbc:mysql:///test?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=UTC
 * </p>
 * 另外,新版驱动类位置发生了改变:com.mysql.cj.jdbc.Driver
 * eg:
 * Class.forName("com.mysql.jdbc.Driver");
 * Class.forName("com.mysql.cj.jdbc.Driver");
 * </p>
 * <a href = "https://blog.csdn.net/tb_520/article/details/79676543"></a>
 * <p>
 * java.sql.SQLException: Unknown system variable 'query_cache_size'?
 * <p>
 * You Need
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 01/10/2018 19:08
 */
public class ConnectJdbc {

    private final String ip;
    private final int port;
    private final String user;
    private final String password;
    private final boolean useSSL;
    private static final int DEFAULT_PORT = 3306;
    private static final String DEFAULT_HOST = "localhost";

    private Connection connection = null;
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DEFAULT_CLASS_NAME = "com.mysql.jdbc.Driver";

//    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectJdbc.class);

    static {
        try {
            Class.forName(DEFAULT_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            try {
                Class.forName(CLASS_NAME);
            } catch (ClassNotFoundException e1) {
                System.err.println("===========can not load jdbc driver==============");
                e.printStackTrace();
            }
        }
    }

    public ConnectJdbc(String user, String password) {
        this(DEFAULT_HOST, DEFAULT_PORT, user, password);
    }

    public ConnectJdbc(String ip, String user, String password) {
        this(ip, DEFAULT_PORT, user, password);
    }

    public ConnectJdbc(String ip, int port, String user, String password) {
        this(ip, port, user, password, false);
    }

    public ConnectJdbc(String ip, int port, String user, String password, boolean useSSL) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
        this.useSSL = useSSL;
    }

    /**
     * 初始化数据库连接
     */
    public Connection init() {
        try {
            if (connection != null) {
                return connection;
            }

            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port
                    + "?useUnicode=true&characterEncoding=utf-8&useSSL=" + useSSL, user, password);

        } catch (SQLException e) {
            System.out.println("===========Get jdbc connection failure============");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 查询库
     */
    @SuppressWarnings("unchecked")
    public List<String> showDatabases() {

        if (connection == null) {
            System.err.println("JDBC Connection Is Null, Exit");
            return Collections.EMPTY_LIST;
        }

        List<String> databases = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement("SHOW DATABASES;").executeQuery();
            while (resultSet.next()) {
                databases.add(resultSet.getString("database"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return databases;
    }

    /**
     * 获取指定库数据表
     *
     * @param database 数据库
     * @return 数据库数据表集合
     */
    @SuppressWarnings("unchecked")
    public List<Table> showTables(String database) {

        if (connection == null) {
            System.err.println("JDBC Connection Is Null, Exit");
            return Collections.EMPTY_LIST;
        }

        List<Table> tables = new ArrayList<>();
        try {
            // 不指定库查询所有库所有表
            // 查询数据库元数据信息
            // DatabaseMetaData metaData = connection.getMetaData();

            // 获取数据库 TABLE,这里会查
            // 询所有库的数据不表,返回结果:
            // INDEX 1: database name
            // INDEX 3: table name
			/*resultSet = metaData.getTables(null,null,null, new String[]{"TABLE"});

			while (resultSet.next()) {
				String dbName = resultSet.getString(1);
				String tableName = resultSet.getString(3);

				// 过滤指定库
				if (StringUtils.isNotBlank(database) && !database.equals(dbName)){
					continue;
				}
				tables.add(tableName);
			}*/

            // 指定数据库查询表
			/*PreparedStatement tables1 = connection.prepareStatement("SHOW TABLES from car_owner");
			ResultSet show = tables1.executeQuery();
			while (show.next()){
				System.out.println(show.getString(1));
			}*/

            // 查看数据表注释, 如果不指定 database 将会查询
            // 所有库的 table, 查询语句如下
            // SHOW TABLE STATUS FROM database;

            ResultSet tablesComment = connection.prepareStatement("SHOW TABLE STATUS FROM " + database, new String[]{"name", "comment", "create_time"}).executeQuery();
            while (tablesComment.next()) {
                String tableName = tablesComment.getString("name");
                String tableComment = tablesComment.getString("comment");
                Date createTime = tablesComment.getDate("create_time");
                String createTimeStr = new DateTime(createTime).toString("yyyy-MM-dd HH:mm:ss");

                System.out.println(String.format("%s - %s - %s", tableName, tableComment, createTimeStr));
                System.out.println();
                tables.add(new Table(tableName, tableComment, createTime));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return tables;
    }

    /**
     * 获取表字段信息
     *
     * @param database 数据库
     * @param table    数据表
     * @return 数据表字段集合
     */
    @SuppressWarnings("unchecked")
    public List<Column> showColumns(String database, String table) {

        if (connection == null) {
            System.err.println("JDBC Connection Is Null, Exit");
            return Collections.EMPTY_LIST;
        }

        if (StringUtils.isBlank(database) || StringUtils.isBlank(table)) {
            throw new RuntimeException("JDBC connect Database is EMPTY or Table is EMPTY");
        }

        List<Column> columns = new ArrayList<>();
        try {
            // 查询数据表字段及信息
            ResultSet columnSet = connection.prepareStatement(
                    "SELECT COLUMN_NAME name, COLUMN_TYPE type, COLUMN_KEY `key`, IS_NULLABLE isNullable, COLUMN_COMMENT `comment` "
                            + "FROM information_schema.columns WHERE TABLE_SCHEMA='" + database + "' AND TABLE_NAME = '" + table + "';").executeQuery();

            while (columnSet.next()) {
                String name = columnSet.getString("name");
                String type = columnSet.getString("type");
                String key = columnSet.getString("key");
                String isNullable = columnSet.getString("isNullable");
                String comment = columnSet.getString("comment");
                System.out.println(String.format("%s - %s - %s - %s - %s", name, type, key, isNullable, comment));
                columns.add(new Column(name, type, key, isNullable, comment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return columns;
    }

    /**
     * 关闭数据库连接
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("============close jdbc connection failure==============");
                e.printStackTrace();
            }
        }
    }
}