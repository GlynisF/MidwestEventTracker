-- MySQL dump 10.13  Distrib 8.1.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: test_et
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artist` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `first_name` varchar(100) DEFAULT NULL,
                          `last_name` varchar(100) DEFAULT NULL,
                          `moniker` varchar(200) NOT NULL,
                          `fee` double DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `artist_pk2` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
                         `event_id` int NOT NULL AUTO_INCREMENT,
                         `event_name` varchar(255) NOT NULL,
                         `notebook_id` int NOT NULL,
                         PRIMARY KEY (`event_id`),
                         UNIQUE KEY `event_pk` (`event_id`),
                         KEY `notebook_id` (`notebook_id`),
                         CONSTRAINT `notebook_id` FOREIGN KEY (`notebook_id`) REFERENCES `notebook` (`notebook_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'RIN',1),(2,'S. Bar',1),(3,'J jams',4),(4,'Patio takeover',5);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_artist`
--

DROP TABLE IF EXISTS `event_artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_artist` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `event_id` int NOT NULL,
                                `artist_id` int NOT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `event_artist_pk` (`id`),
                                KEY `artist_id` (`artist_id`),
                                KEY `event_id` (`event_id`),
                                CONSTRAINT `artist_id` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`id`),
                                CONSTRAINT `event_id` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_artist`
--

LOCK TABLES `event_artist` WRITE;
/*!40000 ALTER TABLE `event_artist` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `location_name` varchar(100) NOT NULL,
                            `phone_number` varchar(20) DEFAULT NULL,
                            `address` varchar(100) NOT NULL,
                            `apartment` varchar(100) DEFAULT NULL,
                            `city` varchar(100) NOT NULL,
                            `state` varchar(100) NOT NULL,
                            `zip` varchar(10) DEFAULT NULL,
                            `wheelchair_accessible_entrance` tinyint(1) DEFAULT NULL,
                            `website` varchar(255) DEFAULT NULL,
                            `event_fk` int NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `location_pk2` (`id`),
                            KEY `event_fk` (`event_fk`),
                            CONSTRAINT `event_fk` FOREIGN KEY (`event_fk`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES
                           (1, 'High Noon Saloon', '608-123-3333', '701 East Washington Ave', NULL, 'Madison', 'WI', '53703', 1, 'https://www.highnoon.com', 1),
                           (2, 'Quarters', '608-222-3333', '301 E Center Ave', NULL, 'Milwaukee', 'WI', '53702', 1, 'https://www.quarters.com', 1),
                           (3, 'Sylvee', '608-111-1555', '102 East Washington Ave', NULL, 'Madison', 'WI', '53704', 0, 'https://www.sylvee.com', 4),
                           (4, 'Smart Bar', '312-111-4444', '305 Clark St', NULL, 'Chicago', 'IL', '61802', 1, 'https://www.smartbar.com', 3),
                           (5, 'The Spot', '414-414-4144', '111 South St', NULL, 'Milwaukee', 'WI', '53505', 0, 'https://www.thespot.com', 2),
                           (6, 'Music Lounge', '222-559-6695', '123 North St', NULL, 'Lincoln', 'NE', '43507', 1, 'https://www.musiclounge.com', 2),
                           (7, 'Disc Parlour', '312-213-3123', '523 Parlour Ave', NULL, 'Chicago', 'IL', '68909', 1, 'https://www.discparlour.com', 4);

/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notebook`
--

DROP TABLE IF EXISTS `notebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notebook` (
                            `notebook_id` int NOT NULL AUTO_INCREMENT,
                            `title` varchar(100) DEFAULT NULL,
                            `user_user_id` int NOT NULL,
                            PRIMARY KEY (`notebook_id`),
                            UNIQUE KEY `notebook_pk2` (`notebook_id`),
                            KEY `user_user_id` (`user_user_id`),
                            CONSTRAINT `user_user_id` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=814 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notebook`
--

LOCK TABLES `notebook` WRITE;
/*!40000 ALTER TABLE `notebook` DISABLE KEYS */;
INSERT INTO `notebook` VALUES (1,'RIN Showcase',1),(2,'September Events',2),(3,'October Events',3),(4,'November Events',3),(5,'December Events',2),(6,'Events',1);
/*!40000 ALTER TABLE `notebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `user_id` int NOT NULL AUTO_INCREMENT,
                        `first_name` varchar(255) DEFAULT NULL,
                        `last_name` varchar(255) DEFAULT NULL,
                        `user_name` varchar(255) DEFAULT NULL,
                        `email_address` varchar(255) NOT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `date_of_birth` date DEFAULT NULL,
                        `gender` char(3) DEFAULT NULL,
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `email_address_UNIQUE` (`email_address`),
                        UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1049 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Joe','Coyne','jcoyne','jcoyne@email.com','supersecret1','1964-04-01','F'),(2,'Fred','Hensen','fhensen','fhensen@email.com','supersecret2','1988-05-08','M'),(3,'Barney','Curry','bcurry','bcurry@email.com','supersecret3','1947-11-11','NB'),(4,'Karen','Mack','kmack','kmack@email.com','supersecret4','1986-07-08','NS'),(5,'Dianne','Klein','dklein','dklein@email.com','supersecret5','1991-09-22','F'),(6,'Dawn','Tillman','dtillman','dtillman@email.com','supersecret6','1979-08-30','F'),(1048,'Glynis','Fisher','gfisher','gcadagfisher@email.com','wahoo11','1992-08-11','F');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-25 17:33:33