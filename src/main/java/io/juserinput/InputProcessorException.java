package io.juserinput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.juserinput.errors.InputProcessorError;
import jakarta.annotation.Nonnull;

public class InputProcessorException extends IllegalArgumentException {

	private final @Nonnull String inputName;
	private final @Nonnull List<InputProcessorError> errors;

	public InputProcessorException(@Nonnull String inputName, @Nonnull List<InputProcessorError> errors) {
		super("Invalid value for " + inputName + ":\n" + toExceptionMessage(errors));
		this.inputName = inputName;
		this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
	}

	public InputProcessorException(@Nonnull String inputName, @Nonnull InputProcessorError error) {
		this(inputName, List.of(error));
	}

	public InputProcessorException(@Nonnull String inputName, @Nonnull String errorMessage) {
		this(inputName, InputProcessorError.newError(inputName, errorMessage));
	}

	private static String toExceptionMessage(List<InputProcessorError> errors) {
		return errors.stream()
			.map(InputProcessorError::getMessage)
			.collect(Collectors.joining("\n"));
	}

}
