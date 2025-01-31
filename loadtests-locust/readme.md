# Locust Load Test for Petclinic REST API

This repository contains a Locust test script for performance testing the Spring Petclinic REST API.

## Prerequisites

- Python 3.x installed
- Locust installed (`pip install locust`)

## Running the Test

1. Start the Spring Petclinic backend:
    `mvn spring-boot:run`
(Ensure the API is running at `http://localhost:8080` or update `host` accordingly.)

2. Run the Locust test:
    `locust -f locust_petclinic_test.py --host=http://localhost:9966`


3. Open Locust web UI at [http://localhost:8089](http://localhost:8089) and start the test.

## Test Scenarios

The test script includes:
- Fetching owners, pets, vets, visits, and health status
- Creating new owners and pets
- Deleting an owner

## Customization

Modify `locust_petclinic_test.py` to add more API calls or adjust user behavior.

## Logging

Logs will be generated automatically in the terminal and can be redirected to a file:
`locust -f locust_petclinic_test.py --host=http://localhost:9966 > test_logs.txt`


