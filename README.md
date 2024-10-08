项目描述：B2C 模式前后端分离的电子商务平台，包含后台管理系统和前台用户系统。
涉及技术：Spring Boot、Spring MVC、Mybatis、Redis、Minio、Vue、Axios 等。
项目细节：
1、用户身份登录保存功能上，使用 Redis 保存验证码以及登录过后的用户信息，通过登录拦截器和 ThreadLocal，实现一个请求过程中的用户信息共享。
2、通过 Spring Task 实现统计每日订单信息保存到数据库中，减少查询订单统计数据的开销。
3、使用 Minio 完成用户和商品图片上传存储，节约文件存储的成本。
4、基于 AOP 的环绕通知和自定义注解确定切入点完成日志记录，降低代码重复度以及携带一些变化的参数，比如模块名称。
