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
-- Table structure for table `communicazione`
--

DROP TABLE IF EXISTS `communicazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `communicazione` (
  `nomeUtenteDottore` varchar(255) NOT NULL,
  `descrizione` varchar(1500) DEFAULT NULL,
  `dataOrarioCommunicazione` timestamp NOT NULL,
  PRIMARY KEY (`nomeUtenteDottore`,`dataOrarioCommunicazione`),
  CONSTRAINT `communicazione_ibfk_1` FOREIGN KEY (`nomeUtenteDottore`) REFERENCES `utente` (`nomeUtente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `communicazione`
--

LOCK TABLES `communicazione` WRITE;
/*!40000 ALTER TABLE `communicazione` DISABLE KEYS */;
INSERT INTO `communicazione` VALUES ('admin','Admin Notizia 1','2020-07-14 05:30:26'),('admin','Admin Notizia 2','2020-07-14 05:30:35'),('testDottore','Notizia 1','2020-07-08 13:10:08'),('testDottore','Notizia 2','2020-07-08 13:10:12'),('testDottore','Notizia 3','2020-07-08 13:10:15'),('testDottore','Notizia 4','2020-07-08 13:10:18'),('testDottore','Notizia 5','2020-07-08 13:10:22'),('testDottore','Notizia 6','2020-07-08 13:10:27'),('testDottore','Notizia 7','2020-07-08 13:10:31'),('testDottore','Notizia 8','2020-07-08 13:10:38'),('testDottore','Notizia 9','2020-07-08 13:10:41'),('testDottore','Notizia 10','2020-07-16 06:39:54'),('testDottore2','Notizia 1','2020-07-14 07:22:03'),('testDottore2','Notizia 2','2020-07-14 07:22:12'),('testDottore2','Notizia 3','2020-07-14 07:23:15'),('testDottore3','Notizia 1','2020-07-16 12:16:16');
/*!40000 ALTER TABLE `communicazione` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-16 16:20:32
