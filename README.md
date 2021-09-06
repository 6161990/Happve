## Happve
> #### 🍵 서울특별시 채식 식당 가이드 Happve(Happy Vegan)

<br>




<br>

## 1. 제작 기간 & 참여 인원
* 2021년 6월 25일 ~ 2021년 7월 11일
* 개인 프로젝트 

<br>

## 2. 사용기술
`Back-end`   
* Java 15
* MySQL 10.4.19v
* Apache Tomcat 9.0v
* JSP 2.3
* Servlet 4.0

`Front-end`  
* BootStrap 
* HTML, CSS3 

`Tool & Program`   
* Eclipse 2021-03
* Navicat Premium 15.0.25v
* XAMPP Control Panel v3.3.0
* Visual Studio Code


<br>


## 3. ERD 설계 

<img width="732" alt="20210710100653" src="https://user-images.githubusercontent.com/74708028/125147491-98b75e80-e166-11eb-923a-8e140056fc10.png">


<br>

## 4. 요구사항

#### ▶ [블로그 요구사항 정리 파일](https://github.com/6161990/Blog-Project/files/6794610/default.xlsx)


<br>

## 5. 핵심기능
#### 이 서비스의 핵심 기능은 컨텐츠 등록 기능입니다. 
#### 사용자들이 작성한 영화 리뷰, 기사 등에 대한 컨텐츠를 모든 사용자들이 볼 수 있습니다.
#### 글 등록 뿐만 아니라 사진업로드, 글 수정, 글 삭제 또한 가능합니다.
#### 홈에서는 swiper-slide로 최신글과 인기글, 내가 관심갈만 한 글을 볼 수 있습니다.

<br>


## 6. 핵심 트러블 슈팅 
▶ 6.1 blog-detail.jsp로 넘어가기 전 컨텐츠 필터 처리 문제 
* `blog-detail.jsp` 는 작성글 상세 페이지입니다. `글 작성(write-post.jsp)`, `나의 가장 최신글, 글 목록(author-post.jsp, index.jsp)` 총 3 곳에서 `PostDetailProc`를 거쳐 blog-detail.jsp로 넘어갑니다.
* `PostDetailProc`의 역할은 다음과 같습니다. 
   * ##### 넘어온 페이지로부터 게시물 고유번호(post_idx)를 얻어 그 글의 객체를 request setting
   * ##### 게시물 고유 번호로 이미지 테이블에서 저장한 이미지 request setting 
   * ##### 사이드에 위치한 `최근 글 목록`에 뿌려줄 글 목록 얻어와 request setting 
   * ##### 하단에 위치한 `관련글`에 뿌려줄 글 목록 얻어와 request setting 
   * ##### 해당 글의 카테고리 이름을 카테고리 테이블에서 얻어와 request setting  
   
  <br>
  
✔ 이 중 포인트는 넘어온 페이지로부터 게시물 고유번호(post_idx)를 얻는 것이었습니다. 
* request가 어디서 넘어왔냐에 따라 상세 페이지에서 보여줄 data가 달라지기 때문이었습니다. 

  > ##### `글 작성`에서 넘어왔다면, 내가 방금 작성한 글이 잘 등록되었는지 바로 확인하는 용도로 보여주고 싶었고
  > ##### `나의 가장 최신글` 메뉴를 클릭해 넘어왔다면, 최근 내가 쓴 글을 보여주고 싶었습니다.
  > ##### 그 외 index.jsp , author-post.jsp 에서도 글 상세를 보기위해 `PostDetailProc`로 넘어옵니다.     

* 하지만 경우마다 servlet을 따로 만드는 것에 대해 고민되었습니다.     
* 넘겨받는 게시물 고유번호의 key값을 각각 다르게 받아보면 어떨까 생각했습니다. 📝 [코드확인](https://github.com/6161990/Blog-Project/blob/34ed83a1dcbf972e363469d04d58d54406ea3896/Blog/src/main/java/controller/PostDetailProc.java#L53)

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

#### index.jsp 
> #### 블로그의 홈 화면 페이지      
> ##### `오늘의 랜덤 리뷰`  회원들이 작성한 글 리스트에서 랜덤으로 한 건을 표시      
> ##### `인기 글`  조회수 순으로 인기글 랭크인, 6건 씩 2번 swiper-slide로 총 12건 표시    
> ##### `인기주제`  프로그래밍 구현 없이, 화면에서 처리    
> ##### `내가 관심갈 만한 글`  내가 가장 최근 작성한 글과 같은 카테고리의 글들을 N건 모아 swiper-slide로 보여줌    
> #####                         (비회원 또는 로그인 안한 사용자의 경우 글작성 유도하는 버튼 표시)    
> ##### `떠오르는 신작 예고편`  유튜브 영상을 링크하여 영화 예고편 보여주기 
> ##### `로그아웃`  로그인 된 사용자의 경우 logout 버튼    

<br>

#### register.jsp
> #### 회원가입 페이지
> ##### `사용자 ID` `이름` `이메일` , `패스워드`를 입력받음

<br>

#### login.jsp 
> #### 로그인 페이지 
> ##### 로그인 실패 시 request `msg`에 값이 있다면 로그인 실패 메세지를 던져주고, 재입력 유도
> ##### 로그인 성공 시 회원이 가장 최근 작성한 글을 `myPost`에 담아 index에 전달
> ##### 하단에 `비밀번호 찾기`와 `회원가입`버튼으로 각각 searchPass.jsp 와 register.jsp 유도
> ##### 해당 홈페이지 전역적으로 로그인이 필요한 서비스를 이용하려는 경우 login 먼저 이용하게 되어있음

<br>

#### searchPass.jsp
> #### 비밀번호 찾기 페이지
> ##### `사용자 ID` 와 `이메일주소` 가 일치하면 비밀번호 노출

<br>

#### memberInfo.jsp
> #### 회원 프로필 페이지
> ##### 회원 이름과 아이디를 확인할 수 있으며 `프로필 수정` 버튼으로 profilePass.jsp 유도

<br>

#### profilePass.jsp
> #### 회원 비밀번호 변경 페이지
> ##### `사용자 ID` 와 `이름` 을 `readonly`로 표시하게하고, `현재 비밀번호` 와 `새 비밀번호` 입력
> ##### 현재 비밀번호가 맞으면 새 비밀번호로 update

<br>

#### write-post.jsp
> #### 글 작성 페이지
> ##### `카테고리` `제목` `내용` `해시태그` `이미지(파일업로드)` 를 multipart로 data 전송

<br>

#### updateWrite-post.jsp
> #### 글 수정 페이지
> ##### `제목` `카테고리` `해시태그` `이미지` `내용` 수정 가능

<br>

#### blog-details.jsp 
> #### 작성한 글을 클릭하면 볼 수 있는 글 상세 페이지 
> ##### `작성한 글의 내용`
> ##### `회원 프로필`
> ##### `Swiper-slide`를 통한 `같은 카테고리의 다른 글` 과 `블로그 최신 작성 글` 목록 표시 

<br>

#### about-us.jsp 
> #### 홈페이지 소개글 페이지 
> ##### 블로그 관리 기능은 따로 구현하지 않으므로 화면에서 처리

<br>

#### author-post.jsp 
> #### 로그인 한 사용자의 글 목록 페이지
> ##### 로그인 사용자가 아직 등록한 글이 없다면 'no-post.jsp'로 보내 글쓰기 유도
> ##### 로그인하지않은 사용자일 경우 'login.jsp'로 msg 담아 보내 로그인 유도

<br>

#### category.jsp 
> #### 카테고리 별 글 목록 페이지

<br>

#### contact-us.jsp 
> #### 비회원을 포함한 사용자가 문의를 남길 수 있는 페이지
> ##### 보통 문의 확인을 작성한 사용자에게 email 보내주지만, 여기서는 DB에 문의 내역을 저장하기만 구현

<br>

#### error-404.jsp 
> #### 각 페이지에서 에러 발생시 가게되는 페이지 
> ##### 각 페이지에 에러 페이지를 명시함

<br>

#### noPost.jsp
> #### `내가 작성한 최신 글`과 `내 글 목록`에 data가 없을 경우 보여지는 페이지
> ##### 글 작성 페이지인 write-post.jsp로 유도

<br>



#### 


<br>
