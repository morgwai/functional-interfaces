// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;



/**
 * {@link ThrowingTask} with up to 3 inferable {@link Exception}s.
 * Convenient for casting lambda expressions. See {@link Throwing2Computation} for detailed code
 * examples.
 */
@FunctionalInterface
public interface Throwing3Task<E1 extends Throwable, E2 extends Throwable, E3 extends Throwable>
		extends ThrowingTask<E1, E2, E3, RuntimeException> {

	void execute() throws E1, E2, E3;
}
