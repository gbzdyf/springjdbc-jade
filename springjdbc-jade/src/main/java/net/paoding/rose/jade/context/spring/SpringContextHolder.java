
package net.paoding.rose.jade.context.spring;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.io.DefaultResourceLoader;


/**
 * 
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 *  
 * @author  窦日晓
 * @version  [V2.00, 2016年3月8日]
 * @since V1.00
 */


/**
 * 其实就是spring加载后就找继承了这个接口的类，然后自动执行setApplicationContext方法，
 * 给它填个applicationContext，然后其他地方都可以通过这个SpringContextHolder获取applicationContext，
 * 进而拿到beans
 * 
 *
 */
public class SpringContextHolder  {

	private static ListableBeanFactory applicationContext = null;

	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);
	

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ListableBeanFactory getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	

	public static String getResourceRootRealPath() {
		String rootRealPath = "";
		try {
			rootRealPath = new DefaultResourceLoader().getResource("").getFile().getAbsolutePath();
		} catch (IOException e) {
			logger.warn("获取资源根目录失败");
		}
		return rootRealPath;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearHolder() {
		if (logger.isDebugEnabled()) {
			logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		}
		applicationContext = null;
	}

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	public static void setApplicationContext(ListableBeanFactory applicationContext) {
		// logger.debug("注入ApplicationContext到SpringContextHolder:{}", applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.info("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ SpringContextHolder.applicationContext);
		}

		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	public void destroy() throws Exception {
		SpringContextHolder.clearHolder();
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {	    
//		Validate.validState(applicationContext != null,
	//			"applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
	}
}