
CREATE TABLE DIET_DIARY_MEMBERS(
	  DIET_DIARY_MEMBERS_IDX NUMBER PRIMARY KEY
	, ID VARCHAR2(30) UNIQUE NOT NULL
	, PASS VARCHAR2(64) NOT NULL
	, NAME VARCHAR2(30) UNIQUE NOT NULL
	, REGDATE DATE DEFAULT SYSDATE
	, QUESTION NUMBER
	, ANSWER VARCHAR2(64)
);

CREATE SEQUENCE SEQ_DIET_DIARY_MEMBERS
INCREMENT BY 1
START WITH 1;


SELECT TABLE_NAME FROM USER_TABLES;
SELECT SEQUENCE_NAME FROM USER_SEQUENCES;
SELECT * FROM DIET_DIARY_MEMBERS;

DROP TABLE DIET_DIARY_MEMBERS;
DROP SEQUENCE seq_diet_diary_members;

DROP TABLE HISTORY;
CREATE TABLE HISTORY(
	  HISTORY_IDX NUMBER PRIMARY KEY 
	, DIET_DIARY_MEMBERS_IDX NUMBER
	, YEAR NUMBER NOT NULL
	, MONTH NUMBER NOT NULL
	, DAY NUMBER NOT NULL
	, TOTAL_CALORIES NUMBER DEFAULT 0
	, TOTAL_CARBS NUMBER DEFAULT 0
	, TOTAL_PROTEINS NUMBER DEFAULT 0
	, TOTAL_FATS NUMBER DEFAULT 0
	, CONSTRAINT FK_DIET_DIARY_MEMBERS_HISTORY FOREIGN KEY (DIET_DIARY_MEMBERS_IDX) REFERENCES DIET_DIARY_MEMBERS(DIET_DIARY_MEMBERS_IDX)
	, CONSTRAINT UNIQUE_HISTORY UNIQUE (DIET_DIARY_MEMBERS_IDX, YEAR, MONTH, DAY)
);

CREATE SEQUENCE SEQ_HISTORY
INCREMENT BY 1
START WITH 1;

DROP TABLE food;

CREATE TABLE FOOD(
	  FOOD_IDX NUMBER PRIMARY KEY
	, HISTORY_IDX NUMBER NOT NULL
	, NAME VARCHAR2(40) NOT NULL
	, BRAND VARCHAR2(20) DEFAULT 'N/A'
	, CALORIES NUMBER DEFAULT 0
	, CARBS NUMBER DEFAULT 0
	, PROTEINS NUMBER DEFAULT 0
	, FATS NUMBER DEFAULT 0
	, REGYEAR varchar2(10) DEFAULT 'N/A'
	, SERVESIZE	varchar2(10) DEFAULT 'N/A'
	, QUANTITY NUMBER NOT NULL
	, CONSTRAINT FK_HISTORY_FOOD FOREIGN KEY (HISTORY_IDX) REFERENCES HISTORY(HISTORY_IDX)
);

DROP SEQUENCE seq_food;
CREATE SEQUENCE SEQ_FOOD
INCREMENT BY 1
START WITH 1;

SELECT * FROM history;
SELECT * FROM history INNER JOIN FOOD ON HISTORY.HISTORY_IDX = FOOD.HISTORY_IDX;
SELECT * FROM food;
SELECT * FROM food WHERE HISTORY_IDX =12 ORDER BY food_idx asc;




DELETE FROM history;
DELETE FROM food;

INSERT INTO history(history_idx, DIET_DIARY_MEMBERS_IDX , YEAR, MONTH, DAY)
VALUES (1, 1, 2022, 12, 28);

