// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;

import java.util.concurrent.Callable;
import java.util.function.*;



/**
 * {@link Callable} that can throw inferable {@link Exception} types.
 * This allows to precisely declare/infer types of {@link Exception}s thrown by lambda expressions
 * and avoid boilerplate try-catch-rethrowOrIgnore blocks.
 * <p>
 * Similarly with {@link ThrowingTask} and {@link Throwing2Task}, there's
 * {@link Throwing2Computation} subclass for convenient casting.</p>
 */
@FunctionalInterface
public interface ThrowingComputation<
	R,
	E1 extends Throwable,
	E2 extends Throwable,
	E3 extends Throwable,
	E4 extends Throwable,
	E5 extends Throwable
> extends Runnable, Callable<R> {



	R perform() throws E1, E2, E3, E4, E5;



	@Override
	default void run() {
		try {
			perform();
		} catch (RuntimeException | Error e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} catch (Throwable neverHappens) {
			throw new AssertionError("unreachable code", neverHappens);
		}
	}



	@Override
	default R call() throws Exception {
		try {
			return perform();
		} catch (Exception | Error e) {
			throw e;
		} catch (Throwable neverHappens) {
			throw new AssertionError("unreachable code", neverHappens);
		}
	}



	static <
		E1 extends Throwable,
		E2 extends Throwable,
		E3 extends Throwable,
		E4 extends Throwable,
		E5 extends Throwable
	>
	ThrowingComputation<Void, E1, E2, E3, E4, E5> of(ThrowingTask<E1, E2, E3, E4, E5> throwingTask)
	{
		return new ThrowingComputation<>() {
			@Override public Void perform() throws E1, E2, E3, E4, E5 {
				throwingTask.execute();
				return null;
			}
			@Override public String toString() {
				return "ThrowingComputation { throwingTask = " + throwingTask + " }";
			}
		};
	}



	static <R> Throwing2Computation<R, Exception, RuntimeException> of(Callable<R> callable) {
		return new Throwing2Computation<>() {
			@Override public R perform() throws Exception {
				return callable.call();
			}
			@Override public String toString() {
				return "ThrowingComputation { callable = " + callable + " }";
			}
		};
	}



	static <T, R>
	Throwing2Computation<R, RuntimeException, RuntimeException> of(Function<T, R> function, T param)
	{
		return new Throwing2Computation<>() {
			@Override public R perform() {
				return function.apply(param);
			}
			@Override public String toString() {
				final var q = quote(param);
				return "ThrowingComputation { function = " + function + ", param = " + q + param + q
						+ " }";
			}
		};
	}



	static <T, U, R> Throwing2Computation<R, RuntimeException, RuntimeException> of(
		BiFunction<T, U, R> biFunction,
		T param1,
		U param2
	) {
		return new Throwing2Computation<>() {
			@Override public R perform() {
				return biFunction.apply(param1, param2);
			}
			@Override public String toString() {
				final var q1 = quote(param1);
				final var q2 = quote(param2);
				return "ThrowingComputation { biFunction = " + biFunction + ", param1 = " + q1
						+ param1 + q1 + ", param2 = " + q2 + param2 + q2 + " }";
			}
		};
	}



	static <R>
	Throwing2Computation<R, RuntimeException, RuntimeException> ofSupplier(Supplier<R> supplier) {
		return new Throwing2Computation<>() {
			@Override public R perform() {
				return supplier.get();
			}
			@Override public String toString() {
				return "ThrowingComputation { supplier = " + supplier + " }";
			}
		};
	}



	private static String quote(Object o) {
		return o instanceof String ? "\"" : o instanceof Character ? "'" : "";
	}
}
