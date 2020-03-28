CREATE TABLE `items` (
  `ITEM_ID` int(11) NOT NULL,
  `ITEM_NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ITEM_ID`)
);

CREATE TABLE `login` (
  `LOGIN_ID` int(11) NOT NULL,
  `PASSWORD` varchar(255)  NOT NULL,
  PRIMARY KEY (`LOGIN_ID`)
);

insert into login values('1242892','Nandan');
insert into login values('143','Divya');

insert into items value(1,'Sugar');
insert into items value(2,'Salt');
insert into items value(3,'Apple');

CREATE TABLE ORDERS_RECORD (
    ORDER_ID int NOT NULL auto_increment,
    USER_ID int NOT NULL,
     primary key(ORDER_ID)
);

create table ORDERS_ITEMS (
SERIAL_NO INT NOT NULL auto_increment primary key,
ITEM_ID int NOT NULL,
 ORDER_ID int NOT NULL,
 key FK_DETAILS_INDX (ORDER_ID),
constraint FK_ORDER_ITEMS
FOREIGN KEY (ORDER_ID)
REFERENCES ORDERS_RECORD(ORDER_ID)
);
