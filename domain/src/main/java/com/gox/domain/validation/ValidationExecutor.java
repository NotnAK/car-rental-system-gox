package com.gox.domain.validation;

import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;

import java.util.List;
import java.util.function.Function;

public class ValidationExecutor {

    /**
     * Runs through all the rules, and if there are errors, throws an exception, which is thrown by exceptionFactory.
     *
     * @param ctx               validation context
     * @param rules             list of ValidationRule<Ctx> rules
     * @param exceptionFactory  function from an error message to a specific RuntimeException
     * @param <Ctx>             type of context
     */
    public static <Ctx> void validateOrThrow(
            Ctx ctx,
            List<ValidationRule<Ctx>> rules,
            Function<String, ? extends RuntimeException> exceptionFactory
    ) {
        ValidationResult vr = new ValidationResult();
        for (ValidationRule<Ctx> rule : rules) {
            rule.validate(ctx, vr);
        }
        if (vr.hasErrors()) {
            String msg = vr.getCombinedMessage();
            throw exceptionFactory.apply(msg);
        }
    }
}
