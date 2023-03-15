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
