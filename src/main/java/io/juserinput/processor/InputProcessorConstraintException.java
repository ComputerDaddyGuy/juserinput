package io.juserinput.processor;

public class InputProcessorConstraintException extends InputProcessorException {

	public InputProcessorConstraintException(Input<?> input, String message) {
		super(input, message);
	}

}
