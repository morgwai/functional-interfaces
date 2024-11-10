// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;

import java.util.concurrent.Callable;



/**
 * {@link ThrowingTask} with up to 4 {@link Exception}s.
 * Convenient for casting lambda expressions to declare exact {@link Exception} types.
 * @see ThrowingComputation ThrowingComputation for detailed code examples.
 */
@FunctionalInterface
public interface Throwing4Task<
	E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable
> extends Runnable, Callable<Void> {



	void execute() throws E1, E2, E3, E4;



	@Override
	default void run() {
		try {
			execute();
		} catch (RuntimeException | Error e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} catch (Throwable neverHappens) {
			throw new AssertionError("unreachable code", neverHappens);
		}
	}



	@Override
	default Void call() throws Exception {
		try {
			execute();
			return null;
		} catch (Exception | Error e) {
			throw e;
		} catch (Throwable neverHappens) {
			throw new AssertionError("unreachable code", neverHappens);
		}
	}
}
