DROP TABLE IF EXISTS dev.diagram ;

CREATE TABLE IF NOT EXISTS dev.diagram (
  id SERIAL NOT NULL,
  id_uml VARCHAR(45) NULL,
  name VARCHAR(45) NULL,
  type VARCHAR(45) NULL,
  create_at timestamp NULL,
  modify_at timestamp NULL,
  deleted_at timestamp NULL,
  is_deleted boolean NULL,
  
  PRIMARY KEY (id)
 );