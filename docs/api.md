# API 명세

## 대기열 토큰 발급 API

`POST /v1/queue/tokens`

### 요청

```json
{
  "userId": 1
}
```

### 응답 (성공)

```json
{
  "code": "201",
  "message": "대기열 등록에 성공했습니다.",
  "data": {
    "token": "UUID",
    "status": "WAITING",
    "waitingNumber": 1
  }
}
```

### 응답 (실패)

#### 해당하는 사용자 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 사용자가 없습니다."
}
```

## 대기열 토큰 상태 조회 API

`GET /v1/queue`

### 요청 헤더

```
"QUEUE-AUTH-TOKEN": "UUID" 
```

### 응답 (성공)

```json
{
  "code": "200",
  "message": "현재 대기열 상태 조회에 성공했습니다.",
  "data": {
    "token": "UUID",
    "status": "WAITING",
    "waitingNumber": 1
  }
}
```

### 응답 (실패)

#### 해당하는 토큰 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 토큰 정보가 없습니다."
}
```

### 예약 가능 날짜 API

`GET /v1/concerts/{id}/schedules`

#### 요청 헤더

```
"QUEUE-AUTH-TOKEN": "UUID" 
```

#### 응답

```json
{
  "code": "200",
  "message": "콘서트 일정 조회에 성공했습니다.",
  "data": {
    "schedules": [
      {
        "id": 1,
        "date": "2024-10-10 13:00",
        "availableSeats": 10
      },
      {
        "id": 2,
        "date": "2024-10-11 13:00",
        "availableSeats": 5
      },
      {
        "id": 3,
        "date": "2022-10-12 13:00",
        "availableSeats": 0
      }
    ]
  }
}
```

### 응답 (실패)

#### 해당하는 토큰 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 토큰 정보가 없습니다."
}
```

#### 해당 콘서트 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 토큰 정보가 없습니다."
}
```

### 특정 날짜 좌석 API

`GET /v1/concerts/{concertId}/schedules/{concertScheduleId}/seats`

#### 요청 헤더

```
"QUEUE-AUTH-TOKEN": "UUID" 
```

#### 응답

```json
{
  "code": "200",
  "message": "콘서트 좌석 조회에 성공했습니다.",
  "data": {
    "seats": [
      {
        "id": 1,
        "seatNumber": "A1",
        "status": "AVAILABLE",
        "price": 10000
      },
      {
        "id": 2,
        "seatNumber": "A2",
        "status": "AVAILABLE",
        "price": 10000
      },
      {
        "id": 3,
        "seatNumber": "A3",
        "status": "RESERVED",
        "price": 10000
      },
      {
        "id": 4,
        "seatNumber": "A4",
        "status": "IN_PROGRESS",
        "price": 10000
      },
      ...
    ]
  }
}
```

#### 해당하는 토큰 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 토큰 정보가 없습니다."
}
```

#### 해당 콘서트 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 콘서트 정보가 없습니다."
}
```

### 좌석 예약 요청 API

`POST /v1/concerts/{concertId}/schedules/{concertScheduleId}/seats/reservations`

#### 요청 헤더

```
"QUEUE-AUTH-TOKEN": "UUID" 
```

#### 요청

```json
{
  "seats": [1, 2, 3, 4]
}
```

#### 응답

```json
{
  "code": "200",
  "message": "콘서트 좌석 예약에 성공했습니다.",
  "data": {
    "id": 1,
    "totalPrice": 40000
  }
}
```

### 응답 (실패)

#### 해당하는 토큰 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 토큰 정보가 없습니다."
}
```

#### 해당 콘서트 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 콘서트 정보가 없습니다."
}
```

#### 해당 좌석 정보가 존재하지 않는 경우

```json
{
  "code": "400",
  "message": "해당하는 좌석 정보가 없습니다."
}
```

#### 해당 좌석이 이미 예약되거나 예약 대기 상태인 경우

```json
{
  "code": "400",
  "message": "해당하는 좌석은 이미 예약되었습니다."
}
```

## 잔액 충전 API

`PATCH /v1/users/{userId}/wallet/charge`

### 요청 헤더

```
"QUEUE-AUTH-TOKEN": "UUID" 
```

### 요청

```json
{
  "amount": 10000
}
```

### 응답 (성공)

```json
{
  "code": "200",
  "message": "잔액 충전에 성공했습니다.",
  "data": {
    "balance": 10000
  }
}
```

### 응답 (실패)

#### 해당하는 토큰 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 토큰 정보가 없습니다."
}
```

#### 토큰의 사용자 정보와 식별자로 전달한 정보가 다른 경우

```json
{
  "code": "400",
  "message": "인증된 사용자가 아닙니다."
}
```

## 잔액 조회 API

`GET /v1/users/{id}/wallet`

### 요청 헤더

```
"QUEUE-AUTH-TOKEN": "UUID" 
```

### 응답 (성공)

```json
{
  "code": "200",
  "message": "잔액 조회에 성공했습니다.",
  "data": {
    "balance": 10000
  }
}
```

### 응답 (실패)

#### 해당하는 토큰 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 토큰 정보가 없습니다."
}
```

#### 토큰의 사용자 정보와 식별자로 전달한 정보가 다른 경우

```json
{
  "code": "400",
  "message": "인증된 사용자가 아닙니다."
}
```

## 결제 API
`POST /v1/payments`

### 요청 헤더

```
"QUEUE-AUTH-TOKEN": "UUID" 
```

### 요청

```json
{
  "userId": 1,
  "amount": 10000
}
```

### 응답 (성공)

```json
{
  "code": "200",
  "message": "결제에 성공했습니다.",
  "data": {
    "balance": 0
  }
}
```

### 응답 (실패)

#### 해당하는 토큰 정보가 없는 경우

```json
{
  "code": "400",
  "message": "해당하는 토큰 정보가 없습니다."
}
```

#### 토큰의 사용자 정보와 식별자로 전달한 정보가 다른 경우

```json
{
  "code": "400",
  "message": "인증된 사용자가 아닙니다."
}
```