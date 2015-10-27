package com.codyy.rrt.base.easyui;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.codyy.rrt.base.Pageable;
import com.codyy.rrt.base.util.DateUtils;
import com.codyy.rrt.base.util.PropertyComparator;

public class ResponseUtils {

	public static String buildJson(Pageable<?> page) {
		return buildJson(page, null, null);
	}

	public static String buildJson(Pageable<?> page, String dateFormate) {
		return buildJson(page, dateFormate, null);
	}

	public static String buildJson(Pageable<?> page, String[] buildProperties) {
		return buildJson(page, null, buildProperties);
	}

	/**
	 * 将Pageable对象转换为EasyUI中grid所使用的json对象
	 * 
	 * @param page
	 * @return
	 */
	public static String buildJson(Pageable<?> page, String dateFormate, String[] buildProperties) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalRecords());
		map.put("rows", page.getList());
		return toJSONString(map, dateFormate, buildProperties);
	}

	public static String buildJson(List<?> list) {
		return buildJson(list, null, null);
	}

	public static String buildJson(List<?> list, String dateFormate) {
		return buildJson(list, dateFormate, null);
	}

	public static String buildJson(List<?> list, String[] buildProperties) {
		return buildJson(list, null, buildProperties);
	}

	/**
	 * 将List转换为EasyUI中grid所使用的json对象
	 * 
	 * @param list
	 * @return
	 */
	public static String buildJson(List<?> list, String dateFormate, String[] buildProperties) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("rows", list);
		return toJSONString(map, dateFormate, buildProperties);
	}

	/**
	 * 把List数据转换为easyui中treegrid所使用的json数据, 根据数据自动构建父子关系 <br>
	 * 适用于EasyUI的TreeGrid
	 * 
	 * @param list
	 *            构建用的数据集合
	 * @param idProperty
	 *            数据对象表示主键的属性
	 * @param parentIdProperty
	 *            用于表示父节点id的属性
	 * @param sortProperty
	 *            在同一级中, 按照那个字段排序
	 * @param asc
	 *            排序是否按照asc排序
	 * @param rootValue
	 *            根节点中parentIdProperty的值
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T, E> String buildTreeGridJson(List<E> list, String idProperty, String parentIdProperty, String sortProperty, boolean asc, T rootValue) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		JSONArray array = buildTreeGridJsonNest(list, idProperty, parentIdProperty, sortProperty, asc, rootValue, 0);
		return toJSONString(array);

	}

	/**
	 * 
	 * @param list
	 * @param idProperty
	 * @param parentIdProperty
	 * @param sortProperty
	 * @param asc
	 * @param parentValue
	 * @param level
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T, E> JSONArray buildTreeGridJsonNest(List<E> list, String idProperty, String parentIdProperty, String sortProperty, boolean asc, T parentValue, int level) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		JSONArray array = new JSONArray();
		List<E> subList = new LinkedList();
		for (int i = 0; i < list.size(); i++) {
			E obj = list.get(i);
			T parentId = (T) PropertyUtils.getProperty(obj, parentIdProperty);
			if (parentId == parentValue || (parentId != null && parentId.equals(parentValue))) {
				subList.add(obj);
			}
		}
		Collections.sort(subList, new PropertyComparator(sortProperty, asc ? 1 : -1));
		for (int i = 0; i < subList.size(); i++) {
			E obj = subList.get(i);
			T id = (T) PropertyUtils.getProperty(obj, idProperty);
			JSONObject jsonObj = JSON.parseObject(toJSONString(obj));
			jsonObj.put("sort", subList.size() == 1 ? 0 : (i == 0 ? 1 : (i == subList.size() - 1 ? 3 : 2)));
			jsonObj.put("level", level);
			JSONArray subarray = buildTreeGridJsonNest(list, idProperty, parentIdProperty, sortProperty, asc, id, level + 1);
			if (subarray.size() > 0)
				jsonObj.put("children", subarray);
			array.add(jsonObj);
		}
		return array;
	}

	/**
	 * 把List数据转换为easyui中tree, combotree所使用的json数据, 根据数据自动构建父子关系 <br>
	 * 适用于EasyUI的Tree, ComboTree
	 * 
	 * @param list
	 *            构建用的数据集合
	 * @param idProperty
	 *            数据对象表示主键的属性
	 * @param parentIdProperty
	 *            用于表示父节点id的属性
	 * @param textProperty
	 *            在Tree中所显示的文字对应的属性
	 * @param sortProperty
	 *            在同一级中, 按照那个字段排序
	 * @param asc
	 *            排序是否按照asc排序
	 * @param rootValue
	 *            根节点中parentIdProperty的值
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T, E> String buildTreeDataJson(List<E> list, String idProperty, String parentIdProperty, String textProperty, String sortProperty, boolean asc, T rootValue) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		JSONArray array = buildTreeDataJsonNest(list, idProperty, parentIdProperty, textProperty, sortProperty, asc, rootValue, null, null);
		return toJSONString(array);

	}

	/**
	 * @see ResponseUtils.buildTreeDataJson(List<E> list, String idProperty,
	 *      String parentIdProperty, String textProperty, String sortProperty,
	 *      boolean asc, T rootValue) 增加node的checked状态的参数checkedIds
	 * @return
	 */
	public static <T, E> String buildTreeDataJsonChecked(List<E> list, String idProperty, String parentIdProperty, String textProperty, String sortProperty, boolean asc, T rootValue, List<T> checkedIds) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		JSONArray array = buildTreeDataJsonNest(list, idProperty, parentIdProperty, textProperty, sortProperty, asc, rootValue, checkedIds, null);
		return toJSONString(array);

	}

	/**
	 * @see ResponseUtils.buildTreeDataJson(List<E> list, String idProperty,
	 *      String parentIdProperty, String textProperty, String sortProperty,
	 *      boolean asc, T rootValue) 增加node的closed,open状态的参数closedIds
	 * @return
	 */
	public static <T, E> String buildTreeDataJsonClosed(List<E> list, String idProperty, String parentIdProperty, String textProperty, String sortProperty, boolean asc, T rootValue, List<T> closedIds) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		JSONArray array = buildTreeDataJsonNest(list, idProperty, parentIdProperty, textProperty, sortProperty, asc, rootValue, null, closedIds);
		return toJSONString(array);

	}

	/**
	 * 
	 * @param list
	 * @param idProperty
	 * @param parentIdProperty
	 * @param textProperty
	 * @param sortProperty
	 * @param asc
	 * @param parentValue
	 * @param checkedIds
	 * @param closedIds
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T, E> JSONArray buildTreeDataJsonNest(List<E> list, String idProperty, String parentIdProperty, String textProperty, String sortProperty, boolean asc, T parentValue, List<T> checkedIds, List<T> closedIds) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		JSONArray array = new JSONArray();
		List<E> subList = new LinkedList();
		for (int i = 0; i < list.size(); i++) {
			E obj = list.get(i);
			T parentId = (T) PropertyUtils.getProperty(obj, parentIdProperty);
			if (parentId == parentValue || (parentId != null && parentId.equals(parentValue))) {
				subList.add(obj);
			}
		}
		Collections.sort(subList, new PropertyComparator(sortProperty, asc ? 1 : -1));
		for (E obj : subList) {
			T id = (T) PropertyUtils.getProperty(obj, idProperty);
			String text = BeanUtils.getSimpleProperty(obj, textProperty);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", id);
			jsonObj.put("text", text);
			JSONObject jsonAttri = JSON.parseObject(toJSONString(obj));
			jsonObj.put("attributes", jsonAttri);
			JSONArray subarray = buildTreeDataJsonNest(list, idProperty, parentIdProperty, textProperty, sortProperty, asc, id, checkedIds, closedIds);
			if (subarray.size() > 0) {
				if (checkedIds != null)
					jsonObj.put("checked", false);
				jsonObj.put("children", subarray);
			} else {
				if (checkedIds != null)
					jsonObj.put("checked", checkedIds.contains(id));
			}
			if (closedIds != null && subarray.size() > 0) {
				if (closedIds.contains(id))
					jsonObj.put("state", "closed");
				else
					jsonObj.put("state", "open");
			}

			array.add(jsonObj);
		}
		return array;
	}

	public static final String toJSONString(Object object) {
		return toJSONString(object, null, null);
	}

	public static final String toJSONString(Object object, String dateFormate) {
		return toJSONString(object, dateFormate, null);
	}

	public static final String toJSONString(Object object, String[] buildProperties) {
		return toJSONString(object, null, buildProperties);
	}

	public static final String toJSONString(Object object, String dateFormate, String[] buildProperties) {
		SerializeWriter out = new SerializeWriter();
		try {
			JSONSerializer serializer = new JSONSerializer(out);

			serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
			if (StringUtils.isBlank(dateFormate))
				serializer.setDateFormat(DateUtils.PATTERN_DATETIME);
			else
				serializer.setDateFormat(dateFormate);
			serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
			serializer.write(object);
			return out.toString();
		} finally {
			out.close();
		}
	}

	public static String buildResultJson(Boolean result) {
		return buildResultJson(result, null);
	}

	public static String buildResultJson(Boolean result, String message) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", result);
		jsonObj.put("message", message);
		return jsonObj.toJSONString();
	}

	public static String buildResultJson(Map<String, Object> map) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(map);
		return jsonObj.toJSONString();
	}

}
