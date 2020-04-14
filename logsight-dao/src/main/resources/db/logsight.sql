DROP TABLE IF EXISTS `log_field`;
CREATE TABLE IF NOT EXISTS `log_field`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL COMMENT 'Log files path',
  `timeline` timestamp COMMENT 'Last update createTime',
  `file_count` int COMMENT 'File count',
  `create_time` timestamp,
  `status` int default 1 ,
  PRIMARY KEY (`id`)
);
INSERT INTO `log_field`(`path`,`create_time`,`file_count`, `status`) VALUES('/data',CURRENT_TIME  ,12,2);
INSERT INTO `log_field`(`path`,`create_time`,`file_count`, `status`) VALUES('/data',CURRENT_TIME  ,1,0);
INSERT INTO `log_field`(`path`,`create_time`) VALUES('/data',CURRENT_TIME  );

