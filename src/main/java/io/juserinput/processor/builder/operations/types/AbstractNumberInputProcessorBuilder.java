package io.juserinput.processor.builder.operations.types;

import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.builder.operations.AbstractObjectOperationInputProcessorBuilder;

public abstract class AbstractNumberInputProcessorBuilder<SELF extends AbstractNumberInputProcessorBuilder<SELF, IN, N>, IN, N extends Number>
	extends AbstractObjectOperationInputProcessorBuilder<SELF, IN, N> {

	protected AbstractNumberInputProcessorBuilder(InputProcessor<IN, N> previous, Class<?> selfType) {
		super(previous, selfType);
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
