## About
This project is an automated test suite for the [Interactive Brokers EU website](https://www.interactivebrokers.ie). The test cases cover critical areas such as buying and selling shares, as well as less important QOL features such as bookmarking news articles of interest.

A GitHub Actions workflow is also set up to update the associated TestRail project, and to generate HTML reports detailing the test results. The latest report is available on GitHub pages: https://vanessahoamea.github.io/interactive-brokers-test/reports.

## Setup
### Test execution
Login credentials are stored in an `.env` file, along with the base URL of our AUT, as well as the local environment flag (when set to false, the browser runs in headless mode):

```
BASE_URL=https://www.interactivebrokers.ie
USERNAME=<your_ibkr_username>
PASSWORD=<your_ibkr_password>
LOCAL=true
```

After setting up the environment, start the test run using the command:

```
mvn clean test
```

### TestRail integration
The easiest way to configure TestRail is through a `trcli-config.yaml` file:

```
host: <your_testrail_host>
project: <your_testrail_project>
username: <your_testrail_username>
password: <your_testrail_password>
title: TestNG Automated Test Run
```

Once the test run is finished, you can forward the results to your TestRail project using the TestRail CLI:

```
# install the CLI
pip install trcli

# upload results to TestRail instance
trcli -y -c "trcli-config.yaml" parse_junit -f "./target/surefire-reports/TEST-TestSuite.xml"
```

Missing test cases will be generated automatically, and a new test run named **TestNG Automated Test Run** will be created for the project.

### Report generation
This project uses the Allure framework to generate HTML reports for visualizing the test results:

```
# install the framework
npm i -g allure

# optionally, provide additional information about your working environment
cp ./src/main/resources/environments/local.properties ./target/allure-results/environment.properties

# create HTML report
allure generate -o ./resources/allure-report
```

This will output the finalized report in the `./resources/allure-report` folder.

## Tech stack
- Java
- Selenium
- TestNG
- Allure Report