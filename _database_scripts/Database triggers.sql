DROP TRIGGER IF EXISTS orderSnapshotTrigger; 

DELIMITER $$
CREATE TRIGGER orderSnapshotTrigger
AFTER INSERT ON EventListOrderStatus
FOR EACH ROW
	IF (select NEW.versionNr from EventListOrderStatus ORDER BY versionNr DESC limit 1) % 3 <= 0
	THEN
		INSERT INTO OrderStatusSnapshots VALUES (NEW.orderId, NEW.versionNr/3, NEW.versionNr, NEW.dateCreated, NEW.orderStatus);
    END IF;


DROP TRIGGER IF EXISTS productAmountSnapshotTrigger;
DELIMITER $$
CREATE TRIGGER productAmountSnapshotTrigger
AFTER INSERT ON EventSourceProductQuantity
FOR EACH ROW
	if(select NEW.versionNr from EventSourceProductQuantity ORDER BY versionNr DESC limit 1) % 3 <= 0
    THEN
		INSERT INTO ProductQuantitySnapshots VALUES(NEW.productID, NEW.versionNr/3, NEW.versionNr, NEW.dateCreated, NEW.eventType, (select sum(quantity) as totalQuantity from EventSourceProductQuantity where productID = NEW.productID));
	END IF;