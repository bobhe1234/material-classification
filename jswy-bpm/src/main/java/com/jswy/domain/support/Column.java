package com.jswy.domain.support;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	
	String name() default "";
	
	boolean identity() default false;
	
	boolean generate() default true;
	
	Class<?> type() default String.class;
	
	boolean unique() default false;

	boolean nullable() default true;

	boolean insert() default true;

	boolean update() default true;

	String columnDefinition() default "";

	String table() default "";

	int length() default 255;

	int precision() default 0;

	int scale() default 0;
}