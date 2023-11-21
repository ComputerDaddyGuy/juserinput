package io.juserinput.processor.builder.operations.transformers;

import java.util.function.Function;

import io.juserinput.processor.builder.operations.InputOperationProcessorBuilder;
import io.juserinput.processor.result.InputProcessorResultAssert;
import io.juserinput.processor.result.InputProcessorResultBuilder;

/**
 * Utility class to perform simple transformer tests.
 */
public class TransformerTestPerformer {

	public static <IN, T, B extends InputOperationProcessorBuilder<?, IN, T>> void performTransformationTest(
		B builder, Function<B, B> validatorSetter, IN value, T expectedValue
	) {
		var proc = validatorSetter.apply(builder).build();

		var actual = proc.process("myAttr", value);

		var expected = InputProcessorResultBuilder
			.newInstance("myAttr", expectedValue)
			.build();

		InputProcessorResultAssert.assertThat(actual)
			.hasNoError()
			.isEqualTo(expected);
	}

}
