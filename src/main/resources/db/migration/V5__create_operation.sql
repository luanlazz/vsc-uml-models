DROP TABLE IF EXISTS dev.Operation ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.Operation (
  id_operation SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_class bigint NULL,
  id_return bigint NULL,  
  name VARCHAR(45) NULL,
  id_type bigint NULL,
  visibility VARCHAR(45) NULL,
  is_class INT NULL,
  PRIMARY KEY (id_operation),
  CONSTRAINT fk_Operation_1
    FOREIGN KEY (id_class)
    REFERENCES dev.Class (id_class)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Operation_2
    FOREIGN KEY (id_type)
    REFERENCES dev.Type (id_type)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Operation_1_idx ON dev.Operation (id_class ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Operation_2_idx ON dev.Operation (id_type ASC);