DROP TABLE IF EXISTS dev.Parameter ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.op_parameter (
  id_parameter SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_operation bigint NULL,
  id_type bigint NULL,
  name VARCHAR(45) NULL,
  value_default VARCHAR(45) NULL,
  PRIMARY KEY (id_parameter),
  CONSTRAINT fk_op_parameter_1
    FOREIGN KEY (id_operation)
    REFERENCES dev.Operation (id_operation)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_op_parameter_2
    FOREIGN KEY (id_type)
    REFERENCES dev.Type (id_type)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Parameter_1_idx ON dev.op_parameter (id_operation ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Parameter_2_idx ON dev.op_parameter (id_type ASC);