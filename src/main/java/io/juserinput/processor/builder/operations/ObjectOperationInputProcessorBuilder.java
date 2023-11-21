package io.juserinput.processor.builder.operations;

import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.builder.InputProcessorBuilder;

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
