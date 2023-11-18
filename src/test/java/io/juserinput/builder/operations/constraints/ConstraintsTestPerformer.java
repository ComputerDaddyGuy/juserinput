package io.juserinput.builder.operations.constraints;

import java.util.function.Consumer;
import java.util.function.Function;

import io.juserinput.builder.InputProcessorBuilder;
import io.juserinput.result.InputProcessorResultAssert;

/**
 * Utility class to perform simple constraint tests.
 */
public class ConstraintsTestPerformer {

	public static <T, B extends InputProcessorBuilder<?, T, T, T>> void performValidTest(B builder, Function<B, B> constraintSetter, T value, String inputName) {
		performTest(
			builder, constraintSetter, value, inputName,
			resultAssert -> resultAssert.hasNoError()
		);
	}

	public static <T, B extends InputProcessorBuilder<?, T, T, T>> void performErrorTest(
		B builder, Function<B, B> constraintSetter, T value, String inputName, String expectedErrorMsg
	) {
		performTest(
			builder, constraintSetter, value, inputName,
			resultAssert -> resultAssert.containsExactlyErrorMessages(expectedErrorMsg)
		);
	}

	private static <T, B extends InputProcessorBuilder<?, T, T, T>> void performTest(
		B builder, Function<B, B> contraintSetter, T value, String inputName, Consumer<InputProcessorResultAssert<T>> assertionConsumer
	) {
		var proc = contraintSetter.apply(builder).build();
		var result = proc.process(value, inputName);
		assertionConsumer.accept(InputProcessorResultAssert.assertThat(result));
	}

}
