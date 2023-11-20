package io.juserinput.builder.mapping;

import java.util.function.Function;

import io.juserinput.builder.InputProcessorBuilder;
import io.juserinput.builder.operations.InputOperationProcessorBuilder;
import io.juserinput.result.InputProcessorResultAssert;
import io.juserinput.result.InputProcessorResultBuilder;

/**
 * Utility class to perform simple transformer tests.
 */
public class MappingTestPerformer {

	public static <IN, T, B extends InputOperationProcessorBuilder<?, IN, IN>> void performMappingTest(
		B builder, Function<B, InputProcessorBuilder<?, IN, T>> mapperSetter, IN value, T expectedValue
	) {
		var proc = mapperSetter.apply(builder).build();

		var actual = proc.process("myAttr", value);

		var expected = InputProcessorResultBuilder
			.newInstance("myAttr", expectedValue)
			.build();

		InputProcessorResultAssert.assertThat(actual)
			.hasNoError()
			.isEqualTo(expected);
	}

}
