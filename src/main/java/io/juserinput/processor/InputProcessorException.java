package io.juserinput.processor;

public class InputProcessorException extends RuntimeException {

	private final Input<?> input;

	public InputProcessorException(Input<?> input, String message, Throwable cause) {
		super(message, cause);
		this.input = input;
	}

	public InputProcessorException(Input<?> input, String message) {
		this(input, message, null);
	}

	public Input<?> getInput() {
		return input;
	}

}
