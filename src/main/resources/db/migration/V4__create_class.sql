DROP TABLE IF EXISTS dev.class ;

CREATE TABLE IF NOT EXISTS dev.class (
  id SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_version bigint NULL,
  id_diagram bigint NULL,
  name VARCHAR(45) NULL,
  superClassId bigint NULL,
  create_at timestamp NULL,
  modify_at timestamp NULL,
  deleted_at timestamp NULL,
  is_deleted boolean NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT fk_class_1
    FOREIGN KEY (id_diagram)
    REFERENCES dev.diagram (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_class_2
    FOREIGN KEY (id_version)
    REFERENCES dev.version (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_class_1_idx ON dev.class (id_diagram ASC);
CREATE INDEX fk_class_2_idx ON dev.class (id_version ASC);