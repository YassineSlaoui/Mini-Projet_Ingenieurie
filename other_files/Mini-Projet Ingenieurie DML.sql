INSERT INTO Client(nomClient,numTel,adresse,nationalite) VALUES
	('Maintenance Hotel','','',''),
	('Amir','+0000000','adr1','Tun'),
    ('Ali','+0000001','adr2','Fr'),
    ('Mariem','+0000002','adr3','US');
    
INSERT INTO TypeCh(typeCh,capacite) VALUES
	('Simple',1),
    ('Double',2),
	('Triple',3),
    ('Quad',4),
    ('Suite',7);

INSERT INTO Chambre(numero,idTypeCh) VALUES
	(1,1),
    (2,1),
    (3,2),
    (4,2),
    (5,3),
    (6,3),
    (7,4),
    (8,4),
    (9,5),
    (10,5);
    
INSERT INTO Reservation(dateDebut,dateFin,idClient,idChambre) VALUES
	('2010-10-10 14:50','2010-10-12 15:50',1,4),
    ('2010-11-10 14:50','2010-11-12 15:50',2,4),
	('2010-12-10 14:50','2010-12-12 15:50',3,5),
    ('2010-11-15 14:50','2010-11-19 15:50',4,8),
    ('2010-12-18 14:50','2010-12-31 15:50',2,6),
    ('2010-09-15 14:50','2010-10-21 15:50',1,7),
	('2010-10-25 14:50','2010-11-06 15:50',3,10),
    ('2010-07-16 14:50','2010-08-23 15:50',1,9),
    ('2010-05-12 14:50','2010-05-13 15:50',4,1);