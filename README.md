<p align="center">
    <img src="https://github.com/newnyee/saving/assets/121937711/5ff9b3d4-00c8-47ed-83ee-8965aa494cfb" width="300" height="300"/>
</p>

# 💰 예산 관리 어플리케이션 'Saving'

## 목차

* [🎤 프로젝트 소개](#-프로젝트-소개)
* [🔧 기술 스택](#-기술-스택)
* [🔗 프로젝트 구조](#-프로젝트-구조)
* [💻 기본 구현 과정](#-기본-구현-과정)
* [💻 상세 구현 과정](#-상세-구현-과정)

<br>

## 🎤 프로젝트 소개
본 서비스는 사용자들이 개인 재무를 관리하고 지출을 추적하는 데 도움을 주는 애플리케이션입니다. 이 앱은 사용자들이 예산을 설정하고 지출을 모니터링하며 재무 목표를 달성하는 데 도움이 됩니다.

<br>
<br>

## 🔧 기술 스택

### 개발 환경
```
• IDE : IntelliJ IDEA Ultimate
• 언어 : Java 17
• 프레임워크 : Spring Boot 3.1.5
• 빌드 도구 : Gradle
• 데이터베이스 : MySQL 8.0
```
<br>

### 사용 기술 & 툴
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring Boot 3.1.5-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring Data JPA-gray?style=for-the-badge&logoColor=white"/></a>
<img src="https://img.shields.io/badge/QueryDSL-0078D4?style=for-the-badge&logo=Spring Data JPA&logoColor=white"/></a>
<img src="https://img.shields.io/badge/MySQL 8-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Gradle-4429A1?style=for-the-badge&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Junit-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Notion-FFFFFF?style=for-the-badge&logo=Notion&logoColor=black"/></a>
<img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=Jira%20software&logoColor=white"/></a>

<br>
<br>

## 🔗 프로젝트 구조

```
* 도메인 기본 구조*
├─ controller
├─ domain
│  ├─ entity
│  └─ repository
├─ dto
├─ exception
└─ service


*프로젝트 구조*
saving
├─ common
│  ├─ config
│  ├─ domain
│  ├─ exception
│  ├─ interceptor
│  ├─ response
│  └─ util
│
├─ category
│  │
│  └─ budget
│
├─ expense
│
├─ user
│
└─ webhook
```

<br>
<br>

## 💻 기본 구현 과정

[기본 구현 과정](https://github.com/newnyee/saving/wiki/%F0%9F%92%BB-%EA%B8%B0%EB%B3%B8-%EA%B5%AC%ED%98%84-%EA%B3%BC%EC%A0%95)은 Wiki에 따로 업로드 하였습니다.

[📌요구사항 분석](https://github.com/newnyee/saving/wiki/%F0%9F%92%BB-%EA%B8%B0%EB%B3%B8-%EA%B5%AC%ED%98%84-%EA%B3%BC%EC%A0%95#%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EB%B6%84%EC%84%9D)

[📌요구 사항 분석에 따른 개체 추출](https://github.com/newnyee/saving/wiki/%F0%9F%92%BB-%EA%B8%B0%EB%B3%B8-%EA%B5%AC%ED%98%84-%EA%B3%BC%EC%A0%95#%EC%9A%94%EA%B5%AC-%EC%82%AC%ED%95%AD-%EB%B6%84%EC%84%9D%EC%97%90-%EB%94%B0%EB%A5%B8-%EA%B0%9C%EC%B2%B4-%EC%B6%94%EC%B6%9C)

[📌개체 추출에 따른 ERD 생성](https://github.com/newnyee/saving/wiki/%F0%9F%92%BB-%EA%B8%B0%EB%B3%B8-%EA%B5%AC%ED%98%84-%EA%B3%BC%EC%A0%95#%EA%B0%9C%EC%B2%B4-%EC%B6%94%EC%B6%9C%EC%97%90-%EB%94%B0%EB%A5%B8-erd-%EC%83%9D%EC%84%B1)

[📌API 설계](https://github.com/newnyee/saving/wiki/%F0%9F%92%BB-%EA%B8%B0%EB%B3%B8-%EA%B5%AC%ED%98%84-%EA%B3%BC%EC%A0%95#API-%EC%84%A4%EA%B3%84)

<br>

## 💻 상세 구현 과정
이번 프로젝트를 구현하기 위해 여러가지의 가계부 어플리케이션을 이용해 본 후 가장 많이 사용되는 예산 방식, 지출 관리 방식, 결산 방식, 이용자의 카테고리 관리 방식으로 구현하였습니다.

<br>

### 📌Notion, JIRA를 이용한 프로젝트 관리
- Notion
  - 먼저 Notion을 이용하여 프로젝트를 계획하는것에서 부터 시작했습니다.
  - 이번 프로젝트는 일정이 굉장히 타이트 하여 첫날 설계까지 마친 후 API를 중요도 순으로 나누어 개발을 시작했습니다.

- JIAR
  - 팀 프로젝트를 진행할 때 처럼 JIRA를 이용하여 프로젝트를 관리하였습니다.
  - 인텔리제이와 연동하여 해당 티켓당 브랜치를 생성하여 PR 하는방식으로 진행하였습니다.
<p style="display: flex; justify-content: center" >
  <img src="https://github.com/newnyee/saving/assets/121937711/5ec57a59-4fe9-4513-af3a-3f9778b9ea2c" width="400"/>
  <img src="https://github.com/newnyee/saving/assets/121937711/2faa77a0-2750-42ac-8023-116b14e73c61" width="400"/>
</p>
<br>

### 📌사용자
- JWT 구현을 위해 [JWT util class](https://github.com/newnyee/saving/blob/main/src/main/java/com/saving/common/util/JwtUtil.java)를 생성하였습니다.
  - JWT util class는 `JWT의 생성과 인증`에 관련된 메소드들이 존재하는 클래스입니다.
- [Interceptor](https://github.com/newnyee/saving/blob/main/src/main/java/com/saving/common/interceptor/AuthenticationInterceptor.java)를 이용하여 JWT의 인증을 구현하였습니다.
  - Interceptor를 사용한 이유?
  - 예전 프로젝트때에는 Filter를 이용했었습니다. Filter를 이용하게되면 필터 되는 시점이 스프링 컨테이너에 도달하기 전이기 때문에 예외처리에 대한 부분을 따로 구현해야한다는 불편함이 있습니다.
  이번 프로젝트 일정이 타이트 하므로 시간들을 줄이기 위해 Interceptor를 이용하였습니다.

<br>

### 📌카테고리
- 서비스의 확장을 고려해 `사용자가 자신의 카테고리를 관리(CRUD)` 할 것을 고려하여 `카테고리 테이블`을 생성하였습니다.
- 서비스 확장을 위해 카테고리 삭제시 실제 삭제되는것이 아닌 상태만 변경되는것으로 테이블을 구성하였습니다. (실제 삭제시 지출이나 예산에서 카테고리 이름을 가지고올 경우 문제가 발생될 것이라 생각하였습니다.)
- 서비스의 확장을 고려해 `사용자 가입시점에 초기화되는 카테고리의 목록을 관리자가 관리` 할것을 고려하여 카테고리 목록과 별개로 `기본 카테고리 목록 테이블`을 생성하였습니다.

<br>

### 📌예산
- 예산의 기준이 카테고리가 되므로 `예산(budget)` 패키지를 `카테고리(category)` 패키지 하위로 구성하였습니다.

<br>

### 📌지출
- `지출 통계`에 사용될 `Dummy 데이터 생성`을 위해 [Procedure](https://github.com/newnyee/saving/wiki/Dummy-data%EB%A5%BC-%EC%9C%84%ED%95%9C-Procedure)를 이용했습니다.

    <img src="https://github.com/newnyee/saving/assets/121937711/27f83d69-9abc-4de3-b677-c6d4800f920d" width="400"/>
<br>

- [Scheduler](https://github.com/newnyee/saving/blob/main/src/main/java/com/saving/expense/service/ExpenseService.java)를 이용하여 매일 지정한 시간마다 [Discord webhook](https://github.com/newnyee/saving/tree/main/src/main/java/com/saving/webhook)을 통해 알림이 갈 수 있도록 구현하였습니다.

    <img src="https://github.com/newnyee/saving/assets/121937711/1a2a8167-e97b-41fd-b339-fff306dcdb96" width="500"/>

<br>

### 📌API 명세서
- 이번 프로젝트는 특히나 자주 사용되지 않는 영어 용어들이 많기때문에 API 사용시 혼동이 올 수 있을것을 생각하여 swagger를 이용하여 더욱 더 꼼꼼하게 명세서를 만들었습니다.

  <img src="https://github.com/newnyee/saving/assets/121937711/5c50ac7c-7e52-44ee-8f53-1a07eb4407df" width="600"/>
