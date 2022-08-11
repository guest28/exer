-- 더미 회원 정보 생성 (100명 분량)

-- delete member where id like 'ezen%';
-- delete member_role where id like 'ezen%';
CREATE OR REPLACE PROCEDURE dummy_member_gen_proc
IS
BEGIN
 
    FOR i IN 1..100 LOOP
    
        INSERT INTO member VALUES
        ('ezen_' || (1000+i),
         '@Abcd1234',
         '김' || (100+i),
         'ezen_' || i || '@abcd.com',
         '010-3434-' || (1000+i),
         '12345',  
         '서울 강서구 화곡동',
         '이젠아카데미 화곡점',
         'y',
         SYSDATE);
         
        INSERT INTO member_role VALUES 
        (role_seq.nextval,
        'ezen_' || (1000+i),
        'user');
송
     END LOOP;
 
    COMMIT;    
END;
/

exec dummy_member_gen_proc;

CREATE OR REPLACE PROCEDURE name_update_gen_proc  
IS  
      TYPE first_name_array      IS VARRAY(13) OF CLOB;  
      TYPE middle_name_array      IS VARRAY(13) OF CLOB;  
      TYPE last_name_array      IS VARRAY(13) OF CLOB;  
      first_names   first_name_array;  
      middle_names   middle_name_array;  
      last_names   last_name_array;        
      v_firstName CLOB;  
      v_middleName CLOB;  
      v_lastName CLOB;  
      totalName CLOB;  
      temp_num NUMBER(2);  
BEGIN  
      first_names := first_name_array('김','이','박','최','남','황보','오','백','장','정','양','조','송');  
      middle_names := middle_name_array('미','상','성','동','재','선','원','우','이','철','영','태','명');  
      last_names := last_name_array('영','수','희','민','정','순','주','연','영','현','우','훈','민');  
       
      FOR i IN 1..100 LOOP  
         
        temp_num := round(DBMS_RANDOM.VALUE(1,13),0);  
        v_firstName :=  first_names(temp_num);  
        temp_num := round(DBMS_RANDOM.VALUE(1,13),0);  
        v_middleName :=  middle_names(temp_num);  
        temp_num := round(DBMS_RANDOM.VALUE(1,13),0);  
        v_lastName :=  last_names(temp_num);  
        totalName := v_firstName || v_middleName || v_lastName;  
                         
        UPDATE member SET name = totalName  
        WHERE id = 'ezen_' || (1000+i);
 
    END LOOP;  

    COMMIT;         
  END;  
/  
EXECUTE name_update_gen_proc; 

-- 전체 회원 조회(페이징 적용)
-- 페이징을 활용한 전체 회원정보 조회 (인라인 뷰(inline-view) 활용)
-- 한번에 10명씩 출력
-- 아이디 중심으로 오름차순 정렬
SELECT *  
FROM (SELECT ROWNUM,  
             m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT * FROM member  
             ORDER BY id ASC
           ) m  
      )  
WHERE page = 1;

-- 전체 회원 조회(role 포함, 페이징 적용)
SELECT *  
FROM (SELECT ROWNUM,  
             m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT m.id, m.pwd, m.name, m.email, m.phone,
             		m.zip_num, m.address1, m.address2, m.useyn, m.indate, r.role
             FROM member m, member_role r
             WHERE m.id = r.id
             ORDER BY id ASC
           ) m  
      )  
WHERE page = 1;

-- 검색(admin/member/member_list)
-- where절의 공통 부분
-- AND m.id = 'ezen_1002'
-- AND address1 like '%화곡동%'
-- AND address2 like '%심당빌딩 404호%'

-- 아이디
SELECT *  
FROM (SELECT ROWNUM,  
             m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT m.id, m.pwd, m.name, m.email, m.phone, 
			       m.zip_num, m.address1, m.address2, m.useyn, m.indate, r.role
			 FROM member m, member_role r 
			 WHERE m.id = r.id
			 AND m.id = 'ezen_1002'
			 ORDER BY id ASC
           ) m  
      )  
WHERE page = 1;

-- 이름
SELECT *  
FROM (SELECT ROWNUM,  
             m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT m.id, m.pwd, m.name, m.email, m.phone, 
			       m.zip_num, m.address1, m.address2, m.useyn, m.indate, r.role
			 FROM member m, member_role r 
			 WHERE m.id = r.id
			 AND name like '%' || '이' || '%' 
			 ORDER BY m.id ASC
           ) m  
      )  
WHERE page = 1;

-- 기본 주소
SELECT *  
FROM (SELECT ROWNUM,  
             m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT m.id, m.pwd, m.name, m.email, m.phone, 
			       m.zip_num, m.address1, m.address2, m.useyn, m.indate, r.role
			 FROM member m, member_role r 
			 WHERE m.id = r.id
			 AND address1 like '%화곡동%'
			 ORDER BY m.id ASC
           ) m  
      )  
WHERE page = 1;

-- 상세 주소
SELECT *  
FROM (SELECT ROWNUM,  
             m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT m.id, m.pwd, m.name, m.email, m.phone, 
			       m.zip_num, m.address1, m.address2, m.useyn, m.indate, r.role
			 FROM member m, member_role r 
			 WHERE m.id = r.id
			 AND address2 like '%심당빌딩 404호%'
			 ORDER BY m.id ASC
           ) m  
      )  
WHERE page = 1;

-- 관리자 모드 : 개별 이메일/연락처/주소 개별 수정
UPDATE member SET email = 'abcdabcdabcd@naver.com' WHERE id='java1234';
UPDATE member SET phone = '010-9898-7979' WHERE id='java1234';
UPDATE member SET zip_num = '13245' WHERE id='java1234';
UPDATE member SET address1 = '서울 강서구 화곡동' WHERE id='java1234';
UPDATE member SET address2 = '이젠 학원' WHERE id='java1234';

-- 최근 등록 상품 아이디 조회
SELECT max(pseq) FROM product;

-- 상품 정보 수정
UPDATE product SET 
name='구두',
kind=3, 
price1=10000, 
price2=17900, 
price3=7900, 
content='신상 구두', 
image='~~~~.jpg', 
thumb_image='thumb-~~~.jpg',
useyn='y', 
bestyn='n',
indate=sysdate
WHERE pseq=43;