- [Amortisation schedule](#amortisation-schedule)
    * [_A Restful API application_](#-a-restful-api-application-)
    * [Features](#features)
    * [Using the Amortisation schedule](#using-the-amortisation-schedule)
    * [Tests](#tests)
        - [Test Case 1 - Create Amortisation schedule without balloon payments, not passing in the balloon payment parameter](#test-case-1---create-amortisation-schedule-without-balloon-payments--not-passing-in-the-balloon-payment-parameter)
        - [Test Case 2 - Create Amortisation schedule without balloon payments, passing in the balloon payment parameter, but setting it to 0](#test-case-2----create-amortisation-schedule-without-balloon-payments--passing-in-the-balloon-payment-parameter--but-setting-it-to-0)
        - [Test Case 3 - Create Amortisation schedule without balloon payments, passing in the balloon payment parameter, but setting it to 10000](#test-case-3----create-amortisation-schedule-without-balloon-payments--passing-in-the-balloon-payment-parameter--but-setting-it-to-10000)
        - [Test Case 4 - Retrieve all the data from the loan_details table](#test-case-4----retrieve-all-the-data-from-the-loan-details-table)
        - [Test Case 5 - Retrieve all the data from the Schedules table](#test-case-5----retrieve-all-the-data-from-the-schedules-table)
        - [Test Case 6 - Retrieve data from the loan_details table with a given correct id.](#test-case-6----retrieve-data-from-the-loan-details-table-with-a-given-correct-id)
        - [Test Case 7 - Retrieve Data from the Schedules table with a given correct id.](#test-case-7----retrieve-data-from-the-schedules-table-with-a-given-correct-id)
        - [Test Case 8 - Retrieve data from the Schedules table with a given incorrect id.](#test-case-8----retrieve-data-from-the-schedules-table-with-a-given-incorrect-id)
        - [Test Case 9 - Retrieve data from the loan_details table with a given correct id.](#test-case-9----retrieve-data-from-the-loan-details-table-with-a-given-correct-id)
        - [Test Case 10 - Create Amortisation schedule without balloon payments, not passing in the CostOfAsset parameter](#test-case-10---create-amortisation-schedule-without-balloon-payments--not-passing-in-the-costofasset-parameter)
        - [Test Case 11 - Create Amortisation schedule without balloon payments, passing in the CostOfAsset parameter as a letter](#test-case-11---create-amortisation-schedule-without-balloon-payments--passing-in-the-costofasset-parameter-as-a-letter)
    * [Pre-requisites](#pre-requisites)
    * [Build and Installation](#build-and-installation)
    * [Future enhancements](#future-enhancements)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with
markdown-toc</a></i></small>

# Amortisation schedule

## _A Restful API application_

Amortisation schedule, is a Restful API application, that creates and retrieves schedules for assets that are to be
financed.

## Features

- Creates an Amortisation schedule for a set of loan details with and without balloon payment.
- Retrieves all the loan details created.
- Retrieves individual loan details by its unique id.
- Retrieves all the schedules created.
- Retrieves each schedule by its unique id, with its corresponding loan details.
- Saves the loan details on a table called **Loan_Details** on an H2 database.
- Saves the schedules on a table called **Schedules** on an H2 database.

## Using the Amortisation schedule

Postman or curl can be used to post and retrieve data from the API. To post to the application, loan details will need
to be passed to the API. The loan details passed to the API depends on whether the schedules are created with a balloon
payment or not. If the parameters passed, do not contain a value for balloon payment, or the balloon payment is zero,
then the payment will be calculated without the balloon payment. The list of the api url are as follows:

- http://localhost:8080/loan/
- http://localhost:8080/loan/schedules/
- http://localhost:8080/loan/schedule/21/  (where 21 is the unique id)
- http://localhost:8080/loan/load_details/
- http://localhost:8080/loan/load_detail/14/ (where 14 is the unique id)

The H2 database can be accessed from http://localhost:8080/h2-console/. The Amortisation schedule H2 database jdbc url
is as follows:

- jdbc:h2:mem:loans

**Note**
Here are some example of parameter used to create the schedules:
With balloon payments {"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"
balloonPayment":1000}

Without balloon payments {"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12} {"
costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"balloonPayment":0}

## Tests

#### Test Case 1 - Create Amortisation schedule without balloon payments, not passing in the balloon payment parameter

    Using curl
    curl --location --request POST 'http://localhost:8080/loan/' \
    --header 'Content-Type: application/json' \
    --data-raw '{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}'

    Using postman Post - url http://localhost:8080/loan/  

- The parameters - {"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}
- Expected result - loan details will be used to generate schedules with a final balance of 0, the schedules and the
  loan details will be saved into the schedules and loan_details table respectively.
- Actual result - At the end of the loan period, the balance of the loan was 0. The data was correctly saved on the
  schedules and loan_details tables.
- Pass/fail - Pass

#### Test Case 2 - Create Amortisation schedule without balloon payments, passing in the balloon payment parameter, but setting it to 0

    Using Curl
    curl --location --request POST 'http://localhost:8080/loan/' \
    --header 'Content-Type: application/json' \
    --data-raw '{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"ballonPayment":0}'

    Using postman Post - url http://localhost:8080/loan/ 

- The parameters -{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"
  balloonPayment":0}
- Expected result - loan details will be used to generate schedules with a final balance of 0, the schedules and the
  loan details will be saved into the schedules and loan_details table respectively.
- Actual result - At the end of the loan period, the balance of the loan was 0. the data was correctly saved on the
  schedules and loan_details tables.
- Pass/Fail - Pass

#### Test Case 3 - Create Amortisation schedule without balloon payments, passing in the balloon payment parameter, but setting it to 10000

    Using Curl
    curl --location --request POST 'http://localhost:8080/loan/' \
    --header 'Content-Type: application/json' \
    --data-raw '{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"ballonPayment":10000}'

     Using postman Post - url http://localhost:8080/loan/ 

- The parameters -{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"
  balloonPayment":1000}
- Expected result - loan details will be used to generate schedules with a final balance of approximately 10000, the
  schedules and the loan details will be saved into the schedules and loan_details table respectively.
- Actual result - At the end of the loan period, the balance of the loan was 10000. the data was correctly saved on the
  schedules and loan_details tables.
- Pass/fail - Pass

#### Test Case 4 - Retrieve all the data from the loan_details table

     Using Curl
     curl --location --request GET 'http://localhost:8080/loan/load_details/'

     Using postman Get - http://localhost:8080/loan/load_details/ 

- The parameters - none
- Expected result - Retrieves 2 records from the Loan Details table.
- Actual result - Retrieves 2 records from the Loan Details table.
- Pass/fail - Pass

#### Test Case 5 - Retrieve all the data from the Schedules table

    Using Curl
    curl --location --request GET 'http://localhost:8080/loan/schedules/'

     Using postman Get - http://localhost:8080/loan/Schedules/ 

- The parameters - none
- Expected result - Retrieves 24 records from the Schedules table.
- Actual result - Retrieves 24 records from the Schedules table.
- Pass/fail - Pass

#### Test Case 6 - Retrieve data from the loan_details table with a given correct id.

    Using Curl
    curl --location --request GET 'http://localhost:8080/loan/load_detail/1/'

     Using postman Get - http://localhost:8080/loan/load_detail/1/

- The parameters - none
- Expected result - Retrieves one record from the Loan Details table with the id of 1.
- Actual result - Retrieves one record from the Loan Details table with the id of 1.
- Pass/fail - Pass

#### Test Case 7 - Retrieve Data from the Schedules table with a given correct id.

    Using curl
    curl --location --request GET 'http://localhost:8080/loan/schedule/5/'

     Using postman Get - http://localhost:8080/loan/Schedule/5/ 

- The parameters - none
- Expected result - Retrieves a record from the Schedules table with id 13.
- Actual result - Retrieves a record from the Schedules table with the id 13.
- Pass/fail - Pass

#### Test Case 8 - Retrieve data from the Schedules table with a given incorrect id.

     Using postman Get - http://localhost:8080/loan/Schedule/100 

- The parameters - none
- Expected result - Should throw a not found error.
- Actual result - throws a status 404 not found error.
- Pass/fail - Pass

#### Test Case 9 - Retrieve data from the loan_details table with a given correct id.

     Using postman Get - http://localhost:8080/loan/load_detail/50 

- The parameters - none
- Expected result - Should throw a not found error.
- Actual result - throws a status 404 not found error.
- Pass/fail - Pass

#### Test Case 10 - Create Amortisation schedule without balloon payments, not passing in the CostOfAsset parameter

    Using curl
    curl --location --request POST 'http://localhost:8080/loan/' \
    --header 'Content-Type: application/json' \
    --data-raw '{ "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}'

    Using postman Post - url http://localhost:8080/loan/  

- The parameters - { "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}
- Expected result - loan details will fail to generate schedules and throw an error.
- Actual result - 500 internal server error
- Pass/fail - Pass

#### Test Case 11 - Create Amortisation schedule without balloon payments, passing in the CostOfAsset parameter as a letter

    Using curl
    curl --location --request POST 'http://localhost:8080/loan/' \
    --header 'Content-Type: application/json' \
    --data-raw '{"costOfAsset":A, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}'

    Using postman Post - url http://localhost:8080/loan/  

- The parameters - {"costOfAsset":A, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}
- Expected result - loan details will fail to generate schedules and throw an error.
- Actual result - 400 Bad Request
- Pass/fail - Pass

## Pre-requisites

Amortisation schedule uses a number of open source projects to work properly:

- Java 11
- Postman, cURL or your favorite REST Client
- Maven
- Git (to clone the repository. Optional, can download the zip from GitHub)

## Build and Installation

Clone the repository and execute the following commands:

Mac build

```sh
 ./mvnw clean package
 ```

Windows build

 ```sh
mvn clean install
 ```

The jar is built in the target directory and can be executed from there like so:

```sh
java -jar target/amortisationShd-0.0.1-SNAPSHOT.jar
```

Use your favourite REST client to trigger REST calls. e.g: curl or postman.

## Future enhancements

Details of the enhancements to the API has been discussed in the [ROADMAP](ROADMAP.md).

