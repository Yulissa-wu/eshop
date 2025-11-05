-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: localhost    Database: eshop
-- ------------------------------------------------------
-- Server version	8.0.43-0ubuntu0.22.04.2

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKioh3c0mo0al2kswtnk5r09y7f` (`customer_id`),
  CONSTRAINT `FKioh3c0mo0al2kswtnk5r09y7f` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (2,'2025-10-31 08:17:14.624097','2025-11-01 10:48:43.403611',1);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `added_at` datetime(6) NOT NULL,
  `quantity` int NOT NULL,
  `cart_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK99e0am9jpriwxcm6is7xfedy3` (`cart_id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FK99e0am9jpriwxcm6is7xfedy3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (21,'2025-11-04 08:58:08.489901',1,2,2);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cate_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'手機'),(2,'筆記本電腦'),(3,'運動鞋');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrh1g1a20omjmn6kurd35o3eit` (`user_id`),
  CONSTRAINT `FKrh1g1a20omjmn6kurd35o3eit` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'0912345678',1),(2,'0987654321',2);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (2,'John Doe','IT');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ord_num` int NOT NULL,
  `prod_num` int NOT NULL,
  `ord_qty` int NOT NULL DEFAULT '1',
  `ord_price` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) GENERATED ALWAYS AS ((`ord_qty` * `ord_price`)) STORED,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `detail_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ord_num` (`ord_num`),
  KEY `idx_prod_num` (`prod_num`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`ord_num`) REFERENCES `orders` (`ord_num`) ON DELETE CASCADE,
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`prod_num`) REFERENCES `products` (`prod_num`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` (`id`, `ord_num`, `prod_num`, `ord_qty`, `ord_price`, `created_at`, `detail_id`) VALUES (1,1001,1,1,35900.00,'2025-10-25 04:30:53',0),(2,1002,2,1,37900.00,'2025-10-25 04:30:53',0);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `order_items_view`
--

DROP TABLE IF EXISTS `order_items_view`;
/*!50001 DROP VIEW IF EXISTS `order_items_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `order_items_view` AS SELECT 
 1 AS `id`,
 1 AS `ord_num`,
 1 AS `prod_num`,
 1 AS `prod_name`,
 1 AS `prod_type`,
 1 AS `prod_line`,
 1 AS `ord_qty`,
 1 AS `ord_price`,
 1 AS `subtotal`,
 1 AS `created_at`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ord_num` int NOT NULL,
  `cust_num` int NOT NULL,
  `order_date` date NOT NULL,
  `county` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('pending','processing','shipped','delivered','cancelled') COLLATE utf8mb4_unicode_ci DEFAULT 'pending',
  `total_amount` decimal(10,2) DEFAULT '0.00',
  `shipping_fee` decimal(10,2) DEFAULT '0.00',
  `payment_method` enum('cash','credit_card','bank_transfer') COLLATE utf8mb4_unicode_ci DEFAULT 'cash',
  `payment_status` enum('unpaid','paid','refunded') COLLATE utf8mb4_unicode_ci DEFAULT 'unpaid',
  `shipping_address` text COLLATE utf8mb4_unicode_ci,
  `notes` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ord_num` (`ord_num`),
  KEY `idx_ord_num` (`ord_num`),
  KEY `idx_cust_num` (`cust_num`),
  KEY `idx_order_date` (`order_date`),
  KEY `idx_status` (`status`),
  KEY `idx_payment_status` (`payment_status`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1001,2,'2024-01-15','台北市','delivered',35900.00,0.00,'credit_card','paid','台北市信義區信義路五段7號',NULL,'2025-10-25 04:30:53','2025-10-25 04:30:53'),(2,1002,3,'2024-01-16','新北市','processing',37900.00,0.00,'bank_transfer','paid','新北市板橋區文化路一段188號',NULL,'2025-10-25 04:30:53','2025-10-25 04:30:53');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `product_details`
--

DROP TABLE IF EXISTS `product_details`;
/*!50001 DROP VIEW IF EXISTS `product_details`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `product_details` AS SELECT 
 1 AS `id`,
 1 AS `prod_num`,
 1 AS `prod_name`,
 1 AS `prod_type`,
 1 AS `prod_line`,
 1 AS `prod_price`,
 1 AS `description`,
 1 AS `stock_quantity`,
 1 AS `status`,
 1 AS `weight`,
 1 AS `dimensions`,
 1 AS `brand`,
 1 AS `sku`,
 1 AS `tags`,
 1 AS `is_featured`,
 1 AS `sort_order`,
 1 AS `created_at`,
 1 AS `updated_at`,
 1 AS `main_image`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `prod_num` int NOT NULL,
  `image_path` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image_description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sort_order` int DEFAULT '0',
  `is_main` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_prod_num` (`prod_num`),
  KEY `idx_sort_order` (`sort_order`),
  KEY `idx_is_main` (`is_main`),
  CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`prod_num`) REFERENCES `products` (`prod_num`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `prod_num` int NOT NULL,
  `prod_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prod_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prod_line` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prod_price` int NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `stock_quantity` int DEFAULT '0',
  `main_image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` enum('active','inactive','draft') COLLATE utf8mb4_unicode_ci DEFAULT 'draft',
  `weight` decimal(8,2) DEFAULT NULL,
  `dimensions` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `brand` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sku` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tags` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_featured` tinyint(1) DEFAULT '0',
  `sort_order` int DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `img_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prod_image` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `prod_num` (`prod_num`),
  UNIQUE KEY `sku` (`sku`),
  KEY `idx_prod_num` (`prod_num`),
  KEY `idx_prod_type` (`prod_type`),
  KEY `idx_prod_line` (`prod_line`),
  KEY `idx_prod_price` (`prod_price`),
  KEY `idx_status` (`status`),
  KEY `idx_is_featured` (`is_featured`),
  KEY `idx_sku` (`sku`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,'iPhone 15 Pro','手機','Apple',35900,'最新款iPhone，搭載A17 Pro晶片',50,NULL,'active',NULL,NULL,NULL,NULL,NULL,0,0,'2025-10-25 04:30:53','2025-10-30 15:14:42','','https://www.citypng.com/public/uploads/preview/iphone-15-pro-max-all-colors-hd-png-116949841795tz5ootrjo.png',1),(2,2,'MacBook Air M2','筆記本電腦','Apple',37900,'輕薄便攜的MacBook Air',30,NULL,'active',NULL,NULL,NULL,NULL,NULL,0,0,'2025-10-25 04:30:53','2025-10-25 04:30:53','','0',0),(3,3,'Samsung Galaxy S24','手機','Samsung',28900,'Samsung旗艦手機',40,NULL,'active',NULL,NULL,NULL,NULL,NULL,0,0,'2025-10-25 04:30:53','2025-10-25 04:30:53','','0',0),(4,4,'Dell XPS 13','筆記本電腦','Dell',45900,'高階商務筆記本電腦',25,NULL,'active',NULL,NULL,NULL,NULL,NULL,0,0,'2025-10-25 04:30:53','2025-10-25 04:30:53','','0',0),(5,5,'Nike Air Max','運動鞋','Nike',4500,'經典運動鞋款',100,NULL,'active',NULL,NULL,NULL,NULL,NULL,0,0,'2025-10-25 04:30:53','2025-10-25 04:30:53','','0',0),(6,6,'Adidas Ultraboost','運動鞋','Adidas',5200,'專業跑步鞋',80,NULL,'active',NULL,NULL,NULL,NULL,NULL,0,0,'2025-10-25 04:30:53','2025-10-25 04:30:53','','0',0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'dsds@dsa.csas','sdds','abc123'),(2,'asdsda@sdads.gf','esds','abc123'),(3,'sdds@dsdsil.com','sdssa','dsssdWDDW1234#$%');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `order_items_view`
--

/*!50001 DROP VIEW IF EXISTS `order_items_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`wsq`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `order_items_view` AS select `od`.`id` AS `id`,`od`.`ord_num` AS `ord_num`,`od`.`prod_num` AS `prod_num`,`p`.`prod_name` AS `prod_name`,`p`.`prod_type` AS `prod_type`,`p`.`prod_line` AS `prod_line`,`od`.`ord_qty` AS `ord_qty`,`od`.`ord_price` AS `ord_price`,`od`.`subtotal` AS `subtotal`,`od`.`created_at` AS `created_at` from (`order_detail` `od` left join `products` `p` on((`od`.`prod_num` = `p`.`prod_num`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `product_details`
--

/*!50001 DROP VIEW IF EXISTS `product_details`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`wsq`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `product_details` AS select `p`.`id` AS `id`,`p`.`prod_num` AS `prod_num`,`p`.`prod_name` AS `prod_name`,`p`.`prod_type` AS `prod_type`,`p`.`prod_line` AS `prod_line`,`p`.`prod_price` AS `prod_price`,`p`.`description` AS `description`,`p`.`stock_quantity` AS `stock_quantity`,`p`.`status` AS `status`,`p`.`weight` AS `weight`,`p`.`dimensions` AS `dimensions`,`p`.`brand` AS `brand`,`p`.`sku` AS `sku`,`p`.`tags` AS `tags`,`p`.`is_featured` AS `is_featured`,`p`.`sort_order` AS `sort_order`,`p`.`created_at` AS `created_at`,`p`.`updated_at` AS `updated_at`,(select `product_images`.`image_path` from `product_images` where ((`product_images`.`prod_num` = `p`.`prod_num`) and (`product_images`.`is_main` = true)) limit 1) AS `main_image` from `products` `p` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-05 10:36:43
