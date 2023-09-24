# SIMS PPOB (PT. Nutech Integrasi)
Technical test untuk posisi Java Developer. Untuk melihat dokumentasi dan url server project ini, 
silahkan akses melalui link
- POSTMAN (Google Drive) : https://drive.google.com/drive/folders/13Xeu77OYE_nqJydK5JXizX_KVO0yI3OX?usp=sharing
- URL Server Project : http://simsppob.application-panca.cloud

##
## Local Installation

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

##
## Deploy to Server VPS

Aku mengasumsikan sudah memiliki server VPS menggunakan linux Ubuntu 18.09. Berikut IP server yang digunakan :
```
IP Address : 1xx.xxx.xxx.xxx
Username : xxxxxx
Password : xxxxxx
Port : xxxx
```

### Java Version

Silahkan instalasi Java, disini aku menggunakan versi 20.02 menggunakan Open JDK, anda bisa akses di website resmi open jdk.
```
Open JDK url : https://openjdk.org
Lokasi penyimpanan di server : /usr/java/openjdk/jdk-20.0.2/bin/java
```

### Installasi Nginx

Pastikan anda memperbarui dependencies linux
```
sudo apt update
```

Installasi nginx
```
sudo apt install nginx
```

Menjalankan nginx
```
sudo systemctl start nginx
sudo systemctl enable nginx
```

### Installasi Docker Linux

Set up Docker's Apt repository
```
# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

# Add the repository to Apt sources:
echo \
  "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
```

Install versi terbaru
```
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

Testing docker sudah berhasil diinstall
```
sudo docker run hello-world
```

### Build file jar

File jar nantinya kita akan upload ke server, anda bisa menggunakan FIlezila untuk upload ke server. Pastikan IP, username, passsword
dan port nya sesuai dengan VPS anda
```
mvn clean package
```

### Setup nginx

Arahkan ke directory /etc/nginx/conf.d, buatlah sebuah file dengan exstension .conf. Disini aku memberi nama simsppob.application-panca.cloud.conf
```
cd /etc/nginx/conf.d
```
```
nano simsppob.application-panca.cloud.conf
```

Isi file nya, arahkan ke port localhost:3000, dan server_name sesuaikan dengan domain anda
```
server {
    listen 80;
    server_name simsppob.application-panca.cloud;

    location / {
        proxy_pass http://127.0.0.1:3000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

### Menjalankan dengan systemd

Arahkan ke directory systemd, dan buatlah sebuah file dengan exstension .service. Jalankan file jar menggunakan systemd, 
yang perlu diketahui adalah lokasi penyimpanan file jar dan lokasi java disimpan
```
cd /etc/systemd/system/nama_service.service
```
```
[Unit]
Description=SIMS PPOB
After=network.target

[Service]
User=root
WorkingDirectory=/root/sims_ppob
ExecStart=/usr/java/openjdk/jdk-20.0.2/bin/java -jar /root/sims_ppob/com.sims.ppob-0.0.1-SNAPSHOT.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

##
## JSON Specification

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
            "banner_image": "http://simsppob.application-panca.cloud/dummy.jpeg",
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
            "banner_image": "http://simsppob.application-panca.cloud/dummy.jpeg",
            "description": "Banner Pemuda Pemudi"
        }
    ]
}
```

Response Body (Conflict) :
```
{
    "status": 120,
    "message": "Status code sudah terdaftar",
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
