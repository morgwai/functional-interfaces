// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;

import java.util.concurrent.Callable;
import java.util.function.*;



/**
 * Task that can throw inferable {@link Exception} types.
 * This allows to precisely declare/infer types of {@link Exception}s thrown by lambda expressions
 * and avoid boilerplate try-catch-rethrowOrIgnore blocks. See {@link ThrowingComputation} (similar
 * interface but for non-void returning expressions) for detailed code examples.
 * @see Throwing2Task Throwing2Task subclass for convenient casting
 */
@FunctionalInterface
public interface ThrowingTask<
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



	static
	Throwing2Task<RuntimeException, RuntimeException> of(Runnable runnable) {
		return new Throwing2Task<>() {
			@Override public void execute() {
				runnable.run();
			}
			@Override public String toString() {
				return "ThrowingTask { runnable = " + runnable + " }";
			}
		};
	}



	static <T>
	Throwing2Task<RuntimeException, RuntimeException> of(Consumer<T> consumer, T param) {
		return new Throwing2Task<>() {
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
	Throwing2Task<RuntimeException, RuntimeException> of(BiConsumer<T, U> biConsumer,
			T param1, U param2) {
		return new Throwing2Task<>() {
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
