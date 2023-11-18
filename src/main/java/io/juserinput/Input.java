package io.juserinput;

import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Input<T> {

	private final T value;
	private final String name;

	Input(@Nullable T value, @Nullable String name) {
		super();
		this.value = value;
		this.name = name == null ? "value" : name;
	}

	public static <T> Input<T> of(@Nullable T value) {
		return new Input<T>(value, "value");
	}

	public static <T> Input<T> of(@Nullable T value, @Nullable String name) {
		return new Input<T>(value, name);
	}

	public <U> Input<U> withValue(@Nullable U newValue) {
		return new Input<U>(newValue, name);
	}

	public T getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

}
