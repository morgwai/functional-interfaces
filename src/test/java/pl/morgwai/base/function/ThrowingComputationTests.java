// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;

import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static pl.morgwai.base.function.Invokers.*;



public class ThrowingComputationTests {



	String noThrow() {
		return "";
	}

	@Test
	public void verifyNoExceptionInference() {
		final String ignored = invoke(this::noThrow);
	}



	String throws1() throws InterruptedException {
		return "";
	}

	@Test
	public void verifySingleExceptionInference() throws InterruptedException {
		final var ignored = invoke(this::throws1);
	}



	String throws2() throws InterruptedException, InstantiationException {
		return "";
	}

	@Test
	public void verifyThrowing2ComputationCasting()
			throws InterruptedException, InstantiationException {
		final var ignored = invoke(
			(Throwing2Computation<String, InstantiationException, InterruptedException>)
					this::throws2
		);
	}



	String throws3() throws InterruptedException, InstantiationException, ClassNotFoundException {
		return "";
	}

	@Test
	public void verifyThrowing3ComputationCasting()
			throws InterruptedException, InstantiationException, ClassNotFoundException {
		final var ignored = invoke(
			(Throwing3Computation<
				String,
				InstantiationException,
				ClassNotFoundException,
				InterruptedException
			>) this::throws3
		);
	}



	/**
	 * Compiler insists {@link ThrowingComputation#call()} never actually throws an
	 * {@link Exception} and its {@code throws} declaration may be removed: test it's propagated
	 * correctly nevertheless.
	 */
	@Test
	public void testCallableExceptionPropagation() throws Exception {
		final var thrown = new InterruptedException();
		try {
			final var ignored = call(() -> { throw thrown; });
			fail("InterruptedException expected");
		} catch (InterruptedException caught) {
			assertSame("caught Exception should be the same as thrown",
					thrown, caught);
		}
	}

	static <
		R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable
	> R call(ThrowingComputation<R, E1, E2, E3, E4> task) throws Exception {
		return task.call();
	}
}
