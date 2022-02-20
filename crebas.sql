/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/2/20 23:21:59                           */
/*==============================================================*/


drop table if exists good_info;

drop table if exists User_1.order_info;

drop table if exists user_info;

/*==============================================================*/
/* User: User_1                                                 */
/*==============================================================*/
create user User_1;

/*==============================================================*/
/* Table: good_info                                             */
/*==============================================================*/
create table good_info
(
   good_name            varchar(20),
   price                decimal(4,2),
   maker                varchar(20),
   make_date            date,
   order_id             int
);

/*==============================================================*/
/* Table: order_info                                            */
/*==============================================================*/
create table User_1.order_info
(
   id                   int not null,
   user_id              int,
   address_get          varchar(50),
   iphone_user          varchar(18),
   create_time          date,
   creater              varchar(20),
   count_money          decimal,
   primary key (id)
);

/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   id                   int not null,
   userName             varchar(12),
   Iphone               varchar(12),
   address              varchar(18),
   create_time          date,
   creater              varchar(12),
   update_time          date,
   updater              varchar(12),
   primary key (id)
);

alter table good_info add constraint FK_Reference_2 foreign key (order_id)
      references User_1.order_info (id) on delete restrict on update restrict;

alter table User_1.order_info add constraint FK_Reference_1 foreign key (user_id)
      references user_info (id) on delete restrict on update restrict;

