package com.codyy.rrt.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.codyy.rrt.base.model.BaseModel;
import com.codyy.rrt.base.util.BeanUtils;

/**
 * 基础Dao接口实现类，实现改类的子类必须设置泛型类型
 * 
 * @author kongchuifang
 * @date 2014-10-29
 */
public class BaseDaoImpl<T extends BaseModel> implements BaseDao<T> {

	@Autowired(required = true)
	protected SqlSession sqlSessionTemplate;

	public static final String SQLNAME_SEPARATOR = ".";
	public final String SQL_COUNT = "count";
	public final String SQL_LIST = "list";
	public final String SQL_GET_BY_ID = "getById";
	public final String SQL_UPDATE_BY_ID = "updateById";
	public final String SQL_UPDATE_BY_ID_SELECTIVE = "updateByIdSelective";
	public final String SQL_DELETE = "delete";
	public final String SQL_DELETE_BY_ID = "deleteById";
	public final String SQL_INSERT = "insert";

	/**
	 * 获取泛型类型的实体对象类全名:包名+类名
	 */
	protected String getDefaultSqlNamespace() {
		Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
		return genericClass == null ? null : genericClass.getName();
	}

	/**
	 * SqlMapping命名空间 + “.” +sqlName
	 */
	protected String getSqlName(String sqlName) {
		return getDefaultSqlNamespace() + SQLNAME_SEPARATOR + sqlName;
	}

	/**
	 * 查询实体
	 * 
	 * @param query
	 * @return
	 */
	public <V extends T> V get(T query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName(SQL_LIST), params);
		} catch (Exception e) {
			throw new RuntimeException(String.format("查询一条记录出错！语句：%s", getSqlName(SQL_LIST)), e);
		}
	}

	/**
	 * 根据id查询实体
	 * 
	 * @param id
	 * @return
	 */
	public <V extends T> V getById(String id) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName(SQL_GET_BY_ID), id);
		} catch (Exception e) {
			throw new RuntimeException(String.format("根据ID查询对象出错！语句：%s", getSqlName(SQL_GET_BY_ID)), e);
		}
	}

	/**
	 * 查询列表
	 * 
	 * @param query
	 * @return
	 */
	public <V extends T> List<V> list(T query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectList(getSqlName(SQL_LIST), params);
		} catch (Exception e) {
			throw new RuntimeException(String.format("查询对象列表出错！语句：%s", getSqlName(SQL_LIST)), e);
		}
	}

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	public <V extends T> List<V> listAll() {
		try {
			return sqlSessionTemplate.selectList(getSqlName(SQL_LIST));
		} catch (Exception e) {
			throw new RuntimeException(String.format("查询所有对象列表出错！语句：%s", getSqlName(SQL_LIST)), e);
		}
	}

	/**
	 * 查询map
	 * 
	 * @param query
	 * @param mapKey
	 * @return
	 */
	public <K, V extends T> Map<K, V> map(T query, String mapKey) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectMap(getSqlName(SQL_LIST), params, mapKey);
		} catch (Exception e) {
			throw new RuntimeException(String.format("查询对象Map时出错！语句：%s", getSqlName(SQL_LIST)), e);
		}
	}

	/**
	 * 设置分页
	 * 
	 * @param pageInfo
	 *            分页信息
	 * @return SQL分页参数对象
	 */
	protected RowBounds getRowBounds(Pageable pageable) {
		RowBounds bounds = RowBounds.DEFAULT;
		if (null != pageable) {
			bounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
		}
		return bounds;
	}

	/**
	 * 获取分页查询参数
	 * 
	 * @param query
	 *            查询对象
	 * @param pageable
	 *            分页对象
	 * @return Map 查询参数
	 */
	protected Map<String, Object> getParams(T query, Pageable pageable) {
		Map<String, Object> params = BeanUtils.toMap(query, getRowBounds(pageable));
		if (pageable != null && pageable.getSort() != null) {
			String sorting = pageable.getSort().toString();
			params.put("sorting", sorting.replace(":", ""));
		}
		return params;
	}

	/**
	 * 根据分页对象查询列表
	 * 
	 * @param query
	 * @param pageable
	 * @return
	 */
	public <V extends T> List<V> listByPage(T query, Pageable pageable) {
		try {
			return sqlSessionTemplate.selectList(getSqlName(SQL_LIST), getParams(query, pageable));
		} catch (Exception e) {
			throw new RuntimeException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQL_LIST)), e);
		}
	}

	/**
	 * 根据分页对象查询列表
	 * 
	 * @param query
	 * @param pageable
	 * @return
	 */
	public <V extends T> Page<V> page(T query, Pageable pageable) {
		try {
			List<V> contentList = sqlSessionTemplate.selectList(getSqlName(SQL_LIST), getParams(query, pageable));
			return new PageImpl<V>(contentList, pageable, this.count(query));
		} catch (Exception e) {
			throw new RuntimeException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQL_LIST)), e);
		}
	}

	/**
	 * 根据分页对象查询列表
	 * 
	 * @param query
	 * @param mapKey
	 * @param pageable
	 * @return
	 */
	public <K, V extends T> Map<K, V> mapByPage(T query, String mapKey, Pageable pageable) {
		try {
			return sqlSessionTemplate.selectMap(getSqlName(SQL_LIST), getParams(query, pageable), mapKey);
		} catch (Exception e) {
			throw new RuntimeException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQL_LIST)), e);
		}
	}

	/**
	 * 查询对象总数
	 * 
	 * @return
	 */
	public Long count() {
		try {
			return sqlSessionTemplate.selectOne(getSqlName(SQL_COUNT));
		} catch (Exception e) {
			throw new RuntimeException(String.format("查询对象总数出错！语句：%s", getSqlName(SQL_COUNT)), e);
		}
	}

	/**
	 * 查询对象总数
	 * 
	 * @param query
	 * @return
	 */
	public Long count(T query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName(SQL_COUNT), params);
		} catch (Exception e) {
			throw new RuntimeException(String.format("查询对象总数出错！语句：%s", getSqlName(SQL_COUNT)), e);
		}
	}

	/**
	 * 添加对象
	 * 
	 * @param entity
	 */
	public void insert(T entity) {
		try {
			sqlSessionTemplate.insert(getSqlName(SQL_INSERT), entity);
		} catch (Exception e) {
			throw new RuntimeException(String.format("添加对象出错！语句：%s", getSqlName(SQL_INSERT)), e);
		}
	}

	/**
	 * 删除对象
	 * 
	 * @param query
	 * @return
	 */
	public int delete(T query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.delete(getSqlName(SQL_DELETE), params);
		} catch (Exception e) {
			throw new RuntimeException(String.format("删除对象出错！语句：%s", getSqlName(SQL_DELETE)), e);
		}
	}

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(String id) {
		try {
			return sqlSessionTemplate.delete(getSqlName(SQL_DELETE_BY_ID), id);
		} catch (Exception e) {
			throw new RuntimeException(String.format("根据ID删除对象出错！语句：%s", getSqlName(SQL_DELETE_BY_ID)), e);
		}
	}

	/**
	 * 删除所有对象
	 * 
	 * @return
	 */
	public int deleteAll() {
		try {
			return sqlSessionTemplate.delete(getSqlName(SQL_DELETE));
		} catch (Exception e) {
			throw new RuntimeException(String.format("删除所有对象出错！语句：%s", getSqlName(SQL_DELETE)), e);
		}
	}

	/**
	 * 根据ID更新对象
	 * 
	 * @param entity
	 * @return
	 */
	public int updateById(T entity) {
		try {
			return sqlSessionTemplate.update(getSqlName(SQL_UPDATE_BY_ID), entity);
		} catch (Exception e) {
			throw new RuntimeException(String.format("根据ID更新对象出错！语句：%s", getSqlName(SQL_UPDATE_BY_ID)), e);
		}
	}

	/**
	 * 根据ID更新对象某些属性
	 * 
	 * @param entity
	 * @return
	 */
	public int updateByIdSelective(T entity) {
		try {
			return sqlSessionTemplate.update(getSqlName(SQL_UPDATE_BY_ID_SELECTIVE), entity);
		} catch (Exception e) {
			throw new RuntimeException(String.format("根据ID更新对象某些属性出错！语句：%s", getSqlName(SQL_UPDATE_BY_ID_SELECTIVE)), e);
		}
	}

	/**
	 * 根据id集合批量删除
	 */
	public void deleteByIdInBatch(List<String> idList) {
		if (idList == null || idList.isEmpty())
			return;
		for (String id : idList) {
			this.deleteById(id);
		}
	}

	/**
	 * 根据实体集合批量修改
	 */
	public void updateInBatch(List<T> entityList) {
		if (entityList == null || entityList.isEmpty())
			return;
		for (T entity : entityList) {
			this.updateByIdSelective(entity);
		}
	}

	/**
	 * 根据实体集合批量插入
	 */
	public void insertInBatch(List<T> entityList) {
		if (entityList == null || entityList.isEmpty())
			return;
		for (T entity : entityList) {
			this.insert(entity);
		}
	}

}