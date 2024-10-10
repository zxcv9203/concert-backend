```
└── concertbackend
    ├── api
    │   ├── concert
    │   │   ├── request
    │   │   └── response
    │   ├── payment
    │   │   ├── request
    │   │   └── response
    │   ├── queue
    │   │   ├── request
    │   │   └── response
    │   └── wallet
    │       ├── request
    │       └── response
    ├── application
    │   ├── concert
    │   ├── payment
    │   ├── queue
    │   └── wallet
    ├── common
    │   ├── model
    │   └── exception
    ├── domain
    │   ├── concert
    │   ├── payment
    │   ├── queue
    │   └── wallet
    ├── infrastructure
    │   ├── persistence
    │   │   ├── concert
    │   │   │   ├── entity
    │   │   │   └── model
    │   │   ├── payment
    │   │   │   ├── entity
    │   │   │   └── model
    │   │   ├── queue
    │   │   │   ├── entity
    │   │   │   └── model
    │   │   └── wallet
    │   │       ├── entity
    │   │       └── model
    │   └── web
    │       ├── config
    │       ├── filter
```

- api: 컨트롤러 및 요청/응답 객체들이 위치합니다.
- application: 비즈니스 로직을 담당하는 서비스 및 facade가 위치합니다.
- common: 전역적으로 사용되는 클래스 들이 위치합니다. (유틸, 예외, 응답 모델)
- domain: 도메인 객체 및 비즈니스 규칙이 포함됩니다.
- infrastructure: 외부와의 연동을 담당하는 클래스들이 위치합니다. (DB, WEB)
  - persistence: 데이터 영속화를 위해 사용되는 클래스들이 위치합니다.(DB, JPA)
  - web: 웹과 관련된 클래스들이 위치합니다. (Filter, Config)