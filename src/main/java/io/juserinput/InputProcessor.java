package io.juserinput;

import io.juserinput.builder.ChainedInputProcessor;
import io.juserinput.builder.operations.ObjectOperationInputProcessorBuilder;
import io.juserinput.builder.operations.types.IntegerInputProcessorBuilder;
import io.juserinput.builder.operations.types.StringInputProcessorBuilder;
import io.juserinput.result.InputProcessorResult;
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

	@SuppressWarnings("unused")
	public static <T> ObjectOperationInputProcessorBuilder<T> forClass(Class<T> clazz) {
		return new ObjectOperationInputProcessorBuilder<>();
	}

	public static StringInputProcessorBuilder forString() {
		return new StringInputProcessorBuilder();
	}

	public static IntegerInputProcessorBuilder forInteger() {
		return new IntegerInputProcessorBuilder();
	}

}
