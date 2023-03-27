CREATE TABLE if not exists  condominium (
    idCondominium BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    corporateName VARCHAR(100) NULL,
    cnpj VARCHAR(14) NULL,
    email VARCHAR(100) NULL,
    phone VARCHAR(10) NULL,
    cellphone VARCHAR(11) NULL,
    address VARCHAR(100) NULL,
    addressNumber VARCHAR(6) NULL,
    addressComplement VARCHAR(30) NULL,
    neighborhood VARCHAR(30) NULL,
    city VARCHAR(30) NULL,
    state VARCHAR(2) NULL,
    cep VARCHAR(8) NULL,
    PRIMARY KEY(idCondominium)
    );
    
CREATE TABLE if not exists  users (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    password VARCHAR(100) NULL,
    active BOOL NULL,
    name VARCHAR(50) NULL,
    username VARCHAR(50) NULL,
    cpf VARCHAR(11) UNIQUE NULL,
    email VARCHAR(100) NULL,
    cellphone VARCHAR(100) NULL,
    auth VARCHAR(50) NULL,
    idCondominium BIGINT UNSIGNED NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(idCondominium)
    REFERENCES condominium(idCondominium)
    ON DELETE SET NULL
    ON UPDATE CASCADE
    );

CREATE TABLE if not exists  auths (
    id_user BIGINT UNSIGNED NOT NULL,
    auth VARCHAR(50) NOT NULL,
    PRIMARY KEY(id_user, auth),
    FOREIGN KEY(id_user)
    REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

CREATE TABLE if not exists  person (
    idPerson BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NULL,
    email VARCHAR(100) NULL,
    cpf VARCHAR(11) UNIQUE NULL,
    phone VARCHAR(15) NULL,
    cellphone VARCHAR(15) NULL,
    address VARCHAR(100) NULL,
    addressNumber VARCHAR(6) NULL,
    addressComplement VARCHAR(30) NULL,
    neighborhood VARCHAR(30) NULL,
    city VARCHAR(30) NULL,
    state VARCHAR(2) NULL,
    cep VARCHAR(8) NULL,
    idCondominium BIGINT UNSIGNED NOT NULL,
    type VARCHAR(30) NULL,
    PRIMARY KEY(idPerson),
    FOREIGN KEY(idCondominium)
    REFERENCES condominium(idCondominium)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

CREATE TABLE if not exists  apartments (
    idApartment BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    number VARCHAR(10) NULL,
    idCondominium BIGINT UNSIGNED NOT NULL,
    idPerson BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY(idApartment),
    FOREIGN KEY(idCondominium)
    REFERENCES condominium(idCondominium)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY(idPerson)
    REFERENCES person(idPerson)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

CREATE TABLE if not exists expenses (
	idExpense BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	idApartment BIGINT UNSIGNED NOT NULL,
	issuanceDate DATE NULL,
	expirationDate DATE NULL,
	value DECIMAL(9,2) NULL,
    name VARCHAR(255) NULL,
    situation CHAR NULL,
    receivingDate DATE NULL,
    idCondominium BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY(idExpense),
    FOREIGN KEY(idCondominium)
    REFERENCES condominium(idCondominium)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY(idApartment)
    REFERENCES apartments(idApartment)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

CREATE TABLE if not exists financialMovement (
	idMovement BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	date DATE NULL,
	value DECIMAL(9,2) NULL,
    source VARCHAR(20) NULL,
    description VARCHAR(255) NULL,
    PRIMARY KEY(idMovement)
    );
   
CREATE TABLE if not exists account (
	idAccount BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	type VARCHAR(1) NULL,
    description VARCHAR(255) NULL,
	initialBalance DECIMAL(9,2) NULL,
    currentBalance DECIMAL(9,2) NULL,
    idCondominium BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY(idAccount),
    FOREIGN KEY(idCondominium)
    REFERENCES condominium(idCondominium)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );


create table if not exists expense_type (
    id            bigint unsigned auto_increment
    primary key,
    value         decimal(9, 2)   null,
    name          varchar(255)    null,
    status        char            null,
    idcondominium bigint unsigned not null,
    constraint expense_type_ibfk_1
    foreign key (idcondominium) references condominium (idcondominium)
    );


alter table expenses
    add expense_type char null;

alter table expenses
drop column value;

alter table expenses
    add idExpenseType bigint unsigned null;

constraint foreign_key_name
        foreign key (idExpenseType) references expense_type (id);

alter table expenses
    change idExpenseType idexpensetype bigint unsigned null;



CREATE TABLE if not exists apartment_reading
(
    id_apartment_reading BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    id_apartment          BIGINT UNSIGNED NOT NULL ,
    last_measurement      DECIMAL(9, 2)   NOT NULL,
    current_measurement   DECIMAL(9, 2)   NOT NULL,
    id_condominium        BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id_apartment_reading),
    FOREIGN KEY (id_condominium)
    REFERENCES condominium (idCondominium)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (id_apartment)
    REFERENCES apartments (idapartment)
    );

CREATE TABLE if not exists expense_apartment_reading
(
    id_expense BIGINT UNSIGNED NOT NULL,
    id_apartment_reading BIGINT UNSIGNED NOT NULL ,
    FOREIGN KEY (id_apartment_reading)
    REFERENCES apartment_reading (id_apartment_reading),
    FOREIGN KEY (id_expense)
    REFERENCES expenses (idExpense)
    );

alter table expenses
    modify idapartment bigint unsigned null;

alter table expenses
    add column last_measurement DECIMAL(9, 2);

alter table expenses
    add column current_measurement DECIMAL(9, 2);

alter table expense_apartment_reading
    change id_expense idexpense bigint unsigned not null;

alter table expenses
    modify total decimal(10, 2) null;


create view reports as select uuid() as uuid ,D.*
                       from (select expenses.idExpense as id_expense,
                                    expenses.name                                                                             as expense_name,
                                    expenses.receivingDate                                                                    as expense_receiving_date,
                                    expenses.expirationDate                                                                   as expense_expiration_date,
                                    case when expenses.expense_type = 'P' then 'Proporcinal' else 'Igualitário' end           as type,
                                    case when expenses.situation = 'N' then 'Não Pago' else 'Pago' end                        as expense_situation,
                                    expenses.total                                                                            as total,
                                    et.name                                                                                   as expense_type,
                                    ar.id_apartment_reading,
                                    case when ar.id_apartment_reading is not null then a.idApartment else ape.idApartment end as id_apartment,
                                    case when ar.id_apartment_reading is not null then a.number else ape.number end           as apartment_number,
                                    case when ar.id_apartment_reading is not null then p.idPerson else apep.idPerson end      as id_person,
                                    case when ar.id_apartment_reading is not null then p.name else apep.name end              as person_name,
                                    expenses.idcondominium
                             from expenses
                                      left join expense_apartment_reading ear on expenses.idExpense = ear.idexpense
                                      left  join apartment_reading ar on ear.id_apartment_reading = ar.id_apartment_reading
                                      left join apartments a on ar.id_apartment = a.idApartment
                                      left join person p on a.idPerson = p.idPerson
                                      left join apartments ape on ape.idApartment = expenses.idapartment
                                      left join person apep on ape.idPerson = apep.idPerson
                                      left join expense_type et on expenses.idexpensetype = et.id
                                      left join condominium c on a.idCondominium = c.idCondominium) D;
