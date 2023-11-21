package io.juserinput.processor.builder.mapping;

import java.util.Objects;

import io.juserinput.processor.Input;
import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.result.InputProcessorResult;

public class MappingInputProcessor<IN, OUT, NEW_OUT> implements InputProcessor<IN, NEW_OUT> {

	private final InputProcessor<IN, OUT> priorProcessor;
	private final MapFunction<OUT, NEW_OUT> mappingFunction;

	public MappingInputProcessor(InputProcessor<IN, OUT> priorProcessor, MapFunction<OUT, NEW_OUT> mappingFunction) {
		super();
		this.priorProcessor = Objects.requireNonNull(priorProcessor, "Prior processor cannot be null");
		this.mappingFunction = Objects.requireNonNull(mappingFunction, "Mapping function cannot be null");
	}

	@Override
	public InputProcessorResult<IN, NEW_OUT> process(Input<IN> input) {
		var interResult = priorProcessor.process(input);

		if (interResult.hasError()) {
			return InputProcessorResult.error(input, interResult.getErrors());
		}

		try {
			NEW_OUT outValue = interResult.asOptional().map(mappingFunction).orElse(null);
			return InputProcessorResult.valid(input, outValue);
		} catch (Exception e) {
			return InputProcessorResult.error(input, input.getName() + " is not a valid " + mappingFunction.getNewClassName());
		}
	}

}
