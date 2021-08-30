package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
public class ValidationUtilImpl implements ValidationUtil {
    @Override
    public <E> boolean isValid(E entity) {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        return validator.validate(entity).isEmpty();
    }
}
