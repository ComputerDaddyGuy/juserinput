package io.juserinput.builder.mapping;

import io.juserinput.InputProcessor;
import io.juserinput.builder.operations.InputOperationProcessorBuilder;

public interface InputProcessorMapper<IN, OUT, B extends InputOperationProcessorBuilder<?, IN, NEW_OUT>, NEW_OUT> {

	public B map(InputProcessor<IN, OUT> inputProcessor);

}
