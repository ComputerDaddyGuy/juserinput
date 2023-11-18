package io.juserinput;

import io.juserinput.builder.operations.ObjectInputProcessorBuilder;
import io.juserinput.builder.types.IntegerInputProcessorBuilder;
import io.juserinput.builder.types.StringInputProcessorBuilder;
import io.juserinput.result.InputProcessorResult;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @param <IN>
 * @param <OUT>
 */
public interface InputProcessor<IN, OUT> {

	// -----------------------------------------------------------------------------------------------------------

	public default InputProcessorResult<OUT> process(@Nullable IN value, @Nonnull String attrName) {
		return process(Input.of(value, attrName));
	}

	public InputProcessorResult<OUT> process(@Nonnull Input<IN> input);

	// ===========================================================================================================

	public static <T> ObjectInputProcessorBuilder<T> forClass(Class<T> clazz) {
		return new ObjectInputProcessorBuilder<>(clazz);
	}

	public static StringInputProcessorBuilder forString() {
		return new StringInputProcessorBuilder();
	}

	public static IntegerInputProcessorBuilder forInteger() {
		return new IntegerInputProcessorBuilder();
	}

}
