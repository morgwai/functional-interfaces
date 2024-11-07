# Functional Interfaces

Collection of Java functional interfaces.
Copyright 2024 Piotr Morgwai Kotarbinski, Licensed under the Apache License, Version 2.0<br/>
<br/>
**latest release: [pending](https://search.maven.org/artifact/pl.morgwai.base/functional-interfaces/pending/jar)**
([javadoc](https://javadoc.io/doc/pl.morgwai.base/functional-interfaces/pending))


## MAIN USER CLASSES

### [ThrowingTask](https://javadoc.io/doc/pl.morgwai.base/functional-interfaces/latest/pl/morgwai/base/function/ThrowingTask.html)
Task that can throw "configurable" `Exception` types. This allows to precisely declare thrown types and avoid boilerplate try-catch-rethrow blocks.

### [Throwing5Task](https://javadoc.io/doc/pl.morgwai.base/functional-interfaces/latest/pl/morgwai/base/function/ThrowingTask.html)
`ThrowingTask`'s super class with 5 "configurable" `Exception` types.
