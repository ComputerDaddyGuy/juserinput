package io.juserinput.processor.builder;

import io.juserinput.processor.Input;
import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.result.InputProcessorResult;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class NoOpInputProcessor<T> implements InputProcessor<T, T> {

	private final @NonNull Class<T> inputType;

	@Override
	public InputProcessorResult<T, T> process(Input<T> input) {
		return InputProcessorResult.valid(input, input.getValue());
	}

}
