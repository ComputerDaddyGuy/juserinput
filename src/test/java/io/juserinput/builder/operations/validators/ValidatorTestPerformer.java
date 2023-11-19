package io.juserinput.builder.operations.validators;

import java.util.function.Consumer;
import java.util.function.Function;

import io.juserinput.builder.operations.InputOperationProcessorBuilder;
import io.juserinput.result.InputProcessorResultAssert;

/**
 * Utility class to perform simple validator tests.
 */
public class ValidatorTestPerformer {

	public static <T, B extends InputOperationProcessorBuilder<?, T, T>> void performValidTest(B builder, Function<B, B> validatorSetter, T value, String inputName) {
		performValidationTest(
			builder, validatorSetter, value, inputName,
			resultAssert -> resultAssert.hasNoError()
		);
	}

	public static <T, B extends InputOperationProcessorBuilder<?, T, T>> void performErrorTest(
		B builder, Function<B, B> validatorSetter, T value, String inputName, String expectedErrorMsg
	) {
		performValidationTest(
			builder, validatorSetter, value, inputName,
			resultAssert -> resultAssert.containsExactlyErrorMessages(expectedErrorMsg)
		);
	}

	private static <T, B extends InputOperationProcessorBuilder<?, T, T>> void performValidationTest(
		B builder, Function<B, B> validatorSetter, T value, String inputName, Consumer<InputProcessorResultAssert<T>> assertionConsumer
	) {
		var proc = validatorSetter.apply(builder).build();
		var result = proc.process(inputName, value);
		assertionConsumer.accept(InputProcessorResultAssert.assertThat(result));
	}

}