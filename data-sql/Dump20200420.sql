-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: fmsdb
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS authority;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE authority (
  authority_id int NOT NULL AUTO_INCREMENT,
  `name` varchar(70) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  created_by int DEFAULT NULL,
  created_date datetime DEFAULT NULL,
  modified_by int DEFAULT NULL,
  last_modified_date datetime DEFAULT NULL,
  PRIMARY KEY (authority_id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS event;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  id int NOT NULL AUTO_INCREMENT,
  event_id varchar(20) NOT NULL,
  event_name varchar(70) DEFAULT NULL,
  event_description varchar(250) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  event_date datetime DEFAULT NULL,
  base_location varchar(45) DEFAULT NULL,
  beneficiary_name varchar(70) DEFAULT NULL,
  venue_address varchar(350) DEFAULT NULL,
  council_name varchar(70) DEFAULT NULL,
  project varchar(70) DEFAULT NULL,
  category varchar(100) DEFAULT NULL,
  total_no_of_volunteers int DEFAULT NULL,
  total_volunteer_hours decimal(6,2) DEFAULT NULL,
  total_travel_hours decimal(6,2) DEFAULT NULL,
  overall_volunteering_hours decimal(6,2) DEFAULT NULL,
  lives_impacted int DEFAULT NULL,
  activity_type int DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY event_id_UNIQUE (event_id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_feedback`
--

DROP TABLE IF EXISTS event_feedback;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE event_feedback (
  id int NOT NULL AUTO_INCREMENT,
  event_id int NOT NULL,
  user_id int NOT NULL,
  participation_status_id int DEFAULT NULL,
  rating int DEFAULT NULL,
  created_date datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_ef_event_idx (event_id),
  KEY fk_ef_user_idx (user_id),
  KEY fk_ef_event_participation_type_idx (participation_status_id),
  CONSTRAINT fk_ef_event FOREIGN KEY (event_id) REFERENCES `event` (id),
  CONSTRAINT fk_ef_event_participation_type FOREIGN KEY (participation_status_id) REFERENCES event_participation_type (id),
  CONSTRAINT fk_ef_user FOREIGN KEY (user_id) REFERENCES `user` (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_participant_detail`
--

DROP TABLE IF EXISTS event_participant_detail;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE event_participant_detail (
  id int NOT NULL AUTO_INCREMENT,
  event_id int NOT NULL,
  user_id int NOT NULL,
  participation_status_id int NOT NULL,
  volunteer_hours int DEFAULT NULL,
  travel_hours int DEFAULT NULL,
  business_unit varchar(70) DEFAULT NULL,
  iiep_category varchar(70) DEFAULT NULL,
  feedback_mail_sent_status int DEFAULT NULL,
  feedback_submitted_status int DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_ep_participant_type_idx (participation_status_id),
  KEY fk_epd_user_idx (user_id),
  KEY fk_epd_event_idx (event_id),
  CONSTRAINT fk_epd_event FOREIGN KEY (event_id) REFERENCES `event` (id),
  CONSTRAINT fk_epd_participant_type FOREIGN KEY (participation_status_id) REFERENCES event_participation_type (id),
  CONSTRAINT fk_epd_user FOREIGN KEY (user_id) REFERENCES `user` (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_participation_type`
--

DROP TABLE IF EXISTS event_participation_type;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE event_participation_type (
  id int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_poc_user`
--

DROP TABLE IF EXISTS event_poc_user;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE event_poc_user (
  event_id int NOT NULL,
  user_id int NOT NULL,
  PRIMARY KEY (event_id,user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback_answer`
--

DROP TABLE IF EXISTS feedback_answer;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE feedback_answer (
  id int NOT NULL AUTO_INCREMENT,
  question_id int DEFAULT NULL,
  answer_text varchar(350) NOT NULL,
  created_by int DEFAULT NULL,
  created_date datetime DEFAULT NULL,
  modified_by int DEFAULT NULL,
  last_modified_date datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_fa_question_idx (question_id),
  CONSTRAINT fk_fa_question FOREIGN KEY (question_id) REFERENCES feedback_question (id)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback_question`
--

DROP TABLE IF EXISTS feedback_question;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE feedback_question (
  id int NOT NULL AUTO_INCREMENT,
  question_text varchar(350) NOT NULL,
  participation_type_id int DEFAULT NULL,
  feedback_type varchar(45) DEFAULT NULL,
  created_by int DEFAULT NULL,
  created_date datetime DEFAULT NULL,
  modified_by int DEFAULT NULL,
  last_modified_date datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback_response`
--

DROP TABLE IF EXISTS feedback_response;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE feedback_response (
  id int NOT NULL AUTO_INCREMENT,
  event_feedback_id int DEFAULT NULL,
  question_id int NOT NULL,
  answer_text varchar(350) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_fr_question_idx (question_id),
  KEY fk_fr_event_feedback_idx (event_feedback_id),
  CONSTRAINT fk_fr_event_feedback FOREIGN KEY (event_feedback_id) REFERENCES event_feedback (id),
  CONSTRAINT fk_fr_question FOREIGN KEY (question_id) REFERENCES feedback_question (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS role;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  role_id int NOT NULL AUTO_INCREMENT,
  role_name varchar(45) NOT NULL,
  role_description varchar(150) DEFAULT NULL,
  created_by int DEFAULT NULL,
  created_date datetime DEFAULT NULL,
  modified_by int DEFAULT NULL,
  last_modified_date datetime DEFAULT NULL,
  active_status int DEFAULT NULL,
  PRIMARY KEY (role_id),
  UNIQUE KEY role_name_UNIQUE (role_name),
  KEY fk_role_modified_user_idx (modified_by,created_by),
  KEY fk_role_created_by_user_idx (created_by),
  CONSTRAINT fk_role_created_by_user FOREIGN KEY (created_by) REFERENCES `user` (user_id),
  CONSTRAINT fk_role_modified_by_user FOREIGN KEY (modified_by) REFERENCES `user` (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_authority`
--

DROP TABLE IF EXISTS role_authority;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE role_authority (
  role_id int NOT NULL,
  authority_id int NOT NULL,
  PRIMARY KEY (role_id,authority_id),
  KEY fk_ra_authority_idx (authority_id),
  CONSTRAINT fk_ra_authority FOREIGN KEY (authority_id) REFERENCES authority (authority_id),
  CONSTRAINT fk_ra_role FOREIGN KEY (role_id) REFERENCES `role` (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS user;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  id int NOT NULL AUTO_INCREMENT,
  user_id int DEFAULT NULL,
  user_name varchar(45) DEFAULT NULL,
  encrypted_password varchar(65) DEFAULT NULL,
  email_id varchar(45) DEFAULT NULL,
  mobile_number varchar(20) DEFAULT NULL,
  created_by int DEFAULT NULL,
  created_date datetime DEFAULT NULL,
  active_status int DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY userId_UNIQUE (user_id),
  KEY fk_user_creaded_by_user_idx (created_by),
  CONSTRAINT fk_user_creaded_by_user FOREIGN KEY (created_by) REFERENCES `user` (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS user_role;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE user_role (
  user_id int NOT NULL,
  role_id int NOT NULL,
  PRIMARY KEY (user_id,role_id),
  KEY fk_role_idx (role_id),
  CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES `role` (role_id),
  CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES `user` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-20 14:40:07
