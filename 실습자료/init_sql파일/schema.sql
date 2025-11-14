use jpa;

-- 테이블이 존재하면 삭제
DROP TABLE IF EXISTS todos;
DROP TABLE IF EXISTS authentications;

-- 테이블 만들기
CREATE TABLE todos (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY, -- serial → MySQL에서는 AUTO_INCREMENT 사용
                       todo VARCHAR(255) NOT NULL,
                       detail TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE authentications (
                                 username VARCHAR(50) PRIMARY KEY,
                                 password VARCHAR(255) NOT NULL
);
