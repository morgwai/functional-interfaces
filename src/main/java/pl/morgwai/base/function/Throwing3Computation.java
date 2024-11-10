// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;



/**
 * {@link ThrowingComputation} with up to 3 inferable {@link Exception}s.
 * Convenient for casting lambda expressions. See {@link Throwing2Computation} subclass for detailed
 * code examples.
 */
@FunctionalInterface
public interface Throwing3Computation<
	R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable
> extends ThrowingComputation<R, E1, E2, E3, RuntimeException> {

	R perform() throws E1, E2, E3;
}
