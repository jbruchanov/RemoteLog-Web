package com.scurab.gwt.rlw.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Joe Scurab
 * Annotiation used by {@link DataServiceImpl.#getData(Class)} method to take care about sorting
 *
 */
@Target(ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
public @interface DefaultOrderString {
	String value();
}
