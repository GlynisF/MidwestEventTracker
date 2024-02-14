-- MySQL dump 10.13  Distrib 8.1.0, for Win64 (x86_64)
--
-- Host: awseb-e-cimx28xpi2-stack-awsebrdsdatabase-egvchjg7ataw.czsgd5vqo4ab.us-east-2.rds.amazonaws.com    Database: eventtracker
-- ------------------------------------------------------
-- Server version	8.0.33

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artist` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `moniker` varchar(150) DEFAULT NULL,
                          `first_name` varchar(100) NOT NULL,
                          `last_name` varchar(100) NOT NULL,
                          `booking_fee` decimal(10,2) NOT NULL,
                          `event_id` int NOT NULL,
                          `email` varchar(100) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `artist_pk` (`id`),
                          KEY `event_id` (`event_id`),
                          CONSTRAINT `event_id` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES (1,'Dj person','Jim','James',500.00,12,'jimjames@email.com'),(3,'Dj people','Jane','Janes',200.00,13,'jane@email.com'),(4,'Dj Best','Sarah','Johnson',1000.00,12,'sjohnson@email.com'),(5,'Dj Worst','Paul','Johnson',100.00,14,'djworst@email.com'),(6,'Dj Happy','Ed','Edwards',239.00,11,'e.edwards@email.com'),(7,'Dj Sad','Alana','Porter',455.25,12,'djsad@email.com');
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'RIN',1),(2,'S. Bar',1),(3,'J jams',4),(4,'Patio takeover',5),(5,'Patio takeover',5),(10,'LFF',815),(11,'RIN @ HNS 20234',816),(12,'Jams',821),(13,'Jams',823),(14,'RIN',815);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_details`
--

DROP TABLE IF EXISTS `event_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_details` (
                                 `details_id` int NOT NULL AUTO_INCREMENT,
                                 `date_of_event` date DEFAULT NULL,
                                 `start_time` time DEFAULT NULL,
                                 `end_time` time DEFAULT NULL,
                                 `event_id` int NOT NULL,
                                 PRIMARY KEY (`details_id`),
                                 UNIQUE KEY `event_details_pk_2` (`details_id`),
                                 KEY `event_details_fk` (`event_id`),
                                 CONSTRAINT `event_details_fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_details`
--

LOCK TABLES `event_details` WRITE;
/*!40000 ALTER TABLE `event_details` DISABLE KEYS */;
INSERT INTO `event_details` VALUES (1,'2024-01-27','07:12:52','09:13:16',10),(2,'2024-02-24','07:14:03','12:14:07',11),(3,'2024-03-10','10:16:00','11:20:00',12),(4,'2024-01-16','12:16:05','14:16:11',13);
/*!40000 ALTER TABLE `event_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `location_name` varchar(100) NOT NULL,
                            `phone_number` varchar(20) DEFAULT NULL,
                            `address` varchar(100) NOT NULL,
                            `apartment` varchar(100) DEFAULT NULL,
                            `city` varchar(100) NOT NULL,
                            `state` varchar(2) NOT NULL,
                            `zip` varchar(10) DEFAULT NULL,
                            `wheelchair_accessible_entrance` tinyint(1) DEFAULT NULL,
                            `website` varchar(255) DEFAULT NULL,
                            `event_fk` int NOT NULL,
                            `place_id` varchar(100) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `location_pk2` (`id`),
                            KEY `event_fk` (`event_fk`),
                            CONSTRAINT `event_fk` FOREIGN KEY (`event_fk`) REFERENCES `event` (`event_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'High Noon Saloon','608-123-3333','701 East Washington Ave',NULL,'Madison','WI','53703',1,'https://www.highnoon.com',1,NULL),(2,'Quarters','608-222-3333','301 E Center Ave',NULL,'Milwaukee','WI','53702',1,'https://www.quarters.com',1,NULL),(3,'Sylvee','608-111-1555','102 East Washington Ave',NULL,'Madison','WI','53704',0,'https://www.sylvee.com',5,NULL),(4,'Smart Bar','312-111-4444','305 Clark St',NULL,'Chicago','IL','61802',1,'https://www.smartbar.com',3,NULL),(5,'The Spot','414-414-4144','111 South St',NULL,'Milwaukee','WI','53505',0,'https://www.thespot.com',2,NULL),(6,'Music Lounge','222-559-6695','123 North St',NULL,'Lincoln','NE','43507',1,'https://www.musiclounge.com',2,NULL),(7,'Disc Parlour','312-213-3123','523 Parlour Ave',NULL,'Chicago','IL','68909',1,'https://www.discparlour.com',5,NULL),(12,'Nattspil','','211 King Street','','Madison','WI','53703',1,'http://www.nattspil.com/',4,'ChIJRQmp2T9TBogRUmenzgdjqbc'),(13,'Wisconsin State Capitol','(608) 266-0382','2 East Main Street','','Madison','WI','53703',1,'https://www.wisconsin.gov/Pages/home.aspx',4,'ChIJ5YH4falWBogR5Lpub1B0OVA'),(14,'Madison Children\'s Museum','(608) 256-6445','100 North Hamilton Street','','Madison','WI','53703',1,'http://madisonchildrensmuseum.org/',4,'ChIJf0C9UkdTBogRQ6v1ecrB8Yk'),(15,'The Sylvee','(608) 709-8157','25 South Livingston Street','','Madison','WI','53703',1,'http://thesylvee.com/',11,'ChIJnwlKE4VTBogRvmF0txWd5nY'),(16,'Asian Sweet Bakery','(608) 665-3988','1017 South Park Street','','Madison','WI','53715',1,'',4,'ChIJ88SKRNVSBogRFaR5RQyqw_Q'),(17,'MonsoonSiamsssss','(608) 284-9282','2326 Atwood Avenue','','Madison','WI','53704',0,NULL,12,'ChIJrdVV94ZTBogRgDwyGooHASY'),(18,'Madison Children\'s Museum','(608) 256-6445','100 North Hamilton Street','','Madison','WI','53703',1,'http://madisonchildrensmuseum.org/',4,'ChIJf0C9UkdTBogRQ6v1ecrB8Yk'),(19,'Asian Midway Foods','(608) 255-5864','301 South Park Street','','Madison','WI','53715',1,'',11,'ChIJt11B4dKsB4gRw8_shzc3OgQ'),(20,'Tenney Park','(608) 266-4711','402 North Thornton Avenue','','Madison','WI','53703',1,'https://www.cityofmadison.com/parks/find-a-park/park.cfm?id=1357',10,'ChIJ4S4bKH1TBogRoJs0IEFgQXk'),(21,'Madison Area Technical College','(608) 246-6100','1701 Wright Street','','Madison','WI','53704',1,'https://madisoncollege.edu/',14,'ChIJdWMutiRUBogRX1C3iULmO_M'),(22,'Madison Area Technical College','(608) 246-6100','1701 Wright Street','','Madison','WI','53704',1,'https://madisoncollege.edu/',4,''),(23,'Madison Area Technical College','(608) 246-6100','1701 Wright Street','','Madison','WI','53704',1,'https://madisoncollege.edu/',13,''),(24,'Tenney Park','6082664711','402 North Thornton Avenue','','Madison','WI','53703',0,'',4,NULL),(29,'Madison Sourdough','(608) 442-8009','916 Williamson Street','','Madison','WI','53703',0,'https://www.madisonsourdough.com/',4,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=830 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notebook`
--

LOCK TABLES `notebook` WRITE;
/*!40000 ALTER TABLE `notebook` DISABLE KEYS */;
INSERT INTO `notebook` VALUES (1,'RIN Showcase',1),(2,'September Events',2),(3,'October Events',3),(4,'November Events',3),(5,'December Events',2),(6,'Events',1),(815,'January Events',1050),(816,'February Events',1050),(821,'April Events',1050),(822,'May Events',1050),(823,'May 2024',1050),(824,'June Events',1050),(825,'July Events',1050),(826,'August Events',1050),(827,'September Events',1050),(828,'October Events',1050),(829,'November Events',1050);
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
                        `gender` varchar(20) DEFAULT NULL,
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `email_address_UNIQUE` (`email_address`),
                        UNIQUE KEY `user_pk` (`user_id`),
                        UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1051 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Joe','Coyne','jcoyne','jcoyne@email.com','supersecret1','1964-04-01','F'),(2,'Fred','Hensen','fhensen','fhensen@email.com','supersecret2','1988-05-08','M'),(3,'Barney','Curry','bcurry','bcurry@email.com','supersecret3','1947-11-11','NB'),(4,'Karen','Mack','kmack','kmack@email.com','supersecret4','1986-07-08','NS'),(5,'Dianne','Klein','dklein','dklein@email.com','supersecret5','1991-09-22','F'),(6,'Dawn','Tillman','dtillman','dtillman@email.com','supersecret6','1979-08-30','F'),(1048,'Glynis','Fisher','gfisher9','gcadagfisher@email.com','wahoo11','1992-08-11','F'),(1049,'Glynis','Fisher','gfisher4','gcadagfisher2@email.com','wahoo11','1992-08-11','F'),(1050,'Glynis','Fisher','gfisher','glyniscfisher@gmail.com','df9ad74d-63d8-4a8d-aa9f-c708f73372c4','1992-08-11','Female');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-25 12:34:16