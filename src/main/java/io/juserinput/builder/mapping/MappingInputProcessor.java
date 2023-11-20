package io.juserinput.builder.mapping;

import java.util.Objects;

import io.juserinput.Input;
import io.juserinput.InputProcessor;
import io.juserinput.result.InputProcessorResult;
import io.juserinput.result.InputProcessorResult.InputProcessorErrorResult;
import io.juserinput.result.InputProcessorResult.InputProcessorValidResult;

public class MappingInputProcessor<IN, OUT, NEW_OUT> implements InputProcessor<IN, NEW_OUT> {

	private final InputProcessor<IN, OUT> priorProcessor;
	private final MapFunction<OUT, NEW_OUT> mappingFunction;

	public MappingInputProcessor(InputProcessor<IN, OUT> priorProcessor, MapFunction<OUT, NEW_OUT> mappingFunction) {
		super();
		this.priorProcessor = Objects.requireNonNull(priorProcessor, "Prior processor cannot be null");
		this.mappingFunction = Objects.requireNonNull(mappingFunction, "Mapping function cannot be null");
	}

	@Override
	public InputProcessorResult<NEW_OUT> process(Input<IN> input) {
		var interResult = priorProcessor.process(input);

		if (interResult.hasError()) {
			return new InputProcessorErrorResult<>(input, interResult.getErrors());
		}

		try {
			NEW_OUT outValue = interResult.asOptional().map(mappingFunction).orElse(null);
			return new InputProcessorValidResult<>(input.getName(), outValue);
		} catch (Exception e) {
			return new InputProcessorErrorResult<>(input, input.getName() + " is not a valid " + mappingFunction.getNewClassName());
		}
	}

}
