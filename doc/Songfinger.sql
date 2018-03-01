SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `finger`
-- ----------------------------
DROP TABLE IF EXISTS `finger`;
CREATE TABLE `finger` (
  `finger_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `f1` smallint(6) DEFAULT NULL,
  `f2` smallint(6) DEFAULT NULL,
  `dt` float DEFAULT NULL,
  PRIMARY KEY (`finger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `song`
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song` (
  `song_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `song_name` varchar(100) DEFAULT '',
  PRIMARY KEY (`song_id`),
  UNIQUE KEY `song_name` (`song_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `songfinger`
-- ----------------------------
DROP TABLE IF EXISTS `songfinger`;
CREATE TABLE `songfinger` (
  `song_id` int(10) unsigned NOT NULL,
  `finger_id` int(10) unsigned NOT NULL,
  `offset` int(11) DEFAULT NULL,
  KEY `fid` (`finger_id`),
  KEY `sid` (`song_id`),
  CONSTRAINT `fid` FOREIGN KEY (`finger_id`) REFERENCES `finger` (`finger_id`) ON DELETE CASCADE,
  CONSTRAINT `sid` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
