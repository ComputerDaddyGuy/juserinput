package io.juserinput.builder.operations.validators;

import java.util.function.Function;
import java.util.function.Predicate;

import io.juserinput.Input;

public interface Validator<T> {

	public boolean isValid(Input<T> input);

	public String errorMessage(Input<T> input);

	public static <T> Validator<T> validator(Predicate<T> validationPredicate, String errorMessage) {
		return validator(validationPredicate, input -> errorMessage);
	}

	public static <T> Validator<T> validator(Predicate<T> validationPredicate, Function<Input<T>, String> errorMessage) {
		return new SimpleValidator<>(validationPredicate, errorMessage);
	}

}
