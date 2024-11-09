// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;



/**
 * {@link ThrowingComputation} with up to 2 inferable {@link Exception}s.
 * Convenient for casting lambda expressions.
 */
@FunctionalInterface
public interface Throwing2Computation<R, E1 extends Throwable, E2 extends Throwable>
	extends ThrowingComputation<R, E1, E2, RuntimeException, RuntimeException, RuntimeException> {

	R perform() throws E1, E2;
}
