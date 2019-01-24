package com.atguigu.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;

import com.atguigu.atcrowdfunding.util.Const;

@WebListener //声明监听器对象
//@WebFilter //声明过滤器对象
//@WebServlet //声明Servlet对象
public class StartSystemListener implements ServletContextListener {

	//在application被创建时执行
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//将pageContext.request.contextPath上下文路径存放在application域中，给jsp使用. ${APP_PATH}
		System.out.println("自定义监听器已经执行--StartSystemListener");
		ServletContext application = sce.getServletContext();
		String contextPath = application.getContextPath();
		application.setAttribute(Const.APP_PATH, contextPath);
	}

	
	//在application被销毁时执行
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
