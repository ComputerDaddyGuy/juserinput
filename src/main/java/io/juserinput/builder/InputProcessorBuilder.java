package io.juserinput.builder;

import io.juserinput.InputProcessor;

/**
 * @param <IN>
 * @param <OUT>
 */
@FunctionalInterface
public interface InputProcessorBuilder<SELF extends InputProcessorBuilder<SELF, IN, OUT>, IN, OUT> {

	// -----------------------------------------------------------------------------------------------------------

	public InputProcessor<IN, OUT> build();

	// -----------------------------------------------------------------------------------------------------------

	public static <S extends InputProcessorBuilder<S, T, T>, T> InputProcessorBuilder<S, T, T> noOp(Class<T> treatedType) {
		return () -> new NoOpInputProcessor<>(treatedType);
	}

}
