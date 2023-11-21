package io.juserinput.processor.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.juserinput.processor.Input;
import io.juserinput.processor.errors.InputProcessorError;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class InputProcessorResultBuilder<IN, T> {

	private final @Nonnull Input<IN> input;
	private final @Nonnull List<InputProcessorError> errors;
	private @Nullable T currentValue;

	private InputProcessorResultBuilder(Input<IN> input, T currentValue) {
		this.input = Objects.requireNonNull(input, "input cannot be null");
		this.currentValue = currentValue;
		this.errors = new ArrayList<>();
	}

	public static <T> InputProcessorResultBuilder<T, T> newInstance(Input<T> input) {
		return new InputProcessorResultBuilder<>(input, input.getValue());
	}

	public static <IN, T> InputProcessorResultBuilder<IN, T> newInstance(Input<IN> input, T currentValue) {
		return new InputProcessorResultBuilder<>(input, currentValue);
	}

	public static <IN, T> InputProcessorResultBuilder<IN, T> newInstance(String inputName, IN inputValue, T currentValue) {
		return newInstance(Input.of(inputName, inputValue), currentValue);
	}

	public Input<IN> getInitialInput() {
		return input;
	}

	public Input<T> getCurrentInput() {
		return Input.of(getInputName(), currentValue);
	}

	public T getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(T newValue) {
		this.currentValue = newValue;
	}

	public String getInputName() {
		return input.getName();
	}

	public InputProcessorResultBuilder<IN, T> addError(InputProcessorError error) {
		this.errors.add(error);
		return this;
	}

	public InputProcessorResult<IN, T> build() {
		return errors.isEmpty()
			? InputProcessorResult.valid(input, currentValue)
			: InputProcessorResult.error(input, errors);
	}

}