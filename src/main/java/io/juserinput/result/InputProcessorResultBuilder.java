package io.juserinput.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.juserinput.Input;
import io.juserinput.errors.InputProcessorError;
import io.juserinput.result.InputProcessorResult.InputProcessorErrorResult;
import io.juserinput.result.InputProcessorResult.InputProcessorValidResult;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class InputProcessorResultBuilder<T> {

	private final @Nonnull Input<T> input;
	private final @Nonnull String inputName;
	private @Nullable T currentValue;
	private final List<InputProcessorError> errors;

	private InputProcessorResultBuilder(Input<T> input) {
		this.input = Objects.requireNonNull(input, "input cannot be null");
		this.currentValue = input.getValue();
		this.inputName = input.getName();
		this.errors = new ArrayList<>();
	}

	public static <T> InputProcessorResultBuilder<T> newInstance(Input<T> input) {
		return new InputProcessorResultBuilder<>(input);
	}

	public static <T> InputProcessorResultBuilder<T> newInstance(String inputName, T inputValue) {
		return new InputProcessorResultBuilder<>(Input.of(inputName, inputValue));
	}

	public Input<T> getInitialInput() {
		return input;
	}

	public Input<T> getCurrentInput() {
		return Input.of(inputName, currentValue);
	}

	public T getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(T newValue) {
		this.currentValue = newValue;
	}

	public String getInputName() {
		return inputName;
	}

	public InputProcessorResultBuilder<T> addError(InputProcessorError error) {
		this.errors.add(error);
		return this;
	}

	public InputProcessorResult<T> build() {
		return errors.isEmpty()
			? new InputProcessorValidResult<>(inputName, currentValue)
			: new InputProcessorErrorResult<>(input, errors);
	}

}