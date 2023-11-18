package io.juserinput.result;

import io.juserinput.InputProcessorException;

public class InputProcessorNoValueException extends InputProcessorException {

	public InputProcessorNoValueException(String inputName) {
		super(inputName, inputName + " has no value");
	}

}
