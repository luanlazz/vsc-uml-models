DROP TABLE IF EXISTS dev.operation ;

CREATE TABLE IF NOT EXISTS dev.operation (
  id SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_version bigint NULL,
  id_class bigint NULL,
  id_return bigint NULL,  
  name VARCHAR(45) NULL,
  id_type bigint NULL,
  visibility VARCHAR(45) NULL,
  is_class INT NULL,
  create_at timestamp NULL,
  modify_at timestamp NULL,
  deleted_at timestamp NULL,
  is_deleted boolean NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT fk_operation_1
    FOREIGN KEY (id_class)
    REFERENCES dev.Class (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_operation_2
    FOREIGN KEY (id_type)
    REFERENCES dev.Type (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_operation_3
    FOREIGN KEY (id_version)
    REFERENCES dev.version (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_operation_1_idx ON dev.operation (id_class ASC);
CREATE INDEX fk_operation_2_idx ON dev.operation (id_type ASC);
CREATE INDEX fk_operation_3_idx ON dev.operation (id_version ASC);