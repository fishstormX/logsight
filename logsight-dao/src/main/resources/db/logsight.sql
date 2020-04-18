DROP TABLE IF EXISTS `log_field`;
DROP TABLE IF EXISTS `log_field_file_list`;
CREATE TABLE IF NOT EXISTS `log_field`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL COMMENT 'Log files path',
  `timeline` timestamp COMMENT 'Last update createTime',
  `file_count` int COMMENT 'File count',
  `create_time` timestamp,
  `status` int default 0,
  PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `log_field_file_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11),
  `path_name` varchar(255) DEFAULT NULL,
  `timeline` timestamp COMMENT 'Last update createTime',
  `status` int default 1 ,
  PRIMARY KEY (`id`)
);
INSERT INTO `log_field`(`path`,`create_time`,`file_count`, `status`) VALUES('D:\My Java\untitled\apache-maven-3.5.4',CURRENT_TIME  ,12,0);
INSERT INTO `log_field`(`path`,`create_time`,`file_count`, `status`) VALUES('E:\',CURRENT_TIME  ,1,0);
INSERT INTO `log_field`(`path`,`create_time`) VALUES('F:\*maple\target\*',CURRENT_TIME  );
INSERT INTO `log_field`(`path`,`create_time`,`file_count`, `status`) VALUES('F:\*hmaple\SQL\*',CURRENT_TIME  ,12,0);


