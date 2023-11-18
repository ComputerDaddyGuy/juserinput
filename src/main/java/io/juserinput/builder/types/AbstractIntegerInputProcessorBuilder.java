package io.juserinput.builder.types;

public abstract class AbstractIntegerInputProcessorBuilder<SELF extends AbstractIntegerInputProcessorBuilder<SELF>>
	extends AbstractNumberInputProcessorBuilder<SELF, Integer> {

	protected AbstractIntegerInputProcessorBuilder(Class<?> selfType) {
		super(Integer.class, selfType);
	}

	// ===========================================================================================================
	// SANITIZERS

	/**
	 * @return
	 */
	@Override
	public SELF clamp(Integer min, Integer max) {
		return sanitize(value -> value < min ? min : value > max ? max : value);
	}

	// ===========================================================================================================
	// CONSTRAINTS

	// ===========================================================================================================
	// MAPPERS

}
