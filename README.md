# Functional Interfaces

Collection of Java functional interfaces.<br/>
Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0<br/>
<br/>
**latest release: [pending](https://search.maven.org/artifact/pl.morgwai.base/functional-interfaces/pending/jar)**
([javadoc](https://javadoc.io/doc/pl.morgwai.base/functional-interfaces/pending))


## MAIN USER CLASSES

### [ThrowingTask](https://javadoc.io/doc/pl.morgwai.base/functional-interfaces/latest/pl/morgwai/base/function/ThrowingTask.html)
Task that can throw inferable `Exception` types. This allows to precisely declare/infer types of `Exception`s thrown by lambda expressions and avoid boilerplate try-catch-rethrowOrIgnore blocks.

### [ThrowingComputation](https://javadoc.io/doc/pl.morgwai.base/functional-interfaces/latest/pl/morgwai/base/function/ThrowingComputation.html)
Similar to `ThrowingTask` but for non-void returning expressions.
