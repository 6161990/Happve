
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

<br>


## 3. ERD 설계 

<img width="500" alt="Happve-ERD" src="https://user-images.githubusercontent.com/74708028/132154658-c11a63ac-b37e-4af3-a3ee-4970a13a6c6f.png">


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
   * ##### 리뷰쓰기 & 즐겨찾기
   * ##### 다른 사용자들이 작성한 해당 비건 식당 리뷰
   
   
  <br>
  
✔ JSONParse와 Jackson 라이브러리인 ObjectMapper를 이용해 Json 데이터를 자바 객체로 parsing 하는 것이 이 페이지의 핵심 구현입니다. 

  > ##### 트리 구조로 되어있는 JSON node를 JSONObject와 JSONArray 형변환을 통해 꺼내어 RestaurantAPI 자바 객체로 매핑 시켜 주었습니다. 
  > ##### @JsonProperty를 이용해 대문자로 이루어진 오픈 API 데이터를 소문자로 이루어진 자바 객체 변수에 매핑되도록 지정해주었습니다. 
  > ##### 그 외 index.jsp , author-post.jsp 에서도 글 상세를 보기위해 `PostDetailProc`로 넘어옵니다.     

📝 [코드확인](https://github.com/6161990/Happve/blob/35cf106adf4be73e7dfddc681fa759e7ce1f35c4/src/main/java/com/kh/happve/controller/ApiController.java#L84)

<br>

📍 기존코드 
* `글 작성(write-post.jsp)`, `나의 가장 최신글`, `글 목록(author-post.jsp, index.jsp)` 각각의 servlet을 만들어 게시물 고유번호 받는 경우,  
* 글 상세 페이지 `blog-detail.jsp`로 가는 모든 경우의 수마다 servlet을 만들면 중복되는 코드가 많았기 때문에 비효율적이었습니다. 
 
📍 개선된 코드 
* 사용자가 보고싶은 게시물 고유번호를 넘겨 받을 때 key값을 각각 다르게 설정하기
* 조건문으로 null 값을 체크해 post_idx 확인 


<br>


## 7. 그 외 트러블 슈팅
* 테이블에 방금 INSERT한 데이터 RETURN 처리 문제 📌[코드확인](https://github.com/6161990/Blog-Project/blob/d1d108e7898eb24ad37249049a3f27f4c4410098/Blog/src/main/java/dao/PostDAO.java#L66)
* `내가 관심갈만한 글` 에서 '내'가 없는 경우, 예외 처리 문제 📌[코드확인](https://github.com/6161990/Blog-Project/blob/d1d108e7898eb24ad37249049a3f27f4c4410098/Blog/src/main/webapp/index.jsp#L850)
  * ###### 홈화면(index.jsp)에서 `내가 관심갈만한 글` 목록은 내가 작성한 최신 글과 같은 카테고리 글 목록을 가져오는 것이었습니다.    
    ###### 하지만, 비 로그인 사용자나 아직 작성글이 없는 사용자의 경우는 data가 없어서 errorPage로 가게되었습니다.     
    ###### 이런 사용자의 경우 `내가 관심갈만한 글` 대신 `전체글목록`을 띄워줄까 고민했습니다.    
    ###### 하지만 해당 사이트가 이제 막 서비스를 시작했다는 가정 하에 많은 데이터를 확보하는 것이 우선이라 판단하여    
    ###### 아직 작성글이 없다면 글작성을 유도하는 버튼으로 예외처리를 했습니다.     
  

<br>


## 8. 페이지 설명 
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
> ##### 사용자가 건의 내용이 전달되었는지 알 수 있도록 RedirectAttribute로 상태 값을 true로 전달
> ##### View에서 status가 true면 "건의 내용이 전달되었습니다"라는 메시지가 출력됨

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

