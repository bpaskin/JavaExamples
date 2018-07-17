package com.ibm.example.cdi20;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Named;
import javax.interceptor.Interceptor;

@Named
@ApplicationScoped
public class CDIEvents {
	
	public void firstObserver(@Observes @Priority(Interceptor.Priority.APPLICATION) @Information  String payload) {
		System.out.println("1st Observer Fired");
	}
	
	public void secondObserver(@Observes @Priority(Interceptor.Priority.APPLICATION + 1) @Information String payload) {
		System.out.println("2nd Observer Fired");
	}
	
	public void firstAsyncObserver(@ObservesAsync @Information String payload) {
		System.out.println("1st Async Observer Fired");
	}
	
	public void secondAsyncObserver(@ObservesAsync @Information String payload) {
		System.out.println("2nd Async Observer Fired");
	}
	
	public void thirdAsyncObserver(@ObservesAsync @Information String payload) {
		System.out.println("3rd Async Observer Fired");
	}
}
