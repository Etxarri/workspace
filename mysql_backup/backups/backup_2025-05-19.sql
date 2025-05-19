-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: 172.18.0.2    Database: welco
-- ------------------------------------------------------
-- Server version	8.4.5

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
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `categoriaID` smallint unsigned NOT NULL,
  `nombre` text NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`categoriaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Asesoría legal','Ayuda con trámites legales y documentación'),(2,'Idiomas','Clases e intercambios de idiomas'),(3,'Empleo','Ofertas de trabajo y formación laboral'),(4,'Eventos sociales','Actividades para conocer gente y la cultura local');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciudad`
--

DROP TABLE IF EXISTS `ciudad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ciudad` (
  `ciudadID` smallint unsigned NOT NULL,
  `comunidadID` smallint unsigned NOT NULL,
  `nombre` text NOT NULL,
  `codigo_postal` text,
  PRIMARY KEY (`ciudadID`),
  KEY `CIUDAD_COMUNIDAD_FK` (`comunidadID`),
  CONSTRAINT `CIUDAD_COMUNIDAD_FK` FOREIGN KEY (`comunidadID`) REFERENCES `comunidad_autonoma` (`comunidadID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciudad`
--

LOCK TABLES `ciudad` WRITE;
/*!40000 ALTER TABLE `ciudad` DISABLE KEYS */;
INSERT INTO `ciudad` VALUES (1,1,'Sevilla','41001'),(2,1,'Málaga','29001'),(3,3,'Madrid','28001'),(4,3,'Alcalá de Henares','28801'),(5,2,'Barcelona','08001'),(6,4,'Valencia','46001'),(7,5,'Santiago de Compostela','15701');
/*!40000 ALTER TABLE `ciudad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comentarioforo`
--

DROP TABLE IF EXISTS `comentarioforo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentarioforo` (
  `comentarioID` smallint unsigned NOT NULL,
  `hiloID` smallint unsigned NOT NULL,
  `usuarioID` smallint unsigned NOT NULL,
  `contenido` text NOT NULL,
  `fecha_hora` timestamp NOT NULL,
  KEY `COMENTARIOFORO_HILO_FK` (`hiloID`),
  KEY `COMENTARIOFORO_USUARIO_FK` (`usuarioID`),
  CONSTRAINT `COMENTARIOFORO_HILO_FK` FOREIGN KEY (`hiloID`) REFERENCES `hiloforo` (`hiloID`),
  CONSTRAINT `COMENTARIOFORO_USUARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comentarioforo`
--

LOCK TABLES `comentarioforo` WRITE;
/*!40000 ALTER TABLE `comentarioforo` DISABLE KEYS */;
INSERT INTO `comentarioforo` VALUES (1,1,1,'Necesitas tu pasaporte y un contrato de alquiler o compra','2023-01-10 11:00:00'),(2,1,4,'También puedes empadronarte si tienes una carta de invitación','2023-01-10 12:30:00'),(3,3,4,'En Idealista hay muchas opciones, o en grupos de Facebook','2023-03-20 10:00:00');
/*!40000 ALTER TABLE `comentarioforo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comunidad_autonoma`
--

DROP TABLE IF EXISTS `comunidad_autonoma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comunidad_autonoma` (
  `comunidadID` smallint unsigned NOT NULL,
  `paisID` smallint unsigned NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`comunidadID`),
  KEY `COMUNIDAD_PAIS_FK` (`paisID`),
  CONSTRAINT `COMUNIDAD_PAIS_FK` FOREIGN KEY (`paisID`) REFERENCES `pais` (`paisID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comunidad_autonoma`
--

LOCK TABLES `comunidad_autonoma` WRITE;
/*!40000 ALTER TABLE `comunidad_autonoma` DISABLE KEYS */;
INSERT INTO `comunidad_autonoma` VALUES (1,1,'Andalucía'),(2,1,'Cataluña'),(3,1,'Comunidad de Madrid'),(4,1,'Comunidad Valenciana'),(5,1,'Galicia');
/*!40000 ALTER TABLE `comunidad_autonoma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encuesta`
--

DROP TABLE IF EXISTS `encuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encuesta` (
  `encuestaID` smallint unsigned NOT NULL,
  `titulo` text NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`encuestaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encuesta`
--

LOCK TABLES `encuesta` WRITE;
/*!40000 ALTER TABLE `encuesta` DISABLE KEYS */;
INSERT INTO `encuesta` VALUES (1,'Satisfacción con los servicios','Valora tu experiencia con los recursos locales'),(2,'Necesidades no cubiertas','Qué echas en falta en tu ciudad');
/*!40000 ALTER TABLE `encuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventolocal`
--

DROP TABLE IF EXISTS `eventolocal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventolocal` (
  `eventoID` smallint unsigned NOT NULL,
  `ciudadID` smallint unsigned NOT NULL,
  `categoriaID` smallint unsigned NOT NULL,
  `usuarioID` smallint unsigned NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `fecha_hora` timestamp NOT NULL,
  `lugar` varchar(100) NOT NULL,
  PRIMARY KEY (`eventoID`),
  KEY `EVENTOLOCAL_CIUDAD_FK` (`ciudadID`),
  KEY `EVENTOLOCA_CATEGORIA_FK` (`categoriaID`),
  KEY `EVENTOLOCAL_VOLUNTARIO_FK` (`usuarioID`),
  CONSTRAINT `EVENTOLOCA_CATEGORIA_FK` FOREIGN KEY (`categoriaID`) REFERENCES `categoria` (`categoriaID`),
  CONSTRAINT `EVENTOLOCAL_CIUDAD_FK` FOREIGN KEY (`ciudadID`) REFERENCES `ciudad` (`ciudadID`),
  CONSTRAINT `EVENTOLOCAL_VOLUNTARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `voluntario` (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventolocal`
--

LOCK TABLES `eventolocal` WRITE;
/*!40000 ALTER TABLE `eventolocal` DISABLE KEYS */;
INSERT INTO `eventolocal` VALUES (1,1,4,1,'Café de bienvenida','Encuentro para recién llegados a Sevilla','2023-09-10 17:00:00','Cafetería Central, Plaza Nueva'),(2,5,2,4,'Intercambio de idiomas','Práctica de español con nativos','2023-09-15 18:30:00','Biblioteca Municipal de Barcelona'),(3,3,1,4,'Taller de empadronamiento','Cómo empadronarse en Madrid','2023-09-20 16:00:00','Centro Cultural Lavapiés');
/*!40000 ALTER TABLE `eventolocal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hacer_encuesta`
--

DROP TABLE IF EXISTS `hacer_encuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hacer_encuesta` (
  `encuestaID` smallint unsigned NOT NULL,
  `usuarioID` smallint unsigned NOT NULL,
  `titulo` text NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`encuestaID`,`usuarioID`),
  KEY `HACER_USUARIO_FK` (`usuarioID`),
  CONSTRAINT `HACER_ENCUESTA_FK` FOREIGN KEY (`encuestaID`) REFERENCES `encuesta` (`encuestaID`),
  CONSTRAINT `HACER_USUARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hacer_encuesta`
--

LOCK TABLES `hacer_encuesta` WRITE;
/*!40000 ALTER TABLE `hacer_encuesta` DISABLE KEYS */;
INSERT INTO `hacer_encuesta` VALUES (1,2,'Encuesta completada','Valoración de servicios'),(2,3,'Respuestas a necesidades','Feedback sobre ayudas necesarias');
/*!40000 ALTER TABLE `hacer_encuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hiloforo`
--

DROP TABLE IF EXISTS `hiloforo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hiloforo` (
  `hiloID` smallint unsigned NOT NULL,
  `preguntaID` smallint unsigned NOT NULL,
  `fecha_creacion` timestamp NOT NULL,
  PRIMARY KEY (`hiloID`),
  KEY `HILOFORO_PREGUNTA_FK` (`preguntaID`),
  CONSTRAINT `HILOFORO_PREGUNTA_FK` FOREIGN KEY (`preguntaID`) REFERENCES `preguntafrecuente` (`preguntaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hiloforo`
--

LOCK TABLES `hiloforo` WRITE;
/*!40000 ALTER TABLE `hiloforo` DISABLE KEYS */;
INSERT INTO `hiloforo` VALUES (1,1,'2023-01-10 10:30:00'),(2,2,'2023-02-15 11:45:00'),(3,3,'2023-03-20 09:15:00');
/*!40000 ALTER TABLE `hiloforo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pais` (
  `paisID` smallint unsigned NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `codigo_iso` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`paisID`),
  UNIQUE KEY `codigo_iso` (`codigo_iso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,'España','ES'),(2,'Portugal','PT'),(3,'Francia','FR'),(4,'Argentina','AR'),(5,'Mexico','ME');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntafrecuente`
--

DROP TABLE IF EXISTS `preguntafrecuente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preguntafrecuente` (
  `preguntaID` smallint unsigned NOT NULL,
  `temaID` smallint unsigned NOT NULL,
  `pregunta` text NOT NULL,
  `fecha_creacion` date NOT NULL,
  PRIMARY KEY (`preguntaID`),
  KEY `PREGUNTA_TEMA_FK` (`temaID`),
  CONSTRAINT `PREGUNTA_TEMA_FK` FOREIGN KEY (`temaID`) REFERENCES `temaforo` (`temaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntafrecuente`
--

LOCK TABLES `preguntafrecuente` WRITE;
/*!40000 ALTER TABLE `preguntafrecuente` DISABLE KEYS */;
INSERT INTO `preguntafrecuente` VALUES (1,1,'¿Qué documentos necesito para empadronarme?','2023-01-10'),(2,1,'¿Cómo solicitar la tarjeta sanitaria?','2023-02-15'),(3,2,'¿Dónde buscar piso compartido en Barcelona?','2023-03-20');
/*!40000 ALTER TABLE `preguntafrecuente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recienllegado`
--

DROP TABLE IF EXISTS `recienllegado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recienllegado` (
  `usuarioID` smallint unsigned NOT NULL,
  `necesidades` text,
  `fecha_llegada` date DEFAULT NULL,
  PRIMARY KEY (`usuarioID`),
  CONSTRAINT `RECIENLLEGADO_USUARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recienllegado`
--

LOCK TABLES `recienllegado` WRITE;
/*!40000 ALTER TABLE `recienllegado` DISABLE KEYS */;
INSERT INTO `recienllegado` VALUES (2,'Necesito ayuda con papeleo de empadronamiento','2023-05-15'),(3,'Buscando cursos de español y trabajo en hostelería','2023-06-20'),(5,'Necesito información sobre colegios para mis hijos','2023-07-10');
/*!40000 ALTER TABLE `recienllegado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recienllegado_apuntarse_evento`
--

DROP TABLE IF EXISTS `recienllegado_apuntarse_evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recienllegado_apuntarse_evento` (
  `eventoID` smallint unsigned NOT NULL,
  `usuarioID` smallint unsigned NOT NULL,
  PRIMARY KEY (`eventoID`,`usuarioID`),
  KEY `APUNTARSE_VOLUNTARIO_FK` (`usuarioID`),
  CONSTRAINT `APUNTARSE_EVENTOLOCAL_FK` FOREIGN KEY (`eventoID`) REFERENCES `eventolocal` (`eventoID`),
  CONSTRAINT `APUNTARSE_VOLUNTARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `recienllegado` (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recienllegado_apuntarse_evento`
--

LOCK TABLES `recienllegado_apuntarse_evento` WRITE;
/*!40000 ALTER TABLE `recienllegado_apuntarse_evento` DISABLE KEYS */;
INSERT INTO `recienllegado_apuntarse_evento` VALUES (1,2),(3,2),(2,3),(3,5);
/*!40000 ALTER TABLE `recienllegado_apuntarse_evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recursolocal`
--

DROP TABLE IF EXISTS `recursolocal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recursolocal` (
  `recursoID` smallint unsigned NOT NULL,
  `categoriaID` smallint unsigned NOT NULL,
  `ciudadID` smallint unsigned NOT NULL,
  `nombre` text NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(9) DEFAULT NULL,
  `horario` text,
  `web` text,
  PRIMARY KEY (`recursoID`),
  KEY `RECURSOLOCAL_CATEGORIA_FK` (`categoriaID`),
  KEY `RECURSOLOCAL_CIUDAD_FK` (`ciudadID`),
  CONSTRAINT `RECURSOLOCAL_CATEGORIA_FK` FOREIGN KEY (`categoriaID`) REFERENCES `categoria` (`categoriaID`),
  CONSTRAINT `RECURSOLOCAL_CIUDAD_FK` FOREIGN KEY (`ciudadID`) REFERENCES `ciudad` (`ciudadID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recursolocal`
--

LOCK TABLES `recursolocal` WRITE;
/*!40000 ALTER TABLE `recursolocal` DISABLE KEYS */;
INSERT INTO `recursolocal` VALUES (1,1,3,'Oficina de Atención al Inmigrante','Asesoría gratuita para trámites legales','Calle Leganitos 12','915432100','L-V 9:00-14:00','www.madrid.es/inmigracion'),(2,2,5,'Escuela Oficial de Idiomas','Cursos de español para extranjeros','Avinguda Diagonal 123','934567890','L-V 16:00-21:00','www.eoibd.cat'),(3,3,1,'Agencia de Colocación Andalucía','Ofertas de empleo y formación','Calle Torneo 25','955551212','L-V 8:00-15:00','www.andaluciaorienta.es');
/*!40000 ALTER TABLE `recursolocal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temaforo`
--

DROP TABLE IF EXISTS `temaforo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `temaforo` (
  `temaID` smallint unsigned NOT NULL,
  `nombre` text NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`temaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temaforo`
--

LOCK TABLES `temaforo` WRITE;
/*!40000 ALTER TABLE `temaforo` DISABLE KEYS */;
INSERT INTO `temaforo` VALUES (1,'Trámites legales','Dudas sobre papeles, empadronamiento, etc.'),(2,'Vivienda','Cómo buscar piso, contratos, compañeros'),(3,'Cultura española','Costumbres, tradiciones, formas de vida');
/*!40000 ALTER TABLE `temaforo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `usuarioID` smallint unsigned NOT NULL,
  `paisID` smallint unsigned NOT NULL,
  `ciudadID` smallint unsigned NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `contraseña` text NOT NULL,
  `idioma_principal` text NOT NULL,
  `tipo` enum('recienllegado','voluntario') NOT NULL,
  PRIMARY KEY (`usuarioID`),
  UNIQUE KEY `email` (`email`),
  KEY `USUARIO_PAIS_FK` (`paisID`),
  KEY `USUARIO_CIUDAD_FK` (`ciudadID`),
  CONSTRAINT `USUARIO_CIUDAD_FK` FOREIGN KEY (`ciudadID`) REFERENCES `ciudad` (`ciudadID`),
  CONSTRAINT `USUARIO_PAIS_FK` FOREIGN KEY (`paisID`) REFERENCES `pais` (`paisID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,1,'Juan','García','juan@gmail.com','pass123','español','voluntario'),(2,1,3,'María','López','maria@hotmail.com','pass456','español','recienllegado'),(3,1,5,'Ahmed','Khan','ahmed@yahoo.com','pass789','urdu','recienllegado'),(4,1,6,'Elena','Martínez','elena@gmail.com','pass012','español','voluntario'),(5,1,2,'Mohamed','Ali','mohamed@outlook.com','pass345','árabe','recienllegado');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voluntario`
--

DROP TABLE IF EXISTS `voluntario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voluntario` (
  `usuarioID` smallint unsigned NOT NULL,
  `habilidades` text,
  `años_en_ciudad` int DEFAULT NULL,
  `motivacion` text,
  PRIMARY KEY (`usuarioID`),
  CONSTRAINT `VOLUNTARIO_USUARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voluntario`
--

LOCK TABLES `voluntario` WRITE;
/*!40000 ALTER TABLE `voluntario` DISABLE KEYS */;
INSERT INTO `voluntario` VALUES (1,'Traducción inglés-español, asesoramiento legal básico',5,'Quiero ayudar a integrarse a los nuevos vecinos'),(4,'Organización de eventos, enseñanza de español',8,'Me gusta construir comunidad');
/*!40000 ALTER TABLE `voluntario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-19 17:13:00
