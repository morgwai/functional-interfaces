# Functional Interfaces

Collection of Java functional interfaces.<br/>
Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0<br/>
<br/>
**latest release: [1.0](https://search.maven.org/artifact/pl.morgwai.base/functional-interfaces/1.0/jar)**
([javadoc](https://javadoc.io/doc/pl.morgwai.base/functional-interfaces/1.0))


## MAIN USER CLASSES

### [ThrowingComputation](https://javadoc.io/doc/pl.morgwai.base/functional-interfaces/latest/pl/morgwai/base/function/ThrowingComputation.html)
Similar to `Callable` but can throw inferable `Exception` types. This allows to precisely declare/infer types of `Exceptions` thrown by lambda expressions and avoid boilerplate try-catch-rethrowOrIgnore blocks.

### [ThrowingTask](https://javadoc.io/doc/pl.morgwai.base/functional-interfaces/latest/pl/morgwai/base/function/ThrowingTask.html)
Similar to `ThrowingComputation` but for void-returning tasks.
