DROP TABLE IF EXISTS dev.Parameter ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.Parameter (
  idParameter SERIAL NOT NULL,
  idUml VARCHAR(45) NULL,
  idOperation INT NULL,
  idType INT NULL,
  name VARCHAR(45) NULL,
  valueDefault VARCHAR(45) NULL,
  PRIMARY KEY (idParameter),
  CONSTRAINT fk_Parameter_1
    FOREIGN KEY (idOperation)
    REFERENCES dev.Operation (idOperation)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Parameter_2
    FOREIGN KEY (idType)
    REFERENCES dev.Type (idType)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Parameter_1_idx ON dev.Parameter (idOperation ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Parameter_2_idx ON dev.Parameter (idType ASC);