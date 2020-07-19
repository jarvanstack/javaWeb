## 一、billlist订单查询

别去整那个前端分页，那个很简单，你自己没有前端的接口做个啥，瞎搞，先把功能实现再说，那是锦上添花

























## X、问题和解决

### 1. 如何知道Bill list 需要查询哪些字段？

查询 pojo表 类即可，

比如

![image-20200718151659307](C:%5CUsers%5C25301%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200718151659307.png)

所以对应的SQL

```sql
SELECT
	smbms_bill.*,
	smbms_provider.proName
FROM
	smbms_bill,
	smbms_provider
WHERE
	smbms_bill.providerId = smbms_provider.id
```





### 2. 因为没有给bill 某些字段赋值初始值导致的空指针异常.

解决办法，

* 初始化bill的时构造中初始化
* 在执行表单查询的时候 检查，如果为空就

### 3. 如果不加smbms前缀导致查询为空的问题

![image-20200718155326972](C:%5CUsers%5C25301%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200718155326972.png)

加上试试 

问题解决，使用预编译的查问题功能，实现SQL 的检查就不会因为SQL语句报错

### 4. 订单添加方法十分奇怪的问题

![image-20200718171540023](C:%5CUsers%5C25301%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200718171540023.png)

重定向？？

![image-20200718171559212](C:%5CUsers%5C25301%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200718171559212.png)

### 5. junit 加上断点测试 取代 sout



### 6. 添加成功后转发到list页面，但是无法刷新的问题

刷新要经过Servlet，就要使用请求。

```
resp.sendRedirect("bill.do?method=query");//使用这个方法实现了页面的刷新
```

### 7.MD5 加密的问题

MD5 加密 + 前端密码长度限制 = **你有数据库也登录不上的安全**

解决方法，在

```java
User user = userDao.findUser(username, MD5Util.string2MD5(password));
```

去掉MD5 加密

![image-20200719150614755](C:%5CUsers%5C25301%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200719150614755.png)

### 8. 模糊查询报错的问题

![image-20200719163349153](C:%5CUsers%5C25301%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200719163349153.png)

### 9。 SQL where  1= 1 解决动态SQL的问题