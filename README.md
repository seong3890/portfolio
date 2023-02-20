# portfolio
웹쇼핑몰 프로젝트 입니다.<br/>
인프런 김영한님 강의를 베이스로 만들었습니다.
<div>
개발:유명성 <br/>

JPA를 이용해 패치조인,배치,dto클래스를 이용해 최적화 하는데 집중함. <br/>

was 서버 배포, 스프링 시큐리티 적용 예정 <br/>
was 서버 putty로 ec2 접속<br/>
리펙토링 + 업데이트 중 <br/>


</div>
<br/>
<h2>배포환경</h2>
<div>
<ul>
<li>was 예정</li>
</ul>
</div>
<h1>사용한 기술스택</h1>
<div>
    <ul>
        <li>JAVA8</li>
        <li>Spring Boot</li>
        <li>Spring Interceptor</li>
        <li>Spring Data Jpa</li>
        <li>Querydsl</li>
        <li>Thymeleaf</li>
        <li>Mysql</li>
    </ul>
</div>
<br/>
<br/>

<h1>구현 기능</h1>
<div>
    <ul>
       <li>로그인,로그아웃,회원가입 </li>
	     <li>CRUD</li>
	     <li>게시판 페이징</li>
	     <li>상품등록</li>
	     <li>주문</li>
	     <li>주문목록</li>
     </ul>

  </div>
<div>
   <h1>트러블 슈팅</h1>
<details>
<summary>member.findAll() 조회가 안 되는 문제 발생</summary>
 osiv(Open Session In View: 영속성 컨텍스트를 뷰까지 적용)<br/>
  를 잠시 꺼놓아서 문제가 발생하였다. 다시 켜놓으니까 문제 해결<br/>
</details>

<details>
<summary>타임리프에서 LIst<inquiry > 를 모델로 넘겼는데 ${inquiry.name} 이 안됨.</summary>
th:each="inquirys:${inquiry}" 로 해결
</details>


<details>
<summary>N+1 문제</summary>
패치 조인과 배치 설정, DTO 변환으로 해결하고<br/>
일대다 관계는 따로 MAP<Long,DTO>로 만들어서 foreach로 값을 채워주었다.
</details>	

<details>
<summary>페이징 오류</summary>
카운트 값을 따로 생성해서 해결<br/>
</details>
	
</div>
<div>
<h1>느낀점</h1>	
먼저 메모를 따로 하지 않아 일지를 작성하는데 어려움을 느낌<br/>
aws 배포, 상품 삭제, 시큐리티 적용 등 아직 할 게 산더미이다.</br>
2023-02-02
	
	
</div>
<div>
<h1>사진</h1>
<details>
<summary>사진</summary>
<img width="80%" src="https://user-images.githubusercontent.com/74175871/216360666-292ee415-bad8-4476-bc4f-f46068d4ea91.JPG"/><br/>
<img width="80%" src="https://user-images.githubusercontent.com/74175871/216359902-a5bdbd28-34d8-4137-b50d-6781b112d5e8.JPG"/><br/>
<img width="80%" src="https://user-images.githubusercontent.com/74175871/216360493-7d745143-302a-4044-915c-da223ae3ae1e.JPG"/><br/>
인프런-JPA 최적화 강의 참고</br>
<img width="80%" src="https://user-images.githubusercontent.com/74175871/216360807-e6852f10-1bda-463e-a010-471ba2c36002.JPG"/><br/>
<img width="80%" src="https://user-images.githubusercontent.com/74175871/216360895-8097332f-c0dd-4d83-94a9-a99d36a5b5b2.JPG"/><br/>

</details>
</div>

