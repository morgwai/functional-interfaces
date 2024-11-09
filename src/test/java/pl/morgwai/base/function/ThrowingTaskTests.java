// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;

import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static pl.morgwai.base.function.Invokers.*;



public class ThrowingTaskTests {



	void noThrow() {}

	@Test
	public void verifyNoExceptionInference() {
		invoke(this::noThrow);
	}



	void throws1() throws InterruptedException {}

	@Test
	public void verifySingleExceptionInference() throws InterruptedException {
		invoke(this::throws1);
	}



	void throws2() throws InterruptedException, InstantiationException {}

	@Test
	public void verifyThrowing2RunnableCasting()
			throws InterruptedException, InstantiationException {
		invoke((Throwing2Task<InstantiationException, InterruptedException>) this::throws2);
	}



	/**
	 * Compiler insists {@link ThrowingTask#call()} never actually throws an {@link Exception} and
	 * its {@code throws} declaration may be removed: test it's propagated correctly nevertheless.
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
		E1 extends Throwable,
		E2 extends Throwable,
		E3 extends Throwable,
		E4 extends Throwable,
		E5 extends Throwable
	> Void call(ThrowingTask<E1, E2, E3, E4, E5> task) throws Exception {
		return task.call();
	}
}
