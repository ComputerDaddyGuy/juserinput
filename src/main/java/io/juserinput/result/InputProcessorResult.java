package io.juserinput.result;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.juserinput.Input;
import io.juserinput.InputProcessorException;
import io.juserinput.errors.InputProcessorError;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

public sealed interface InputProcessorResult<OUT> {

	public boolean isValid();

	public default boolean hasError() {
		return !isValid();
	}

	public Input<OUT> asInput();

	public String getName();

	public @Nonnull Optional<OUT> asOptional();

	public @Nonnull OUT asRequired();

	public @Nonnull List<InputProcessorError> getErrors();

	@ToString
	@EqualsAndHashCode
	public final class InputProcessorValidResult<OUT> implements InputProcessorResult<OUT> {

		private final @Nonnull String inputName;
		private final @Nullable OUT value;

		public InputProcessorValidResult(@Nonnull String inputName, @Nullable OUT value) {
			this.value = value;
			this.inputName = Objects.requireNonNull(inputName, "inputName cannot be null");
		}

		public InputProcessorValidResult(@Nonnull Input<OUT> input) {
			this(input.getName(), input.getValue());
		}

		@Override
		public String getName() {
			return inputName;
		}

		@Override
		public boolean isValid() {
			return true;
		}

		@Override
		public @Nonnull Optional<OUT> asOptional() {
			return Optional.ofNullable(value);
		}

		@Override
		public @Nonnull OUT asRequired() {
			return asOptional().orElseThrow(() -> new InputProcessorNoValueException(inputName));
		}

		@Override
		public List<InputProcessorError> getErrors() {
			return List.of();
		}

		@Override
		public Input<OUT> asInput() {
			return Input.of(getName(), value);
		}

	}

	@ToString
	@EqualsAndHashCode
	public final class InputProcessorErrorResult<IN, OUT> implements InputProcessorResult<OUT> {

		private final @Nonnull Input<IN> input;
		private final @NonNull List<InputProcessorError> errors;

		public InputProcessorErrorResult(@Nonnull Input<IN> input, List<InputProcessorError> errors) {
			this.input = Objects.requireNonNull(input, "input cannot be null");
			this.errors = Objects.requireNonNull(errors, "errors cannot be null");
		}

		public InputProcessorErrorResult(@Nonnull Input<IN> input, InputProcessorError error) {
			this(input, List.of(error));
		}

		public InputProcessorErrorResult(@Nonnull Input<IN> input, String errorMessage) {
			this(input, InputProcessorError.newError(input.getName(), errorMessage));
		}

		public Input<IN> getInput() {
			return input;
		}

		@Override
		public String getName() {
			return input.getName();
		}

		@Override
		public boolean isValid() {
			return false;
		}

		@Override
		public @Nonnull Optional<OUT> asOptional() {
			throw new InputProcessorException(getName(), errors);
		}

		@Override
		public @Nonnull OUT asRequired() {
			throw new InputProcessorException(getName(), errors);
		}

		@Override
		public List<InputProcessorError> getErrors() {
			return errors;
		}

		@Override
		public Input<OUT> asInput() {
			return Input.of(getName(), null);
		}

	}

}
