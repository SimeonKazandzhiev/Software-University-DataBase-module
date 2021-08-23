package softuni.exam.util;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorUtilImpl implements ValidatorUtil {

    private final Validator validator;

    public ValidatorUtilImpl() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E entity) {
        //TODO Implement me
        throw  new NotYetImplementedException("Not implemented");
    }


}
