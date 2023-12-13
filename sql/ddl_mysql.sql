--mysql version

--db먼저 생성
CREATE DATABASE js_tutorial_makeSite;
USE js_tutorial_makeSite;

CREATE TABLE js_site_member
(
  Auto_serial_number BIGINT AUTO_INCREMENT,
  ID VARCHAR(255),
  PW VARCHAR(255),
  NickName VARCHAR(255),
  PRIMARY KEY (Auto_serial_number)
);

--두 db를 연결하려면 ID에 unique 처리를 해야함!
ALTER TABLE js_site_member
ADD CONSTRAINT unique_id_constraint UNIQUE (ID);

CREATE TABLE Background_img (
    Auto_serial_number BIGINT AUTO_INCREMENT PRIMARY KEY,
    ID VARCHAR(255),
    file_name VARCHAR(255),
    file_path VARCHAR(255),
    file_size BIGINT,
    creation_time TIMESTAMP,
    FOREIGN KEY (ID) REFERENCES js_site_member(ID)
);