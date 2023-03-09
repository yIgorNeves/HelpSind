CREATE TABLE users (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  password VARCHAR(100) NULL,
  active BOOL NULL,
  nome VARCHAR(50) NULL,
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

CREATE TABLE condominium (
  idCondominium BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  corporateName VARCHAR(100) NULL,
  cnpj VARCHAR(14) NULL,
  email VARCHAR(100) NULL,
  phone VARCHAR(10) NULL,
  cellphone VARCHAR(11) NULL,
  street VARCHAR(100) NULL,
  addressNumber VARCHAR(6) NULL,
  addressComplement VARCHAR(30) NULL,
  neighborhood VARCHAR(30) NULL,
  city VARCHAR(30) NULL,
  state VARCHAR(2) NULL,
  cep VARCHAR(8) NULL,
  PRIMARY KEY(idCondominium)
);

CREATE TABLE auths (
  id_user BIGINT UNSIGNED NOT NULL,
  auth VARCHAR(50) NOT NULL,
  PRIMARY KEY(id_user, auth),
  FOREIGN KEY(id_user)
    REFERENCES users(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);