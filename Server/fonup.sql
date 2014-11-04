CREATE DATABASE  IF NOT EXISTS `fonup` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `fonup`;
-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: fonup
-- ------------------------------------------------------
-- Server version	5.5.37-0ubuntu0.12.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `idAnswer` int(7) NOT NULL AUTO_INCREMENT,
  `idQuestion` int(7) NOT NULL,
  `text` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `correct` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idAnswer`),
  KEY `idQuestion` (`idQuestion`),
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,1,'Skup podataka',0),(2,1,'Slozen tip podataka',1),(3,1,'Vrednost podataka',0),(4,1,'Skup vrednosti podataka',0),(5,2,'Skup vrednosti i operacija nad tim vrednostima',1),(6,2,'Skup vrednosti koje imaju isto znacenje',0),(7,2,'Skup operacija',0),(8,2,'Skup pokazivaca',0),(9,3,'O(n^2)',0),(10,3,'O(log(n))',0),(11,3,'O(n)',1),(12,3,'O(1)',0),(13,4,'O(n log(n)) sortiranja',0),(14,4,'\'Podeli-pa-vladaj\' (divide-and-conquer) sortiranja)',1),(15,4,'Interchange sortiranja',1),(16,4,'Prosecno vreme izvrsenja je kvadratno',0),(17,5,'Lista implementirana kao niz',1),(18,5,'Dvostruko spregnuta lista',0),(19,5,'Jednostruko spregnuta lista',0),(20,5,'I jednostruko spregnuta i dvostruko spregnuta',0),(21,6,'niz[1]',0),(22,6,'niz[2]',0),(23,6,'niz[11]',0),(24,6,'niz[12]',1),(25,7,'(S % 1) + kapacitetNiza',0),(26,7,'s % (1 + kapacitetNiza)',0),(27,7,'(s + 1) % kapacitetNiza',1),(28,7,'s + (1 % kapacitetNiza)',0),(29,8,'9,4,12,2,6,8,18',0),(30,8,'4,9,12,2,6,8,18',0),(31,8,'2,4,12,9,6,8,18',1),(32,8,' 2,4,9,12,6,8,18',0),(33,9,'niz[0]',1),(34,9,'niz[9]',0),(35,9,'niz[1]',0),(36,9,'niz[10]',0),(37,10,'ABCD',1),(38,10,'ABDC',0),(39,10,'DCAB',0),(40,10,'DCBA',0),(41,11,'O(n2)',0),(42,11,'O(log n)',0),(43,11,'O(1)',1),(44,11,'O(n) ',0),(45,12,'niz[0]',0),(46,12,'niz[9]',1),(47,12,'niz[1]',0),(48,12,'niz[10]',0),(49,13,'58',1),(50,13,'35',0),(51,13,'89',0),(52,13,'0',0),(53,14,'ubaci(34), izbaci(),ubaci(11),izbaci(),ubaci(4),ubaci(17)',1),(54,14,'ubaci(25), izbaci(),ubaci(12),izbaci(),ubaci(9),ubaci(17)',0),(55,14,'ubaci(37), izbaci(),ubaci(17),izbaci(),ubaci(5),ubaci(17)',0),(56,14,'ubaci(65), izbaci(),ubaci(16),izbaci(),ubaci(7),ubaci(17)',0),(57,15,'5',0),(58,15,'8',1),(59,15,'4',0),(60,15,'6',0),(61,16,'L.sledeci.prethodni',0),(62,16,'L.prethodni.prethodni',0),(63,16,'L.sledeci.prethodni.prethodni',1),(64,16,'L.prethodni.sledeci',0),(65,17,'1 2 4 5',1),(66,17,'1 3 4 5',0),(67,17,'1 2 3 5',0),(68,17,'Nista od navedenog',0),(69,18,'Stek',0),(70,18,'Red',0),(71,18,'DS lista',1),(72,18,'Sve je jednako dobro',0),(73,19,'Vreme izvrsavanja algoritma je proporcionalno sa n',1),(74,19,'Vreme izvrsavanja algoritma je manje od n sekundi',0),(75,19,'Algoritam ima n ugnjezdenih petlji',0),(76,19,'Algoritam je n puta sporiji od standardnog algoritma',0),(77,20,'3',0),(78,20,'4',0),(79,20,'5',0),(80,20,'Naci ce broj pre trece iteracije',1),(81,21,'O(n log n) sortiranja',0),(82,21,'\'Podeli-pa-vladaj\' (divide-and-conquer) sortiranja',0),(83,21,'Interchange sortiranja',1),(84,21,'Prosecno vreme izvrsenja je kvadratno',0),(85,22,'10000011 01101011 00110111 11011110',1),(86,22,'10000011 01101011 00110110 11011101',0),(87,22,'10000001 01101011 00110110 11011101',0),(88,22,'01000001 10010001 01110111 10001010',0),(89,23,'10',0),(90,23,'100',0),(91,23,'1000',0),(92,23,'1024',1),(93,23,'999',0),(94,24,'Drugu generaciju racunara',0),(95,24,'Trecu generaciju racunara',0),(96,24,'Cetvrtu generaciju racunara',1),(97,24,'Petu generaciju racunara',0),(98,25,'Drugu generaciju racunara',0),(99,25,'Trecu generaciju racunara',0),(100,25,'Cetvrtu generaciju racunara',0),(101,25,'Petu generaciju racunara',1),(102,26,'U prvoj generaciji racunara',0),(103,26,'U drugoj generaciji racunara',0),(104,26,'U trecoj generaciji racunara',0),(105,26,'U cetvrtoj generaciji racunara',1),(106,26,'U petoj generaciji racunara',0),(107,27,'Gordon Moore',0),(108,27,'Doug Engelbart',1),(109,27,'John Kemeny',0),(110,27,'Thomas Kurtz',0),(111,27,'John Backus',0),(112,28,'Gordon Moore',0),(113,28,'Doug Engelbart',0),(114,28,'John Kemeny',0),(115,28,'Thomas Kurtz',0),(116,28,'John Backus',1),(117,29,'Broj bita koji mogu biti procesirani odjednom',1),(118,29,'Broj bita koji mogu biti preneti odjednom',0),(119,29,'Broj bia koji sadrzi operacioni kod masinskih instrukcija',0),(120,29,'Broj bita koji sadrze operandi masinskih instrukcija',0),(121,30,'Broj bita koji mogu biti procesirani odjednom',0),(122,30,'Broj bia koji sadrzi operacioni kod masinskih instrukcija',0),(123,30,'Broj bita koji mogu biti preneti odjednom',1),(124,30,'Broj bita koji sadrze operandi masinskih instrukcija',0),(125,31,'Object packager',0),(126,31,'Java Virtual Machine',0),(127,31,'Kompajler',1),(128,31,'Interpreter',0),(129,31,'Linker',0),(130,32,'HTML',0),(131,32,'SMTP',1),(132,32,'POP3',0),(133,32,'HTTP',0);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answered`
--

DROP TABLE IF EXISTS `answered`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answered` (
  `idClient` int(7) NOT NULL,
  `idTest` int(7) NOT NULL,
  `idQuestion` int(7) NOT NULL,
  `idItemTest` int(7) NOT NULL,
  `idMaster` int(7) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idMaster`),
  KEY `idTest` (`idTest`),
  KEY `idItemTest` (`idItemTest`),
  KEY `idQuestion` (`idQuestion`),
  KEY `fk_answered_client_idx` (`idClient`),
  CONSTRAINT `fk_answered_client` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_answered_test` FOREIGN KEY (`idTest`) REFERENCES `test` (`idTest`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_answered_question` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_answered_itemtest` FOREIGN KEY (`idItemTest`) REFERENCES `testitem` (`idItemTest`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answered`
--

LOCK TABLES `answered` WRITE;
/*!40000 ALTER TABLE `answered` DISABLE KEYS */;
/*!40000 ALTER TABLE `answered` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answeritem`
--

DROP TABLE IF EXISTS `answeritem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answeritem` (
  `idTest` int(7) NOT NULL,
  `idQuestion` int(7) NOT NULL,
  `idItemTest` int(7) NOT NULL,
  `idAnswer` int(7) NOT NULL,
  `idMaster` int(7) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idMaster`),
  KEY `idTest` (`idTest`),
  KEY `fk_answeritem_question_idx` (`idQuestion`),
  KEY `fk_answeritem_itemtest_idx` (`idItemTest`),
  KEY `fk_answeritem_answer_idx` (`idAnswer`),
  CONSTRAINT `fk_answeritem_test` FOREIGN KEY (`idTest`) REFERENCES `test` (`idTest`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_answeritem_question` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_answeritem_itemtest` FOREIGN KEY (`idItemTest`) REFERENCES `testitem` (`idItemTest`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_answeritem_answer` FOREIGN KEY (`idAnswer`) REFERENCES `answer` (`idAnswer`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answeritem`
--

LOCK TABLES `answeritem` WRITE;
/*!40000 ALTER TABLE `answeritem` DISABLE KEYS */;
/*!40000 ALTER TABLE `answeritem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `idCat` int(7) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `approved` tinyint(1) DEFAULT '0',
  `idCate` int(7) DEFAULT NULL,
  PRIMARY KEY (`idCat`),
  KEY `idCate` (`idCate`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`idCate`) REFERENCES `category` (`idCat`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Informacioni sistemi i tehnologije',1,NULL),(2,'Prva godina',1,1),(3,'Druga godina',1,1),(4,'Treca godina',1,1),(5,'Cetvrta godina',1,1),(6,'Strukture podataka i algoritmi',1,3),(7,'Prvi kolokvijum',1,6),(8,'Osnove informaciono komunikacionih tehnologija',1,2),(9,'Prvi kolokvijum',1,8),(10,'sfdf',1,8),(11,'Operaciona istrazivanja 2',1,4),(12,'Prvi kolokvijum',1,11),(13,'Drugi kolokvijum',1,11),(14,'Internet tehnologije',1,5),(15,'Test',1,14),(16,'dddd',1,3);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `idClient` int(7) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `coname` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `forbiden` tinyint(1) DEFAULT '0',
  `admin` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Tihomir','Radosavljevic','tihi','234',NULL,0,1),(2,'Jelena','Tabas','jelena','123',NULL,0,0),(3,'Lazar','Ristic','laki','12345678','laki.smarac@gmail.com',0,1),(4,'Administrator','Administrator','Administrator','Administrator','Administrator@fon.bg.ac.rs',0,1),(9,'NovoIme','NovoPrezime','Korisnik','Korisnik','Korisnik@fon.bg.ac.rs',0,0),(10,'Tihomir','Radosavljevic','Tihomir','12345678','radosavljevic9210i@fon.bg.ac.rs',1,0),(11,NULL,NULL,'laza',NULL,NULL,0,0),(12,NULL,NULL,'laki91',NULL,NULL,0,0),(13,NULL,NULL,'laster',NULL,NULL,0,0),(14,'Tihomir','Radosavljevic','Tihi','12345678','rad9210i@fon.bg.ac.rs',0,0);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `idQuestion` int(7) NOT NULL AUTO_INCREMENT,
  `text` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `approved` tinyint(1) DEFAULT '0',
  `deleted` tinyint(1) DEFAULT '0',
  `idCat` int(7) DEFAULT NULL,
  PRIMARY KEY (`idQuestion`),
  KEY `idCat` (`idCat`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`idCat`) REFERENCES `category` (`idCat`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'Sta je struktura podataka?',1,0,7),(2,'Sta je tip podatka?',1,0,7),(3,'Kakva je vremenska kompleksnost najboljeg moguceg alogartima za pretrazivanje dovstruko spregnute liste koja ima n elemenata?',1,0,7),(4,'Merge sort i Quick sort pripadaju istoj kategoriji alogaritama. Koja je to kategorija?',1,0,7),(5,'Koja vrsta liste ce najbrze dati odgovor na pitanje \'Koji je element na poziciji n?\'',1,0,7),(6,'Pretpostavimo da imamo red implementiran preko niza, kapaciteta 42 i u njega 11 ubacenih elemenata (niz[2] do niz[11]). Na koju poziciju ce metoda enqueue ubaciti novi element?',1,0,7),(7,'Ako je red implementiran preko niza i ako je s index poslednjeg ubacenog elementa u niz, koja je formula za dobijanje sledeceg indexa za ubacivanje?',1,0,7),(8,'Dat je niz elemenata: [9,4,12,2,6,8,18]. Kako ce izgledati niz prvog prolaza kroz niz alogaritama Selection sort?',1,0,7),(9,'Pretpostavimo da imamo red implementiran preko niza, kapaciteta 42 i u njega 10 ubacenih elemenata (niz[0] do niz[9]). Sa koje pozicije će metoda dequeue() izbaciti element?',1,0,7),(10,'Ako su slova \'D\', \'C\', \'B\', \'A\' smestena u stack (u datom redosledu), a zatim izbacujemo jedno po jedno slovo, kojim redosledom ce biti izbacena?',1,0,7),(11,'Kakva je vremenska kompleksnost najboljeg moguceg algoritma za pretraživanje jednostruko spregnute liste?',1,0,7),(12,'Pretpostavimo da imamo stack implementiran preko niza, kapaciteta 42 i u njega 10 ubacenih elemenata (niz[0] do niz[9]). Sa koje pozicije ce metoda pop() izbaciti element?',1,0,7),(13,'Dat je neki niz celih brojeva i neki skup naredbi.int[] b = { 7, 8, 15, 23, 36 };public static int saberiRekurzivno(int[] niz, int index) throws Exception{  if(niz == null || niz.length == 0)     throw new Exception(\'Niz je prazan\');  if(index >= niz.length)     return 0;  return niz[index] + saberiRekurzivno(niz, index+2); }Kolika je vrednost rezultata nakon izvrsenja ovog koda, ako su ulazni parametri b i 0?',1,0,7),(14,'Dat je niz brojeva, implementiran preko reda (2,5,7,8,12) kapaciteta 7. Pokazivaci P na 2 i K na 12. Koji niz komandi daje niz (4,17,7,8,12,34,11) a pokazivaci P na 7 i K na 17?',1,0,7),(15,'Brojevi ulaze redosledom 5,8,2,6,4 koji ce izaci drugi po redu ako primenimo FIFO?',1,0,7),(16,'Data je ciklicna DS lista. Ako je L pokazivac na prvi element, koji pokazuje na poslednji?',1,0,7),(17,'Dati su elementi DS liste (1,2,3,4,5) sta radi sledeca metoda?X=prvi.sledeci.sledeci;X.sledeci=x.prethodni.sledeci; x.prethodni=x.sledeci.prethodni;',1,0,7),(18,'Ako imamo ljude koji cekaju red u banci, i radnica na salteru svakog momenta moze da prozove bilo koga ko stoji u redu (ili proziva onoga ko je na redu) da pridje salteru za to je onda najbolje primeniti?',1,0,7),(19,'Kada se kaze da algoritam ima vremensku kompleksnost O(n) onda to znaci da?',1,0,7),(20,'Dat je neki niz celih brojeva i iterativni algoritam za interpolaciono pretrazivanje.int[] b = { 7, 8, 15, 23, 36 };public static int interpolaciono(int podatak, int[] n) { int l = 0; int d = n.length - 1; while (l <= d) { int index = l + (podatak - n[l])/(n[d] - n[l])*(d - l); if (n[index] == podatak) return index; if (n[index] > podatak) d = index - 1; else l = index + 1; } return -1; }Na kojoj poziciji se nalazi element u nizu kome pristupa algoritam u trecoj iteraciji, ako se trazi broj 36?',1,0,7),(21,'Selection sort i Quick sort pripadaju istoj kategoriji algoritama. Koja je to kategorija?',1,0,7),(22,'Vasem racunaru je dodeljena IP adresa 131.107.55.222. Koji od sledecih izraza predstavlja ovaj broj u binarnom formatu?',1,0,9),(23,'Koliko memorijskih lokacija se moze adresirati pomocu 10 bitova?',1,0,9),(24,'LSI (Large Scale Integration) tehnologija karakterise:',1,0,9),(25,'VLSI (Very Large Scale Integration) i UVLSI (Ultra Very Large Scale Integration) tehnologije karakterisu:',1,0,9),(26,'Procesr se na jednom cipu pojavljuje po prvi put:',1,0,9),(27,'Za uvodjenje prvo racunara misa je zasluzan',1,0,9),(28,'Tvorac programskog jezika FORTRAN:',1,0,9),(29,'Duzina reci nekog racunara predstavlja:',1,0,9),(30,'Sirina magistrale nekog racunara predstavlja:',1,0,9),(31,'Kako se zove program koji prevodi citav (source) kod celog programa u masinski jezik?',1,0,9),(32,'Koji protokol se koristi za slanje elektronske poste na e-mail server?',1,0,9),(33,'Hello',0,0,15);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `result`
--

DROP TABLE IF EXISTS `result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `result` (
  `idClient` int(7) NOT NULL,
  `idTest` int(7) NOT NULL,
  `date` date DEFAULT NULL,
  `value` int(11) DEFAULT '0',
  `idMaster` int(7) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idMaster`),
  KEY `fk_result_client_idx` (`idClient`),
  KEY `fk_result_test_idx` (`idTest`),
  CONSTRAINT `fk_result_client` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_result_test` FOREIGN KEY (`idTest`) REFERENCES `test` (`idTest`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `result`
--

LOCK TABLES `result` WRITE;
/*!40000 ALTER TABLE `result` DISABLE KEYS */;
/*!40000 ALTER TABLE `result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `idTest` int(7) NOT NULL AUTO_INCREMENT,
  `idCat` int(7) NOT NULL,
  PRIMARY KEY (`idTest`),
  KEY `idCat` (`idCat`),
  CONSTRAINT `test_ibfk_1` FOREIGN KEY (`idCat`) REFERENCES `category` (`idCat`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,7),(2,7),(3,7),(4,7),(5,7),(6,7),(7,7),(8,7),(9,7),(10,7),(11,7),(12,7),(13,7),(14,7),(15,7),(16,7),(17,7),(18,7),(19,7);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testitem`
--

DROP TABLE IF EXISTS `testitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testitem` (
  `idItemTest` int(7) NOT NULL AUTO_INCREMENT,
  `idTest` int(7) NOT NULL,
  `idQuestion` int(7) NOT NULL,
  PRIMARY KEY (`idItemTest`),
  KEY `fk_testitem_test_idx` (`idTest`),
  KEY `fk_testitem_question_idx` (`idQuestion`),
  CONSTRAINT `fk_testitem_test` FOREIGN KEY (`idTest`) REFERENCES `test` (`idTest`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_testitem_question` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testitem`
--

LOCK TABLES `testitem` WRITE;
/*!40000 ALTER TABLE `testitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `testitem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-02 10:48:19
