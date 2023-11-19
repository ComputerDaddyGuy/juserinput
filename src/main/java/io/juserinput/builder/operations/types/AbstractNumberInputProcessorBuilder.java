package io.juserinput.builder.operations.types;

import io.juserinput.builder.operations.AbstractObjectOperationInputProcessorBuilder;

public abstract class AbstractNumberInputProcessorBuilder<SELF extends AbstractNumberInputProcessorBuilder<SELF, N>, N extends Number>
	extends AbstractObjectOperationInputProcessorBuilder<SELF, N> {

	protected AbstractNumberInputProcessorBuilder(Class<?> selfType) {
		super(selfType);
	}

	// ===========================================================================================================
	// TRANSFORMERS

	/**
	 * @return
	 */
	public abstract SELF clamp(N min, N max);

	// ===========================================================================================================
	// VALIDATORS

	// ===========================================================================================================
	// MAPPERS

}
