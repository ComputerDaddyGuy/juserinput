package io.juserinput.processor.builder.operations;

import java.util.function.Function;
import java.util.function.Predicate;

import io.juserinput.processor.Input;
import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.builder.InputProcessorBuilder;
import io.juserinput.processor.builder.mapping.MapFunction;
import io.juserinput.processor.builder.mapping.MappingInputProcessor;
import io.juserinput.processor.builder.operations.validators.Validator;

/**
 * @param <IN>
 * @param <OUT>
 */
public interface InputOperationProcessorBuilder<SELF extends InputOperationProcessorBuilder<SELF, IN, T>, IN, T>
	extends InputProcessorBuilder<SELF, IN, T> {

	// -----------------------------------------------------------------------------------------------------------

	public SELF transform(Function<T, T> transformer);

	// -----------------------------------------------------------------------------------------------------------

	public default SELF validate(Predicate<T> validationPredicate, String errorMessage) {
		return validate(Validator.validator(validationPredicate, input -> errorMessage));
	}

	public default SELF validate(Predicate<T> validationPredicate, Function<Input<T>, String> errorMessage) {
		return validate(Validator.validator(validationPredicate, errorMessage));
	}

	public SELF validate(Validator<T> validator);

	// -----------------------------------------------------------------------------------------------------------

	public default <OUT> ObjectOperationInputProcessorBuilder<IN, OUT> map(
		Class<OUT> newOutClass, Function<T, OUT> mapFunction
	) {
		return map(MapFunction.newInstance(newOutClass, mapFunction));
	}

	public default <OUT> ObjectOperationInputProcessorBuilder<IN, OUT> map(
		MapFunction<T, OUT> mapFunction
	) {
		InputProcessor<IN, OUT> previous = new MappingInputProcessor<>(this.build(), mapFunction);
		return new ObjectOperationInputProcessorBuilder<>(previous);
	}

}
