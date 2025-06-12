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
-- Table structure for table `aa`
--

DROP TABLE IF EXISTS `aa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aa` (
  `encuestaID` smallint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aa`
--

LOCK TABLES `aa` WRITE;
/*!40000 ALTER TABLE `aa` DISABLE KEYS */;
/*!40000 ALTER TABLE `aa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `categoriaID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`categoriaID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Idiomas','Clases, recursos y apoyo para aprender o mejorar el idioma local'),(2,'Cultura','Actividades relacionadas con la cultura local, tradiciones y costumbres'),(3,'Trabajo','Ofertas laborales, orientación profesional y talleres de empleo'),(4,'Colegio','Información y apoyo sobre escolarización, inscripciones y recursos educativos'),(5,'Salud','Recursos sanitarios, centros de salud y campañas de bienestar'),(6,'Documentos y Trámites','Asistencia con empadronamiento, NIE, seguridad social y otros trámites'),(7,'Jurídico','Asesoría legal, derechos laborales y recursos jurídicos');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciudad`
--

DROP TABLE IF EXISTS `ciudad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ciudad` (
  `ciudadID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `comunidadID` smallint unsigned NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `codigo_postal` varchar(10) NOT NULL,
  `latitud` decimal(10,7) DEFAULT NULL,
  `longitud` decimal(10,7) DEFAULT NULL,
  PRIMARY KEY (`ciudadID`),
  KEY `CIUDAD_COMUNIDAD_FK` (`comunidadID`),
  CONSTRAINT `CIUDAD_COMUNIDAD_FK` FOREIGN KEY (`comunidadID`) REFERENCES `comunidad_autonoma` (`comunidadID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciudad`
--

LOCK TABLES `ciudad` WRITE;
/*!40000 ALTER TABLE `ciudad` DISABLE KEYS */;
INSERT INTO `ciudad` VALUES (1,1,'Sevilla','41001',37.3886000,-5.9823000),(2,2,'Barcelona','08001',41.3888000,2.1590000),(3,3,'Madrid','28001',40.4168000,-3.7038000),(4,4,'Valencia','46001',39.4699000,-0.3763000),(5,5,'Santiago de Compostela','15701',42.8805000,-8.5457000),(6,6,'Bilbao','48001',43.2630000,-2.9350000);
/*!40000 ALTER TABLE `ciudad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comentarioforo`
--

DROP TABLE IF EXISTS `comentarioforo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentarioforo` (
  `comentarioID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `hiloID` smallint unsigned NOT NULL,
  `usuarioID` smallint unsigned NOT NULL,
  `contenido` varchar(50) NOT NULL,
  `fecha_hora` timestamp NOT NULL,
  `boto_pos` int DEFAULT NULL,
  PRIMARY KEY (`comentarioID`),
  KEY `COMENTARIOFORO_HILO_FK` (`hiloID`),
  KEY `COMENTARIOFORO_USUARIO_FK` (`usuarioID`),
  CONSTRAINT `COMENTARIOFORO_HILO_FK` FOREIGN KEY (`hiloID`) REFERENCES `hiloforo` (`hiloID`),
  CONSTRAINT `COMENTARIOFORO_USUARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`usuarioID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comentarioforo`
--

LOCK TABLES `comentarioforo` WRITE;
/*!40000 ALTER TABLE `comentarioforo` DISABLE KEYS */;
INSERT INTO `comentarioforo` VALUES (1,1,1,'Puedes ver series en español','2023-04-11 11:00:00',NULL),(2,2,2,'Hay procesiones en Semana Santa','2023-04-16 16:00:00',NULL),(3,3,3,'asdf','2025-06-11 14:16:19',3),(4,3,3,'asdfa','2025-06-11 14:16:23',1);
/*!40000 ALTER TABLE `comentarioforo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comunidad_autonoma`
--

DROP TABLE IF EXISTS `comunidad_autonoma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comunidad_autonoma` (
  `comunidadID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `paisID` smallint unsigned NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`comunidadID`),
  KEY `COMUNIDAD_PAIS_FK` (`paisID`),
  CONSTRAINT `COMUNIDAD_PAIS_FK` FOREIGN KEY (`paisID`) REFERENCES `pais` (`paisID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comunidad_autonoma`
--

LOCK TABLES `comunidad_autonoma` WRITE;
/*!40000 ALTER TABLE `comunidad_autonoma` DISABLE KEYS */;
INSERT INTO `comunidad_autonoma` VALUES (1,1,'Andalucía'),(2,1,'Cataluña'),(3,1,'Madrid'),(4,1,'Comunidad Valenciana'),(5,1,'Galicia'),(6,1,'País Vasco');
/*!40000 ALTER TABLE `comunidad_autonoma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encuesta`
--

DROP TABLE IF EXISTS `encuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encuesta` (
  `encuestaID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `titulo` varchar(50) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`encuestaID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encuesta`
--

LOCK TABLES `encuesta` WRITE;
/*!40000 ALTER TABLE `encuesta` DISABLE KEYS */;
INSERT INTO `encuesta` VALUES (1,'Encuesta de Integración 2025','Evaluación completa del nivel de integración de migrantes'),(2,'Satisfacción de eventos','Cuestionario sobre los eventos realizados'),(3,'Evaluación de taller cultural','Me gustó el evento cultural realizado');
/*!40000 ALTER TABLE `encuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventolocal`
--

DROP TABLE IF EXISTS `eventolocal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventolocal` (
  `eventoID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `ciudadID` smallint unsigned NOT NULL,
  `categoriaID` smallint unsigned NOT NULL,
  `usuarioID` smallint unsigned NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `fecha_hora` timestamp NOT NULL,
  `lugar` varchar(100) NOT NULL,
  `imagen` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`eventoID`),
  KEY `EVENTOLOCAL_CIUDAD_FK` (`ciudadID`),
  KEY `EVENTOLOCA_CATEGORIA_FK` (`categoriaID`),
  KEY `EVENTOLOCAL_VOLUNTARIO_FK` (`usuarioID`),
  CONSTRAINT `EVENTOLOCA_CATEGORIA_FK` FOREIGN KEY (`categoriaID`) REFERENCES `categoria` (`categoriaID`),
  CONSTRAINT `EVENTOLOCAL_CIUDAD_FK` FOREIGN KEY (`ciudadID`) REFERENCES `ciudad` (`ciudadID`),
  CONSTRAINT `EVENTOLOCAL_VOLUNTARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `voluntario` (`usuarioID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventolocal`
--

LOCK TABLES `eventolocal` WRITE;
/*!40000 ALTER TABLE `eventolocal` DISABLE KEYS */;
INSERT INTO `eventolocal` VALUES (1,1,1,1,'Intercambio de idiomas','Sesión para practicar inglés y español','2023-05-20 18:00:00','Centro Cultural',NULL),(2,3,2,1,'Taller cultural','Introducción a las festividades españolas','2023-06-15 10:00:00','Sala de conferencias',NULL),(3,1,3,1,'Feria de empleo','Conecta con empleadores locales y recibe orientación laboral','2023-07-10 09:00:00','Pabellón municipal de Barcelona',NULL),(4,2,4,1,'Puertas abiertas escolares','Visita a colegios públicos para conocer opciones educativas','2023-07-15 17:30:00','Escuela Primaria Central',NULL),(5,3,5,1,'Charla sobre salud pública','Información sobre servicios sanitarios gratuitos','2023-07-22 11:00:00','Centro de Salud Lavapiés',NULL),(6,1,6,1,'Ayuda con trámites','Taller práctico para empadronamiento y NIE','2023-08-01 16:00:00','Oficina de Atención al Ciudadano',NULL),(7,2,7,1,'Asesoría legal básica','Sesión de preguntas y respuestas sobre derechos laborales','2023-08-10 18:00:00','Centro Comunitario La Merced',NULL),(8,3,2,1,'Taller cultural','Introducción a las festividades españolas','2023-06-15 10:00:00','Sala de conferencias',NULL),(9,1,6,3,'asdf','asdf','2025-06-04 14:27:00','asdf',NULL),(10,1,5,3,'asd','asdf','2025-06-05 14:29:00','asdf',NULL);
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
  `titulo` varchar(50) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `resultado_psicologico` double(5,4) unsigned NOT NULL,
  `resultado_linguistico` double(5,4) unsigned NOT NULL,
  `resultado_economico` double(5,4) unsigned NOT NULL,
  `resultado_politico` double(5,4) unsigned NOT NULL,
  `resultado_social` double(5,4) unsigned NOT NULL,
  `resultado_navegacional` double(5,4) unsigned NOT NULL,
  `resultado_total` double(5,4) unsigned NOT NULL,
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
INSERT INTO `hacer_encuesta` VALUES (1,2,'Encuesta de Integración 2025','Evaluación completa del nivel de integración de migrantes',0.2500,0.3700,0.1400,0.0990,0.6010,0.5240,0.0820),(1,3,'Encuesta de Integración 2025','Evaluación completa del nivel de integración de migrantes',0.4375,0.4375,0.5625,0.3125,0.5625,0.4375,0.4583);
/*!40000 ALTER TABLE `hacer_encuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hiloforo`
--

DROP TABLE IF EXISTS `hiloforo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hiloforo` (
  `hiloID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `preguntaID` smallint unsigned NOT NULL,
  `fecha_creacion` timestamp NOT NULL,
  PRIMARY KEY (`hiloID`),
  KEY `HILOFORO_PREGUNTA_FK` (`preguntaID`),
  CONSTRAINT `HILOFORO_PREGUNTA_FK` FOREIGN KEY (`preguntaID`) REFERENCES `preguntafrecuente` (`preguntaID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hiloforo`
--

LOCK TABLES `hiloforo` WRITE;
/*!40000 ALTER TABLE `hiloforo` DISABLE KEYS */;
INSERT INTO `hiloforo` VALUES (1,1,'2023-04-11 10:00:00'),(2,2,'2023-04-16 15:30:00'),(3,4,'2025-06-11 14:16:14');
/*!40000 ALTER TABLE `hiloforo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensajechat`
--

DROP TABLE IF EXISTS `mensajechat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensajechat` (
  `mensajeID` int unsigned NOT NULL AUTO_INCREMENT,
  `usuarioID` smallint unsigned NOT NULL,
  `contenido` varchar(1000) NOT NULL,
  `fecha_hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idioma` varchar(10) NOT NULL,
  PRIMARY KEY (`mensajeID`),
  KEY `usuarioID` (`usuarioID`),
  CONSTRAINT `mensajechat_ibfk_1` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensajechat`
--

LOCK TABLES `mensajechat` WRITE;
/*!40000 ALTER TABLE `mensajechat` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensajechat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pais` (
  `paisID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `codigo_iso` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`paisID`),
  UNIQUE KEY `codigo_iso` (`codigo_iso`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,'España','ES'),(2,'Francia','FR'),(3,'Portugal','PT'),(4,'Colombia','CO'),(5,'Marruecos','MA'),(6,'Venezuela','VE');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntafrecuente`
--

DROP TABLE IF EXISTS `preguntafrecuente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preguntafrecuente` (
  `preguntaID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `temaID` smallint unsigned NOT NULL,
  `pregunta` varchar(50) NOT NULL,
  `fecha_creacion` date NOT NULL,
  PRIMARY KEY (`preguntaID`),
  KEY `PREGUNTA_TEMA_FK` (`temaID`),
  CONSTRAINT `PREGUNTA_TEMA_FK` FOREIGN KEY (`temaID`) REFERENCES `temaforo` (`temaID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntafrecuente`
--

LOCK TABLES `preguntafrecuente` WRITE;
/*!40000 ALTER TABLE `preguntafrecuente` DISABLE KEYS */;
INSERT INTO `preguntafrecuente` VALUES (1,1,'¿Cómo practicar español diariamente?','2023-04-10'),(2,2,'¿Qué festividades hay en Sevilla?','2023-04-15'),(3,2,'asd','2025-06-11'),(4,5,'asd','2025-06-11');
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
  `necesidades` varchar(50) DEFAULT NULL,
  `lenguaje` varchar(10) DEFAULT NULL,
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
INSERT INTO `recienllegado` VALUES (2,'Necesito aprender español','Inglés','2023-05-01'),(5,'q','es','2025-06-11');
/*!40000 ALTER TABLE `recienllegado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recursolocal`
--

DROP TABLE IF EXISTS `recursolocal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recursolocal` (
  `recursoID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `categoriaID` smallint unsigned NOT NULL,
  `ciudadID` smallint unsigned NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `hora_abierto` time DEFAULT NULL,
  `hora_cerrado` time DEFAULT NULL,
  `latitud` decimal(9,6) NOT NULL,
  `longitud` decimal(9,6) NOT NULL,
  PRIMARY KEY (`recursoID`),
  KEY `RECURSOLOCAL_CATEGORIA_FK` (`categoriaID`),
  KEY `RECURSOLOCAL_CIUDAD_FK` (`ciudadID`),
  CONSTRAINT `RECURSOLOCAL_CATEGORIA_FK` FOREIGN KEY (`categoriaID`) REFERENCES `categoria` (`categoriaID`),
  CONSTRAINT `RECURSOLOCAL_CIUDAD_FK` FOREIGN KEY (`ciudadID`) REFERENCES `ciudad` (`ciudadID`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recursolocal`
--

LOCK TABLES `recursolocal` WRITE;
/*!40000 ALTER TABLE `recursolocal` DISABLE KEYS */;
INSERT INTO `recursolocal` VALUES (1,5,1,'Centro de Salud Norte','Atención sanitaria primaria gratuita','Avda. de Miraflores 15','954789012','08:00:00','15:00:00',37.410142,-5.975333),(2,1,1,'Ouiea Idiomas','Academia de idiomas','C. Lebrija, 2','954701105','16:30:00','20:30:00',37.376971,-5.970471),(3,2,1,'Peña Cultural La Barzola','Centro cultural','Pl. Virgen del Pilar, 15','954946967','11:00:00','22:30:00',37.408400,-5.980943),(4,3,1,'Servicio Público de Empleo Estatal','Oficina de empleo','C. Botánica, s/n, Norte','955563646','09:00:00','14:00:00',37.432218,-5.975492),(5,4,1,'Equipo de Orientación Educativa','Consejeria de educacion','C. Gallega a la Ventana, 5','955624587','09:00:00','14:00:00',37.396755,-5.967081),(6,6,1,'Agencia Tributaria de Sevilla','Oficina de administracion tributaria','C. José Luis Luque, 4 acc, Casco Antiguo','902415600','08:30:00','14:00:00',37.394558,-5.991113),(7,7,1,'Analistas Laborales Sur','Gestoria','C. Telegrafistas, 18, Norte','664828542','09:00:00','20:00:00',37.419225,-5.968626),(8,1,2,'Idiomes BCN','Talleres de catalán y español','Rambla Catalunya 18','933001122','09:00:00','13:00:00',41.388946,2.166938),(9,2,2,'Centre Cultural Raval','Charlas y exposiciones culturales','Calle Hospital 22','933003344','11:00:00','20:00:00',41.380775,2.173661),(10,3,2,'Emplea Jove BCN','Orientación para empleo juvenil','Avenida Diagonal 120','933005566','08:00:00','14:00:00',41.394170,2.151200),(11,5,2,'Salut Migrant','Información sanitaria gratuita','Calle Pelayo 7','933007788','08:00:00','15:00:00',41.385064,2.168294),(12,4,2,'Centro de Apoyo Escolar Barcelona','Refuerzo académico y apoyo para familias migrantes','Gran Vía 99','933223344','16:00:00','20:00:00',41.410566,2.195869),(13,6,2,'Tràmits Fàcils','Asesoría sobre NIE y empadronamiento','Passeig de Gràcia 55','933009900','10:00:00','17:00:00',41.392235,2.165409),(14,7,2,'Defensa Legal Migrant','Asistencia jurídica para inmigrantes','Gran Via 101','933001234','12:00:00','19:00:00',41.410566,2.195869),(15,1,3,'Idiomas Madrid Centro','Clases para recién llegados','Calle Arenal 13','915001122','10:00:00','14:00:00',40.416775,-3.703790),(16,2,3,'Casa de Cultura Latina','Eventos multiculturales','Calle Embajadores 44','915003344','11:00:00','19:00:00',40.404674,-3.702560),(17,3,3,'Empleo Madrid Activa','Servicios para el empleo','Calle Princesa 77','915005566','09:00:00','15:00:00',40.426000,-3.714000),(18,6,3,'Punto de Información Administrativa','Asistencia para trámites legales en Madrid','Calle Alcalá 100','915432100','09:30:00','17:30:00',40.420000,-3.688000),(19,4,3,'Centro Escolar Madrid','Refuerzo escolar gratuito','Avenida de América 99','915007788','16:00:00','20:00:00',40.439000,-3.675000),(20,5,3,'Centro de Salud Este','Atención sanitaria gratuita','Calle Alcalde Sainz 12','915009900','08:00:00','16:00:00',40.410000,-3.670000),(21,7,3,'Jurídico Madrid Norte','Asesoría legal laboral','Paseo Castellana 234','915001234','10:00:00','18:00:00',40.465000,-3.688000),(22,2,4,'Museo Local Valencia','Talleres sobre historia local','Calle Historiador 6','961223344','10:00:00','18:00:00',39.470000,-0.376000),(23,1,4,'Centro de Idiomas Valencia','Cursos de español y valenciano','Calle Colón 22','963456789','09:00:00','17:00:00',39.470000,-0.375000),(24,4,4,'Colegio Abierto Valencia','Asesoramiento sobre educación','Calle Maestro 14','961334455','15:00:00','20:00:00',39.460000,-0.370000),(25,5,4,'Ambulatorio Central','Atención médica sin cita','Av. del Puerto 45','961445566','08:00:00','14:00:00',39.460000,-0.340000),(26,3,4,'Oficina Empleo Jove','Ayuda para encontrar trabajo joven en Valencia','Avinguda de les Corts 10','961112233','08:30:00','14:30:00',39.470000,-0.350000),(27,6,4,'Trámites Valencia','NIE, seguridad social y más','Calle San Vicente 90','961556677','09:00:00','17:00:00',39.470000,-0.380000),(28,7,4,'LegalVal','Orientación jurídica migratoria','Plaza Ayuntamiento 3','961667788','11:00:00','19:00:00',39.469900,-0.376288),(29,2,5,'Centro Cultural Compostela','Muestra de arte y cultura gallega','Rúa Nova 11','981223344','10:00:00','20:00:00',42.880000,-8.545000),(30,3,5,'Oficina Empleo Compostela','Ayuda laboral en Galicia','Rúa do Hórreo 30','981334455','09:00:00','15:00:00',42.880000,-8.540000),(31,4,5,'Escuela de Refuerzo','Apoyo educativo migrante','Rúa de San Pedro 18','981445566','16:00:00','19:00:00',42.880000,-8.540000),(32,1,5,'Aula de Idiomas Compostela','Clases gratuitas de gallego para inmigrantes','Rúa do Franco 14','981123456','10:00:00','18:00:00',42.880000,-8.545000),(33,5,5,'Centro Salud Zona Sur','Salud pública gratuita','Rúa das Galeras 27','981556677','08:00:00','14:00:00',42.880000,-8.550000),(34,6,5,'Oficina Trámites Compostela','Gestiones legales','Rúa do Vilar 3','981667788','09:30:00','16:30:00',42.880000,-8.545000),(35,7,5,'Asesoría Legal Galega','Ayuda jurídica para inmigrantes','Praza do Obradoiro 4','981778899','11:00:00','18:00:00',42.880000,-8.545000),(36,1,6,'Euskera para Todos','Cursos de euskera y castellano','Calle Gran Vía 12','944223344','09:00:00','13:00:00',43.262211,-2.929884),(37,3,6,'Lanpostu Bilbao','Talleres de inserción laboral','Avenida Lehendakari Aguirre 50','944334455','08:30:00','14:30:00',43.270000,-2.940000),(38,2,6,'Casa de Cultura Vasca','Eventos y talleres sobre cultura vasca','Calle Iparraguirre 45','944556677','11:00:00','20:00:00',43.262000,-2.938000),(39,4,6,'Educación Migrante','Ayuda escolar en euskera y español','Calle Zabalbide 33','944445566','16:00:00','20:00:00',43.254585,-2.912248),(40,5,6,'Centro Salud Miribilla','Centro médico accesible','Calle Miribilla 1','944556677','08:00:00','15:00:00',43.254658,-2.925853),(41,6,6,'Trámites Bizkaia','Asistencia a trámites legales','Calle Autonomía 44','944667788','09:00:00','17:00:00',43.256000,-2.938000),(42,7,6,'Asesoría Jurídica Bilbao','Orientación legal gratuita sobre inmigración y trabajo','Plaza Moyua 3','944112233','12:00:00','19:00:00',43.262542,-2.935483);
/*!40000 ALTER TABLE `recursolocal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temaforo`
--

DROP TABLE IF EXISTS `temaforo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `temaforo` (
  `temaID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `logo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`temaID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temaforo`
--

LOCK TABLES `temaforo` WRITE;
/*!40000 ALTER TABLE `temaforo` DISABLE KEYS */;
INSERT INTO `temaforo` VALUES (1,'Trámites-Documentación','Resuelve tus dudas sobre papeles, permisos y procesos administrativos.','bi bi-file-earmark-text'),(2,'Vivienda','Comparte y encuentra información sobre alquiler, compra y ayudas para vivienda','bi bi-house-door'),(3,'Empleo','Ofertas, consejos y experiencias sobre el mundo laboral.','bi bi-briefcase'),(4,'Educación','Información sobre estudios, homologaciones y formación.','bi bi-mortarboard'),(5,'Salud','Acceso a la sanidad, seguros y consejos para tu bienestar.','bi bi-heart-pulse'),(6,'Otros','Comparte inquietudes o preguntas que no se encuadren en las categorías anteriores.','bi bi-three-dots');
/*!40000 ALTER TABLE `temaforo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `usuarioID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `paisID` smallint unsigned NOT NULL,
  `ciudadID` smallint unsigned NOT NULL,
  `username` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `tipo` enum('recienllegado','voluntario') NOT NULL,
  PRIMARY KEY (`usuarioID`),
  UNIQUE KEY `email` (`email`),
  KEY `USUARIO_PAIS_FK` (`paisID`),
  KEY `USUARIO_CIUDAD_FK` (`ciudadID`),
  CONSTRAINT `USUARIO_CIUDAD_FK` FOREIGN KEY (`ciudadID`) REFERENCES `ciudad` (`ciudadID`),
  CONSTRAINT `USUARIO_PAIS_FK` FOREIGN KEY (`paisID`) REFERENCES `pais` (`paisID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,1,'user123','Juan','García','juan@gmail.com','pass123','voluntario'),(2,2,1,'user456','María','López','maria@hotmail.com','pass456','recienllegado'),(3,1,1,'d','d','d','d@gmail','$2a$10$8kcvNcV0ygyJ7WlApHmAku6.rxwjdjo07yMonaWrN5A8Ms1ILH2r6','voluntario'),(4,3,3,'a','a','a','asdz@gmail','$2a$10$BlgvtTQA5cdoJANAJRcSzu31dimhmhkfgvky/lqa1LMP1EyTSwyl.','voluntario'),(5,1,4,'q','q','q','q@gmail','$2a$10$ZlpeJzJEthmFeSLdMPj6iuUUE6IIui0jy9hCVSWFSCQtdegeaoRKG','recienllegado');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_apuntarse_evento`
--

DROP TABLE IF EXISTS `usuario_apuntarse_evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_apuntarse_evento` (
  `eventoID` smallint unsigned NOT NULL,
  `usuarioID` smallint unsigned NOT NULL,
  `fecha_inscripcion` date DEFAULT NULL,
  PRIMARY KEY (`eventoID`,`usuarioID`),
  KEY `APUNTARSE_USUARIO_FK` (`usuarioID`),
  CONSTRAINT `APUNTARSE_EVENTOLOCAL_FK` FOREIGN KEY (`eventoID`) REFERENCES `eventolocal` (`eventoID`),
  CONSTRAINT `APUNTARSE_USUARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_apuntarse_evento`
--

LOCK TABLES `usuario_apuntarse_evento` WRITE;
/*!40000 ALTER TABLE `usuario_apuntarse_evento` DISABLE KEYS */;
INSERT INTO `usuario_apuntarse_evento` VALUES (3,3,NULL);
/*!40000 ALTER TABLE `usuario_apuntarse_evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voluntario`
--

DROP TABLE IF EXISTS `voluntario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voluntario` (
  `usuarioID` smallint unsigned NOT NULL,
  `habilidades` varchar(50) DEFAULT NULL,
  `motivacion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`usuarioID`),
  CONSTRAINT `VOLUNTARIO_USUARIO_FK` FOREIGN KEY (`usuarioID`) REFERENCES `usuario` (`usuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voluntario`
--

LOCK TABLES `voluntario` WRITE;
/*!40000 ALTER TABLE `voluntario` DISABLE KEYS */;
INSERT INTO `voluntario` VALUES (1,'Traducción inglés-español','Ayudar a la integración cultural'),(3,'d','d'),(4,'a','a');
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

-- Dump completed on 2025-06-11 22:05:23
