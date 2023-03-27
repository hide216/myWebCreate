package mySolution.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import com.google.api.client.auth.openidconnect.IdToken.Payload;

import mySolution.validate.NotBlankSizePatternValidator;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotBlankSizePatternValidator.class)
public @interface NotBlankSizePattern {
	//必須の項目
	public String message() default "";
	Class<? extends Payload>[] payload() default{};
    Class<?>[] groups() default {};
	//ここから任意の項目
	String name() default "";
	String pettern() default "";
	boolean isEmail() default false;
	short min() default 0;
	short max() default Short.MAX_VALUE;
}
