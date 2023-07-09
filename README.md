# OnePass API

This repository contains the source code for the OnePass API, which provides endpoints to manage categories, credentials, and users in the OnePass password management system.

## Endpoints

### User Controller

#### GET

##### Get user by email

- URL: `/users/email={email}`
- Method: `POST`
- Response:
  - Status: 404 Not Found (if the user with the given email does not exist)

```
{
    "user": {
        "id": 1,
        "secretKey": "new secret",
        "email": "user1@example.com"
    },
    "uuid": "tFAdUMiXnq6mYGaLj8SL/d2D5N1+hrPNAGLnIZoF5JisawEiuI97XMk175kSm39B"
}
```

#### CREATE

##### Create user

- URL: `/users?uuid=4d9e6260-79de-468a-bd6d-94648fde9458`
- Method: `POST`
- Requires decrypted uuid
- Input:

```
{
    "secretKey": "my secret key",
    "email": "user1@exam.com"
}
```

#### UPDATE

##### Update user

- URL: `/users/{id}?uuid=4d9e6260-79de-468a-bd6d-94648fde9458`
- Method: `PUT`
- Requires decrypted uuid
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

- URL: `/users/{id}?uuid=4d9e6260-79de-468a-bd6d-94648fde9458`
- Method: `DELETE`
- Requires decrypted uuid

### Category Controller

#### GET



##### Get categories by id

- URL: `/categories/user/1?uuid=f222c0fb-2fe4-402b-b4a6-51502a0869bd`
- Method: `POST`
- Requires decrypted uuid
- Response:

```
{
    "id": 1,
    "name": "Category 1",
    "user_id": 1
}
```

#### UPDATE

##### Update category

- URL: `/categories/{id}?uuid=f222c0fb-2fe4-402b-b4a6-51502a0869bd`
- Method: `PUT`
- Requires decrypted uuid
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

- URL: `/categories/{id}?uuid=f222c0fb-2fe4-402b-b4a6-51502a0869bd`
- Method: `DELETE`
- Requires decrypted uuid

### Credentials Controller

#### GET

#### Get password for a credential

- URL: `/credentials/{id}/password?uuid=f222c0fb-2fe4-402b-b4a6-51502a0869bd`
- Method: `POST`
- Response:
  - Body: Text/plain with the password of the requested credential

```
password1
```

##### Get credentials by user id

- URL: `/credentials/user/{id}?uuid=f222c0fb-2fe4-402b-b4a6-51502a0869bd`
- Method: `POST`
- Requires decrypted uuid
- Response:
  - Returns only credentials with the given user id

```
  {
    "id": 3,
    "name": "name3",
    "username": "user3",
    "email": "user3@example.com",
    "password": "password3",
    "url": "https://example.com",
    "notice": "Note 3",
    "category": {
        "id": 2,
        "name": "Category 2",
        "user_id": 1
    },
    "user_id": 1
  }
```

#### CREATE

##### Create credentials

- URL: `/credentials?uuid=f222c0fb-2fe4-402b-b4a6-51502a0869bd`
- Method: `POST`
- Requires decrypted uuid
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

- URL: `/credentials/{id}?uuid=f222c0fb-2fe4-402b-b4a6-51502a0869bd`
- Method: `PUT`
- Requires decrypted uuid
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

- URL: `/credentials/{id}?uuid=f222c0fb-2fe4-402b-b4a6-51502a0869bd`
- Requires decrypted uuid
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
