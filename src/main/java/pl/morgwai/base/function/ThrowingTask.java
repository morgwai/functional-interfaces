// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;

import java.util.function.*;



/**
 * Task that can throw inferable {@link Exception} types.
 * This allows to precisely declare/infer types of {@link Exception}s thrown by lambda expressions
 * and avoid boilerplate try-catch-rethrowOrIgnore blocks.
 * @see ThrowingComputation guidelines and detailed code examples for ThrowingComputation (similar
 *     interface for non-void returning expressions)
 */
@FunctionalInterface
public interface ThrowingTask<E1 extends Throwable, E2 extends Throwable>
		extends Throwing3Task<E1, E2, RuntimeException> {



	void execute() throws E1, E2;



	static
	ThrowingTask<RuntimeException, RuntimeException> of(Runnable runnable) {
		return new ThrowingTask<>() {
			@Override public void execute() {
				runnable.run();
			}
			@Override public String toString() {
				return "ThrowingTask { runnable = " + runnable + " }";
			}
		};
	}



	static <T>
	ThrowingTask<RuntimeException, RuntimeException> of(Consumer<T> consumer, T param) {
		return new ThrowingTask<>() {
			@Override public void execute() {
				consumer.accept(param);
			}
			@Override public String toString() {
				final var q = quote(param);
				return "ThrowingTask { consumer = " + consumer + ", param = " + q + param + q
						+ " }";
			}
		};
	}



	static <T, U>
	ThrowingTask<RuntimeException, RuntimeException> of(BiConsumer<T, U> biConsumer,
			T param1, U param2) {
		return new ThrowingTask<>() {
			@Override public void execute() {
				biConsumer.accept(param1, param2);
			}
			@Override public String toString() {
				final var q1 = quote(param1);
				final var q2 = quote(param2);
				return "ThrowingTask { biConsumer = " + biConsumer + ", param1 = " + q1 + param1
						+ q1 + ", param2 = " + q2 + param2 + q2 + " }";
			}
		};
	}



	private static String quote(Object o) {
		return o instanceof String ? "\"" : o instanceof Character ? "'" : "";
	}
}
