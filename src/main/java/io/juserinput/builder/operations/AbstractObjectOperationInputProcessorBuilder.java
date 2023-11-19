package io.juserinput.builder.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import io.juserinput.InputProcessor;
import io.juserinput.builder.operations.validators.Validator;

/**
 * @param <IN>
 * @param <OUT>
 */
public abstract class AbstractObjectOperationInputProcessorBuilder<SELF extends AbstractObjectOperationInputProcessorBuilder<SELF, T>, T>
	implements InputOperationProcessorBuilder<SELF, T, T> {

	protected final SELF myself;
	private final List<Operation<T>> operations;

	@SuppressWarnings("unchecked")
	protected AbstractObjectOperationInputProcessorBuilder(Class<?> selfType) {
		this.myself = (SELF) Objects.requireNonNull(selfType, "selfType cannot be null").cast(this);
		this.operations = new ArrayList<>();
	}

	// -----------------------------------------------------------------------------------------------------------

	@Override
	public SELF transform(Function<T, T> transformFunction) {
		operations.add(new TransformationOperation<>(transformFunction));
		return myself;
	}

	// -----------------------------------------------------------------------------------------------------------

	@Override
	public SELF validate(Validator<T> validator) {
		Objects.requireNonNull(validator, "validator cannot be null");
		operations.add(new ValidationOperation<>(validator));
		return myself;
	}

	// -----------------------------------------------------------------------------------------------------------

	@Override
	public InputProcessor<T, T> build() {
		return new InputOperationProcessor<>(operations);
	}

}
