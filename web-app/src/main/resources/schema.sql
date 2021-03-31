DROP TABLE IF EXISTS RESIDETNS;
DROP TABLE IF EXISTS APARTMENT;

CREATE TABLE APARTMENT
(
    APARTMENT_ID INT NOT NULL AUTO_INCREMENT,
    APARTMENT_NUMBER INT NOT NULL UNIQUE,
    APARTMENT_CLASS VARCHAR(30) NOT NULL,
    CONSTRAINT APARTMENT_PK PRIMARY KEY (APARTMENT_ID)
);

/**
  use ON DELETE SET NULL for FK.
  when delete apartment, apartment_id in Resident table become NULL
 */
CREATE TABLE RESIDENT
(
    RESIDENT_ID INT NOT NULL AUTO_INCREMENT,
    FIRSTNAME VARCHAR(30) NOT NULL,
    LASTNAME VARCHAR(30) NOT NULL,
    EMAIL VARCHAR(30) NOT NULL UNIQUE,
    ARRIVAL_TIME DATE NOT NULL,
    DEPARTURE_TIME DATE NOT NULL,
    APARTMENT_ID INT ,
    CONSTRAINT RESIDENT_PK PRIMARY KEY (RESIDENT_ID),
    CONSTRAINT RESIDENT_APARTMENT_FK FOREIGN KEY (APARTMENT_ID) REFERENCES APARTMENT(APARTMENT_ID)
        ON DELETE SET NULL
);