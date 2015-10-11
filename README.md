MCDashboard
===========

This is a real-time analytics dashboard app that I developed in 24 hours for [MasterCard](https://www.mastercard.us/en-us.html)'s [MastersOfCode]
(http://mastersofcode.com/) Hackathon. 

It uses [Simplify Commerce](https://www.simplify.com/commerce/) and [Lost and Stolen Account List](https://developer
.mastercard.com/portal/display/api/Lost-Stolen+Account+List) APIs. The application creates payment attempts 
automatically when it starts running. You can see the results by connecting it after you ran the application. (i.e 
http://localhost:8080) 

It's developed using [Spring Boot](http://projects.spring.io/spring-boot/) and Web Socket. It uses [Gentelella Admin]
(https://github.com/kimlabs/gentelella) Bootstrap template for UI. [c3.js](http://c3js.org/) is used for chart library.

## Before You Run

Before you run the application:
 
* Create a Simplify Commerce API key from [here](https://www.simplify.com/commerce/)
* Update `application.yml` file with your public and private key

## Run

It's a Spring Boot application. You can use the steps [here](http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html) to run the application or simply run the command below
 in your project home directory:

    gradle bootRun

## Screenshots

![Dashboard](/src/main/resources/static/images/screenshots/dashboard.png?raw=true "Dashboard")

![Payments](/src/main/resources/static/images/screenshots/payments.png?raw=true "Payments")

![Refunds](/src/main/resources/static/images/screenshots/refunds.png?raw=true "Refunds")

