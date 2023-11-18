package io.juserinput.builder.types;

import java.util.function.Function;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.juserinput.InputProcessor;
import io.juserinput.builder.operations.constraints.ConstraintsTestPerformer;
import io.juserinput.builder.types.StringInputProcessorBuilder;

class StringInputProcessorTest {

	// ===========================================================================================================
	// SANITIZERS

	@Nested
	class Sanitizers {

		@Test
		void strip() {
			var proc = InputProcessor.forString()
				.strip()
				.build();

			var result = proc.process(" test ", "myAttr");

			Assertions.assertThat(result.asRequired()).isEqualTo("test");
		}

	}

	// ===========================================================================================================
	// CONSTRAINTS

	@Nested
	class Constraints {

		private static void performSringErrorTest(
			Function<StringInputProcessorBuilder, StringInputProcessorBuilder> contraintSetter, String value, String inputName, String expectedErrorMessage
		) {
			ConstraintsTestPerformer.performErrorTest(InputProcessor.forString(), contraintSetter, value, inputName, expectedErrorMessage);
		}

		private static void performSringValidTest(
			Function<StringInputProcessorBuilder, StringInputProcessorBuilder> contraintSetter, String value, String inputName
		) {
			ConstraintsTestPerformer.performValidTest(InputProcessor.forString(), contraintSetter, value, inputName);
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

}
