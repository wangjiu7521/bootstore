<?xml version="1.0" encoding="UTF-8"?>
<schemadesigner version="6.5">
<source>
<database charset="utf8" collation="utf8_general_ci">bookstore</database>
</source>
<canvas zoom="100">
<tables>
<table name="tb_book" view="colnames">
<left>302</left>
<top>103</top>
<width>97</width>
<height>202</height>
<sql_create_table>CREATE TABLE `tb_book` (
  `bid` char(32) NOT NULL,
  `bname` varchar(100) DEFAULT NULL,
  `pricce` decimal(5,1) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  `cid` char(32) DEFAULT NULL,
  PRIMARY KEY (`bid`),
  KEY `cid` (`cid`),
  CONSTRAINT `tb_book_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `tb_category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8</sql_create_table>
</table>
<table name="tb_category" view="colnames">
<left>83</left>
<top>117</top>
<width>97</width>
<height>122</height>
<sql_create_table>CREATE TABLE `tb_category` (
  `cid` char(32) NOT NULL,
  `cname` varchar(100) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8</sql_create_table>
</table>
<table name="tb_orderitem" view="colnames">
<left>516</left>
<top>101</top>
<width>108</width>
<height>182</height>
<sql_create_table>CREATE TABLE `tb_orderitem` (
  `iid` char(32) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `subtotal` decimal(10,0) DEFAULT NULL,
  `oid` char(32) DEFAULT NULL,
  `bid` char(32) DEFAULT NULL,
  PRIMARY KEY (`iid`),
  KEY `oid` (`oid`),
  KEY `bid` (`bid`),
  CONSTRAINT `tb_orderitem_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `tb_orders` (`oid`),
  CONSTRAINT `tb_orderitem_ibfk_2` FOREIGN KEY (`bid`) REFERENCES `tb_book` (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8</sql_create_table>
</table>
<table name="tb_orders" view="colnames">
<left>676</left>
<top>92</top>
<width>116</width>
<height>202</height>
<sql_create_table>CREATE TABLE `tb_orders` (
  `oid` char(32) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `total` decimal(10,0) DEFAULT NULL,
  `state` smallint(1) DEFAULT NULL,
  `uid` char(32) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `uid` (`uid`),
  CONSTRAINT `tb_orders_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `tb_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8</sql_create_table>
</table>
<table name="tb_user" view="colnames">
<left>892</left>
<top>52</top>
<width>117</width>
<height>202</height>
<sql_create_table>CREATE TABLE `tb_user` (
  `uid` char(32) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `code` char(64) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8</sql_create_table>
</table>
</tables>
</canvas>
</schemadesigner>