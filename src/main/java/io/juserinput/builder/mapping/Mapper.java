package io.juserinput.builder.mapping;

import java.util.function.Function;

public interface Mapper<OUT, NEW_OUT> extends Function<OUT, NEW_OUT> {

	public Class<NEW_OUT> getNewClass();

	public default String getNewClassName() {
		return getNewClass().getSimpleName();
	}

}
