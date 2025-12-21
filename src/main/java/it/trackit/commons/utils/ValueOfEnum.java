package it.trackit.commons.utils;

import it.trackit.commons.utils.validation.ValueOfEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
         ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValueOfEnum {
  Class<? extends Enum<?>> enumClass();
  String message() default "Must be any valid value of enum {enumClass}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
