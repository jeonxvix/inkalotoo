-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: prueba1
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `billetera_usuario`
--

DROP TABLE IF EXISTS `billetera_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `billetera_usuario` (
  `id_billetera` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `saldo_actual` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id_billetera`),
  UNIQUE KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `fk_billetera_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billetera_usuario`
--

LOCK TABLES `billetera_usuario` WRITE;
/*!40000 ALTER TABLE `billetera_usuario` DISABLE KEYS */;
INSERT INTO `billetera_usuario` VALUES (1,1,112.00),(2,7,0.00);
/*!40000 ALTER TABLE `billetera_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `juego`
--

DROP TABLE IF EXISTS `juego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `juego` (
  `id_juego` int NOT NULL AUTO_INCREMENT,
  `nombre_juego` varchar(100) NOT NULL,
  `tipo_juego` enum('BINGO','SORTEO','SLOTS') NOT NULL,
  `precio_apuesta` decimal(10,2) NOT NULL,
  `reglas` text,
  `premio_base` decimal(10,2) DEFAULT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_juego`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `juego`
--

LOCK TABLES `juego` WRITE;
/*!40000 ALTER TABLE `juego` DISABLE KEYS */;
INSERT INTO `juego` VALUES (1,'BINGO BÁSICO','BINGO',1.00,NULL,20.00,1),(2,'BINGO MEDIO','BINGO',2.00,NULL,40.00,1),(3,'BINGO PREMIUM','BINGO',5.00,NULL,100.00,1),(4,'Sorteo Diario 1 Sol','SORTEO',1.00,'Participa en el sorteo diario por solo S/ 1.00. \n- El sorteo se realiza todos los días a las 20:00 hrs.\n- El ganador se elige aleatoriamente entre todos los participantes.\n- El premio base es de S/ 50.00 más el 80% del pozo acumulado.\n- Máximo 1000 participantes por sorteo.',50.00,1),(5,'Sorteo Diario 2 Soles','SORTEO',2.00,'Participa en el sorteo diario premium por S/ 2.00. \n- El sorteo se realiza todos los días a las 21:00 hrs.\n- Mayor premio y menos participantes.\n- El premio base es de S/ 100.00 más el 85% del pozo acumulado.\n- Máximo 500 participantes por sorteo.',100.00,1),(6,'Sorteo Semanal 1 Sol','SORTEO',1.00,'Gran sorteo semanal por solo S/ 1.00. \n- El sorteo se realiza todos los domingos a las 18:00 hrs.\n- Premio acumulativo durante toda la semana.\n- El premio base es de S/ 200.00 más el 90% del pozo acumulado.\n- Sin límite de participantes.',200.00,1),(7,'Sorteo Semanal 2 Soles','SORTEO',2.00,'Gran sorteo semanal premium por S/ 2.00. \n- El sorteo se realiza todos los domingos a las 19:00 hrs.\n- El premio más grande de la plataforma.\n- El premio base es de S/ 500.00 más el 95% del pozo acumulado.\n- Sin límite de participantes.',500.00,1);
/*!40000 ALTER TABLE `juego` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugada`
--

DROP TABLE IF EXISTS `jugada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jugada` (
  `id_jugada` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_juego` int NOT NULL,
  `id_sorteo` int DEFAULT NULL,
  `codigo_jugada` varchar(30) NOT NULL,
  `detalle_jugada` varchar(255) NOT NULL,
  `monto_apostado` decimal(10,2) NOT NULL,
  `gano` tinyint(1) NOT NULL DEFAULT '0',
  `monto_ganado` decimal(10,2) NOT NULL DEFAULT '0.00',
  `id_mov_apuesta` int NOT NULL,
  `id_mov_premio` int DEFAULT NULL,
  `fecha_jugada` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_jugada`),
  KEY `fk_jugada_usuario` (`id_usuario`),
  KEY `fk_jugada_juego` (`id_juego`),
  KEY `fk_jugada_sorteo` (`id_sorteo`),
  KEY `fk_jugada_mov_apuesta` (`id_mov_apuesta`),
  KEY `fk_jugada_mov_premio` (`id_mov_premio`),
  CONSTRAINT `fk_jugada_juego` FOREIGN KEY (`id_juego`) REFERENCES `juego` (`id_juego`),
  CONSTRAINT `fk_jugada_mov_apuesta` FOREIGN KEY (`id_mov_apuesta`) REFERENCES `movimiento_billetera` (`id_movimiento`),
  CONSTRAINT `fk_jugada_mov_premio` FOREIGN KEY (`id_mov_premio`) REFERENCES `movimiento_billetera` (`id_movimiento`),
  CONSTRAINT `fk_jugada_sorteo` FOREIGN KEY (`id_sorteo`) REFERENCES `sorteo` (`id_sorteo`),
  CONSTRAINT `fk_jugada_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugada`
--

LOCK TABLES `jugada` WRITE;
/*!40000 ALTER TABLE `jugada` DISABLE KEYS */;
INSERT INTO `jugada` VALUES (11,1,1,NULL,'BINGO-TEST-123','Jugada añadida manualmente para prueba',5.00,0,0.00,1,NULL,'2025-11-30 02:02:05'),(12,1,1,NULL,'BINGO-1764486164918','Compra de 1 cartón(es) de bingo',1.00,0,0.00,94,NULL,'2025-11-30 07:02:45'),(13,1,1,NULL,'BINGO-1764486490282','Compra de 1 cartón(es) de bingo',1.00,0,0.00,95,NULL,'2025-11-30 07:08:10'),(14,1,1,NULL,'BINGO-1764486682266','Compra de 1 cartón(es) de bingo',1.00,0,0.00,96,NULL,'2025-11-30 07:11:22'),(15,1,1,NULL,'BINGO-1764486820090','Compra de 1 cartón(es) de bingo',1.00,0,0.00,97,NULL,'2025-11-30 07:13:40'),(16,1,1,NULL,'BINGO-1764536744959','Compra de 1 cartón(es) de bingo',5.00,0,0.00,99,NULL,'2025-11-30 21:05:45'),(17,1,1,NULL,'BINGO-1764536760184','Compra de 15 cartón(es) de bingo',75.00,0,0.00,100,NULL,'2025-11-30 21:06:00'),(18,1,1,NULL,'BINGO-1764536876034','Compra de 3 cartón(es) de bingo',3.00,0,0.00,101,NULL,'2025-11-30 21:07:56'),(19,1,1,NULL,'BINGO-1764536902847','Compra de 3 cartón(es) de bingo',3.00,0,0.00,102,NULL,'2025-11-30 21:08:23'),(25,1,2,1,'SORTEO-1764542268606','Participación en sorteo',2.00,0,0.00,108,NULL,'2025-11-30 22:37:49'),(26,1,2,2,'SORTEO-1764542316611','Participación en sorteo',2.00,0,0.00,109,NULL,'2025-11-30 22:38:37'),(27,1,2,4,'SORTEO-1764542393021','Participación en sorteo',2.00,0,0.00,111,NULL,'2025-11-30 22:39:53'),(28,1,2,5,'SORTEO-1764543599774','13-35-8-26-30-15',2.00,0,0.00,112,NULL,'2025-11-30 23:00:00'),(29,1,1,NULL,'BINGO-1764543860792','Compra de 1 cartón(es) de bingo',1.00,0,0.00,113,NULL,'2025-11-30 23:04:21'),(30,1,2,6,'SORTEO-1764547662173','5-2-40-4-3-26',2.00,0,0.00,115,NULL,'2025-12-01 00:07:42'),(31,1,3,NULL,'SLOT-1764550671365','?-?-?',1.00,1,10.00,116,117,'2025-12-01 00:57:51'),(32,1,3,NULL,'SLOT-1764550673167','?-?-?',1.00,1,10.00,118,119,'2025-12-01 00:57:53'),(33,1,3,NULL,'SLOT-1764550674133','?-?-?',1.00,1,10.00,120,121,'2025-12-01 00:57:54'),(34,1,3,NULL,'SLOT-1764550674613','?-?-?',1.00,1,10.00,122,123,'2025-12-01 00:57:55'),(35,1,3,NULL,'SLOT-1764550674818','?-?-?',1.00,1,10.00,124,125,'2025-12-01 00:57:55'),(36,1,3,NULL,'SLOT-1764550674994','?-?-?',1.00,1,10.00,126,127,'2025-12-01 00:57:55'),(37,1,3,NULL,'SLOT-1764550675178','?-?-?',1.00,1,10.00,128,129,'2025-12-01 00:57:55'),(38,1,3,NULL,'SLOT-1764550675370','?-?-?',1.00,1,10.00,130,131,'2025-12-01 00:57:55'),(39,1,3,NULL,'SLOT-1764550689584','?-?-?',1.00,1,10.00,132,133,'2025-12-01 00:58:10'),(40,1,3,NULL,'SLOT-1764550691015','?-?-?',1.00,1,10.00,134,135,'2025-12-01 00:58:11'),(41,1,3,NULL,'SLOT-1764551293083','?-?-?',1.00,1,10.00,136,137,'2025-12-01 01:08:13'),(42,1,3,NULL,'SLOT-1764551294649','?-?-?',1.00,1,10.00,138,139,'2025-12-01 01:08:15'),(43,1,3,NULL,'SLOT-1764551295417','?-?-?',1.00,1,10.00,140,141,'2025-12-01 01:08:15'),(44,1,3,NULL,'SLOT-1764551295804','?-?-?',1.00,1,10.00,142,143,'2025-12-01 01:08:16'),(45,1,3,NULL,'SLOT-1764551296067','?-?-?',1.00,1,10.00,144,145,'2025-12-01 01:08:16'),(46,1,3,NULL,'SLOT-1764551296261','?-?-?',1.00,1,10.00,146,147,'2025-12-01 01:08:16'),(47,1,3,NULL,'SLOT-1764551296446','?-?-?',1.00,1,10.00,148,149,'2025-12-01 01:08:16'),(48,1,3,NULL,'SLOT-1764551296627','?-?-?',1.00,1,10.00,150,151,'2025-12-01 01:08:17'),(49,1,3,NULL,'SLOT-1764552124044','?-?-?',1.00,1,10.00,152,153,'2025-12-01 01:22:04'),(50,1,3,NULL,'SLOT-1764552129641','?-?-?',1.00,1,10.00,154,155,'2025-12-01 01:22:10'),(51,1,3,NULL,'SLOT-1764552628589','⭐-?-⭐',1.00,0,0.00,156,NULL,'2025-12-01 01:30:29'),(52,1,3,NULL,'SLOT-1764552630241','⭐-?-?',1.00,0,0.00,157,NULL,'2025-12-01 01:30:30'),(53,1,3,NULL,'SLOT-1764552631188','?-?-?',1.00,0,0.00,158,NULL,'2025-12-01 01:30:31'),(54,1,3,NULL,'SLOT-1764552631622','?-?-?',1.00,0,0.00,159,NULL,'2025-12-01 01:30:32'),(55,1,3,NULL,'SLOT-1764552632172','?-?-?',1.00,0,0.00,160,NULL,'2025-12-01 01:30:32'),(56,1,3,NULL,'SLOT-1764552632641','?-⭐-?',1.00,0,0.00,161,NULL,'2025-12-01 01:30:33'),(57,1,3,NULL,'SLOT-1764552633007','⭐-?-?',1.00,0,0.00,162,NULL,'2025-12-01 01:30:33'),(58,1,3,NULL,'SLOT-1764552633707','?-?-?',1.00,0,0.00,163,NULL,'2025-12-01 01:30:34'),(59,1,3,NULL,'SLOT-1764552634172','?-?-?',1.00,0,0.00,164,NULL,'2025-12-01 01:30:34'),(60,1,3,NULL,'SLOT-1764552634652','?-?-?',1.00,0,0.00,165,NULL,'2025-12-01 01:30:35'),(61,1,3,NULL,'SLOT-1764552635388','?-?-⭐',1.00,0,0.00,166,NULL,'2025-12-01 01:30:35'),(62,1,3,NULL,'SLOT-1764552636101','?-?-?',1.00,0,0.00,167,NULL,'2025-12-01 01:30:36'),(63,1,3,NULL,'SLOT-1764552636714','⭐-?-?',1.00,0,0.00,168,NULL,'2025-12-01 01:30:37'),(64,1,3,NULL,'SLOT-1764552637241','?-?-?',1.00,0,0.00,169,NULL,'2025-12-01 01:30:37'),(65,1,3,NULL,'SLOT-1764552637688','?-?-?',1.00,0,0.00,170,NULL,'2025-12-01 01:30:38'),(66,1,3,NULL,'SLOT-1764552638305','?-?-?',1.00,0,0.00,171,NULL,'2025-12-01 01:30:38'),(67,1,3,NULL,'SLOT-1764552638746','?-?-⭐',1.00,0,0.00,172,NULL,'2025-12-01 01:30:39'),(68,1,3,NULL,'SLOT-1764552638922','?-?-?',1.00,0,0.00,173,NULL,'2025-12-01 01:30:39'),(69,1,3,NULL,'SLOT-1764552639145','⭐-⭐-?',1.00,0,0.00,174,NULL,'2025-12-01 01:30:39'),(70,1,3,NULL,'SLOT-1764552639487','?-⭐-?',1.00,0,0.00,175,NULL,'2025-12-01 01:30:40'),(71,1,3,NULL,'SLOT-1764552639914','⭐-?-⭐',1.00,0,0.00,176,NULL,'2025-12-01 01:30:40'),(72,1,3,NULL,'SLOT-1764552640280','?-?-?',1.00,0,0.00,177,NULL,'2025-12-01 01:30:40'),(73,1,3,NULL,'SLOT-1764552640591','?-?-?',1.00,0,0.00,178,NULL,'2025-12-01 01:30:41'),(74,1,3,NULL,'SLOT-1764552640785','⭐-?-?',1.00,0,0.00,179,NULL,'2025-12-01 01:30:41'),(75,1,3,NULL,'SLOT-1764552640966','⭐-?-?',1.00,0,0.00,180,NULL,'2025-12-01 01:30:41'),(76,1,3,NULL,'SLOT-1764552641154','?-?-⭐',1.00,0,0.00,181,NULL,'2025-12-01 01:30:41'),(77,1,3,NULL,'SLOT-1764552641361','?-?-?',1.00,0,0.00,182,NULL,'2025-12-01 01:30:41'),(78,1,3,NULL,'SLOT-1764552641529','?-?-?',1.00,0,0.00,183,NULL,'2025-12-01 01:30:42'),(79,1,3,NULL,'SLOT-1764552641744','⭐-?-?',1.00,0,0.00,184,NULL,'2025-12-01 01:30:42'),(80,1,3,NULL,'SLOT-1764552642128','?-?-?',1.00,0,0.00,185,NULL,'2025-12-01 01:30:42'),(81,1,3,NULL,'SLOT-1764552643663','?-?-?',1.00,0,0.00,186,NULL,'2025-12-01 01:30:44'),(82,1,3,NULL,'SLOT-1764552643897','⭐-?-?',1.00,0,0.00,187,NULL,'2025-12-01 01:30:44'),(83,1,3,NULL,'SLOT-1764552644097','?-?-?',1.00,0,0.00,188,NULL,'2025-12-01 01:30:44'),(84,1,3,NULL,'SLOT-1764552644287','?-⭐-?',1.00,0,0.00,189,NULL,'2025-12-01 01:30:44'),(85,1,3,NULL,'SLOT-1764552644485','?-?-?',1.00,0,0.00,190,NULL,'2025-12-01 01:30:45'),(86,1,3,NULL,'SLOT-1764552644666','?-⭐-?',1.00,0,0.00,191,NULL,'2025-12-01 01:30:45'),(87,1,3,NULL,'SLOT-1764552644867','⭐-?-⭐',1.00,0,0.00,192,NULL,'2025-12-01 01:30:45'),(88,1,3,NULL,'SLOT-1764552645074','?-?-⭐',1.00,0,0.00,193,NULL,'2025-12-01 01:30:45'),(89,1,3,NULL,'SLOT-1764552645291','?-?-?',1.00,0,0.00,194,NULL,'2025-12-01 01:30:45'),(90,1,3,NULL,'SLOT-1764552645512','⭐-?-⭐',1.00,0,0.00,195,NULL,'2025-12-01 01:30:46'),(91,1,3,NULL,'SLOT-1764552645715','?-?-?',1.00,0,0.00,196,NULL,'2025-12-01 01:30:46'),(92,1,3,NULL,'SLOT-1764552645929','?-?-⭐',1.00,0,0.00,197,NULL,'2025-12-01 01:30:46'),(93,1,3,NULL,'SLOT-1764552646114','?-?-?',1.00,0,0.00,198,NULL,'2025-12-01 01:30:46'),(94,1,3,NULL,'SLOT-1764552646321','?-?-?',1.00,0,0.00,199,NULL,'2025-12-01 01:30:46'),(95,1,3,NULL,'SLOT-1764552646527','?-?-?',1.00,0,0.00,200,NULL,'2025-12-01 01:30:47'),(96,1,3,NULL,'SLOT-1764552646759','?-?-?',1.00,0,0.00,201,NULL,'2025-12-01 01:30:47'),(97,1,3,NULL,'SLOT-1764552646943','⭐-?-?',1.00,0,0.00,202,NULL,'2025-12-01 01:30:47'),(98,1,3,NULL,'SLOT-1764552647192','?-⭐-?',1.00,0,0.00,203,NULL,'2025-12-01 01:30:47'),(99,1,3,NULL,'SLOT-1764552647364','⭐-?-?',1.00,0,0.00,204,NULL,'2025-12-01 01:30:47'),(100,1,3,NULL,'SLOT-1764552647591','?-?-?',1.00,0,0.00,205,NULL,'2025-12-01 01:30:48'),(101,1,3,NULL,'SLOT-1764552647794','?-⭐-⭐',1.00,0,0.00,206,NULL,'2025-12-01 01:30:48'),(102,1,3,NULL,'SLOT-1764552648010','?-?-?',1.00,0,0.00,207,NULL,'2025-12-01 01:30:48'),(103,1,3,NULL,'SLOT-1764552648233','?-?-?',1.00,0,0.00,208,NULL,'2025-12-01 01:30:48'),(104,1,3,NULL,'SLOT-1764552648447','⭐-?-⭐',1.00,0,0.00,209,NULL,'2025-12-01 01:30:48'),(105,1,3,NULL,'SLOT-1764552648648','?-?-?',1.00,0,0.00,210,NULL,'2025-12-01 01:30:49'),(106,1,3,NULL,'SLOT-1764552648848','?-?-⭐',1.00,0,0.00,211,NULL,'2025-12-01 01:30:49'),(107,1,3,NULL,'SLOT-1764552649033','⭐-?-⭐',1.00,0,0.00,212,NULL,'2025-12-01 01:30:49'),(108,1,3,NULL,'SLOT-1764552649232','?-?-?',1.00,0,0.00,213,NULL,'2025-12-01 01:30:49'),(109,1,3,NULL,'SLOT-1764552649644','?-?-?',1.00,0,0.00,214,NULL,'2025-12-01 01:30:50'),(110,1,3,NULL,'SLOT-1764552649814','⭐-?-⭐',1.00,0,0.00,215,NULL,'2025-12-01 01:30:50'),(111,1,3,NULL,'SLOT-1764552649974','⭐-?-?',1.00,0,0.00,216,NULL,'2025-12-01 01:30:50'),(112,1,3,NULL,'SLOT-1764552650247','⭐-?-?',1.00,0,0.00,217,NULL,'2025-12-01 01:30:50'),(113,1,3,NULL,'SLOT-1764552650493','?-?-?',1.00,0,0.00,218,NULL,'2025-12-01 01:30:51'),(114,1,3,NULL,'SLOT-1764552650701','?-⭐-?',1.00,0,0.00,219,NULL,'2025-12-01 01:30:51'),(115,1,3,NULL,'SLOT-1764552650925','?-?-?',1.00,0,0.00,220,NULL,'2025-12-01 01:30:51'),(116,1,3,NULL,'SLOT-1764554318794','?-⭐-⭐',1.00,0,0.00,221,NULL,'2025-12-01 01:58:39'),(117,1,3,NULL,'SLOT-1764554321377','?-?-?',1.00,0,0.00,222,NULL,'2025-12-01 01:58:41'),(118,1,3,NULL,'SLOT-1764562310901','⭐-?-⭐',1.00,0,0.00,234,NULL,'2025-12-01 04:11:51'),(119,1,3,NULL,'SLOT-1764566045294','?-?-?',1.00,0,0.00,240,NULL,'2025-12-01 05:14:05'),(120,1,1,NULL,'BINGO-1764566090353','Compra de 1 cartón(es) de bingo',1.00,0,0.00,242,NULL,'2025-12-01 05:14:50'),(121,1,2,7,'SORTEO-1764566108934','12-35-10-23-16-33',2.00,0,0.00,243,NULL,'2025-12-01 05:15:09'),(122,1,1,NULL,'BINGO-1764608503664','Compra de 1 cartón(es) de bingo',1.00,0,0.00,245,NULL,'2025-12-01 17:01:44'),(123,1,2,8,'SORTEO-1764608525502','32-8-11-43-31-26',2.00,0,0.00,246,NULL,'2025-12-01 17:02:06'),(124,1,3,NULL,'SLOT-1764608545174','?-?-?',1.00,0,0.00,247,NULL,'2025-12-01 17:02:25'),(125,1,3,NULL,'SLOT-1764608546548','?-⭐-?',1.00,0,0.00,248,NULL,'2025-12-01 17:02:27');
/*!40000 ALTER TABLE `jugada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento_billetera`
--

DROP TABLE IF EXISTS `movimiento_billetera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento_billetera` (
  `id_movimiento` int NOT NULL AUTO_INCREMENT,
  `id_billetera` int NOT NULL,
  `tipo_movimiento` enum('RECARGA','APUESTA','PREMIO','RETIRO','AJUSTE') NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `signo` tinyint NOT NULL,
  `metodo_pago` enum('TARJETA','YAPE_PLIN','TRANSFERENCIA','BILLETERA_DIGITAL') DEFAULT NULL,
  `codigo_movimiento` varchar(30) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_movimiento` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_movimiento`),
  KEY `fk_mov_billetera` (`id_billetera`),
  CONSTRAINT `fk_mov_billetera` FOREIGN KEY (`id_billetera`) REFERENCES `billetera_usuario` (`id_billetera`)
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento_billetera`
--

LOCK TABLES `movimiento_billetera` WRITE;
/*!40000 ALTER TABLE `movimiento_billetera` DISABLE KEYS */;
INSERT INTO `movimiento_billetera` VALUES (1,1,'RECARGA',5.00,1,NULL,'REC-CA635D5C','Recarga de saldo vía YAPE','2025-11-27 05:58:34'),(2,1,'RECARGA',10.00,1,NULL,'REC-879A2AEC','Recarga de saldo vía YAPE','2025-11-27 06:08:56'),(3,1,'RECARGA',5.00,1,NULL,'REC-868851F0','Recarga de saldo vía YAPE','2025-11-28 06:09:54'),(4,1,'RECARGA',5.00,1,NULL,'REC-CFC0669E','Recarga de saldo vía YAPE','2025-11-28 12:40:12'),(5,1,'RECARGA',5.00,1,NULL,'REC-82CC8D68','Recarga de saldo vía YAPE','2025-11-28 13:04:27'),(6,1,'RECARGA',5.00,1,NULL,'REC-9C8750AB','Recarga de saldo vía YAPE','2025-11-28 13:18:36'),(7,1,'RECARGA',10.00,1,NULL,'REC-C4C1FF1E','Recarga de saldo vía YAPE','2025-11-30 00:46:32'),(8,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466191452','Compra de 1 cartón(es) Bingo','2025-11-30 01:29:51'),(9,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466192467','Compra de 1 cartón(es) Bingo','2025-11-30 01:29:52'),(10,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466192675','Compra de 1 cartón(es) Bingo','2025-11-30 01:29:53'),(11,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466192853','Compra de 1 cartón(es) Bingo','2025-11-30 01:29:53'),(12,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466195074','Compra de 1 cartón(es) Bingo','2025-11-30 01:29:55'),(13,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466195270','Compra de 1 cartón(es) Bingo','2025-11-30 01:29:55'),(14,1,'RECARGA',5.00,1,NULL,'REC-D264D9E6','Recarga de saldo vía YAPE','2025-11-30 01:34:26'),(15,1,'RECARGA',5.00,1,NULL,'REC-154EA0F1','Recarga de saldo vía YAPE','2025-11-30 01:36:43'),(16,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466708963','Compra de 1 cartón(es) Bingo','2025-11-30 01:38:29'),(17,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466709374','Compra de 1 cartón(es) Bingo','2025-11-30 01:38:29'),(18,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466709559','Compra de 1 cartón(es) Bingo','2025-11-30 01:38:30'),(19,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466709711','Compra de 1 cartón(es) Bingo','2025-11-30 01:38:30'),(20,1,'APUESTA',1.00,-1,NULL,'BINGO-1764466709879','Compra de 1 cartón(es) Bingo','2025-11-30 01:38:30'),(21,1,'APUESTA',2.00,-1,NULL,'BINGO-1764467198058','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:38'),(22,1,'APUESTA',2.00,-1,NULL,'BINGO-1764467199466','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:39'),(23,1,'APUESTA',2.00,-1,NULL,'BINGO-1764467199993','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:40'),(24,1,'APUESTA',2.00,-1,NULL,'BINGO-1764467200541','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:41'),(25,1,'APUESTA',2.00,-1,NULL,'BINGO-1764467200713','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:41'),(26,1,'APUESTA',2.00,-1,NULL,'BINGO-1764467200911','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:41'),(27,1,'APUESTA',2.00,-1,NULL,'BINGO-1764467201064','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:41'),(28,1,'APUESTA',5.00,-1,NULL,'BINGO-1764467202659','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:43'),(29,1,'APUESTA',5.00,-1,NULL,'BINGO-1764467203002','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:43'),(30,1,'APUESTA',5.00,-1,NULL,'BINGO-1764467203193','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:43'),(31,1,'APUESTA',5.00,-1,NULL,'BINGO-1764467208538','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:49'),(32,1,'APUESTA',1.00,-1,NULL,'BINGO-1764467215353','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:55'),(33,1,'APUESTA',1.00,-1,NULL,'BINGO-1764467218964','Compra de 1 cartón(es) de bingo','2025-11-30 01:46:59'),(34,1,'APUESTA',5.00,-1,NULL,'BINGO-1764467220651','Compra de 1 cartón(es) de bingo','2025-11-30 01:47:01'),(35,1,'RECARGA',100.00,1,NULL,'REC-3462DEC4','Recarga de saldo vía YAPE','2025-11-30 01:48:18'),(36,1,'APUESTA',1.00,-1,NULL,'BINGO-1764467754798','Compra de 1 cartón(es) de bingo','2025-11-30 01:55:55'),(37,1,'APUESTA',5.00,-1,NULL,'BINGO-1764467869891','Compra de 1 cartón(es) de bingo','2025-11-30 01:57:50'),(38,1,'APUESTA',12.00,-1,NULL,'BINGO-1764467877693','Compra de 12 cartón(es) de bingo','2025-11-30 01:57:58'),(39,1,'APUESTA',1.00,-1,NULL,'BINGO-1764469245763','Compra de 1 cartón(es) de bingo','2025-11-30 02:20:46'),(40,1,'APUESTA',1.00,-1,NULL,'BINGO-1764469357909','Compra de 1 cartón(es) de bingo','2025-11-30 02:22:38'),(41,1,'APUESTA',1.00,-1,NULL,'BINGO-1764469358790','Compra de 1 cartón(es) de bingo','2025-11-30 02:22:39'),(42,1,'APUESTA',1.00,-1,NULL,'BINGO-1764469359005','Compra de 1 cartón(es) de bingo','2025-11-30 02:22:39'),(43,1,'APUESTA',1.00,-1,NULL,'BINGO-1764469359189','Compra de 1 cartón(es) de bingo','2025-11-30 02:22:39'),(44,1,'APUESTA',1.00,-1,NULL,'BINGO-1764469359376','Compra de 1 cartón(es) de bingo','2025-11-30 02:22:39'),(45,1,'APUESTA',1.00,-1,NULL,'BINGO-1764469512018','Compra de 1 cartón(es) de bingo','2025-11-30 02:25:12'),(46,1,'APUESTA',1.00,-1,NULL,'BINGO-1764469513109','Compra de 1 cartón(es) de bingo','2025-11-30 02:25:13'),(47,1,'APUESTA',5.00,-1,NULL,'BINGO-1764469622276','Compra de 1 cartón(es) de bingo','2025-11-30 02:27:02'),(48,1,'APUESTA',5.00,-1,NULL,'BINGO-1764469632529','Compra de 1 cartón(es) de bingo','2025-11-30 02:27:13'),(49,1,'APUESTA',5.00,-1,NULL,'BINGO-1764469633240','Compra de 1 cartón(es) de bingo','2025-11-30 02:27:13'),(50,1,'APUESTA',5.00,-1,NULL,'BINGO-1764469633416','Compra de 1 cartón(es) de bingo','2025-11-30 02:27:13'),(51,1,'APUESTA',5.00,-1,NULL,'BINGO-1764469633624','Compra de 1 cartón(es) de bingo','2025-11-30 02:27:14'),(52,1,'APUESTA',4.00,-1,NULL,'BINGO-1764470310688','Compra de 2 cartón(es) de bingo','2025-11-30 02:38:31'),(53,1,'APUESTA',1.00,-1,NULL,'BINGO-1764470449807','Compra de 1 cartón(es) de bingo','2025-11-30 02:40:50'),(54,1,'APUESTA',1.00,-1,NULL,'BINGO-1764470469123','Compra de 1 cartón(es) de bingo','2025-11-30 02:41:09'),(55,1,'APUESTA',1.00,-1,NULL,'BINGO-1764470538142','Compra de 1 cartón(es) de bingo','2025-11-30 02:42:18'),(56,1,'APUESTA',1.00,-1,NULL,'BINGO-1764470655066','Compra de 1 cartón(es) de bingo','2025-11-30 02:44:15'),(57,1,'APUESTA',1.00,-1,NULL,'BINGO-1764470660302','Compra de 1 cartón(es) de bingo','2025-11-30 02:44:20'),(58,1,'APUESTA',1.00,-1,NULL,'BINGO-1764470660513','Compra de 1 cartón(es) de bingo','2025-11-30 02:44:21'),(59,1,'APUESTA',1.00,-1,NULL,'BINGO-1764470660690','Compra de 1 cartón(es) de bingo','2025-11-30 02:44:21'),(60,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471517308','Compra de 1 cartón(es) de bingo','2025-11-30 02:58:37'),(61,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471726983','Compra de 1 cartón(es) de bingo','2025-11-30 03:02:07'),(62,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471728260','Compra de 1 cartón(es) de bingo','2025-11-30 03:02:08'),(63,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471729115','Compra de 1 cartón(es) de bingo','2025-11-30 03:02:09'),(64,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471729276','Compra de 1 cartón(es) de bingo','2025-11-30 03:02:09'),(65,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471729433','Compra de 1 cartón(es) de bingo','2025-11-30 03:02:09'),(66,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471841512','Compra de 1 cartón(es) de bingo','2025-11-30 03:04:02'),(67,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471843057','Compra de 1 cartón(es) de bingo','2025-11-30 03:04:03'),(68,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471843299','Compra de 1 cartón(es) de bingo','2025-11-30 03:04:03'),(69,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471843487','Compra de 1 cartón(es) de bingo','2025-11-30 03:04:03'),(70,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471936632','Compra de 1 cartón(es) de bingo','2025-11-30 03:05:37'),(71,1,'APUESTA',1.00,-1,NULL,'BINGO-1764471937454','Compra de 1 cartón(es) de bingo','2025-11-30 03:05:37'),(72,1,'RECARGA',10.00,1,NULL,'REC-7F61094D','Recarga de saldo vía YAPE','2025-11-30 03:23:36'),(73,1,'APUESTA',15.00,-1,NULL,'BINGO-1764473043213','Compra de 3 cartón(es) de bingo','2025-11-30 03:24:03'),(74,1,'APUESTA',1.00,-1,NULL,'BINGO-1764475816853','Compra de 1 cartón(es) de bingo','2025-11-30 04:10:17'),(75,1,'APUESTA',1.00,-1,NULL,'BINGO-1764476592127','Compra de 1 cartón(es) de bingo','2025-11-30 04:23:12'),(76,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479430153','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:30'),(77,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479432265','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:32'),(78,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479433388','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:33'),(79,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479433608','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:34'),(80,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479433779','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:34'),(81,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479433973','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:34'),(82,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479434545','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:35'),(83,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479434735','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:35'),(84,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479434932','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:35'),(85,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479437636','Compra de 1 cartón(es) de bingo','2025-11-30 05:10:38'),(86,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479607027','Compra de 1 cartón(es) de bingo','2025-11-30 05:13:27'),(87,1,'APUESTA',1.00,-1,NULL,'BINGO-1764479785399','Compra de 1 cartón(es) de bingo','2025-11-30 05:16:25'),(88,1,'APUESTA',1.00,-1,NULL,'BINGO-1764480013286','Compra de 1 cartón(es) de bingo','2025-11-30 05:20:13'),(89,1,'APUESTA',1.00,-1,NULL,'BINGO-1764480105393','Compra de 1 cartón(es) de bingo','2025-11-30 05:21:45'),(90,1,'APUESTA',1.00,-1,NULL,'BINGO-1764480610450','Compra de 1 cartón(es) de bingo','2025-11-30 05:30:10'),(91,1,'APUESTA',1.00,-1,NULL,'BINGO-1764485009282','Compra de 1 cartón(es) de bingo','2025-11-30 06:43:29'),(92,1,'APUESTA',1.00,-1,NULL,'BINGO-1764485264984','Compra de 1 cartón(es) de bingo','2025-11-30 06:47:45'),(93,1,'APUESTA',5.00,-1,NULL,'TEST-MOV-001','Movimiento de prueba','2025-11-30 01:50:48'),(94,1,'APUESTA',1.00,-1,NULL,'BINGO-1764486164918','Compra de 1 cartón(es) de bingo','2025-11-30 07:02:45'),(95,1,'APUESTA',1.00,-1,NULL,'BINGO-1764486490282','Compra de 1 cartón(es) de bingo','2025-11-30 07:08:10'),(96,1,'APUESTA',1.00,-1,NULL,'BINGO-1764486682266','Compra de 1 cartón(es) de bingo','2025-11-30 07:11:22'),(97,1,'APUESTA',1.00,-1,NULL,'BINGO-1764486820090','Compra de 1 cartón(es) de bingo','2025-11-30 07:13:40'),(98,1,'RECARGA',100.00,1,NULL,'REC-0587D78E','Recarga de saldo vía YAPE','2025-11-30 21:05:34'),(99,1,'APUESTA',5.00,-1,NULL,'BINGO-1764536744959','Compra de 1 cartón(es) de bingo','2025-11-30 21:05:45'),(100,1,'APUESTA',75.00,-1,NULL,'BINGO-1764536760184','Compra de 15 cartón(es) de bingo','2025-11-30 21:06:00'),(101,1,'APUESTA',3.00,-1,NULL,'BINGO-1764536876034','Compra de 3 cartón(es) de bingo','2025-11-30 21:07:56'),(102,1,'APUESTA',3.00,-1,NULL,'BINGO-1764536902847','Compra de 3 cartón(es) de bingo','2025-11-30 21:08:23'),(103,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764538172784','Participación en sorteo','2025-11-30 21:29:33'),(104,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764538512343','Participación en sorteo','2025-11-30 21:35:12'),(105,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764538846092','Participación en sorteo','2025-11-30 21:40:46'),(106,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764540199925','Participación en sorteo','2025-11-30 22:03:20'),(107,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764540405655','Participación en sorteo','2025-11-30 22:06:46'),(108,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764542268606','Participación en sorteo','2025-11-30 22:37:49'),(109,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764542316611','Participación en sorteo','2025-11-30 22:38:37'),(110,1,'RECARGA',100.00,1,NULL,'REC-9407E756','Recarga de saldo vía YAPE','2025-11-30 22:39:40'),(111,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764542393021','Participación en sorteo','2025-11-30 22:39:53'),(112,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764543599774','Participación en sorteo','2025-11-30 23:00:00'),(113,1,'APUESTA',1.00,-1,NULL,'BINGO-1764543860792','Compra de 1 cartón(es) de bingo','2025-11-30 23:04:21'),(114,1,'RECARGA',10.00,1,NULL,'REC-FC00C68A','Recarga de saldo vía YAPE','2025-11-30 23:06:02'),(115,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764547662173','Participación en sorteo','2025-12-01 00:07:42'),(116,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550671365','Jugada de tragamonedas','2025-12-01 00:57:51'),(117,1,'PREMIO',10.00,1,NULL,'SLOT-1764550671365-P','Premio tragamonedas','2025-12-01 00:57:51'),(118,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550673167','Jugada de tragamonedas','2025-12-01 00:57:53'),(119,1,'PREMIO',10.00,1,NULL,'SLOT-1764550673167-P','Premio tragamonedas','2025-12-01 00:57:53'),(120,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550674133','Jugada de tragamonedas','2025-12-01 00:57:54'),(121,1,'PREMIO',10.00,1,NULL,'SLOT-1764550674133-P','Premio tragamonedas','2025-12-01 00:57:54'),(122,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550674613','Jugada de tragamonedas','2025-12-01 00:57:55'),(123,1,'PREMIO',10.00,1,NULL,'SLOT-1764550674613-P','Premio tragamonedas','2025-12-01 00:57:55'),(124,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550674818','Jugada de tragamonedas','2025-12-01 00:57:55'),(125,1,'PREMIO',10.00,1,NULL,'SLOT-1764550674818-P','Premio tragamonedas','2025-12-01 00:57:55'),(126,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550674994','Jugada de tragamonedas','2025-12-01 00:57:55'),(127,1,'PREMIO',10.00,1,NULL,'SLOT-1764550674994-P','Premio tragamonedas','2025-12-01 00:57:55'),(128,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550675178','Jugada de tragamonedas','2025-12-01 00:57:55'),(129,1,'PREMIO',10.00,1,NULL,'SLOT-1764550675178-P','Premio tragamonedas','2025-12-01 00:57:55'),(130,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550675370','Jugada de tragamonedas','2025-12-01 00:57:55'),(131,1,'PREMIO',10.00,1,NULL,'SLOT-1764550675370-P','Premio tragamonedas','2025-12-01 00:57:55'),(132,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550689584','Jugada de tragamonedas','2025-12-01 00:58:10'),(133,1,'PREMIO',10.00,1,NULL,'SLOT-1764550689584-P','Premio tragamonedas','2025-12-01 00:58:10'),(134,1,'APUESTA',1.00,-1,NULL,'SLOT-1764550691015','Jugada de tragamonedas','2025-12-01 00:58:11'),(135,1,'PREMIO',10.00,1,NULL,'SLOT-1764550691015-P','Premio tragamonedas','2025-12-01 00:58:11'),(136,1,'APUESTA',1.00,-1,NULL,'SLOT-1764551293083','Jugada de tragamonedas','2025-12-01 01:08:13'),(137,1,'PREMIO',10.00,1,NULL,'SLOT-1764551293083-P','Premio tragamonedas','2025-12-01 01:08:13'),(138,1,'APUESTA',1.00,-1,NULL,'SLOT-1764551294649','Jugada de tragamonedas','2025-12-01 01:08:15'),(139,1,'PREMIO',10.00,1,NULL,'SLOT-1764551294649-P','Premio tragamonedas','2025-12-01 01:08:15'),(140,1,'APUESTA',1.00,-1,NULL,'SLOT-1764551295417','Jugada de tragamonedas','2025-12-01 01:08:15'),(141,1,'PREMIO',10.00,1,NULL,'SLOT-1764551295417-P','Premio tragamonedas','2025-12-01 01:08:15'),(142,1,'APUESTA',1.00,-1,NULL,'SLOT-1764551295804','Jugada de tragamonedas','2025-12-01 01:08:16'),(143,1,'PREMIO',10.00,1,NULL,'SLOT-1764551295804-P','Premio tragamonedas','2025-12-01 01:08:16'),(144,1,'APUESTA',1.00,-1,NULL,'SLOT-1764551296067','Jugada de tragamonedas','2025-12-01 01:08:16'),(145,1,'PREMIO',10.00,1,NULL,'SLOT-1764551296067-P','Premio tragamonedas','2025-12-01 01:08:16'),(146,1,'APUESTA',1.00,-1,NULL,'SLOT-1764551296261','Jugada de tragamonedas','2025-12-01 01:08:16'),(147,1,'PREMIO',10.00,1,NULL,'SLOT-1764551296261-P','Premio tragamonedas','2025-12-01 01:08:16'),(148,1,'APUESTA',1.00,-1,NULL,'SLOT-1764551296446','Jugada de tragamonedas','2025-12-01 01:08:16'),(149,1,'PREMIO',10.00,1,NULL,'SLOT-1764551296446-P','Premio tragamonedas','2025-12-01 01:08:16'),(150,1,'APUESTA',1.00,-1,NULL,'SLOT-1764551296627','Jugada de tragamonedas','2025-12-01 01:08:17'),(151,1,'PREMIO',10.00,1,NULL,'SLOT-1764551296627-P','Premio tragamonedas','2025-12-01 01:08:17'),(152,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552124044','Jugada de tragamonedas','2025-12-01 01:22:04'),(153,1,'PREMIO',10.00,1,NULL,'SLOT-1764552124044-P','Premio tragamonedas','2025-12-01 01:22:04'),(154,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552129641','Jugada de tragamonedas','2025-12-01 01:22:10'),(155,1,'PREMIO',10.00,1,NULL,'SLOT-1764552129641-P','Premio tragamonedas','2025-12-01 01:22:10'),(156,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552628589','Jugada de tragamonedas','2025-12-01 01:30:29'),(157,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552630241','Jugada de tragamonedas','2025-12-01 01:30:30'),(158,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552631188','Jugada de tragamonedas','2025-12-01 01:30:31'),(159,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552631622','Jugada de tragamonedas','2025-12-01 01:30:32'),(160,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552632172','Jugada de tragamonedas','2025-12-01 01:30:32'),(161,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552632641','Jugada de tragamonedas','2025-12-01 01:30:33'),(162,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552633007','Jugada de tragamonedas','2025-12-01 01:30:33'),(163,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552633707','Jugada de tragamonedas','2025-12-01 01:30:34'),(164,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552634172','Jugada de tragamonedas','2025-12-01 01:30:34'),(165,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552634652','Jugada de tragamonedas','2025-12-01 01:30:35'),(166,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552635388','Jugada de tragamonedas','2025-12-01 01:30:35'),(167,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552636101','Jugada de tragamonedas','2025-12-01 01:30:36'),(168,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552636714','Jugada de tragamonedas','2025-12-01 01:30:37'),(169,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552637241','Jugada de tragamonedas','2025-12-01 01:30:37'),(170,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552637688','Jugada de tragamonedas','2025-12-01 01:30:38'),(171,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552638305','Jugada de tragamonedas','2025-12-01 01:30:38'),(172,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552638746','Jugada de tragamonedas','2025-12-01 01:30:39'),(173,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552638922','Jugada de tragamonedas','2025-12-01 01:30:39'),(174,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552639145','Jugada de tragamonedas','2025-12-01 01:30:39'),(175,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552639487','Jugada de tragamonedas','2025-12-01 01:30:39'),(176,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552639914','Jugada de tragamonedas','2025-12-01 01:30:40'),(177,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552640280','Jugada de tragamonedas','2025-12-01 01:30:40'),(178,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552640591','Jugada de tragamonedas','2025-12-01 01:30:41'),(179,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552640785','Jugada de tragamonedas','2025-12-01 01:30:41'),(180,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552640966','Jugada de tragamonedas','2025-12-01 01:30:41'),(181,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552641154','Jugada de tragamonedas','2025-12-01 01:30:41'),(182,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552641361','Jugada de tragamonedas','2025-12-01 01:30:41'),(183,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552641529','Jugada de tragamonedas','2025-12-01 01:30:42'),(184,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552641744','Jugada de tragamonedas','2025-12-01 01:30:42'),(185,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552642128','Jugada de tragamonedas','2025-12-01 01:30:42'),(186,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552643663','Jugada de tragamonedas','2025-12-01 01:30:44'),(187,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552643897','Jugada de tragamonedas','2025-12-01 01:30:44'),(188,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552644097','Jugada de tragamonedas','2025-12-01 01:30:44'),(189,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552644287','Jugada de tragamonedas','2025-12-01 01:30:44'),(190,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552644485','Jugada de tragamonedas','2025-12-01 01:30:44'),(191,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552644666','Jugada de tragamonedas','2025-12-01 01:30:45'),(192,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552644867','Jugada de tragamonedas','2025-12-01 01:30:45'),(193,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552645074','Jugada de tragamonedas','2025-12-01 01:30:45'),(194,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552645291','Jugada de tragamonedas','2025-12-01 01:30:45'),(195,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552645512','Jugada de tragamonedas','2025-12-01 01:30:46'),(196,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552645715','Jugada de tragamonedas','2025-12-01 01:30:46'),(197,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552645929','Jugada de tragamonedas','2025-12-01 01:30:46'),(198,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552646114','Jugada de tragamonedas','2025-12-01 01:30:46'),(199,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552646321','Jugada de tragamonedas','2025-12-01 01:30:46'),(200,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552646527','Jugada de tragamonedas','2025-12-01 01:30:47'),(201,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552646759','Jugada de tragamonedas','2025-12-01 01:30:47'),(202,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552646943','Jugada de tragamonedas','2025-12-01 01:30:47'),(203,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552647192','Jugada de tragamonedas','2025-12-01 01:30:47'),(204,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552647364','Jugada de tragamonedas','2025-12-01 01:30:47'),(205,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552647591','Jugada de tragamonedas','2025-12-01 01:30:48'),(206,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552647794','Jugada de tragamonedas','2025-12-01 01:30:48'),(207,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552648010','Jugada de tragamonedas','2025-12-01 01:30:48'),(208,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552648233','Jugada de tragamonedas','2025-12-01 01:30:48'),(209,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552648447','Jugada de tragamonedas','2025-12-01 01:30:48'),(210,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552648648','Jugada de tragamonedas','2025-12-01 01:30:49'),(211,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552648848','Jugada de tragamonedas','2025-12-01 01:30:49'),(212,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552649033','Jugada de tragamonedas','2025-12-01 01:30:49'),(213,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552649232','Jugada de tragamonedas','2025-12-01 01:30:49'),(214,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552649644','Jugada de tragamonedas','2025-12-01 01:30:50'),(215,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552649814','Jugada de tragamonedas','2025-12-01 01:30:50'),(216,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552649974','Jugada de tragamonedas','2025-12-01 01:30:50'),(217,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552650247','Jugada de tragamonedas','2025-12-01 01:30:50'),(218,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552650493','Jugada de tragamonedas','2025-12-01 01:30:51'),(219,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552650701','Jugada de tragamonedas','2025-12-01 01:30:51'),(220,1,'APUESTA',1.00,-1,NULL,'SLOT-1764552650925','Jugada de tragamonedas','2025-12-01 01:30:51'),(221,1,'APUESTA',1.00,-1,NULL,'SLOT-1764554318794','Jugada de tragamonedas','2025-12-01 01:58:39'),(222,1,'APUESTA',1.00,-1,NULL,'SLOT-1764554321377','Jugada de tragamonedas','2025-12-01 01:58:41'),(223,1,'RETIRO',10.00,-1,'BILLETERA_DIGITAL','RET-1764555014765','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 02:10:15'),(224,1,'RETIRO',10.00,-1,'BILLETERA_DIGITAL','RET-1764556137965','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 02:28:58'),(225,1,'RECARGA',5.00,1,NULL,'REC-3A7495FE','Recarga de saldo vía YAPE','2025-12-01 02:29:12'),(226,1,'RETIRO',10.00,-1,'BILLETERA_DIGITAL','RET-1764556192089','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 02:29:52'),(227,1,'RETIRO',10.00,-1,'BILLETERA_DIGITAL','RET-1764558399889','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 03:06:40'),(228,1,'RETIRO',10.00,-1,'BILLETERA_DIGITAL','RET-1764558912397','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 03:15:12'),(229,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764559313256','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 03:21:53'),(230,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764559688782','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 03:28:09'),(231,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764560189235','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 03:36:29'),(232,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764560308342','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 03:38:28'),(233,1,'RECARGA',5.00,1,NULL,'REC-E8FD8270','Recarga de saldo vía YAPE','2025-12-01 04:05:06'),(234,1,'APUESTA',1.00,-1,NULL,'SLOT-1764562310901','Jugada de tragamonedas','2025-12-01 04:11:51'),(235,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764563302187','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 04:28:22'),(236,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764563321482','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 04:28:41'),(237,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764563631303','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 04:33:51'),(238,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764563652786','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 04:34:13'),(239,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764563983524','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 04:39:44'),(240,1,'APUESTA',1.00,-1,NULL,'SLOT-1764566045294','Jugada de tragamonedas','2025-12-01 05:14:05'),(241,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764566071298','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 05:14:31'),(242,1,'APUESTA',1.00,-1,NULL,'BINGO-1764566090353','Compra de 1 cartón(es) de bingo','2025-12-01 05:14:50'),(243,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764566108934','Participación en sorteo','2025-12-01 05:15:09'),(244,1,'RETIRO',5.00,-1,'BILLETERA_DIGITAL','RET-1764608488005','Retiro de premios a BILLETERA_DIGITAL (970058002)','2025-12-01 17:01:28'),(245,1,'APUESTA',1.00,-1,NULL,'BINGO-1764608503664','Compra de 1 cartón(es) de bingo','2025-12-01 17:01:44'),(246,1,'APUESTA',2.00,-1,NULL,'SORTEO-1764608525502','Participación en sorteo','2025-12-01 17:02:06'),(247,1,'APUESTA',1.00,-1,NULL,'SLOT-1764608545174','Jugada de tragamonedas','2025-12-01 17:02:25'),(248,1,'APUESTA',1.00,-1,NULL,'SLOT-1764608546548','Jugada de tragamonedas','2025-12-01 17:02:27');
/*!40000 ALTER TABLE `movimiento_billetera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificacion`
--

DROP TABLE IF EXISTS `notificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificacion` (
  `id_notificacion` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `tipo_juego` enum('BINGO','SORTEO','SLOTS') DEFAULT NULL,
  `codigo_jugada` varchar(30) DEFAULT NULL,
  `titulo` varchar(100) NOT NULL,
  `mensaje` text NOT NULL,
  `fecha_notificacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `leido` tinyint(1) NOT NULL DEFAULT '0',
  `eliminado` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_notificacion`),
  KEY `fk_notif_usuario` (`id_usuario`),
  CONSTRAINT `fk_notif_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificacion`
--

LOCK TABLES `notificacion` WRITE;
/*!40000 ALTER TABLE `notificacion` DISABLE KEYS */;
INSERT INTO `notificacion` VALUES (1,1,NULL,'RET-1764563631303','Retiro registrado','Se realizó un retiro de S/ 5 hacia BILLETERA_DIGITAL (970058002).','2025-12-01 04:33:51',1,0),(2,1,NULL,'RET-1764563652786','Retiro registrado','Se realizó un retiro de S/ 5 hacia BILLETERA_DIGITAL (970058002).','2025-12-01 04:34:13',0,0),(3,1,NULL,'RET-1764563983524','Retiro registrado','Se realizó un retiro de S/ 5 hacia BILLETERA_DIGITAL (970058002).','2025-12-01 04:39:44',0,0),(4,1,NULL,'RET-1764566071298','Retiro registrado','Se realizó un retiro de S/ 5 hacia BILLETERA_DIGITAL (970058002).','2025-12-01 05:14:31',0,0),(5,1,NULL,'RET-1764608488005','Retiro registrado','Se realizó un retiro de S/ 5 hacia BILLETERA_DIGITAL (970058002).','2025-12-01 17:01:28',0,0);
/*!40000 ALTER TABLE `notificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `premio_usuario`
--

DROP TABLE IF EXISTS `premio_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `premio_usuario` (
  `id_premio` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_jugada` int NOT NULL,
  `tipo_juego` enum('BINGO','SORTEO','SLOTS') NOT NULL,
  `codigo_jugada` varchar(30) NOT NULL,
  `detalle` varchar(255) NOT NULL,
  `monto_ganado` decimal(10,2) NOT NULL,
  `fecha_registro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_premio`),
  KEY `fk_premio_usuario` (`id_usuario`),
  KEY `fk_premio_jugada` (`id_jugada`),
  CONSTRAINT `fk_premio_jugada` FOREIGN KEY (`id_jugada`) REFERENCES `jugada` (`id_jugada`),
  CONSTRAINT `fk_premio_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `premio_usuario`
--

LOCK TABLES `premio_usuario` WRITE;
/*!40000 ALTER TABLE `premio_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `premio_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `retiro_premios`
--

DROP TABLE IF EXISTS `retiro_premios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `retiro_premios` (
  `id_retiro` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `codigo_retiro` varchar(30) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `metodo_retiro` enum('CUENTA_BANCARIA','BILLETERA_DIGITAL') NOT NULL,
  `destino` varchar(100) NOT NULL,
  `estado_retiro` enum('SOLICITADO','EN_PROCESO','PAGADO','RECHAZADO','CANCELADO') NOT NULL DEFAULT 'SOLICITADO',
  `fecha_solicitud` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_mov_retiro` int DEFAULT NULL,
  PRIMARY KEY (`id_retiro`),
  KEY `fk_retiro_usuario` (`id_usuario`),
  KEY `fk_retiro_mov` (`id_mov_retiro`),
  CONSTRAINT `fk_retiro_mov` FOREIGN KEY (`id_mov_retiro`) REFERENCES `movimiento_billetera` (`id_movimiento`),
  CONSTRAINT `fk_retiro_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `retiro_premios`
--

LOCK TABLES `retiro_premios` WRITE;
/*!40000 ALTER TABLE `retiro_premios` DISABLE KEYS */;
/*!40000 ALTER TABLE `retiro_premios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sorteo`
--

DROP TABLE IF EXISTS `sorteo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sorteo` (
  `id_sorteo` int NOT NULL AUTO_INCREMENT,
  `id_juego` int NOT NULL,
  `fecha_programada` datetime NOT NULL,
  `numeros_ganadores` varchar(255) DEFAULT NULL,
  `estado_sorteo` enum('PROGRAMADO','EJECUTADO','CANCELADO') NOT NULL DEFAULT 'PROGRAMADO',
  PRIMARY KEY (`id_sorteo`),
  KEY `fk_sorteo_juego` (`id_juego`),
  CONSTRAINT `fk_sorteo_juego` FOREIGN KEY (`id_juego`) REFERENCES `juego` (`id_juego`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sorteo`
--

LOCK TABLES `sorteo` WRITE;
/*!40000 ALTER TABLE `sorteo` DISABLE KEYS */;
INSERT INTO `sorteo` VALUES (1,2,'2025-11-30 22:37:49','36-18-25-33-19-43','EJECUTADO'),(2,2,'2025-11-30 22:38:37','25-20-16-9-5-12','EJECUTADO'),(3,2,'2025-11-30 22:39:26','18-26-7-23-31-14','EJECUTADO'),(4,2,'2025-11-30 22:39:53','13-19-21-25-26-27','EJECUTADO'),(5,2,'2025-11-30 23:00:00','16-4-12-37-10-27','EJECUTADO'),(6,2,'2025-12-01 00:07:42','2-43-21-18-24-32','EJECUTADO'),(7,2,'2025-12-01 05:15:09','45-21-11-6-34-16','EJECUTADO'),(8,2,'2025-12-01 17:02:05','9-4-33-21-18-7','EJECUTADO');
/*!40000 ALTER TABLE `sorteo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `tipo_documento` enum('DNI','CE','PASAPORTE') NOT NULL,
  `numero_documento` varchar(20) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `genero` enum('MASCULINO','FEMENINO','NO_ESPECIFICA') NOT NULL,
  `correo` varchar(120) NOT NULL,
  `celular` varchar(15) NOT NULL,
  `departamento` varchar(50) NOT NULL,
  `provincia` varchar(50) NOT NULL,
  `contrasena_hash` varchar(255) NOT NULL,
  `fecha_registro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `numero_documento` (`numero_documento`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Luis','Pérez','DNI','12345678','2000-01-01','MASCULINO','juan.perez@correo.com','999999999','AREQUIPA','AREQUIPA','123456','2025-11-24 01:05:06'),(2,'Alessandra','Quihue','DNI','73376004','2005-08-08','FEMENINO','angeles.qr08@gmail.com','970058002','Arequipa','Arequipa','angeles08','2025-11-24 06:19:31'),(3,'Alessandro','Caceres','DNI','73376005','2001-08-08','MASCULINO','angeles@gmail.com','970058003','Arequipa','Arequipa','ale123','2025-11-24 16:34:37'),(4,'Angeles','Chavez','DNI','73376002','2001-08-08','FEMENINO','ale123@gmail.com','970058055','Arequipa','Arequipa','123456','2025-11-24 16:49:03'),(5,'Angeles','Chavez','DNI','73376001','2001-08-08','FEMENINO','ale1234@gmail.com','970058055','Arequipa','Arequipa','123456','2025-11-24 16:58:53'),(6,'Angeles','Chavez','DNI','73376000','2001-08-08','FEMENINO','ale12345@gmail.com','970058055','Arequipa','Arequipa','111111','2025-11-24 21:24:58'),(7,'Angeles','Chavez','DNI','73376008','2001-08-08','FEMENINO','ale123456@gmail.com','970058055','Arequipa','Arequipa','123456','2025-11-28 12:44:14');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-01 15:00:18
