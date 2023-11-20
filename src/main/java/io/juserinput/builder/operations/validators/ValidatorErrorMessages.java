package io.juserinput.builder.operations.validators;

public class ValidatorErrorMessages {

	private ValidatorErrorMessages() {

	}

	public static String empty(String inputName) {
		return String.format("%s must not be empty", inputName);
	}

	public static String blank(String inputName) {
		return String.format("%s must not be blank", inputName);
	}

	public static String minLength(String inputName, int minLength, int currentLength) {
		return String.format("The length of %s must be at least %d, but is %d", inputName, minLength, currentLength);
	}

	public static String maxLength(String inputName, int maxLength, int currentLength) {
		return String.format("The length of %s must not exceed %d, but is %d", inputName, maxLength, currentLength);
	}

	public static String lengthBetween(String inputName, int minLength, int maxLength, int currentLength) {
		return String.format("The length of %s must be between %d and %d, but is %d", inputName, minLength, maxLength, currentLength);
	}

	public static String pattern(String inputName, String pattern) {
		return String.format("%s must match pattern: %s", inputName, pattern);
	}

}
