create database llcdb;

CREATE USER 'llcuser'@'localhost' IDENTIFIED BY 'llcuser';

GRANT ALL PRIVILEGES ON * . * TO 'llcuser'@'localhost';

CREATE TABLE IF NOT EXISTS payment_txn (
txn_id INT PRIMARY KEY NOT NULL, 
seller_id INT NOT NULL, 
seller_name VARCHAR(40) NOT NULL, 
txn_amount FLOAT NOT NULL, 
split_id INT,
txn_created_time TIMESTAMP DEFAULT now(),
txn_updated_time TIMESTAMP DEFAULT now() on update now() 
);

alter table payment_txn add column citrus_mp_txn_id varchar(10) after txn_amount;

alter table payment_txn change txn_id txn_id INT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE payment_txn AUTO_INCREMENT = 100;

insert into payment_txn (seller_id, seller_name, txn_amount) values (1138, "Dummy123", 500);

alter table payment_txn add column txn_status ENUM('PAYMENT_INITIATED', 'PAYMENT_SUCCESSFUL', 'PAYMENT_FAILED', 'FUND_RELEASED', 'REFUND_INITIATED', 'REFUND_PROCESSED') after split_id;

alter table payment_txn change split_id split_id VARCHAR(10);

alter table payment_txn change seller_id seller_id VARCHAR(10);

alter table payment_txn change split_id split_id INT UNSIGNED;
alter table payment_txn change citrus_mp_txn_id citrus_mp_txn_id INT UNSIGNED;
alter table payment_txn change seller_id seller_id INT UNSIGNED;

########

CREATE TABLE IF NOT EXISTS engage_customer (
customer_id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
customer_name VARCHAR(40) NOT NULL,
customer_email99 VARCHAR(80) NOT NULL,
customer_passwd99 VARCHAR(80) NOT NULL,
customer_country_code varchar(5) NOT NULL,
customer_mobile_number varchar(11) NOT NULL,
customer_country varchar(20) NOT NULL,
customer_city varchar(20) NOT NULL,

customer_created_time TIMESTAMP DEFAULT now(),
customer_updated_time TIMESTAMP DEFAULT now() on update now() 
);

ALTER TABLE engage_customer AUTO_INCREMENT = 100000;

alter table payment_txn add column engage_customer_id INT UNSIGNED before seller_id;

ALTER TABLE payment_txn ADD CONSTRAINT fk_engage_customer_id FOREIGN KEY (engage_customer_id) REFERENCES engage_customer(customer_id);

CREATE TABLE IF NOT EXISTS engage_seller (
seller_id INT UNSIGNED PRIMARY KEY NOT NULL,
seller_name VARCHAR(80) NOT NULL,
seller_email99 VARCHAR(80) NOT NULL,
seller_passwd99 VARCHAR(80) NOT NULL,
seller_country_code varchar(5) NOT NULL,
seller_mobile_number varchar(11) NOT NULL,
seller_country varchar(20) NOT NULL,
seller_city varchar(20) NOT NULL,
seller_address varchar(200) NOT NULL,
seller_location_latitude FLOAT(10,6),
seller_location_longitude FLOAT(10,6),
seller_split_percent FLOAT not null default 0.0,
seller_kyc_doc_type varchar(20),
seller_kyc_doc_value varchar(40),

seller_created_time TIMESTAMP DEFAULT now(),
seller_updated_time TIMESTAMP DEFAULT now() on update now() 
);