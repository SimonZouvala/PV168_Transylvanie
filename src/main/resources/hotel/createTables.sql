CREATE TABLE "ROOM" (
    "ID" BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "PRICE" INTEGER NOT NULL,
    "CAPACITY" INTEGER NOT NULL,
    "NUMBEROFROOM" INTEGER NOT NULL,
);

CREATE TABLE "GUEST" (
    "ID" BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "ROOMID" BIGINT REFERENCES ROOM (ID),
    "NAME" VARCHAR(255) NOT NULL,
    "PHONE" VARCHAR(9) NOT NULL,
    "DATEOFCHECKIN" DATE,
    "DATEOFCHECKOUT" DATE,
);
