package io.juserinput.builder.operations.constraints;

import io.juserinput.Input;

public interface Constraint<T> {

	public boolean check(Input<T> input);

	public String failMessage(Input<T> input);

}
