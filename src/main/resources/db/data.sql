INSERT INTO users (name, `created_at`, `updated_at`) VALUES ('user1', now(), now());
INSERT INTO users (name, `created_at`, `updated_at`) VALUES ('user2', now(), now());
INSERT INTO users (name, `created_at`, `updated_at`) VALUES ('user3', now(), now());

INSERT INTO wallets (user_id, balance, `created_at`, `updated_at`) VALUES (1, 0, now(), now());
INSERT INTO wallets (user_id, balance, `created_at`, `updated_at`) VALUES (2, 0, now(), now());
INSERT INTO wallets (user_id, balance, `created_at`, `updated_at`) VALUES (3, 0, now(), now());
INSERT INTO concerts (name, `created_at`, `updated_at`) VALUES ('concert1', now(), now());

INSERT INTO concert_schedules (concert_id, start_time, end_time, max_seat, created_at, updated_at)
VALUES (1,
        DATEADD('MONTH', 1, now()),
        DATEADD('MONTH', 2, DATEADD('MONTH', 1, now())),
        50,
        now(),
        now());
INSERT INTO concert_seats (concert_schedule_id, name, status, price, created_at, updated_at)
VALUES
    (1, 'A1', 'AVAILABLE', 10000, now(), now()),
    (1, 'A2', 'AVAILABLE', 10000, now(), now()),
    (1, 'A3', 'AVAILABLE', 10000, now(), now()),
    (1, 'A4', 'AVAILABLE', 10000, now(), now()),
    (1, 'A5', 'AVAILABLE', 10000, now(), now()),
    (1, 'A6', 'AVAILABLE', 10000, now(), now()),
    (1, 'A7', 'AVAILABLE', 10000, now(), now()),
    (1, 'A8', 'AVAILABLE', 10000, now(), now()),
    (1, 'A9', 'AVAILABLE', 10000, now(), now()),
    (1, 'A10', 'AVAILABLE', 10000, now(), now()),
    (1, 'A11', 'AVAILABLE', 10000, now(), now()),
    (1, 'A12', 'AVAILABLE', 10000, now(), now()),
    (1, 'A13', 'AVAILABLE', 10000, now(), now()),
    (1, 'A14', 'AVAILABLE', 10000, now(), now()),
    (1, 'A15', 'AVAILABLE', 10000, now(), now()),
    (1, 'A16', 'AVAILABLE', 10000, now(), now()),
    (1, 'A17', 'AVAILABLE', 10000, now(), now()),
    (1, 'A18', 'AVAILABLE', 10000, now(), now()),
    (1, 'A19', 'AVAILABLE', 10000, now(), now()),
    (1, 'A20', 'AVAILABLE', 10000, now(), now()),
    (1, 'A21', 'AVAILABLE', 10000, now(), now()),
    (1, 'A22', 'AVAILABLE', 10000, now(), now()),
    (1, 'A23', 'AVAILABLE', 10000, now(), now()),
    (1, 'A24', 'AVAILABLE', 10000, now(), now()),
    (1, 'A25', 'AVAILABLE', 10000, now(), now()),
    (1, 'A26', 'AVAILABLE', 10000, now(), now()),
    (1, 'A27', 'AVAILABLE', 10000, now(), now()),
    (1, 'A28', 'AVAILABLE', 10000, now(), now()),
    (1, 'A29', 'AVAILABLE', 10000, now(), now()),
    (1, 'A30', 'AVAILABLE', 10000, now(), now()),
    (1, 'A31', 'AVAILABLE', 10000, now(), now()),
    (1, 'A32', 'AVAILABLE', 10000, now(), now()),
    (1, 'A33', 'AVAILABLE', 10000, now(), now()),
    (1, 'A34', 'AVAILABLE', 10000, now(), now()),
    (1, 'A35', 'AVAILABLE', 10000, now(), now()),
    (1, 'A36', 'AVAILABLE', 10000, now(), now()),
    (1, 'A37', 'AVAILABLE', 10000, now(), now()),
    (1, 'A38', 'AVAILABLE', 10000, now(), now()),
    (1, 'A39', 'AVAILABLE', 10000, now(), now()),
    (1, 'A40', 'AVAILABLE', 10000, now(), now()),
    (1, 'A41', 'AVAILABLE', 10000, now(), now()),
    (1, 'A42', 'AVAILABLE', 10000, now(), now()),
    (1, 'A43', 'AVAILABLE', 10000, now(), now()),
    (1, 'A44', 'AVAILABLE', 10000, now(), now()),
    (1, 'A45', 'AVAILABLE', 10000, now(), now()),
    (1, 'A46', 'AVAILABLE', 10000, now(), now()),
    (1, 'A47', 'AVAILABLE', 10000, now(), now()),
    (1, 'A48', 'AVAILABLE', 10000, now(), now()),
    (1, 'A49', 'AVAILABLE', 10000, now(), now()),
    (1, 'A50', 'AVAILABLE', 10000, now(), now());