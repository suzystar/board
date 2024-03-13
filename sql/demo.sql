
--테이블삭제

drop table Ccomment;
drop table Board;
drop table member;

--회원테이블
create table member(
    member_id   number(10),     --내부관리 아이디
    email       varchar2(50),   --로긴 아이디
    passwd      varchar2(12),   --로긴 비밀번호
    nickname    varchar2(30),   --별칭
    gubun       varchar2(11),    --default 'M0101', --회원구분(일반,우수,관리자1,관리자2)..
    pic         blob,           --사진
    cdate       timestamp,      --생성일,가입일
    udate       timestamp       --수정일
);
--기본키생성
alter table member add Constraint member_member_id_pk primary key(member_id);

drop sequence member_member_id_seq;
--시퀀스
create sequence member_member_id_seq;

--디폴트
alter table member modify gubun default 'M0101';
alter table member modify cdate default systimestamp;
alter table member modify udate default systimestamp;

--notnull

--샘플테이터
insert into member (member_id,email,passwd,nickname)
    values(member_member_id_seq.nextval,'user1@kh.com','user1','사용자1');
insert into member (member_id,email,passwd,nickname)
    values(member_member_id_seq.nextval,'user2@kh.com','user2','사용자2');

--시퀀스 삭제
drop sequence Board_user_id_seq;
--시퀀스생성
create sequence Board_user_id_seq;

---------
--게시판관리
--------
create table Board(
    user_id  number(10),
    pname       varchar2(30),
    title       varchar2(30),
    contents    varchar(50),
    email       varchar2(50),
    cdate       timestamp, --생성일시
    udate       timestamp  --수정일시
);


--기본키
alter table Board add constraint Board_user_id_pk primary key(user_id);

----외래키
--alter table Board add constraint Board_email_fk foreign key(email) references member(email);



--디폴트
alter table Board modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table Board modify udate default systimestamp; --운영체제 일시를 기본값으로



--생성--
insert into Board(user_id,pname,title,contents)
     values(Board_user_id_seq.nextval, '신수지', '안녕하세요', '반갑습니다');

insert into Board(user_id,pname,title,contents)
     values(Board_user_id_seq.nextval, '백종민', '옷팝니다', '한벌에8만원');

insert into Board(user_id,pname,title,contents)
     values(Board_user_id_seq.nextval, '깜비', '간식줘', '간식내놔');




--시퀀스삭제
drop sequence ccomment_ccomment_id_seq;
--시퀀스생성
create sequence ccomment_ccomment_id_seq;
----------
--댓글테이블
----------
create table Ccomment(
    ccomment_id  number(10),  -- 아이디
    cnum        number(10),  -- 원글번호
    contents    varchar2(50),   -- 내용
    uname       varchar2(30),-- 작성자
    cdate       timestamp,   --작성날짜
    udate       timestamp    --수정날짜
);

--기본키
alter table Ccomment add constraint Ccomment_ccomment_id_pk primary key(ccomment_id);




--디폴트
alter table Ccomment modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table Ccomment modify udate default systimestamp; --운영체제 일시를 기본값으로

--필수 not null
alter table Ccomment modify cnum not null;
alter table Ccomment modify contents not null;
alter table Ccomment modify uname not null;

--샘플데이터 생성--
insert into Ccomment(ccomment_id, cnum, contents, uname)
     values(ccomment_ccomment_id_seq.nextval, '1', '댓글 내용 입력', '홍길동');
insert into Ccomment(ccomment_id, cnum, contents, uname)
     values(ccomment_ccomment_id_seq.nextval, '1', '댓글 내용 입력', '홍길순');



//조회
select * from Ccomment
order by Ccomment_id desc;





// 조회
// 원글1번의 댓글 목록 가져오기
select * from ccomment
where cnum = 1;

// 수정
-- 댓글 내용 수정
update Ccomment set contents = '수정쿼리 확인'
where ccomment_id = 1;

// 삭제
-- 특정 댓글 삭제
delete from ccomment
where ccomment_id = 2;


//조회
    select ccomment_id, cnum, contents, uname, cdate, udate
      from ccomment
  order by ccomment_id desc;




