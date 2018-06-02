DROP TABLE IF EXISTS `hadoop_cloud`.`hc_user`;
CREATE TABLE `hadoop_cloud`.`hc_user` (
  `id`            VARCHAR(45) NOT NULL,
  `username`      VARCHAR(45) NULL DEFAULT 'unnamed_user',
  `password`      VARCHAR(60) NOT NULL,
  `gender`        VARCHAR(45) NULL DEFAULT 'unknown',
  `age`           INT         NULL DEFAULT 0,
  `email`         VARCHAR(45) NULL DEFAULT 'unknown',
  `is_valid`      BOOLEAN     NOT NULL,
  `time_created`  TIMESTAMP   NULL,
  `time_modified` TIMESTAMP   NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = 'user info table';
