package io.juserinput.builder.operations;

import java.util.function.Function;

import io.juserinput.result.InputProcessorResultBuilder;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * 
 * 
 *
 */
@AllArgsConstructor
final class SanitizationOperation<T> implements Operation<T> {

	private final @NonNull Function<T, T> sanitizeFunction;

	@Override
	public void operate(InputProcessorResultBuilder<T> resultBuilder) {
		var newValue = sanitizeFunction.apply(resultBuilder.getCurrentValue());
		resultBuilder.setCurrentValue(newValue);
	}

}