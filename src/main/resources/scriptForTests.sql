drop schema if exists `computer-database-db-tests`;
  create schema if not exists `computer-database-db-tests`;
  use `computer-database-db-tests`;

  drop table if exists computer;
  drop table if exists company;

  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                timestamp NULL,
    discontinued              timestamp NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);




insert into company (id,name) values (  1,'company1');
insert into company (id,name) values (  2,'company2');
insert into company (id,name) values (  3,'company3');
insert into company (id,name) values (  4,'company4');
insert into computer (id,name,introduced,discontinued,company_id) values (  1,'computer1',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (  2,'computer2',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (  3,'computer3',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (  4,'computer4',null,null,3);
insert into computer (id,name,introduced,discontinued,company_id) values (  5,'computer5',null,null,3);
