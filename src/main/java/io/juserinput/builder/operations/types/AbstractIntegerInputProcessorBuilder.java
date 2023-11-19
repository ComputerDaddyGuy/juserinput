package io.juserinput.builder.operations.types;

public abstract class AbstractIntegerInputProcessorBuilder<SELF extends AbstractIntegerInputProcessorBuilder<SELF>>
	extends AbstractNumberInputProcessorBuilder<SELF, Integer> {

	protected AbstractIntegerInputProcessorBuilder(Class<?> selfType) {
		super(selfType);
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
