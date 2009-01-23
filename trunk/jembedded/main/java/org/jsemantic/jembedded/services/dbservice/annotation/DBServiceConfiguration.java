/**
 * 
 */
package org.jsemantic.jembedded.services.dbservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author adolfo
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE})
public @interface DBServiceConfiguration {
	
	public String dbPath();
	
	public String user();
	
	public String password();
	
	boolean isMemoryMode() default true;
	
}
