CQRS和DDD
目录结构：
.
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── jswy
    │   │           └── ddd
    │   │               ├── MaterialClassificationApplication.java
    │   │               ├── application 应用层
    │   │               │    ├─assembler     装配器
    │   │               │    ├─dto           数据传输对象，xxxCommand/xxxQuery/xxxVo     
    │   │               │    │    ├─command  接受增删改的参数
    │   │               │    │    ├─query    接受查询的参数
    │   │               │    │    ├─vo       返回给前端的vo对象
    │   │               │    ├─service       应用服务，负责领域的组合、编排、转发、转换和传递
    │   │               │    ├─repository    查询数据的仓库接口
    │   │               │    ├─listener      事件监听定义
    │   │               │    ├── TradeEventProcessor.java
    │   │               │    ├── TradeMQReceiver.java
    │   │               │    └── TradeManager.java
	│   │               ├── interfaces   	用户接口层 
	│   │               │   ├── facade    	外观模式，对外提供本地接口和dubbo接口
	│   │               │   ├── adapter     适配器、总线
    │   │               │	├── model	       视图模型，数据模型定义 vo/dto
    │   │               │   │   └── TradeDTO.java
    │   │               │	├── assembler   装配器，实现模型转换
    │   │               │	├── controller  控制器，对外提供接口
    │   │               │   ├	├── TradeController.java
    │   │               │   ├	├── WalletController.java
    │   │               │	├── constant
    │   │               │   	├── MessageConstant.java
    │   │               ├── domain      	领域层
    │   │               │   ├── core	       核心域，决定业务成功和公司核心竞争力的子域，整个系统最重要部分
    │   │               │   ├── support	       支撑域，不是你的核心竞争力，但又不得不做，市场上也找不到现成方案的子域；支撑域具有企业特性，但不具通用性
    │   │               │   ├── generic	       通用域，公共代码抽取,被多个子域使用的是通用域，如认证，权限、登录验证、验证码、支付能力等
    │   │               │   ├── entity      领域实体
    │   │               │   ├── vo			领域值对象
    │   │               │   ├── service     领域服务
    │   │               │   ├── repository  仓库接口，增删改的接口
    │   │               │   ├── acl      	防腐层接口
    │   │               │   ├── event       领域事件
    │   │               │   ├── factory	       工厂类
    │   │               │   │	├── TradeService.java
    │   │               │   │	├── TradeServiceImpl.java
    │   │               │   ├── enums		枚举类
    │   │               │   │   ├── InOutFlag.java
    │   │               │   │   ├── TradeStatus.java
    │   │               │   │   ├── TradeType.java
    │   │               │   │   └── WalletStatus.java
    │   │               │   ├── event	      领域事件
    │   │               │   │   └── TradeEvent.java
    │   │               │   ├── model	      领域模型
    │   │               │   │   ├── Customer.java
    │   │               │   │   ├── TradeRecord.java
    │   │               │   │   └── Product.java
    │   │               │   └── repository  仓储接口，依赖倒置
    │   │               │       ├── CustomerRepository.java
    │   │               │       └── TradeRepository.java
    │   │               └── infrastructure  基础设施层
    │   │                   ├── listener   实体转换器
    │   │                   ├── model       持久化机制
    │   │                   ├── mapper      映射，mybatis mapper接口
    │   │                   ├── impl        仓库实现，持久化接口实现，映射ORM
    │   │                   │   ├── TradeRepositoryImpl.java
    │   │                   │   ├── WalletRepositoryImpl.java
    │   │                   ├── config      配置类
    │   │                   ├── exception 	工具类
    │   │                   ├── util 	       工具类
    │   │                   ├── cache		缓存
    │   │                   │   └── Redis.java
    │   │                   ├── client		客户端
    │   │                   │   ├── AuthFeignClient.java
    │   │                   │   └── LocalAuthClient.java
    │   │                   └── mq          消息队列、订阅
    │   │                       └── RabbitMQSender.java
    │   └── resources
    │       ├── application.properties
    │       └── rabbitmq-spring.xml
    └── test
        └── java
        