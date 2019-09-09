# housing-finance-api

## 사용기술
- java11
- spring-boot 2
- h2db
- jpa
- maven

## 문제해결전략
### api
#### 주택금융공급기관 조회 api 
- url : GET /api/v1/housing/institutes
#### csv파일 업로드하여 저장하는 api
- url : POST /api/v1/housing/finances
#### 업로드된 파일의 내용얼 조회하는 api
- url : GET /api/v1/housing/finances/{historyId}
#### 년도별 각 금융기관의 지원금액 합계를 출력하는 API
- url : GET /api/v1/housing/finances/years/summary
#### 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API
- url : GET /api/v1/housing/finances/institute-years/largest-amount
#### 전체 년도(2005~2016)에서 기관별 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
- url : GET /api/v1/housing/finances/institute/{instituteName}/support-amount

### csv파싱
org.apache.commons 의 commons-csv를 이용하여 파싱<br>
Excel title을 이용해서 기관별 매핑을 하려고 했으나 title에 기관명 말고 금액 단위까지 있어서 <br>
기관별 excel 열 위치가 동일하다는 가정으로 개발<br>

### 엔티티
연도 / 월 / 기관 / 금액 기준으로 Entity 를 구성하고 필요 정보를 application에서 summary처리 하였으나<br>
요구 사항 확인시 연도별 집계 Entity가 필요하다고 판단하여<br>
연도 / 월 / 기관 / 금액 기준의 Entity와<br>
연도 / 기관 / summary금액 기준의 Entity를 구성함<br>

### 고민거리들
csv저장시 기존 저장되어있는 기관의 경우 Duplication Exception이 발생 하는데 이부분에 대한 failover방안 필요<br>
csv저장시 기관에 대한 위치가 고정으로 가정하였기 때문에 순서가 다르게 들어오는 csv파일에 대한 고려 필요<br>

## 빌드
mvn clean<br>
mvn package<br>
java -jar target/hf-api-0.0.2-SNAPSHOT.jar<br>
