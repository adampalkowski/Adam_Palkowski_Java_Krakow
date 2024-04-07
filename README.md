# Adam_Pałkowski_Java_Krakow


## Prerequisites

- Java 17 or newer installed
- Gradle installed (Optional for running tests)

## Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/adampalkowski/Adam_Palkowski_Java_Krakow
   cd Adam_Palkowski_Java_Krakow
   ```
2. Build the project
   ```bash
   ./gradlew build

   ```
3. Run the tests
   ```bash
   ./gradlew test
   ```
   
## Project Structure
- `src/main/java/com/ocado/basket`: Contains the main Java source code.
- `src/test/java/com/ocado/basket`: Contains the test code.

## Configuration

- The configuration file is located at `src/test/resources/config.json`.
- Basket-1 and Basket-2 is located at `src/test/resources`.
  
## Running the Tests

- Tests can be executed using the Gradle task `test`.
- Test files are located under `src/test/java`.
