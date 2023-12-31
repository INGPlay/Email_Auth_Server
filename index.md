# EmailAuthServer

## 프로세스
### 요청 단계 (request)
- POST 요청
- 인증이 끝나고 리다이렉트 할 url 파라미터 필요
- 데이터베이스 내에서 가입된 이메일이 존재하는지 boolean 리턴하는 api url 필요(추후)
- UUID 발급 -> 인증키로써 Cache 저장
- UUID를 가지는 입력페이지 주소로 리다이렉트

### 이메일 입력단계 (input)
- GET 요청
- 이메일 입력 페이지

### 이메일 전송 (send)
- POST 요청
- 먼저 데이터베이스 내에서 가입된 이메일이 존재하는지 확인(추후)
- 이메일로 6자리의 랜덤한 숫자 발송
- UUID를 가지는 인증페이지 주소로 리다이렉트

### 이메일 인증페이지 (check)
- GET 요청
- 이메일 인증코드 입력

### 이메일 인증완료 (success)
- 처음에 받았던 url로 리다이렉트
- 인증완료 정보 보내기

### 이메일 인증실패 (fail)
- 이메일 입력단계로 uuid와 함께 리다이렉트

## Entity 연관관계
- 최대한 N:1(@ManyToOne)만 사용, 필요 시 편의메소드와 함께 양방향 관계 사용, 1:N(@OneToMany)만 사용하지 말 것