
### 1. ✅ ლოგირება დაკონფიგურირებული

**რეალიზაცია:** ლოგირება კონფიგურირებულია სხვადასხვა application properties ფაილებში:

#### Production კონფიგურაცია (`application-prod.properties`):
- `logging.level.ge.tsu.boredreader=INFO`
- `logging.level.org.springframework.web=WARN`
- `logging.level.org.hibernate=WARN`
- `logging.level.org.springframework.security=WARN`

#### Development კონფიგურაცია (`application-dev.properties`):
- `logging.level.ge.tsu.boredreader=DEBUG`
- `logging.level.org.springframework.security=DEBUG`
- `logging.level.org.hibernate.SQL=DEBUG`
- `logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE`
### 2. ✅ ტესტები
[E2E ტესტი ლოგინ გვერდისთვის](src/test/java/ge/tsu/boredreader/prodTest.java)

**3.✅ Deployment ვარიანტი (Docker)**

[Docker კონფიგურაცია](Dockerfile)


### 4. ✅ მონიტორინგი დაკონფიგურირებული (Actuator)
#### [Production კონფიგურაცია](src/main/resources/application-prod.properties)
#### [Development კონფიგურაცია](src/main/resources/application-dev.properties)
**[Info Endpoint კონფიგურაცია](src/main/resources/application.properties)**

## Actuator Endpoints

### Production გარემოში:
- `/actuator/health` - აპლიკაციის მდგომარეობა
- `/actuator/info` - აპლიკაციის ინფორმაცია
- `/actuator/env` - გარემოს კონფიგურაცია