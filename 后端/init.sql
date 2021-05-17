# 创建数据库shop
DROP DATABASE `shop`;
CREATE DATABASE `shop` ;
USE `shop`;

# 创建表user
CREATE TABLE `user` (
    `student_id` VARCHAR(16) NOT NULL,
    `mailbox` VARCHAR(255) NOT NULL,
    `password` VARCHAR (16) NOT NULL,
    `avatar` VARCHAR(255) DEFAULT '/static/upload/avatar/default.jpg',
    `name` VARCHAR(10) NOT NULL,
    `sex` ENUM('男', '女') NOT NULL DEFAULT '男',
    `college` VARCHAR(16) NOT NULL DEFAULT '',
    `major` VARCHAR(16) NOT NULL DEFAULT '',
    `dormitory` VARCHAR(10) NOT NULL DEFAULT '',
    PRIMARY KEY (`student_id`),
    UNIQUE (`mailbox`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# 插入初始数据
INSERT INTO `user`(`student_id`,`mailbox`,`password`,`name`,`sex`,`college`,`major`,`dormitory`)
VALUES ('640248','1006480577@qq.com','111111','LJL','男','华工','计联','C12'),
       ('387145','387145@qq.com','222222','LLL','女','中大','计创','C6');

# 创建表commodity_type
CREATE TABLE `commodity_type`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(16) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# 插入商品类型
INSERT INTO `commodity_type`(`type`)
VALUES('电子产品'),('图书'),('男士服装'),('女士服装'),('非机动车'),('生鲜水果'),('演艺门票'),('代考/代跑'),('其他闲置');

# 创建表commodity
CREATE TABLE `commodity`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `seller_id` VARCHAR(16) NOT NULL ,
    `name` VARCHAR(50) NOT NULL,
    `type` VARCHAR(20) NOT NULL ,
    `price` DOUBLE NOT NULL ,
    `picture` VARCHAR(255) NOT NULL DEFAULT 'default.jpg',
    `description` VARCHAR(50) NOT NULL ,
    `express_fee` DOUBLE NOT NULL ,
    `bought` ENUM('是', '否') NOT NULL DEFAULT '否',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`seller_id`) REFERENCES `user`(`student_id`),
    FOREIGN KEY (`type`) REFERENCES `commodity_type`(`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# 插入商品
INSERT INTO `commodity`(`seller_id`,`name`,`type`,`price`,`description`,`express_fee`)
VALUES ('640248','耳机','电子产品',50.5,'音质好',2),
       ('640248','男士西装','男士服装',999,'帅气',3),
       ('387145','MYSQL从删库到跑路','图书',153.5,'跑跑跑跑跑',0),
       ('387145','自行车','非机动车',535,'好骑',5);

# 创建表order
CREATE TABLE `order`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `customer_id` VARCHAR(16) NOT NULL,
    `commodity_id` BIGINT NOT NULL,
    `date` DATETIME NOT NULL,
    `reception` ENUM('是','否') NOT NULL DEFAULT '否',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`customer_id`) REFERENCES `user`(`student_id`),
    FOREIGN KEY (`commodity_id`) REFERENCES `commodity`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 创建表comment
CREATE TABLE `comment`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_id` BIGINT NOT NULL ,
    `content` VARCHAR(255) NOT NULL ,
    `star` ENUM('1','2','3','4','5') NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE (`order_id`),
    FOREIGN KEY (`order_id`) REFERENCES `order`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 创建表cart
CREATE TABLE `cart`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `customer_id` VARCHAR(16) NOT NULL ,
    `commodity_id` BIGINT NOT NULL ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`customer_id`) REFERENCES `user`(`student_id`),
    FOREIGN KEY (`commodity_id`) REFERENCES `commodity`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 创建表admin
CREATE TABLE `admin`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `account` VARCHAR(16) NOT NULL ,
    `password` VARCHAR(16) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# 插入管理员信息
INSERT INTO `admin`(`account`, `password`) VALUES ('admin', 'admin123');