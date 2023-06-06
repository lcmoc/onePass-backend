# OnePass API

This repository contains the source code for the OnePass API, which provides endpoints to manage categories, credentials, and users in the OnePass password management system.

## Endpoints

### User Controller

#### Get all users

- URL: `/users`
- Method: `POST`
- Response: 

````
[
  {
    "id": 1,
    "secretKey": "secret1",
    "email": "user1@example.com"
  },
  {
    "id": 2,
    "secretKey": "secret2",
    "email": "user2@example.com"
  },
  {
    "id": 3,
    "secretKey": "secret3",
    "email": "user3@example.com"
  }
]
````

#### Get user by email

- URL: `/users/email={email}`
- Method: `POST`
- Response:
    - Status: 404 Not Found (if the user with the given email does not exist)
````
{
  "id": 1,
  "secretKey": "secret1",
  "email": "user1@example.com"
}
````

### Category Controller

#### Get all categories

- URL: `/categories`
- Method: `POST`
- Response:
````
[
  {
    "id": 1,
    "name": "Category 1"
  },
  {
    "id": 2,
    "name": "Category 2"
  },
  {
    "id": 3,
    "name": "Category 3"
  },
  {
    "id": 4,
    "name": "Category 4"
  }
]
````

### Credentials Controller

#### Get all credentials

- URL: `/credentials`
- Method: `POST`
- Response:
    - Body: JSON array of CredentialsEntity objects with password field set to `null`
```
[
  {
    "id": 1,
    "username": "user1",
    "email": "user1@example.com",
    "password": null,
    "url": "example.com",
    "notice": "Notice 1",
    "category": {
      "id": 1,
      "name": "Category 1"
    }
  },
  {
    "id": 2,
    "username": "user2",
    "email": "user2@example.com",
    "password": null,
    "url": "example.com",
    "notice": "Notice 2",
    "category": {
      "id": 1,
      "name": "Category 1"
    }
  },
  {
    "id": 3,
    "username": "user3",
    "email": "user3@example.com",
    "password": null,
    "url": "example.com",
    "notice": "Notice 3",
    "category": {
      "id": 2,
      "name": "Category 2"
    }
  },

]
```

#### Get credential by ID

- URL: `/credentials/{id}`
- Method: `POST`
- Response:
    - Body: JSON object of the requested CredentialsEntity with password field set to `null`
    - Status: 404 Not Found (if the credential with the given ID does not exist)

```
{
  "id": 1,
  "username": "user1",
  "email": "user1@example.com",
  "password": null,
  "url": "example.com",
  "notice": "Notice 1",
  "category": {
    "id": 1,
    "name": "Category 1"
  }
}
```

#### Get password for a credential

- URL: `/credentials/{id}/password`
- Method: `POST`
- Response:
    - Body: Text/plain with the password of the requested credential

```
password1
```


## Technologies Used

- Java
- Spring Framework
- Spring Boot

## Setup

To run the OnePass API locally, follow these steps:

1. Clone the repository: `git clone <repository_url>`
2. Navigate to the project directory: `cd <project_directory>`
3. Start the docker server, which you can find in `src/docker/compose.yml`
4. Insert the database to the server script: `src/docker/onePassSqlScript.sql`
5. If not already happened insert the testdata into the database: `scr/docker/testdata.sql`

The API will be available at `http://localhost:8080`.

## Conclusion

The OnePass API provides endpoints to manage categories, credentials, and users in the OnePass password management system. Feel free to explore and use the provided endpoints to interact with the API.
