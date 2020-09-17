/* Copyright 2019 Paremus, Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package org.apache.aries.typedevent.bus.osgi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.aries.typedevent.bus.common.TestEvent;
import org.apache.aries.typedevent.bus.common.TestEvent2;
import org.junit.jupiter.api.AfterEach;
import org.mockito.ArgumentMatcher;
import org.osgi.framework.ServiceRegistration;

/**
 * This is a JUnit test that will be run inside an OSGi framework.
 * 
 * It can interact with the framework by starting or stopping bundles,
 * getting or registering services, or in other ways, and then observing
 * the result on the bundle(s) being tested.
 */
public abstract class AbstractIntegrationTest {
    
	
	protected final List<ServiceRegistration<?>> regs = new ArrayList<ServiceRegistration<?>>();
    
    @AfterEach
    public void tearDown() throws Exception {
        regs.forEach(sr -> {
        	try {
        		sr.unregister();
        	} catch (Exception e) { }
        });
    }
    
    protected ArgumentMatcher<TestEvent> isTestEventWithMessage(String message) {
    	return new ArgumentMatcher<TestEvent>() {
			
			@Override
			public boolean matches(TestEvent argument) {
				return message.equals(argument.message);
			}
		};
    }

    protected ArgumentMatcher<TestEvent2> isTestEvent2WithMessage(String message) {
    	return new ArgumentMatcher<TestEvent2>() {
    		
    		@Override
    		public boolean matches(TestEvent2 argument) {
    			return message.equals(argument.subEvent.message);
    		}
    	};
    }
    
    protected ArgumentMatcher<Map<String, Object>> isUntypedTestEventWithMessage(String message) {
    	return new ArgumentMatcher<Map<String, Object>>() {
    		
    		@Override
    		public boolean matches(Map<String, Object> argument) {
    			return argument != null && message.equals(argument.get("message"));
    		}
    	};
    }
}