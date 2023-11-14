package io.juserinput.processor;

import java.util.Objects;
import java.util.Optional;

import jakarta.annotation.Nonnull;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class InputProcessorResult<T> {

	private final @Nonnull Input<T> input;

	public InputProcessorResult(@Nonnull Input<T> input) {
		super();
		this.input = Objects.requireNonNull(input, "input cannot be null");
	}

	public Optional<T> asOptional() {
		return Optional.ofNullable(input.getValue());
	}

	public T asRequired() {
		return asOptional().orElseThrow(() -> new InputProcessorNoValueException(input, "null"));
	}

	public String getName() {
		return input.getName();
	}

}
