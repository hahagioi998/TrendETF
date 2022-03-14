# TrendETF
SpringBoot&amp;MyBatisPlus练手项目：ETF趋势交易系统

## 项目简介

- 本项目采用2005~2019年的ETF指数数据，通过简单的MA均线策略，对ETF进行策略回测。
- 本项目参考how2j网站趋势投资项目的单站版本。ETF数据来源及项目架构均来源于该项目，相比站长的原版项目，区别主要在以下几点：
  - 原项目数据文件为json格式，本项目通过Navicat将json文件导入Mysql数据库，并利用MyBatisPlus对数据进行查询。
  - 原项目前端采用chart.js制图，本项目采用echart.js。
- 本项目主要技术栈：
  - 前端技术：html，css，javascript，bootstrap，echart，vue
  - 后端技术：SpringBoot，MyBatisPlus
  - 数据库：MySql
- 项目使用说明详见项目内置的帮助文档页。

## 项目运行步骤

- 打开MySql数据库，创建项目数据库trenddata，并创建两张空表。（ps：sql语句已上传在sql&json文件夹中）

  ```mysql
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
  ```

- 通过navicat将ETF的原始json数据导入，由于Navicat的导入无法直接将ETF的代码直接输入，每次导入一个json数据需执行以下sql语句，将ETF的代码手动添加。（ps：导入流程可参考https://www.cnblogs.com/shenhaha520/p/11031817.html 博客的步骤）

  ```mysql
  # 以添加000015.json文件为例，添加后执行下面语句，赋值datacode,余同
  UPDATE etfdata SET datacode = '000015'
  WHERE datacode IS NULL;
  ```

- 修改application.properties中的数据库配置信息，更改为自身的数据库配置。

- 启动TrendETFApplication.java，打开浏览器，输入http://localhost:8088/trend/ （最后的'/'不能省略）即可进入项目首页。运行成功界面如下：

<img src="https://myimageserver.oss-cn-beijing.aliyuncs.com/TrendShow.png" style="zoom:80%;" />

## 备注

- 本项目仅用于个人学习。
- 新手程序员练手项目，未经深入调试，如有bug，非常正常，︿(￣︶￣)︿，欢迎指正。
- 关于后续优化方向：
  - 可辅以python爬虫，定时爬取每日数据，供趋势交易系统即时分析并提供决策建议。
  - 可结合其他交易策略，如价值投资，网格交易等投资策略，并引入更多的指标，进一步优化策略系统。





