package io.juserinput.builder.types;

import java.util.function.Function;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.juserinput.InputProcessor;
import io.juserinput.builder.InputProcessorBuilder;
import io.juserinput.builder.mapping.MappingTestPerformer;
import io.juserinput.builder.operations.transformers.TransformerTestPerformer;
import io.juserinput.builder.operations.types.StringInputProcessorBuilder;
import io.juserinput.builder.operations.validators.ValidatorTestPerformer;

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
			performSringTransformationTest(StringInputProcessorBuilder::strip, " test ", "test");
		}

		@Test
		void toUpperCase() {
			performSringTransformationTest(StringInputProcessorBuilder::toUpperCase, " test ", " TEST ");
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
			void given_blankValue_when_isEmptyCheck_then_error() {
				performSringErrorTest(b -> b.isNotBlank(), " ", "myAttr", "myAttr must not be blank");
			}

			@NullSource
			@ValueSource(strings = { "a", "1" })
			@ParameterizedTest
			void given_notBlankValue_when_isEmptyCheck_then_valid(String value) {
				performSringValidTest(b -> b.isNotBlank(), value, "myAttr");
			}

		}

	}

	// ===========================================================================================================
	// MAPPERS

	@Nested
	class Mappers {

		private static <T> void performSringMappingTest(
			Function<StringInputProcessorBuilder<String>, InputProcessorBuilder<?, String, T>> transformerSetter, String value, T expectedValue
		) {
			MappingTestPerformer.performMappingTest(InputProcessor.forString(), transformerSetter, value, expectedValue);
		}

		@Nested
		class MapToInteger {

			@Test
			void nominal() {
				performSringMappingTest(StringInputProcessorBuilder::mapToInteger, "42", 42);
			}

		}

	}

}
