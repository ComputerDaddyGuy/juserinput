package io.juserinput.builder.operations.types;

import io.juserinput.builder.operations.AbstractObjectOperationInputProcessorBuilder;

public abstract class AbstractStringInputProcessorBuilder<SELF extends AbstractStringInputProcessorBuilder<SELF>>
	extends AbstractObjectOperationInputProcessorBuilder<SELF, String> {

	protected AbstractStringInputProcessorBuilder(Class<?> selfType) {
		super(selfType);
	}

	// ===========================================================================================================
	// TRANSFORMERS

	/**
	 * Strips the input string.
	 * 
	 * @return
	 */
	public SELF strip() {
		return transform(value -> value.strip());
	}

	// ===========================================================================================================
	// VALIDATORS

	public SELF isNotEmpty() {
		return validate(value -> !value.isEmpty(), input -> input.getName() + " must not be empty");
	}

	public SELF isNotBlank() {
		return validate(value -> !value.isBlank(), input -> input.getName() + " must not be blank");
	}

	// ===========================================================================================================
	// MAPPERS

//	public IntegerInputProcessorBuilder mapToInteger() {
//		var mapper = new SimpleMapper<String, Integer>(Integer.class, Integer::parseInt);
//		return new IntegerInputProcessorBuilder(mapper);
//	}

}