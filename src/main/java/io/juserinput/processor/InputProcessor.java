package io.juserinput.processor;

import java.util.Objects;
import java.util.function.Function;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class InputProcessor<I, F> {

	private final InputProcessorPipelineBuilder<I, F> pipeline;

	/* internal */ InputProcessor(InputProcessorPipelineBuilder<I, F> pipeline) {
		Objects.requireNonNull(pipeline, "pipeline cannot be null");
		this.pipeline = pipeline;
	}

	public static <T> InputProcessorPipelineBuilder<T, T> forClass(Class<T> clazz) {
		return new InputProcessorPipelineBuilder<>(Function.identity());
	}

	public InputProcessorResult<F> process(@Nullable I value, @Nonnull String attrName) {
		return process(new Input<>(value, attrName));
	}

	public InputProcessorResult<F> process(@Nonnull Input<I> input) {
		Objects.requireNonNull(input, "input to process cannot be null");
		var inputResult = pipeline.process(input);
		return new InputProcessorResult<>(inputResult);
	}

}
