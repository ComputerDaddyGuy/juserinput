package io.juserinput.builder;

import java.util.function.Function;
import java.util.function.Predicate;

import io.juserinput.Input;
import io.juserinput.InputProcessor;
import io.juserinput.builder.mapping.MapInputProcessorBuilder;
import io.juserinput.builder.mapping.Mapper;
import io.juserinput.builder.mapping.SimpleMapper;
import io.juserinput.builder.operations.constraints.Constraint;
import io.juserinput.builder.operations.constraints.SimpleConstraint;

/**
 * @param <IN>
 * @param <OUT>
 */
public interface InputProcessorBuilder<SELF extends InputProcessorBuilder<SELF, IN, T, OUT>, IN, T, OUT> {

	// -----------------------------------------------------------------------------------------------------------

	public SELF sanitize(Function<T, T> sanitizeFunction);

	// -----------------------------------------------------------------------------------------------------------

	public default SELF constraint(Predicate<T> constraintPredicate, String constraintMessage) {
		return constraint(constraintPredicate, input -> constraintMessage);
	}

	public default SELF constraint(Predicate<T> constraintPredicate, Function<Input<T>, String> constraintMessage) {
		return constraint(new SimpleConstraint<>(constraintPredicate, constraintMessage));
	}

	public SELF constraint(Constraint<T> constraint);

	// -----------------------------------------------------------------------------------------------------------

	public default <NEW_OUT> MapInputProcessorBuilder<IN, T, OUT, NEW_OUT> map(Class<NEW_OUT> newClass, Function<OUT, NEW_OUT> mappingFunction) {
		return map(new SimpleMapper<>(newClass, mappingFunction));
	}

	public default <NEW_OUT> MapInputProcessorBuilder<IN, T, OUT, NEW_OUT> map(Mapper<OUT, NEW_OUT> mappingFunction) {
		return new MapInputProcessorBuilder<>(this, mappingFunction);
	}

	// -----------------------------------------------------------------------------------------------------------

	public InputProcessor<IN, OUT> build();

}
