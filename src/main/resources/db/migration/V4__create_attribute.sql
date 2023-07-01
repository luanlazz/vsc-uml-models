DROP TABLE IF EXISTS dev.Attribute ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.Attribute (
  id_attribute SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_class bigint NULL,
  name VARCHAR(45) NULL,
  id_type bigint NULL,
  visibility VARCHAR(45) NULL,
  PRIMARY KEY (id_attribute),
  CONSTRAINT fk_Attribute_1
    FOREIGN KEY (id_class)
    REFERENCES dev.Class (id_class)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Attribute_2
    FOREIGN KEY (id_type)
    REFERENCES dev.Type (id_type)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Attribute_1_idx ON dev.Attribute (id_class ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE INDEX fk_Attribute_2_idx ON dev.Attribute (id_type ASC);