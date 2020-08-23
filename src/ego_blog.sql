/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.29 : Database - db_blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_blog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_blog`;

/*Table structure for table `tb_blog` */

DROP TABLE IF EXISTS `tb_blog`;

CREATE TABLE `tb_blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `first_picture` varchar(255) DEFAULT NULL,
  `flag` varchar(20) DEFAULT NULL,
  `content` text,
  `view` int(11) DEFAULT NULL,
  `appreciation` tinyint(1) DEFAULT NULL,
  `share_statement` tinyint(1) DEFAULT NULL,
  `commentabled` tinyint(1) DEFAULT NULL,
  `published` tinyint(1) DEFAULT NULL,
  `recommended` tinyint(1) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tb_blog` */

insert  into `tb_blog`(`id`,`title`,`first_picture`,`flag`,`content`,`view`,`appreciation`,`share_statement`,`commentabled`,`published`,`recommended`,`create_time`,`update_time`,`user_id`,`type_id`) values (4,'redis的数据类型','java.text.SimpleDateFormat@953a00cd4e204e82cb5a46fb9be72bb2c8caa921.jpg','0','# NoSql\r\n\r\nNoSql数据库四大分类：\r\n\r\n1、kv键值对：Redis\r\n\r\n2、文档型：MongoDB\r\n\r\n3、列存储数据库：HBase\r\n\r\n4、图关系数据库：它不是存图像，存的是关系，如：朋友圈推荐，广告推荐\r\n\r\n> 官方文档链接 https://redis.io/commands\r\n\r\n## String(字符串)\r\n\r\n### key-value 键值对\r\n\r\n```bash\r\nset  key  value  # 设置一个键值对\r\nget  key  # 获取对应键的值\r\nkeys  *   # 查看当前所有的键\r\nexists key  # 查看这个键是否存在 存在返回1\r\nappend key value # 往key中追加一个字符串，不存在则新建\r\nstrlen key  # 获取key的长度 \r\n\r\n```\r\n\r\n### 步长 n\r\n\r\n```bash\r\nincr  key  # +1\r\ndecr  key  # -1\r\nincrby  key n # +n\r\ndecrby key n # -n\r\n\r\n```\r\n\r\n### 字符串范围\r\n\r\n```bash\r\ngetrange  key start end  # 截取字符串 end=-1时 获取全部字符串\r\nsetrange key start value  # 替换start开始的字符串，\r\n```\r\n\r\n### 时间设置\r\n\r\n```bash\r\nsetex （set with expire） # 设置过期时间\r\nsetnx （set if not expire） # 不存在才能设置（在分布式锁中常常使用）\r\n\r\nsetex key s value # 设置s秒后过期\r\nttl key # 查看key还有多久过期\r\n\r\n```\r\n\r\n### 一次性设置多个值\r\n\r\n```bash\r\nmset k1 v1 k2 v2 k3 v3  # 一次性设置多个值\r\nmget k1 k2 k3 # 一次性设置多个值\r\n\r\nmsetnx k1 v1 k4 v4  # msetnx是一个原子性的操作\r\n\r\n```\r\n\r\n### 对象\r\n\r\n```bash\r\nset user：1{name：zhangsan} # 设置一个user：1对象key为json字符串\r\n\r\n\r\nmset user:1:name zhangsan user:1:age 15 # 另一种\r\n\r\ngetset key value  # 组合命令 先执行get后执行set\r\n```\r\n\r\nString使用场景\r\n\r\nvalue除了是我们的字符串也可以是数字\r\n\r\n- 计数器\r\n- 统计多单位数量（如浏览量，粉丝数）\r\n- 对象缓存\r\n\r\n## List（列表）\r\n\r\n基本的数据类型，列表，一个list中允许重复的value。\r\n\r\n在redis里面，list可以完成栈，队列，阻塞队列\r\n\r\n所有的list里面的命令都是L开头,Redis不区分大小写命令\r\n\r\n```bash\r\nLpush list value  # 将value插入到list头部(或左放入)\r\nrpush list value  # 将value插入到list尾部（或右放入)\r\nLrange start end  # 查看start-end的value值，end=-1查看全部\r\n\r\nLpop list  # 从list中左移出一个值\r\nRpop list  # 从list中右移出一个值\r\n\r\nLindex  list index  # 从list中获取下标为index的value\r\nLlen list  # 查看list的长度\r\nLrem list n value  #移除n个指定的value值\r\n\r\nLtrim list start end  # 截取list中的start-end\r\n```\r\n\r\n组合命令\r\n\r\n```bash\r\nRpopLpush list1 list2  # 冲list1中右取出一个value放到list2头部（或左放入）\r\n\r\nexists list  # 判断list是否存在befor\r\nlset list index value  # 修改list中下标为index的值，如果不存在列表就会报错\r\n\r\nLinsert  list before|after value1 value2  # 往value1之前或者之后插入一个value2\r\n\r\n\r\n```\r\n\r\n> 小结\r\n\r\n- 它实际上是一个链表，可以在前面或后面插入值\r\n- 如果key不存在，创建新的链表\r\n- 如果key存在，新增内容\r\n- 如果移除了所有值，空链表，也代表不存在\r\n- 在两边插入或者改动值，效率最高！中间元素，相对来说效率会第一点\r\n\r\n## set（集合）\r\n\r\nset中的值不能重复，无序不重复\r\n\r\n```bash\r\nsadd Set value # 往Set中增加一个value\r\nsmembers Set  # 查看Set中所有value\r\nsismember Set value  # 查看Set中是否存在某个value。存在返回1\r\nscard Set  # 获取Set集合中的元素个数\r\nsrem Set value  # 从Set中移除指定的value\r\n\r\n\r\nsrandmember Set n  # 从Set中随机抽选出n个元素，n默认为1 可以用在抽奖\r\n\r\nspop Set  # 随机移除一个元素\r\n\r\nsmove Set1 Set2 value  # 将一个指定的值移动到另一个Set中\r\n\r\n\r\n数字集合类\r\n	- 差集  sdiff Set1 Set2\r\n	- 交集  sinter Set1 Set2 #  共同好友实现\r\n	- 并集  sunion Set1 Set2\r\n```\r\n\r\n交集应用广泛，如共同关注，共同爱好，二度好友，可以将关注的人放在一个set集合中，粉丝也放在一个集合中。\r\n\r\n## Hash\r\n\r\nMap集合，hash-<filed,value>。\r\n\r\n```bash\r\nhset hash field value  # 往hash集合中放入field- value键值对，如果存在则覆盖\r\nhget hsah field  # 从hash中取出field的value\r\n\r\nhmset hmget # 多个操作\r\nhgetall hash  # 从hash中取出所有值\r\nhdel hash filed  # 删除hash中指定的filed\r\nhlen hash  # 获取hash中有多少个值\r\nhexists hash field  # 判断存在\r\nhkeys hash  # 获得所有field\r\nhvals hash  # 获取所有value\r\n```\r\n\r\nhash存变更的数据，如user的name，age。hash更适合对象的存储，String更适合存字符串\r\n\r\n## Zset（有序集合）\r\n\r\n在set的基础上，增加了一个值，默认从小到大排序\r\n\r\nkey：集合名称  n：集合位重，用来排序  value：值\r\n\r\n```bash\r\nzadd key n value # 添加一个值\r\nzrange key  # 查看所有值\r\nzrangebyscore key min max withscore  # 排序min max为查看范围，min取负无穷-inf withscore使结果附带value\r\nzrevrange key 0 -1  # 从大到小排序\r\nzrem key value  # 移除元素\r\nzcard key  # 获取集合中的个数\r\nzcount key min max  # 查看指定范围的个数，闭区间\r\n```\r\n\r\n案例思路：set排序  存储班级成绩表，工资表排序！\r\n\r\n普通消息，1；重要消息，2。带权重\r\n\r\n排行榜实现，取Top N。\r\n\r\n## 三种特殊数据类型\r\n\r\n### Geospatial（地理位置）\r\n\r\n朋友的定位，附件的人，打车距离计算\r\n\r\n只有六个命令\r\n\r\n两极无法添加，我们一般会下载城市数据，直接通过java导入。\r\n\r\n注意：有效的经度从-180到180，有效的维度从-85.0511到85.0511，超出会报错\r\n\r\n```bash\r\ngeoadd china:city 经度 维度 地理位置名称  # 插入一个地理位置经纬度，可以去在线网站查经纬度\r\ngeopst china:city 地理位置名称  # 获取经纬度，地理位置名称可以为多个，查询多个\r\ngeodist china:city 地理位置名称 地理位置名称 单位（m，km，mi，ft）  #查询两个位置之间的直线距离,加单位显示\r\ngeoradius china:city 经度 维度 radius 单位 withdist  # 以给定的经纬度为中心，radius为半径查询，withdist显示距离；withcoord，显示经纬度；count，设置查询的数量\r\ngeoradius china:city 地理位置名称 数值 单位  # 查找位于指定位置的周围信息\r\ngeohash chana:city 地理位置名称 地理位置名称 # 返回经纬度的hash，为11个字符串。\r\n```\r\n\r\ngeo底层的实现原理其实就是Zset，可以用Zset命令去操作\r\n\r\n### HyperLogLog\r\n\r\n### Bitmap\r\n\r\n## Redis.conf\r\n\r\n启动的时候，就通过配置文件来启动\r\n\r\n> 单位\r\n\r\n![image-20200417144312142](http://qfga80n5c.hn-bkt.clouddn.com/20200822142352.png)\r\n\r\n1、配置文件单位对大小写不敏感\r\n\r\n> 标签\r\n\r\n![image-20200417144515688](http://qfga80n5c.hn-bkt.clouddn.com/20200822142349.png)\r\n\r\n可以导入其他配置文件\r\n\r\n> 网络\r\n\r\n![image-20200417144605341](http://qfga80n5c.hn-bkt.clouddn.com/20200822142345.png)\r\n\r\n![image-20200417144831860](http://qfga80n5c.hn-bkt.clouddn.com/20200822142331.png)\r\n\r\n![image-20200417145051019](http://qfga80n5c.hn-bkt.clouddn.com/20200822142328.png)\r\n\r\n![image-20200417145408550](http://qfga80n5c.hn-bkt.clouddn.com/20200822142323.png)\r\n\r\n```bash\r\nbind 127.0.0.1  # 绑定的ip\r\nprotected-mode no  # 是否开启保护模式\r\n\r\nport  # 端口设置\r\ndaemonize  # 是否设置为守护进程 默认是no，需要自己开启为yes\r\n\r\npidfile /var/run/redis_6379.pid  # 如果以后台的方式运行，我们就需要指定一个pid文件\r\n\r\nloglevel notice  # 设置日志的级别\r\nlogfile \"\" # 配置日志生成的文件位置\r\n\r\ndatabases 16  # 数据库数量，默认16\r\nalways-show-logo yes  # 是否显示logo\r\n\r\n\r\n```\r\n\r\n> 快照\r\n\r\n持久化，在规定的时间内，执行了多少次操作，则会持久化到文件.rdb .aof\r\n\r\nredis是内存数据库，如果没有持久化，那么数据断电即失\r\n\r\n![image-20200417145925363](http://qfga80n5c.hn-bkt.clouddn.com/20200822142318.png)\r\n\r\n```bash\r\nsava 900 1  # 如果900s内，如果至少有1个key进行了修改，我们就进行持久化操作\r\nsave 300 10 如果300s内，如果至少有10个key进行了修改，我们就进行持久化操作\r\nsave 60 10000 如果60s内，如果至少有10000个key进行了修改，我们就进行持久化操作\r\n\r\nstop-writes-on-bgsave-error yes  # 持久化如果出错，是否还继续工作\r\nrdbcompression yes  # 是否压缩rdb文件，需要消耗一些cpu资源xiaoyan\r\nrdbcompression yes  #保存时进行校验\r\nrdbcompression yes  # rdb文件保存的目录，默认当前文件夹下\r\n\r\n\r\n```\r\n\r\n> Security\r\n\r\n可以在这里设置redis的密码，默认是没有密码的\r\n\r\n![image-20200417151021396](http://qfga80n5c.hn-bkt.clouddn.com/20200822142311.png)\r\n\r\n```bash\r\nconfig get requirepass  # 查看密码\r\nconfig set requirepass \"\"  # 设置密码\r\nauth \"\"  # 登录认值\r\n```\r\n\r\n\r\n\r\n> 限制client\r\n\r\n![image-20200417151308056](http://qfga80n5c.hn-bkt.clouddn.com/20200822142304.png)\r\n\r\n```bash\r\nmaxclients 10000  # 设置能连接上redis的最大客户端的数量\r\nmaxclients 10000  # 配置最大的内存容量\r\nmaxmemory <bytes>  # 内存到达上限之后的处理策略\r\n#移除一些过期的key；报错。。。\r\n```\r\n\r\n> Append Only模式  aof配置\r\n\r\n\r\n\r\n```bash\r\nappendonly no  #默认是不开启aof模式的，默认是rdb方式持久化的，在大部分所有的情况下，rdb完全够用\r\n\r\n\r\nappendfilename \"appendonly.aof\"  # The name of the append only file (default: \"appendonly.aof\")\r\nappendfsync everysec  # 每秒执行一次同步，可能会丢失这一秒 everysec 可以换成always每次修改no不执行这个时候操作系统自己同步，速度最快\r\n```\r\n\r\n',7,1,1,1,1,1,'2020-08-22 14:03:07','2020-08-22 14:03:07',1,2);

/*Table structure for table `tb_blog_tag` */

DROP TABLE IF EXISTS `tb_blog_tag`;

CREATE TABLE `tb_blog_tag` (
  `blog_id` int(11) DEFAULT NULL,
  `tag_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_blog_tag` */

insert  into `tb_blog_tag`(`blog_id`,`tag_id`) values (4,2);

/*Table structure for table `tb_comment` */

DROP TABLE IF EXISTS `tb_comment`;

CREATE TABLE `tb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `content` text NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `blog_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_comment` */

insert  into `tb_comment`(`id`,`nick_name`,`email`,`content`,`avatar`,`create_time`,`blog_id`) values (1,'ego','897717059@qq.com','既然是爷的博客，爷肯定先来试下评论',NULL,'2020-08-22 14:27:20',4);

/*Table structure for table `tb_file` */

DROP TABLE IF EXISTS `tb_file`;

CREATE TABLE `tb_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `suffix` varchar(30) DEFAULT NULL,
  `size` varchar(30) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_file` */

insert  into `tb_file`(`id`,`name`,`suffix`,`size`,`type`,`upload_time`) values (1,'3.1算法导论_英文版_第三版.pdf','.pdf','5.41MB','application/pdf','2020-08-22 06:18:39');

/*Table structure for table `tb_tag` */

DROP TABLE IF EXISTS `tb_tag`;

CREATE TABLE `tb_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_tag` */

insert  into `tb_tag`(`id`,`name`) values (2,'学习笔记'),(1,'实践');

/*Table structure for table `tb_type` */

DROP TABLE IF EXISTS `tb_type`;

CREATE TABLE `tb_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_type` */

insert  into `tb_type`(`id`,`name`) values (1,'Java'),(2,'Redis');

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `type` tinyint(1) DEFAULT '0',
  `avatar` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`nick_name`,`user_name`,`password`,`email`,`type`,`avatar`,`create_time`,`update_time`) values (1,'test','test','d56768c90dbf5b0e58c4d36ad2ffe963',NULL,0,'/image/1.jpg','2020-08-22 13:14:44','2020-08-22 13:14:46');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
