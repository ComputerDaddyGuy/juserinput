package io.juserinput.errors;

/**
 * 
 * 
 *
 */
public interface InputProcessorError {

	public String getInputName();

	public String getMessage();

	/**
	 * @param  inputName
	 * @param  message
	 * 
	 * @return
	 */
	public static InputProcessorError newError(String inputName, String message) {
		return new SimpleInputProcessorError(inputName, message);
	}

}
