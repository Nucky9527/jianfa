package com.atguigu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
	
	//ThreadLocal是一个工具类，它可以将任何数据绑定到当前线程上。
	ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<Map<String,Object>>();
	
	public void start() {
		Map<String,Object> result = new HashMap<String,Object>();
		threadLocal.set(result); //哪个线程创建result对象，就将result对象绑定到哪个线程上。
	}
	
	public void data(Object data) {
		threadLocal.get().put("data", data);
	}
	
	public void success(boolean success) {
		threadLocal.get().put("success", success); //哪个线程调用这个方法，就从哪个线程上获取result,来存储数据。
	}
	
	public void message(String message) {
		threadLocal.get().put("message", message);
	}
	
	public Object end() {
		return threadLocal.get() ;
	}
}
