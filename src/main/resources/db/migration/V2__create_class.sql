DROP TABLE IF EXISTS dev.Class ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.Class (
  id_class SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_diagram bigint NULL,
  name VARCHAR(45) NULL,
  superClassId bigint NULL,
  PRIMARY KEY (id_class),
  CONSTRAINT fk_Class_1
    FOREIGN KEY (id_diagram)
    REFERENCES dev.Diagram (id_diagram)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE INDEX fk_Class_1_idx ON dev.Class (id_diagram ASC);