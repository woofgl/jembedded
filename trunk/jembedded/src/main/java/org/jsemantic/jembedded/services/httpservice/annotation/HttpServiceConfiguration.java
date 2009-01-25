package org.jsemantic.jembedded.services.httpservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE})
public @interface HttpServiceConfiguration {
	
	public int port();
	
	public String host();
	
	public String root();
	
	public String webApplication();

}
