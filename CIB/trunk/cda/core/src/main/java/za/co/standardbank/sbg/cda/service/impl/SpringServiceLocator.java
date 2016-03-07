package za.co.standardbank.sbg.cda.service.impl;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import za.co.standardbank.sbg.cda.service.Service;
import za.co.standardbank.sbg.cda.service.ServiceLocator;


public class SpringServiceLocator implements ServiceLocator {

    private static SpringServiceLocator instance;

    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new SpringServiceLocator();
        }
        return instance;
    }

    private XmlBeanFactory beanFactory;

    private SpringServiceLocator() {
        super();
        this.beanFactory = new XmlBeanFactory(new ClassPathResource(getContextFilename()));
        PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
        cfg.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        cfg.postProcessBeanFactory(this.beanFactory);
    }

    protected String getContextFilename() {
        return "sbg-cda-serviceContext.xml";
    }

	public Service getService(String name) {
        return (Service) this.beanFactory.getBean(name);
    }

}
