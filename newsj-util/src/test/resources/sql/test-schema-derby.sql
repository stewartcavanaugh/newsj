-- Derby Test Schema
-- This is purely to be dropped by the real schema - TODO: remove later
CREATE SCHEMA SA;
SET SCHEMA SA;
--------------------------------------------------------
--  DDL for Sequence BINARYBLACKLISTENTRY_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "BINARYBLACKLISTENTRY_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 10 START WITH 100001;
--------------------------------------------------------
--  DDL for Sequence BINARY_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "BINARY_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100 START WITH 1 ;
--------------------------------------------------------
--  DDL for Sequence FORUMPOST_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "FORUMPOST_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100 START WITH 101  ;
--------------------------------------------------------
--  DDL for Sequence GROUP_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "GROUP_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100 START WITH 101  ;
--------------------------------------------------------
--  DDL for Sequence NEWSJ_SEQ
--------------------------------------------------------

CREATE SEQUENCE "NEWSJ_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100
  START WITH 10301;
--------------------------------------------------------
--  DDL for Sequence PARTREPAIR_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "PARTREPAIR_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100 START WITH 1  ;
--------------------------------------------------------
--  DDL for Table GENERATOR_IDS
--------------------------------------------------------

CREATE TABLE "GENERATOR_IDS"
("NAME_" VARCHAR(255) NOT NULL PRIMARY KEY ,
 "VALUE" BIGINT NOT NULL DEFAULT 0);
--------------------------------------------------------
--  DDL for Sequence RELEASECOMMENT_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASECOMMENT_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100 START WITH 1;
--------------------------------------------------------
--  DDL for Sequence RELEASENFO_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASENFO_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100 START WITH 1;
--------------------------------------------------------
--  DDL for Sequence RELEASEREGEX_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASEREGEX_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100 START WITH 1001 ;
--------------------------------------------------------
--  DDL for Sequence RELEASE_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASE_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100 START WITH 1;
--------------------------------------------------------
--  DDL for Sequence USER_SEQ
--------------------------------------------------------

CREATE SEQUENCE "USER_SEQ" AS BIGINT
  MINVALUE 1
  INCREMENT BY 100
  START WITH 1;
--------------------------------------------------------
--  DDL for Table BINARIES
--------------------------------------------------------

CREATE TABLE "BINARIES"
(	"ID" BIGINT NOT NULL PRIMARY KEY ,
   "NAME_" VARCHAR(1024) NOT NULL,
   "FROMNAME" VARCHAR(255) NOT NULL,
   "DATE_" TIMESTAMP,
   "XREF" VARCHAR(255),
   "TOTALPARTS" INT NOT NULL DEFAULT 0,
   "GROUPID" INT NOT NULL DEFAULT 0,
   "PROCSTAT" INT DEFAULT 0,
   "PROCATTEMPTS" INT DEFAULT 0,
   "CATEGORYID" INT,
   "REGEXID" INT,
   "REQID" INT,
   "RELPART" INT DEFAULT 0,
   "RELTOTALPART" INT DEFAULT 0,
   "BINARYHASH" VARCHAR(255) NOT NULL,
   "RELNAME" VARCHAR(255),
   "IMPORTNAME" VARCHAR(255),
   "RELEASEID" INT,
   "DATEADDED" TIMESTAMP
);
--------------------------------------------------------
--  DDL for Table BINARYBLACKLIST
--------------------------------------------------------

CREATE TABLE "BINARYBLACKLIST"
(	"ID" BIGINT NOT NULL PRIMARY KEY ,
   "GROUPNAME" VARCHAR(255),
   "REGEX" VARCHAR(2000) NOT NULL,
   "MSGCOL" INT NOT NULL DEFAULT 1,
   "OPTYPE" INT NOT NULL DEFAULT 1,
   "STATUS" INT NOT NULL DEFAULT 1,
   "DESCRIPTION" VARCHAR(1000)
);
--------------------------------------------------------
--  DDL for Table CATEGORY
--------------------------------------------------------

CREATE TABLE "CATEGORY"
(	"ID" INT NOT NULL PRIMARY KEY,
   "TITLE" VARCHAR(255) NOT NULL,
   "PARENTID" INT,
   "STATUS" INT NOT NULL DEFAULT 1,
   "DESCRIPTION" VARCHAR(255)
);
--------------------------------------------------------
--  DDL for Table CONSOLEINFO
--------------------------------------------------------

CREATE TABLE "CONSOLEINFO"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "TITLE" VARCHAR(128) NOT NULL,
   "ASIN" VARCHAR(128),
   "URL" VARCHAR(1000),
   "SALESRANK" INT,
   "PLATFORM" VARCHAR(255),
   "PUBLISHER" VARCHAR(255),
   "GENREID" INT,
   "ESRB" VARCHAR(255),
   "RELEASEDATE" DATE,
   "REVIEW" VARCHAR(2000),
   "COVER" BOOLEAN NOT NULL DEFAULT FALSE,
   "CREATEDDATE" TIMESTAMP NOT NULL,
   "UPDATEDDATE" TIMESTAMP NOT NULL
) ;
--------------------------------------------------------
--  DDL for Table CONTENT
--------------------------------------------------------

CREATE TABLE "CONTENT"
(	"ID" INT NOT NULL PRIMARY KEY,
   "TITLE" VARCHAR(255) NOT NULL,
   "URL" VARCHAR(2000),
   "BODY" CLOB,
   "METADESCRIPTION" VARCHAR(1000),
   "METAKEYWORDS" VARCHAR(1000),
   "CONTENTTYPE" INT NOT NULL,
   "SHOWINMENU" INT NOT NULL,
   "STATUS" INT NOT NULL,
   "ORDINAL" INT,
   "ROLE" INT NOT NULL DEFAULT 0
) ;
--------------------------------------------------------
--  DDL for Table FORUMPOST
--------------------------------------------------------

CREATE TABLE "FORUMPOST"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "FORUMID" INT NOT NULL DEFAULT 1,
   "PARENTID" INT NOT NULL DEFAULT 0,
   "USERID" INT NOT NULL,
   "SUBJECT" VARCHAR(255) NOT NULL,
   "MESSAGE" CLOB NOT NULL,
   "LOCKED" BOOLEAN NOT NULL DEFAULT FALSE,
   "STICKY" BOOLEAN NOT NULL DEFAULT FALSE,
   "REPLIES" INT NOT NULL DEFAULT 0,
   "CREATEDDATE" TIMESTAMP NOT NULL,
   "UPDATEDDATE" TIMESTAMP NOT NULL
) ;
--------------------------------------------------------
--  DDL for Table GENRES
--------------------------------------------------------

CREATE TABLE "GENRES"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "TITLE" VARCHAR(255) NOT NULL,
   "TYPE" INT
);
--------------------------------------------------------
--  DDL for Table GROUPS
--------------------------------------------------------

CREATE TABLE "GROUPS"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "NAME_" VARCHAR(255) NOT NULL,
   "BACKFILL_TARGET" INT NOT NULL DEFAULT 1,
   "FIRST_RECORD" BIGINT NOT NULL DEFAULT 0,
   "FIRST_RECORD_POSTDATE" TIMESTAMP,
   "LAST_RECORD" BIGINT NOT NULL DEFAULT 0,
   "LAST_RECORD_POSTDATE" TIMESTAMP,
   "LAST_UPDATED" TIMESTAMP,
   "MINFILESTOFORMRELEASE" INT,
   "ACTIVE" BOOLEAN NOT NULL DEFAULT FALSE,
   "DESCRIPTION" VARCHAR(255)
);
--------------------------------------------------------
--  DDL for Table JOBCONFIG
--------------------------------------------------------

CREATE TABLE "JOBCONFIG"
(
  "ID" INT PRIMARY KEY NOT NULL ,
  "JOB_NAME" VARCHAR(255) NOT NULL ,
  "JOB_FREQ" VARCHAR(255) NOT NULL ,
  "JOB_CONFIG" VARCHAR(255) NOT NULL
);
--------------------------------------------------------
--  DDL for Table JOBLOG
--------------------------------------------------------
CREATE TABLE "JOBLOG" (
  "ID" INT PRIMARY KEY NOT NULL ,
  "JOB_NAME" VARCHAR(255) NOT NULL ,
  "START_DATE" TIMESTAMP DEFAULT NULL,
  "END_DATE" TIMESTAMP DEFAULT NULL,
  "RESULT" VARCHAR(255) NOT NULL,
  "NOTES" VARCHAR(2000) DEFAULT NULL
);
--------------------------------------------------------
--  DDL for Table MENU
--------------------------------------------------------

CREATE TABLE "MENU"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "HREF" VARCHAR(2000) NOT NULL,
   "TITLE" VARCHAR(2000) NOT NULL,
   "TOOLTIP" VARCHAR(2000) NOT NULL,
   "ROLE" INT NOT NULL,
   "ORDINAL" INT NOT NULL,
   "MENUEVAL" VARCHAR(2000)
) ;
--------------------------------------------------------
--  DDL for Table MOVIEINFO
--------------------------------------------------------

CREATE TABLE "MOVIEINFO"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "IMDBID" INT NOT NULL,
   "TMDBID" INT,
   "TITLE" VARCHAR(128) NOT NULL,
   "TAGLINE" VARCHAR(255) NOT NULL,
   "RATING" VARCHAR(4) NOT NULL,
   "PLOT" VARCHAR(255) NOT NULL,
   "YEAR" VARCHAR(4) NOT NULL,
   "GENRE" VARCHAR(64) NOT NULL,
   "DIRECTOR" VARCHAR(64) NOT NULL,
   "ACTORS" VARCHAR(255) NOT NULL,
   "LANGUAGE" VARCHAR(64) NOT NULL,
   "COVER" BOOLEAN NOT NULL DEFAULT FALSE,
   "BACKDROP" BOOLEAN NOT NULL DEFAULT FALSE,
   "CREATEDDATE" TIMESTAMP NOT NULL,
   "UPDATEDDATE" TIMESTAMP NOT NULL
);
--------------------------------------------------------
--  DDL for Table MUSICINFO
--------------------------------------------------------

CREATE TABLE "MUSICINFO"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "TITLE" VARCHAR(128) NOT NULL,
   "ASIN" VARCHAR(128),
   "URL" VARCHAR(1000),
   "SALESRANK" INT,
   "ARTIST" VARCHAR(255),
   "PUBLISHER" VARCHAR(255),
   "RELEASEDATE" DATE,
   "REVIEW" VARCHAR(2000),
   "YEAR" VARCHAR(4) NOT NULL,
   "GENREID" INT,
   "TRACKS" VARCHAR(2000),
   "COVER" BOOLEAN NOT NULL DEFAULT FALSE,
   "CREATEDDATE" TIMESTAMP NOT NULL,
   "UPDATEDDATE" TIMESTAMP NOT NULL
);
--------------------------------------------------------
--  DDL for Table PARTREPAIR
--------------------------------------------------------

CREATE TABLE "PARTREPAIR"
(	"ID" INT NOT NULL PRIMARY KEY,
   "NUMBERID" BIGINT NOT NULL,
   "GROUPID" INT NOT NULL,
   "ATTEMPTS" INT NOT NULL DEFAULT 0
);
--------------------------------------------------------
--  DDL for Table PARTS
--------------------------------------------------------

CREATE TABLE "PARTS"
(	"ID" BIGINT NOT NULL PRIMARY KEY,
   "BINARYID" INT NOT NULL DEFAULT 0,
   "MESSAGEID" VARCHAR(255) NOT NULL,
   "NUMBER_" BIGINT NOT NULL DEFAULT 0,
   "PARTNUMBER" INT NOT NULL DEFAULT 0,
   "SIZE_" BIGINT NOT NULL DEFAULT 0,
   "DATEADDED" TIMESTAMP
);
--------------------------------------------------------
--  DDL for Table RELEASECOMMENT
--------------------------------------------------------

CREATE TABLE "RELEASECOMMENT"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "RELEASEID" INT NOT NULL,
   "TEXT" VARCHAR(2000) NOT NULL,
   "USERID" INT NOT NULL,
   "CREATEDDATE" TIMESTAMP,
   "HOST" VARCHAR(15)
);
--------------------------------------------------------
--  DDL for Table RELEASENFO
--------------------------------------------------------

CREATE TABLE "RELEASENFO"
(	"ID" BIGINT  NOT NULL PRIMARY KEY ,
   "RELEASEID" INT NOT NULL,
   "BINARYID" INT NOT NULL,
   "ATTEMPTS" INT NOT NULL DEFAULT 0,
   "NFO" BLOB
);
--------------------------------------------------------
--  DDL for Table RELEASEREGEX
--------------------------------------------------------

CREATE TABLE "RELEASEREGEX"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "GROUPNAME" VARCHAR(255),
   "REGEX" VARCHAR(2000) NOT NULL,
   "ORDINAL" INT NOT NULL,
   "STATUS" INT NOT NULL DEFAULT 1,
   "DESCRIPTION" VARCHAR(1000),
   "CATEGORYID" INT
);
--------------------------------------------------------
--  DDL for Table RELEASES
--------------------------------------------------------

CREATE TABLE "RELEASES"
(	"ID" BIGINT NOT NULL PRIMARY KEY ,
   "NAME_" VARCHAR(1024) NOT NULL,
   "SEARCHNAME" VARCHAR(1024) NOT NULL,
   "TOTALPART" INT DEFAULT 0,
   "GROUPID" INT NOT NULL DEFAULT 0,
   "SIZE_" BIGINT NOT NULL DEFAULT 0,
   "POSTDATE" TIMESTAMP,
   "ADDDATE" TIMESTAMP,
   "GUID" VARCHAR(50) NOT NULL,
   "FROMNAME" VARCHAR(255),
   "COMPLETION" FLOAT NOT NULL DEFAULT 0.0,
   "CATEGORYID" INT DEFAULT 0,
   "REGEXID" INT DEFAULT 0,
   "RAGEID" INT,
   "SERIESFULL" VARCHAR(15),
   "SEASON" VARCHAR(10),
   "EPISODE" VARCHAR(10),
   "TVTITLE" VARCHAR(255),
   "TVAIRDATE" DATE,
   "IMDBID" INT,
   "MUSICINFOID" INT,
   "CONSOLEINFOID" INT,
   "REQID" INT,
   "GRABS" INT NOT NULL DEFAULT 0,
   "COMMENTS" INT NOT NULL DEFAULT 0,
   "PASSWORDSTATUS" INT NOT NULL DEFAULT 0
);
--------------------------------------------------------
--  DDL for Table SITE
--------------------------------------------------------

CREATE TABLE "SITE"
(	"ID" INT NOT NULL PRIMARY KEY,
   "CODE" VARCHAR(255) NOT NULL,
   "TITLE" VARCHAR(1000) NOT NULL,
   "STRAPLINE" VARCHAR(1000) NOT NULL,
   "METATITLE" VARCHAR(1000) NOT NULL,
   "METADESCRIPTION" VARCHAR(1000) NOT NULL,
   "METAKEYWORDS" VARCHAR(1000) NOT NULL,
   "FOOTER" VARCHAR(2000) NOT NULL,
   "EMAIL" VARCHAR(1000) NOT NULL,
   "LASTUPDATE" TIMESTAMP NOT NULL,
   "GOOGLE_ADSENSE_SEARCH" VARCHAR(255),
   "GOOGLE_ADSENSE_SIDEPANEL" VARCHAR(255),
   "GOOGLE_ANALYTICS_ACC" VARCHAR(255),
   "GOOGLE_ADSENSE_ACC" VARCHAR(255),
   "SITESEED" VARCHAR(50) NOT NULL,
   "TANDC" VARCHAR(4000) NOT NULL,
   "REGISTERSTATUS" INT NOT NULL DEFAULT 0,
   "STYLE" VARCHAR(50),
   "MENUPOSITION" INT NOT NULL DEFAULT 1,
   "DEREFERRER_LINK" VARCHAR(255),
   "NZBPATH" VARCHAR(500) NOT NULL,
   "RAWRETENTIONDAYS" INT NOT NULL DEFAULT 3,
   "ATTEMPTGROUPBINDAYS" INT NOT NULL DEFAULT 2,
   "LOOKUPTVRAGE" INT NOT NULL DEFAULT 1,
   "LOOKUPIMDB" INT NOT NULL DEFAULT 1,
   "LOOKUPNFO" INT NOT NULL DEFAULT 1,
   "LOOKUPMUSIC" INT NOT NULL DEFAULT 1,
   "LOOKUPGAMES" INT NOT NULL DEFAULT 1,
   "AMAZONPUBKEY" VARCHAR(255),
   "AMAZONPRIVKEY" VARCHAR(255),
   "TMDBKEY" VARCHAR(255),
   "COMPRESSEDHEADERS" INT NOT NULL DEFAULT 0,
   "MAXMSSGS" INT NOT NULL DEFAULT 20000,
   "NEWGROUPSCANMETHOD" INT NOT NULL DEFAULT 0,
   "NEWGROUPDAYSTOSCAN" INT NOT NULL DEFAULT 3,
   "NEWGROUPMSGSTOSCAN" INT NOT NULL DEFAULT 50000,
   "STOREUSERIPS" INT NOT NULL DEFAULT 0,
   "MINFILESTOFORMRELEASE" INT NOT NULL DEFAULT 1,
   "REQIDURL" VARCHAR(1000),
   "LATESTREGEXURL" VARCHAR(1000),
   "LATESTREGEXREVISION" INT DEFAULT 0,
   "RELEASERETENTIONDAYS" INT DEFAULT 0,
   "CHECKPASSWORDEDRAR" INT NOT NULL DEFAULT 0,
   "SHOWPASSWORDEDRELEASE" INT NOT NULL DEFAULT 0
);
--------------------------------------------------------
--  DDL for Table TVRAGE
--------------------------------------------------------

CREATE TABLE "TVRAGE"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "RAGEID" INT NOT NULL,
   "TVDBID" INT NOT NULL,
   "RELEASETITLE" VARCHAR(255) NOT NULL ,
   "DESCRIPTION" VARCHAR(4000),
   "GENRE" VARCHAR(64),
   "COUNTRY" VARCHAR(2),
   "IMGDATA" BLOB,
   "CREATEDDATE" TIMESTAMP
);
--------------------------------------------------------
--  DDL for Table USERCART
--------------------------------------------------------

CREATE TABLE "USERCART"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "USERID" INT NOT NULL,
   "RELEASEID" INT NOT NULL,
   "CREATEDDATE" TIMESTAMP NOT NULL
);
--------------------------------------------------------
--  DDL for Table USEREXCAT
--------------------------------------------------------

CREATE TABLE "USEREXCAT"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "USERID" INT NOT NULL,
   "CATEGORYID" INT NOT NULL,
   "CREATEDDATE" TIMESTAMP NOT NULL
);
--------------------------------------------------------
--  DDL for Table USERINVITE
--------------------------------------------------------

CREATE TABLE "USERINVITE"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "GUID" VARCHAR(50) NOT NULL,
   "USERID" INT NOT NULL,
   "CREATEDDATE" TIMESTAMP NOT NULL
);
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

CREATE TABLE "USERS"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "USERNAME" VARCHAR(50) NOT NULL,
   "EMAIL" VARCHAR(255) NOT NULL,
   "PASSWORD" VARCHAR(255) NOT NULL,
   "ROLE" INT NOT NULL DEFAULT 1,
   "HOST" VARCHAR(15),
   "GRABS" INT NOT NULL DEFAULT 0,
   "RSSTOKEN" VARCHAR(32) NOT NULL,
   "CREATEDDATE" TIMESTAMP NOT NULL,
   "RESETGUID" VARCHAR(50),
   "LASTLOGIN" TIMESTAMP,
   "APIACCESS" TIMESTAMP,
   "INVITES" INT NOT NULL DEFAULT 0,
   "INVITEDBY" INT,
   "MOVIEVIEW" INT NOT NULL DEFAULT 1,
   "MUSICVIEW" INT NOT NULL DEFAULT 1,
   "CONSOLEVIEW" INT NOT NULL DEFAULT 1,
   "USERSEED" VARCHAR(50) NOT NULL
);