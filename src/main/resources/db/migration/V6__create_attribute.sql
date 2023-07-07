DROP TABLE IF EXISTS dev.attribute ;

CREATE TABLE IF NOT EXISTS dev.attribute (
  id SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  id_version bigint NULL,
  id_class bigint NULL,
  name VARCHAR(45) NULL,
  id_type bigint NULL,
  visibility VARCHAR(45) NULL,
  create_at timestamp NULL,
  modify_at timestamp NULL,
  deleted_at timestamp NULL,
  is_deleted boolean NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT fk_attribute_1
    FOREIGN KEY (id_class)
    REFERENCES dev.class (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_attribute_2
    FOREIGN KEY (id_type)
    REFERENCES dev.Type (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_attribute_3
    FOREIGN KEY (id_version)
    REFERENCES dev.version (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_attribute_1_idx ON dev.attribute (id_class ASC);
CREATE INDEX fk_attribute_2_idx ON dev.attribute (id_type ASC);
CREATE INDEX fk_attribute_3_idx ON dev.attribute (id_version ASC);