package com.codyy.rrt.resource.mapper;

import java.util.List;
import java.util.Map;

import com.codyy.rrt.resource.model.BaseAttacheFile;

public interface BaseAttacheFileMapper {

	/**
	 * 根据资源ID查找轻松一刻图片列表
	 * 
	 * @param parMap
	 * @return
	 */
	public List<BaseAttacheFile> findByForeignIdAndType(Map<String, Object> parMap);
}
