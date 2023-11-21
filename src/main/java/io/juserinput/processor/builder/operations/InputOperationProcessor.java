package io.juserinput.processor.builder.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.juserinput.processor.Input;
import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.result.InputProcessorResult;
import io.juserinput.processor.result.InputProcessorResultBuilder;
import jakarta.annotation.Nonnull;

/**
 * @param <IN>
 * @param <OUT>
 */
class InputOperationProcessor<T> implements InputProcessor<T, T> {

	private final List<Operation<T>> operations;

	protected InputOperationProcessor(List<Operation<T>> operations) {
		this.operations = Collections.unmodifiableList(new ArrayList<>(operations));
	}

	@Override
	public InputProcessorResult<T> process(@Nonnull Input<T> input) {
		Objects.requireNonNull(input, "input to process cannot be null");
		var result = InputProcessorResultBuilder.newInstance(input);

		for (Operation<T> op : operations) {
			if (result.getCurrentValue() != null) {
				op.operate(result);
			} else {
				break;
			}
		}

		return result.build();
	}

}
