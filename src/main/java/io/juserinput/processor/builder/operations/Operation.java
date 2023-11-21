package io.juserinput.processor.builder.operations;

import io.juserinput.processor.result.InputProcessorResultBuilder;
import jakarta.annotation.Nonnull;

/**
 * @param <T>
 */
interface Operation<T> {

	public void operate(@Nonnull InputProcessorResultBuilder<T> resultBuilder);

}
