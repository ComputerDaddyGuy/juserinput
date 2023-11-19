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
final class TransformationOperation<T> implements Operation<T> {

	private final @NonNull Function<T, T> transformFunction;

	@Override
	public void operate(InputProcessorResultBuilder<T> resultBuilder) {
		var newValue = transformFunction.apply(resultBuilder.getCurrentValue());
		resultBuilder.setCurrentValue(newValue);
	}

}