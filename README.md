> # 声明性REST客户端：Feign

Feign是一个声明式的Web服务客户端。这使得Web服务客户端的写入更加方便 要使用Feign创建一个界面并对其进行注释。它具有可插入注释支持，包括Feign注释和JAX-RS注释。Feign还支持可插拔编码器和解码器。Spring Cloud增加了对Spring MVC注释的支持，并使用Spring Web中默认使用的HttpMessageConverters。Spring Cloud集成Ribbon和Eureka以在使用Feign时提供负载均衡的http客户端。


基础介绍就不说了，可以自行查找资料

> # Spring cloud openfeign

Feign本身是Netflix的产品，Spring Cloud Feign是在原生Feign的基础上进行了封装，引入了大量的SpringMVC注解支持

1. 服务注册中心
2. 服务提供者
3. 服务消费者/调用者

### 服务注册中心

* feign-eureka

```
<dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```
### 服务提供者

* feign-provider

```
@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class FeignProviderApplication {

    @GetMapping("/getPrint")
    public String getPrint(@RequestParam("content") String content) {
        return "getPrint输入内容：".concat(content);
    }

    @PostMapping("/postPrint")
    public String postPrint(@RequestParam("content") String content) {
        return "postPrint输入内容：".concat(content);
    }


    public static void main(String[] args) {
        SpringApplication.run(FeignProviderApplication.class, args);
    }
}
```

```
server:
  port: 9000
spring:
  application:
    name: feign-provider
eureka:
  instance:
    hostname: localhost
  client:
    serviceUrl:
      defaultZone: http://localhost:8500/eureka/
```

讲服务注册到eureka中,这里值得注意的是 **spring.application.name=feign-provider** (服务名称)消费者通过服务名称找到具体的服务

###  服务消费者/调用者

* feign-consumer

```
@FeignClient("feign-provider")
public interface PrintService {

    @PostMapping(value = "/postPrint")
    String postPrint(@RequestParam("content") String content);

    @GetMapping(value = "/getPrint")
    String getPrint(@RequestParam("content")String content);
}
```

通过@FeignClient注解到注册中心找到对应的服务，在根据@PostMapping(value = "/postPrint")找到具体的服务，这个应该都不会陌生

也就是说Feign是通过http的方式来消费服务的

```
@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeignConsumerApplication {

    @Autowired
    private PrintService printService;

    @GetMapping("/getPrint")
    public String getPrint(String content) {
        return printService.getPrint(content);
    }

    @GetMapping("/postPrint")
    public String postPrint(String content) {
        return printService.postPrint(content);
    }

    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerApplication.class, args);
    }
}
```

```
server:
  port: 9100
spring:
  application:
    name: feign-consumer
eureka:
  instance:
    hostname: localhost
  client:
    serviceUrl:
      defaultZone: http://localhost:8500/eureka/
```

@EnableFeignClients开启feign

### 测试

http://localhost:9100/getPrint?content=hello
http://localhost:9100/postPrint?content=hello

### feign使用注意

* #### Feign默认使用@RequestBody


#### 错误的示例

* 服务提供者

```
@GetMapping("/getPrint")
public String getPrint(String content){...}
```

* 服务消费者

```
@GetMapping("/getPrint")
public String getPrint(String content){...}
```

**调用结果**

![输入图片说明](https://images.gitee.com/uploads/images/2018/0810/142408_b9c9e12f_966228.png "屏幕截图.png")

与期待的不一样，服务提供者声明的是GET请求，我消费的时候也是GET为什么实际发送的是POST

是因为如果没有显示的指定注解那么Feign默认使用@RequestBody，而@RequestBody一定是包含在请求体中的 GET无法做到所有将其转为了POST

### 解决办法

显示的指定@RequestParam()

* #### 服务提供者和消费者请求类型必须统一(get/post...)

* #### 不建议使用map传参,不符合面向对象要求

* #### 不建议使用map传参,不符合面向对象要求





