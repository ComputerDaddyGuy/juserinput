package io.juserinput.processor.result;

import io.juserinput.processor.InputProcessorException;

public class InputProcessorNoValueException extends InputProcessorException {

	public InputProcessorNoValueException(String inputName) {
		super(inputName, inputName + " has no value");
	}

}
