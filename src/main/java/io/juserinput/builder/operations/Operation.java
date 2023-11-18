package io.juserinput.builder.operations;

import io.juserinput.result.InputProcessorResultBuilder;
import jakarta.annotation.Nonnull;

/**
 * @param <T>
 */
interface Operation<T> {

	public void operate(@Nonnull InputProcessorResultBuilder<T> resultBuilder);

}
