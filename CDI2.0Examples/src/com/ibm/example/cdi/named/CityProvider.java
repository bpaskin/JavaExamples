package com.ibm.example.cdi.named;

import java.lang.reflect.Type;
import java.util.Iterator;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

public class CityProvider {
	@Inject private BeanManager bm;
	
	public Object getBean(String name) {
        
		Iterator<Bean<?>> iter = bm.getBeans(name).iterator();
		if (!iter.hasNext()) {
			throw new IllegalStateException("Cannot find instance of " + name);
		}
	 
		Bean<?> bean = iter.next();
		CreationalContext<?> ctx = bm.createCreationalContext(bean);
		Type type = (Type) bean.getTypes().iterator().next();
		return bm.getReference(bean, type, ctx);
    }
 
	public Object getBean(Class<?> clazz) {
 
		Iterator<Bean<?>> iter = bm.getBeans(clazz).iterator();
		if (!iter.hasNext()) {
			throw new IllegalStateException("Cannot find instance of " + clazz.getCanonicalName());
		}
	 
		Bean<?> bean = iter.next();
		CreationalContext<?> ctx = bm.createCreationalContext(bean);
		return bm.getReference(bean, clazz, ctx);
	}
}
