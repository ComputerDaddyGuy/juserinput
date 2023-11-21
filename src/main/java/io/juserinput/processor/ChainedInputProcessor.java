package io.juserinput.processor;

import java.util.stream.Stream;

import io.juserinput.processor.result.InputProcessorResult;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
class ChainedInputProcessor<IN, OUT, NEW_OUT> implements InputProcessor<IN, NEW_OUT> {

	private final @NonNull InputProcessor<IN, OUT> first;
	private final @NonNull InputProcessor<OUT, NEW_OUT> second;

	@Override
	public InputProcessorResult<IN, NEW_OUT> process(Input<IN> input) {
		var resultOut = first.process(input);
		var resultNewOut = second.process(resultOut.asInput());

		if (resultOut.hasError() || resultNewOut.hasError()) {
			return InputProcessorResult.error(input, Stream.concat(resultOut.getErrors().stream(), resultNewOut.getErrors().stream()).toList());
		}

		return InputProcessorResult.valid(input, resultNewOut.asOptional().orElse(null));
	}

}
