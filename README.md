# FinCalc_Br

A comprehensive financial calculator app for Android built with Kotlin and Jetpack Compose, designed specifically for Brazilian financial calculations.

## Features

FinCalc_Br includes the following financial calculators:

- **Basic Calculator**: Expression evaluation with support for basic arithmetic operations
- **Simple Interest**: Calculate simple interest (Juros Simples) investments
- **Compound Interest**: Calculate compound interest (Juros Compostos) investments
- **Net Salary**: Calculate net salary (Salário Líquido) according to Brazilian CLT regulations
- **Overtime**: Calculate overtime pay (Horas Extras) based on Brazilian labor laws
- **Vacation**: Calculate vacation pay (Férias) including constitutional bonus

## Screenshots

*(Add screenshots of the app here showing each calculator screen)*

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Navigation**: Jetpack Compose Navigation
- **Coroutines**: Kotlin Coroutines for asynchronous operations
- **JSON Parsing**: Kotlin Serialization
- **Logging**: Timber
- **Testing**: JUnit

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/FinCalc_Br.git
```

2. Open the project in Android Studio Flamingo or later

3. Build and run the app on an Android emulator or physical device (minSdk 26)

## Usage

Upon launching the app, you'll see a navigation drawer allowing you to switch between different financial calculators:

1. Select the desired calculator from the navigation menu
2. Enter the required values in the input fields
3. View the calculated results instantly
4. Use the clear button to reset inputs when needed

## Running Tests

To run unit tests:
```bash
./gradlew :app:testDebugUnitTest
```

To run instrumented tests:
```bash
./gradlew :app:connectedAndroidTest
```

To run all checks (lint, tests, etc.):
```bash
./gradlew :app:check
```

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   └── java/com/financalcbr/app/
│   │       ├── ui/
│   │       │   ├── features/          # Calculator screens and ViewModels
│   │       │   │   ├── calculator/    # Basic calculator
│   │       │   │   ├── simple_interest/ # Simple interest calculator
│   │       │   │   ├── compound_interest/ # Compound interest calculator
│   │       │   │   ├── net_salary/    # Net salary calculator (CLT)
│   │       │   │   ├── overtime/      # Overtime calculator
│   │       │   │   └── vacation/      # Vacation calculator
│   │       │   ├── components/        # Reusable UI components
│   │       │   ├── common/            # Common utilities (transformations, etc.)
│   │       │   ├── navigation/        # Navigation configuration
│   │       │   └── theme/             # Material Design theming
│   │       ├── domain/                # Business logic (UseCases)
│   │       │   └── calculator/        # Financial calculation use cases
│   │       ├── utils/                 # Utility classes
│   │       └── FinanCalcBR.kt         # Application class
│   └── res/                           # Resources
└── build.gradle.kts                   # App build configuration
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please ensure to update tests as appropriate and follow the existing code style.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Jetpack Compose team for the modern UI toolkit
- Hilt team for dependency injection solutions
- All open-source libraries used in this project

