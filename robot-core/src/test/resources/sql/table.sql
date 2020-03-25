CREATE TABLE `neo_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `province_code` varchar(6) DEFAULT '' COMMENT '省份编码',
  `city_code` varchar(6) DEFAULT '' COMMENT '市编码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `center` varchar(64) NOT NULL DEFAULT '' COMMENT '中心点经纬度',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1新地址，0老地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_city_code` (`city_code`),
  KEY `idx_province_code` (`province_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市表';

CREATE TABLE `ibo_business_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `province_code` varchar(6) DEFAULT '' COMMENT '省份编码',
  `city_code` varchar(6) DEFAULT '' COMMENT '市编码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `center` varchar(64) NOT NULL DEFAULT '' COMMENT '中心点经纬度',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1新地址，0老地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_city_code` (`city_code`),
  KEY `idx_province_code` (`province_code`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='业务城市表';
