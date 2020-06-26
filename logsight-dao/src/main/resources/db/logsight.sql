CREATE TABLE IF NOT EXISTS `log_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timeline` timestamp COMMENT 'Last update time',
  `name` varchar(255) DEFAULT NULL,
  `create_time` timestamp,
  `status` int default 0,
  `fieldIds` varchar(1500),
  `field_count` int,
  `remarks` varchar(500) DEFAULT NULL COMMENT 'remarks',
  UNIQUE (`name`),
  PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `log_field`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL COMMENT 'Log files path',
  `timeline` timestamp COMMENT 'Last scanUpdate time',
  `file_count` int COMMENT 'File count',
  `create_time` timestamp,
  `size` double,
  `status` int default 0,
  `remarks` varchar(500) DEFAULT NULL COMMENT 'remarks',
  PRIMARY KEY (`id`),
  UNIQUE (`path`)
);
CREATE TABLE IF NOT EXISTS `log_field_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11),
  `path_name` varchar(600) DEFAULT NULL,
  `file_size` bigint DEFAULT 0,
  `timeline` timestamp COMMENT 'Last scanUpdate time',
  `status` int DEFAULT 0,
  `last_scan` timestamp ,
  `prev_size` bigint DEFAULT 0,
  `tree_scanned_flag` int DEFAULT 0,
  `last_modified` bigint DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE (`field_id`,`path_name`),
  INDEX field_id_idx ( `field_id` )
);
CREATE TABLE IF NOT EXISTS `log_field_tree`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11),
  `level` int(11),
  `parent_id` bigint DEFAULT 0,
  `last_scan` timestamp ,
  `name` varchar(600),
  `path` varchar(2200),
  `last_flag` int DEFAULT 0,
  UNIQUE (`field_id`,`path`),
  PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `report_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path_name` varchar(255) DEFAULT NULL,
  `file_size` bigint DEFAULT 0,
  `timespan` timestamp COMMENT 'Last scanUpdate time',
  PRIMARY KEY (`id`),
  INDEX path_name_idx ( `path_name` )
);