DROP TABLE IF EXISTS dev.op_parameter ;

CREATE TABLE IF NOT EXISTS dev.op_parameter (
  id SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_version bigint NULL,
  id_operation bigint NULL,
  id_type bigint NULL,
  name VARCHAR(45) NULL,
  value_default VARCHAR(45) NULL,
  create_at timestamp NULL,
  modify_at timestamp NULL,
  deleted_at timestamp NULL,
  is_deleted boolean NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT fk_op_parameter_1
    FOREIGN KEY (id_operation)
    REFERENCES dev.Operation (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_op_parameter_2
    FOREIGN KEY (id_type)
    REFERENCES dev.type (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_op_parameter_3
    FOREIGN KEY (id_version)
    REFERENCES dev.version (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_op_parameter_1_idx ON dev.op_parameter (id_operation ASC);
CREATE INDEX fk_op_parameter_2_idx ON dev.op_parameter (id_type ASC);
CREATE INDEX fk_op_parameter_3_idx ON dev.op_parameter (id_version ASC);