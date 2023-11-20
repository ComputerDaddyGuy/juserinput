package io.juserinput.builder.operations.types;

import io.juserinput.InputProcessor;
import io.juserinput.builder.InputProcessorBuilder;

public class StringInputProcessorBuilder<IN> extends AbstractStringInputProcessorBuilder<StringInputProcessorBuilder<IN>, IN> {

	public StringInputProcessorBuilder(InputProcessor<IN, String> previous) {
		super(previous, StringInputProcessorBuilder.class);
	}

	public static StringInputProcessorBuilder<String> newInstance() {
		return new StringInputProcessorBuilder<>(InputProcessorBuilder.noOp(String.class).build());
	}

}
