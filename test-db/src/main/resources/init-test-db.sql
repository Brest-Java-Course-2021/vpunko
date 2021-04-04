INSERT INTO APARTMENT (APARTMENT_ID, APARTMENT_NUMBER, APARTMENT_CLASS)
        VALUES (1, 101, 'LUXURIOUS' );
INSERT INTO APARTMENT (APARTMENT_ID, APARTMENT_NUMBER, APARTMENT_CLASS)
        VALUES (2, 102, 'CHEAP' );
INSERT INTO APARTMENT (APARTMENT_ID, APARTMENT_NUMBER, APARTMENT_CLASS)
        VALUES (3, 105, 'MEDIUM' );

Insert into RESIDENT (RESIDENT_ID, FIRSTNAME, LASTNAME, EMAIL, ARRIVAL_TIME, DEPARTURE_TIME, APARTMENT_NUMBER)
    values(1, 'Stephen', 'King', 'stephenking@test.com', '2021-03-13', '2021-03-23', 101);
Insert into RESIDENT (RESIDENT_ID, FIRSTNAME, LASTNAME, EMAIL, ARRIVAL_TIME, DEPARTURE_TIME, APARTMENT_NUMBER)
    values(2, 'Margaret', 'Mitchell', 'margaretmitchell@test.com', '2020-10-26', '2021-04-10', 102);
Insert into RESIDENT (RESIDENT_ID, FIRSTNAME, LASTNAME, EMAIL, ARRIVAL_TIME, DEPARTURE_TIME, APARTMENT_NUMBER)
    values(3, 'Den', 'Brown', 'denbrown@test.com', '2021-02-13', '2021-02-25', 101);
Insert into RESIDENT (RESIDENT_ID, FIRSTNAME, LASTNAME, EMAIL, ARRIVAL_TIME, DEPARTURE_TIME, APARTMENT_NUMBER)
    values(4, 'Erich', 'Remark', 'remark@test.com', '2021-04-10', '2021-06-01', 102);