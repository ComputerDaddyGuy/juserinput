package io.juserinput;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.juserinput.errors.InputProcessorError;
import io.juserinput.result.InputProcessorResultAssert;
import io.juserinput.result.InputProcessorResultBuilder;

class InputProcessorTest {

	@Nested
	class Sanitization {

		@Test
		void null_isNot_sanitized() {
			var proc = InputProcessor.forClass(String.class)
				.sanitize(s -> s.trim())
				.build();

			var expected = InputProcessorResultBuilder.newInstance(Input.of("myAttr", null)).build();
			Assertions.assertThat(proc.process("myAttr", null)).isEqualTo(expected);
		}

		@Test
		void nominal() {
			var proc = InputProcessor.forClass(String.class)
				.sanitize(s -> s.trim())
				.build();

			var expected = InputProcessorResultBuilder.newInstance(Input.of("myAttr", "test")).build();
			Assertions.assertThat(proc.process("myAttr", " test ")).isEqualTo(expected);
		}

	}

	@Nested
	class ConstraintCheck {

		@Test
		void null_isNot_constrained() {
			var proc = InputProcessor.forClass(String.class)
				.constraint(s -> true, "value should starts with 'a'")
				.build();

			var actual = proc.process("myAttr", null);

			var expected = InputProcessorResultBuilder.newInstance(Input.of("myAttr", null)).build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void when_constraintFailed_with_staticMessage_then_exceptionWithStaticMessage() {
			var proc = InputProcessor.forClass(String.class)
				.constraint(s -> false, "should starts with 'a'")
				.build();

			var actual = proc.process(Input.of("myAttr", "best"));

			var expected = InputProcessorResultBuilder
				.newInstance(Input.of("myAttr", "best"))
				.addError(InputProcessorError.newError("myAttr", "should starts with 'a'"))
				.build();

			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void when_constraintFailed_with_dynamicMessage_then_exceptionWithDynamicMessage() {
			var proc = InputProcessor.forClass(String.class)
				.constraint(s -> false, input -> input.getName() + " should starts with 'a', but was '" + input.getValue() + "'")
				.build();

			var actual = proc.process(Input.of("myAttr", "best"));

			var expected = InputProcessorResultBuilder
				.newInstance(Input.of("myAttr", "best"))
				.addError(InputProcessorError.newError("myAttr", "myAttr should starts with 'a', but was 'best'"))
				.build();

			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

	}

	@Nested
	class Mapping {

		@Test
		void null_isNot_mapped() {
			var proc = InputProcessor.forClass(String.class)
				.map(Long.class, Long::parseLong)
				.build();

			var actual = proc.process("myAttr", null);

			var expected = InputProcessorResultBuilder.newInstance(Input.of("myAttr", null)).build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void nominal() {
			var proc = InputProcessor.forClass(String.class)
				.map(Long.class, Long::parseLong)
				.build();

			var actual = proc.process("myAttr", "3");

			var expected = InputProcessorResultBuilder.newInstance(Input.of("myAttr", 3L)).build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void when_mappingFailed_then_error() {
			var proc = InputProcessor.forClass(String.class)
				.map(Long.class, Long::parseLong)
				.build();

			var actual = proc.process(Input.of("myAttr", "notALong"));

			var expected = InputProcessorResultBuilder
				.newInstance(Input.of("myAttr", "notALong"))
				.addError(InputProcessorError.newError("myAttr", "myAttr is not a valid Long"))
				.build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

	}

}
