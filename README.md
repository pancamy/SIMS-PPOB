# SIMS PPOB (PT. Nutech Integrasi)
Technical test untuk posisi Java Developer. Untuk melihat dokumentasi yang ada dalam project ini, silahkan download postman melalui 
link berikut -> https://download.com 

## Documentations

### Registration

Endpoint : /registrations

Method : POST

Request Body :
```
{
    "email": "john.doe@gmail.com",
    "first_name": "John",
    "last_name": "Doe",
    "password": "ini_password123"
}
```

Response Body (Success) :
```
{
    "status": 0,
    "message": "Registrasi berhasil silahkan login",
    "data": null
}
```

Response Body (Bad Request) :
```
{
    "status": 102,
    "message": "Parameter email tidak boleh kosong",
    "data": null
}
```

Response Body (Conflict) :
```
{
    "status": 120,
    "message": "Email sudah terdaftar",
    "data": null
}
```

### Login

Request Body :

Endpoint : /login

Method : POST

Request Body :
```
Kententuan request body:
- Email valid
- Password minimal 8 digit karakter

{
    "email": "john.doe@gmail.com",
    "password": "ini_password123"
}
```

Response Body (Success) :
```
{
    "status": 0,
    "message": "Login Sukses",
    "data": {
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzIbmju1NiJ9.eyJzdWIiOiJwYW5jYTkwOTBAZ21haWwuY29tIiwiZW1haWwiOiJwYW5jYTkwOTBAZ21haWwuY29tIiwiZXhwIjoxNjk1NTQ4ODc4fQ.n_sauPle_nOnZ_zQ0RD39-lyNB6RQyVxAWEmAuvQHSU"
    }
}
```

Response Body (Bad Request) :
```
{
    "status": 102,
    "message": "Parameter email tidak boleh kosong",
    "data": null
}
```

### Get Profile

Request Body :

Endpoint : /profile

Method : GET

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :
```
{
    "status": 0,
    "message": "Sukses",
    "data": {
        "email": "john.doe@gmail.com",
        "first_name": "John",
        "last_name": "Doe",
        "profile_image": "http://simsppob.application-panca.cloud/user-static-profile.jpeg"
    }
}
```

Response Body (Unauthorized) :
```
{
    "status": 108,
    "message": "Token tidak valid atau kadaluwarsa",
    "data": null
}
```

### Put Profile

Request Body :

Endpoint : /profile/update

Method : PUT

Request Header :

- Authorization : Bearer Token (Mandatory)

Request Body :
```
{
    "first_name": "John Edit",
    "last_name": "Doe Edit"
}
```

Response Body (Success) :
```
{
    "status": 0,
    "message": "Update Pofile berhasil",
    "data": {
        "email": "john.doe@gmail.com",
        "first_name": "John Edit",
        "last_name": "Doe Edit",
        "profile_image": "http://simsppob.application-panca.cloud/user-static-profile.jpeg"
    }
}
```

Response Body (Bad Request) :
```
{
    "status": 102,
    "message": "Parameter first name tidak boleh kosong",
    "data": null
}
```

Response Body (Unauthorized) :
```
{
    "status": 108,
    "message": "Token tidak valid atau kadaluwarsa",
    "data": null
}
```

### Put Profile Image

Request Body :

Endpoint : /profile/image

Method : PUT

Request Header :

- Authorization : Bearer Token (Mandatory)

Request From-Data

- Binary Image
- Type yang diizinkan hanya .jpeg & .png

Response Body (Success) :
```
{
    "status": 0,
    "message": "Update Pofile Image berhasil",
    "data": {
        "email": "panca9090@gmail.com",
        "first_name": "Panca Edit",
        "last_name": "Muhammad Edit",
        "profile_image": "http://simsppob.application-panca.cloud/dumy.png"
    }
}
```

Response Body (Unauthorized) :
```
{
    "status": 108,
    "message": "Token tidak valid atau kadaluwarsa",
    "data": null
}
```

### Get Banner (API Public)

Request Body :

Endpoint : /banner

Method : GET

Response Body (Success) :
```
{
    "status": 0,
    "message": "Sukses",
    "data": [
        {
            "banner_name": "Banner 1",
            "banner_image": "simsppob.application-panca.cloud/dummy.jpeg",
            "description": "Banner Pemuda Pemudi"
        }
    ]
}
```

### Get Services

Request Body :

Endpoint : /services

Method : GET

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :
```
{
    "status": 0,
    "message": "Sukses",
    "data": [
        {
            "banner_name": "Banner 1",
            "banner_image": "simsppob.application-panca.cloud/dummy.jpeg",
            "description": "Banner Pemuda Pemudi"
        }
    ]
}
```

Response Body (Unauthorized) :
```
{
    "status": 108,
    "message": "Token tidak valid atau kadaluwarsa",
    "data": null
}
```

### Get Balance

Request Body :

Endpoint : /banner

Method : GET

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :
```
{
    "status": 0,
    "message": "Get Balance Berhasil",
    "data": {
        "balance": 9510000
    }
}
```

Response Body (Unauthorized) :
```
{
    "status": 108,
    "message": "Token tidak valid atau kadaluwarsa",
    "data": null
}
```

### Topup Balance

Request Body :

Endpoint : /topup

Method : POST

Request Header :

- Authorization : Bearer Token (Mandatory)

Request Body :
```
{
    "top_up_amount": 10000000
}
```

Response Body (Success) :
```
{
    "status": 0,
    "message": "Top Up Balance berhasil",
    "data": {
        "balance": 19510000
    }
}
```

Response Body (Bad Request) :
```
{
    "status": 102,
    "message": "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0",
    "data": null
}
```

Response Body (Unauthorized) :
```
{
    "status": 108,
    "message": "Token tidak valid atau kadaluwarsa",
    "data": null
}
```

### Payment

Request Body :

Endpoint : /transaction

Method : POST

Request Header :

- Authorization : Bearer Token (Mandatory)

Request Body :
```
{
    "service_code": "PULSA"
}
```

Response Body (Success) :
```
{
    "status": 0,
    "message": "Sukses",
    "data": {
        "invoice_number": "INV24092023-9170",
        "service_code": "PULSA",
        "service_name": "PAYMENT PULSA",
        "transaction_type": "PAYMENT",
        "total_amount": 19510000,
        "created_on": "2023-09-24T04:58:46.801463"
    }
}
```

Response Body (Bad Request) :
```
{
    "status": 102,
    "message": "Parameter service code tidak boleh kosong",
    "data": null
}
```

Response Body (Unauthorized) :
```
{
    "status": 108,
    "message": "Token tidak valid atau kadaluwarsa",
    "data": null
}
```

## Installation

Anda hanya memerlukan docker untuk menjalankan database postgres dan migrasi otomatis table project. Silahkan sesuaika
dengan kebutuhan project anda

compose.yaml
```
services:
  postgres:
    container_name: 'postgres_sims_ppob'
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=db_sims_ppob'
      - 'POSTGRES_PASSWORD=abc123'
      - 'POSTGRES_USER=postgres'
    ports:
      - '5432:5432'
```

Jalankan project dengan port localhost:3000, dan database postgres dengan port localhost:5432