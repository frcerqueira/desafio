CREATE TABLE usuario (
	email VARCHAR(50) PRIMARY KEY,
	nome VARCHAR(50),
	password VARCHAR(15),
	created TIMESTAMP,
	modified TIMESTAMP,
	last_login TIMESTAMP,
	token VARCHAR(128)
);

CREATE TABLE telefone (
/*	id INTEGER PRIMARY KEY, */
	ddd INTEGER,
	numero VARCHAR(10)
/*	,
	usuid INTEGER FOREIGN KEY REFERENCES usuario(id) */
);