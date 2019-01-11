package com.ibm.example.cdi.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Logging
public class LoggingInterceptor {
	
	static int counter = 0;
	
	@AroundInvoke
	public Object doLog (InvocationContext ctx) throws Exception {
		System.out.println(++counter + " : " + ctx.getMethod().getName());
		return ctx.proceed();
	}
}
