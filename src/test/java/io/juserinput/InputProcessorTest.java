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

			var expected = InputProcessorResultBuilder.newInstance(Input.of(null, "myAttr")).build();
			Assertions.assertThat(proc.process(null, "myAttr")).isEqualTo(expected);
		}

		@Test
		void nominal() {
			var proc = InputProcessor.forClass(String.class)
				.sanitize(s -> s.trim())
				.build();

			var expected = InputProcessorResultBuilder.newInstance(Input.of("test", "myAttr")).build();
			Assertions.assertThat(proc.process(" test ", "myAttr")).isEqualTo(expected);
		}

	}

	@Nested
	class ConstraintCheck {

		@Test
		void null_isNot_constrained() {
			var proc = InputProcessor.forClass(String.class)
				.constraint(s -> true, "value should starts with 'a'")
				.build();

			var actual = proc.process(null, "myAttr");

			var expected = InputProcessorResultBuilder.newInstance(Input.of(null, "myAttr")).build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void when_constraintFailed_with_staticMessage_then_exceptionWithStaticMessage() {
			var proc = InputProcessor.forClass(String.class)
				.constraint(s -> false, "should starts with 'a'")
				.build();

			var actual = proc.process(Input.of("best", "myAttr"));

			var expected = InputProcessorResultBuilder
				.newInstance(Input.of("best", "myAttr"))
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

			var actual = proc.process(Input.of("best", "myAttr"));

			var expected = InputProcessorResultBuilder
				.newInstance(Input.of("best", "myAttr"))
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

			var actual = proc.process(null, "myAttr");

			var expected = InputProcessorResultBuilder.newInstance(Input.of(null, "myAttr")).build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void nominal() {
			var proc = InputProcessor.forClass(String.class)
				.map(Long.class, Long::parseLong)
				.build();

			var actual = proc.process("3", "myAttr");

			var expected = InputProcessorResultBuilder.newInstance(Input.of(3L, "myAttr")).build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void when_mappingFailed_then_error() {
			var proc = InputProcessor.forClass(String.class)
				.map(Long.class, Long::parseLong)
				.build();

			var actual = proc.process(Input.of("notALong", "myAttr"));

			var expected = InputProcessorResultBuilder
				.newInstance(Input.of("notALong", "myAttr"))
				.addError(InputProcessorError.newError("myAttr", "myAttr is not a valid Long"))
				.build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

	}

}
