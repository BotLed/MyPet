Working with Gradle:

`java --version` you should be running v23

`./gradlew run` - runs main class in GameLauncher (brings up the application). This compiles and executes the code.

`./gradlew build` - run before final deployment and make sure it passes tests (when we setup the tests)

`./gradlew test` - when we make the JUnit 5 test gradle will auto run them all

Why are we using Gradle???:
• Automates build, run and test processes
• Manages the dependencies automatically (JavaFX JUnit)
• Reduces MANUAL library setup
• Ensures everyone is using the same setup (it will auto download the correct versiosn and libraries like JavaFX and JUnit)

Why JavaFX instead of Swing???:
• Actively maintained (more support, less bugs, nothing outdated)
• Just makes better UI
• Works best with Gradle
• Swing simpler but lacks visual features
• Supports CSS styling
