DROP TABLE IF EXISTS dev.relationship ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS dev.relationship (
  id SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_version bigint NULL,
  id_class1 bigint NULL,
  id_class2 bigint NULL,
  type VARCHAR(45) NULL,
  multipcity_Lower_1 VARCHAR(45) NULL,
  multipcity_Lower_2 VARCHAR(45) NULL,
  multipcity_Uper_1 VARCHAR(45) NULL,
  multipcity_Uper_2 VARCHAR(45) NULL,
  navigable_1 VARCHAR(45) NULL,
  create_at timestamp NULL,
  modify_at timestamp NULL,
  deleted_at timestamp NULL,
  is_deleted boolean NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT fk_relationship_1
    FOREIGN KEY (id_version)
    REFERENCES dev.version (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_relationship_1_idx ON dev.relationship (id_version ASC);