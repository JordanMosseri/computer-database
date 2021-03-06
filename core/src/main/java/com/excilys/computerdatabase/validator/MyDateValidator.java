package com.excilys.computerdatabase.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.excilys.computerdatabase.util.Utils;

/**
 * Used by Spring validator
 * @author Jordan Mosseri
 *
 */
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
