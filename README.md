# 健康数据分析系统 (Health Data Analytics System)

## 📋 项目简介

健康数据分析系统是一个基于 Spring Boot 3 的综合性医疗健康数据管理和分析平台，集成了数据采集、存储、分析、可视化等多项功能。系统旨在为医疗卫生领域提供全方位的数据支持和智能分析服务。

## ✨ 核心功能

### 1. 用户认证与权限管理
- **JWT 身份认证**：基于令牌的安全认证机制
- **多角色权限体系**：支持 ADMIN、RESEARCHER、ANALYST、USER 等多种角色
- **细粒度权限控制**：基于 AOP 的资源级权限管理
- **验证码保护**：高危操作强制验证码验证

### 2. 医疗数据统计分析
- **全国数据统计**：
  - 人口总数、医疗机构数、医疗人员数
  - 床位总数、门诊/住院服务量
  - 医疗总费用等关键指标

- **省级数据分析**：
  - 各省份历年数据趋势分析
  - 人均医疗资源指标计算
  - 省级数据对比与排名

- **中位数分析**：
  - 各项医疗指标中位数计算
  - 中位数对应省份定位

### 3. 智能数据采集
- **多省份爬虫系统**：
  - 支持 20+ 省份卫健委官网新闻抓取
  - 异步并发采集，提高效率
  - 按日期精准筛选数据
  - 缓存机制减少重复请求

- **省份覆盖**：
  - 河北、山西、辽宁、吉林、黑龙江
  - 安徽、江西、山东、湖北、重庆
  - 内蒙古、广西、新疆、云南等

### 4. 文件管理与OCR识别
- **HDFS 分布式存储**：
  - 大文件分布式存储
  - 按类别自动分类
  - 文件元数据管理

- **智能 OCR 识别**：
  - 支持多种图片格式（JPG、PNG、BMP等）
  - 百度 OCR API 表格识别
  - 自动数据结构化存储

- **Hive 数据仓库**：
  - OCR 结果自动导入 Hive
  - 按分类动态建表
  - 支持大数据量分析

### 5. AI 对话服务
- **智能问答**：
  - 集成 DeepSeek AI 模型
  - 流式响应提升用户体验
  - 医疗健康领域知识问答

### 6. 日志审计
- **操作日志记录**：
  - AOP 自动记录关键操作
  - 日志趋势分析
  - 支持日期范围查询

## 🏗️ 技术架构

### 后端技术栈
- **核心框架**：Spring Boot 3.4.4
- **Java 版本**：JDK 21
- **持久层**：
  - MyBatis-Plus 3.5.6
  - MySQL 8.0+
  - Redis（缓存与会话管理）
  
- **大数据组件**：
  - Hadoop HDFS 3.3.6（分布式文件存储）
  - Hive 1.2.1（数据仓库）

- **安全认证**：
  - Spring Security 6.x
  - JWT (JJWT 0.11.5)
  - BCrypt 密码加密

- **数据采集**：
  - Jsoup 1.17.2（HTML 解析）
  - Selenium 4.21.0（动态页面）
  - Apache HttpClient 4.5.14

- **OCR 服务**：
  - 百度 OCR API
  - 腾讯云 OCR SDK 3.1.1297
  - 华为云 OCR SDK 3.1.154

- **其他工具**：
  - Lombok 1.18.32（简化代码）
  - AspectJ 1.9.24（AOP）
  - OkHttp 4.12.0（HTTP 客户端）

### 数据库设计

#### 核心数据表
1. **用户与权限**
   - `user`：用户账户表
   - `permission`：权限资源表
   - `role_permission`：角色权限关联表

2. **统计数据表**
   - `national_stat`：全国统计数据
   - `province`：省份基础信息
   - `province_health_statistics`：省级综合统计
   - `population_stat`：人口统计
   - `institution_stat`：医疗机构统计
   - `personnel_stat`：医疗人员统计
   - `bed_stat`：床位统计
   - `service_stat`：医疗服务统计
   - `cost_stat`：医疗费用统计

3. **衍生指标表**
   - `per_10k_beds`：每万人床位数
   - `per_10k_institutions`：每万人医疗机构数
   - `per_10k_personnel`：每万人医疗人员数
   - `per_10k_service`：每万人医疗服务量
   - `per_capita_cost`：人均医疗费用
   - `latest_year_median_stats`：最新年份中位数统计

4. **系统管理**
   - `log`：操作日志表
   - `cat`、`tag`：文件分类标签
   - `uploadedfile`：上传文件记录

## 🚀 快速开始

### 环境要求
- JDK 21+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Hadoop/Hive（可选，用于大数据功能）

### 安装步骤

1. **克隆项目**
```bash
git clone <repository-url>
cd health-data-analys
```

2. **数据库初始化**
```bash
# 执行数据库脚本
mysql -u root -p < src/main/resources/database/health_analytics_db.sql
```

3. **配置文件**

创建 `application.yml` 或 `application.properties` 并配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/health_analytics_db
    username: your_username
    password: your_password
    
  redis:
    host: localhost
    port: 6379
    
# HDFS 配置（可选）
# 配置文件位于 src/main/resources/core-site.xml 和 hdfs-site.xml

# OCR 配置
baidu:
  ocr:
    accessToken: your_baidu_ocr_token

# AI 对话配置
chat:
  api:
    key: your_deepseek_api_key
    url: https://api.deepseek.com/v1/chat/completions
```

4. **编译运行**
```bash
mvn clean install
mvn spring-boot:run
```

5. **访问系统**
```
默认端口：8080
API 前缀：/api
```

## 📡 主要 API 接口

### 用户认证
```
POST /api/auth/register       # 用户注册
POST /api/auth/login          # 用户登录
GET  /api/auth/captcha        # 获取验证码
GET  /api/user/profile        # 获取用户信息
PUT  /api/user/profile        # 更新用户信息
PUT  /api/user/password       # 修改密码
```

### 统计数据
```
GET /api/nation/latest              # 全国最新数据
GET /api/nation/population/years    # 历年人口数据
GET /api/nation/institution/years   # 历年医疗机构数据
GET /api/province/{id}/latest       # 省级最新数据
GET /api/statistics/latest-province-data  # 省份对比数据
```

### 数据采集
```
GET /api/crawler/fetchTodayNews     # 抓取今日新闻
GET /api/crawler/homepage           # 获取缓存新闻
GET /api/crawler/fetchDaysNewsWithResult?days=3  # 抓取多日新闻
```

### 文件管理
```
POST /api/files/upload  # 文件上传（支持 OCR）
参数：
  - file: 文件
  - category: 分类
  - tag: 标签
  - year: 年份
```

### 日志管理
```
GET /api/logs/trend  # 日志趋势分析
参数：startDate, endDate
```

## 📊 数据指标说明

### 基础指标
- **人口总数**：单位万人
- **医疗机构总数**：各类医疗机构数量
- **医疗人员总数**：含医生、护士等
- **床位总数**：医疗机构床位数量
- **门诊诊疗人次**：单位万人次
- **入院人数**：单位万人
- **医疗总费用**：单位亿元

### 衍生指标
- **每万人床位数**：(床位总数 / 人口总数) × 10000
- **每万人医疗机构数**：(机构总数 / 人口总数) × 10000
- **每万人医疗人员数**：(人员总数 / 人口总数) × 10000
- **人均医疗费用**：医疗总费用 / 人口总数

## 🔐 权限说明

### 角色类型
- **ADMIN**：管理员，拥有所有权限
- **RESEARCHER**：研究人员，可查看和编辑研究数据
- **ANALYST**：分析人员，可查看统计数据和生成报告
- **USER**：普通用户，基础查看权限

### 权限资源
- `patient_info`：患者信息
- `research_data`：研究数据
- `statistical_analysis`：统计分析
- `report`：报告管理

### 权限动作
- `view`：查看
- `edit`：编辑
- `delete`：删除
- `generate`：生成

## 🗂️ 项目结构

```
src/main/java/com/healthdata/
├── VO/                      # 视图对象
├── annotation/              # 自定义注解
├── aspect/                  # AOP 切面
│   ├── LogAspect.java      # 日志切面
│   └── PermissionAspect.java # 权限切面
├── config/                  # 配置类
│   ├── SecurityConfig.java # 安全配置
│   ├── MyBatisPlusConfig.java
│   └── ChatConfig.java
├── controller/             # 控制器
├── crawler/                # 爬虫模块
│   ├── core/              # 核心接口
│   ├── impl/              # 各省份爬虫实现
│   ├── service/           # 爬虫服务
│   └── controller/        # 爬虫控制器
├── entity/                # 实体类
├── enums/                 # 枚举类
├── exception/             # 异常处理
├── mappers/               # MyBatis Mapper
├── service/               # 服务层
│   └── impl/             # 服务实现
├── utils/                # 工具类
│   ├── JwtUtils.java    # JWT 工具
│   └── HdfsUtil.java    # HDFS 工具
└── HealthDataSystemApplication.java

src/main/resources/
├── database/             # 数据库脚本
├── mapper/              # MyBatis XML
├── core-site.xml       # Hadoop 配置
└── hdfs-site.xml       # HDFS 配置
```

## 🔧 配置说明

### HDFS 配置
系统集成了 Hadoop HDFS 进行大文件存储，配置文件：
- `core-site.xml`：HDFS 地址配置
- `hdfs-site.xml`：DataNode 配置

### OCR 配置
支持多家 OCR 服务商：
- 百度智能云 OCR（主要使用）
- 腾讯云 OCR
- 华为云 OCR

需在配置文件中设置对应的 API Token。

## 📈 性能优化

1. **数据库优化**
   - 合理使用索引
   - 外键约束保证数据一致性
   - 按省份和年份建立联合唯一索引

2. **缓存策略**
   - Redis 缓存热点数据
   - 爬虫结果缓存减少重复请求

3. **并发处理**
   - 爬虫采用异步并发抓取
   - CompletableFuture 异步编程

## 🛡️ 安全特性

- JWT 令牌过期时间：2小时
- 密码 BCrypt 加密存储
- CORS 跨域保护
- SQL 注入防护（MyBatis-Plus）
- XSS 防护

## 📝 开发规范

### 代码规范
- 使用 Lombok 简化实体类
- 统一异常处理（GlobalExceptionHandler）
- 统一响应格式（CommonResponse）
- RESTful API 设计

### 日志规范
- 使用 SLF4J + Logback
- 关键操作记录审计日志
- 错误日志详细记录堆栈信息

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

[请根据实际情况添加许可证信息]

## 👥 联系方式

[请根据实际情况添加联系方式]

---

## 🎯 未来规划

- [ ] 数据可视化大屏
- [ ] 更多省份爬虫支持
- [ ] 机器学习预测模型
- [ ] 移动端适配
- [ ] 数据导出功能增强
- [ ] 实时数据监控
- [ ] 更完善的权限管理系统

---

**最后更新时间**：2025年12月

**项目版本**：0.0.1-SNAPSHOT
