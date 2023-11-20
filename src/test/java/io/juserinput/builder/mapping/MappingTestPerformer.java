package io.juserinput.builder.mapping;

import java.util.function.Consumer;
import java.util.function.Function;

import io.juserinput.builder.InputProcessorBuilder;
import io.juserinput.builder.operations.InputOperationProcessorBuilder;
import io.juserinput.result.InputProcessorResultAssert;
import io.juserinput.result.InputProcessorResultBuilder;

/**
 * Utility class to perform simple transformer tests.
 */
public class MappingTestPerformer {

	public static <IN, T, B extends InputOperationProcessorBuilder<?, IN, IN>> void performErrorMappingTest(
		B builder, Function<B, InputProcessorBuilder<?, IN, T>> mapperSetter, String inputName, IN value, String expectedErrorMsg
	) {
		performMappingTest(
			builder, mapperSetter, value, inputName,
			resultAssert -> resultAssert.hasError().containsExactlyErrorMessages(expectedErrorMsg)
		);
	}

	public static <IN, T, B extends InputOperationProcessorBuilder<?, IN, IN>> void performSuccessMappingTest(
		B builder, Function<B, InputProcessorBuilder<?, IN, T>> mapperSetter, String inputName, IN value, T expectedValue
	) {
		var expected = InputProcessorResultBuilder
			.newInstance(inputName, expectedValue)
			.build();
		performMappingTest(
			builder, mapperSetter, value, inputName,
			resultAssert -> resultAssert.hasNoError().isEqualTo(expected)
		);
	}

	private static <IN, T, B extends InputOperationProcessorBuilder<?, IN, IN>> void performMappingTest(
		B builder, Function<B, InputProcessorBuilder<?, IN, T>> mapperSetter, IN value, String inputName, Consumer<InputProcessorResultAssert<T>> assertionConsumer
	) {
		var proc = mapperSetter.apply(builder).build();
		var result = proc.process(inputName, value);
		assertionConsumer.accept(InputProcessorResultAssert.assertThat(result));
	}

}
