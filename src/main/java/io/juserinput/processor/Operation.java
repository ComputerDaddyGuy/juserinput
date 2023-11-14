package io.juserinput.processor;

import java.util.function.Function;
import java.util.function.Predicate;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.NonNull;

sealed interface Operation<T> {

	public T operate(@Nonnull Input<T> input);

	/**
	 * 
	 * 
	 *
	 */
	@AllArgsConstructor
	final class SanitizationOperation<T> implements Operation<T> {

		private final @NonNull Function<T, T> sanitizeFunction;

		@Override
		public T operate(@Nonnull Input<T> input) {
			if (input.getValue() == null) {
				return null;
			}
			return sanitizeFunction.apply(input.getValue());
		}

	}

	@AllArgsConstructor
	final class ConstraintCheckOperation<T> implements Operation<T> {

		private final @NonNull Predicate<T> constraintPredicate;
		private final @NonNull Function<Input<T>, String> constraintMessage;

		@Override
		public T operate(@Nonnull Input<T> input) {
			if (input.getValue() == null) {
				return null;
			}
			if (!constraintPredicate.test(input.getValue())) {
				throw new InputProcessorConstraintException(input, constraintMessage.apply(input));
			}
			return input.getValue();
		}

	}

}
