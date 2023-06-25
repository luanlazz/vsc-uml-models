DROP TABLE IF EXISTS dev.Relationship ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.Relationship (
  idRelationship SERIAL NOT NULL,
  idUml VARCHAR(45) NULL,
  idClass1 int NULL,
  idClass2 int NULL,
  type VARCHAR(45) NULL,
  multipcity_Lower_1 VARCHAR(45) NULL,
  multipcity_Lower_2 VARCHAR(45) NULL,
  multipcity_Uper_1 VARCHAR(45) NULL,
  multipcity_Uper_2 VARCHAR(45) NULL,
  navigable_1 VARCHAR(45) NULL,
  PRIMARY KEY (idRelationship))
;