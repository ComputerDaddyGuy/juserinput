package io.juserinput.processor.result;

import java.util.List;
import java.util.Optional;

import io.juserinput.processor.Input;
import io.juserinput.processor.InputProcessorException;
import io.juserinput.processor.errors.InputProcessorError;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InputProcessorResult<IN, OUT> {

	private final @Nonnull Input<IN> input;
	private final @NonNull List<InputProcessorError> errors;
	private final @Nullable OUT value;

	public static <IN, OUT> InputProcessorResult<IN, OUT> valid(@Nonnull Input<IN> input, @Nullable OUT value) {
		return new InputProcessorResult<IN, OUT>(input, List.of(), value);
	}

	public static <IN, OUT> InputProcessorResult<IN, OUT> error(@Nonnull Input<IN> input, List<InputProcessorError> errors) {
		return new InputProcessorResult<>(input, errors, null);
	}

	public static <IN, OUT> InputProcessorResult<IN, OUT> error(@Nonnull Input<IN> input, InputProcessorError error) {
		return error(input, List.of(error));
	}

	public static <IN, OUT> InputProcessorResult<IN, OUT> error(@Nonnull Input<IN> input, String errorMessage) {
		return error(input, InputProcessorError.newError(input.getName(), errorMessage));
	}

	public boolean isValid() {
		return errors.isEmpty();
	}

	public boolean hasError() {
		return !isValid();
	}

	public Input<OUT> asInput() {
		return Input.of(getName(), value);
	}

	public String getName() {
		return input.getName();
	}

	public @Nonnull Optional<OUT> asOptional() {
		if (hasError()) {
			throw new InputProcessorException(getName(), errors);
		}
		return Optional.ofNullable(value);
	}

	public @Nonnull OUT asRequired() {
		return asOptional().orElseThrow(() -> new InputProcessorNoValueException(getName()));
	}

	public @Nonnull List<InputProcessorError> getErrors() {
		return errors;
	}

}
