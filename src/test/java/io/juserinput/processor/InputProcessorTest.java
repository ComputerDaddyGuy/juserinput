package io.juserinput.processor;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class InputProcessorTest {

	@Nested
	class Sanitization {

		@Test
		void null_isNot_sanitized() {
			var proc = InputProcessor.forClass(String.class)
				.sanitize(s -> s.trim())
				.build();

			var expected = new InputProcessorResult<>(Input.of(null, "myAttr"));
			Assertions.assertThat(proc.process(null, "myAttr")).isEqualTo(expected);
		}

		@Test
		void nominal() {
			var proc = InputProcessor.forClass(String.class)
				.sanitize(s -> s.trim())
				.build();

			var expected = new InputProcessorResult<>(Input.of("test", "myAttr"));
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

			var expected = new InputProcessorResult<>(Input.of(null, "myAttr"));
			Assertions.assertThat(proc.process(null, "myAttr")).isEqualTo(expected);
		}

		@Test
		void when_constraintFailed_with_staticMessage_then_exceptionWithStaticMessage() {
			var proc = InputProcessor.forClass(String.class)
				.constraint(s -> false, "should starts with 'a'")
				.build();

			var input = Input.of("best", "myAttr");
			Assertions.assertThatExceptionOfType(InputProcessorConstraintException.class)
				.isThrownBy(() -> proc.process(input))
				.withMessage("should starts with 'a'")
				.extracting(InputProcessorConstraintException::getInput).isEqualTo(input);
		}

		@Test
		void when_constraintFailed_with_dynamicMessage_then_exceptionWithDynamicMessage() {
			var proc = InputProcessor.forClass(String.class)
				.constraint(s -> false, input -> input.getName() + " should starts with 'a', but was '" + input.getValue() + "'")
				.build();

			var input = Input.of("best", "myAttr");
			Assertions.assertThatExceptionOfType(InputProcessorConstraintException.class)
				.isThrownBy(() -> proc.process(input))
				.withMessage("myAttr should starts with 'a', but was 'best'")
				.extracting(InputProcessorConstraintException::getInput).isEqualTo(input);
		}

	}

	@Nested
	class Map {

		@Test
		void nominal() {
			var proc = InputProcessor.forClass(String.class)
				.map(s -> 3L)
				.build();

			var expected = new InputProcessorResult<>(Input.of(3L, "myAttr"));
			Assertions.assertThat(proc.process("test", "myAttr")).isEqualTo(expected);
		}

	}

}
