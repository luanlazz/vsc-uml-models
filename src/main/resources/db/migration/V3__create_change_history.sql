DROP TABLE IF EXISTS dev.history_change;

CREATE TABLE IF NOT EXISTS dev.history_change (
  id SERIAL NOT NULL,
  id_diagram bigint NOT NULL,
  id_version bigint NOT NULL,
  entity_type VARCHAR(45) NULL,
  id_entity bigint NOT NULL,
  operation VARCHAR(45) NOT NULL,
  property VARCHAR(45) NULL,
  value VARCHAR(45) NULL,
  create_at timestamp NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT fk_history_change_1
    FOREIGN KEY (id_diagram)
    REFERENCES dev.diagram (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_history_change_2
    FOREIGN KEY (id_version)
    REFERENCES dev.version (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_history_change_1_idx ON dev.history_change (id_diagram ASC);
CREATE INDEX fk_history_change_2_idx ON dev.history_change (id_version ASC);
