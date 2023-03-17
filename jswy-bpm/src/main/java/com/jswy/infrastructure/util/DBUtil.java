package com.jswy.infrastructure.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 数据库操作工具类【通过配置文件dbutil.properties配置】
 *
 * @author admin
 * @since 2023
 *
 * @param user
 * @param password
 * @param driver
 * @param url
 */
public class DBUtil {
	private static String packagePath = "src.com.cpms.test.junit";// default current package's path
	private static Connection connection = null;
	private static Properties properties = null;
	private static String dbFilePath = "jdbc.properties";// default properties file's path

	private static String username;
	private static String password;
	private static String url;
	private static String driver;

	static {
		try {
			getDatabaseInfo(dbFilePath);
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void getDatabaseInfo(String path) {
		InputStream in = DBUtil.class.getClassLoader().getResourceAsStream(path);
		Properties p = new Properties();
		try {
			p.load(in);
			username = p.getProperty("jdbc.username");
			password = p.getProperty("jdbc.password");
			url = p.getProperty("jdbc.url");
			driver = p.getProperty("jdbc.driver");
			if ((driver == null) || (url == null) || (username == null) || (password == null)) {
				System.out.println("[DBUtil:ERROR ] " + packagePath
						+ " > arguments[url/user/password/driver]'s value is not complete.");
			}
			properties.load(in);
			System.out.println("[DBUtil:SUCCESS ] " + packagePath + " > load properties success!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[DBUtil:ERROR ] " + packagePath + " > close file failed!");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static PreparedStatement prepareStatement(Connection connection, String sql) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	public static void closePreparedStatement(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
				preparedStatement = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Statement createStatement(Connection connection) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	public static void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet getResultSet(Statement statement, String sql) {
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public static void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// (lazy) load connection
	private static void loadConnection() {

		// load Connection
		try {
			Class.forName(properties.getProperty("driver"));
		} catch (ClassNotFoundException e) {
			System.out.println("[DBUtil:ERROR ] " + packagePath + " > load jdbc driver faild.");
			e.printStackTrace();
		}

		// connection database
		try {
			connection = DriverManager.getConnection(properties.getProperty("url"), properties);
		} catch (SQLException e) {
			System.out.println("[DBUtil:ERROR ] " + packagePath + " > [loadConnection] connection database faild.");
			e.printStackTrace();
		}

		System.out.println("[DBUtil:SUCCESS ] " + packagePath + " > [loadConnection] connection database success!");

	}

	// get connection
	public static Connection getConnection() {
		if (connection == null) {
			loadConnection();
		}
		return connection;
	}

	// reset properties file path
	public static void setPropertiesFilePath(String propertiesFilePath) {
		dbFilePath = propertiesFilePath;
	}

	// get dbutil's proprties
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * 关闭数据库资源
	 * 
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
		// 关闭数据库资源
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DML:insert update delete
	 * 
	 * @throws Exception
	 */
	public static int executeUpdate(String sql, Object... params) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			// 获取数据库连接
			conn = getConnection();

			// 使用手枪发送SQL命令并得到结果
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			n = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			// 关闭数据库资源
			closeAll(null, pstmt, null);// 采用业务层后不关闭
		}

		// 返回数据
		return n;
	}

	public void DBUtilTest() {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int index = 0;// resultSet's index
		String sql = "select * from employee";

		// init prepareStatement
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			System.out.println("[Test:execute error]: load prepareStatement failed!");
			e.printStackTrace();
		}

		// execute SQL:get resultSet
		try {

			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			System.out.println("[Test:execute error]: execute sql failed!");
			e.printStackTrace();
		}

		try {
			while (resultSet.next()) {
				// notice: resultSet.getObject(var):default: from 1 to n
				System.out.println("[Test] " + index + ":" + resultSet.getObject(index + 1).toString());
				index++;
			}
			System.out.println("[Test] row total is " + resultSet.getRow() + ".");
		} catch (SQLException e) {
			System.out.println("[Test:execute error]: iterate resultset failed!");
			e.printStackTrace();
		}

	}
}