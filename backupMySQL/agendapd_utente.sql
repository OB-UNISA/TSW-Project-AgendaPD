-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: agendapd
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `nomeUtente` varchar(255) NOT NULL,
  `pass` varchar(512) DEFAULT NULL,
  `ruolo` tinyint DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `nome` varchar(35) DEFAULT NULL,
  `cognome` varchar(35) DEFAULT NULL,
  `cf` varchar(16) DEFAULT NULL,
  `dataDiNascita` date DEFAULT NULL,
  `via` varchar(40) DEFAULT NULL,
  `citta` varchar(30) DEFAULT NULL,
  `cap` int DEFAULT NULL,
  PRIMARY KEY (`nomeUtente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('admin','Root1',-1,'admin@agendapd.it','Admin','Admin','AAAAAAAAAAAAAAAA','1990-01-01','Admin','Admin',1111),('testDottore','Root1',3,'d@tester.it','Franco','Rossi','DDDDDDDDDDDDDDDD','1990-01-01','Vittorio','Milano',20019),('testDottore2','Root1',3,'d2@tester.it','Luigi','Verdi','LVLVLVLVLVLVLVLV','1980-05-01','Emanuele','Roma',45871),('testDottore3','Root1',3,'d3@tester.it','Antonella','Mauri','GS54VF53DF4G5DF5','1965-06-05','Salvi','Napoli',57545),('testPaziente','Root1',1,'p@tester.it','Mario','Bianchi','PPPPPPPPPPPPPPPP','1990-01-01','Emanuele','Milano',20019),('testPaziente1','Root1',1,'p1@tester.it','Francesco','Marroni','MFMGJDIEHG98JSBG','1970-03-15','Palme','Milano',20019),('testPaziente2','Root1',1,'p2@tester.it','Pluto','Neri','PNPNPNPNPNPNPNPN','1985-09-25','Umberto','Milano',20019),('testPaziente3','Root1',1,'p3@tester.it','Stefano','Lavoro','SWSWSWSWSWSWSWSW','1955-02-24','Mela','Milano',20019),('testPaziente4','Root1',1,'p4@tester.it','Luca','Vasto','LVLVLVVDV445DFD4','1970-05-04','Degli specchi','Genova',57545),('testSegretaria','Root1',2,'s@tester.it','Chiara','Verdi','SSSSSSSSSSSSSSSS','1984-10-05','Garibaldi','Genova',819778),('testSegretaria1','Root1',2,'s1@tester.it','Anna','Luce','ALALALALALALALAL','1970-02-15','Garibaldi','Napoli',5435),('testSegretaria2','Root1',2,'s2@tester.it','Lucia','Biondi','WR54F35W4D5F45DD','1960-08-07','Lastri','Perugia',5454);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-16 16:20:33
