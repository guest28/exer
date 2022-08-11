-- 베스트 상품 조회
SELECT * FROM best_pro_view;

-- 신상품 조회
SELECT * FROM new_pro_view;

-- category1 상품 조회
SELECT * FROM product WHERE kind=1;

-- 회원 정보 삽입
INSERT INTO member VALUES
('abcd1234', '#Abcd1234', '홍길동', 'abcd@abcd.com', '010-1234-5678', '07714', '서울특별시 강서구 화곡로149', '심당빌딩 404호', 'y', sysdate);

-- 회원 정보 삽입
INSERT INTO member VALUES
('java1234', '#Abcd1234', '장길산', 'efgh@abcd.com', '010-1111-2222', '07714', '서울특별시 강서구 화곡로149', '심당빌딩 404호', 'y', sysdate);

-- 개별 회원 정보 조회 : 아이디 존재 여부
SELECT count(*) FROM memeber WHERE id='abcd1234';

-- 개별 회원 정보 조회 : 이메일 존재 여부
SELECT count(*) FROM memeber WHERE email='abcd@abcd.com';

-- 개별 회원 정보 조회 : 연락처 존재 여부
SELECT count(*) FROM memeber WHERE phone='010-1234-5678';

-- 로그인	: count가 1이면 로그인
SELECT count(*) FROM member WHERE id='skacjfgus' AND pwd='skacjfgus89!';

-- 아이디에 따른 개별 회원 전체 정보 조회
SELECT * FROM member WHERE id='abcd1234';

-- 개별 회원 정보 수정(갱신) : update
UPDATE member
SET pwd='asdf1234!',
	email='asdf1234@asdf.com',
	phone='010-4321-4321',
	zip_num='',
	address1='',
	address2='',
	useyn='y'
WHERE id='asdf1234';

-- 로그인한 사용자의 이메일 외의 이메일 현황
SELECT email FROM member WHERE id!='asdf1234';

-- 이메일 중복 점검 : 기존의 이메일 제외한 나머지 이메일과의 중복 점검
SELECT count(*)
FROM(SELECT email FROM member WHERE id!='asdf1234')
WHERE email='asdf1234@asdf.com';

-- 회원 탈퇴  처리 : 삭제가 아닌 휴면 상태로 => useyn = 'n'
UPDATE member SET useyn='n' WHERE id='asdf1234';

-- 회원 탈퇴  처리 : role = 'Guest'
UPDATE member_role SET role='GUEST' WHERE id='asdf1234';

-- 회원 활성화 여부 점검 : 활동/탈퇴 회원 점검
SELECT useyn FROM member WHERE id='asdf1234';

-- 개별 회원 정보 : 휴면 => 활성 시 점검
SELECT count(*) FROM member WHERE id='asdf1234' AND pwd='asdf1234!' AND email='asdf1234@asdf1234.com' AND phone='010-1414-1415';

--개별 회원 롤 정보 조회
SELECT role FROM member_role WHERE id='asdf1234';

-- 총 회원수
select count(*) from member; 