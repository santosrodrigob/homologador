use data_teste;
/*
drop table tb_bugs;
drop table tb_teste_atributo;
drop table tb_teste;
drop table tb_regra_negocio;
drop table tb_caso_teste;
drop table tb_comportamento;
drop table tb_tipo;
drop table tb_feature;
drop table tb_modulo;
drop table tb_filtro;
drop table tb_leia_me;
*/

/*
	select * from tb_modulo;
	select * from tb_filtro;
	select * from tb_feature;
	select * from tb_tipo;
	select * from tb_caso_teste;
	select * from tb_regra_negocio;
	select * from tb_comportamento;
	select * from tb_teste;
	select * from tb_filtro;
	select * from tb_leia_me;
*/

create table if not exists `tb_filtro`
(
	codigo_modulo int NULL,
    codigo_feature int NULL,
    codigo_tipo int NULL,
	solicitante varchar(50) NULL,
	codigo_modulo_leia_me int NULL,
	codigo_feature_leia_me int NULL,
    versao_leia_me varchar(15) NULL,
    data_alteracao date NULL
) Engine InnoDB;

create table if not exists `tb_usuario`
(
	id_usuario int auto_increment NOT NULL,
	codigo_usuario int NOT NULL,
    usuario varchar(50) NOT NULL,
    senha varchar(8) NOT NULL,
    inativo bit NULL,
		primary key (id_usuario)
) Engine InnoDB;

create table if not exists `tb_modulo`
(
	codigo_modulo int auto_increment, 
    modulo varchar(50) NOT NULL, 
    inativo bit NULL,
		primary key (codigo_modulo)
) Engine InnoDB;

create table if not exists `tb_feature`
(
	codigo_feature int auto_increment NOT NULL, 
    codigo_modulo int NOT NULL,
    feature varchar(50) NOT NULL,
    inativo bit NULL,
		primary key (codigo_feature)
) Engine InnoDB;
alter table tb_feature add foreign key (codigo_modulo) references tb_modulo (codigo_modulo);

create table if not exists `tb_tipo`
(
	codigo_tipo int auto_increment NOT NULL, 
    codigo_modulo int NOT NULL,
    codigo_feature int NOT NULL,
    tipo varchar(50) NOT NULL,
    inativo bit NULL,
		primary key (codigo_tipo)
) Engine InnoDB;
alter table tb_tipo add foreign key (codigo_modulo) references tb_modulo (codigo_modulo);
alter table tb_tipo add foreign key (codigo_feature) references tb_feature (codigo_feature);

create table if not exists `tb_regra_negocio`
(
	codigo_regra int auto_increment NOT NULL,
    regra_negocio varchar(2000) NOT NULL,
    codigo_modulo int NOT NULL,
    codigo_feature int NOT NULL,
	codigo_tipo int NOT NULL,
	teste_feature bit NOT NULL,
	codigo_usuario_criacao int NULL,
	codigo_usuario_alteracao int NULL,
	data_criacao datetime NULL,
	data_alteracao datetime NULL,
    inativo bit NULL,
		primary key (codigo_regra)
) Engine InnoDB;

alter table tb_regra_negocio add foreign key (codigo_modulo) references tb_modulo (codigo_modulo);
alter table tb_regra_negocio add foreign key (codigo_feature) references tb_feature (codigo_feature);
alter table tb_regra_negocio add foreign key (codigo_tipo) references tb_tipo (codigo_tipo);

create table if not exists `tb_comportamento`
(
	codigo_comportamento int auto_increment,
    comportamento varchar(2000) NOT NULL,
    codigo_modulo int NOT NULL,
    codigo_feature int NOT NULL,
	codigo_tipo int NOT NULL,
	teste_feature bit NOT NULL,
	codigo_usuario_criacao int NULL,
	codigo_usuario_alteracao int NULL,
	data_criacao datetime NULL,
	data_alteracao datetime NULL,
    inativo bit NULL,
		primary key (codigo_comportamento)
) Engine InnoDB;

alter table tb_comportamento add foreign key (codigo_modulo) references tb_modulo (codigo_modulo);
alter table tb_comportamento add foreign key (codigo_feature) references tb_feature (codigo_feature);
alter table tb_comportamento add foreign key (codigo_tipo) references tb_tipo (codigo_tipo);

create table if not exists `tb_caso_teste`
(
	codigo_caso_teste int auto_increment NOT NULL,
    caso_teste varchar(2000) NOT NULL,
    codigo_modulo int NOT NULL,
    codigo_feature int NOT NULL,
	codigo_tipo int NOT NULL,
	teste_feature bit NOT NULL,
	codigo_usuario_criacao int NULL,
	codigo_usuario_alteracao int NULL,
	data_criacao datetime NULL,
	data_alteracao datetime NULL,
    inativo bit NULL,
		primary key (codigo_caso_teste)
) Engine InnoDB;

alter table tb_caso_teste add foreign key (codigo_modulo) references tb_modulo (codigo_modulo);
alter table tb_caso_teste add foreign key (codigo_feature) references tb_feature (codigo_feature);
alter table tb_caso_teste add foreign key (codigo_tipo) references tb_tipo (codigo_tipo);

/* TESTES */

create table if not exists `tb_teste`
(
	codigo_teste int auto_increment NOT NULL,
    codigo_modulo int NOT NULL,
    codigo_feature int NOT NULL,
	codigo_tipo int NOT NULL,
    situacao_caso_teste bit NULL,
    situacao_regra bit NULL,
    situacao_comportamento bit NULL,
    conclusao varchar(2000) NULL,
    observacao varchar(2000) NULL,
    inativo bit NULL,
	codigo_usuario_criacao int NULL,
	codigo_usuario_alteracao int NULL,	
	data_criacao date NOT NULL,
	data_alteracao date NULL,
		primary key(codigo_teste)
) Engine InnoDB;

alter table tb_teste add foreign key (codigo_modulo) references tb_modulo (codigo_modulo);
alter table tb_teste add foreign key (codigo_feature) references tb_feature (codigo_feature);
alter table tb_teste add foreign key (codigo_tipo) references tb_tipo (codigo_tipo);

create table if not exists `tb_teste_atributo`
(
	id_teste int auto_increment NOT NULL ,
	codigo_teste int NOT NULL,
    codigo_modulo int NOT NULL,
    codigo_feature int NOT NULL,
	codigo_tipo int NOT NULL,
	codigo_caso_teste int NULL,
	codigo_comportamento int NULL,
	codigo_regra int NULL,
    situacao bit NULL,
    conclusao varchar(2000) NULL,
    observacao varchar(2000) NULL,
    data_hora_ini datetime NULL,
    data_hora_fim datetime NULL,
    inativo bit NULL,
		primary key(id_teste)
) Engine InnoDB;

alter table tb_teste_atributo add foreign key (codigo_modulo) references tb_modulo (codigo_modulo);
alter table tb_teste_atributo add foreign key (codigo_feature) references tb_feature (codigo_feature);
alter table tb_teste_atributo add foreign key (codigo_tipo) references tb_tipo (codigo_tipo);
alter table tb_teste_atributo add foreign key (codigo_teste) references tb_teste (codigo_teste);
alter table tb_teste_atributo add foreign key (codigo_caso_teste) references tb_caso_teste (codigo_caso_teste);
alter table tb_teste_atributo add foreign key (codigo_regra) references tb_regra_negocio (codigo_regra);
alter table tb_teste_atributo add foreign key (codigo_comportamento) references tb_comportamento (codigo_comportamento);

create table if not exists `tb_bugs`
(
	codigo_bug int auto_increment NOT NULL,
    bug	varchar(1000) NOT NULL,
	codigo_teste int NOT NULL,
	codigo_caso_teste int NULL,
	codigo_comportamento int NULL,
	codigo_regra int NULL,
    situacao bit NULL,
    tipo int NULL,
		primary key(codigo_bug)
) Engine InnoDB;

alter table tb_bugs add foreign key (codigo_teste) references tb_teste (codigo_teste);
alter table tb_bugs add foreign key (codigo_caso_teste) references tb_caso_teste (codigo_caso_teste);
alter table tb_bugs add foreign key (codigo_regra) references tb_regra_negocio (codigo_regra);
alter table tb_bugs add foreign key (codigo_comportamento) references tb_comportamento (codigo_comportamento);

create table if not exists `tb_leia_me`
(
	codigo_leia_me int auto_increment NOT NULL, 
    codigo_modulo int NOT NULL,
    codigo_feature int NOT NULL,
    leia_me varchar(2000) NOT NULL,
    versao varchar(20) NOT NULL,
    solicitante varchar(50) NOT NULL,
    codigo_usuario int NULL,
    data_criacao date NULL,
    data_alteracao date NULL,
    inativo bit NULL,
		primary key (codigo_leia_me)
) Engine InnoDB;
alter table tb_leia_me add foreign key (codigo_modulo) references tb_modulo (codigo_modulo);
alter table tb_leia_me add codigo_feature int NOT NULL;
alter table tb_leia_me add solicitante varchar(50) NOT NULL;

