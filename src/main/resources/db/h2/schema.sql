create table contract (
	id bigint not null,
	helper_id bigint,
	member_id bigint,
	primary key (id)
);

create table payment (
	id bigint not null,
	approve_no varchar(255),
	contract_id bigint,
	payment_method varchar(255),
	payment_request_state varchar(255),
	payment_type varchar(255),
	request_amount integer,
	request_date_time timestamp,
	response_date_time timestamp,
	payment_reject_id bigint,
	primary key (id),
    FOREIGN KEY(contract_id) REFERENCES contract(id)
);

create table payment_reject (
	id bigint not null,
	payment_id bigint,
	reason varchar(255),
	primary key (id),
    FOREIGN KEY(payment_id) REFERENCES payment(id)
);

Insert into contract (id, member_id, helper_id) values (1, 1, 11);
Insert into contract (id, member_id, helper_id) values (2, 2, 11);
Insert into contract (id, member_id, helper_id) values (3, 1, 22);
Insert into contract (id, member_id, helper_id) values (4, 1, 33);
Insert into contract (id, member_id, helper_id) values (5, 2, 33);