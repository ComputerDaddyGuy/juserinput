package io.juserinput.builder.operations;

/**
 * @param <IN>
 * @param <OUT>
 */
public class ObjectInputProcessorBuilder<T> extends AbstractObjectInputProcessorBuilder<ObjectInputProcessorBuilder<T>, T> {

	public ObjectInputProcessorBuilder(Class<T> clazz) {
		super(clazz, ObjectInputProcessorBuilder.class);
	}

}
