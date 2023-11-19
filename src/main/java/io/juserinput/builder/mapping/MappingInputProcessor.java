package io.juserinput.builder.mapping;

import java.util.Objects;

import io.juserinput.Input;
import io.juserinput.InputProcessor;
import io.juserinput.result.InputProcessorResult;
import io.juserinput.result.InputProcessorResult.InputProcessorErrorResult;
import io.juserinput.result.InputProcessorResult.InputProcessorValidResult;

class MappingInputProcessor<IN, INTER, OUT> implements InputProcessor<IN, OUT> {

	private final InputProcessor<IN, INTER> priorProcessor;
	private final Mapper<INTER, OUT> mappingFunction;

	public MappingInputProcessor(InputProcessor<IN, INTER> priorProcessor, Mapper<INTER, OUT> mappingFunction) {
		super();
		this.priorProcessor = Objects.requireNonNull(priorProcessor, "Prior processor cannot be null");
		this.mappingFunction = Objects.requireNonNull(mappingFunction, "Mapping function cannot be null");
	}

	@Override
	public InputProcessorResult<OUT> process(Input<IN> input) {
		var interResult = priorProcessor.process(input);

		if (interResult.hasError()) {
			return new InputProcessorErrorResult<>(input, interResult.getErrors());
		}

		try {
			OUT outValue = interResult.asOptional().map(mappingFunction).orElse(null);
			return new InputProcessorValidResult<>(input.getName(), outValue);
		} catch (Exception e) {
			return new InputProcessorErrorResult<>(input, input.getName() + " is not a valid " + mappingFunction.getNewClassName());
		}
	}

}
