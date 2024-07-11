package com.microservice.entidadms.utils.annotations;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface StoreProcedureParam {
    String param() default "";
    String defaultValue() default "";
    int type() default 12;
    boolean isOut() default false;
}
