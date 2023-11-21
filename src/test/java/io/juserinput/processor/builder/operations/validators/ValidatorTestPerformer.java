package io.juserinput.processor.builder.operations.validators;

import java.util.function.Consumer;
import java.util.function.Function;

import io.juserinput.processor.builder.operations.InputOperationProcessorBuilder;
import io.juserinput.processor.result.InputProcessorResultAssert;

/**
 * Utility class to perform simple validator tests.
 */
public class ValidatorTestPerformer {

	public static <IN, T, B extends InputOperationProcessorBuilder<?, IN, T>> void performValidTest(B builder, Function<B, B> validatorSetter, IN value, String inputName) {
		performValidationTest(
			builder, validatorSetter, value, inputName,
			resultAssert -> resultAssert.hasNoError()
		);
	}

	public static <IN, T, B extends InputOperationProcessorBuilder<?, IN, T>> void performErrorTest(
		B builder, Function<B, B> validatorSetter, IN value, String inputName, String expectedErrorMsg
	) {
		performValidationTest(
			builder, validatorSetter, value, inputName,
			resultAssert -> resultAssert.containsExactlyErrorMessages(expectedErrorMsg)
		);
	}

	private static <IN, T, B extends InputOperationProcessorBuilder<?, IN, T>> void performValidationTest(
		B builder, Function<B, B> validatorSetter, IN value, String inputName, Consumer<InputProcessorResultAssert<T>> assertionConsumer
	) {
		var proc = validatorSetter.apply(builder).build();
		var actual = proc.process(inputName, value);
		assertionConsumer.accept(InputProcessorResultAssert.assertThat(actual));
	}

}
