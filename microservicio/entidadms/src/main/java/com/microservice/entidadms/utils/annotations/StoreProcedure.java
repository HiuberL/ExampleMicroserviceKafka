package com.microservice.entidadms.utils.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StoreProcedure {
  String value() default "";
  String name() default "";
  String schema() default "";
  String catalog() default "";  
}
