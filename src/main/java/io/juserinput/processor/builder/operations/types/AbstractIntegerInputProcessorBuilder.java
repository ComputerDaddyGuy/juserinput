package io.juserinput.processor.builder.operations.types;

import io.juserinput.processor.InputProcessor;

public abstract class AbstractIntegerInputProcessorBuilder<SELF extends AbstractIntegerInputProcessorBuilder<SELF, IN>, IN>
	extends AbstractNumberInputProcessorBuilder<SELF, IN, Integer> {

	protected AbstractIntegerInputProcessorBuilder(InputProcessor<IN, Integer> previous, Class<?> selfType) {
		super(previous, selfType);
	}

	// ===========================================================================================================
	// TRANSFORMERS

	/**
	 * @return
	 */
	@Override
	public SELF clamp(Integer min, Integer max) {
		return transform(value -> value < min ? min : value > max ? max : value);
	}

	// ===========================================================================================================
	// VALIDATORS

	// ===========================================================================================================
	// MAPPERS

}
