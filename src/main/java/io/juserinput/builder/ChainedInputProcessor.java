package io.juserinput.builder;

import java.util.stream.Stream;

import io.juserinput.Input;
import io.juserinput.InputProcessor;
import io.juserinput.result.InputProcessorResult;
import io.juserinput.result.InputProcessorResult.InputProcessorErrorResult;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ChainedInputProcessor<IN, OUT, NEW_OUT> implements InputProcessor<IN, NEW_OUT> {

	private final @NonNull InputProcessor<IN, OUT> first;
	private final @NonNull InputProcessor<OUT, NEW_OUT> second;

	@Override
	public InputProcessorResult<NEW_OUT> process(Input<IN> input) {
		var resultOut = first.process(input);
		var resultNewOut = second.process(resultOut.asInput());

		if (resultOut.hasError() || resultNewOut.hasError()) {
			return new InputProcessorErrorResult<>(input, Stream.concat(resultOut.getErrors().stream(), resultNewOut.getErrors().stream()).toList());
		}

		return resultNewOut;
	}

}
