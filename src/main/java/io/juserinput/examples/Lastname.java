package io.juserinput.examples;

import io.juserinput.InputProcessor;

public final class Lastname {

	private static final int MAX_LENGTH = 100;
	private static final InputProcessor<String, String> PROCESSOR = InputProcessor.forString()
		.isMaxLength(MAX_LENGTH)
		.toUpperCase()
		.build();

}
