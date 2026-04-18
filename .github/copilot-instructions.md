# Copilot Instructions

## Build, Test, and Format

```bash
# Build and test all modules
./gradlew build

# Test a single module
./gradlew :xc-core:test
./gradlew :collage:test
./gradlew :jst:test

# Run a single test class
./gradlew :xc-core:test --tests "org.xcommand.threadcontext.ContextStackTest"

# Apply code formatting (Palantir Java Format via Spotless)
./gradlew spotlessApply

# Check for dependency updates
./gradlew dependencyUpdates
```

Java toolchain: **Java 25** (auto-downloaded via Foojay resolver).

## Architecture

This is a multi-module Gradle project implementing an **extended Command pattern** with a thread-scoped context system.

### Modules

- **`xc-core`** — Core framework: commands, thread context, DynaBean proxies, data structures, state machine, web utilities.
- **`collage`** — Template engine with ANTLR-generated parser (`TemplateLexer.g4` / `TemplateParser.g4`); depends on `xc-core`.
- **`jst`** — Java Server Templates with ANTLR-generated parser (`JSTLexer.g4` / `JSTParser.g4`); depends on `xc-core`.

### Core Concepts

**`ICommand`** — Functional interface with a single `execute()` method. Commands are the primary unit of behaviour.

**`TCP` (ThreadContext Provider)** — Manages a per-thread stack of `Map<String, Object>` using Java's `ScopedValue`. All context-aware code runs inside `TCP.start(() -> { ... })`. Contexts can be pushed/popped for scoped overrides, and an inheritable context can be created via `TCP.newInheritableContext()`.

**`AC` (ApplicationContext)** — Static singleton `Map<String, Object>` for application-wide data (initialised before `TCP.start`).

**Context Views (CV)** — Typed, interface-based facades over the thread context map. An interface named `IFooCV` with `getFoo()`/`setFoo()` methods is backed by a dynamic proxy (`DynaBeanProvider`) that reads/writes the current `TCP` context. The key stored in the map is `InterfaceName.PropertyName` (e.g., `"org.xcommand.example.commands.IEchoCV.Message"`).

**`DynaBeanProvider`** — Creates context-view proxies. The standard variant is `DynaBeanProvider.newThreadClassMethodInstance()` (key = class + method name). Alternatives: `newThreadMethodInstance()` (method name only) and `newThreadObjectIdentityInstance()` (object identity).

**`ContextViews`** — Singleton providing pre-wired CV instances for the application (e.g., `ContextViews.get().echoCV`).

### ANTLR Code Generation

Both `collage` and `jst` generate parser/lexer source during `compileJava`. Generated sources land under `build/generated-src/antlr/main` and are committed hand-edited tokens files are under `src/main/antlr/gen/` and `src/main/gen/`.

## Key Conventions

**CV interface naming** — Context View interfaces are prefixed with `I` and suffixed with `CV` (e.g., `IEchoCV`, `ITreeNodeCV`, `IStateCV`).

**Null safety** — NullAway (ErrorProne) is enforced at **ERROR** severity for all packages under `org.xcommand`. Annotate nullable return types and parameters with `@org.jspecify.annotations.Nullable`. NullAway is disabled on test source sets.

**Tests must run inside `TCP.start`** — Any test exercising CV or context-aware code must be wrapped in `TCP.start(() -> { ... })` because the `ScopedValue` holding the context stack is only bound inside that scope.

**Mockito agent** — `xc-core` wires the Mockito Java agent via a `mockitoAgent` configuration and JVM arg so inline mocking works without extra setup.

**Code formatting** — All Java source is formatted with **Palantir Java Format** via the Spotless plugin. Run `./gradlew spotlessApply` before committing. The `build/**` directory is excluded from Spotless.

**Lombok** — Used in `collage` and `jst` for boilerplate reduction; not used in `xc-core`.

**RecordBuilder** — Used in `xc-core` and `jst` (`@RecordBuilder`) to auto-generate builder classes for Java records.

**Build logic** — Shared Gradle convention plugins live in `buildSrc/src/main/groovy/` (e.g., `buildlogic.java-library-conventions.gradle`). All submodules apply `buildlogic.java-library-conventions`.
