package io.juserinput.builder.operations.types;

import io.juserinput.InputProcessor;
import io.juserinput.builder.mapping.MapFunction;
import io.juserinput.builder.mapping.MappingInputProcessor;
import io.juserinput.builder.operations.AbstractObjectOperationInputProcessorBuilder;

public abstract class AbstractStringInputProcessorBuilder<SELF extends AbstractStringInputProcessorBuilder<SELF, IN>, IN>
	extends AbstractObjectOperationInputProcessorBuilder<SELF, IN, String> {

	protected AbstractStringInputProcessorBuilder(InputProcessor<IN, String> previous, Class<?> selfType) {
		super(previous, selfType);
	}

	// ===========================================================================================================
	// TRANSFORMERS

	public SELF strip() {
		return transform(value -> value.strip());
	}

	public SELF toUpperCase() {
		return transform(value -> value.toUpperCase());
	}

	// ===========================================================================================================
	// VALIDATORS

	public SELF isNotEmpty() {
		return validate(value -> !value.isEmpty(), input -> input.getName() + " must not be empty");
	}

	public SELF isNotBlank() {
		return validate(value -> !value.isBlank(), input -> input.getName() + " must not be blank");
	}

	public SELF isMaxLength(int maxLength) {
		if (maxLength < 0) {
			throw new IllegalArgumentException("Invalid max length, must be positive or nul: " + maxLength);
		}
		return validate(value -> value.length() <= maxLength, input -> input.getName() + " must be " + maxLength + " long max (currently: " + input.getValue().length() + ")");
	}

	// ===========================================================================================================
	// MAPPERS

	public IntegerInputProcessorBuilder<IN> mapToInteger() {
		MapFunction<String, Integer> mappingFunction = MapFunction.newInstance(Integer.class, Integer::parseInt);
		InputProcessor<IN, Integer> previous = new MappingInputProcessor<>(this.build(), mappingFunction);
		return new IntegerInputProcessorBuilder<>(previous);
	}

}
