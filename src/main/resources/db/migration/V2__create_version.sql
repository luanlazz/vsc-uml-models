DROP TABLE IF EXISTS dev.version;

CREATE TABLE IF NOT EXISTS dev.version (
  id SERIAL NOT NULL,
  token VARCHAR(8) NULL,
  create_at timestamp NULL,
  
  PRIMARY KEY (id)
);