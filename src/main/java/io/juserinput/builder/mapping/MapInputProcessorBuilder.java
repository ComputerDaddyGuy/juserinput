package io.juserinput.builder.mapping;

import java.util.Objects;
import java.util.function.Function;

import io.juserinput.InputProcessor;
import io.juserinput.builder.InputProcessorBuilder;
import io.juserinput.builder.operations.constraints.Constraint;

public class MapInputProcessorBuilder<IN, T, OUT, NEW_OUT> implements InputProcessorBuilder<MapInputProcessorBuilder<IN, T, OUT, NEW_OUT>, IN, T, NEW_OUT> {

	private InputProcessorBuilder<?, IN, T, OUT> priorBuilder;
	private final Mapper<OUT, NEW_OUT> mappingFunction;

	public MapInputProcessorBuilder(InputProcessorBuilder<?, IN, T, OUT> priorBuilder, Mapper<OUT, NEW_OUT> mappingFunction) {
		super();
		this.priorBuilder = Objects.requireNonNull(priorBuilder, "Prior builder cannot be null");
		this.mappingFunction = Objects.requireNonNull(mappingFunction, "Mapping function cannot be null");
	}

	@Override
	public MapInputProcessorBuilder<IN, T, OUT, NEW_OUT> sanitize(Function<T, T> sanitizeFunction) {
		priorBuilder = priorBuilder.sanitize(sanitizeFunction);
		return this;
	}

	@Override
	public MapInputProcessorBuilder<IN, T, OUT, NEW_OUT> constraint(Constraint<T> constraint) {
		priorBuilder = priorBuilder.constraint(constraint);
		return this;
	}

	@Override
	public InputProcessor<IN, NEW_OUT> build() {
		return new MappingInputProcessor<>(priorBuilder.build(), mappingFunction);
	}

}
