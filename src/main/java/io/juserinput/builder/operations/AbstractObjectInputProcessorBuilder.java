package io.juserinput.builder.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import io.juserinput.InputProcessor;
import io.juserinput.builder.InputProcessorBuilder;
import io.juserinput.builder.operations.constraints.Constraint;

/**
 * @param <IN>
 * @param <OUT>
 */
public abstract class AbstractObjectInputProcessorBuilder<SELF extends AbstractObjectInputProcessorBuilder<SELF, T>, T>
	implements InputProcessorBuilder<SELF, T, T, T> {

	protected final Class<T> inputType;
	protected final SELF myself;

	private final List<Operation<T>> operations;

	@SuppressWarnings("unchecked")
	protected AbstractObjectInputProcessorBuilder(Class<T> inputType, Class<?> selfType) {
		this.inputType = Objects.requireNonNull(inputType, "inputType cannot be null");
		this.myself = (SELF) Objects.requireNonNull(selfType, "selfType cannot be null").cast(this);
		this.operations = new ArrayList<>();
	}

	public static <T> ObjectInputProcessorBuilder<T> forClass(Class<T> clazz) {
		return new ObjectInputProcessorBuilder<>(clazz);
	}

	// -----------------------------------------------------------------------------------------------------------

	@Override
	public SELF sanitize(Function<T, T> sanitizeFunction) {
		operations.add(new SanitizationOperation<>(sanitizeFunction));
		return myself;
	}

	// -----------------------------------------------------------------------------------------------------------

	@Override
	public SELF constraint(Constraint<T> constraint) {
		Objects.requireNonNull(constraint, "constraint cannot be null");
		operations.add(new ConstraintCheckOperation<>(constraint));
		return myself;
	}

	// -----------------------------------------------------------------------------------------------------------

	@Override
	public InputProcessor<T, T> build() {
		return new InputOperationProcessor<>(operations);
	}

}
