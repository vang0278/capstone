package org.vang.john.hotelbooking.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		this.firstFieldName = constraintAnnotation.first();
		this.secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try {
			final Object firstObject = BeanUtils.getProperty(value, firstFieldName);
			final Object secondObject = BeanUtils.getProperty(value, secondFieldName);
			return firstObject == null && secondObject == null
					|| firstObject != null && firstObject.equals(secondObject);
		} catch (final Exception ignore) {
		}
		return true;// ?? shouldn't default be false
	}

}
