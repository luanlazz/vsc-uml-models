DROP TABLE IF EXISTS dev.type ;

CREATE TABLE IF NOT EXISTS dev.type (
  id SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_version bigint NULL,
  name VARCHAR(45) NULL,
  create_at timestamp NULL,
  modify_at timestamp NULL,
  deleted_at timestamp NULL,
  is_deleted boolean NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT fk_type_1
    FOREIGN KEY (id_version)
    REFERENCES dev.version (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_type_1_idx ON dev.type (id_version ASC);
