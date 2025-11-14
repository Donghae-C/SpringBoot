-- 첫 번째 데이터 등록
INSERT INTO todos (todo, detail, created_at, updated_at)
VALUES
    ('쇼핑', '마트에서 식재료 구입하기', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 두 번째 데이터 등록
INSERT INTO todos (todo, detail, created_at, updated_at)
VALUES
    ('도서관에 가기', '책 빌리기', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 세 번째 데이터 등록
INSERT INTO todos (todo, detail, created_at, updated_at)
VALUES
    ('헬스장 가기', '운동하기', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 인증 테이블에 더미 데이터를 추가
INSERT INTO authentications (username, password)
VALUES ('admin', '$2a$10$b4kM8vzoH1dlO8pm7YL48e35mZgmprtUo2Ct6cS/2Z85or6EwjCiC');
