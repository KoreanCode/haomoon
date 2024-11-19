CREATE TABLE user_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,                -- 사용자의 고유 ID
    provider VARCHAR(50),                                -- 로그인 제공자 (카카오, 구글, 네이버 등)
    provider_user_id VARCHAR(255),                       -- 제공자에서 제공하는 고유 사용자 ID
    email VARCHAR(255) NOT NULL UNIQUE,                  -- 이메일 (고유 제약 조건)
    password VARCHAR(255) NOT NULL,                      -- 비밀번호 (로컬 회원가입을 위한 필드)
    nickname VARCHAR(100) NOT NULL UNIQUE,               -- 닉네임 (고유 제약 조건)
    phone VARCHAR(20),                                   -- 전화번호
    gender CHAR(1),                                      -- 성별 (M, F 등으로 표시)
    name VARCHAR(100),                                   -- 사용자 이름
    point INT DEFAULT 0,                                 -- 포인트, 기본값 0으로 설정
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- 계정 생성일
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 마지막 수정일
    CONSTRAINT idx_provider_user_id UNIQUE (provider, provider_user_id)  -- provider + provider_user_id 조합으로 유니크 제약 조건 추가
);


-- 투표 기록 테이블
CREATE TABLE vote_record_tb (
    vote_id INT AUTO_INCREMENT PRIMARY KEY,              -- 투표 고유 ID
    user_id BIGINT NOT NULL,                             -- 투표 사용자 ID
    match_id INT NOT NULL,                               -- 매칭 ID
    selected_option VARCHAR(255) NOT NULL,               -- 사용자가 선택한 옵션
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 마지막 수정일
    FOREIGN KEY (user_id) REFERENCES user_tb(id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES favorite_match_tb(match_id) ON DELETE CASCADE
);

-- 이상형 테이블
CREATE TABLE favorite_tb (
    favorite_id INT AUTO_INCREMENT PRIMARY KEY,          -- 이상형 고유 ID
    title VARCHAR(255) NOT NULL,                         -- 월드컵 제목
    category VARCHAR(50),                                -- 카테고리 (예: 영화, 음식 등)
    creator_id BIGINT NOT NULL,                          -- 생성자 ID (사용자 ID와 연결)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- 생성 날짜 및 시간
    total_round INT NOT NULL,                            -- 총 몇강인지
    FOREIGN KEY (creator_id) REFERENCES user_tb(id) ON DELETE CASCADE
);

-- 라운드 테이블
CREATE TABLE round_tb (
    round_id INT AUTO_INCREMENT PRIMARY KEY,             -- 라운드 고유 ID
    favorite_id INT NOT NULL,                            -- 이상형 ID
    user_id BIGINT NOT NULL,                             -- 사용자 ID
    round_number INT NOT NULL,                           -- 라운드 번호 (예: 16강, 8강 등)
    option1 VARCHAR(255) NOT NULL,                       -- 선택지 1
    option2 VARCHAR(255) NOT NULL,                       -- 선택지 2
    winner VARCHAR(255),                                 -- 승리한 선택지
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    FOREIGN KEY (favorite_id) REFERENCES favorite_tb(favorite_id) ON DELETE CASCADE
);

CREATE TABLE round_detail_tb (
    round_detail_id INT AUTO_INCREMENT PRIMARY KEY,             -- 라운드 고유 ID
    round_id INT NOT NULL,                            -- 라운드 ID
    end_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (round_id) REFERENCES round_tb(round_id) ON DELETE CASCADE
);

-- 옵션 테이블
CREATE TABLE option_tb (
    option_id INT AUTO_INCREMENT PRIMARY KEY,            -- 옵션 고유 ID
    favorite_id INT NOT NULL,                            -- 즐겨찾기 ID
    title VARCHAR(255) NOT NULL,                         -- 제목
    image BLOB NOT NULL,                                 -- 이미지 파일
    text VARCHAR(255) NOT NULL,                          -- 내용
    FOREIGN KEY (favorite_id) REFERENCES favorite_tb(favorite_id) ON DELETE CASCADE
);

-- 설문조사 테이블
CREATE TABLE survey_tb (
    survey_id INT PRIMARY KEY AUTO_INCREMENT,            -- 설문조사 ID
    creater_ID INT,					-- 설문조사 생성자 ID -> user_ID
    suervey_title VARCHAR(255) NOT NULL,                         -- 설문조사 제목
    survey_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- 생성일자
    survey_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 업데이트 일자
    survey_status ENUM('active', 'inactive') DEFAULT 'active',   -- 설문조사 상태 (활성화/비활성화)
    survey_endtime TIMESTAMP,
    survey_personnel INT, -- 설문조사 제한인원
    FPREOGM KEY (creater_ID) REFERENCES user_tb(user_ID) ON DELETE CASCADE
);
-- 설문조사 참가자 테이블
CREATE TABLE survey_participants_tb (
    participant_id INT PRIMARY KEY AUTO_INCREMENT,       -- 참가자 ID
    survey_id INT,                                       -- 설문조사 ID
    user_id INT,                                         -- 참가자 ID (user_tb의 user_ID)
    participation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 참가 일자
    FOREIGN KEY (survey_id) REFERENCES survey_tb(survey_id) ON DELETE CASCADE, -- 설문조사 외래키
    FOREIGN KEY (user_id) REFERENCES user_tb(user_ID) ON DELETE CASCADE       -- 사용자 외래키
);
-- 설문조사 질문 테이블
CREATE TABLE survey_question_tb (
    question_id INT PRIMARY KEY AUTO_INCREMENT,          -- 질문 ID
    survey_id INT,                                       -- 설문조사 ID
    question_text TEXT,                         -- 질문 내용
    FOREIGN KEY (survey_id) REFERENCES survey_tb(survey_id) ON DELETE CASCADE -- 설문조사 외래키
);
-- 설문조사 답변 테이블
CREATE TABLE survey_answer_tb (
    answer_id INT PRIMARY KEY AUTO_INCREMENT,          -- 응답 ID
    question_id INT,                                   -- 질문 ID (survey_question_tb의 question_id)
    participant_id INT,                                -- 참가자 ID (survey_participants_tb의 participant_id)
    answer_text TEXT,                                  -- 응답 내용 (텍스트형 또는 객관식 답변 내용 저장)
    answer_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 응답 일자
    FOREIGN KEY (question_id) REFERENCES survey_question_tb(question_id) ON DELETE CASCADE, -- 질문 외래키
    FOREIGN KEY (participant_id) REFERENCES survey_participants_tb(participant_id) ON DELETE CASCADE -- 참가자 외래키
);
-- 리워드 설문조사 테이블
CREATE TABLE reward_survey_tb (
    reward_survey_id INT PRIMARY KEY AUTO_INCREMENT,     -- 리워드형 설문조사 고유 ID
    survey_id INT,                                       -- 일반 설문조사의 ID
    reward_amount INT NOT NULL,               -- 리워드 금액
    FOREIGN KEY (survey_id) REFERENCES survey_tb(survey_id) ON DELETE CASCADE -- survey_tb의 외래키
);

CREATE TABLE chat_room_tb (
    chat_room_id INT PRIMARY KEY AUTO_INCREMENT,          -- 채팅방 고유 ID
    round_id INT NOT NULL,                                -- 라운드 ID (투표 관련)
    message_file_path VARCHAR(255),                      -- 메시지 파일 경로 (파일 저장 위치)
    is_archived BOOLEAN DEFAULT FALSE,                   -- 아카이브 여부
    archived_at TIMESTAMP NULL,                          -- 아카이브 완료 시점
    FOREIGN KEY (round_id) REFERENCES round_tb(round_id) ON DELETE CASCADE
);