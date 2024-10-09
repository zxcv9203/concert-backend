# 시퀀스 다이어그램

## 유저 대기열 토큰 기능 구현
- 요구사항
  - 서비스를 이용할 토큰을 발급받는 API를 작성합니다.
  - 토큰은 유저의 UUID 와 해당 유저의 대기열을 관리할 수 있는 정보 ( 대기 순서 or 잔여 시간 등 ) 를 포함합니다.
  - 이후 모든 API 는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능합니다.
  - 기본적으로 폴링으로 본인의 대기열을 확인한다고 가정하며, 다른 방안 또한 고려해보고 구현해 볼 수 있습니다.
### 토큰 발급 API
```mermaid
sequenceDiagram
autonumber
actor user as 사용자
participant server as 서버
participant waitQueue as 대기열
participant db as 데이터베이스

user ->> server : 토큰 발급 요청
server ->> db : 사용자 정보 조회
db ->> server : 사용자 정보 반환
alt 사용자 정보가 없는 경우
server ->> user : 400 에러 반환
end
server ->> db : 콘서트 정보 조회
db ->> server : 콘서트 정보 반환
alt 콘서트 정보가 없는 경우
server ->> user : 400 에러 반환
end
server ->> waitQueue : 현재 대기열 상태 조회
waitQueue ->> server : 결과 반환
server ->> server : 대기열 토큰 발급
server ->> db : 대기열 토큰 저장
db ->> server : 결과 반환
server ->> user : 대기열 토큰 반환
```
### 대기열 상태 확인 API (폴링)
```mermaid
sequenceDiagram
autonumber
actor user as 사용자
participant server as 서버
participant waitQueue as 대기열
participant processQueue as 처리열
participant db as 데이터베이스

user ->> server : 대기열 상태 확인
server ->> db : 토큰 정보 조회
db ->> server : 토큰 정보 반환
alt 토큰 정보가 없는 경우
server ->> user : 400 에러 반환
end
server ->> db : 토큰 만료 시간 갱신
db ->> server : 결과 반환
server ->> waitQueue : 대기열 상태 조회

alt 처리 가능한 상태인 경우
waitQueue ->> processQueue : 처리열로 이동
end
waitQueue ->> server : 대기열 결과 반환
server ->> user : 토큰 대기열 상태 반환
```

### 대기열 토큰 만료 / 진입 처리 스케쥴러
```mermaid
sequenceDiagram
autonumber
participant scheduler as 스케쥴러
participant waitQueue as 대기열
participant processQueue as 처리열

scheduler ->> processQueue : 참가열 토큰 만료 처리
scheduler ->> waitQueue : 대기열 토큰 만료 처리
scheduler ->> waitQueue : 대기열 토큰 상위 N개 리스트 조회
waitQueue ->> scheduler : 대기열 토큰 리스트 반환
scheduler ->> processQueue : 대기열 토큰 상위 N개 리스트 추가
```
## 예약 가능 날짜 / 좌석 API 구현
- 요구사항
  - 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API 를 각각 작성합니다.
  - 예약 가능한 날짜 목록을 조회할 수 있습니다.
  - 날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.
  - 좌석 정보는 1 ~ 50 까지의 좌석번호로 관리됩니다.
### 예약 가능 날짜 조회 API
```mermaid
sequenceDiagram
  autonumber
  actor user as 사용자
  participant server as 서버
  participant processQueue as 처리열
  participant db as 데이터베이스
  user ->> server: 예약 가능 날짜 조회
  server ->> processQueue: 처리열에 존재하는 토큰인지 조회
  processQueue ->> server: 결과 반환
  alt 처리열에 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 전달한 콘서트 id에 대해 조회
  db ->> server: 결과 반환
  alt 전달한 콘서트 id가 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 예약 가능 날짜 조회
  db ->> server: 결과 반환
  server ->> user: 결과 반환
```


### 좌석 조회 API
```mermaid
sequenceDiagram
  autonumber
  actor user as 사용자
  participant server as 서버
  participant db as 데이터베이스
  participant processQueue as 처리열
  user ->> server: 예약 가능 날짜 조회
  server ->> processQueue: 처리열에 존재하는 토큰인지 조회
  processQueue ->> server: 결과 반환
  alt 처리열에 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 전달한 콘서트 id에 대해 조회
  alt 전달한 콘서트 id가 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 해당하는 콘서트 날짜의 좌석 정보 조회
  db ->> server: 결과 반환
  server ->> user: 해당하는 콘서트의 날짜 좌석 정보 응답
```

## 좌석 예약 요청 API
- 요구사항
  - 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
  - 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
  - 만약 배정 시간 내에 결제가 완료되지 않는다면 좌석에 대한 임시 배정은 해제되어야 하며 다른 사용자는 예약할 수 없어야 한다.
### 좌석 예약 요청 API
```mermaid
sequenceDiagram
  autonumber
  actor user as 사용자
  participant server as 서버
  participant db as 데이터베이스
  participant processQueue as 처리열
  user ->> server: 좌석 예약 요청
  server ->> processQueue: 처리열에 존재하는 토큰인지 조회
  processQueue ->> server: 결과 반환
  alt 처리열에 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 콘서트 정보 조회
  db ->> server: 결과 반환
  alt 전달한 콘서트 id가 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 해당하는 콘서트의 날짜의 좌석 정보 조회
  db ->> server: 결과 반환
  alt 전달한 좌석 정보가 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  alt 해당 좌석이 이미 예약되거나 예약 대기 상태인 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 선택한 좌석 예약 대기 상태로 변경
  db ->> server: 결과 반환
  server ->> user: 좌석 예약 성공
```

## 잔액 충전 / 조회 API
- 요구사항
  - 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
  - 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
  - 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.
### 잔액 충전 API
```mermaid
sequenceDiagram
  autonumber
  actor user as 사용자
  participant server as 서버
  participant db as 데이터베이스
  participant processQueue as 처리열
  user ->> server: 잔액 충전 요청
  server ->> processQueue: 처리열에 존재하는 토큰인지 조회
  processQueue ->> server: 결과 반환
  alt 처리열에 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 토큰 정보 조회
  db ->> server: 결과 반환
  server ->> server: 토큰의 사용자 정보와 식별자의 사용자 정보가 같은지 확인
  alt 토큰의 사용자 정보와 식별자의 사용자 정보가 다른 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 해당 사용자에 대한 금액 정보 조회
  db ->> server: 결과 반환
  server ->> server: 금액 충전
  server ->> db: 금액 업데이트
  db ->> server: 결과 반환
  server ->> db: 금액 충전 히스토리 저장
  db ->> server: 결과 반환
  server ->> user: 금액 충전 결과 응답
```

### 잔액 조회 API
```mermaid
sequenceDiagram
  autonumber
  actor user as 사용자
  participant server as 서버
  participant db as 데이터베이스
  participant processQueue as 처리열
  user ->> server: 잔액 충전 요청
  server ->> processQueue: 처리열에 존재하는 토큰인지 조회
  processQueue ->> server: 결과 반환
  alt 처리열에 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 토큰 정보 조회
  db ->> server: 결과 반환
  server ->> server: 토큰의 사용자 정보와 식별자의 사용자 정보가 같은지 확인
  alt 토큰의 사용자 정보와 식별자의 사용자 정보가 다른 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 해당 사용자에 대한 금액 정보 조회
  db ->> server: 결과 반환
  server ->> user: 금액 충전 결과 응답
```
## 결제 API


### 결제 API
- 요구사항
  - 결제 처리하고 결제 내역을 생성하는 API 를 작성합니다.
  - 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료시킵니다.
```mermaid
sequenceDiagram
  autonumber
  actor user as 사용자
  participant server as 서버
  participant db as 데이터베이스
  participant processQueue as 처리열
  user ->> server: 좌석 결제 요청
  server ->> processQueue: 처리열에 존재하는 토큰인지 조회
  processQueue ->> server: 결과 반환
  alt 처리열에 존재하지 않는 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 해당 유저의 금액 조회
  db ->> server: 결과 반환
  server ->> server: 요청된 금액만큼 결제 진행
  alt 결제 금액이 부족한 경우
    server ->> user: 400 에러 반환
  end
  server ->> db: 금액 정보 업데이트
  db ->> server: 결과 반환
  server ->> db: 결제 정보 히스토리 저장
  db ->> server: 결과 반환
  server ->> db: 해당 좌석을 예약 완료 상태로 변경
  db ->> server: 결과 반환
  server ->> processQueue: 전달한 토큰 만료 처리
  processQueue ->> server: 결과 반환
  server ->> user: 결제 결과 반환
```