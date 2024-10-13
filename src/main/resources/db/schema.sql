CREATE TABLE IF NOT EXISTS `concerts`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`       VARCHAR(255) NOT NULL COMMENT '콘서트 이름',
    `created_at` DATETIME     NOT NULL COMMENT '생성 시간',
    `updated_at` DATETIME     NOT NULL COMMENT '수정 시간'
);


CREATE TABLE IF NOT EXISTS `concert_schedules`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `concert_id` BIGINT   NOT NULL COMMENT '콘서트 ID',
    `start_time` DATETIME NOT NULL COMMENT '콘서트 시작 시간',
    `end_time`   DATETIME NOT NULL COMMENT '콘서트 종료 시간',
    `max_seat`   INT      NOT NULL COMMENT '최대 좌석 수',
    `created_at` DATETIME NOT NULL COMMENT '생성 시간',
    `updated_at` DATETIME NOT NULL COMMENT '수정 시간'
);

CREATE TABLE IF NOT EXISTS `concert_seats`
(
    `id`                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    `concert_schedule_id` BIGINT      NOT NULL COMMENT '콘서트 스케줄 ID',
    `name`                VARCHAR(20) NOT NULL COMMENT '좌석 이름',
    `status`              VARCHAR(30) NOT NULL COMMENT '좌석 상태',
    `created_at`          DATETIME    NOT NULL COMMENT '생성 시간',
    `updated_at`          DATETIME    NOT NULL COMMENT '수정 시간'
);

CREATE TABLE IF NOT EXISTS `concert_reservations`
(
    `id`                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    `concert_schedule_id` BIGINT      NOT NULL COMMENT '콘서트 스케줄 ID',
    `concert_seat_id`     BIGINT      NOT NULL COMMENT '콘서트 좌석 ID',
    `total_price`         BIGINT      NOT NULL COMMENT '총 가격',
    `status`              VARCHAR(30) NOT NULL COMMENT '예약 상태',
    `expires_at`          DATETIME    NOT NULL COMMENT '예약 만료 시간',
    `created_at`          DATETIME    NOT NULL COMMENT '생성 시간',
    `updated_at`          DATETIME    NOT NULL COMMENT '수정 시간'
);

CREATE TABLE IF NOT EXISTS `concert_reservation_items`
(
    `id`                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    `concert_reservation_id` BIGINT   NOT NULL COMMENT '콘서트 예약 ID',
    `concert_seat_id`        BIGINT   NOT NULL COMMENT '콘서트 좌석 ID',
    `created_at`             DATETIME NOT NULL COMMENT '생성 시간',
    `updated_at`             DATETIME NOT NULL COMMENT '수정 시간'
);

CREATE TABLE IF NOT EXISTS `payment_histories`
(
    `id`                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    `wallet_id`              BIGINT      NOT NULL COMMENT '지갑 ID',
    `concert_reservation_id` BIGINT      NOT NULL COMMENT '콘서트 예약 ID',
    `amount`                 BIGINT      NOT NULL COMMENT '금액',
    `status`                 VARCHAR(30) NOT NULL COMMENT '결제 상태',
    `paid_at`                VARCHAR(30) NOT NULL COMMENT '결제 시간',
    `created_at`             DATETIME    NOT NULL COMMENT '생성 시간',
    `updated_at`             DATETIME    NOT NULL COMMENT '수정 시간'
);

CREATE TABLE IF NOT EXISTS `queue_tokens`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id`    BIGINT       NOT NULL COMMENT '사용자 ID',
    `token`      VARCHAR(255) NOT NULL UNIQUE COMMENT '토큰(UUID)',
    `expires_at` DATETIME     NULL COMMENT '활성 후 만료 시간',
    `created_at` DATETIME     NOT NULL COMMENT '생성 시간',
    `updated_at` DATETIME     NOT NULL COMMENT '수정 시간'
);

CREATE TABLE IF NOT EXISTS `waiting_queue`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `token`      VARCHAR(255) NOT NULL UNIQUE COMMENT '토큰(UUID)',
    `status`     VARCHAR(30)  NOT NULL COMMENT '대기 상태',
    `created_at` DATETIME     NOT NULL COMMENT '생성 시간',
    `updated_at` DATETIME     NOT NULL COMMENT '수정 시간'
);

CREATE TABLE IF NOT EXISTS `users`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`       VARCHAR(50) NOT NULL COMMENT '사용자 이름',
    `created_at` DATETIME    NOT NULL COMMENT '생성 시간',
    `updated_at` DATETIME    NOT NULL COMMENT '수정 시간'
);

CREATE TABLE IF NOT EXISTS `wallets`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id`    BIGINT   NOT NULL COMMENT '사용자 ID',
    `balance`    BIGINT   NOT NULL COMMENT '잔액',
    `created_at` DATETIME NOT NULL COMMENT '생성 시간',
    `updated_at` DATETIME NOT NULL COMMENT '수정 시간'
);

CREATE TABLE IF NOT EXISTS `balance_histories`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `wallet_id`  BIGINT      NOT NULL COMMENT '지갑 ID',
    `balance`    BIGINT      NOT NULL COMMENT '잔액',
    `type`       VARCHAR(30) NOT NULL COMMENT '변동 타입',
    `created_at` DATETIME    NOT NULL COMMENT '생성 시간',
    `updated_at` DATETIME    NOT NULL COMMENT '수정 시간'
);