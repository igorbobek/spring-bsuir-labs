-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cryptocrash
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wallet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `balance` double NOT NULL,
  `private_key` varchar(100) NOT NULL,
  `public_key` varchar(100) NOT NULL,
  `wif` varchar(100) NOT NULL,
  PRIMARY KEY (`id`,`address`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

LOCK TABLES `wallet` WRITE;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT INTO `wallet` VALUES (3,'1zcxhLssFnzuakcKCtuF83pu1d1GsNg5V',0,'089131a7615893c3a0497c00a9eafbfe17994a4ccafd7180e2a29e04d7d385e3','029438ad97c1e03a0c7c6d864c54f31d9a3f619e06f924d893b94ebeff96a89b27','KwWN5rVxBfnHb3anJURYrepace4nm7v6rfUriKeFda3foan22xki'),(4,'1H4yHcnM5DeCqNXP7Vr4MrUdYK6LDiPEnp',0,'0489d7397998e32ebcb61ce290fb805db51ae0e966c0012f71e95ff38d55244b','03cd0fdbed0b67e39345ca6e53edf4b9499521fcad4f113f8f433977f639727409','KwNXsKWdXUVB16JXYpjcARZfTNWmTzMUYFkcbsDrMGsocb3YuXSk'),(5,'1BSJD4uEbxLbCufu2cqYKdYdnPTqjkmfx4',0,'a7a7013aa539dcebb8113c790e4b0fd8447495b8088bcc83abf1d2e59c65b294','027ed4acd90c46017d7a525713dd1e6b064efc942a5458844e861c3f74267c0698','L2qc42q7aqGTb9DmjuXitiFfsFpWYhmhCYN9w8Zba9D14JXRtHKL'),(6,'1GPRkW9sHQxada3mj5Hh67et57LfdFfVpQ',0,'820021f4abe234b69017af8f144c7f353890fdf8416906825114f44114c88bc3','03626898b3a11c50474297954163d071d2b03e1d638abb42f6b91297e511b269a2','L1aR1yn4qM9oM6stsPrFWMg6a45AkRacQbihPJGiXpZJm3qgGBGj');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-18  8:52:18
