-- 개인 개별 게시글 작성(쓰기)
INSERT INTO qna (qseq, subject, content, id) 
VALUES(qna_seq.nextval, ?, ?, ?);

-- 관리자 : 게시글 목록 조회
SELECT * FROM qna ORDER BY indate DESC;

-- 관리자 : 게시글 목록 조회 (페이징)
SELECT *  
FROM (SELECT ROWNUM,  
             m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM qna 
			 ORDER BY indate DESC
           ) m  
      )  
WHERE page = 1;

-- 관리자 : 개별 게시글 수정(댓글(답글) 작성)
UPDATE qna SET reply=?, rep='2' WHERE qseq=?;


-- 더미 Q&A 정보 생성 (2인 20개 분량)
CREATE OR REPLACE PROCEDURE dummy_qna_gen_proc
IS
BEGIN
 
    FOR i IN 1..10 LOOP
    
        INSERT INTO qna 
        (qseq, subject, content, id) 
        VALUES
        (qna_seq.nextval,
         '제목', 
         '내용', 
         'java1234');

     END LOOP;
     
     
     FOR i IN 1..10 LOOP
    
        INSERT INTO qna 
        (qseq, subject, content, id) 
        VALUES
        (qna_seq.nextval,
         '제목', 
         '내용', 
         'spring1234');

     END LOOP;
 
    COMMIT;    
END;
/

exec dummy_qna_gen_proc;