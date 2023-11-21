package io.juserinput.processor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.juserinput.processor.InputProcessor;
import io.juserinput.processor.errors.InputProcessorError;
import io.juserinput.processor.result.InputProcessorResultAssert;
import io.juserinput.processor.result.InputProcessorResultBuilder;

class InputProcessorTest {

	@Nested
	class Transformation {

		@Test
		void given_nullValue_when_transforming_then_transformationIsIgnored() {
			var proc = InputProcessor.forClass(String.class)
				.transform(s -> s.trim())
				.build();

			var actual = proc.process("myAttr", null);

			var expected = InputProcessorResultBuilder.newInstance("myAttr", null).build();
			Assertions.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void given_exceptionThrownInTransformer_when_transforming_then_exceptionIsActuallyThrown() {
			var proc = InputProcessor.forClass(String.class)
				.transform(s -> {
					throw new NullPointerException("Test NPE");
				})
				.build();

			Assertions.assertThatNullPointerException()
				.isThrownBy(() -> proc.process("myAttr", "test"))
				.withMessage("Test NPE");
		}

		@Test
		void nominal() {
			var proc = InputProcessor.forClass(String.class)
				.transform(s -> s.trim())
				.build();

			var actual = proc.process("myAttr", " test ");

			var expected = InputProcessorResultBuilder.newInstance("myAttr", "test").build();
			Assertions.assertThat(actual)
				.isEqualTo(expected);
		}

	}

	@Nested
	class Validation {

		@Test
		void null_isNot_concernedByValidation() {
			var proc = InputProcessor.forClass(String.class)
				.validate(s -> true, "value should starts with 'a'")
				.build();

			var actual = proc.process("myAttr", null);

			var expected = InputProcessorResultBuilder.newInstance("myAttr", null).build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void when_validationFailed_with_staticMessage_then_exceptionWithStaticMessage() {
			var proc = InputProcessor.forClass(String.class)
				.validate(s -> false, "should starts with 'a'")
				.build();

			var actual = proc.process("myAttr", "best");

			var expected = InputProcessorResultBuilder
				.newInstance("myAttr", "best")
				.addError(InputProcessorError.newError("myAttr", "should starts with 'a'"))
				.build();

			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void when_validationFailed_with_dynamicMessage_then_errorWithDynamicMessage() {
			var proc = InputProcessor.forClass(String.class)
				.validate(s -> false, input -> input.getName() + " should starts with 'a', but was '" + input.getValue() + "'")
				.build();

			var actual = proc.process("myAttr", "best");

			var expected = InputProcessorResultBuilder
				.newInstance("myAttr", "best")
				.addError(InputProcessorError.newError("myAttr", "myAttr should starts with 'a', but was 'best'"))
				.build();

			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

	}

	@Nested
	class Chaining {

		@Test
		void given_multipleTransformationProcessors_when_theyAreChained_then_allTransformationsAreApplied() {
			var proc1 = InputProcessor.forClass(String.class)
				.transform(s -> s.trim())
				.build();
			var proc2 = InputProcessor.forClass(String.class)
				.transform(s -> s.toUpperCase())
				.build();

			var actual = proc1.then(proc2).process("myAttr", " test ");

			var expected = InputProcessorResultBuilder.newInstance("myAttr", "TEST").build();
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

			var expected = InputProcessorResultBuilder.newInstance("myAttr", null).build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void nominal() {
			var proc = InputProcessor.forClass(String.class)
				.map(Long.class, Long::parseLong)
				.build();

			var actual = proc.process("myAttr", "3");

			var expected = InputProcessorResultBuilder.newInstance("myAttr", 3L).build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

		@Test
		void when_mappingFailed_then_error() {
			var proc = InputProcessor.forClass(String.class)
				.map(Long.class, Long::parseLong)
				.build();

			var actual = proc.process("myAttr", "notALong");

			var expected = InputProcessorResultBuilder
				.newInstance("myAttr", "notALong")
				.addError(InputProcessorError.newError("myAttr", "myAttr is not a valid Long"))
				.build();
			InputProcessorResultAssert.assertThat(actual)
				.isEqualTo(expected);
		}

	}

}
