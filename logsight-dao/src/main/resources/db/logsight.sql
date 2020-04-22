DROP TABLE IF EXISTS `log_field`;
DROP TABLE IF EXISTS `log_field_file`;
CREATE TABLE IF NOT EXISTS `log_field`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL COMMENT 'Log files path',
  `timeline` timestamp COMMENT 'Last update time',
  `file_count` int COMMENT 'File count',
  `create_time` timestamp,
  `status` int default 0,
  PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `log_field_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11),
  `path_name` varchar(255) DEFAULT NULL,
  `file_size` bigint DEFAULT 0,
  `timeline` timestamp COMMENT 'Last update time',
  `status` int default 1 ,
  PRIMARY KEY (`id`)
);
ALTER TABLE `log_field` ADD UNIQUE (`path`);
INSERT INTO `log_field`(`path`,`create_time`,`file_count`, `status`) VALUES('/data/*',CURRENT_TIME  ,12,0);



