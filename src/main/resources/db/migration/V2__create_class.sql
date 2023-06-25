DROP TABLE IF EXISTS dev.Class ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.Class (
  idClass SERIAL NOT NULL,
  idUml VARCHAR(45) NULL,
  idDiagram INT NULL,
  name VARCHAR(45) NULL,
  superClassId INT NULL,
  PRIMARY KEY (idClass),
  CONSTRAINT fk_Class_1
    FOREIGN KEY (idDiagram)
    REFERENCES dev.Diagram (idDiagram)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE INDEX fk_Class_1_idx ON dev.Class (idDiagram ASC);