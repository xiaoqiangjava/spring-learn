package com.xq.learn.message;

import java.util.Locale;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * @author xiaoqiang
 * @date 2020/6/7 16:35
 */
@Component
public class MessageHelper {
    private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code, Object... args) {
        String message = messageSource.getMessage(code, args, code, Locale.CHINA);
        logger.info("message: " + message);
        return message;
    }

    public <T> void valid(T obj) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(obj);
        for (ConstraintViolation<T> valid : validate) {
            String code = valid.getMessage();
            String message = this.getMessage(code, valid.getPropertyPath());
            throw new RuntimeException(message);
        }
    }
}
