# Register & Authentication Service 

Spring Boot + JWT + Swagger + Docker + Mockito



## Content
- [Design](#Design)  
  
  - [Diagram](#ER-Diagram)
  - [Api Design](#Api-Design)
    - [Authentication](#API-Authentication)
    - [Registration](#API-Registration)
    - [Get User](#API-User)
  
- [Installation](#Installation)

- [Usage](#Usage) 

  

## Flow

#### Registeration  =>  Authentication  =>  Get User



## Design

### Structure Database (ER Diagram)
![](images/er-diagram.png)



## API Design
### API Description
|  API Name| API URL  | Permission | HTTP Method |
|---|---|---|---|
|  [Registration](#API-Registeration) | http://localhost:8008/api/register    | All | POST |
|  [Authentication](#API-Authentication)| http://localhost:8008/api/authenticate | All | POST |
| [Get User](#API-User) | http://localhost:8008/api/user | Authentication | GET |



### API Registration

**URL** : http://localhost:8008/api/register

**HTTP Method** : POST

**Request Model**

|  Field Name | Data Type  | Length | Not Null |
|---|---|---|---|
| username  | string    | 255| Y |
| password | string | 255 | Y |
| salary| double | | Y |
| phoneNumber | string | 10 | Y |
| address| string | 1000 | N |

**JSON Request**

```json
{
    "username":"test",
    "password":"P@ssw0rd",
    "phone":"0999999999",
    "salary": 0.00 ,
    "address":"test address"
}
```


**Success Response Model**

| Field Name | Data Type | Description              |
| ---------- | --------- | ------------------------ |
| code       | string    | 200                      |
| data       | object    | User data                |
| message    | string    | Registered Successfully. |
| status     | string    | success                  |

**Error Response Model**

| Field Name | Data Type | Description        |
| ---------- | --------- | ------------------ |
| code       | string    | Http status code.  |
| message    | string    | Error message.     |
| status     | string    | error              |
| info       | string    | Error information. |



**JSON Success Response**

```json
{
  "message": "Registered Successfully.",
  "code": "200",
  "data": {
    "createdBy": "jane",
    "updatedBy": "jane",
    "createdDate": "2021-01-15 03:36:42",
    "updatedDate": "2021-01-15 03:36:42",
    "username": "jane",
    "address": "test addr",
    "phoneNumber": "0981230824",
    "refCode": "202101150824",
    "salary": 50000,
    "memberType": {
      "createdBy": "system",
      "updatedBy": "system",
      "createdDate": "2021-01-13 17:00:00",
      "updatedDate": "2021-01-13 17:00:00",
      "name": "Platinum",
      "code": "01"
    }
  },
  "status": "success"
}
```

**JSON Error Response**

```json
{
  "message": "Information entered is incorrect.",
  "code": "202",
  "status": "error",
  "info": "Username has already been used."
}
```



### API Authentication

**URL** : http://localhost:8008/api/authenticate

**HTTP Method** : POST

**Request Model**

| Field Name | Data Type | Length | Not Null |
| ---------- | --------- | ------ | -------- |
| username   | string    | 255    | Y        |
| password   | string    | 255    | Y        |

**JSON Request**

```json
{
    "username":"test",
    "password":"P@ssw0rd"
}
```



**Success Response Model**

| Field Name       | Data Type | Description    |
| ---------------- | --------- | -------------- |
| code             | string    | 200            |
| data.accessToken | string    | Access token.  |
| data.userInfo    | object    | User data.     |
| message          | string    | Authenticated. |
| status           | string    | success        |

**Error Response Model**

| Field Name | Data Type | Description        |
| ---------- | --------- | ------------------ |
| code       | string    | Http status code.  |
| message    | string    | Error message.     |
| status     | string    | error              |
| info       | string    | Error information. |



**JSON Success Response**

```json
{
  "message": "Authenticated.",
  "code": "200",
  "data": {
    "accessToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lIiwiaWF0IjoxNjEwNjgzODgzLCJleHAiOjE2MTA3MDE4ODN9.V8dwdlY8NNJXCfN_GfhEmxXW-JEjCYx5-tL06uCzb9Q",
    "userInfo": {
      "createdBy": "jane",
      "updatedBy": "jane",
      "createdDate": "2021-01-15 03:36:42",
      "updatedDate": "2021-01-15 03:36:42",
      "username": "jane",
      "address": "test addr",
      "phoneNumber": "0981230824",
      "refCode": "202101150824",
      "salary": 50000,
      "memberType": {
        "createdBy": "system",
        "updatedBy": "system",
        "createdDate": "2021-01-13 17:00:00",
        "updatedDate": "2021-01-13 17:00:00",
        "name": "Platinum",
        "code": "01"
      }
    }
  },
  "status": "success"
```

**JSON Error Response**

```json
{
  "message": "User not found with username: janex",
  "code": "500",
  "status": "error",
  "info": null
}
```



### API User

**URL** : http://localhost:8008/api/user

**HTTP Method** : GET

**HTTP Header Authorization Ex. Curl**

```bash
curl -X GET "http://localhost:8008/api/user" -H "accept: */*" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lIiwiaWF0IjoxNjEwNjg0ODk4LCJleHAiOjE2MTA3MDI4OTh9._rYmTPC9A_XGsSRT1QPfnV61ZNb_ofnd7i6s3J4_Tc0"
```



**Success Response Model**

| Field Name | Data Type | Description |
| ---------- | --------- | ----------- |
| code       | string    | 200         |
| data       | object    | User data.  |
| message    | string    | Success.    |
| status     | string    | success     |

**Error Response Model**

| Field Name | Data Type | Description                |
| ---------- | --------- | -------------------------- |
| status     | string    | Http status code.          |
| message    | string    | Error message.             |
| error      | string    | Error description.         |
| timestamp  | long      | Current time milliseconds. |
| path       | string    | Current path.              |



**JSON Success Response**

```json
{
  "message": "Success.",
  "code": "200",
  "data": {
    "createdBy": "jane",
    "updatedBy": "jane",
    "createdDate": "2021-01-15 03:36:42",
    "updatedDate": "2021-01-15 03:36:42",
    "username": "jane",
    "address": "test addr",
    "phoneNumber": "0981230824",
    "refCode": "202101150824",
    "salary": 50000,
    "memberType": {
      "createdBy": "system",
      "updatedBy": "system",
      "createdDate": "2021-01-13 17:00:00",
      "updatedDate": "2021-01-13 17:00:00",
      "name": "Platinum",
      "code": "01"
    }
  },
  "status": "success"
}
```

**JSON Error Response**

```json
{
  "timestamp": 1610686401405,
  "status": 500,
  "error": "Internal Server Error",
  "message": "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.",
  "path": "/api/user"
}
```

```json
{
  "timestamp": 1610686530399,
  "status": 401,
  "error": "Unauthorized",
  "message": "Unauthorized",
  "path": "/api/user"
}
```



## Installation
### Required

- [Docker]: https://www.docker.com/products/docker-desktop

  version 19.03.3 or greater

- [Docker-compose]: https://dockerlabs.collabnix.com/intermediate/workshop/DockerCompose/How_to_Install_Docker_Compose.html

   version 1.25.4 or greater

- Port : 8008 for Web API

- Port : 5432 for Postgres SQL database



## Usage
#### Step 1 - Run web service

After clone project go to folder and use command `./build.sh`

If can run is show message below.

```bash
app    | 2021-01-15 10:30:13.227  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path '/api'
app    | 2021-01-15 10:30:13.231  INFO 1 --- [           main] com.spt.app.APIBackendTestApplication    : Started APIBackendTestApplication in 14.654 seconds (JVM running for 16.071)
app    | 2021-01-15 10:30:13.259  INFO 1 --- [           main] com.spt.app.spring.ApplicationStartup    : ApplicationStartup.....!
app    | 2021-01-15 10:30:13.259  INFO 1 --- [           main] com.spt.app.spring.ApplicationStartup    : Swagger UI : /swagger-ui.html

```

#### Step 2 - Test API via browser

Test the service through the URL : http://localhost:8008/api/swagger-ui.html

![](images\swagger-ui.png)

