package com.codyy.rrt.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.codyy.base.web.SpringContext;
import com.codyy.rrt.commons.entity.KeywordConfig;

public class KeywordWrapper extends HttpServletRequestWrapper {
	private final static String KEYWORD_EXCEPT_PROPERTIES ="keywordExceptFilter.properties";//定义不需要过滤的url
	private boolean needFilter = true;

	private Set<String> keywords = null;

	public KeywordWrapper(HttpServletRequest request) throws Exception {
		super(request);
		Set<String> except = getKeywordURLExcept().keySet();
		String location = request.getServletPath();
		for(String string : except){
			if(location.indexOf(string) > -1){//判定是否需要过滤 
				needFilter = false;
			}
		}
		if(needFilter){//初始化关键字信息（先从redis中去，若不存在，则去mongodb中取）
			StringRedisTemplate template = SpringContext.getBean(StringRedisTemplate.class);
			BoundHashOperations<String, String, String> operations = template.boundHashOps(CommonsConstant.KEYWORD);
			Map<String, String> map = operations.entries();
			keywords = map.keySet();
			if (keywords.size() == 0) {
				Map<String, String> tm = getKeyWords();
				operations.putAll(tm);
				keywords = tm.keySet();
			}
		}
	}

	public String filter(String value) {
		if(needFilter && StringUtils.isNotBlank(value) && keywords != null){
			for (String str : keywords) {
				if (StringUtils.isNotBlank(str)) {
					value = value.replace(str, "***");
				}
			}
		}
		return value;
	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value == null)
			return null;
		return filter(value);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> paramMap = super.getParameterMap();
		Map<String, String[]> map = new HashMap<String, String[]>();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			String[] values = entry.getValue();
			if (values != null && values.length > 0) {
				String[] newValues = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					newValues[i] = filter(values[i]);
				}
				map.put(entry.getKey(), newValues);
			}
		}
		return map;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				values[i] = filter(values[i]);
			}
		}
		return values;
	}

	private Map<String, String> getKeyWords() {
		MongoTemplate mongoTemplate = SpringContext.getBean(MongoTemplate.class);
		Criteria criteria = Criteria.where("state").is(KeywordConfig.KEYWORD_STATE_ON);
		Query query = new Query(criteria);
		List<KeywordConfig> list = mongoTemplate.find(query, KeywordConfig.class);
		Map<String, String> result = new HashMap<String, String>();
		if (list.size() > 0) {
			for (KeywordConfig keywordConfig : list) {
				result.put(getString(keywordConfig.getKeywordName()), "1");
			}
		}
		return result;
	}

	private String getString(String string) {
		if (string == null) {
			return "";
		}
		return string;
	}

	
	private Map<String,String> getKeywordURLExcept(){
		StringRedisTemplate template = SpringContext.getBean(StringRedisTemplate.class);
		BoundHashOperations<String, String, String> operations = template.boundHashOps(CommonsConstant.KEYWORD_EXCEPT);
		Map<String,String> map = operations.entries();
		if(map.size()>0){
			return map;
		}else{
			try{
				InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(KEYWORD_EXCEPT_PROPERTIES);
				Properties prop = new Properties();
				prop.load(input);
				Set<Object> keySet = prop.keySet();
				input.close();
				if(keySet.size()>0){
					for(Object object : keySet){
						map.put(object.toString(), "1");
				}
				operations.putAll(map);
				return map;
				}else{
					return map;
				}
			}catch(IOException e) {
					System.out.println("读取免过滤url错误"); e.printStackTrace(); return map;
			}
		}
	}
}
