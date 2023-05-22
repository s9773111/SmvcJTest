package test.isaac.config;

import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	/**
	 * RootConfig.class = xml配置的dispatcher-servlet.xml
	 * (取代web.xml內容 org.springframework.web.context.ContextLoaderListener)
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {RootConfig.class};
	}

	
	/**
	 * WebConfig.class = xml配置的applicationContext.xml
	 * (取代web.xml內容 org.springframework.web.servlet.DispatcherServlet
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {WebConfig.class};
	}

	/**
	 * "/"請求映射到DispatcherServlet上
	 * (取代web.xml內容 <servlet-mapping>
	 */
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);  
        return new Filter[] { characterEncodingFilter};
	}
	
	
	
	
	
	
	
}
