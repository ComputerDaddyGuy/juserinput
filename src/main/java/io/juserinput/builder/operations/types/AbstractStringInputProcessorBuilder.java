package io.juserinput.builder.operations.types;

import java.util.regex.Pattern;

import io.juserinput.InputProcessor;
import io.juserinput.builder.mapping.MapFunction;
import io.juserinput.builder.mapping.MappingInputProcessor;
import io.juserinput.builder.operations.AbstractObjectOperationInputProcessorBuilder;
import io.juserinput.builder.operations.validators.ValidatorErrorMessages;

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

	public SELF toLowerCase() {
		return transform(value -> value.toLowerCase());
	}

	// ===========================================================================================================
	// VALIDATORS

	public SELF isNotEmpty() {
		return validate(
			value -> !value.isEmpty(),
			input -> ValidatorErrorMessages.empty(input.getName())
		);
	}

	public SELF isNotBlank() {
		return validate(
			value -> !value.isBlank(),
			input -> ValidatorErrorMessages.blank(input.getName())
		);
	}

	public SELF isMinLength(int minLength) {
		if (minLength < 0) {
			throw new IllegalArgumentException("Invalid min length: must be positive or null, but is " + minLength);
		}
		return validate(
			value -> minLength <= value.length(),
			input -> ValidatorErrorMessages.minLength(input.getName(), minLength, input.getValue().length())
		);
	}

	public SELF isMaxLength(int maxLength) {
		if (maxLength < 0) {
			throw new IllegalArgumentException("Invalid max length: must be positive or null, but is " + maxLength);
		}
		return validate(
			value -> value.length() <= maxLength,
			input -> ValidatorErrorMessages.maxLength(input.getName(), maxLength, input.getValue().length())
		);
	}

	public SELF isLengthBetween(int minLength, int maxLength) {
		if (minLength < 0 || maxLength < 0) {
			throw new IllegalArgumentException(
				"Invalid min length or max length: must be positive or null, but min length is %d and max length is %d".formatted(minLength, maxLength)
			);
		} else if (minLength > maxLength) {
			throw new IllegalArgumentException("Min length must be greater than max length, but min length is %d and max length is %d".formatted(minLength, maxLength));
		}
		return validate(
			value -> minLength <= value.length() && value.length() <= maxLength,
			input -> ValidatorErrorMessages.lengthBetween(input.getName(), minLength, maxLength, input.getValue().length())
		);
	}

	public SELF matches(Pattern pattern) {
		if (pattern == null) {
			throw new IllegalArgumentException("Pattern is null");
		}
		return validate(
			value -> pattern.matcher(value).matches(),
			input -> ValidatorErrorMessages.pattern(input.getName(), pattern.pattern())
		);
	}

	// ===========================================================================================================
	// MAPPERS

	public IntegerInputProcessorBuilder<IN> mapToInteger() {
		MapFunction<String, Integer> mappingFunction = MapFunction.newInstance(Integer.class, Integer::parseInt);
		InputProcessor<IN, Integer> previous = new MappingInputProcessor<>(this.build(), mappingFunction);
		return new IntegerInputProcessorBuilder<>(previous);
	}

}
