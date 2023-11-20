package io.juserinput.builder.types;

import java.util.function.Function;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.juserinput.Input;
import io.juserinput.InputProcessor;
import io.juserinput.builder.operations.types.StringInputProcessorBuilder;
import io.juserinput.builder.operations.validators.ValidatorTestPerformer;
import io.juserinput.result.InputProcessorResultAssert;
import io.juserinput.result.InputProcessorResultBuilder;

class StringInputProcessorTest {

	// ===========================================================================================================
	// TRANSFORMERS

	@Nested
	class Transformation {

		@Test
		void strip() {
			var proc = InputProcessor.forString()
				.strip()
				.build();

			var actual = proc.process("myAttr", " test ");

			Assertions.assertThat(actual.asRequired()).isEqualTo("test");
		}

	}

	// ===========================================================================================================
	// VALIDATORS

	@Nested
	class Validation {

		private static void performSringErrorTest(
			Function<StringInputProcessorBuilder<String>, StringInputProcessorBuilder<String>> contraintSetter, String value, String inputName, String expectedErrorMessage
		) {
			ValidatorTestPerformer.performErrorTest(InputProcessor.forString(), contraintSetter, value, inputName, expectedErrorMessage);
		}

		private static void performSringValidTest(
			Function<StringInputProcessorBuilder<String>, StringInputProcessorBuilder<String>> contraintSetter, String value, String inputName
		) {
			ValidatorTestPerformer.performValidTest(InputProcessor.forString(), contraintSetter, value, inputName);
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

		@Nested
		class MapToInteger {

			@Test
			void nominal() {
				var proc = InputProcessor.forString()
					.mapToInteger()
					.build();

				var actual = proc.process("myAttr", "42");

				var expected = InputProcessorResultBuilder
					.newInstance(Input.of("myAttr", 42))
					.build();

				InputProcessorResultAssert.assertThat(actual)
					.isEqualTo(expected);
			}

		}

	}

}
