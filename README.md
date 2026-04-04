# TryOnce — Authentication Platform

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.09-4285F4?style=flat&logo=jetpackcompose&logoColor=white)
![Hilt](https://img.shields.io/badge/Hilt-2.52-FF6F00?style=flat&logo=google&logoColor=white)
![Tests](https://img.shields.io/badge/Unit%20Tests-10%20passing-22C55E?style=flat)
![CI](https://github.com/YOURUSERNAME/tryitonce/actions/workflows/ci.yml/badge.svg)

> Production-grade Android authentication platform built with Kotlin, Jetpack Compose, Hilt, and Clean Architecture. Designed to connect to a real REST backend — swap one line to go from mock to production.

---

## Demo

> Register → Login → Home → Logout — full auth flow

*(Add your demo GIF here after recording)*

---

## Screenshots

| Login | Register | Home |
|-------|----------|------|
| tryitonce/ScreenShots/LoginScreen.png | tryitonce/ScreenShots/CreateAccount.png | tryitonce/ScreenShots/HomeScreen.png |

---

## Architecture

Clean Architecture — 3 layers, zero cross-layer leakage:

```
Presentation  →  Domain  ←  Data
(Compose UI)    (UseCases,   (Repository
(ViewModels)     Models,      Impl,
(UiState)        Interfaces)  DataStore)
```

The domain layer has **zero Android imports** — pure Kotlin. Every ViewModel, screen, and use case is independently unit testable.

---

## Tech Stack

| Category | Technology |
|----------|-----------|
| Language | Kotlin 2.0 + Coroutines |
| UI | Jetpack Compose + Material 3 |
| DI | Hilt 2.52 — compile-time verified |
| Navigation | Navigation Compose — nested graphs |
| Persistence | DataStore Preferences — async, coroutine-safe |
| State | StateFlow + collectAsStateWithLifecycle |
| Testing | JUnit5 + MockK + Turbine + Truth |
| CI/CD | GitHub Actions |

---

## Project Structure

```
com.TRY.tryitonce/
├── data/
│   ├── local/TokenManager.kt         # JWT stored in DataStore
│   └── repository/
│       ├── MockAuthRepositoryImpl.kt # Works without a backend
│       └── AuthRepositoryImpl.kt     # Connects to real API
├── domain/
│   ├── model/User.kt                 # Core domain entity
│   ├── repository/AuthRepository.kt  # Contract interface
│   └── usecase/UseCases.kt           # Login, Register, Logout
├── ui/
│   ├── auth/login/                   # LoginScreen + ViewModel + UiState
│   ├── auth/register/                # RegisterScreen + ViewModel
│   ├── home/                         # HomeScreen + ViewModel
│   ├── navigation/                   # AppNavigation + Screen
│   ├── components/                   # AuthTextField, LoadingButton
│   └── theme/                        # Colors, Typography, Theme
└── di/
    └── RepositoryModule.kt           # @Binds interface → implementation
```

---

## Running Locally

```bash
# Clone
git clone https://github.com/YOURUSERNAME/tryitonce.git

# Open in Android Studio
# File → Open → select tryitonce folder

# Run on API 34 emulator (recommended)
# Build → Run
```

### Run tests
```bash
./gradlew test
```

---

## Key Engineering Decisions

**`@Binds` not `@Provides`** — for interface-to-implementation binding, `@Binds` generates no extra factory class. More efficient, cleaner graph.

**`StateFlow` not `LiveData`** — coroutine-native, no Android dependency, testable without `InstantTaskExecutorRule` workarounds on modern code.

**`Resource<T>` sealed class** — wraps every async result. Compiler enforces handling Loading/Success/Error — no unhandled null states.

**Mock → Real backend in one line** — change `MockAuthRepositoryImpl` to `AuthRepositoryImpl` in `RepositoryModule.kt`. Zero other changes.

---

## Unit Tests

| Test Class | Covers |
|-----------|--------|
| `ValidationUtilTest` | Email, password, name validation — pure functions, zero mocks |
| `LoginUseCaseTest` | Validation blocks network calls — `coVerify(exactly = 0)` |
| `RegisterUseCaseTest` | Password mismatch, whitespace trimming |
| `LoginViewModelTest` | StateFlow emissions, loading state — Turbine |
| `RegisterViewModelTest` | Field-level error routing, success navigation |

---

## Roadmap

- [x] Clean Architecture (data → domain → presentation)
- [x] Hilt dependency injection
- [x] JWT token management with DataStore
- [x] Rotating refresh tokens with replay detection design
- [x] 10 unit tests
- [x] CI/CD via GitHub Actions
- [x] Mock backend (works without a server)
- [ ] Real Ktor + PostgreSQL backend connection
- [ ] Biometric authentication
- [ ] MFA / TOTP support

---

## Related

[auth-platform](https://github.com/YOURUSERNAME/auth-platform) — Full backend architecture: adversarial security design, risk engine, database schema, CI/CD pipeline, deployment.

---

<p align="center">Built with Kotlin · Jetpack Compose · Hilt · Clean Architecture</p>
# tryitonce
