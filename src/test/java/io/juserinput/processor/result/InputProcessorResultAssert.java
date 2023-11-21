package io.juserinput.processor.result;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import io.juserinput.processor.errors.InputProcessorError;

public class InputProcessorResultAssert<IN, T> extends AbstractAssert<InputProcessorResultAssert<IN, T>, InputProcessorResult<IN, T>> {

	protected InputProcessorResultAssert(InputProcessorResult<IN, T> actual) {
		super(actual, InputProcessorResultAssert.class);
	}

	public static <IN, T> InputProcessorResultAssert<IN, T> assertThat(InputProcessorResult<IN, T> actual) {
		return new InputProcessorResultAssert<>(actual);
	}

	public InputProcessorResultAssert<IN, T> hasNoError() {
		if (actual.hasError()) {
			failWithMessage("Expected to have no error, but has %d: %s", actual.getErrors().size(), actual.getErrors());
		}
		return myself;
	}

	public InputProcessorResultAssert<IN, T> hasError() {
		if (!actual.hasError()) {
			failWithMessage("Expected to have error, but has none");
		}
		return myself;
	}

	public InputProcessorResultAssert<IN, T> containsExactlyErrorMessages(String... errorsMessages) {
		Assertions.assertThat(actual.getErrors())
			.map(InputProcessorError::getMessage)
			.containsExactly(errorsMessages);
		return myself;
	}

}
