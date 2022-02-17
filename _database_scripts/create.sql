use JDIOnderzoek;

DROP table if exists EventSourceProductQuantity;
DROP table if exists EventListOrderStatus;
DROP TABLE IF EXISTS OrderStatusSnapshots;
DROP TABLE IF EXISTS ProductQuantitySnapshots;
DROP table if exists productInOrder;
DROP table if exists orders;
DROP table if exists product;
DROP table if exists customer;

create table customer (
customerId int NOT NULL AUTO_INCREMENT,
customerName varchar(255) NOT NULL,
customerAddress varchar(255),
customerType char(4),

constraint PK_Customer PRIMARY KEY(customerId)
);

create table product (
productId int NOT NULL AUTO_INCREMENT,
productName varchar(255) NOT NULL,
productPrice numeric(5,2) NOT NULL,
productDescription varchar(255),
productQuantity int NOT NULL,

constraint PK_Product PRIMARY KEY (productId)
);

create table orders (
orderId int NOT NULL AUTO_INCREMENT,
customerId int NOT NULL,

constraint PK_Order PRIMARY KEY (orderId),
constraint FK_Orders_CustomerId FOREIGN KEY (customerId) REFERENCES customer (customerId)
);

create table productInOrder (
orderId int NOT NULL,
productId int NOT NULL,
productQuantity int NOT NULL,
productPrice numeric(5,2) NOT NULL,

constraint PK_ProductInOrder PRIMARY KEY (orderId, productId),
constraint FK_ProductInOrder_OrderId FOREIGN KEY(orderId) references orders(orderId),
constraint FK_ProductInOrder_productId FOREIGN KEY(productId) references product(productId)
);

create table EventSourceProductQuantity (
productID int NOT NULL,
versionNr int NOT NULL,
dateCreated datetime NOT NULL,
eventType varchar(255) NOT NULL,
quantity int NOT NULL,

    -- constraint PK_EventProducts PRIMARY KEY(productID, versionNr)
    constraint FK_ProductEvent_productId FOREIGN KEY(productId) references product(productId)
);

create table EventListOrderStatus (
orderID int NOT NULL,
versionNr int NOT NULL,
dateCreated datetime NOT NULL,
orderStatus varchar(30) NOT NULL,

    -- constraint PK_EventOrders PRIMARY KEY(orderID, versionNr)
);

create table ProductQuantitySnapshots(
productID int NOT NULL,
snapshotNr int NOT NULL,
fromEventNr int NOT NULL,
dateCreated datetime NOT NULL,
lastEventType varchar(255) NOT NULL,
quantity int NOT NULL,

    constraint FK_ProductSnapshots_productId FOREIGN KEY(productId) references product(productId)
);

create table OrderStatusSnapshots(
orderID int NOT NULL,
snapshotNr int NOT NULL,
fromEventNr int NOT NULL,
dateCreated dateTime NOT NULL,
orderStatus varchar(30) NOT NULL,

    constraint FK_OrderSnapshot_OrderId FOREIGN KEY(orderId) references orders(orderId)
    -- constraint PK_OrderSnapshots PRIMARY KEY (orderID, snapshotNr)
);
