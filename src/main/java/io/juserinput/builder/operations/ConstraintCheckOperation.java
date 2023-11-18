package io.juserinput.builder.operations;

import io.juserinput.builder.operations.constraints.Constraint;
import io.juserinput.errors.InputProcessorError;
import io.juserinput.result.InputProcessorResultBuilder;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
final class ConstraintCheckOperation<T> implements Operation<T> {

	private final @NonNull Constraint<T> constraint;

	@Override
	public void operate(InputProcessorResultBuilder<T> resultBuilder) {
		var input = resultBuilder.getCurrentInput();
		if (!constraint.check(input)) {
			resultBuilder.addError(InputProcessorError.newError(input.getName(), constraint.failMessage(input)));
		}
	}

}