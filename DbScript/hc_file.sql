DROP TABLE IF EXISTS `hadoop_cloud`.`hc_file`;
CREATE TABLE `hadoop_cloud`.`hc_file` (
  `id`            BIGINT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`       VARCHAR(45)        NOT NULL,
  `parent_id`     BIGINT(8)          NOT NULL,
  `title`         VARCHAR(255)       NOT NULL,
  `orig_title`    VARCHAR(255)       NOT NULL,
  `lft`           INT                NOT NULL,
  `rgt`           INT                NOT NULL,
  `node_level`    INT                NOT NULL,
  `is_file`       TINYINT(1)         NOT NULL,
  `time_created`  TIMESTAMP          NULL,
  `time_modified` TIMESTAMP          NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = 'file info table';