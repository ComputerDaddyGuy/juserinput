package io.juserinput.builder.operations.validators;

import java.util.function.Function;
import java.util.function.Predicate;

import io.juserinput.Input;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
class SimpleValidator<T> implements Validator<T> {

	private final @NonNull Predicate<T> validationPredicate;
	private final @NonNull Function<Input<T>, String> errorMessage;

	@Override
	public boolean isValid(Input<T> input) {
		return validationPredicate.test(input.getValue());
	}

	@Override
	public String errorMessage(Input<T> input) {
		return errorMessage.apply(input);
	}

}
