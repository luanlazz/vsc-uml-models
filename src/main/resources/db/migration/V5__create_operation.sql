DROP TABLE IF EXISTS dev.Operation ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.Operation (
  idOperation SERIAL NOT NULL,
  idUml VARCHAR(45) NULL,
  idClass INT NULL,
  idReturn INT NULL,  
  name VARCHAR(45) NULL,
  idType INT NULL,
  visibility VARCHAR(45) NULL,
  isClass INT NULL,
  PRIMARY KEY (idOperation),
  CONSTRAINT fk_Operation_1
    FOREIGN KEY (idClass)
    REFERENCES dev.Class (idClass)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Operation_2
    FOREIGN KEY (idType)
    REFERENCES dev.Type (idType)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Operation_1_idx ON dev.Operation (idClass ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Operation_2_idx ON dev.Operation (idType ASC);