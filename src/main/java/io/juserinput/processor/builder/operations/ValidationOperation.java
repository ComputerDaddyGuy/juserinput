package io.juserinput.processor.builder.operations;

import io.juserinput.processor.builder.operations.validators.Validator;
import io.juserinput.processor.errors.InputProcessorError;
import io.juserinput.processor.result.InputProcessorResultBuilder;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
final class ValidationOperation<T> implements Operation<T> {

	private final @NonNull Validator<T> validator;

	@Override
	public void operate(InputProcessorResultBuilder<?, T> resultBuilder) {
		var input = resultBuilder.getCurrentInput();
		if (!validator.isValid(input)) {
			resultBuilder.addError(InputProcessorError.newError(input.getName(), validator.errorMessage(input)));
		}
	}

}