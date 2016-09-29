package net.paoding.rose.jade.context.spring;

import java.lang.reflect.Method;


import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.context.JadeInvocationHandler;
import net.paoding.rose.jade.statement.DAOMetaData;

public class JadeSpringInvocationHandler extends JadeInvocationHandler  {
	private final DAOMetaData daoMetaData;
	
	

	public JadeSpringInvocationHandler(DAOMetaData daoMetaData) {
		super(daoMetaData);
		this.daoMetaData = daoMetaData;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Class<?> clazz =method.getDeclaringClass();
		if (clazz!= daoMetaData.getDAOClass()
				&& clazz.getAnnotation(DAO.class) == null) {
			Object bean = SpringContextHolder.getBean(clazz);
			return method.invoke(bean, args);
		}

		return super.invoke(proxy, method, args);
	}

	

}
