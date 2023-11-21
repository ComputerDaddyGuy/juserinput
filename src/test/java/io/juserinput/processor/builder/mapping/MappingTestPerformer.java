package io.juserinput.processor.builder.mapping;

import java.util.function.Consumer;
import java.util.function.Function;

import io.juserinput.processor.builder.InputProcessorBuilder;
import io.juserinput.processor.builder.operations.InputOperationProcessorBuilder;
import io.juserinput.processor.result.InputProcessorResultAssert;
import io.juserinput.processor.result.InputProcessorResultBuilder;

/**
 * Utility class to perform simple transformer tests.
 */
public class MappingTestPerformer {

	public static <IN, T, B extends InputOperationProcessorBuilder<?, IN, IN>> void performErrorMappingTest(
		B builder, Function<B, InputProcessorBuilder<?, IN, T>> mapperSetter, String inputName, IN inputValue, String expectedErrorMsg
	) {
		performMappingTest(
			builder, mapperSetter, inputName, inputValue,
			resultAssert -> resultAssert.hasError().containsExactlyErrorMessages(expectedErrorMsg)
		);
	}

	public static <IN, T, B extends InputOperationProcessorBuilder<?, IN, IN>> void performSuccessMappingTest(
		B builder, Function<B, InputProcessorBuilder<?, IN, T>> mapperSetter, String inputName, IN inputValue, T expectedValue
	) {
		var expected = InputProcessorResultBuilder
			.newInstance(inputName, inputValue, expectedValue)
			.build();
		performMappingTest(
			builder, mapperSetter, inputName, inputValue,
			resultAssert -> resultAssert.hasNoError().isEqualTo(expected)
		);
	}

	private static <IN, T, B extends InputOperationProcessorBuilder<?, IN, IN>> void performMappingTest(
		B builder, Function<B, InputProcessorBuilder<?, IN, T>> mapperSetter, String inputName, IN inputValue, Consumer<InputProcessorResultAssert<IN, T>> assertionConsumer
	) {
		var proc = mapperSetter.apply(builder).build();
		var result = proc.process(inputName, inputValue);
		assertionConsumer.accept(InputProcessorResultAssert.assertThat(result));
	}

}
