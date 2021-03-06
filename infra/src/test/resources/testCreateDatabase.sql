--
-- Copyright (C) 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--         http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--
DROP DATABASE IF EXISTS glasnost_local;
CREATE DATABASE glasnost_local CHARACTER SET utf8 COLLATE utf8_bin;
USE glasnost_local;
GRANT ALL ON *.* TO 'root'@'localhost' identified by '';


CREATE TABLE PROPER_NAME (ID BIGINT NOT NULL, NAMEDENTITY_ID BIGINT, WORDDEFINITION_ID BIGINT, VERTEX_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE DIAGRAMM (ID BIGINT NOT NULL, NAMESPACE_BASED TINYINT(1) default 0, DEFINITION_ID BIGINT, NAMESPACE_ID BIGINT NOT NULL, PRIMARY KEY (ID));
CREATE TABLE VERTEX (ID BIGINT NOT NULL, STEREOTYPE VARCHAR(255), NAME VARCHAR(255), NAMESPACE_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE EDGE_CONSTRAINT (ID BIGINT NOT NULL, WEIGHT DOUBLE, EDGE_ID BIGINT, DEFINITION_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE NAMED_ENTITY_REGISTER (ID BIGINT NOT NULL, NAME VARCHAR(255), ENTITY_VERTEX_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE FOLDER (ID BIGINT NOT NULL, REVISION BIGINT, VISIBILITY VARCHAR(255), NAME VARCHAR(255), CHANGEABILITY VARCHAR(255), DOMESTIC TINYINT(1) default 0, PHASE VARCHAR(255), OWNER_ID BIGINT, PARENT_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE TOPIC_MEMBERS (TOPIC_ID BIGINT NOT NULL, MEMBER_ID BIGINT NOT NULL, PRIMARY KEY (TOPIC_ID, MEMBER_ID));
CREATE TABLE EDGE (ID BIGINT NOT NULL, WEIGHT DOUBLE, STEREOTYPE VARCHAR(255), CLIENT_ID BIGINT, CONTEXT_ID BIGINT, TYPE_ID BIGINT, SUPPLIER_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE RESOURCE_DESC (ID BIGINT NOT NULL, LANGUAGE VARCHAR(255), TYPE VARCHAR(255), REFERENCE VARCHAR(255), LITERAL VARCHAR(255), VERTEX_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE TERM_LINK (ID BIGINT NOT NULL, WORDDEFINITION_ID BIGINT, VERTEX_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE WORD_DEF (ID BIGINT NOT NULL, DTYPE VARCHAR(31), REQUIREDGENUS VARCHAR(255), NORMALIZED VARCHAR(255), NENNFORM VARCHAR(255), FLAVOR VARCHAR(255), ORIGIN VARCHAR(255), GENUS VARCHAR(255), REQUIREDCASUS VARCHAR(255), PREFIX VARCHAR(255), WORDCLASS VARCHAR(255), LANGUAGE VARCHAR(255), RADICAL VARCHAR(255), FLECT_CLASS BIGINT, BASE_DEF BIGINT, SUPERLATIVEBASE VARCHAR(255), COMPARATIVEBASE VARCHAR(255), PLURALBASE VARCHAR(255), PARTICIPLE2 VARCHAR(255), PARTICIPLE1 VARCHAR(255), IRREGULAR TINYINT(1) default 0, PRIMARY KEY (ID));
CREATE TABLE ASSOC_IMPLICATION (ID BIGINT NOT NULL, STEREOTYPE VARCHAR(255), ASSOC_TYPE BIGINT, CLIENT BIGINT, IMPL_VERTEX BIGINT, SUPPLIER BIGINT, PRIMARY KEY (ID));
CREATE TABLE EQUAL_IMPLICATION (ID BIGINT NOT NULL, IMPL_VERTEX BIGINT, SUBJECT BIGINT, EQUIVALENT BIGINT, PRIMARY KEY (ID));
CREATE TABLE DOMAIN (ID BIGINT NOT NULL, NAME VARCHAR(255), DOMESTIC TINYINT(1) default 0, ROOT_FOLDER BIGINT, PRIMARY KEY (ID));
CREATE TABLE CONTEXT (ID BIGINT NOT NULL, CREATED DATETIME, OWNER_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE NAMESPACE (ID BIGINT NOT NULL, REVISION BIGINT, VISIBILITY VARCHAR(255), COUNTER BIGINT, PREFIX VARCHAR(255), CHANGEABILITY VARCHAR(255), DOMESTIC TINYINT(1) default 0, PHASE VARCHAR(255), URI VARCHAR(255), OWNER_ID BIGINT, DOMAIN_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE GRAMMATICAL_ATTR (ID BIGINT NOT NULL, VALUE VARCHAR(255), TYPE VARCHAR(255), WORD_DEF_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE FLECT_CLASS (ID BIGINT NOT NULL, WORDCLASS VARCHAR(255), PATTERN VARCHAR(255), NAME VARCHAR(255), GENUS VARCHAR(255), LANGUAGE VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE CONTENT_DEF (ID BIGINT NOT NULL, REVISION BIGINT, VISIBILITY VARCHAR(255), NAME VARCHAR(255), CHANGEABILITY VARCHAR(255), XML_CONTENT VARCHAR(255), TYPE VARCHAR(255), DOMESTIC TINYINT(1) default 0, PHASE VARCHAR(255), FOLDER_ID BIGINT, OWNER_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE COMPLEX_TERM (ID BIGINT NOT NULL, WORD_DEF_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE COMPLEX_TERM_ELEMENTS (TERM_ID BIGINT NOT NULL, WORD_DEF_ID BIGINT NOT NULL, PRIMARY KEY (TERM_ID, WORD_DEF_ID));
CREATE TABLE TIME_SPEC (ID BIGINT NOT NULL, MASK VARCHAR(255), END_TSP DATETIME, BEGIN_TSP DATETIME, VERTEX_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE WORD_FLECTED (ID BIGINT NOT NULL, PERSON VARCHAR(255), ADJECTIVE_COMP VARCHAR(255), REQUIRED_CASUS VARCHAR(255), MODUS VARCHAR(255), NAME VARCHAR(255), FLAVOR VARCHAR(255), NUMERUS VARCHAR(255), GENUS VARCHAR(255), TEMPUS VARCHAR(255), CASUS VARCHAR(255), DEFINITION_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE AFFIX (ID BIGINT NOT NULL, NAME VARCHAR(255), LANGUAGE VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE IDENT (ID BIGINT NOT NULL, DTYPE VARCHAR(31), NAME VARCHAR(255), TYPE VARCHAR(255), DOMESTIC TINYINT(1) default 0, DOMAIN_ID BIGINT, EMAIL VARCHAR(255), STREET VARCHAR(255), FULLNAME VARCHAR(255), PASSWORD VARCHAR(255), PREFERREDLANGUAGE VARCHAR(255), COUNTRY VARCHAR(255), CITY VARCHAR(255), DESCRIPTON VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE ELEMENTARY_DATA (ID BIGINT NOT NULL, STRING_VAL VARCHAR(255), DECIMAL_VAL DOUBLE, TYPE VARCHAR(255) NOT NULL, INT_VAL BIGINT, UNIT_ID BIGINT, VERTEX_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE GROUP_MEMBERS (USER_ID BIGINT NOT NULL, GROUP_ID BIGINT NOT NULL, PRIMARY KEY (USER_ID, GROUP_ID));
CREATE TABLE ABBR_DEF (ID BIGINT NOT NULL, STYLE VARCHAR(255), NAME VARCHAR(255), ORIGIN VARCHAR(255), LANGUAGE VARCHAR(255), WORD_DEF_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE TERM_MI (ID BIGINT NOT NULL, REVISION BIGINT, CREATE_REVISION BIGINT, STATE VARCHAR(255), TERMINOLOGY_ID BIGINT, DEFINITION_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE TERMINOLOGY (ID BIGINT NOT NULL, REVISION BIGINT, VISIBILITY VARCHAR(255), NAME VARCHAR(255), CHANGEABILITY VARCHAR(255), LANGUAGE VARCHAR(255), TYPE VARCHAR(255), DOMESTIC TINYINT(1) default 0, URI VARCHAR(255), MODIFIED TINYINT(1) default 0, OWNER_ID VARCHAR(255), DTYPE VARCHAR(31), PRIMARY KEY (ID));
ALTER TABLE PROPER_NAME ADD CONSTRAINT FK_PROPER_NAME_VERTEX_ID FOREIGN KEY (VERTEX_ID) REFERENCES VERTEX (ID);
ALTER TABLE PROPER_NAME ADD CONSTRAINT FK_PROPER_NAME_WORDDEFINITION_ID FOREIGN KEY (WORDDEFINITION_ID) REFERENCES WORD_DEF (ID);
ALTER TABLE PROPER_NAME ADD CONSTRAINT FK_PROPER_NAME_NAMEDENTITY_ID FOREIGN KEY (NAMEDENTITY_ID) REFERENCES NAMED_ENTITY_REGISTER (ID);
ALTER TABLE DIAGRAMM ADD CONSTRAINT FK_DIAGRAMM_NAMESPACE_ID FOREIGN KEY (NAMESPACE_ID) REFERENCES NAMESPACE (ID);
ALTER TABLE DIAGRAMM ADD CONSTRAINT FK_DIAGRAMM_DEFINITION_ID FOREIGN KEY (DEFINITION_ID) REFERENCES CONTENT_DEF (ID);
ALTER TABLE VERTEX ADD CONSTRAINT FK_VERTEX_NAMESPACE_ID FOREIGN KEY (NAMESPACE_ID) REFERENCES NAMESPACE (ID);
ALTER TABLE EDGE_CONSTRAINT ADD CONSTRAINT FK_EDGE_CONSTRAINT_EDGE_ID FOREIGN KEY (EDGE_ID) REFERENCES EDGE (ID);
ALTER TABLE EDGE_CONSTRAINT ADD CONSTRAINT FK_EDGE_CONSTRAINT_DEFINITION_ID FOREIGN KEY (DEFINITION_ID) REFERENCES VERTEX (ID);
ALTER TABLE NAMED_ENTITY_REGISTER ADD CONSTRAINT FK_NAMED_ENTITY_REGISTER_ENTITY_VERTEX_ID FOREIGN KEY (ENTITY_VERTEX_ID) REFERENCES VERTEX (ID);
ALTER TABLE FOLDER ADD CONSTRAINT FK_FOLDER_PARENT_ID FOREIGN KEY (PARENT_ID) REFERENCES FOLDER (ID);
ALTER TABLE FOLDER ADD CONSTRAINT FK_FOLDER_OWNER_ID FOREIGN KEY (OWNER_ID) REFERENCES IDENT (ID);
ALTER TABLE TOPIC_MEMBERS ADD CONSTRAINT FK_TOPIC_MEMBERS_MEMBER_ID FOREIGN KEY (MEMBER_ID) REFERENCES VERTEX (ID);
ALTER TABLE TOPIC_MEMBERS ADD CONSTRAINT FK_TOPIC_MEMBERS_TOPIC_ID FOREIGN KEY (TOPIC_ID) REFERENCES DIAGRAMM (ID);
ALTER TABLE EDGE ADD CONSTRAINT FK_EDGE_CONTEXT_ID FOREIGN KEY (CONTEXT_ID) REFERENCES CONTEXT (ID);
ALTER TABLE EDGE ADD CONSTRAINT FK_EDGE_TYPE_ID FOREIGN KEY (TYPE_ID) REFERENCES VERTEX (ID);
ALTER TABLE EDGE ADD CONSTRAINT FK_EDGE_CLIENT_ID FOREIGN KEY (CLIENT_ID) REFERENCES VERTEX (ID);
ALTER TABLE EDGE ADD CONSTRAINT FK_EDGE_SUPPLIER_ID FOREIGN KEY (SUPPLIER_ID) REFERENCES VERTEX (ID);
ALTER TABLE RESOURCE_DESC ADD CONSTRAINT FK_RESOURCE_DESC_VERTEX_ID FOREIGN KEY (VERTEX_ID) REFERENCES VERTEX (ID);
ALTER TABLE TERM_LINK ADD CONSTRAINT FK_TERM_LINK_WORDDEFINITION_ID FOREIGN KEY (WORDDEFINITION_ID) REFERENCES WORD_DEF (ID);
ALTER TABLE TERM_LINK ADD CONSTRAINT FK_TERM_LINK_VERTEX_ID FOREIGN KEY (VERTEX_ID) REFERENCES VERTEX (ID);
ALTER TABLE WORD_DEF ADD CONSTRAINT FK_WORD_DEF_FLECT_CLASS FOREIGN KEY (FLECT_CLASS) REFERENCES FLECT_CLASS (ID);
ALTER TABLE WORD_DEF ADD CONSTRAINT FK_WORD_DEF_BASE_DEF FOREIGN KEY (BASE_DEF) REFERENCES WORD_DEF (ID);
ALTER TABLE ASSOC_IMPLICATION ADD CONSTRAINT FK_ASSOC_IMPLICATION_SUPPLIER FOREIGN KEY (SUPPLIER) REFERENCES VERTEX (ID);
ALTER TABLE ASSOC_IMPLICATION ADD CONSTRAINT FK_ASSOC_IMPLICATION_CLIENT FOREIGN KEY (CLIENT) REFERENCES VERTEX (ID);
ALTER TABLE ASSOC_IMPLICATION ADD CONSTRAINT FK_ASSOC_IMPLICATION_IMPL_VERTEX FOREIGN KEY (IMPL_VERTEX) REFERENCES VERTEX (ID);
ALTER TABLE ASSOC_IMPLICATION ADD CONSTRAINT FK_ASSOC_IMPLICATION_ASSOC_TYPE FOREIGN KEY (ASSOC_TYPE) REFERENCES VERTEX (ID);
ALTER TABLE EQUAL_IMPLICATION ADD CONSTRAINT FK_EQUAL_IMPLICATION_SUBJECT FOREIGN KEY (SUBJECT) REFERENCES VERTEX (ID);
ALTER TABLE EQUAL_IMPLICATION ADD CONSTRAINT FK_EQUAL_IMPLICATION_EQUIVALENT FOREIGN KEY (EQUIVALENT) REFERENCES VERTEX (ID);
ALTER TABLE EQUAL_IMPLICATION ADD CONSTRAINT FK_EQUAL_IMPLICATION_IMPL_VERTEX FOREIGN KEY (IMPL_VERTEX) REFERENCES VERTEX (ID);
ALTER TABLE DOMAIN ADD CONSTRAINT FK_DOMAIN_ROOT_FOLDER FOREIGN KEY (ROOT_FOLDER) REFERENCES FOLDER (ID);
ALTER TABLE CONTEXT ADD CONSTRAINT FK_CONTEXT_OWNER_ID FOREIGN KEY (OWNER_ID) REFERENCES IDENT (ID);
ALTER TABLE NAMESPACE ADD CONSTRAINT FK_NAMESPACE_DOMAIN_ID FOREIGN KEY (DOMAIN_ID) REFERENCES DOMAIN (ID);
ALTER TABLE NAMESPACE ADD CONSTRAINT FK_NAMESPACE_OWNER_ID FOREIGN KEY (OWNER_ID) REFERENCES IDENT (ID);
ALTER TABLE GRAMMATICAL_ATTR ADD CONSTRAINT FK_GRAMMATICAL_ATTR_WORD_DEF_ID FOREIGN KEY (WORD_DEF_ID) REFERENCES WORD_DEF (ID);
ALTER TABLE CONTENT_DEF ADD CONSTRAINT FK_CONTENT_DEF_OWNER_ID FOREIGN KEY (OWNER_ID) REFERENCES IDENT (ID);
ALTER TABLE CONTENT_DEF ADD CONSTRAINT FK_CONTENT_DEF_FOLDER_ID FOREIGN KEY (FOLDER_ID) REFERENCES FOLDER (ID);
ALTER TABLE COMPLEX_TERM ADD CONSTRAINT FK_COMPLEX_TERM_WORD_DEF_ID FOREIGN KEY (WORD_DEF_ID) REFERENCES WORD_DEF (ID);
ALTER TABLE COMPLEX_TERM_ELEMENTS ADD CONSTRAINT FK_COMPLEX_TERM_ELEMENTS_WORD_DEF_ID FOREIGN KEY (WORD_DEF_ID) REFERENCES WORD_DEF (ID);
ALTER TABLE COMPLEX_TERM_ELEMENTS ADD CONSTRAINT FK_COMPLEX_TERM_ELEMENTS_TERM_ID FOREIGN KEY (TERM_ID) REFERENCES COMPLEX_TERM (ID);
ALTER TABLE TIME_SPEC ADD CONSTRAINT FK_TIME_SPEC_VERTEX_ID FOREIGN KEY (VERTEX_ID) REFERENCES VERTEX (ID);
ALTER TABLE WORD_FLECTED ADD CONSTRAINT FK_WORD_FLECTED_DEFINITION_ID FOREIGN KEY (DEFINITION_ID) REFERENCES WORD_DEF (ID);
ALTER TABLE IDENT ADD CONSTRAINT FK_IDENT_DOMAIN_ID FOREIGN KEY (DOMAIN_ID) REFERENCES DOMAIN (ID);
ALTER TABLE ELEMENTARY_DATA ADD CONSTRAINT FK_ELEMENTARY_DATA_UNIT_ID FOREIGN KEY (UNIT_ID) REFERENCES VERTEX (ID);
ALTER TABLE ELEMENTARY_DATA ADD CONSTRAINT FK_ELEMENTARY_DATA_VERTEX_ID FOREIGN KEY (VERTEX_ID) REFERENCES VERTEX (ID);
ALTER TABLE GROUP_MEMBERS ADD CONSTRAINT FK_GROUP_MEMBERS_USER_ID FOREIGN KEY (USER_ID) REFERENCES IDENT (ID);
ALTER TABLE GROUP_MEMBERS ADD CONSTRAINT FK_GROUP_MEMBERS_GROUP_ID FOREIGN KEY (GROUP_ID) REFERENCES IDENT (ID);
ALTER TABLE ABBR_DEF ADD CONSTRAINT FK_ABBR_DEF_WORD_DEF_ID FOREIGN KEY (WORD_DEF_ID) REFERENCES WORD_DEF (ID);
ALTER TABLE TERM_MI ADD CONSTRAINT FK_TERM_MI_DEFINITION_ID FOREIGN KEY (DEFINITION_ID) REFERENCES WORD_DEF (ID);
ALTER TABLE TERM_MI ADD CONSTRAINT FK_TERM_MI_TERMINOLOGY_ID FOREIGN KEY (TERMINOLOGY_ID) REFERENCES TERMINOLOGY (ID);
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME));
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0);

;

CREATE INDEX IDX_WF_1 ON WORD_FLECTED(NAME);

CREATE INDEX IDX_WF_2 ON WORD_FLECTED(DEFINITION_ID);

;
CREATE INDEX IDX_WD_1 ON WORD_DEF(RADICAL);

CREATE INDEX IDX_WD_2 ON WORD_DEF(WORDCLASS);

CREATE INDEX IDX_WD_3 ON WORD_DEF(LANGUAGE);

CREATE INDEX IDX_WD_4 ON WORD_DEF(GENUS);

CREATE INDEX IDX_WD_5 ON WORD_DEF(PLURALBASE);

CREATE INDEX IDX_WD_6 ON WORD_DEF(NENNFORM);

CREATE INDEX IDX_WD_7 ON WORD_DEF(NORMALIZED);

CREATE INDEX IDX_WD_8 ON WORD_DEF(PARTICIPLE1);

CREATE INDEX IDX_WD_9 ON WORD_DEF(PARTICIPLE2);

CREATE INDEX IDX_WD_10 ON WORD_DEF(COMPARATIVEBASE);

CREATE INDEX IDX_WD_11 ON WORD_DEF(SUPERLATIVEBASE);

;
CREATE INDEX IDX_EDGE_1 ON EDGE(SUPPLIER_ID);

CREATE INDEX IDX_EDGE_2 ON EDGE(CLIENT_ID);

CREATE INDEX IDX_EDGE_3 ON EDGE(STEREOTYPE);

CREATE INDEX IDX_EDGE_4 ON EDGE(TYPE_ID);

;
CREATE INDEX IDX_TL_1 ON TERM_LINK(WORDDEFINITION_ID);

CREATE INDEX IDX_TL_2 ON TERM_LINK(VERTEX_ID);

;
CREATE INDEX IDX_VTX_1 ON VERTEX(NAMESPACE_ID);

CREATE INDEX IDX_VTX_2 ON VERTEX(NAME);

;
CREATE INDEX IDX_TMI_1 ON TERM_MI(DEFINITION_ID);

CREATE INDEX IDX_TMI_2 ON TERM_MI(TERMINOLOGY_ID);

CREATE INDEX IDX_TMI_3 ON TERM_MI(REVISION);

CREATE INDEX IDX_TMI_4 ON TERM_MI(STATE);

;
CREATE INDEX IDX_PNAME_1 ON PROPER_NAME(NAMEDENTITY_ID);

CREATE INDEX IDX_PNAME_2 ON PROPER_NAME(WORDDEFINITION_ID);

CREATE INDEX IDX_PNAME_3 ON PROPER_NAME(VERTEX_ID);


;
CREATE INDEX IDX_NER_1 ON NAMED_ENTITY_REGISTER(ENTITY_VERTEX_ID);


-- DROP VIEW V_ASSOC;

/*CREATE VIEW V_ASSOC (ID, SUPPLIER, STEREOTYPE, ASTYPE, CLIENT ) AS
SELECT e.id, s.name, e.stereotype, t.name, c.name
FROM EDGE e 
LEFT JOIN VERTEX s ON s.id = e.supplier_id
LEFT JOIN VERTEX c ON c.id = e.client_id
LEFT JOIN VERTEX t ON t.id = e.type_id;

DROP VIEW V_TOPIC;

CREATE VIEW V_TOPIC (TOPIC, MEMBER, NAMESPACE ) AS
SELECT t.name, v.name, n.uri  
FROM TOPIC_MEMBERS tm
LEFT JOIN VERTEX v ON v.id = tm.member_id 
LEFT JOIN DIAGRAM t ON t.id = tm.topic_id
LEFT JOIN NAMESPACE n ON n.id = t.namespace_id;

DROP VIEW V_INTENSIONS;

CREATE VIEW V_INTENSIONS (WORD, NAMESPACE, NODE ) AS
SELECT w.nennform, n.uri, v.name  
FROM TERM_LINK tl
LEFT JOIN VERTEX term ON term.id = tl.vertex_id 
LEFT JOIN EDGE edge ON edge.supplier_id = term.id 
LEFT JOIN VERTEX v ON v.id = edge.client_id 
LEFT JOIN WORD_DEF w ON w.id = tl.wordDefinition_id
LEFT JOIN NAMESPACE n ON n.id = v.namespace_id; 

DROP VIEW V_MODIFIED_TERMS;

CREATE VIEW V_MODIFIED_TERMS (LANGUAGE, WORDCLASS, NENNFORM, STATE, VERSION ) AS
SELECT wd.language, wd.wordclass, wd.nennform, mi.state, mi.revision  
FROM WORD_DEF wd
LEFT JOIN TERM_MI mi ON wd.id = mi.worddef_id
WHERE mi.state != 'UNMODIFIED';

DROP VIEW V_NAME_REGISTER;

CREATE VIEW V_NAME_REGISTER(register_id, entity, word_def_id, nennform, name) AS
SELECT ner.id, ner.name, wd.id, wd.nennform, v.name
FROM NAMED_ENTITY_REGISTER ner
LEFT JOIN PROPER_NAME pn ON pn.namedentity_id = ner.id
LEFT JOIN WORD_DEF wd on wd.id = pn.worddefinition_id
LEFT JOIN VERTEX v on v.id= ner.ENTITY_VERTEX_ID;

 */
UPDATE sequence SET seq_count = 10000;

INSERT INTO FOLDER (ID, NAME, DOMESTIC) VALUES (1, '/', 1);
INSERT INTO FOLDER (ID, NAME, DOMESTIC) VALUES (2, '/', 0);
INSERT INTO FOLDER (ID, NAME, DOMESTIC) VALUES (3, '/', 0);
INSERT INTO FOLDER (ID, NAME, DOMESTIC) VALUES (4, '/', 0);

INSERT INTO DOMAIN (ID, NAME, DOMESTIC, ROOT_FOLDER) VALUES (1, 'LOCAL_DOMAIN', 1, 1);
INSERT INTO DOMAIN (ID, NAME, DOMESTIC, ROOT_FOLDER) VALUES (2, 'tinybluepla.net', 0, 2);
INSERT INTO DOMAIN (ID, NAME, DOMESTIC, ROOT_FOLDER) VALUES (3, 'lichtflut.de', 0, 3);
INSERT INTO DOMAIN (ID, NAME, DOMESTIC, ROOT_FOLDER) VALUES (4, 'arastreju.org', 0, 4);

INSERT INTO IDENT (ID, DTYPE, NAME, TYPE, DOMESTIC, DOMAIN_ID, PASSWORD ) VALUES (1, 'UserDBO', 'root', 'USER', 1, 1, '63a8efe97bb87f50796b649d84481845');

SET autocommit=0
