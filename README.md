# OnePass API

This repository contains the source code for the OnePass API, which provides endpoints to manage categories, credentials, and users in the OnePass password management system.

## Endpoints

### User Controller

#### GET

##### Get all users

- URL: `/users`
- Method: `POST`
- Response:

```
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
```

##### Get user by email

- URL: `/users/email={email}`
- Method: `POST`
- Response:
  - Status: 404 Not Found (if the user with the given email does not exist)

```
{
  "id": 1,
  "secretKey": "secret1",
  "email": "user1@example.com"
}
```

##### Get all emails

- URL: `/users/emails`
- Method: `POST`
- Response:

```
[
    "user1@example.com",
    "user2@example.com",
    "user3@example.com",
    "user1@exam.com"
]
```

#### CREATE

##### Create user

- URL: `/users`
- Method: `POST`
- Input:

```
{
    "secretKey": "my secret key",
    "email": "user1@exam.com"
}
```

#### UPDATE

##### Update user

- URL: `/users/{id}`
- Method: `PUT`
- Input:

```
{
    "id" 1,
    "secretKey": "my secret key",
    "email": "user1@exam.com"
}
```

#### DELETE

##### Delete user

- URL: `/users/{id}`
- Method: `DELETE`

### Category Controller

#### GET

##### Get all categories

- URL: `/categories`
- Method: `POST`
- Response:

```
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
```

##### Get categories by name

- URL: `/categories/name={name}`
- Method: `POST`
- Response:

```
{
    "id": 3,
    "name": "Category 3",
    "user_id": 2
}
```

#### UPDATE

##### Update category

- URL: `/categories/{id}`
- Method: `PUT`
- Input:

```
{
    "id": 1,
    "name": "first category",
    "user_id": 1
}
```

#### DELETE

##### Delete category

- URL: `/categories/{id}`
- Method: `DELETE`

### Credentials Controller

#### GET

##### Get all credentials

- URL: `/credentials`
- Method: `POST`
- Response:
  - Body: JSON array of CredentialsEntity objects with password field set to `null`

```
[
    {
        "id": 1,
        "username": "user5",
        "email": "user1@example.com",
        "password": null,
        "url": "https://example.com",
        "notice": "Note 1",
        "category": {
            "id": 1,
            "name": "Category 1",
            "user_id": 1
        },
        "user": {
            "id": 1,
            "secretKey": "secret1",
            "email": "user1@example.com"
        }
    },
    {
        "id": 2,
        "username": "user2",
        "email": "user2@example.com",
        "password": null,
        "url": "https://example.com",
        "notice": "Note 2",
        "category": {
            "id": 1,
            "name": "Category 1",
            "user_id": 1
        },
        "user": {
            "id": 1,
            "secretKey": "secret1",
            "email": "user1@example.com"
        }
    },
    {
        "id": 3,
        "username": "user3",
        "email": "user3@example.com",
        "password": null,
        "url": "https://example.com",
        "notice": "Note 3",
        "category": {
            "id": 2,
            "name": "Category 2",
            "user_id": 1
        },
        "user": {
            "id": 1,
            "secretKey": "secret1",
            "email": "user1@example.com"
        }
    },
    {
        "id": 4,
        "username": "user4",
        "email": "user4@example.com",
        "password": null,
        "url": "https://example.com",
        "notice": "Note 4",
        "category": {
            "id": 2,
            "name": "Category 2",
            "user_id": 1
        },
        "user": {
            "id": 1,
            "secretKey": "secret1",
            "email": "user1@example.com"
        }
    }
]
```

##### Get credential by ID

- URL: `/credentials/{id}`
- Method: `POST`
- Response:
  - Body: JSON object of the requested CredentialsEntity with password field set to `null`
  - Status: 404 Not Found (if the credential with the given ID does not exist)

```
{
    "id": 1,
    "username": "user5",
    "email": "user1@example.com",
    "password": null,
    "url": "https://example.com",
    "notice": "Note 1",
    "category": {
        "id": 1,
        "name": "Category 1",
        "user_id": 1
    },
    "user": {
        "id": 1,
        "secretKey": "secret1",
        "email": "user1@example.com"
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

##### Get credentials by user id

- URL: `/credentials/user/{id}`
- Method: `POST`
- Response:
  - Returns only credentials with the given user id

```
[
    {
        "id": 1,
        "username": "user5",
        "email": "user1@example.com",
        "password": null,
        "url": "https://example.com",
        "notice": "Note 1",
        "category": {
            "id": 1,
            "name": "Category 1",
            "user_id": 1
        },
        "user": {
            "id": 1,
            "secretKey": "secret1",
            "email": "user1@example.com"
        }
    },
    {
        "id": 2,
        "username": "user2",
        "email": "user2@example.com",
        "password": null,
        "url": "https://example.com",
        "notice": "Note 2",
        "category": {
            "id": 1,
            "name": "Category 1",
            "user_id": 1
        },
        "user": {
            "id": 1,
            "secretKey": "secret1",
            "email": "user1@example.com"
        }
    },
    {
        "id": 3,
        "username": "user3",
        "email": "user3@example.com",
        "password": null,
        "url": "https://example.com",
        "notice": "Note 3",
        "category": {
            "id": 2,
            "name": "Category 2",
            "user_id": 1
        },
        "user": {
            "id": 1,
            "secretKey": "secret1",
            "email": "user1@example.com"
        }
    },
    {
        "id": 4,
        "username": "user4",
        "email": "user4@example.com",
        "password": null,
        "url": "https://example.com",
        "notice": "Note 4",
        "category": {
            "id": 2,
            "name": "Category 2",
            "user_id": 1
        },
        "user": {
            "id": 1,
            "secretKey": "secret1",
            "email": "user1@example.com"
        }
    }
]
```

#### CREATE

##### Create credentials

- URL: `/credentials`
- Method: `POST`
- Input:
  - The user_id from the category must be equal to the id of the user, because one category can only be assigned to one user

```
{
    "username": "test",
    "email": "user4@example.com",
    "password": null,
    "url": "https://example.com",
    "notice": "Note 4",
    "category": {
        "id": 1
    },
    "user": {
        "id": 1
    }
}
```

#### UPDATE

##### Update credentials

- URL: `/credentials/{id}`
- Method: `PUT`
- Input:
  - The user_id from the category must be equal to the id of the user, because one category can only be assigned to one user
  - If there are no new changes, the server will not do a update

```
{
  "id": 1,
  "username": "new user name",
  "email": "new email",
  "password": "new password",
  "url": "new url",
  "notice": "new Note",
  "category": {
    "id": 1,
    "name": "Category 1",
    "user_id": 1
  },
  "user": {
    "id": 3,
    "secretKey": "secret1",
    "email": "user1@example.com"
  }
}
```

#### DELETE

##### Delete credential

- URL: `/credentials/{id}`
- Method: `DELETE`


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
6. Import the postman collections into postman you can find them at: `scr/postman/`

The API will be available at `http://localhost:8080`.
Use the postman http request to test the API.

## Conclusion

The OnePass API provides endpoints to manage categories, credentials, and users in the OnePass password management system. Feel free to explore and use the provided endpoints to interact with the API.
