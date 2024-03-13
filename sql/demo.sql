--테이블삭제
drop table Board;

--시퀀스 삭제
drop sequence Board_user_id_seq;

---------
--게시판관리
--------
create table Board(
    user_id  number(10),
    pname       varchar(30),
    title       varchar(30),
    contents    varchar(50),
    email       varchar(50),
    cdate       timestamp, --생성일시
    udate       timestamp  --수정일시
);


--기본키
alter table Board add constraint user_user_id_pk primary key(user_id);

--외래키
alter table Board add constraint user_email_fk foreign key(email) references member(email);

--시퀀스생성
create sequence Board_user_id_seq;

--디폴트
alter table Board modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table Board modify udate default systimestamp; --운영체제 일시를 기본값으로

--생성--
insert into Board(user_id,pname,title,contents)
     values(user_user_id_seq.nextval, '신수지', '안녕하세요', '반갑습니다');


--샘플 데이터
insert into Board(user_id,pname,title,contents)
     values(user_user_id_seq.nextval, '백종민', '옷팝니다', '한벌에8만원');

insert into Board(user_id,pname,title,contents)
     values(user_user_id_seq.nextval, '깜비', '간식줘', '간식내놔');
