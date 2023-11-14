package io.juserinput.processor;

public class InputProcessorNoValueException extends InputProcessorException {

	public InputProcessorNoValueException(Input<?> input, String cause) {
		super(input, input.getName() + " has no value" + computeCause(cause));
	}

	private static String computeCause(String cause) {
		if (cause == null) {
			return "";
		}
		return " (" + cause + ")";
	}

}
