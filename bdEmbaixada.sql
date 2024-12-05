DROP DATABASE IF EXISTS embaixadaBrasil  ;
CREATE DATABASE embaixadaBrasil  ;                 
USE embaixadaBrasil;

create table usuario(
	idusuario INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	nomeusuario VARCHAR(50) NOT NULL,
    email varchar(50) not null,
    senha varchar(50) not null,
	rg  varchar(50) not null,
	cpf varchar( 50) not null,
	dataNascimento date not null,
    sexo varchar(50) not null,
    nacionalidade varchar(50) not null,
	cep varchar(50) not null,
    numeroTelefone varchar (50) not null,
	estadoCivil varchar (50) not null
);


create table passaporte (
	idpassaporte int primary key auto_increment,
	numeroPassaporte varchar(50) not null,
	dataEmissao date not null,
    autoridadeEmissora VARCHAR(50) NOT NULL,
	idusuario int NOT NULL,
	foreign key (idusuario) references usuario(idusuario) 
); 


create table visto (
	idvisto int primary key auto_increment,
    tipo VARCHAR(50) NOT NULL,
	paisesdestino varchar(255) not null,
	statusVisto varchar(50) not null,
	dataEmissao date not null,
    dataValidade DATE NOT NULL,
    idusuario int not null,
    idpassaporte INT NOT NULL,
	foreign key (idusuario) references usuario(idusuario),
    FOREIGN KEY (idpassaporte) REFERENCES passaporte(idpassaporte)
);



select * from usuario;
select * from passaporte;
select * from visto;


