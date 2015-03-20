package com.excilys.computerdatabase.validator;

import java.lang.annotation.*;

import javax.validation.*;

@Documented
@Constraint(validatedBy = MyDateValidator.class)
@Target( { ElementType.FIELD })
//@Retention(RetentionPolicy.RUNTIME)
@Retention(value=RetentionPolicy.RUNTIME)
public @interface MyDate {
  
      
    String message() default "{MyDate}";
      
    Class<?>[] groups() default {};
      
    Class<? extends Payload>[] payload() default {};
       
}
