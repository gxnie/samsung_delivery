# samsung_delivery 🚙
## 재용이연이 만든 삼성가 배달 앱🧍🏻🧍🏻‍♀️🧍🏻‍♂️🧍🏻 
### 아웃소싱 프로젝트 배달 앱 만들기 🧑🏻‍💻
------------
###  프로젝트 기간
#### 🗓 2024.12.03 ~ 2024.12.09
------------
## 🛠 개발 환경 
- Tech : ![Spring](https://img.shields.io/badge/Spring-6DB33F.svg?&style=for-the-badge&logo=Spring&logoColor=white) 3.3.5
- IDE : ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
- JDK : ![Java](https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white) 17
- DB : ![MySQL](https://img.shields.io/badge/MySQL-4479A1.svg?&style=for-the-badge&logo=MySQL&logoColor=white) Ver 8+
------------
## 와이어프레임 📝

<details>
  <summary>  와이어프레임 [figma] </summary>

  ### User
  ![와이어프레임 User 2024-12-08 오후 11 18 08](https://github.com/user-attachments/assets/3d54ccfe-18d8-495b-a7b9-30b145206162)

  ### Store
  ![와이어프레임 Store 2024-12-08 오후 11 18 23](https://github.com/user-attachments/assets/47d3c626-8721-48ab-8e20-35f3b7897a36)

  ### Menu
  ![와이어프레임 Menu 2024-12-08 오후 11 18 31](https://github.com/user-attachments/assets/1312e26d-692e-4c00-a506-4daf36e49926)

  ### Order
  ![와이어프레임 Order 2024-12-08 오후 11 18 43](https://github.com/user-attachments/assets/4753487b-5f8d-4ce6-96c1-1c6c32833f7f)

  ### Review
  ![와이어프레임 Review 2024-12-08 오후 11 18 37](https://github.com/user-attachments/assets/7c64df2b-3d1d-4350-bfb4-38e6dbcc4572)

  </details>


------------
## ERD 📁

![최종 ERD 2024-12-08 오후 11 05 15](https://github.com/user-attachments/assets/edf2d4ba-023c-4fbc-82e0-5c2c44740bf2)

------------
## API 명세서 📋

### [Postman samsung_delivery API 명세서](https://documenter.getpostman.com/view/39375040/2sAYBd6ndt)
  
---------------
## 기능 구현 🖥  

### 공통
- 클라이언트가 서버로 요청을 보낼 때마다 해당 Cookie를 자동으로 포함시켜 로그인 인증 처리
- 수정, 삭제 시 Session 에 저장된 로그인된 유저 정보를 통해 본인만 수정 / 삭제 
- 회원, 가게, 메뉴 삭제할 때 Hard Delete 대신, DeleteMapping 후 Enum 상태 값을 변경하여 Soft Delete로 구현
- cascade 영속성 전이를 활용해 User 데이터가 삭제될 때 관련된 데이터가 같이 삭제
- GlobalExceptionHandler 를 구현하여 Error Code의 message와 status 를 일관되게 처리
- coupon type, coupon status, menu status, order status, point status, store status, user role, user status → Enum으로 관리 


### 회원가입, 로그인 `User`
- 로그인 후 서버는 클라이언트에게 JSESSIONID를 쿠키에 저장
- Enum으로 `UserRole`을 총 3가지로 구성하여 `User(고객)`, `Owner(사장)`, `Admin(관리자)` 권한 구분
- 이메일 중복 허용 불가, 탈퇴된 회원의 이메일로 재가입 불가
- `PasswordEncoder`를 만들어 비밀번호 `Bcrypt`로 암호화
- 정규식으로 비밀번호 유효성 검사(대소문자, 숫자, 특수문자 하나 이상 포함, 총 8자 이상)

### 가게 `Store`
- 사장님 `OWNER` 권한 : 가게 만들기, 가게 정보 수정, 가게 폐업 처리
- 사장 한 명은 3개 이하의 가게를 오픈 (폐업한 가게는 포함되지 않음)
- 고객은 가게 Id로 가게를 찾아볼 수 있음
  - 다건 조회시 메뉴를 볼 수 없음.
  - 단건 조회시 메뉴를 볼 수 있음.

### 메뉴 `Menu`
- 사장님 `OWNER` 권한 : 메뉴 만들기, 메뉴 수정, 메뉴 삭제
- 본인의 가게에만 메뉴를 등록할 수 있음
- 가게 조회 시 메뉴를 조회할 수 있음
- 메뉴가 품절된 경우 `MenuStatus`를 `OUT_OF_STOCK` 으로 변경
  - 품절된 메뉴는 주문 불가

### 주문 `Order`
- 고객님 `USER` 권한 : 주문 하기
- 바로 주문하기, 장바구니에서 주문하기
  - 주문에는 하나의 메뉴만 주문 가능
  - 최소 주문 금액을 넘기지 않으면 주문 불가
- 주문 시 메뉴와 수량, 총 가격을 확인
- 사장님 `OWNER` 권한 : `OrderStatus` 로 주문 상태 변경 가능 
  - `ORDER_COMPLETED`, `ACCEPT_ORDER`, `COOKING`, `COOKING_COMPLETED`, `ON_DELIVERY`, `DELIVERY_COMPLETED`, `REJECTED`
  - 주문 거절 `REJECTED` 로 변경된 주문은 다시 주문 수락 `ACCEPT_ORDER` 불가

### 리뷰 `Review`
- 고객님 `USER` 권한 : 리뷰 작성 하기
- 리뷰에 별점을 부여 (1~5)
- 배달 완료 `DELIVERY_COMPLETED` 상태가 아닌 주문에 대해 리뷰를 작성할 수 없음
- 가게에서 생성일자 기준 최신순으로 리뷰 다건 조회  `/reviews?storeId=1&sort=rating`

### 장바구니 `Cart`
- 고객님 `USER` 권한 : 장바구니
- 장바구니에 담아 둔 메뉴를 주문할 수 있음. 메뉴 하나(수량은 여러 개 가능)당 주문 하나
  - 다른 가게의 메뉴를 담을 수 없음
- 장바구니는 Cookie에 encoding 되어 저장, 하루가 지나면 자동으로 삭제
- 장바구니에 담긴 주문 전체 수정 가능
  - 메뉴, 수량 변경
- 장바구니에 새로운 메뉴를 추가
  - 장바구니에 없는 메뉴를 담았을 경우 메뉴가 추가됨
- 장바구니에서 주문 시 장바구니 초기화

### 대시보드 `Dashboard`
- 관리자 `ADMIN` 권한 : 통계 조회
  - 전체 일간 주문 건수/ 총액 조회
  - 전체 월간 주문 건수/총액 조회
  - 가게별 일간 주문 건수/총액 조회
  - 가게별 월간 주문 건수/총액 조회
  - 카테고리별 일간 주문 건수/총액 조회
  - 카테고리별 월간 주문 건수/총액 조회
 
### 포인트 및 쿠폰
- 사장님 `OWNER` 권한 : `Coupon` 생성 및 지정 고객에게 쿠폰 발급
- 고객님 `USER` 권한 : `Point`과 `Coupon` 을 사용하여 주문 가능, `Point` 보유 현황 조회
  - 쿠폰은 한 번만 사용할 수 있음. 사용 후에는 재발급 받아야 함.
- `Point` 는 주문 배달 완료 시 자동 적립 3%
  - `Potin`와 `Coupon`을 동시에 적용하여 주문 가능
- 유효기간이 만료되면 `Point` 차감 및 `Coupon` 소멸
- `Coupon` 은 정률(%) / 정액(₩) 두 종류
  - 총 발급 개수, 발급 개수 제한 가능
  
------------
## 시연 영상
### [재용이연의 삼성 딜리버리 배달 앱 시연 영상](https://youtu.be/et6aDVIeg0Q)

------------
## 팀원 👥

 |이름|역할|MBTI|Github|블로그|
 |------|------|------|------|------|
 |김영웅 |팀원|ESTJ|[herokim97](https://redbull97.tistory.com/)|[tistory](https://velog.io/@qkrdpwls2002/posts)|
 |박용재 |팀원|INTP|[SearchColor](https://github.com/SearchColor)|[velog](https://velog.io/@skykid/posts)|
 |하진이 |팀장|ISFP|[gxnie](https://github.com/gxnie)|[tistory](https://geniebox.tistory.com/)|
 |허수연 |팀원|INTJ|[sooyeoneo](https://github.com/sooyeoneo)|[tistory](https://sooyeoneo.tistory.com/)|

------------
## Tasks ✍🏻
### 김영웅 
    1. API 명세서
    2. 가게 CRUD
    3. 관리자 대시보드
    
### 박용재
    1. 와이어프레임 
    2. 주문 CRUD
    3. 포인트 및 쿠폰
    
### 하진이
    1. API 명세서
    2. github repository 관리
    3. 회원가입/로그인, 리뷰 CRUD
    4. 소셜 로그인
    
### 허수연
    1. ERD
    2. 메뉴 CRUD
    3. 장바구니 Cookie
    4. 시연 영상 & README.md

------------

## 프로젝트를 마무리하며.. 🛎

* 김영웅 : 심도 있는 학습과 JPQL을 활용한 데이터 사용한 점이 흥미로웠으며, 협업 시 설계의 중요성을 느꼈습니다.

* 박용재 :

* 하진이 : 

* 허수연 : 배달 앱 개발에서 메뉴 CRUD, 장바구니 데이터를 Cookie에 저장하는 로직을 구현하였습니다.
평소에 쉽게 접할 수 있는 배달 앱 기능이 여러 비즈니스 로직이 잘 연결된 구성으로 이루어져서 작동이 된다는 것을 직접 경험하게 되는 시간이었습니다. 개발자는 세상의 기반을 만드는 직업이라는 사실을 여실히 깨닫게 됩니다.
실제 기능을 구현하면서 설계 부분과 맞지 않는 방향으로 갈 때, 코드를 여러 번 새로 짜게 되면서 어려움을 겪었습니다. 해결되지 않는 상황을 팀원과 공유하고 함께 해결해 가는 과정이 즐거웠습니다.
특히 장바구니 데이터를 DB가 아닌 Cookie에 저장하는 로직과 장바구니에서 주문을 할 때 장바구니가 초기화되는 로직을 구현하는 것에 시간이 많이 걸렸지만, 팀원의 의견을 반영하여 코드 수정의 방향을 잡아갔던 것이 큰 도움이 되었습니다. 함께 즐거운 코딩을 나눌 수 있어서 뜻 깊은 프로젝트였습니다.

------------
## 트러블 슈팅 🎯
#### [Spring Cookie 생성 오류](https://sooyeoneo.tistory.com/84)



