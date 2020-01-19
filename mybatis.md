1.Mybatis动态sql是做什么的？都有哪些动态sql？简述下动态sql的执行原理？

​	Mybatis的动态sql，可以让我们再XML映射文件中 以XML便签的形式编写sql，完成逻辑判断比企鹅	动态拼接sql的功能

​	Mybatis中动态sql标签 ：<if> <choose> <when> <otherwise> <trim><set><foreach>	 	<bind><where>等。

​	原理：遍历xml中的标签，找到对应的nodeHandler处理类，对sql进行拼接。

2.Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？

​	 支持。Mybatis 仅支持association关联对象和collection关联集合对象的延迟加载。

​	原理是使用CGLIB创建目标对象的代理对象。当发现封装对象的内部对象为null时，调用相应的查询	关联对象sql查出值，并set进封装对象。

3.Mybatis都有哪些Executor执行器？他们之间的区别是什么？

​	**SimpleExecutor：**每执行一次update或select，就开启一个Statement对象，用完立刻关闭	      	Statement对象。

​	ReuseExecutor：执行update或select，以sql作为key查找Statement对象，存在就使用，不存在	 	就创建，用完后，不关闭Statement对象，而是放置于Map内，供下一次使用。简言之，就是重	 	复使用Statement对象。

​	BatchExecutor：执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批	 	处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每 	 	个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相	同。

4.简述下Mybatis的一级，二级缓存（分别从存储结构、范围、失效场景。三方面来作答）？

​	一级缓存：底层存储结构为hashmap，当进行 修改，插入，或者clearCache sqlsession.close()时	会清空缓存，即一级缓存为sqlsession级别缓存，每个sqlsession对象拥有自己的缓存。

​	二级缓存：二级缓存的底层结构也是hashmap当进行 修改，插入等事务提交时，也会清空二级缓	 	存，二级缓存可为多个sqlsession对象共用，如果是分布式系统，则需用改用缓存对象，如使用 	 	redis作为二级缓存，避免因多服务器，导致二级缓存失效。

5.简述Mybatis的插件运行原理，以及如何编写一个插件？

​	mybatis 再获取ResultSetHandler，Executor，StatementHandler，ParameterHandler 这四个处理对象时，并不是直接生成原对象，而是通过代理的形式生成代理对象，再生成代理对象时，会先去先去执行 拦截器器中的intercept方法，因此，我们要编写一个插件，需要实现interceptor接口，并在mybatis的配置文件中加入<plugins><plugin> 声明我们定义的插件，就能使用定义的插件。

