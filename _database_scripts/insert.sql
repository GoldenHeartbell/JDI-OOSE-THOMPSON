use JDIOnderzoek;

delete from EventListOrderStatus;
delete from EventSourceProductQuantity;
delete from productInOrder;
delete from orders;
delete from product;
delete from customer;

ALTER TABLE EventListOrderStatus AUTO_INCREMENT = 1;
ALTER TABLE EventSourceProductQuantity AUTO_INCREMENT = 1;
ALTER TABLE productInOrder AUTO_INCREMENT = 1;
ALTER TABLE orders AUTO_INCREMENT = 1;
ALTER TABLE product AUTO_INCREMENT = 1;
ALTER TABLE customer AUTO_INCREMENT = 1;

insert into customer (customerName, customerAddress, customerType)
values('Big Corperation', 'Corporation Avenue 12', 'corp'),
      ('Suleiman Osmangolu','Trabzon Road 3','prsn'),
      ('Small Corp. Philly','Corporation Avenue 56','corp'),
      ('John Komnenos','Trabzon Road 7','prsn');

insert into product (productName, productPrice, productDescription, productQuantity)
values('Orbans bells',99.99,'Hand crafted bronze bells. Can crack if rang excessivly in an hour', 50),
      ('Ace toolset',34.99,'High quality toolset.', 100),
      ('Bohemian glass pannels',50.00,'High quality glass pannels. Wont break even if four people get thrown at it.', 60),
      ('Grey Gravel',12.50,'10 Kg of Grey gravel.', 125);

insert into orders(customerID)
values(1), (1), (2), (3), (4);

insert into productInOrder(orderID,productID,productQuantity,productPrice)
values(1,3,6,50.00), (2,4,3,12.50),
      (3,1,3,99.99), (3,2,1,34.99),
      (4,2,6,34.99), (5,3,1,50.00),
      (5,2,1,34.99);

insert into EventListOrderStatus (orderID, versionNr, dateCreated, orderStatus)
values(1, 1, '2021-11-29 15:55:22', 'RECEIVED'),
      (1, 2, '2021-11-29 15:58:48', 'ACCEPTED'),
      (1, 3, '2021-11-29 15:58:52', 'READY FOR SHIPMENT'),
      (2, 1, '2020-05-26 12:56:23', 'RECEIVED'),
      (2, 2, '2020-05-26 12:57:23', 'ACCEPTED'),
      (2, 3, '2020-05-26 12:58:23', 'READY FOR SHIPMENT'),
      (2, 4, '2020-05-26 14:35:45', 'SHIPPED'),
      (2, 5, '2020-05-26 15:22:12', 'DELIVERED'),
      (2, 6, '2020-07-14 08:24:12', 'RETURNED'),
      (4,1,'2020-07-14 08:24:12','BIG TEST');

insert into EventSourceProductQuantity (productID, versionNr, dateCreated, eventType, quantity)
values(1, 1, '2021-11-29 15:55:22', 'RECEIVED', 10),
      (1, 2, '2021-11-29 15:58:48', 'SHIPPED', -1),
      (1, 3, '2021-11-29 15:58:52', 'CORRECTION', -5),
      (2, 1, '2020-05-26 12:56:23', 'RECEIVED', 200),
      (2, 2, '2020-05-26 12:57:23', 'RECEIVED', 4),
      (2, 3, '2020-05-26 12:58:23', 'CORRECTION', -130),
      (2, 4, '2020-05-26 14:35:45', 'CORRECTION', 26),
      (2, 5, '2020-05-26 15:22:12', 'SHIPPED', -12),
      (2, 6, '2020-07-14 08:24:12', 'RECEIVED', 9);
