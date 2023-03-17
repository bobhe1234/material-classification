package com.jswy.infrastructure.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jswy.domain.generic.demo.model.User;

public class AliJsonUtils {
	private static Logger logger = LoggerFactory.getLogger(AliJsonUtils.class);

	public static void main(String[] args) {
		String filePath = "E:\\workspace\\lenovo\\202302_PCSDPLM\\HWSW Offering Hierarchy Project\\Create HWOH Data.json";
		String json = readFile(filePath);
		System.out.println("Json from File: " + json);
		JSONObject hwOHCodeJson = JSON.parseObject(json);
		System.out.println("number:" + hwOHCodeJson.getString("number"));

		List<User> users = (List<User>) AliJsonUtils.readJson2Obj("/json/user.json", List.class);

//		String path = CommonJsonUtil.class.getClassLoader().getResource("test.json").getPath();
//		String s = ReadUtils.readJsonFile(path);
		JSONObject jobj = JSON.parseObject(json);
		System.out.println("name" + jobj.get("name"));

		JSONObject address1 = jobj.getJSONObject("address");
		String street = (String) address1.get("street");
		String city = (String) address1.get("city");
		String country = (String) address1.get("country");
		System.out.println("street :" + street);
		System.out.println("city :" + city);
		System.out.println("country :" + country);

		JSONArray links = jobj.getJSONArray("links");
		for (int i = 0; i < links.size(); i++) {
			JSONObject key1 = (JSONObject) links.get(i);
			String name = (String) key1.get("name");
			String url = (String) key1.get("url");
			System.out.println(name);
			System.out.println(url);
		}
	}

	/**
	 * 读取json文件返回数据<br>
	 * 
	 * 
	 * @throws IOException
	 */
	public static Object readJson2Obj(String filePath, Class clazz) {
		String jsonString = null;
		try {
			ClassPathResource resource = new ClassPathResource(filePath);
			if (!resource.exists()) {
				logger.warn("{}文件不存在！", filePath);
				return null;
			}
			File file = resource.getFile();
			jsonString = readFile(file);
		} catch (IOException e) {
			logger.warn("{}文件内容解析失败！", filePath);
			return null;
		}
		return JSONObject.parseObject(jsonString, clazz);

	}

	/**
	 * read File from filepath
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		try {
			File file = new File(filePath);
			return readFile(file);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * read File , turn it to string
	 * 
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static String readFile(File file) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		String jsonString;
		Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
		int ch = 0;
		StringBuffer sb = new StringBuffer();
		while ((ch = reader.read()) != -1) {
			sb.append((char) ch);
		}
		reader.close();
		jsonString = sb.toString();
		return jsonString;
	}

	/**
	 * 把json字符串转换为实体类
	 * 
	 * @param jsonStr
	 * @param obj
	 * @return
	 */
	public static <T> Object jsonToBean1(String jsonStr, Class<T> obj) {
		T t = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			t = objectMapper.readValue(jsonStr, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * String转JSON字符串
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String stringToJsonByFastjson(String key, String value) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>(16);
		map.put(key, value);
		return beanToJson1(map, null);
	}

	/**
	 * 实体类转json
	 * 
	 * @param object
	 * @param dataFormatString
	 * @return
	 */
	public static String beanToJson1(Object object, String dataFormatString) {
		if (object != null) {
			if (StringUtils.isEmpty(dataFormatString)) {
				return JSONObject.toJSONString(object);
			}
			return JSON.toJSONStringWithDateFormat(object, dataFormatString);
		} else {
			return null;
		}
	}

	/**
	 * 字符串转成json对象
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object getJsonObject(String jsonString) {
		return JSONArray.parse(jsonString);
	}

	/**
	 * json字符串转集合对象
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
		List<T> ts = (List<T>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}

	// =============================json字符串转换为map的方法========================
	/**
	 * 这个是用JSON类来解析JSON字符串!!!
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> jsonStringToMap1(String jsonString) {
		return (Map) JSON.parse(jsonString);
	}

	/**
	 * 这个是用JSON类的parseObject来解析JSON字符串!!!
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> jsonStringToMap2(String jsonString) {
		return JSON.parseObject(jsonString);
	}

	/**
	 * 这个是用JSON类,指定解析类型，来解析JSON字符串!!!
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> jsonStringToMap3(String jsonString) {
		return JSON.parseObject(jsonString, Map.class);
	}

	/**
	 * 这个是用JSONObject类的parse方法来解析JSON字符串!!!
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> jsonStringToMap4(String jsonString) {
		return (Map) JSONObject.parse(jsonString);
	}

	/**
	 * 这个是用JSONObject的parseObject方法来解析JSON字符串!!!
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> jsonStringToMap5(String jsonString) {
		return JSONObject.parseObject(jsonString);
	}

	/**
	 * 这个是用JSONObject的parseObject方法并执行返回类型来解析JSON字符串!!!
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> jsonStringToMap6(String jsonString) {
		return JSONObject.parseObject(jsonString, Map.class);
	}
}
