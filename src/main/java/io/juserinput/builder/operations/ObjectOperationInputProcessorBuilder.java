package io.juserinput.builder.operations;

import io.juserinput.InputProcessor;
import io.juserinput.builder.InputProcessorBuilder;

/**
 * @param <IN>
 * @param <OUT>
 */
public class ObjectOperationInputProcessorBuilder<IN, T> extends AbstractObjectOperationInputProcessorBuilder<ObjectOperationInputProcessorBuilder<IN, T>, IN, T> {

	protected ObjectOperationInputProcessorBuilder(InputProcessor<IN, T> previous) {
		super(previous, ObjectOperationInputProcessorBuilder.class);
	}

	public static <T> ObjectOperationInputProcessorBuilder<T, T> newInstance(Class<T> clazz) {
		return new ObjectOperationInputProcessorBuilder<>(InputProcessorBuilder.noOp(clazz).build());
	}

}
