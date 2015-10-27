package com.codyy.rrt.base.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;

import com.codyy.rrt.base.model.BaseModel;

public interface BaseMongoDao<T extends BaseModel> {

	/**
	 * 新增
	 */
	public void insert(T entity);

	/**
	 * 批量新增
	 */
	public void insertAll(List<T> list);

	/**
	 * 删除,主键id, 如果主键的值为null,删除会失败
	 */
	public void deleteById(String id);

	/**
	 * 根据主键查询
	 */
	public T findById(String id);

	/**
	 * 生产查询语句
	 * 
	 * @param gtMap
	 * @param ltMap
	 * @param eqMap
	 * @param gteMap
	 * @param lteMap
	 * @param regexMap
	 * @param inMap
	 * @param neMap
	 * @return
	 */
	public Criteria createCriteria(Map<String, Object> gtMap, Map<String, Object> ltMap, Map<String, Object> eqMap, Map<String, Object> gteMap, Map<String, Object> lteMap, Map<String, String> regexMap, Map<String, Collection<Object>> inMap, Map<String, Object> neMap);

	/**
	 * 根据字段相等生产查询语句
	 * 
	 * @param eqMap
	 * @return
	 */
	public Criteria createCriteria(Map<String, Object> eqMap);

	/**
	 * 
	 * @param eqMap
	 * @param lteMap
	 * @param gteMap
	 * @param regexMap
	 * @return
	 */
	public Criteria createCriteria(Map<String, Object> eqMap, Map<String, Object> lteMap, Map<String, Object> gteMap, Map<String, String> regexMap);

	/**
	 * 
	 * @param eqMap
	 * @param neMap
	 * @return
	 */
	public Criteria createCriteria(Map<String, Object> eqMap, Map<String, Object> neMap);

	/**
	 * 
	 * @param eqMap
	 * @param neMap
	 * @param inMap
	 * @return
	 */
	public Criteria createCriteria(Map<String, Object> eqMap, Map<String, Object> neMap, Map<String, Collection<Object>> inMap);

	/**
	 * 根据各种条件查询总数
	 * 
	 * @param gtMap
	 * @param ltMap
	 * @param eqMap
	 * @param gteMap
	 * @param lteMap
	 * @param regexMap
	 * @param inMap
	 * @param neMap
	 * @return
	 */
	public long findCount(Map<String, Object> gtMap, Map<String, Object> ltMap, Map<String, Object> eqMap, Map<String, Object> gteMap, Map<String, Object> lteMap, Map<String, String> regexMap, Map<String, Collection<Object>> inMap, Map<String, Object> neMap);

	/**
	 * 根据创建的条件查询总数
	 * 
	 * @param queryC
	 * @return
	 */
	public long findCount(Criteria queryC);

	/**
	 * 根据多个种条件 or 的方式查询
	 * 
	 * @param orList
	 * @return
	 */
	public long findCount(Criteria... orList);

	public long findCount(Map<String, Object> gtMap, Map<String, Object> ltMap, Map<String, Object> eqMap, Map<String, String> regexMap, Map<String, Collection<Object>> inMap);

	public long findCountByContainRegex(Map<String, Object> gtMap, Map<String, Object> ltMap, Map<String, Object> eqMap, Map<String, String> regexMap);

	/**
	 * 根据分页+条件获取对应的实体集合
	 * 
	 * @param eqMap
	 * @param gtMap
	 * @param ltMap
	 * @param gteMap
	 * @param lteMap
	 * @param regexMap
	 * @param inMap
	 * @param neMap
	 * @param orders
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<T> findListByPage(Map<String, Object> eqMap, Map<String, Object> gtMap, Map<String, Object> ltMap, Map<String, Object> gteMap, Map<String, Object> lteMap, Map<String, String> regexMap, Map<String, Collection<Object>> inMap, Map<String, Object> neMap, List<Order> orders, int pageIndex, int pageSize);

	/**
	 * 符合条件的某一条数据
	 * 
	 * @param eqMap
	 * @param gtMap
	 * @param ltMap
	 * @param gteMap
	 * @param lteMap
	 * @param regexMap
	 * @param inMap
	 * @return
	 */
	public T findObject(Map<String, Object> eqMap, Map<String, Object> gtMap, Map<String, Object> ltMap, Map<String, Object> gteMap, Map<String, Object> lteMap, Map<String, String> regexMap, Map<String, Collection<Object>> inMap);

	/**
	 * 根据查询条件查询某一个object
	 * 
	 * @param c
	 * @return
	 */
	public T findObject(Criteria c);

	/**
	 * 多个查询条件or方式组合查询
	 * 
	 * @param orList
	 * @return
	 */
	public List<T> findList(Criteria... orList);

	/**
	 * 多个查询条件or方式组合查询
	 * 
	 * @param orders
	 * @param orList
	 * @return
	 */
	public List<T> findListByOrder(List<Order> orders, Criteria... orList);

	/**
	 * 根据查询条件直接查询
	 * 
	 * @param c
	 * @param orders
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<T> findListByPage(Criteria c, List<Order> orders, int pageIndex, int pageSize);

	public List<T> findListByOrder(Criteria c, List<Order> orders);

	public List<T> findList(Criteria c);

	/**
	 * 多个查询条件or方式组合查询
	 * 
	 * @param orders
	 * @param pageIndex
	 * @param pageSize
	 * @param orList
	 * @return
	 */
	public List<T> findListByPage(List<Order> orders, int pageIndex, int pageSize, Criteria... orList);

	/**
	 * 
	 * @param eqMap
	 * @param gtMap
	 * @param ltMap
	 * @param gteMap
	 * @param lteMap
	 * @param regexMap
	 * @param inMap
	 * @param neMap
	 * @return
	 */
	public List<T> findListNotContainOrder(Map<String, Object> eqMap, Map<String, Object> gtMap, Map<String, Object> ltMap, Map<String, Object> gteMap, Map<String, Object> lteMap, Map<String, String> regexMap, Map<String, Collection<Object>> inMap, Map<String, Object> neMap);
}
