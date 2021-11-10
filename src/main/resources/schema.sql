create table IF NOT EXISTS contract
(
    id         bigint not null
        constraint contract_pk
            primary key,
    start_date date   not null,
    end_date   date   not null
);

create unique index IF NOT EXISTS contract_id_index
    on contract (id);

CREATE SEQUENCE IF NOT EXISTS contract_sequence
    START WITH 1
    INCREMENT BY 1;

create table IF NOT EXISTS employee
(
    id          bigint      not null
        constraint employee_pk
            primary key,
    contract_id bigint      not null
        constraint employee_contract_id_fk
            references contract,
    first_name  varchar(15) not null,
    last_name   varchar(15) not null,
    age         integer     not null,
    state       varchar(15) not null
);

create unique index IF NOT EXISTS employee_id_index
    on employee (id);

CREATE SEQUENCE IF NOT EXISTS employee_sequence
    START WITH 1
    INCREMENT BY 1;
