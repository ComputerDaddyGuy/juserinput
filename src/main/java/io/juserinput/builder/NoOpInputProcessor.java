package io.juserinput.builder;

import io.juserinput.Input;
import io.juserinput.InputProcessor;
import io.juserinput.result.InputProcessorResult;
import io.juserinput.result.InputProcessorResult.InputProcessorValidResult;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class NoOpInputProcessor<T> implements InputProcessor<T, T> {

	private final @NonNull Class<T> inputType;

	@Override
	public InputProcessorResult<T> process(Input<T> input) {
		return new InputProcessorValidResult<>(input);
	}

}
