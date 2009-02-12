package org.jsemantic.jembedded.services.httpservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE})
/**
 * 
 */
public @interface HttpServiceConfiguration {
	/**
	 * 
	 * @return
	 */
	public int port();
	/**
	 * 
	 * @return
	 */
	public String host();
	/**
	 * 
	 * @return
	 */
	public String root();
	/**
	 * 
	 * @return
	 */
	public String webApplication();

}
