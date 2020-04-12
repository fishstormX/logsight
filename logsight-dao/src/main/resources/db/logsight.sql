DROP TABLE IF EXISTS `log_field`;
CREATE TABLE IF NOT EXISTS `log_field`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL COMMENT 'Log files path',
  `timeline` timestamp COMMENT 'Last update time',
  `status` int default 1 ,
  PRIMARY KEY (`id`)
);
INSERT INTO `log_field`(`path`,`timeline`) VALUES('/data',CURRENT_TIME);
