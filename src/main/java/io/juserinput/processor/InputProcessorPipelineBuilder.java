package io.juserinput.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import io.juserinput.processor.Operation.ConstraintCheckOperation;
import io.juserinput.processor.Operation.SanitizationOperation;

public class InputProcessorPipelineBuilder<IN, OUT> {

	private final Function<Input<IN>, Input<OUT>> mapping;
	private final List<Operation<OUT>> operations;

	/* internal */ InputProcessorPipelineBuilder(Function<Input<IN>, Input<OUT>> mapping) {
		this.mapping = mapping;
		this.operations = new ArrayList<>();
	}

	public InputProcessorPipelineBuilder<IN, OUT> sanitize(Function<OUT, OUT> sanitizeFunction) {
		operations.add(new SanitizationOperation<>(sanitizeFunction));
		return this;
	}

	public InputProcessorPipelineBuilder<IN, OUT> constraint(Predicate<OUT> constraintPredicate, String message) {
		return this.constraint(constraintPredicate, input -> message);
	}

	public InputProcessorPipelineBuilder<IN, OUT> constraint(Predicate<OUT> constraintPredicate, Function<Input<OUT>, String> messageFunction) {
		operations.add(new ConstraintCheckOperation<>(constraintPredicate, messageFunction));
		return this;
	}

	public <NEW_OUT> InputProcessorPipelineBuilder<IN, NEW_OUT> map(Function<OUT, NEW_OUT> mapFunction) {
		Function<Input<IN>, Input<NEW_OUT>> func = a -> {
			var result = this.process(a);
			if (result.getValue() == null) {
				return Input.of(null, result.getName());
			}
			var valueNewOut = mapFunction.apply(result.getValue());
			return result.withValue(valueNewOut);
		};
		return new InputProcessorPipelineBuilder<>(func);
	}

	public InputProcessor<IN, OUT> build() {
		return new InputProcessor<>(this);
	}

	/* internal */ Input<OUT> process(Input<IN> input) {
		var result = mapping.apply(input);
		for (Operation<OUT> op : operations) {
			result = result.withValue(op.operate(result));
		}
		return result;
	}

}
