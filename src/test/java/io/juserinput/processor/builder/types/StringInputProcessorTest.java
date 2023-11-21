package io.juserinput.processor.builder.types;

import java.util.function.Function;
import java.util.regex.Pattern;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.builder.InputProcessorBuilder;
import io.juserinput.processor.builder.mapping.MappingTestPerformer;
import io.juserinput.processor.builder.operations.transformers.TransformerTestPerformer;
import io.juserinput.processor.builder.operations.types.StringInputProcessorBuilder;
import io.juserinput.processor.builder.operations.validators.ValidatorTestPerformer;

class StringInputProcessorTest {

	// ===========================================================================================================
	// TRANSFORMERS

	@Nested
	class Transformation {

		private static void performSringTransformationTest(
			Function<StringInputProcessorBuilder<String>, StringInputProcessorBuilder<String>> transformerSetter, String value, String expectedValue
		) {
			TransformerTestPerformer.performTransformationTest(InputProcessor.forString(), transformerSetter, value, expectedValue);
		}

		@Test
		void strip() {
			performSringTransformationTest(StringInputProcessorBuilder::strip, " tESt ", "tESt");
		}

		@Test
		void toUpperCase() {
			performSringTransformationTest(StringInputProcessorBuilder::toUpperCase, " tESt ", " TEST ");
		}

		@Test
		void toLowerCase() {
			performSringTransformationTest(StringInputProcessorBuilder::toLowerCase, " TeST ", " test ");
		}

	}

	// ===========================================================================================================
	// VALIDATORS

	@Nested
	class Validation {

		private static void performSringErrorTest(
			Function<StringInputProcessorBuilder<String>, StringInputProcessorBuilder<String>> validatorSetter, String value, String inputName, String expectedErrorMessage
		) {
			ValidatorTestPerformer.performErrorTest(InputProcessor.forString(), validatorSetter, value, inputName, expectedErrorMessage);
		}

		private static void performSringValidTest(
			Function<StringInputProcessorBuilder<String>, StringInputProcessorBuilder<String>> validatorSetter, String value, String inputName
		) {
			ValidatorTestPerformer.performValidTest(InputProcessor.forString(), validatorSetter, value, inputName);
		}

		@Nested
		class IsNotEmpty {

			@Test
			void given_emptyValue_when_isEmptyCheck_then_error() {
				performSringErrorTest(b -> b.isNotEmpty(), "", "myAttr", "myAttr must not be empty");
			}

			@NullSource
			@ValueSource(strings = { " ", "\n", "\t", "a", "1" })
			@ParameterizedTest
			void given_notEmptyValue_when_isEmptyCheck_then_valid(String value) {
				performSringValidTest(b -> b.isNotEmpty(), value, "myAttr");
			}

		}

		@Nested
		class IsNotBlank {

			@ValueSource(strings = { " ", "\n", "\t" })
			@ParameterizedTest
			void given_blankValue_when_isEmptyCheck_then_error(String value) {
				performSringErrorTest(b -> b.isNotBlank(), value, "myAttr", "myAttr must not be blank");
			}

			@NullSource
			@ValueSource(strings = { "a", "1" })
			@ParameterizedTest
			void given_notBlankValue_when_isEmptyCheck_then_valid(String value) {
				performSringValidTest(b -> b.isNotBlank(), value, "myAttr");
			}

		}

		@Nested
		class IsMinLength {

			@Test
			void negativeLength_throws_exception() {
				Assertions.assertThatIllegalArgumentException()
					.isThrownBy(() -> InputProcessor.forString().isMinLength(-1).build())
					.withMessage("Invalid min length: must be positive or null, but is -1");
			}

			@ValueSource(strings = { "", " ", "ab" })
			@ParameterizedTest
			void given_invalidValue_when_isMinLength_then_error(String value) {
				performSringErrorTest(b -> b.isMinLength(3), value, "myAttr", "The length of myAttr must be at least 3, but is " + value.length());
			}

			@NullSource
			@ValueSource(strings = { "abc", "   " })
			@ParameterizedTest
			void given_validValue_when_isMinLength_then_valid(String value) {
				performSringValidTest(b -> b.isMinLength(3), value, "myAttr");
			}

		}

		@Nested
		class IsMaxLength {

			@Test
			void negativeLength_throws_exception() {
				Assertions.assertThatIllegalArgumentException()
					.isThrownBy(() -> InputProcessor.forString().isMaxLength(-1).build())
					.withMessage("Invalid max length: must be positive or null, but is -1");
			}

			@ValueSource(strings = { "abcd", "    " })
			@ParameterizedTest
			void given_invalidValue_when_isMaxLength_then_error(String value) {
				performSringErrorTest(b -> b.isMaxLength(3), value, "myAttr", "The length of myAttr must not exceed 3, but is " + value.length());
			}

			@NullSource
			@ValueSource(strings = { "", " ", "abc" })
			@ParameterizedTest
			void given_validValue_when_isMaxLength_then_valid(String value) {
				performSringValidTest(b -> b.isMaxLength(3), value, "myAttr");
			}

		}

		@Nested
		class IsLengthBetween {

			@Test
			void negativeMinLength_throws_exception() {
				Assertions.assertThatIllegalArgumentException()
					.isThrownBy(() -> InputProcessor.forString().isLengthBetween(-1, 2).build())
					.withMessage("Invalid min length or max length: must be positive or null, but min length is -1 and max length is 2");
			}

			@Test
			void negativeMaxLength_throws_exception() {
				Assertions.assertThatIllegalArgumentException()
					.isThrownBy(() -> InputProcessor.forString().isLengthBetween(1, -2).build())
					.withMessage("Invalid min length or max length: must be positive or null, but min length is 1 and max length is -2");
			}

			@Test
			void minLengthGreaterThanMaxLength_throws_exception() {
				Assertions.assertThatIllegalArgumentException()
					.isThrownBy(() -> InputProcessor.forString().isLengthBetween(2, 1).build())
					.withMessage("Min length must be greater than max length, but min length is 2 and max length is 1");
			}

			@ValueSource(strings = { "ab", "abcdef" })
			@ParameterizedTest
			void given_invalidValue_when_isLengthBetween_then_error(String value) {
				performSringErrorTest(b -> b.isLengthBetween(3, 5), value, "myAttr", "The length of myAttr must be between 3 and 5, but is " + value.length());
			}

			@NullSource
			@ValueSource(strings = { "abc", "abcd", "abcde", "ééééé" })
			@ParameterizedTest
			void given_validValue_when_isLengthBetween_then_valid(String value) {
				performSringValidTest(b -> b.isLengthBetween(3, 5), value, "myAttr");
			}

		}

		@Nested
		class Matches {

			@Test
			void nullPattern_throws_exception() {
				Assertions.assertThatIllegalArgumentException()
					.isThrownBy(() -> InputProcessor.forString().matches(null).build())
					.withMessage("Pattern is null");
			}

			@Test
			void given_matchingValue_when_matchTest_then_valid() {
				performSringValidTest(b -> b.matches(Pattern.compile("\\d+")), "123", "myAttr");
			}

			@Test
			void given_notMatchingValue_when_matchTest__then_error() {
				performSringErrorTest(b -> b.matches(Pattern.compile("\\d+")), "abc", "myAttr", "myAttr must match pattern: \\d+");
			}

		}

	}

	// ===========================================================================================================
	// MAPPERS

	@Nested
	class Mappers {

		private static <T> void performValidSringMappingTest(
			Function<StringInputProcessorBuilder<String>, InputProcessorBuilder<?, String, T>> transformerSetter, String value, T expectedValue
		) {
			MappingTestPerformer.performSuccessMappingTest(InputProcessor.forString(), transformerSetter, "myAttr", value, expectedValue);
		}

		private static <T> void performErrorSringMappingTest(
			Function<StringInputProcessorBuilder<String>, InputProcessorBuilder<?, String, T>> transformerSetter, String value, Class<T> clazz
		) {
			MappingTestPerformer.performErrorMappingTest(InputProcessor.forString(), transformerSetter, "myAttr", value, "myAttr is not a valid " + clazz.getSimpleName());
		}

		@Nested
		class MapToInteger {

			@Test
			void nominal() {
				performValidSringMappingTest(StringInputProcessorBuilder::mapToInteger, "42", 42);
			}

			@Test
			void mappingFail() {
				performErrorSringMappingTest(StringInputProcessorBuilder::mapToInteger, "notAnInteger", Integer.class);
			}

		}

	}

}
