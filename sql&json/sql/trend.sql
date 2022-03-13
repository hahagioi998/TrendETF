# 创建数据库
CREATE DATABASE IF NOT EXISTS trenddata;
USE trenddata;

# 创建ETF指数代码表
CREATE TABLE etfcodes (
id INT PRIMARY KEY AUTO_INCREMENT,
`code` VARCHAR(20) NOT NULL,
`name` VARCHAR(50) NOT NULL
);

# 创建ETF历史数据表
CREATE TABLE etfdata(
id INT PRIMARY KEY AUTO_INCREMENT,
`datacode` VARCHAR(20),
`date` DATE,
close_point DECIMAL(8,2)
);

# 通过Navicat导入数据时，需要依次补齐ETF的代码
UPDATE etfdata SET datacode = '399975'
WHERE datacode IS NULL;