# Amortisation schedule
## _A Restful API application_


Amortisation schedule, is a Restful API application, that creates and retrives schedules for assets that are to be financed.


## Features

- Creates an Amortisation schedule for a set of loan details with and without ballon payment.
- Retrives all the loan details created.
- Retrives individual loan details by its unique id.
- Retrives all the schedules created.
- Retrives each schedule by its unique id, with its corresponding loan details.
- Saves the loan details on a table called **Loan_Details** on a H2 database.
- Saves the scheules on a table called **Schedules** on a H2 database.

## Using the Amortisation schedule

Postman or curl can be used to post and retrive data from the API. To post to the application, loan details will need to be passed to the API.
The loan details passed to the API depends on whether the schedules are created with a ballon payment or not. If the parameters passed, 
do not contain a value for ballon payment or the ballon payment is zero, then the payment will be calculated without the ballon payment. 
The list of the api url are as follows:

- http://localhost:8080/loan/
- http://localhost:8080/loan/schedules/
- http://localhost:8080/loan/schedule/21/  (where 21 is the unique id)
- http://localhost:8080/loan/load_details/
- http://localhost:8080/loan/load_detail/14/ (where 14 is the unique id)

The H2 database can be accessed from http://localhost:8080/h2-console/. The Amortisation schedule H2 database jdbc url is as follows:
- jdbc:h2:mem:loans

**Note**
Here are some example of parameter used to create the schedules:
With ballon payments
{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"ballonPayment":1000}

Without ballon payments
{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}
{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"ballonPayment":0}

## Tests

#### Test Case 1 - Create Amortisation schedule without ballon payments, not passing in the ballon payment parameter
    Using postman Post - url http://localhost:8080/loan/  
   - The parameters - {"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}
   - Expected result - loan details will be used to generate schedules with a final balance of 0, the schedules and the loan 
     details will be saved into the schedules and loan_details table respectively.
   - Actual result - At the end of the loan period, the balance of the loan was 0. the data was correctly saved on the schedules and loan_details tables.
   - Pass/fail - Pass

#### Test Case 2 -  Create Amortisation schedule without ballon paymets, passing in the ballon payment parameter, but seting it to 0
    Using postman Post - url http://localhost:8080/loan/ 
   - The parameters -{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"ballonPayment":0}
   - Expected result - loan details will be used to generate schedules with a final balance of 0, the schedules and the loan 
     details will be saved into the schedules and loan_details table respectively.
   - Actual result - At the end of the loan period, the balance of the loan was 0. the data was correctly saved on the schedules and loan_details tables.
   - Pass/Fail - Pass

#### Test Case 3 -  Create Amortisation schedule without ballon paymets, passing in the ballon payment parameter, but seting it to 10000
     Using postman Post - url http://localhost:8080/loan/ 
   - The parameters -{"costOfAsset":25000, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12,"ballonPayment":1000}
   - Expected result - loan details will be used to generate schedules with a final balance of approximately 10000, the schedules and the loan 
     details will be saved into the schedules and loan_details table respectively.
   - Actual result - At the end of the loan period, the balance of the loan was 10000. the data was correctly saved on the schedules and loan_details tables.
   - Pass/fail - Pass
   
#### Test Case 4 -  Retrieve all the data from the loan_details table
     Using postman Get - http://localhost:8080/loan/load_details/ 
   - The parameters - none
   - Expected result - Retrieves 2 records from the Loan Details table.
   - Actual result - Retrieves 2 records from the Loan Details table.
   - Pass/fail - Pass
   
#### Test Case 5 -  Retrieve all the data from the Schedules table
     Using postman Get - http://localhost:8080/loan/Schedules/ 
   - The parameters - none
   - Expected result - Retrieves 24 records from the Schedules table.
   - Actual result - Retrieves 24 records from the Schedules table.
   - Pass/fail - Pass
   
 #### Test Case 6 -  Retrieve data from the loan_details table with a given correct id.
     Using postman Get - http://localhost:8080/loan/load_detail/1 
   - The parameters - none
   - Expected result - Retrieves one record from the Loan Details table with the id of 1.
   - Actual result - Retrieves one record from the Loan Details table with the id of 1.
   - Pass/fail - Pass
   
#### Test Case 7 -  Retrieve Data from the Schedules table with a given correct id.
     Using postman Get - http://localhost:8080/loan/Schedule/13 
   - The parameters - none
   - Expected result - Retrieves a record from the Schedules table with id 13.
   - Actual result - Retrieves a record from the Schedules table with the id 13.
   - Pass/fail - Pass

#### Test Case 8 -  Retrieve data from the Schedules table with a given incorrect id.
     Using postman Get - http://localhost:8080/loan/Schedule/100 
   - The parameters - none
   - Expected result - Should throw a not found error.
   - Actual result - throws a status 404 not found error.
   - Pass/fail - Pass
   
#### Test Case 9 -  Retrieve data from the loan_details table with a given correct id.
     Using postman Get - http://localhost:8080/loan/load_detail/50 
   - The parameters - none
   - Expected result - Should throw a not found error.
   - Actual result - throws a status 404 not found error.
   - Pass/fail - Pass
   
  #### Test Case 10 - Create Amortisation schedule without ballon paymets, not passing in the CostOfAsset parameter
    Using postman Post - url http://localhost:8080/loan/  
   - The parameters - { "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}
   - Expected result - loan deatils will fail to generate schedues and throw an error. 
   - Actual result - 500 internal server error
   - Pass/fail - Pass 
   
  #### Test Case 11 - Create Amortisation schedule without ballon paymets, passing in the CostOfAsset parameter as a letter
    Using postman Post - url http://localhost:8080/loan/  
   - The parameters - {"costOfAsset":A, "depositPaid":5000,"yearlyInterest":7.5,"numberOfMonthlyPayments":12}
   - Expected result - loan deatils will fail to generate schedues and throw an error. 
   - Actual result - 400 Bad Request
   - Pass/fail - Pass 
   

## Tech

Amortisation schedule uses a number of open source projects to work properly:

- Java 11
- Spring 
- Postman
- Curl Command
- H2 database

## Installation
Amortisation schedule is a Spring boot application, copy the jar file to  a directory and a compuer location e.g mydirectory. 

To start up use the command below:
```sh
java -jar /mydirectory/amortisationShd-0.0.1-SNAPSHOT.jar
```

## Future enhancements
Details of the enhancements to the API has been discussed in the RoadMap document.

