package io.juserinput.builder.operations;

import java.util.function.Function;
import java.util.function.Predicate;

import io.juserinput.Input;
import io.juserinput.builder.InputProcessorBuilder;
import io.juserinput.builder.operations.validators.Validator;

/**
 * @param <IN>
 * @param <OUT>
 */
public interface InputOperationProcessorBuilder<SELF extends InputOperationProcessorBuilder<SELF, IN, T>, IN, T>
	extends InputProcessorBuilder<SELF, IN, T> {

	// -----------------------------------------------------------------------------------------------------------

	public SELF transform(Function<T, T> transformer);

	// -----------------------------------------------------------------------------------------------------------

	public default SELF validate(Predicate<T> validationPredicate, String errorMessage) {
		return validate(Validator.validator(validationPredicate, input -> errorMessage));
	}

	public default SELF validate(Predicate<T> validationPredicate, Function<Input<T>, String> errorMessage) {
		return validate(Validator.validator(validationPredicate, errorMessage));
	}

	public SELF validate(Validator<T> validator);

	// -----------------------------------------------------------------------------------------------------------

}
