## 29CM 의 상품 주문 프로그램

### 1. 환경
- Java 21
- Springboot 3.2.4
- H2 inMemory DB

### 2. 설치 및 실행
- 자바 21환경에서
```java
mvn clean package -Dspring.active.profile=local
```
- java -jar target/order_mng-0.0.1-SNAPSHOT.jar

### 3. 구조
- @RestControllerAdvice 로 Exception 처리
- 캐시 사용하여 리스트 조회 속도 향상 시도
- 트랜잭션 부분에 synchronized 동기화 


