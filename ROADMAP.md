# Road Map
## _This document discusses what I would have added to the Amortisation schedule API if I had time._

The following headings list the improvements I would have made to the Amortisation schedule API.
## Spring Boot Unit test
Given time I would have added the spring-boot-starter-test to the pom.xml, and used it to implement some unit tests in my application.
I would have created tests for my controllers as well as mocked the repositories for the schedule and loan details.

## loggers
Adding slf4j API would ensure that I can create useful logs for the API.  I would have added information and error messages to a
log file in a directory. The configuration for my logger will be in the application.properties file of Amortisation schedule API.
It will contain the log file location, logging levels and patterns. To acheive this I would have added the spring-boot-starter-logging
artifact to my pom.xml file.

## Rest APi Exception handling
Time permitting, I would have customised some of the HTTP status errors returned by the Amortisation schedule API using Spring's ResponseStatusException.  This will ensure that a more meaningful error response are sent out to the data receiver.

## Addition Retrieval of schedule data
Currently the Amortisation schedule API, retrieves either all the data on the H2 table for the schedule and loan_details table, or by the table's id.
It will improve the application, if the schedules can be listed by passing the identity of loan details, as this has a one to many relationship. If and identity of a loan details is passed into the request, the schedules that are linked to the loan are retrieved.


