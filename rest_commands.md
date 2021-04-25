## Available REST endpoints

### Apartments

#### Find all apartments

```
curl --request GET 'http://localhost:8090/apartments' | json_pp
```

#### Find all dto apartments

```
curl --request GET 'http://localhost:8090/apartments/dto' | json_pp
```

#### Find apartment by id

```
curl --request GET 'http://localhost:8090/apartments/1' | json_pp
```

#### Create new apartment

```
curl --request POST 'http://localhost:8090/apartments/' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "apartmentNumber":"109",
    "apartmentClass":"MEDIUM"
}'
```

#### Edit apartment

```
curl --request PUT 'http://localhost:8090/apartments/' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "apartmentId":3,
    "apartmentNumber":150,
    "apartmentClass":"MEDIUM"
}'
```

#### Delete apartment by id

```
curl --request DELETE 'http://localhost:8090/apartments/1'
```

### Residents

#### Find all residents

```
curl --request GET 'http://localhost:8090/residents' | json_pp
```

#### Find resident by id

```
curl --request GET 'http://localhost:8090/residents/1' | json_pp
```

#### Create new resident

```
curl --request POST 'http://localhost:8090/residents/' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
        "firstName": "Vitaliy",
        "lastName": "Punko",
        "email": "test@test.com",
        "arrivalTime": [2021, 3, 13],
        "departureTime": [2021, 3, 23],
        "apartmentNumber": 101
}'
```

#### Edit resident

```
curl --request PUT 'http://localhost:8090/residents/' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
         "residentId": 2,
        "firstName": "Margaret",
        "lastName": "Mitchell",
        "email": "margaret@test.com",
        "arrivalTime": [2020, 10, 26],
        "departureTime": [2021, 4, 10],
        "apartmentNumber": 105
}'
```

#### Delete resident by id

```
curl --request DELETE 'http://localhost:8090/residents/1'
```

#### Find residents from arrival time to departure time

```
curl --request GET 'http://localhost:8090/search?arrivalTime=2021-03-01&departureTime=2021-05-02'
```