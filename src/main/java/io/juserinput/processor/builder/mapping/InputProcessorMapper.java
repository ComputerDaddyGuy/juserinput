package io.juserinput.processor.builder.mapping;

import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.builder.operations.InputOperationProcessorBuilder;

public interface InputProcessorMapper<IN, OUT, B extends InputOperationProcessorBuilder<?, IN, NEW_OUT>, NEW_OUT> {

	public B map(InputProcessor<IN, OUT> inputProcessor);

}
