package com.jswy.domain.generic.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.jswy.domain.generic.demo.model.User;

/**
 * mybatis数据层接口
 *
 */
@Repository
public interface UserMapper {

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from t_user where id = #{id}")
	@Results({ @Result(column = "create_time", property = "createTime") })
	User findById(int id);

	/**
	 * 
	 * @param user
	 */
	@Update("update t_user set name=#{name} where id =#{id}")
	void updateUser(User user);

	/**
	 * 
	 * @param user
	 */
	@Delete("delete from t_user where id = #{id}")
	void deleteUser(User user);

	/**
	 * 
	 * @param user
	 * @return
	 */
	@Insert("insert into t_user(id,name,password,age,create_time)values(seq_user1_id.nextval,#{name},#{password},#{age},#{createTime})")
	// @Options(useGeneratedKeys = ture,keyProperty = "id",keyColumn = "user_id")
	// mysql的自增列会用到.user_id指只增列列名
	int insert(User user);

	/**
	 * 
	 * @return
	 */
	@Select("select * from t_user")
	@Results({ @Result(column = "create_time", property = "createTime") })
	List<User> getAll();

	/**
	 * 自定义添加通过用户名称模糊查找用户信息,该实现依赖SQL<br>
	 * （通过yml的apperLocations: classpath:mapper/*.xml路径映射,实际配置在user-Mapper.xml中）
	 * 
	 * @param name
	 * @return
	 */
	List<User> findByName(String name);
}