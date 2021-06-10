DROP TABLE IF EXISTS `ppy_property`;

CREATE TABLE `ppy_property` (
  `id` varchar(32) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 1,
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_updated` datetime DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(256) NOT NULL,
  `is_active` bit(1) NOT NULL DEFAULT b'0',
  `owner_id` varchar(32) NOT NULL,
  `ownership_date` date NULL,
  `geo_addr_latitude` decimal(20,16) NULL,
  `geo_addr_longitude` decimal(20,16) NULL,
  `addr_floor_line` varchar(128) NULL,
  `addr_block` varchar(32) NULL,
  `addr_street_address` varchar(256) NULL,
  `addr_district` varchar(128) NULL,
  `addr_area` varchar(128) NULL,
  `addr_postal_code` varchar(32) NULL,
  `addr_city` varchar(128) NULL,
  `addr_province` varchar(128) NULL,
  `addr_state` varchar(128) NULL,
  `addr_country_code` varchar(8) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `ppy_property`
ADD INDEX `ix_ppy_property_name` (`name` ASC);

ALTER TABLE `ppy_property`
ADD INDEX `ix_ppy_property_owner_id` (`owner_id` ASC);

ALTER TABLE `ppy_property`
ADD INDEX `ix_ppy_property_addr_city` (`addr_city` ASC);

ALTER TABLE `ppy_property`
ADD INDEX `ix_ppy_property_addr_province` (`addr_province` ASC);

ALTER TABLE `ppy_property`
ADD INDEX `ix_ppy_property_addr_country_code` (`addr_country_code` ASC);

ALTER TABLE `ppy_property`
ADD INDEX `ix_ppy_property_geo_addr_latitude_geo_addr_longitude` (`geo_addr_latitude` ASC, `geo_addr_longitude` ASC);
