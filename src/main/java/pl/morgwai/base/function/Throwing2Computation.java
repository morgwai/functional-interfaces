// Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0
package pl.morgwai.base.function;



/**
 * {@link ThrowingComputation} with up to 2 inferable {@link Exception}s.
 * Convenient for casting lambda expressions.
 * <p>
 * Java compiler as of version 11 is able to accurately infer types of 0 or 1 {@link Exception}
 * thrown by a lambda expression (inferring {@link RuntimeException} to "fill the blanks" where
 * needed). Therefore if a lambda throws 2, 3 or 4 {@link Exception}s, it is necessary to cast
 * such an expression to {@code Throwing2Computation}, {@link Throwing3Computation} or
 * {@link ThrowingComputation} respectively with specific type arguments:</p>
 * <pre>{@code
 * String fetchFromDbAndProcess(long id) throws IOException, MalformedDataException;
 *
 * String fetchAndProcessWithinTx(long id) throws IOException, MalformedDataException {
 *     return runWithinTx(
 *         (Throwing2Computation<String, IOException, MalformedDataException>)
 *                 () -> fetchFromDbAndProcess(id)
 *     );
 * }}</pre>
 */
@FunctionalInterface
public interface Throwing2Computation<R, E1 extends Throwable, E2 extends Throwable>
		extends Throwing3Computation<R, E1, E2, RuntimeException> {

	R perform() throws E1, E2;
}
