package io.juserinput.processor;

import io.juserinput.processor.builder.ChainedInputProcessor;
import io.juserinput.processor.builder.operations.ObjectOperationInputProcessorBuilder;
import io.juserinput.processor.builder.operations.types.IntegerInputProcessorBuilder;
import io.juserinput.processor.builder.operations.types.StringInputProcessorBuilder;
import io.juserinput.processor.result.InputProcessorResult;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @param <IN>
 * @param <OUT>
 */
public interface InputProcessor<IN, OUT> {

	public default <NEW_OUT> InputProcessor<IN, NEW_OUT> then(InputProcessor<OUT, NEW_OUT> then) {
		return new ChainedInputProcessor<>(this, then);
	}

	// -----------------------------------------------------------------------------------------------------------

	public default InputProcessorResult<OUT> process(@Nonnull String attrName, @Nullable IN value) {
		return process(Input.of(attrName, value));
	}

	public InputProcessorResult<OUT> process(@Nonnull Input<IN> input);

	// ===========================================================================================================

	public static <T> ObjectOperationInputProcessorBuilder<T, T> forClass(Class<T> clazz) {
		return ObjectOperationInputProcessorBuilder.newInstance(clazz);
	}

	public static StringInputProcessorBuilder<String> forString() {
		return StringInputProcessorBuilder.newInstance();
	}

	public static IntegerInputProcessorBuilder<Integer> forInteger() {
		return IntegerInputProcessorBuilder.newInstance();
	}

}
