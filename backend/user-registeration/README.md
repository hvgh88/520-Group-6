# Microservice for user registration, login, and profile management in UMass Hangout


User Registration Service API
The User Registration Service manages user account operations, including signing up for new accounts, logging in to existing accounts, and managing user profiles. It provides the following REST endpoints:

/api/v1/auth/register: Enables new user registration. Throws an error if the email ID is already registered.

/api/v1/auth/login: Authenticates users with valid credentials. Suggests signing up if no account exists for the provided email ID.

/api/v1/auth/profile/{emailId}: Retrieves profile information for the specified email ID. Throws an error if no account is found.

/api/v1/auth/profile: Creates or updates a user profile. Throws an error if the associated account does not exist.

Note: All email and password validations are handled on the frontend using JavaScript. The API does not explicitly perform these validations.

The following are the sample request and response for each API end point. 

/api/v1/auth/register - POST 
Sample input :
```json
{
    "email": "seppalapally39@umass.edu",
    "password": "Swetha@124"
}
```
Case 1: Email ID already exists - 400 - Bad request

Sample response:
```json{
    "code": 10002,
    "message": "There's already an account associated with the email ID. Please login!"
}
```
Case 2 : Email ID doesn't exist and user registration successful - 201 - Created

```json{
    "code": 10001,
    "data": {
        "id": 205,
        "email": "seppalapally90@umass.edu"
    },
    "message": "Email ID registered successfully!"
}
```
Case 3: Issue with the input - 400 - Bad Request

```json{
    "timestamp": "2024-12-09T13:06:43.387+00:00",
    "status": 400,
    "error": "Bad Request","message": "{error details}"
}
```
Case 4 : Any internal issues - 500 - Internal Server Error

```json{
    "code": 10013,
    "message": "Something went wrong. Please try again later!"
}
```
/api/v1/auth/login - POST

Sample Request

```json{
    "email":"seppalapally90@umass.edu",
    "password":"Swetha@124"
}
```

Case 1 : Successful login - 200 - OK

```json{
    "code": 10004,
    "data": {
        "id": 205,
        "email": "seppalapally90@umass.edu"
    },
    "message": "User login successful"
}
```

Case 2 : Invalid password - 401 - Unauthorized

```json{
    "code": 10005,
    "message": "Invalid Password. Please try again!"
}
```

Case 3 : Email id doesn't exist - 404 - Not Found

```json{
    "code": 10003,
    "message": "There's no account associated with the email ID. Please register!"
}
```
Case 4 : Issue with input - 400 - Bad request

```json{
    "timestamp": "2024-12-09T13:06:43.387+00:00",
    "status": 400,
    "error": "Bad Request","message": "{error details}"
}
```

Case 5 : Any internal issues - 500 - Internal Server Error

```json{
    "code": 10013,
    "message": "Something went wrong. Please try again later!"
}
```


/api/v1/auth/profile/{emailId} - GET

Case 1 : User Profile doesn't exist - 404 - Not Found

```json{
    "code": 10008,
    "message": "User Profile not found."
}
```
Case 2 : User Profile fetch successful - 200 - OK

```json{
    "code": 10006,
    "data": {
        "userId": 201,
        "email": "seppalapally3@umass.edu",
        "firstName": "Swetha",
        "middleName": "",
        "lastName": "E",
        "department": "Computer Science",
        "bio": "UMass Student",
        "graduationYear": 2024
    },
    "message": "User Profile fetched successfully!"
}
```

Case 3 :  Any internal issues - 500 - Internal Server Error

```json{
    "code": 10007,
    "message": "User Profile fetch failed. Please try again later!"
}
```

/api/v1/auth/profile - POST

Sample Request:

```json{
        "userId": 201,
        "email": "seppalapally3@umass.edu",
        "firstName": "Swetha",
        "middleName": "",
        "lastName": "E",
        "department": "Computer Science",
        "bio": "UMass Student",
        "graduationYear": 2024
    }
```
Case 1 : First Time creation successful - 201 - Created

```json{
    "code": 10009,
    "data": {
        "userId": 201,
        "email": "seppalapally3@umass.edu",
        "firstName": "Swetha",
        "middleName": "",
        "lastName": "E",
        "department": "Computer Science",
        "bio": "UMass Student",
        "graduationYear": 2024
    },
    "message": "User Profile created successfully!"
}
```

Case 2 : Further updates successful  - 200 - OK

```json{
    "code": 10010,
    "data": {
        "userId": 201,
        "email": "seppalapally3@umass.edu",
        "firstName": "Swetha",
        "middleName": "",
        "lastName": "E",
        "department": "Computer Science",
        "bio": "UMass Student",
        "graduationYear": 2024
    },
    "message": "User Profile updated successfully!"
}
```

Case 3 : Issues with profile creation/update - 500 - Internal Error

```json{
    "code": 10011,
    "message": "User Profile update failed. Please try again later!"
}
```

Tech Stack Used:

All required depedencies are part of pom.xml

Programming Language - Java  - 1.8

Framework - Spring boot - 2.5.5

Database - MySQL database

Testing - JUnit/Mockito

The service is set to run on port 8080. please change server.port in application.properties if needed
