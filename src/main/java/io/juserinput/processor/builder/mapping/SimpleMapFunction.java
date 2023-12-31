package io.juserinput.processor.builder.mapping;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SimpleMapFunction<OUT, NEW_OUT> implements MapFunction<OUT, NEW_OUT> {

	private final @NonNull Class<NEW_OUT> newClass;
	private final @NonNull Function<OUT, NEW_OUT> delegate;

	@Override
	public Class<NEW_OUT> getNewClass() {
		return newClass;
	}

	@Override
	public NEW_OUT apply(OUT value) {
		return delegate.apply(value);
	}

}
