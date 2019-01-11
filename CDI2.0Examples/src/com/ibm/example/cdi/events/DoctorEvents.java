package com.ibm.example.cdi.events;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.interceptor.Interceptor;

@ApplicationScoped
public class DoctorEvents {
	
	static int regenerateCount = 0;

	public void firstObserver(@Observes @Priority(Interceptor.Priority.APPLICATION) @Doctor Regenerate doctor) {
		System.out.println("The Doctor is regenerating again");
	}
	
	public void secondObserver(@Observes @Priority(Interceptor.Priority.APPLICATION + 1) @Doctor Regenerate doctor) {
		System.out.println("The Doctor has regenerated " + ++regenerateCount + " times");
	}
	
	public void firstAsync(@ObservesAsync @Priority(Interceptor.Priority.APPLICATION) @Doctor Regenerate doctor) {
		System.out.println("The Doctor has regenerated (async1) " + ++regenerateCount + " times");
	}
}
