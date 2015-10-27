package com.codyy.rrt.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.codyy.base.web.SpringContext;
import com.codyy.rrt.commons.entity.AppConfig;

public class SecurityWrapper extends HttpServletRequestWrapper {
	private final static String SECURITY_PROPERTIES = "securityFilter.properties";// 定义不需要过滤的url
	private boolean needFilter = true;
	private static Map<Character, String> transferMap = null;// 需要转换的字符

	static {
		transferMap = new HashMap<Character, String>();
		AppConfig config = SpringContext.getBean(AppConfig.class);
		String transString = config.getTransferChars();
		if (StringUtils.isNotBlank(transString)) {
			char[] transferChars = transString.toCharArray();
			for (char ch : transferChars) {
				transferMap.put(ch, TransferMap.getInstance().convertToReference(ch));
			}
		}
	}

	public SecurityWrapper(HttpServletRequest request) throws Exception {
		super(request);
		String location = request.getServletPath();
		Map<String, String> filterMap = getSecurityURLExcept();
		Set<String> filterSet = filterMap.keySet();
		for (String string : filterSet) {
			if (location.indexOf(string) > -1) {// 判定是否需要过滤
				needFilter = false;
			}
		}
	}

	private String filterSecurity(String value) {
		if (needFilter && StringUtils.isNotBlank(value)) {
			value = trans(value);
		}
		return value;
	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value == null)
			return null;
		return filterSecurity(value);
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
					newValues[i] = filterSecurity(values[i]);
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
				values[i] = filterSecurity(values[i]);
			}
		}
		return values;
	}

	private String trans(String value) {
		if (value == null) {
			return value;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			String str = transferMap.get(ch);
			if (str != null) {
				buffer.append(str);
			} else {
				buffer.append(ch);
			}
		}
		// logger.debug("trans:" + buffer.toString());
		return buffer.toString();
	}

	public static String tansValue(String value) {
		if (value == null) {
			return value;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			String str = transferMap.get(ch);
			if (str != null) {
				buffer.append(str);
			} else {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	private Map<String, String> getSecurityURLExcept() {
		StringRedisTemplate template = SpringContext.getBean(StringRedisTemplate.class);
		BoundHashOperations<String, String, String> operations = template.boundHashOps(CommonsConstant.SECURITY_EXCEPT);
		Map<String, String> map = operations.entries();
		if (map.size() > 0) {
			return map;
		} else {
			try {
				InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(SECURITY_PROPERTIES);
				Properties prop = new Properties();
				prop.load(input);
				Set<Object> keySet = prop.keySet();
				input.close();
				if (keySet.size() > 0) {
					for (Object object : keySet) {
						map.put(object.toString(), "1");
					}
					operations.putAll(map);
					return map;
				} else {
					return map;
				}
			} catch (IOException e) {
				System.out.println("读取免过滤url错误");
				e.printStackTrace();
				return map;
			}
		}
	}
}
