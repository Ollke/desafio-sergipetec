CREATE DATABASE desafio_sergipetec;

-- DROP TABLE public.produto;

CREATE TABLE public.produto (
	produto_cd int8 NOT NULL,
	produto_sq int8 DEFAULT 1 NOT NULL,
	produto_nm varchar(50) NOT NULL,
	produto_ds varchar(50) NOT NULL,
	produto_qt int8 NOT NULL,
	produto_vl numeric(10, 2) NOT NULL,
	cadastro_dt timestamp(6) NOT NULL,
	ativo_fl bool NOT NULL,
	CONSTRAINT produto_pkey PRIMARY KEY (produto_cd, produto_sq)
);