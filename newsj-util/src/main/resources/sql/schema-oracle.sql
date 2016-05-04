--------------------------------------------------------
--  Oracle Schema
--------------------------------------------------------
DROP TABLE "BINARIES" cascade constraints;
DROP TABLE "BINARYBLACKLIST" cascade constraints;
DROP TABLE "CATEGORY" cascade constraints;
DROP TABLE "CONSOLEINFO" cascade constraints;
DROP TABLE "CONTENT" cascade constraints;
DROP TABLE "FORUMPOST" cascade constraints;
DROP TABLE "GENRES" cascade constraints;
DROP TABLE "GROUPS" cascade constraints;
DROP TABLE "MENU" cascade constraints;
DROP TABLE "MOVIEINFO" cascade constraints;
DROP TABLE "MUSICINFO" cascade constraints;
DROP TABLE "PARTREPAIR" cascade constraints;
DROP TABLE "PARTS" cascade constraints;
DROP TABLE "RELEASECOMMENT" cascade constraints;
DROP TABLE "RELEASENFO" cascade constraints;
DROP TABLE "RELEASEREGEX" cascade constraints;
DROP TABLE "RELEASES" cascade constraints;
DROP TABLE "SITE" cascade constraints;
DROP TABLE "TVRAGE" cascade constraints;
DROP TABLE "USERCART" cascade constraints;
DROP TABLE "USEREXCAT" cascade constraints;
DROP TABLE "USERINVITE" cascade constraints;
DROP TABLE "USERS" cascade constraints;
DROP SEQUENCE "BINARYBLACKLISTENTRY_SEQ";
DROP SEQUENCE "BINARY_SEQ";
DROP SEQUENCE "FORUMPOST_SEQ";
DROP SEQUENCE "GROUP_SEQ";
DROP SEQUENCE "NEWSJ_SEQ";
DROP SEQUENCE "PARTREPAIR_SEQ";
DROP SEQUENCE "PART_SEQ";
DROP SEQUENCE "RELEASECOMMENT_SEQ";
DROP SEQUENCE "RELEASENFO_SEQ";
DROP SEQUENCE "RELEASEREGEX_SEQ";
DROP SEQUENCE "RELEASE_SEQ";
DROP SEQUENCE "USER_SEQ";
--------------------------------------------------------
--  DDL for Sequence BINARYBLACKLISTENTRY_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "BINARYBLACKLISTENTRY_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 10 START WITH 100001 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence BINARY_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "BINARY_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence FORUMPOST_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "FORUMPOST_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 101 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence GROUP_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "GROUP_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 101 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence NEWSJ_SEQ
--------------------------------------------------------

CREATE SEQUENCE "NEWSJ_SEQ"
MINVALUE 1
INCREMENT BY 100
START WITH 10301 CACHE 20;
--------------------------------------------------------
--  DDL for Sequence PARTREPAIR_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "PARTREPAIR_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence PART_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "PART_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 1000 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence RELEASECOMMENT_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASECOMMENT_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence RELEASENFO_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASENFO_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence RELEASEREGEX_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASEREGEX_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1001 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence RELEASE_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASE_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence USER_SEQ
--------------------------------------------------------

CREATE SEQUENCE "USER_SEQ"
MINVALUE 1
INCREMENT BY 100
START WITH 1 CACHE 20;
--------------------------------------------------------
--  DDL for Table BINARIES
--------------------------------------------------------

CREATE TABLE BINARIES
(
   ID INTEGER PRIMARY KEY NOT NULL,
   NAME_ VARCHAR2(1024) NOT NULL,
   FROMNAME VARCHAR2(255) NOT NULL,
   DATE_ DATE,
   XREF VARCHAR2(255),
   TOTALPARTS INTEGER DEFAULT 0 NOT NULL,
   GROUPID INTEGER DEFAULT 0 NOT NULL,
   PROCSTAT INTEGER DEFAULT 0,
   PROCATTEMPTS INTEGER DEFAULT 0,
   CATEGORYID INTEGER,
   REGEXID INTEGER,
   REQID INTEGER,
   RELPART INTEGER DEFAULT 0,
   RELTOTALPART INTEGER DEFAULT 0,
   BINARYHASH VARCHAR2(255) NOT NULL,
   RELNAME VARCHAR2(255),
   IMPORTNAME VARCHAR2(255),
   RELEASEID INTEGER,
   DATEADDED DATE
);
CREATE UNIQUE INDEX IX_BINARY_BINARYHASH ON BINARIES (BINARYHASH);
CREATE UNIQUE INDEX IX_BINARY_DATE ON BINARIES (DATE_);
CREATE UNIQUE INDEX IX_BINARY_DATEADDED ON BINARIES (DATEADDED);
CREATE UNIQUE INDEX IX_BINARY_FROMNAME ON BINARIES (FROMNAME);
CREATE UNIQUE INDEX IX_BINARY_GROUPID ON BINARIES (GROUPID);
CREATE UNIQUE INDEX IX_BINARY_GROUPID_PROCSTAT ON BINARIES (PROCSTAT, GROUPID);
CREATE UNIQUE INDEX IX_BINARY_PROCSTAT ON BINARIES (PROCSTAT);
CREATE UNIQUE INDEX IX_BINARY_RELEASEID ON BINARIES (RELEASEID);
CREATE UNIQUE INDEX IX_BINARY_RELNAME ON BINARIES (RELNAME);

--------------------------------------------------------
--  DDL for Table BINARYBLACKLIST
--------------------------------------------------------

CREATE TABLE BINARYBLACKLIST
(
   ID INTEGER PRIMARY KEY NOT NULL,
   GROUPNAME VARCHAR2(255),
   REGEX VARCHAR2(2000) NOT NULL,
   MSGCOL INTEGER DEFAULT 1 NOT NULL,
   OPTYPE INTEGER DEFAULT 1 NOT NULL,
   STATUS INTEGER DEFAULT 1 NOT NULL,
   DESCRIPTION VARCHAR2(1000)
);

--------------------------------------------------------
--  DDL for Table CATEGORY
--------------------------------------------------------

CREATE TABLE CATEGORY
(
   ID INTEGER PRIMARY KEY NOT NULL,
   TITLE VARCHAR2(255) NOT NULL,
   PARENTID INTEGER,
   STATUS INTEGER DEFAULT 1 NOT NULL,
   DESCRIPTION VARCHAR2(255)
);

--------------------------------------------------------
--  DDL for Table CONSOLEINFO
--------------------------------------------------------

CREATE TABLE CONSOLEINFO
(
   ID INTEGER PRIMARY KEY NOT NULL,
   TITLE VARCHAR2(128) NOT NULL,
   ASIN VARCHAR2(128),
   URL VARCHAR2(1000),
   SALESRANK INTEGER,
   PLATFORM VARCHAR2(255),
   PUBLISHER VARCHAR2(255),
   GENREID INTEGER,
   ESRB VARCHAR2(255),
   RELEASEDATE DATE,
   REVIEW VARCHAR2(2000),
   COVER NUMBER(1,0) DEFAULT 0 NOT NULL,
   CREATEDDATE DATE NOT NULL,
   UPDATEDDATE DATE NOT NULL
);

--------------------------------------------------------
--  DDL for Table CONTENT
--------------------------------------------------------

CREATE TABLE CONTENT
(
   ID INTEGER PRIMARY KEY NOT NULL,
   TITLE VARCHAR2(255) NOT NULL,
   URL VARCHAR2(2000),
   BODY CLOB,
   METADESCRIPTION VARCHAR2(1000),
   METAKEYWORDS VARCHAR2(1000),
   CONTENTTYPE INTEGER NOT NULL,
   SHOWINMENU INTEGER NOT NULL,
   STATUS INTEGER NOT NULL,
   ORDINAL INTEGER,
   ROLE INTEGER DEFAULT 0 NOT NULL
);

--------------------------------------------------------
--  DDL for Table FORUMPOST
--------------------------------------------------------

CREATE TABLE FORUMPOST
(
   ID INTEGER PRIMARY KEY NOT NULL,
   FORUMID INTEGER DEFAULT 1 NOT NULL,
   PARENTID INTEGER DEFAULT 0 NOT NULL,
   USERID INTEGER NOT NULL,
   SUBJECT VARCHAR2(255) NOT NULL,
   MESSAGE CLOB NOT NULL,
   LOCKED NUMBER(1,0) DEFAULT 0 NOT NULL,
   STICKY NUMBER(1,0) DEFAULT 0 NOT NULL,
   REPLIES INTEGER DEFAULT 0 NOT NULL,
   CREATEDDATE DATE NOT NULL,
   UPDATEDDATE DATE NOT NULL
);
CREATE UNIQUE INDEX CREATEDDATE ON FORUMPOST (CREATEDDATE);
CREATE UNIQUE INDEX PARENTID ON FORUMPOST (PARENTID);
CREATE UNIQUE INDEX UPDATEDDATE ON FORUMPOST (UPDATEDDATE);
CREATE UNIQUE INDEX USERID ON FORUMPOST (USERID);

--------------------------------------------------------
--  DDL for Table GENRES
--------------------------------------------------------

CREATE TABLE GENRES
(
   ID INTEGER PRIMARY KEY NOT NULL,
   TITLE VARCHAR2(255) NOT NULL,
   TYPE INTEGER
);

--------------------------------------------------------
--  DDL for Table GROUPS
--------------------------------------------------------

CREATE TABLE GROUPS
(
   ID INTEGER PRIMARY KEY NOT NULL,
   NAME_ VARCHAR2(255) NOT NULL,
   BACKFILL_TARGET INTEGER DEFAULT 1 NOT NULL,
   FIRST_RECORD INTEGER DEFAULT 0 NOT NULL,
   FIRST_RECORD_POSTDATE DATE,
   LAST_RECORD INTEGER DEFAULT 0 NOT NULL,
   LAST_RECORD_POSTDATE DATE,
   LAST_UPDATED DATE,
   MINFILESTOFORMRELEASE INTEGER,
   ACTIVE NUMBER(1,0) DEFAULT 0 NOT NULL,
   DESCRIPTION VARCHAR2(255)
);
CREATE UNIQUE INDEX IX_NAME ON GROUPS (NAME_);
CREATE UNIQUE INDEX ACTIVE ON GROUPS (ACTIVE);

--------------------------------------------------------
--  DDL for Table MENU
--------------------------------------------------------

CREATE TABLE MENU
(
   ID INTEGER PRIMARY KEY NOT NULL,
   HREF VARCHAR2(2000) NOT NULL,
   TITLE VARCHAR2(2000) NOT NULL,
   TOOLTIP VARCHAR2(2000) NOT NULL,
   ROLE INTEGER NOT NULL,
   ORDINAL INTEGER NOT NULL,
   MENUEVAL VARCHAR2(2000)
);

--------------------------------------------------------
--  DDL for Table MOVIEINFO
--------------------------------------------------------

CREATE TABLE MOVIEINFO
(
   ID INTEGER PRIMARY KEY NOT NULL,
   IMDBID INTEGER NOT NULL,
   TMDBID INTEGER,
   TITLE VARCHAR2(128) NOT NULL,
   TAGLINE VARCHAR2(255) NOT NULL,
   RATING VARCHAR2(4) NOT NULL,
   PLOT VARCHAR2(255) NOT NULL,
   YEAR_ VARCHAR2(4) NOT NULL,
   GENRE VARCHAR2(64) NOT NULL,
   DIRECTOR VARCHAR2(64) NOT NULL,
   ACTORS VARCHAR2(255) NOT NULL,
   LANGUAGE VARCHAR2(64) NOT NULL,
   COVER NUMBER(1,0) DEFAULT 0 NOT NULL,
   BACKDROP NUMBER(1,0) DEFAULT 0 NOT NULL,
   CREATEDDATE DATE NOT NULL,
   UPDATEDDATE DATE NOT NULL
);
CREATE UNIQUE INDEX IMDBID ON MOVIEINFO (IMDBID);

--------------------------------------------------------
--  DDL for Table MUSICINFO
--------------------------------------------------------

CREATE TABLE MUSICINFO
(
   ID INTEGER PRIMARY KEY NOT NULL,
   TITLE VARCHAR2(128) NOT NULL,
   ASIN VARCHAR2(128),
   URL VARCHAR2(1000),
   SALESRANK INTEGER,
   ARTIST VARCHAR2(255),
   PUBLISHER VARCHAR2(255),
   RELEASEDATE DATE,
   REVIEW VARCHAR2(2000),
   YEAR_ VARCHAR2(4) NOT NULL,
   GENREID INTEGER,
   TRACKS VARCHAR2(2000),
   COVER NUMBER(1,0) DEFAULT 0 NOT NULL,
   CREATEDDATE DATE NOT NULL,
   UPDATEDDATE DATE NOT NULL
);

--------------------------------------------------------
--  DDL for Table PARTREPAIR
--------------------------------------------------------

CREATE TABLE PARTREPAIR
(
   ID INTEGER PRIMARY KEY NOT NULL,
   NUMBERID INTEGER NOT NULL,
   GROUPID INTEGER NOT NULL,
   ATTEMPTS NUMBER(1,0) DEFAULT 0 NOT NULL
);
CREATE UNIQUE INDEX IX_PARTREPAIR_NUMBERID_GROUPID ON PARTREPAIR (NUMBERID, GROUPID);

--------------------------------------------------------
--  DDL for Table PARTS
--------------------------------------------------------

CREATE TABLE PARTS
(
   ID INTEGER PRIMARY KEY NOT NULL,
   BINARYID INTEGER DEFAULT 0 NOT NULL,
   MESSAGEID VARCHAR2(255) NOT NULL,
   NUMBER_ INTEGER DEFAULT 0 NOT NULL,
   PARTNUMBER INTEGER DEFAULT 0 NOT NULL,
   SIZE_ INTEGER DEFAULT 0 NOT NULL,
   DATEADDED DATE
);
CREATE UNIQUE INDEX IX_PARTS_BINARYID ON PARTS (BINARYID);
CREATE UNIQUE INDEX IX_PARTS_DATEADDED ON PARTS (DATEADDED);
CREATE UNIQUE INDEX IX_PARTS_NUMBER ON PARTS (NUMBER_);
CREATE INDEX IX_PARTS_MESSAGEID ON PARTS (MESSAGEID);

--------------------------------------------------------
--  DDL for Table RELEASECOMMENT
--------------------------------------------------------

CREATE TABLE RELEASECOMMENT
(
   ID INTEGER PRIMARY KEY NOT NULL,
   RELEASEID INTEGER NOT NULL,
   TEXT VARCHAR2(2000) NOT NULL,
   USERID INTEGER NOT NULL,
   CREATEDDATE DATE,
   HOST VARCHAR2(15)
);
CREATE UNIQUE INDEX IX_RELEASECOMMENT_RELEASEID ON RELEASECOMMENT (RELEASEID);
CREATE UNIQUE INDEX IX_RELEASECOMMENT_USERID ON RELEASECOMMENT (USERID);

--------------------------------------------------------
--  DDL for Table RELEASENFO
--------------------------------------------------------

CREATE TABLE RELEASENFO
(
   ID INTEGER PRIMARY KEY NOT NULL,
   RELEASEID INTEGER NOT NULL,
   BINARYID INTEGER NOT NULL,
   ATTEMPTS NUMBER(1,0) DEFAULT 0 NOT NULL,
   NFO BLOB
);
CREATE UNIQUE INDEX IX_RELEASENFO_RELEASEID ON RELEASENFO (RELEASEID);

--------------------------------------------------------
--  DDL for Table RELEASEREGEX
--------------------------------------------------------

CREATE TABLE RELEASEREGEX
(
   ID INTEGER PRIMARY KEY NOT NULL,
   GROUPNAME VARCHAR2(255),
   REGEX VARCHAR2(2000) NOT NULL,
   ORDINAL INTEGER NOT NULL,
   STATUS INTEGER DEFAULT 1 NOT NULL,
   DESCRIPTION VARCHAR2(1000),
   CATEGORYID INTEGER
);

--------------------------------------------------------
--  DDL for Table RELEASES
--------------------------------------------------------

CREATE TABLE RELEASES
(
   ID INTEGER PRIMARY KEY NOT NULL,
   NAME_ VARCHAR2(1024) NOT NULL,
   SEARCHNAME VARCHAR2(1024) NOT NULL,
   TOTALPART INTEGER DEFAULT 0,
   GROUPID INTEGER DEFAULT 0 NOT NULL,
   SIZE_ INTEGER DEFAULT 0 NOT NULL,
   POSTDATE DATE,
   ADDDATE DATE,
   GUID VARCHAR2(50) NOT NULL,
   FROMNAME VARCHAR2(255),
   COMPLETION FLOAT(63) DEFAULT 0 NOT NULL,
   CATEGORYID INTEGER DEFAULT 0,
   REGEXID INTEGER DEFAULT 0,
   RAGEID INTEGER,
   SERIESFULL VARCHAR2(15),
   SEASON VARCHAR2(10),
   EPISODE VARCHAR2(10),
   TVTITLE VARCHAR2(255),
   TVAIRDATE DATE,
   IMDBID INTEGER,
   MUSICINFOID INTEGER,
   CONSOLEINFOID INTEGER,
   REQID INTEGER,
   GRABS INTEGER DEFAULT 0 NOT NULL,
   COMMENTS INTEGER DEFAULT 0 NOT NULL,
   PASSWORDSTATUS INTEGER DEFAULT 0 NOT NULL
);
CREATE INDEX IX_RELEASES_ADDDATE ON RELEASES (ADDDATE);
CREATE INDEX IX_RELEASES_CATEGORYID ON RELEASES (CATEGORYID);
CREATE UNIQUE INDEX IX_RELEASES_GUID ON RELEASES (GUID);
CREATE INDEX IX_RELEASES_IMDBID ON RELEASES (IMDBID);
CREATE INDEX IX_RELEASES_POSTDATE ON RELEASES (POSTDATE);
CREATE INDEX IX_RELEASES_RAGEID ON RELEASES (RAGEID);
CREATE INDEX IX_RELEASES_REGEXID ON RELEASES (REGEXID);
CREATE INDEX IX_RELEASES_GROUPID ON RELEASES (GROUPID);

--------------------------------------------------------
--  DDL for Table SITE
--------------------------------------------------------

CREATE TABLE SITE
(
   ID INTEGER PRIMARY KEY NOT NULL,
   CODE VARCHAR2(255) NOT NULL,
   TITLE VARCHAR2(1000) NOT NULL,
   STRAPLINE VARCHAR2(1000) NOT NULL,
   METATITLE VARCHAR2(1000) NOT NULL,
   METADESCRIPTION VARCHAR2(1000) NOT NULL,
   METAKEYWORDS VARCHAR2(1000) NOT NULL,
   FOOTER VARCHAR2(2000) NOT NULL,
   EMAIL VARCHAR2(1000) NOT NULL,
   LASTUPDATE DATE NOT NULL,
   GOOGLE_ADSENSE_SEARCH VARCHAR2(255),
   GOOGLE_ADSENSE_SIDEPANEL VARCHAR2(255),
   GOOGLE_ANALYTICS_ACC VARCHAR2(255),
   GOOGLE_ADSENSE_ACC VARCHAR2(255),
   SITESEED VARCHAR2(50) NOT NULL,
   TANDC VARCHAR2(4000) NOT NULL,
   REGISTERSTATUS INTEGER DEFAULT 0 NOT NULL,
   STYLE VARCHAR2(50),
   MENUPOSITION INTEGER DEFAULT 1 NOT NULL,
   DEREFERRER_LINK VARCHAR2(255),
   NZBPATH VARCHAR2(500) NOT NULL,
   RAWRETENTIONDAYS INTEGER DEFAULT 3 NOT NULL,
   ATTEMPTGROUPBINDAYS INTEGER DEFAULT 2 NOT NULL,
   LOOKUPTVRAGE INTEGER DEFAULT 1 NOT NULL,
   LOOKUPIMDB INTEGER DEFAULT 1 NOT NULL,
   LOOKUPNFO INTEGER DEFAULT 1 NOT NULL,
   LOOKUPMUSIC INTEGER DEFAULT 1 NOT NULL,
   LOOKUPGAMES INTEGER DEFAULT 1 NOT NULL,
   AMAZONPUBKEY VARCHAR2(255),
   AMAZONPRIVKEY VARCHAR2(255),
   TMDBKEY VARCHAR2(255),
   COMPRESSEDHEADERS INTEGER DEFAULT 0 NOT NULL,
   MAXMSSGS INTEGER DEFAULT 20000 NOT NULL,
   NEWGROUPSCANMETHOD INTEGER DEFAULT 0 NOT NULL,
   NEWGROUPDAYSTOSCAN INTEGER DEFAULT 3 NOT NULL,
   NEWGROUPMSGSTOSCAN INTEGER DEFAULT 50000 NOT NULL,
   STOREUSERIPS INTEGER DEFAULT 0 NOT NULL,
   MINFILESTOFORMRELEASE INTEGER DEFAULT 1 NOT NULL,
   REQIDURL VARCHAR2(1000),
   LATESTREGEXURL VARCHAR2(1000),
   LATESTREGEXREVISION INTEGER DEFAULT 0 NOT NULL,
   RELEASERETENTIONDAYS INTEGER DEFAULT 0 NOT NULL,
   CHECKPASSWORDEDRAR INTEGER DEFAULT 0 NOT NULL,
   SHOWPASSWORDEDRELEASE INTEGER DEFAULT 0 NOT NULL
);

--------------------------------------------------------
--  DDL for Table TVRAGE
--------------------------------------------------------

CREATE TABLE TVRAGE
(
   ID INTEGER PRIMARY KEY NOT NULL,
   RAGEID INTEGER NOT NULL,
   TVDBID INTEGER NOT NULL,
   TRAKTID INTEGER NOT NULL,
   RELEASETITLE VARCHAR2(255) NOT NULL,
   DESCRIPTION VARCHAR2(4000),
   GENRE VARCHAR2(64),
   COUNTRY VARCHAR2(2),
   IMGDATA BLOB,
   CREATEDDATE DATE
);
CREATE UNIQUE INDEX IX_TVRAGE_RAGEID ON TVRAGE (RAGEID);
CREATE INDEX IX_TVRAGE_TRAKTID on TVRAGE (TRAKTID);

--------------------------------------------------------
--  DDL for Table USERCART
--------------------------------------------------------

CREATE TABLE USERCART
(
   ID INTEGER PRIMARY KEY NOT NULL,
   USERID INTEGER NOT NULL,
   RELEASEID INTEGER NOT NULL,
   CREATEDDATE DATE NOT NULL
);
CREATE UNIQUE INDEX IX_USERCART_USERRELEASE ON USERCART (USERID, RELEASEID);

--------------------------------------------------------
--  DDL for Table USEREXCAT
--------------------------------------------------------

CREATE TABLE USEREXCAT
(
   ID INTEGER PRIMARY KEY NOT NULL,
   USERID INTEGER NOT NULL,
   CATEGORYID INTEGER NOT NULL,
   CREATEDDATE DATE NOT NULL
);
CREATE UNIQUE INDEX IX_USEREXCAT_USERCAT ON USEREXCAT (USERID, CATEGORYID);

--------------------------------------------------------
--  DDL for Table USERINVITE
--------------------------------------------------------

CREATE TABLE USERINVITE
(
   ID INTEGER PRIMARY KEY NOT NULL,
   GUID VARCHAR2(50) NOT NULL,
   USERID INTEGER NOT NULL,
   CREATEDDATE DATE NOT NULL
);

--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

CREATE TABLE USERS
(
   ID INTEGER PRIMARY KEY NOT NULL,
   USERNAME VARCHAR2(50) NOT NULL,
   EMAIL VARCHAR2(255) NOT NULL,
   PASSWORD VARCHAR2(255) NOT NULL,
   ROLE INTEGER DEFAULT 1 NOT NULL,
   HOST VARCHAR2(15),
   GRABS INTEGER DEFAULT 0 NOT NULL,
   RSSTOKEN VARCHAR2(32) NOT NULL,
   CREATEDDATE DATE NOT NULL,
   RESETGUID VARCHAR2(50),
   LASTLOGIN DATE,
   APIACCESS DATE,
   INVITES INTEGER DEFAULT 0 NOT NULL,
   INVITEDBY INTEGER,
   MOVIEVIEW INTEGER DEFAULT 1 NOT NULL,
   MUSICVIEW INTEGER DEFAULT 1 NOT NULL,
   CONSOLEVIEW INTEGER DEFAULT 1 NOT NULL,
   USERSEED VARCHAR2(50) NOT NULL
);