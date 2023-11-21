package io.juserinput.processor.result;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import io.juserinput.processor.errors.InputProcessorError;
import io.juserinput.processor.result.InputProcessorResult;

public class InputProcessorResultAssert<T> extends AbstractAssert<InputProcessorResultAssert<T>, InputProcessorResult<T>> {

	protected InputProcessorResultAssert(InputProcessorResult<T> actual) {
		super(actual, InputProcessorResultAssert.class);
	}

	public static <T> InputProcessorResultAssert<T> assertThat(InputProcessorResult<T> actual) {
		return new InputProcessorResultAssert<>(actual);
	}

	public InputProcessorResultAssert<T> hasNoError() {
		if (actual.hasError()) {
			failWithMessage("Expected to have no error, but has %d: %s", actual.getErrors().size(), actual.getErrors());
		}
		return myself;
	}

	public InputProcessorResultAssert<T> hasError() {
		if (!actual.hasError()) {
			failWithMessage("Expected to have error, but has none");
		}
		return myself;
	}

	public InputProcessorResultAssert<T> containsExactlyErrorMessages(String... errorsMessages) {
		Assertions.assertThat(actual.getErrors())
			.map(InputProcessorError::getMessage)
			.containsExactly(errorsMessages);
		return myself;
	}

}
