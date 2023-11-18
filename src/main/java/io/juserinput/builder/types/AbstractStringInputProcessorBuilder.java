package io.juserinput.builder.types;

import io.juserinput.builder.operations.AbstractObjectInputProcessorBuilder;

public abstract class AbstractStringInputProcessorBuilder<SELF extends AbstractStringInputProcessorBuilder<SELF>> extends AbstractObjectInputProcessorBuilder<SELF, String> {

	protected AbstractStringInputProcessorBuilder(Class<?> selfType) {
		super(String.class, selfType);
	}

	// ===========================================================================================================
	// SANITIZERS

	/**
	 * Strips the input string.
	 * 
	 * @return
	 */
	public SELF strip() {
		return sanitize(s -> s.strip());
	}

	// ===========================================================================================================
	// CONSTRAINTS

	public SELF isNotEmpty() {
		return constraint(s -> !s.isEmpty(), input -> input.getName() + " must not be empty");
	}

	public SELF isNotBlank() {
		return constraint(s -> !s.isBlank(), input -> input.getName() + " must not be blank");
	}

	// ===========================================================================================================
	// MAPPERS

//	public IntegerInputProcessorBuilder mapToInteger() {
//		var mapper = new SimpleMapper<String, Integer>(Integer.class, Integer::parseInt);
//		return new IntegerInputProcessorBuilder(mapper);
//	}

}
