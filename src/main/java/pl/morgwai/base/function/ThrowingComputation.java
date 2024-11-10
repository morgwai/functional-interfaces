// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;

import java.util.concurrent.Callable;
import java.util.function.*;



/**
 * Similar to {@link Callable} but can throw inferable {@link Exception} types.
 * This allows to precisely declare/infer types of {@link Exception}s thrown by lambda expressions
 * and avoid boilerplate try-catch-rethrowOrIgnore blocks.
 * <p>
 * <b>Example:</b><br/>
 * Given some external function:</p>
 * <pre>{@code
 * String fetchFromDbAndProcess(long id) throws IOException;}</pre>
 * <p>The following code:</p>
 * <pre>{@code
 * <R>
 * R runWithinTx(Callable<R> dbOperation) throws Exception {
 *     // start a TX, invoke `dbOperation.call()`, commit the TX if nothing was thrown
 * }
 *
 * String fetchAndProcessWithinTx(long id) throws IOException {
 *     try {
 *         return runWithinTx(() -> fetchFromDbAndProcess(id));
 *     } catch (IOException | RuntimeException e) {
 *         throw e;
 *     } catch (Exception neverHappens) {
 *         throw new AssertionError("unreachable code", neverHappens);
 *     }
 * }}</pre>
 * <p>...May be replaced with:</p>
 * <pre>{@code
 * <R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable>
 * R runWithinTx(Throwing4Computation<R, E1, E2, E3, E4> dbOperation) throws E1, E2, E3, E4 {
 *     // almost identical as before: just replace `call` with `perform`
 * }
 *
 * String fetchAndProcessWithinTx(long id) throws IOException {
 *     return runWithinTx(() -> fetchFromDbAndProcess(id));
 * }}</pre>
 * <p>
 * Public API methods that are intended to support {@link Exception} type inference of argument
 * expressions should use {@link Throwing4Computation} as their param type to allow more
 * {@link Exception}s thrown by their argument expressions.<br/>
 * Such methods usually should also be overloaded with a variant accepting {@link Throwing4Task}
 * param instead for void-returning expressions:</p>
 * <pre>{@code
 * <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable>
 * void runWithinTx(Throwing4Task<E1, E2, E3, E4> dbOperation) throws E1, E2, E3, E4 {
 *     runWithinTx(ThrowingComputation.of(dbOperation);
 * }}</pre>
 * <p>
 * Java compiler as of version 11 is able to accurately infer types of 0 or 1 {@link Exception}
 * thrown by a lambda expression (inferring {@link RuntimeException} to "fill the blanks" where
 * needed). Therefore if a lambda throws 2, 3 or 4 checked {@link Exception}s, it is necessary to
 * cast it to {@code ThrowingComputation}, {@link Throwing3Computation},
 * {@link Throwing4Computation} respectively (or {@link ThrowingTask}, {@link Throwing3Task},
 * {@link Throwing4Task}) with explicit type arguments:</p>
 * <pre>{@code
 * String fetchFromDbAndDoSthElse(long id) throws IOException, MalformedDataException;
 *
 * String fetchFromDbAndDoSthElseWithinTx(long id) throws IOException, MalformedDataException {
 *     return runWithinTx(
 *         (ThrowingComputation<String, IOException, MalformedDataException>)
 *                 () -> fetchFromDbAndDoSthElse(id)
 *     );
 * }}</pre>
 */
@FunctionalInterface
public interface ThrowingComputation<R, E1 extends Throwable, E2 extends Throwable>
		extends Throwing3Computation<R, E1, E2, RuntimeException> {



	R perform() throws E1, E2;



	static <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable>
	Throwing4Computation<Void, E1, E2, E3, E4> of(Throwing4Task<E1, E2, E3, E4> throwingTask) {
		return new Throwing4Computation<>() {
			@Override public Void perform() throws E1, E2, E3, E4 {
				throwingTask.execute();
				return null;
			}
			@Override public String toString() {
				return "ThrowingComputation { throwingTask = " + throwingTask + " }";
			}
		};
	}



	static <R>
	ThrowingComputation<R, Exception, RuntimeException> of(Callable<R> callable) {
		return new ThrowingComputation<>() {
			@Override public R perform() throws Exception {
				return callable.call();
			}
			@Override public String toString() {
				return "ThrowingComputation { callable = " + callable + " }";
			}
		};
	}



	static <T, R>
	ThrowingComputation<R, RuntimeException, RuntimeException> of(Function<T, R> function, T param)
	{
		return new ThrowingComputation<>() {
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



	static <T, U, R>
	ThrowingComputation<R, RuntimeException, RuntimeException> of(BiFunction<T, U, R> biFunction,
			T param1, U param2) {
		return new ThrowingComputation<>() {
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
	ThrowingComputation<R, RuntimeException, RuntimeException> ofSupplier(Supplier<R> supplier) {
		return new ThrowingComputation<>() {
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
