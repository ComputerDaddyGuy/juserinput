package io.juserinput.errors;

import lombok.NonNull;
import lombok.Value;

/**
 * Simple implementation of {@link InputProcessorError}.
 */
@Value
class SimpleInputProcessorError implements InputProcessorError {

	private final @NonNull String inputName;
	private final @NonNull String message;

}
