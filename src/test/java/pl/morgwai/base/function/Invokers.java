// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;



/**
 * Set of overloaded static functions to test type inference of {@link ThrowingTask} and
 * {@link ThrowingComputation}.
 */
public interface Invokers {



	static <
		R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable
	> R invoke(Throwing4Computation<R, E1, E2, E3, E4> task) throws E1, E2, E3, E4 {
		return task.perform();
	}



	static <
		E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable
	> void invoke(Throwing4Task<E1, E2, E3, E4> task) throws E1, E2, E3, E4 {
		task.execute();
	}



	static void invoke(Runnable task) {
		task.run();
	}
}
