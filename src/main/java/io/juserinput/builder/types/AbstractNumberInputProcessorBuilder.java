package io.juserinput.builder.types;

import io.juserinput.builder.operations.AbstractObjectInputProcessorBuilder;

public abstract class AbstractNumberInputProcessorBuilder<SELF extends AbstractNumberInputProcessorBuilder<SELF, N>, N extends Number>
	extends AbstractObjectInputProcessorBuilder<SELF, N> {

	protected AbstractNumberInputProcessorBuilder(Class<N> numberType, Class<?> selfType) {
		super(numberType, selfType);
	}

	// ===========================================================================================================
	// SANITIZERS

	/**
	 * @return
	 */
	public abstract SELF clamp(N min, N max);

	// ===========================================================================================================
	// CONSTRAINTS

	// ===========================================================================================================
	// MAPPERS

}
