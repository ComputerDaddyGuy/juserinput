package io.juserinput.processor;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class InputProcessorResultTest {

	@Nested
	class Equals {

		@Test
		void differentValue_notEquals() {

			var result1 = new InputProcessorResult<>(Input.of("a", "myAttr"));
			var result2 = new InputProcessorResult<>(Input.of("b", "myAttr"));
			Assertions.assertThat(result1).isNotEqualTo(result2);
		}

		@Test
		void differentAttrName_notEquals() {
			var result1 = new InputProcessorResult<>(Input.of("a", "myAttr1"));
			var result2 = new InputProcessorResult<>(Input.of("a", "myAttr2"));
			Assertions.assertThat(result1).isNotEqualTo(result2);
		}

		@Test
		void sameValue_and_sameAttrName_equals() {
			var result1 = new InputProcessorResult<>(Input.of("a", "myAttr"));
			var result2 = new InputProcessorResult<>(Input.of("a", "myAttr"));
			Assertions.assertThat(result1).isEqualTo(result2);
			Assertions.assertThat(result1.hashCode()).isEqualTo(result2.hashCode());
		}

	}

	@Nested
	class AsOptional {

		@Test
		void nullValue_isValidOptional() {
			var result = new InputProcessorResult<>(Input.of(null, "myAttr"));
			Assertions.assertThat(result.getName()).isEqualTo("myAttr");
			Assertions.assertThat(result.asOptional()).isEmpty();
		}

		@Test
		void nonNullValue_isValidOptional() {
			var result = new InputProcessorResult<>(Input.of("a", "myAttr"));
			Assertions.assertThat(result.getName()).isEqualTo("myAttr");
			Assertions.assertThat(result.asOptional()).contains("a");
		}

	}

	@Nested
	class AsRequired {

		@Test
		void nullValue_failsForREquired() {
			var result = new InputProcessorResult<>(Input.of(null, "myAttr"));
			Assertions.assertThat(result.getName()).isEqualTo("myAttr");
			Assertions.assertThatExceptionOfType(InputProcessorNoValueException.class)
				.isThrownBy(() -> result.asRequired())
				.withMessage("myAttr has no value (null)");
		}

		@Test
		void nonNullValue_successForRequired() {
			var result = new InputProcessorResult<>(Input.of("a", "myAttr"));
			Assertions.assertThat(result.getName()).isEqualTo("myAttr");
			Assertions.assertThat(result.asRequired()).contains("a");
		}

	}

}
