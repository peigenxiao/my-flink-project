
MysqlKafka:从mysql读数据并且写入mysql

WindowWordCount:从txt文件读取数据生成实体类

PowerItems：从csv文件读取数据生成实体类，算法：每隔5分钟输出最近一小时内点击量最多的前 N 个商品

PowerItems1：从csv文件读取数据生成实体类，算法：ZYGGL连续超过1次大于0.8/ZYGGL连续超过1次大于1 的次数

PowerItemsToMysql：将实体类保存到mysql

HotItems  可以参考下面的博客
Flink 零基础实战教程：如何计算实时热门商品
http://wuchong.me/blog/2018/11/07/use-flink-calculate-hot-items/

通过本文你将学到：

如何基于 EventTime 处理，如何指定 Watermark
如何使用 Flink 灵活的 Window API
何时需要用到 State，以及如何使用
如何使用 ProcessFunction 实现 TopN 功能
实战案例介绍
本案例将实现一个“实时热门商品”的需求，我们可以将“实时热门商品”翻译成程序员更好理解的需求：每隔5分钟输出最近一小时内点击量最多的前 N 个商品。将这个需求进行分解我们大概要做这么几件事情：

抽取出业务时间戳，告诉 Flink 框架基于业务时间做窗口
过滤出点击行为数据
按一小时的窗口大小，每5分钟统计一次，做滑动窗口聚合（Sliding Window）
按每个窗口聚合，输出每个窗口中点击量前N名的商品