# Quality Engineering Automation

## Instructions to Execute the Test Cases

1. **Clone the Repository**:
   ```bash
   git clone <repository_url>
   cd <repository_directory>
   
2. **Install dependencies**:

	if using maven - mvn clean install
	if using gradle - ./gradlew build
	
3. **Execute Test Cases:**:
	if using maven - mvn clean test
	if using gradle - ./gradlew test
	
4. **Generate Reports**:
   allure generate --clean
5. 
5. **View Logs and Screenshots**:

Logs and screenshots for each test step, including failures, will be stored in the designated logs and screenshots directory within the project.

6. **Handling Pop-ups**:

**Conclusion**:
The framework includes mechanisms to handle intermittent and ad-hoc pop-ups. Ensure the pop-up handling code is active in your test cases.
Exception Handling:

The framework includes comprehensive exception handling. Check the log files for detailed error messages in case of failures.
Conclusion
This framework is designed to be robust, modular, and maintainable, providing a comprehensive solution for automating test cases for the MakeMyTrip website. Ensure all dependencies are correctly managed, and the framework is set up as per the instructions above for successful execution of the test cases.