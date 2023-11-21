package io.juserinput.processor.builder.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.builder.operations.validators.Validator;

public abstract class AbstractObjectOperationInputProcessorBuilder<SELF extends AbstractObjectOperationInputProcessorBuilder<SELF, IN, T>, IN, T>
	implements InputOperationProcessorBuilder<SELF, IN, T> {

	private final InputProcessor<IN, T> previous;
	protected final SELF myself;
	private final List<Operation<T>> operations;

	@SuppressWarnings("unchecked")
	protected AbstractObjectOperationInputProcessorBuilder(InputProcessor<IN, T> previous, Class<?> selfType) {
		this.previous = Objects.requireNonNull(previous, "Previous processor cannot be null");
		this.myself = (SELF) Objects.requireNonNull(selfType, "Self type cannot be null").cast(this);
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
	public InputProcessor<IN, T> build() {
		return previous.then(new InputOperationProcessor<>(operations));
	}

}
