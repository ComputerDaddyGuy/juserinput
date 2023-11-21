package io.juserinput.processor;

import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Input<T> {

	private final String name;
	private final T value;

	Input(@Nullable String name, @Nullable T value) {
		super();
		this.value = value;
		this.name = name == null ? "value" : name;
	}

	public static <T> Input<T> of(@Nullable T value) {
		return new Input<T>("value", value);
	}

	public static <T> Input<T> of(@Nullable String name, @Nullable T value) {
		return new Input<T>(name, value);
	}

	public <U> Input<U> withValue(@Nullable U newValue) {
		return new Input<U>(name, newValue);
	}

	public T getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

}
