CREATE DATABASE  IF NOT EXISTS `JavaTech_Reservation` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `JavaTech_Reservation`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: JavaTech_Reservation
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.22.04.1

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
-- Table structure for table `groupmember`
--

DROP TABLE IF EXISTS `groupmember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupmember` (
  `group_member_id` int NOT NULL AUTO_INCREMENT,
  `group_member_user_id` int NOT NULL,
  `group_member_group_id` int NOT NULL,
  PRIMARY KEY (`group_member_id`),
  UNIQUE KEY `unique_group_member` (`group_member_user_id`,`group_member_group_id`),
  CONSTRAINT `groupmember_ibfk_1` FOREIGN KEY (`group_member_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupmember`
--

LOCK TABLES `groupmember` WRITE;
/*!40000 ALTER TABLE `groupmember` DISABLE KEYS */;
INSERT INTO `groupmember` VALUES (11,2,1),(10,2,3),(5,2,5),(9,5,3);
/*!40000 ALTER TABLE `groupmember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timegroup`
--

DROP TABLE IF EXISTS `timegroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timegroup` (
  `timegroup_id` int NOT NULL AUTO_INCREMENT,
  `timegroup_admin_id` int NOT NULL,
  `timegroup_name` varchar(50) NOT NULL,
  `timegroup_password` varchar(50) NOT NULL,
  PRIMARY KEY (`timegroup_id`),
  UNIQUE KEY `timegroup_name_UNIQUE` (`timegroup_name`),
  KEY `timegroup_admin_id` (`timegroup_admin_id`),
  CONSTRAINT `timegroup_ibfk_1` FOREIGN KEY (`timegroup_admin_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timegroup`
--

LOCK TABLES `timegroup` WRITE;
/*!40000 ALTER TABLE `timegroup` DISABLE KEYS */;
INSERT INTO `timegroup` VALUES (1,1,'OgTimeGroup','Potato'),(3,1,'newGroup','Batata'),(5,1,'newGroup1','Batata'),(6,2,'Group3','MyG');
/*!40000 ALTER TABLE `timegroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timeslot`
--

DROP TABLE IF EXISTS `timeslot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timeslot` (
  `timeslot_id` int NOT NULL AUTO_INCREMENT,
  `timeslot_user_id` int DEFAULT NULL,
  `timeslot_group_id` int NOT NULL,
  `timeslot_start_time` timestamp NOT NULL,
  `timeslot_end_time` timestamp NOT NULL,
  `timeslot_isReserved` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`timeslot_id`),
  KEY `timeslot_user_id` (`timeslot_user_id`),
  KEY `timeslot_group_id` (`timeslot_group_id`),
  CONSTRAINT `timeslot_ibfk_3` FOREIGN KEY (`timeslot_group_id`) REFERENCES `timegroup` (`timegroup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timeslot`
--

LOCK TABLES `timeslot` WRITE;
/*!40000 ALTER TABLE `timeslot` DISABLE KEYS */;
INSERT INTO `timeslot` VALUES (7,NULL,1,'0000-00-00 00:00:00','0000-00-00 00:00:00',0),(8,0,1,'2024-04-16 00:30:00','2024-04-16 01:00:00',0),(11,2,1,'2024-04-16 03:30:00','2024-04-16 04:00:00',1),(12,NULL,1,'2024-03-24 23:00:00','2024-03-24 23:00:00',0);
/*!40000 ALTER TABLE `timeslot` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `check_timeslot_overlap` BEFORE INSERT ON `timeslot` FOR EACH ROW BEGIN
    DECLARE overlap_count INT;
    SET overlap_count = (
        SELECT COUNT(*)
        FROM timeslot
        WHERE timeslot_group_id = NEW.timeslot_group_id
        AND (
            NEW.timeslot_start_time BETWEEN timeslot_start_time AND timeslot_end_time
            OR NEW.timeslot_end_time BETWEEN timeslot_start_time AND timeslot_end_time
            OR timeslot_start_time BETWEEN NEW.timeslot_start_time AND NEW.timeslot_end_time
            OR timeslot_end_time BETWEEN NEW.timeslot_start_time AND NEW.timeslot_end_time
        )
    );
    IF overlap_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Timeslots cannot overlap within the same time group';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_username` varchar(50) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `user_first_name` varchar(50) NOT NULL,
  `user_last_name` varchar(50) NOT NULL,
  `user_phone_number` varchar(15) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_username` (`user_username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'AdamElh','Potato','Adam','ElHassan','03601360'),(2,'AdamElh21','Potatozzz','Adam','ElHassan','03601360'),(4,'Adoomeh_h','Helloz','Adam','ELHassan','21'),(5,'ada','2121','21','21','222');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'JavaTech_Reservation'
--

--
-- Dumping routines for database 'JavaTech_Reservation'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-25 19:58:17
