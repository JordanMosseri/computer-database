package com.excilys.computerdatabase.validator;

import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.time.DateUtils;

import com.excilys.computerdatabase.util.Utils;

public class MyDateValidator implements ConstraintValidator<MyDate, String> {
	 
    @Override
    public void initialize(MyDate paramA) {
    }
 
    @Override
    public boolean isValid(String string, ConstraintValidatorContext ctx) {
    	
        if(string != null && string.isEmpty()) {
        	return true;
		}
        
        else if(string != null) {
        	if(Utils.isDate(string)) {
            	return true;
            }
        }
        
		//return false if nothing matches the input
        return false;
    }
 
}
