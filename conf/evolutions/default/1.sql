# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table doctor (
  id                        integer not null,
  email                     varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  user_type                 varchar(255),
  phone                     varchar(255),
  address                   varchar(255),
  city                      varchar(255),
  state_                    varchar(255),
  country                   varchar(255),
  age                       integer,
  doctorname                varchar(255),
  doctor_type               varchar(255),
  constraint pk_doctor primary key (id))
;

create table exam (
  id                        integer not null,
  patient_name              varchar(255),
  staff_name                varchar(255),
  systolic                  float,
  diastolic                 float,
  blood_sugar               float,
  weight                    float,
  height                    float,
  date                      timestamp,
  constraint pk_exam primary key (id))
;

create table nurse (
  id                        integer not null,
  email                     varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  user_type                 varchar(255),
  phone                     varchar(255),
  address                   varchar(255),
  city                      varchar(255),
  state_                    varchar(255),
  country                   varchar(255),
  age                       integer,
  nursename                 varchar(255),
  nurse_type                varchar(255),
  constraint pk_nurse primary key (id))
;

create table patient (
  id                        integer not null,
  email                     varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  user_type                 varchar(255),
  phone                     varchar(255),
  address                   varchar(255),
  city                      varchar(255),
  state_                    varchar(255),
  country                   varchar(255),
  age                       integer,
  patientname               varchar(255),
  patient_type              varchar(255),
  constraint pk_patient primary key (id))
;

create table prescription (
  id                        integer not null,
  patient_name              varchar(255),
  staff_name                varchar(255),
  medication_name           varchar(255),
  dosage                    varchar(255),
  frequency                 varchar(255),
  end_date                  timestamp,
  constraint pk_prescription primary key (id))
;

create table user (
  id                        integer not null,
  email                     varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  user_type                 varchar(255),
  phone                     varchar(255),
  address                   varchar(255),
  city                      varchar(255),
  state_                    varchar(255),
  country                   varchar(255),
  age                       integer,
  constraint pk_user primary key (id))
;

create sequence doctor_seq;

create sequence exam_seq;

create sequence nurse_seq;

create sequence patient_seq;

create sequence prescription_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists doctor;

drop table if exists exam;

drop table if exists nurse;

drop table if exists patient;

drop table if exists prescription;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists doctor_seq;

drop sequence if exists exam_seq;

drop sequence if exists nurse_seq;

drop sequence if exists patient_seq;

drop sequence if exists prescription_seq;

drop sequence if exists user_seq;

