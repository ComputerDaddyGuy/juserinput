package io.juserinput.builder.operations.constraints;

import java.util.function.Function;
import java.util.function.Predicate;

import io.juserinput.Input;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SimpleConstraint<T> implements Constraint<T> {

	private final @NonNull Predicate<T> constraintPredicate;
	private final @NonNull Function<Input<T>, String> constraintMessage;

	@Override
	public boolean check(Input<T> input) {
		return constraintPredicate.test(input.getValue());
	}

	@Override
	public String failMessage(Input<T> input) {
		return constraintMessage.apply(input);
	}

}
