
## Happve
> #### 🍵 서울특별시 채식 식당 가이드 Happy Vegan

<br>

<img width="700" height="400" alt="20210710100653" src="https://user-images.githubusercontent.com/74708028/132153689-1b503aa5-2449-44f0-b46f-039e82027830.png">

<br>

## 1. 제작 기간 & 참여 인원
* 2021년 08월 09일 ~ 2021년 08월 30일
* 팀 프로젝트 (6명)
* 맡은 역할 : 식당 상세보기, 건의하기, 리뷰 모아보기 페이지 기능 구현

<br>

## 2. 사용기술
`Back-end`   
* Java 16
* Amazon RDS for MySQL
* Spring boot
* Spring Security
* Spring JPA


`Front-end`  
* HTML, CSS3
* JavaScript, jQuery


`Tool & Program`   
* IntelliJ IDEA 2021-03v
* Navicat Premium 15.0
* Visual Studio Code
* Adobe XD
* Slack , Notion

<br>


## 3. ERD 설계 


<img width="707" alt="20210907155051" src="https://user-images.githubusercontent.com/74708028/132931744-0f1adcfb-98d2-4177-9bb6-d2fe7ddab367.png">


<br>

## 4. 요구사항

#### ▶ [Happve 요구사항 정리 파일](https://github.com/6161990/Happve/files/7113173/A._20210802.xls)

<br>

## 5. 핵심기능
#### 이 서비스의 핵심 기능은 식당 검색 기능입니다. 
#### 서울 전역에 있는 비건 식당을 검색하고 찾을 수 있습니다.
#### 검색 결과 페이지에서 식당을 클릭하면 [서울시 공공데이터 오픈 API](http://data.seoul.go.kr/dataList/OA-2741/S/1/datasetView.do)를 연동해 식당 정보를 확인할 수 있습니다.
#### 식당 검색 뿐만 아니라 식당에 대한 리뷰를 등록할 수 있습니다. 리뷰의 사진업로드, 글 수정, 글 삭제 또한 가능합니다.
#### 내가 가고 싶은 식당이나 자주 가는 식당에 대해 '찜' 기능을 이용할 수 있습니다.

<br>


## 6. 핵심 트러블 슈팅 
▶ 6.1 상세 페이지에서 오픈 API를 자바 객체로 매핑하는 문제
* `detail.html`는 비건 식당 상세 페이지입니다. 해당 페이지에서 사용자는 다음과 같은 정보를 알 수 있습니다.
   * ##### 오픈 API를 이용한 비건 식당의 정보
   * ##### 카카오 API를 이용한 지도상 위치 정보
   * ##### 다른 사용자들이 작성한 해당 비건 식당 리뷰   
      
  
* 여기서 핵심 구현은 JSONParse와 Jackson 라이브러리인 ObjectMapper를 이용해 `Json 데이터를 자바 객체로 parsing` 하는 것입니다.
  * ##### 식당의 고유 번호를 @PathVariable로 받아와 해당 식당 데이터에 접근합니다. 
  * ##### 트리 구조로 되어있는 JSON node를 JSONObject와 JSONArray 형변환을 통해 꺼내어 RestaurantAPI 자바 객체로 매핑합니다.

  <br>
  
📍 기존코드 

<br>
<img width="520" alt="20210907143525" src="https://user-images.githubusercontent.com/74708028/132293722-4691d7c8-662a-4028-a70b-39c3c9c0e0e5.png">

* writeValueAsString, readTree 메소드를 통해 JsonNode로 변환된 객체를 하나씩 변수에 담습니다.
* 변환을 마친 변수를 다시 자바 객체로 매핑합니다.
* 해당 사진에는 전부 다 담기지 않았지만 변수가 31개인 상황에서 해당 방식은 상당히 비효율적입니다.

<br>
  
  
📍 개선된 코드  📝 [코드확인](https://github.com/6161990/Happve/blob/35cf106adf4be73e7dfddc681fa759e7ce1f35c4/src/main/java/com/kh/happve/controller/ApiController.java#L84)
* String으로 변환된 JsonObject 객체를 readValue 메소드를 이용해 자바 객체로 매핑합니다.
* @JsonProperty를 이용해 대문자로 이루어진 오픈 API 데이터를 소문자로 이루어진 자바 객체 변수에 매핑되도록 지정합니다.

<br>
<br>
<br>
<br>

▶ 6.2 식당에 대한 리뷰 별점 List에서 `ArrayIndexOutOfBoundsException` 이 발생하는 문제
  
📍 기존코드

<img width="523" alt="20210907150325" src="https://user-images.githubusercontent.com/74708028/132292421-304dde47-ba64-4718-9223-d35b5efcef04.png">

* 컬렉션 배열에서 get 메소드로 인덱스를 차례로 꺼내면, 배열의 크기보다 크거나 음수 인덱스에 대한 요청일 경우 `ArrayIndexOutOfBoundsException` 이 발생합니다.
 
<br>
   
📍 개선된 코드 📝 [코드확인](https://github.com/6161990/Happve/blob/35cf106adf4be73e7dfddc681fa759e7ce1f35c4/src/main/java/com/kh/happve/controller/ApiController.java#L111)
* Stream의 limit 메소드와 filter, map 메소드를 통해 중간연산, forEach 메소드로 최종연산을 거쳐 데이터를 선별합니다. 
* null값이 아닌 데이터와 컬렉션에서 필요한 데이터만 정해진 숫자만큼 전달할 수 있어 효율적입니다. 

<br>
<br>
<br>
<br>

▶ 6.3 그 외 처리 사항
* 카카오 지도 API를 이용한 식당 지도상 위치 설정에서 x, y좌표변수 할당 처리  📌[코드확인](https://github.com/6161990/Happve/blob/8e2d1e37901836cb8480879f85f4a41d5d0fe95d/src/main/resources/templates/detail.html#L208)
  * ###### Thymeleaf의 inline 을 이용하여 받아온 x, y좌표를 javascript 변수에 할당했습니다.
* `건의하기`에서 사용자에게 메세지 전달 처리
  * ###### 사용자가 건의 내용이 전달되었는지 알 수 있도록, DB에 데이터가 삽입되면 id를 받아옵니다. 
  * ###### 받아온 id에 값이 들어있다면 Controller에서 RedirectAttribute로 상태 값을 true로 전달합니다. 📌[코드확인](https://github.com/6161990/Happve/blob/8e2d1e37901836cb8480879f85f4a41d5d0fe95d/src/main/java/com/kh/happve/controller/SuggestionController.java#L31)
  * ###### View에서는 status가 true면 건의사항 박스에 "건의 내용이 전달되었습니다"라는 메시지가 출력되어 사용자가 확인할 수 있습니다.  📌[코드확인](https://github.com/6161990/Happve/blob/8e2d1e37901836cb8480879f85f4a41d5d0fe95d/src/main/resources/templates/suggestion.html#L104)
    
<br>
<br>


## 7. 페이지 설명 
#### 본인이 구현한 부분만 ✔ 표시 

#### index.html 
> #### 사이트의 홈 화면 페이지      
> ##### 채식 관련 환경 정보, 검색 기능 제공

<br>

#### signup.html
> #### 회원가입 페이지
> ##### `사용자 닉네임` 또는 `이메일` , `패스워드`, `채식 유형` 을 입력받음
> ##### @Valid을 사용하여 유효하지 않은 값들에 대해 커스텀 검증함
> ##### 모든 검증을 마치고 회원가입이 완료 되었을 경우 `Spring Security`로 로그인 상태로 유지시키고 메인페이지로 redirect

<br>

#### login.html 
> #### 로그인 페이지 
> ##### 닉네임 또는 이메일, 비밀번호를 입력 받아 동작, 유효하지 않을 경우 메시지를 출력
> ##### 세션을 유지하는 방법으로 쿠키에 세션을 생성하기 위한 rememberMe 쿠키를 저장

<br>

#### ✔ details.html 
> #### 식당 상세 페이지
> ##### 오픈 API 정보와 연동해 식당 주소, 전화번호, 메뉴 등을 확인 할 수 있음
> ##### 다른 사용자가 작성한 해당 식당에 대한 리뷰를 볼 수 있음
> ##### 카카오 지도 API를 이용해 식당 위치를 지도상으로 확인할 수 있음

<br>

#### ✔ commu.html
> #### 사용자들이 작성한 리뷰 모아보기 페이지 
> ##### 리뷰 작성자, 리뷰 등록시 업로드한 이미지, 별점을 확인할 수 있음
> ##### 해당 리뷰를 클릭하면 리뷰한 식당 상세 페이지로 이동함

<br>

#### ✔ suggestion.html
> #### 건의하기 페이지
> ##### 이메일과 내용을 입력할 수 있으며 @Validated를 사용해 사용자의 입력을 검증함


<br>

#### error.html
> #### 에러 페이지 
> ##### spring boot default 에러 페이지로 사용함


<br>

#### mypage.html 
> #### 회원 프로필 페이지
> ##### 회원 이메일과 닉네임, 채식 주의자 유형을 확인할 수 있음

<br>

#### mypagebookmark.html 
> #### 즐겨찾기 페이지 
> ##### 평소 즐겨찾기를 해둔 식당과 사용자 본인이 작성한 리뷰를 한데 모아 확인할 수 있음
> ##### Ajax를 이용하여 DB와의 비동기 통신으로 구현

<br>

#### updatepassword / updateprofile.html 
> #### 회원 정보 수정 페이지 
> ##### 닉네임, 이메일, 비밀번호를 변경할 수 있음

<br>

#### writereview.html
> #### 리뷰 작성 페이지
> ##### 식당에 대한 리뷰와 별점, 이미지를 `multipart`로 전송
> ##### 로그인 되어있는 상태에서만 작성할 수 있도록 구현
> ##### 로그인 상태가 아닐 경우 로그인 페이지로 유도

<br>

#### searchresult / 2 / 3.html
> #### 검색 결과 페이지 
> ##### 검색 키워드를 식당 / 음식 으로 나누어 적용하고 사용자가 드롭박스에서 선택.


<br>
<br>

## 8. 협업방식
#### 💡 전체적인 코드 관리 `GitHub`,  실시간 이슈 & 코드리뷰 `Slack` , 진행상황 & 스케쥴 관리 `Notion` 

<br>

* `GitHub` (private repository)

<img width="606" alt="20210911112007" src="https://user-images.githubusercontent.com/74708028/132933058-692116c1-07e4-4ad5-bba1-d843cdfc6d5f.png">

<br>

* `Slack`

<img width="610" alt="20210911111313" src="https://user-images.githubusercontent.com/74708028/132933066-87422c2b-50f3-4a8c-9d5b-a06a516bfb38.png">

<br>

* `Notion` 

<img width="620" alt="20210911111633" src="https://user-images.githubusercontent.com/74708028/132933072-d098e8c0-2dcf-483f-b6f8-a3d41a306357.png">

