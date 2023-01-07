package com.jswy.infrastructure.general.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.Assert;

/**
 * 解决实体管理器工厂的浪费资源和耗时问题 通过静态代码块的形式，当程序第一次访问此工具类时，创建一个公共的实体管理器工厂对象
 *
 * 第一次访问getEntityManager方法：经过静态代码块创建一个factory对象，再调用方法创建一个EntityManager对象
 * 第二次方法getEntityManager方法：直接通过一个已经创建好的factory对象，创建EntityManager对象
 */
public class JpaUtils {

	private static EntityManagerFactory factory;

	static {
		// 1.加载配置文件，创建entityManagerFactory
		// 注意：该方法参数必须和persistence.xml中persistence-unit标签name属性取值一致
		factory = Persistence.createEntityManagerFactory("myJpa");
	}

	/**
	 * 获取EntityManager对象
	 */
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	// JPA的实体管理器工厂：相当于Hibernate的SessionFactory
	@PersistenceContext
	private EntityManager em;

	/**
	 * 获取列表
	 * 
	 * @param sql
	 * @param params
	 * @param requiredType
	 * @return
	 */
	public <T> List<T> list(String sql, Map<String, Object> params, Class<T> requiredType) {
		// String hql = "select o.uuid,o.name from UserModel o where 1=1 and
		// o.uuid=:uuid";
		Query query = em.createQuery(sql);
		if (params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.getResultList();
	}

	/**
	 * 获取分页数据
	 * 
	 * @param sql
	 * @param params
	 * @param pageable
	 * @param requiredType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> page(String sql, Map<String, Object> params, Pageable pageable, Class<T> requiredType) {
		Query query = em.createQuery(sql);
		if (params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		if (pageable.isPaged()) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}
		/**
		 * 生成获取总数的sql
		 */
		TypedQuery<Long> cQuery = (TypedQuery<Long>) em.createQuery(QueryUtils.createCountQueryFor(sql));
		return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> executeCountQuery(cQuery));
		// return new PageImpl<T>(query.getResultList(), pageable,
		// executeCountQuery(cQuery));
	}

	/**
	 * Executes a count query and transparently sums up all values returned.
	 *
	 * @param query must not be {@literal null}.
	 * @return
	 */
	private static Long executeCountQuery(TypedQuery<Long> query) {
		Assert.notNull(query, "TypedQuery must not be null!");
		List<Long> totals = query.getResultList();
		Long total = 0L;
		for (Long element : totals) {
			total += element == null ? 0 : element;
		}
		return total;
	}

	/**
	 * 判读当前类是否是支持的类型
	 *
	 * @param type
	 * @return
	 */
	private boolean isSupportType(Class<?> type) {
		if (String.class.equals(type)) {
			return true;
		}
		if (LocalDateTime.class.equals(type)) {
			return true;
		}
		if (BigDecimal.class.equals(type) || Double.class.equals(type) || Float.class.equals(type)) {
			return true;
		}
		if (Boolean.class.equals(type)) {
			return true;
		}
		if (Integer.class.equals(type) || int.class.equals(type)) {
			return true;
		}
		if (Date.class.equals(type)) {
			return true;
		}
		return false;
	}
}