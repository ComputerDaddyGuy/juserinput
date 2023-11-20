package io.juserinput.builder.operations.types;

import io.juserinput.InputProcessor;
import io.juserinput.builder.InputProcessorBuilder;

public class IntegerInputProcessorBuilder<IN>
	extends AbstractIntegerInputProcessorBuilder<IntegerInputProcessorBuilder<IN>, IN> {

	public IntegerInputProcessorBuilder(InputProcessor<IN, Integer> previous) {
		super(previous, IntegerInputProcessorBuilder.class);
	}

	public static <T> IntegerInputProcessorBuilder<Integer> newInstance() {
		return new IntegerInputProcessorBuilder<>(InputProcessorBuilder.noOp(Integer.class).build());
	}

}
