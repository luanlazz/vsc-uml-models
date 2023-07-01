DROP TABLE IF EXISTS dev.Relationship ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.Relationship (
  id_relationship SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_class1 bigint NULL,
  id_class2 bigint NULL,
  type VARCHAR(45) NULL,
  multipcity_Lower_1 VARCHAR(45) NULL,
  multipcity_Lower_2 VARCHAR(45) NULL,
  multipcity_Uper_1 VARCHAR(45) NULL,
  multipcity_Uper_2 VARCHAR(45) NULL,
  navigable_1 VARCHAR(45) NULL,
  PRIMARY KEY (id_relationship))
;