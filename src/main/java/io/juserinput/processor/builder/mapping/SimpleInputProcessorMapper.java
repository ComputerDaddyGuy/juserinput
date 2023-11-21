package io.juserinput.processor.builder.mapping;

import java.util.function.Function;

import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.builder.operations.InputOperationProcessorBuilder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleInputProcessorMapper<IN, OUT, B extends InputOperationProcessorBuilder<?, IN, NEW_OUT>, NEW_OUT> implements InputProcessorMapper<IN, OUT, B, NEW_OUT> {

	private final MapFunction<OUT, NEW_OUT> mapFunction;
	private final Function<InputProcessor<IN, NEW_OUT>, B> builderFunction;

	public SimpleInputProcessorMapper(Class<NEW_OUT> newOutClass, Function<OUT, NEW_OUT> valueMapFunction, Function<InputProcessor<IN, NEW_OUT>, B> builderFunction) {
		this.mapFunction = new SimpleMapFunction<>(newOutClass, valueMapFunction);
		this.builderFunction = builderFunction;
	}

	@Override
	public B map(InputProcessor<IN, OUT> inputProcessor) {
		InputProcessor<IN, NEW_OUT> mappedProcessor = new MappingInputProcessor<>(inputProcessor, mapFunction);
		return builderFunction.apply(mappedProcessor);
	}

}
