-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 30, 2015 at 06:52 PM
-- Server version: 5.6.21
-- PHP Version: 5.5.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `alpha`
--

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
`id` bigint(20) NOT NULL,
  `feedid` bigint(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`id`, `feedid`, `email`, `content`, `createdate`) VALUES
(1, 83, 'test1@gmail.com', 'I like this post', '2015-03-18 14:17:06'),
(2, 83, 'thangai@gmail.com', 'me too', '2015-03-23 20:13:59'),
(3, 83, 'mekong89@gmail.com', 'thanks all', '2015-03-23 20:22:09'),
(4, 83, 'mekong89@gmail.com', 'it work OK now', '2015-03-23 20:24:06'),
(5, 77, 'mekong89@gmail.com', 'I love you', '2015-03-23 22:31:06'),
(6, 100, 'test3@gmail.com', 'great', '2015-03-23 23:08:01'),
(7, 79, 'test3@gmail.com', 'great', '2015-03-23 23:09:03'),
(8, 80, 'mekong89@gmail.com', 'hi therre', '2015-03-25 09:55:40'),
(9, 79, 'mekong89@gmail.com', 'comments', '2015-03-25 10:01:56'),
(10, 80, 'mekong89@gmail.com', 'go up', '2015-03-25 10:02:19'),
(11, 80, 'test3@gmail.com', 'walk you home', '2015-03-25 12:13:37'),
(12, 80, 'test3@gmail.com', 'ok?', '2015-03-25 12:14:18'),
(13, 85, 'test3@gmail.com', 'fhhgg', '2015-03-25 12:17:35'),
(14, 80, 'test3@gmail.com', 'what sup', '2015-03-25 14:19:11'),
(15, 105, 'test3@gmail.com', 'like', '2015-03-25 14:20:59');

-- --------------------------------------------------------

--
-- Table structure for table `feed`
--

CREATE TABLE IF NOT EXISTS `feed` (
  `NoLike` int(11) NOT NULL DEFAULT '0',
`feedID` int(11) NOT NULL,
  `feedowner` varchar(50) NOT NULL,
  `feedcontent` varchar(5000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `feedtimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `whocansee` int(11) NOT NULL DEFAULT '0',
  `imagename` text NOT NULL,
  `lastupdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feed`
--

INSERT INTO `feed` (`NoLike`, `feedID`, `feedowner`, `feedcontent`, `feedtimestamp`, `whocansee`, `imagename`, `lastupdate`) VALUES
(0, 59, 'mekong89@gmail.com', 'new post', '2015-02-10 13:11:51', 0, '', '2015-03-15 11:01:16'),
(0, 60, 'mekong89@gmail.com', 'hi', '2015-02-10 13:12:40', 0, '', '2015-03-15 11:01:31'),
(0, 61, 'mekong89@gmail.com', 'gjuy', '2015-02-10 13:14:44', 0, '', '2015-03-15 11:01:28'),
(0, 62, 'mekong89@gmail.com', 'posttt', '2015-02-10 13:17:23', 0, '', '2015-03-15 11:01:34'),
(0, 63, 'mekong89@gmail.com', 'hehe', '2015-02-10 13:19:58', 0, '', '2015-03-15 11:01:37'),
(0, 64, 'test1@gmail.com', 'new post', '2015-02-10 13:27:33', 0, '', '2015-03-15 11:01:40'),
(0, 65, 'mekong89@gmail.com', 'nice day', '2015-02-10 13:27:36', 0, '', '2015-03-15 11:01:43'),
(0, 66, 'mekong89@gmail.com', 'i post from hell', '2015-03-15 06:58:14', 0, '', '2015-03-15 11:01:46'),
(0, 67, 'mekong89@gmail.com', 'hi there', '2015-03-15 07:28:40', 0, '', '2015-03-15 11:01:49'),
(0, 68, 'mekong89@gmail.com', 'abc', '2015-03-15 07:30:06', 0, '', '2015-03-15 11:01:54'),
(0, 69, 'mekong89@gmail.com', 'hallo', '2015-03-15 07:39:47', 0, '', '2015-03-15 11:01:56'),
(0, 70, 'mekong89@gmail.com', 'yeah, that what i want', '2015-03-15 07:42:10', 0, '', '2015-03-15 11:01:59'),
(0, 71, 'mekong89@gmail.com', 'post new', '2015-03-15 07:43:16', 0, '', '2015-03-15 11:02:01'),
(0, 72, 'mekong89@gmail.com', 'new post', '2015-03-15 07:44:08', 0, '', '2015-03-15 11:02:04'),
(0, 73, 'mekong89@gmail.com', 'last post', '2015-03-15 07:44:16', 0, '', '2015-03-15 11:02:07'),
(0, 74, 'mekong89@gmail.com', 'Nice Sunday', '2015-03-15 09:41:13', 0, '', '2015-03-15 11:02:10'),
(0, 75, 'mekong89@gmail.com', 'heheheh hiye bjshus hjdiyd ghjusghd ', '2015-03-15 10:21:37', 0, '', '2015-03-15 11:02:12'),
(0, 76, 'mekong89@gmail.com', 'bdhhjd\nhdjdgh\nndihdb\nnfoydb', '2015-03-15 10:21:46', 0, '', '2015-03-15 11:02:15'),
(0, 77, 'mekong89@gmail.com', 'g\nh\nj\nk\nl', '2015-03-15 10:21:54', 0, '', '2015-03-15 11:02:24'),
(1, 78, 'mekong89@gmail.com', '1\nw\ne\nr\nt\ny\nujjfnjf\nndih\nudhh\nhdiudb\nbidb\noojw', '2015-03-15 10:24:23', 0, '', '2015-03-15 11:02:29'),
(0, 79, 'mekong89@gmail.com', 'image post', '2015-03-15 10:32:09', 0, 'post79.png', '2015-03-25 10:01:56'),
(0, 80, 'thangai@gmail.com', 'Test follow button', '2015-03-15 10:49:51', 0, '', '2015-03-25 14:19:11'),
(0, 81, 'mekong89@gmail.com', 'Geez', '2015-03-15 10:56:43', 0, '', '2015-03-15 11:02:31'),
(0, 82, 'mekong89@gmail.com', 'follow is next', '2015-03-15 11:15:34', 0, '', '2015-03-15 12:22:08'),
(0, 83, 'mekong89@gmail.com', 'I have some good news', '2015-03-15 11:18:46', 0, '', '2015-03-15 12:22:11'),
(0, 84, 'mekong89@gmail.com', 'gdgrv', '2015-03-15 11:20:28', 0, '', '2015-03-15 12:23:57'),
(0, 85, 'mekong89@gmail.com', 'fggff', '2015-03-15 11:21:06', 0, '', '2015-03-25 12:17:36'),
(0, 86, 'mekong89@gmail.com', 'nice', '2015-03-15 11:21:53', 0, '', '2015-03-15 12:24:15'),
(0, 87, 'mekong89@gmail.com', ' adad', '2015-03-15 11:22:25', 0, '', '2015-03-15 12:24:12'),
(0, 88, 'mekong89@gmail.com', 'hehe', '2015-03-15 11:39:23', 0, '', '2015-03-15 12:24:09'),
(0, 89, 'test3@gmail.com', 'hi', '2015-03-15 11:40:20', 0, '', '2015-03-15 12:24:06'),
(0, 90, 'test3@gmail.com', 'nice', '2015-03-15 11:41:32', 0, '', '2015-03-15 12:24:03'),
(0, 91, 'mekong89@gmail.com', '(y)', '2015-03-15 11:41:58', 0, '', '2015-03-15 12:24:00'),
(0, 92, 'test3@gmail.com', ' fgfi', '2015-03-15 13:51:54', 0, '', '2015-03-15 13:51:54'),
(0, 93, 'test3@gmail.com', 'test this', '2015-03-15 13:54:42', 0, '', '2015-03-15 13:54:42'),
(0, 94, 'mekong89@gmail.com', '/we', '2015-03-15 13:55:39', 0, '', '2015-03-15 13:55:39'),
(0, 95, 'test3@gmail.com', 'hio', '2015-03-18 13:35:37', 0, '', '2015-03-18 13:35:37'),
(0, 96, 'mekong89@gmail.com', 'this is cool ??', '2015-03-21 13:21:25', 0, '', '2015-03-21 13:21:25'),
(0, 97, 'test3@gmail.com', 'haha', '2015-03-21 14:53:13', 0, '', '2015-03-21 14:53:13'),
(0, 98, 'mekong89@gmail.com', 'ta post', '2015-03-23 22:07:36', 0, '', '2015-03-23 22:07:36'),
(0, 99, 'mekong89@gmail.com', 'hi', '2015-03-23 22:09:41', 0, '', '2015-03-23 22:09:41'),
(0, 100, 'mekong89@gmail.com', 'hiu', '2015-03-23 22:09:53', 0, '', '2015-03-23 22:09:53'),
(0, 101, 'test1@gmail.com', 'test social', '2015-03-25 09:54:42', 0, '', '2015-03-25 09:54:42'),
(0, 109, 'mekong89@gmail.com', 'uiy', '2015-03-25 14:48:23', 0, '', '2015-03-25 14:48:23'),
(0, 110, 'mekong89@gmail.com', 'iou', '2015-03-25 15:02:30', 0, '', '2015-03-25 15:02:30'),
(0, 111, 'mekong89@gmail.com', 'dfgt', '2015-03-25 15:07:07', 0, '', '2015-03-25 15:07:07'),
(0, 112, 'mekong89@gmail.com', 'dfgt', '2015-03-25 15:07:57', 0, '', '2015-03-25 15:07:57'),
(0, 113, 'mekong89@gmail.com', 'ufcb', '2015-03-25 15:09:34', 0, '', '2015-03-25 15:09:34'),
(0, 114, 'mekong89@gmail.com', 'fjbnj', '2015-03-25 15:10:48', 0, '114.jpg', '2015-03-25 15:10:49'),
(0, 115, 'test3@gmail.com', 'hege', '2015-03-25 15:13:03', 0, '115.jpg', '2015-03-25 15:13:03');

-- --------------------------------------------------------

--
-- Table structure for table `follow`
--

CREATE TABLE IF NOT EXISTS `follow` (
`id` int(11) NOT NULL,
  `user1` varchar(100) NOT NULL,
  `user2` varchar(100) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `follow`
--

INSERT INTO `follow` (`id`, `user1`, `user2`, `timestamp`) VALUES
(7, 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-25 09:55:09'),
(8, 'test3@gmail.com', 'thangai@gmail.com', '2015-03-25 11:59:50'),
(11, 'test3@gmail.com', 'test1@gmail.com', '2015-03-25 12:08:00'),
(15, 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 12:50:05'),
(16, 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-28 08:38:05');

-- --------------------------------------------------------

--
-- Table structure for table `friend`
--

CREATE TABLE IF NOT EXISTS `friend` (
`id` bigint(20) NOT NULL,
  `user1` varchar(100) NOT NULL,
  `user2` varchar(100) NOT NULL,
  `isaccept` tinyint(1) NOT NULL DEFAULT '0',
  `lastupdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isreaded` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `friend`
--

INSERT INTO `friend` (`id`, `user1`, `user2`, `isaccept`, `lastupdate`, `isreaded`) VALUES
(12, 'mekong89@gmail.com', 'thangai@gmail.com', 1, '2015-03-25 12:23:00', 0),
(15, 'test3@gmail.com', 'thangai@gmail.com', 1, '2015-03-25 12:20:20', 0),
(17, 'test3@gmail.com', 'mekong89@gmail.com', 0, '2015-03-25 14:25:39', 0);

-- --------------------------------------------------------

--
-- Table structure for table `gps`
--

CREATE TABLE IF NOT EXISTS `gps` (
  `email` varchar(50) NOT NULL,
  `loc_long` decimal(8,5) NOT NULL,
  `loc_lat` decimal(8,5) NOT NULL,
  `last_online` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gps`
--

INSERT INTO `gps` (`email`, `loc_long`, `loc_lat`, `last_online`) VALUES
('mekong89@gmail.com', '106.64156', '10.80040', '2014-07-08 05:35:01'),
('test1@gmail.com', '106.76890', '10.84976', '2014-07-29 13:55:08'),
('test2@gmail.com', '106.75515', '10.85263', '2014-09-06 10:02:05'),
('test3@gmail.com', '106.70000', '10.80000', '2014-07-24 22:20:09'),
('thang.ai@gmail.com', '106.64163', '10.80033', '2014-07-08 05:35:22');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
`id` bigint(20) unsigned NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sender` varchar(100) NOT NULL,
  `receiver` varchar(100) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `msgstate` varchar(10) NOT NULL DEFAULT 'uploaded',
  `lastupdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `content`, `sender`, `receiver`, `timestamp`, `msgstate`, `lastupdate`) VALUES
(57, 'test new', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 00:03:00', 'readed', '2015-03-15 00:05:11'),
(58, 'receive', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 00:06:03', 'readed', '2015-03-15 00:06:03'),
(59, 'hi', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 00:17:21', 'readed', '2015-03-15 00:19:23'),
(60, 'hula', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 00:19:43', 'readed', '2015-03-15 00:19:45'),
(61, 'hi', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 00:19:56', 'readed', '2015-03-15 00:19:56'),
(62, 'hurry ', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 00:20:15', 'readed', '2015-03-15 00:20:17'),
(63, 'haha', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 00:20:34', 'readed', '2015-03-15 00:20:34'),
(64, 'he', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 00:20:50', 'readed', '2015-03-15 00:20:51'),
(65, 'hiu', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 00:22:22', 'readed', '2015-03-15 00:22:23'),
(66, 'hi you', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 00:29:37', 'readed', '2015-03-15 00:29:55'),
(67, 'hi', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 00:30:00', 'readed', '2015-03-15 00:30:01'),
(68, 'hello', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 00:30:07', 'readed', '2015-03-15 00:30:07'),
(69, 'haha', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 00:30:15', 'readed', '2015-03-15 00:30:15'),
(70, 'defr', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 00:30:20', 'readed', '2015-03-15 00:30:22'),
(71, 'hi there', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 00:40:01', 'readed', '2015-03-15 01:35:49'),
(72, 'dgh', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 01:16:41', 'readed', '2015-03-15 01:35:49'),
(73, 'hulla', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 01:36:32', 'readed', '2015-03-15 01:38:47'),
(74, 'hello', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 01:39:18', 'readed', '2015-03-15 01:39:50'),
(75, 'hey', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 01:48:19', 'readed', '2015-03-15 01:48:36'),
(76, 'hallo', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 01:48:43', 'readed', '2015-03-15 01:48:43'),
(77, 'henrry', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 01:48:50', 'readed', '2015-03-15 01:56:13'),
(78, 'oki', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 01:49:04', 'readed', '2015-03-15 01:56:13'),
(79, 'hi', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 01:56:20', 'readed', '2015-03-15 02:31:48'),
(80, 'hey', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:24:10', 'readed', '2015-03-15 02:25:25'),
(81, 'are you there', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:24:25', 'readed', '2015-03-15 02:25:25'),
(82, 'how are you', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:24:35', 'readed', '2015-03-15 02:25:25'),
(83, 'slow', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:24:51', 'readed', '2015-03-15 02:25:25'),
(84, 'hi there', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:25:32', 'readed', '2015-03-15 02:31:49'),
(85, 'hulo', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:26:12', 'readed', '2015-03-15 02:26:13'),
(86, 'hey', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:26:39', 'readed', '2015-03-15 02:31:49'),
(87, 'crazy', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:26:54', 'readed', '2015-03-15 02:31:49'),
(88, 'why dont you respond', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:27:19', 'readed', '2015-03-15 02:31:49'),
(89, 'hula', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:27:37', 'readed', '2015-03-15 02:31:49'),
(90, 'hu', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:28:52', 'readed', '2015-03-15 02:31:50'),
(91, 'ghoigx', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:28:55', 'readed', '2015-03-15 02:31:50'),
(92, 'gsrtcu', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:28:59', 'readed', '2015-03-15 02:31:50'),
(93, 'ghutf', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:29:25', 'readed', '2015-03-15 02:29:26'),
(94, 'vhytffgg', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:29:41', 'readed', '2015-03-15 02:31:50'),
(95, 'jgzc', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:29:47', 'readed', '2015-03-15 02:29:48'),
(96, 'xgxgxhxh', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:29:59', 'readed', '2015-03-15 02:31:50'),
(97, 'huro', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:31:54', 'readed', '2015-03-15 02:32:09'),
(98, 'bhggyr', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:32:13', 'readed', '2015-03-15 02:32:14'),
(99, 'ucudus\n', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:32:25', 'readed', '2015-03-15 02:32:40'),
(100, 'shit', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:32:51', 'readed', '2015-03-15 02:32:51'),
(101, ' vdhb', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:33:20', 'readed', '2015-03-15 02:33:21'),
(102, 'cdgbhu', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:33:25', 'readed', '2015-03-15 02:34:59'),
(103, 'fyszv', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:33:37', 'readed', '2015-03-15 02:34:59'),
(104, 'cjgszgitg', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:33:41', 'readed', '2015-03-15 02:34:59'),
(105, 'ffshbfu', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:33:45', 'readed', '2015-03-15 02:34:59'),
(106, 'cjjgfx', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:33:49', 'readed', '2015-03-15 02:35:00'),
(107, 'bhfdhjhtd', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:33:53', 'readed', '2015-03-15 02:35:00'),
(108, 'vgtdxbjt', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:33:57', 'readed', '2015-03-15 02:35:00'),
(109, 'rdykjhftv', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:01', 'readed', '2015-03-15 02:35:00'),
(110, 'bcdthgrv', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:05', 'readed', '2015-03-15 02:35:00'),
(111, 'kbcdtvffu', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:08', 'readed', '2015-03-15 02:35:00'),
(112, 'vyrsxhju', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:11', 'readed', '2015-03-15 02:35:00'),
(113, 'cdeyvg', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:15', 'readed', '2015-03-15 02:35:01'),
(114, 'gruhtdcvh', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:18', 'readed', '2015-03-15 02:35:01'),
(115, 'czwuivrrgh', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:22', 'readed', '2015-03-15 02:35:01'),
(116, 'jurtcfhxrg', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:26', 'readed', '2015-03-15 02:35:01'),
(117, 'vhyexchfrib', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:30', 'readed', '2015-03-15 02:35:01'),
(118, 'swayvhrygxj', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:33', 'readed', '2015-03-15 02:35:01'),
(119, 'ghtcc', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:41', 'readed', '2015-03-15 02:35:01'),
(120, 'vdrycddygfdyb', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:45', 'readed', '2015-03-15 02:35:02'),
(121, 'vhtdxgjgx', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:34:50', 'readed', '2015-03-15 02:35:02'),
(122, 'cgd', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 02:35:27', 'readed', '2015-03-15 02:35:27'),
(123, 'nh', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 02:35:40', 'readed', '2015-03-15 08:00:05'),
(124, 'aguy', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 07:53:46', 'readed', '2015-03-15 09:39:46'),
(125, 'hi mekong', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:36:03', 'readed', '2015-03-15 09:36:23'),
(126, 'hi', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:36:29', 'readed', '2015-03-15 09:36:30'),
(127, 'hi', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:36:39', 'readed', '2015-03-15 09:36:51'),
(128, 'hi there', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:37:09', 'readed', '2015-03-15 09:37:25'),
(129, 'hi the\n', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:37:37', 'readed', '2015-03-15 09:37:37'),
(130, 'xgtrvg', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-15 09:37:41', 'readed', '2015-03-15 09:37:42'),
(131, 'bgrfsdfv', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-15 09:37:45', 'readed', '2015-03-15 09:37:46'),
(132, 'for\n', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:37:55', 'readed', '2015-03-15 09:37:55'),
(133, 'vhfdh', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:38:00', 'readed', '2015-03-15 09:38:00'),
(134, 'vhydeftyhhhfcv', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-15 09:38:03', 'readed', '2015-03-15 09:38:04'),
(135, 'hdhd', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-15 09:38:12', 'readed', '2015-03-15 09:38:21'),
(136, 'test', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:38:25', 'readed', '2015-03-15 09:38:26'),
(137, 'hdsgurn', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-15 09:38:34', 'readed', '2015-03-15 09:39:04'),
(138, 'hehei', 'mekong89@gmail.com', 'test1@gmail.com', '2015-03-15 09:39:22', 'readed', '2015-03-15 09:39:47'),
(139, 'hihi', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:39:56', 'readed', '2015-03-15 09:39:56'),
(140, 'huhu', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:40:00', 'readed', '2015-03-15 09:40:00'),
(141, 'bla bla bla', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:40:25', 'readed', '2015-03-15 09:40:27'),
(142, 'bdjdh\nhdhgd\nhdudh\nhdggd', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:40:56', 'readed', '2015-03-15 09:41:05'),
(143, 'bshgd', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:41:13', 'readed', '2015-03-15 09:41:22'),
(144, 'hsjbdhdn', 'test1@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:41:43', 'readed', '2015-03-15 09:41:44'),
(145, ' nice', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:42:05', 'readed', '2015-03-15 09:42:14'),
(146, 'test', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:42:24', 'readed', '2015-03-15 09:42:24'),
(147, 'haha', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:43:32', 'readed', '2015-03-15 10:55:43'),
(148, 'test', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 09:49:24', 'readed', '2015-03-15 10:55:43'),
(149, 'gee', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-15 10:56:09', 'readed', '2015-03-15 10:56:19'),
(150, 'hey', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-15 11:23:07', 'readed', '2015-03-15 11:23:19'),
(151, 'follow', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 11:23:27', 'readed', '2015-03-15 11:23:28'),
(152, 'I think you can', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 11:29:50', 'readed', '2015-03-15 13:57:59'),
(153, 'follow me', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 11:30:32', 'readed', '2015-03-15 13:58:00'),
(154, 'all in one\n??', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-15 13:58:14', 'readed', '2015-03-15 13:59:03'),
(155, 'hallo\n??', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-15 13:58:44', 'readed', '2015-03-15 13:59:03'),
(156, 'follow', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 13:59:34', 'readed', '2015-03-15 13:59:40'),
(157, 'hey', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-15 15:01:06', 'readed', '2015-03-18 11:49:53'),
(158, 'hi', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-18 13:17:19', 'readed', '2015-03-18 13:17:38'),
(159, 'hfj', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-21 13:24:48', 'readed', '2015-03-21 14:53:59'),
(160, 'di choi k', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-21 14:53:39', 'readed', '2015-03-21 14:53:59'),
(161, 'hi', 'mekong89@gmail.com', 'thangai@gmail.com', '2015-03-24 21:44:51', 'uploaded', '2015-03-24 21:44:51'),
(162, 'hey', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 12:01:33', 'readed', '2015-03-25 12:17:59'),
(163, 'he', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 12:02:20', 'readed', '2015-03-25 12:18:00'),
(164, '', 'test3@gmail.com', 'thangai@gmail.com', '2015-03-25 12:11:24', 'uploaded', '2015-03-25 12:11:24'),
(165, 'nice pix', 'test3@gmail.com', 'thangai@gmail.com', '2015-03-25 12:11:37', 'uploaded', '2015-03-25 12:11:37'),
(166, 'wherr are you now?', 'test3@gmail.com', 'thangai@gmail.com', '2015-03-25 12:11:54', 'uploaded', '2015-03-25 12:11:54'),
(167, '', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 12:20:01', 'readed', '2015-03-25 14:25:50'),
(168, 'are u there??', 'test3@gmail.com', 'thangai@gmail.com', '2015-03-25 12:33:11', 'uploaded', '2015-03-25 12:33:11'),
(169, '', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 12:33:49', 'readed', '2015-03-25 14:25:50'),
(170, 'nice pux', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 13:41:07', 'readed', '2015-03-25 14:25:50'),
(171, 'hi', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 13:42:56', 'readed', '2015-03-25 14:25:50'),
(172, 'are u around?', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 14:21:54', 'readed', '2015-03-25 14:25:51'),
(173, 'see u now', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-25 14:26:16', 'readed', '2015-03-25 14:26:33'),
(174, ':)', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 14:26:43', 'readed', '2015-03-25 14:26:43'),
(175, 'coool', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-25 14:27:45', 'readed', '2015-03-25 14:27:45'),
(176, 'are u free tonight', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-25 14:28:06', 'readed', '2015-03-25 14:28:12'),
(177, 'yea', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 14:28:17', 'readed', '2015-03-25 14:28:18'),
(178, 'I will come by your place', 'mekong89@gmail.com', 'test3@gmail.com', '2015-03-25 14:29:40', 'readed', '2015-03-25 14:29:51'),
(179, 'ok', 'test3@gmail.com', 'mekong89@gmail.com', '2015-03-25 14:30:02', 'readed', '2015-03-25 14:30:18');

-- --------------------------------------------------------

--
-- Table structure for table `tblike`
--

CREATE TABLE IF NOT EXISTS `tblike` (
`likeid` int(11) NOT NULL,
  `feedid` int(11) NOT NULL,
  `email` varchar(60) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblike`
--

INSERT INTO `tblike` (`likeid`, `feedid`, `email`) VALUES
(26, 93, 'test3@gmail.com'),
(29, 91, 'test3@gmail.com'),
(33, 95, 'test3@gmail.com'),
(34, 96, 'test3@gmail.com'),
(39, 97, 'test3@gmail.com'),
(41, 95, 'mekong89@gmail.com'),
(42, 96, 'mekong89@gmail.com'),
(44, 93, 'mekong89@gmail.com'),
(46, 91, 'mekong89@gmail.com'),
(59, 97, 'mekong89@gmail.com'),
(60, 82, 'mekong89@gmail.com'),
(63, 78, 'mekong89@gmail.com'),
(68, 105, 'test3@gmail.com'),
(69, 85, 'test3@gmail.com'),
(70, 106, 'mekong89@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `email` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `image` varchar(50) NOT NULL,
  `birthday` date NOT NULL,
  `cellphone` varchar(50) NOT NULL,
  `facebook` varchar(80) NOT NULL,
  `friends` text NOT NULL,
  `follow` text NOT NULL,
  `create_time` date NOT NULL,
  `last_update` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`email`, `username`, `password`, `image`, `birthday`, `cellphone`, `facebook`, `friends`, `follow`, `create_time`, `last_update`) VALUES
('mekong89@gmail.com', 'Mekong 89', '12345', 'mekong89.jpg', '2014-06-17', '0973771433', 'http://facebook.com/mekong89', '', '', '2014-06-17', '2014-06-17'),
('test1@gmail.com', 'Test 1', '12345', 'test1.jpg', '2014-07-08', '0909888777', 'http://facebook.com/test1', '', '', '2014-07-08', '2014-07-08'),
('test2@gmail.com', 'Test 2', '12345', 'test2.jpg', '2014-07-08', '0909876345', 'http://facebook.com/test2', '', '', '2014-07-08', '2014-07-08'),
('test3@gmail.com', 'Test 3', '12345', 'test3.jpg', '2014-07-01', '0978356123', 'http://mekong89.com/test3', '', '', '2014-07-08', '2014-07-08'),
('thang.ai@gmail.com', 'Thang Ai', '12345', 'thangai.jpg', '1991-01-02', '0909888888', 'http://facebook.com/thang.ai', '', '', '2014-06-23', '2014-06-23');

-- --------------------------------------------------------

--
-- Table structure for table `wifi`
--

CREATE TABLE IF NOT EXISTS `wifi` (
  `email` varchar(50) NOT NULL,
  `wifi_imei` varchar(30) NOT NULL,
  `last_online` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `wifi`
--

INSERT INTO `wifi` (`email`, `wifi_imei`, `last_online`) VALUES
('', 'E8:94:F6:2E:25:BC', '2015-03-13 22:30:05'),
('mekong89@gmail.com', 'E8:94:F6:2E:25:BC', '2015-03-15 01:59:49'),
('test1@gmail.com', 'F8:1A:67:54:13:F0', '2014-09-06 09:52:30'),
('test2@gmail.com', 'F8:1A:67:54:13:F0', '2014-09-06 10:02:05'),
('test3@gmail.com', 'F8:1A:67:54:13:F0', '2014-07-24 22:20:09');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `feed`
--
ALTER TABLE `feed`
 ADD PRIMARY KEY (`feedID`), ADD UNIQUE KEY `feedID` (`feedID`);

--
-- Indexes for table `follow`
--
ALTER TABLE `follow`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `friend`
--
ALTER TABLE `friend`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `gps`
--
ALTER TABLE `gps`
 ADD PRIMARY KEY (`email`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tblike`
--
ALTER TABLE `tblike`
 ADD PRIMARY KEY (`likeid`), ADD UNIQUE KEY `likeid` (`likeid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`email`);

--
-- Indexes for table `wifi`
--
ALTER TABLE `wifi`
 ADD PRIMARY KEY (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `feed`
--
ALTER TABLE `feed`
MODIFY `feedID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=116;
--
-- AUTO_INCREMENT for table `follow`
--
ALTER TABLE `follow`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `friend`
--
ALTER TABLE `friend`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
MODIFY `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=180;
--
-- AUTO_INCREMENT for table `tblike`
--
ALTER TABLE `tblike`
MODIFY `likeid` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=71;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
