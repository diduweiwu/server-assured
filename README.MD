# Server-Assured

**我们的目标:高效敏捷的完成自动化任务**
**即:在接口文档定义但服务未开发完成时可在本地快速/高效启动MockServer进行自动化用例开发**

## 技术选型

- Java 1.8
- Moco
- Maven
- Hutool

## 使用文档

### 0.准备阶段

- 添加Maven依赖(待发布至maven中心库)

```xml

<dependency>
    <groupId>com.diduweiwu</groupId>
    <artifactId>server-assured</artifactId>
    <version>1.0-RELEASE</version>
</dependency>
```

### 1.开发阶段

**待依赖拉取完成后,即可开启我们的Mock服务之旅**
针对Mock服务,常见的是Get,Post,Put,Delete四种,下面以Get接口作为演示示例

#### 1.1 Get接口请求模拟

---

#### 使用

##### 1.1.1 使用端口号初始化mock服务

```java
// 9998为端口号,由于启动的是真实的服务,请确认端口未被占用
StubServer.of(9998);
```

##### 1.1.2 添加接口

**接下来可以通过链式调用来添加接口配置**

```java
StubServer.of(9998).get("/nice",MapUtil.of("name","ok"));
```

**解释**

- get函数为新增一个get请求类的接口,有以下两种重载方法
    - get(String apiPath, Object data)
    - get(String apiPath, Object data, ResponseSetting[] responseSettings)

**参数**

- apiPath为接口的segment路径
- data为接口返回的数据,支持下面三种数据类型
    - String 会直接返回字符串
    - File 会返回数据下载
    - Object 会序列化成Json格式
- responseSettings 为接口返回的相关配置,支持传入一个或多个配置,目前仅支持接口延迟响应,更多功能后续迭代
    - 例如接口延迟3秒钟响应 Delay.builder().deplay(3).build()

**PS: post,put,delete三种类型的接口配置入参与上完全一致**

##### 1.1.3 服务启动

**当定义好服务端口与接口后,就可以进行启动操作了,链式调用的结尾使用start方法即可**

```java
StubServer.of(9998)
        .get("/nice",MapUtil.of("name","ok"))
        .start();
```

**此时,访问 localhost:9999/nice 即可进行接口调用**

### 2.扩展

**我们将Moco的请求配置接口开放出来,以支持更加灵活的方式自定义接口**

#### 2.1 扩展函数

- server.request(Request request)
  参数说明: request入参为实现了 Request接口/继承了BaseRequest基类 的类实例对象

方式请参考GetRequest类

```java
/**
 * @author test
 */
@SuperBuilder
public class GetRequest extends BaseRequest {
    @Override
    public HttpResponseSetting compose(HttpServer server) {
        return server.get(match(uri(apiPath)));
    }
}
```

## 写在最后

**针对环境的动态化暂时由用户来处理,毕竟mock代码不能在实际环境测试时候使用**
**这个工具不是为了替代什么,只是提供多一种mock服务的选择,让我们更加优雅的做接口自动化开发**

**由于这是比较初始的版本,肯定有很多考虑步骤和可优化的地方,欢迎大家使用、反馈和参与扩充功能:)**
