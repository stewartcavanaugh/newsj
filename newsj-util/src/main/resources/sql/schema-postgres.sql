--------------------------------------------------------
--  PostgreSQL Schema
--------------------------------------------------------
DROP TABLE "BINARIES" cascade;
DROP TABLE "BINARYBLACKLIST" cascade;
DROP TABLE "CATEGORY" cascade;
DROP TABLE "CONSOLEINFO" cascade;
DROP TABLE "CONTENT" cascade ;
DROP TABLE "FORUMPOST" cascade ;
DROP TABLE "GENRES" cascade ;
DROP TABLE "GROUPS" cascade ;
DROP TABLE "MENU" cascade ;
DROP TABLE "MOVIEINFO" cascade ;
DROP TABLE "MUSICINFO" cascade ;
DROP TABLE "PARTREPAIR" cascade ;
DROP TABLE "PARTS" cascade ;
DROP TABLE "RELEASECOMMENT" cascade ;
DROP TABLE "RELEASENFO" cascade ;
DROP TABLE "RELEASEREGEX" cascade ;
DROP TABLE "RELEASES" cascade ;
DROP TABLE "SITE" cascade ;
DROP TABLE "TVRAGE" cascade ;
DROP TABLE "USERCART" cascade ;
DROP TABLE "USEREXCAT" cascade ;
DROP TABLE "USERINVITE" cascade ;
DROP TABLE "USERS" cascade ;
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
INCREMENT BY 10 START WITH 100001 CACHE 20;
--------------------------------------------------------
--  DDL for Sequence BINARY_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "BINARY_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1 CACHE 20;
--------------------------------------------------------
--  DDL for Sequence FORUMPOST_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "FORUMPOST_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 101 CACHE 20;
--------------------------------------------------------
--  DDL for Sequence GROUP_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "GROUP_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 101 CACHE 20;
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
INCREMENT BY 100 START WITH 1 CACHE 20;
--------------------------------------------------------
--  DDL for Sequence PART_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "PART_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 1000 START WITH 1 CACHE 20;
--------------------------------------------------------
--  DDL for Sequence RELEASECOMMENT_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASECOMMENT_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1 CACHE 20;
--------------------------------------------------------
--  DDL for Sequence RELEASENFO_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASENFO_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1 CACHE 20;
--------------------------------------------------------
--  DDL for Sequence RELEASEREGEX_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASEREGEX_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1001 CACHE 20;
--------------------------------------------------------
--  DDL for Sequence RELEASE_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "RELEASE_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 1 CACHE 20 ;
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

CREATE TABLE "BINARIES"
(	"ID" BIGINT NOT NULL PRIMARY KEY ,
   "NAME_" VARCHAR(1024) NOT NULL,
   "FROMNAME" VARCHAR(255) NOT NULL,
   "DATE_" TIMESTAMPTZ,
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
   "DATEADDED" TIMESTAMPTZ
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
   "COVER" BOOLEAN NOT NULL DEFAULT 0,
   "CREATEDDATE" TIMESTAMPTZ NOT NULL,
   "UPDATEDDATE" TIMESTAMPTZ NOT NULL
) ;
--------------------------------------------------------
--  DDL for Table CONTENT
--------------------------------------------------------

CREATE TABLE "CONTENT"
(	"ID" INT NOT NULL PRIMARY KEY,
   "TITLE" VARCHAR(255) NOT NULL,
   "URL" VARCHAR(2000),
   "BODY" TEXT,
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
   "MESSAGE" TEXT NOT NULL,
   "LOCKED" BOOLEAN NOT NULL DEFAULT 0,
   "STICKY" BOOLEAN NOT NULL DEFAULT 0,
   "REPLIES" INT NOT NULL DEFAULT 0,
   "CREATEDDATE" TIMESTAMPTZ NOT NULL,
   "UPDATEDDATE" TIMESTAMPTZ NOT NULL
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
   "FIRST_RECORD_POSTDATE" TIMESTAMPTZ,
   "LAST_RECORD" BIGINT NOT NULL DEFAULT 0,
   "LAST_RECORD_POSTDATE" TIMESTAMPTZ,
   "LAST_UPDATED" TIMESTAMPTZ,
   "MINFILESTOFORMRELEASE" INT,
   "ACTIVE" BOOLEAN NOT NULL DEFAULT 0,
   "DESCRIPTION" VARCHAR(255)
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
   "COVER" BOOLEAN NOT NULL DEFAULT 0,
   "BACKDROP" BOOLEAN NOT NULL DEFAULT 0,
   "CREATEDDATE" TIMESTAMPTZ NOT NULL,
   "UPDATEDDATE" TIMESTAMPTZ NOT NULL
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
   "COVER" BOOLEAN NOT NULL DEFAULT 0,
   "CREATEDDATE" TIMESTAMPTZ NOT NULL,
   "UPDATEDDATE" TIMESTAMPTZ NOT NULL
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
   "DATEADDED" TIMESTAMPTZ
);
--------------------------------------------------------
--  DDL for Table RELEASECOMMENT
--------------------------------------------------------

CREATE TABLE "RELEASECOMMENT"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "RELEASEID" INT NOT NULL,
   "TEXT" VARCHAR(2000) NOT NULL,
   "USERID" INT NOT NULL,
   "CREATEDDATE" TIMESTAMPTZ,
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
   "NFO" BYTEA
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
   "POSTDATE" TIMESTAMPTZ,
   "ADDDATE" TIMESTAMPTZ,
   "GUID" VARCHAR(50) NOT NULL,
   "FROMNAME" VARCHAR(255),
   "COMPLETION" FLOAT(63) NOT NULL DEFAULT 0,
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
   "LASTUPDATE" TIMESTAMPTZ NOT NULL,
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
   "IMGDATA" BYTEA,
   "CREATEDDATE" TIMESTAMPTZ
);
--------------------------------------------------------
--  DDL for Table USERCART
--------------------------------------------------------

CREATE TABLE "USERCART"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "USERID" INT NOT NULL,
   "RELEASEID" INT NOT NULL,
   "CREATEDDATE" TIMESTAMPTZ NOT NULL
);
--------------------------------------------------------
--  DDL for Table USEREXCAT
--------------------------------------------------------

CREATE TABLE "USEREXCAT"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "USERID" INT NOT NULL,
   "CATEGORYID" INT NOT NULL,
   "CREATEDDATE" TIMESTAMPTZ NOT NULL
);
--------------------------------------------------------
--  DDL for Table USERINVITE
--------------------------------------------------------

CREATE TABLE "USERINVITE"
(	"ID" INT NOT NULL PRIMARY KEY ,
   "GUID" VARCHAR(50) NOT NULL,
   "USERID" INT NOT NULL,
   "CREATEDDATE" TIMESTAMPTZ NOT NULL
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
   "CREATEDDATE" TIMESTAMPTZ NOT NULL,
   "RESETGUID" VARCHAR(50),
   "LASTLOGIN" TIMESTAMPTZ,
   "APIACCESS" TIMESTAMPTZ,
   "INVITES" INT NOT NULL DEFAULT 0,
   "INVITEDBY" INT,
   "MOVIEVIEW" INT NOT NULL DEFAULT 1,
   "MUSICVIEW" INT NOT NULL DEFAULT 1,
   "CONSOLEVIEW" INT NOT NULL DEFAULT 1,
   "USERSEED" VARCHAR(50) NOT NULL
);

--------------------------------------------------------
--  DDL for Index ACTIVE
--------------------------------------------------------

CREATE INDEX "ACTIVE" ON "GROUPS" ("ACTIVE");
--------------------------------------------------------
--  DDL for Index IX_USERCART_USERRELEASE
--------------------------------------------------------

CREATE UNIQUE INDEX "IX_USERCART_USERRELEASE" ON "USERCART" ("USERID", "RELEASEID") ;
--------------------------------------------------------
--  DDL for Index IX_BINARY_FROMNAME
--------------------------------------------------------

CREATE INDEX "IX_BINARY_FROMNAME" ON "BINARIES" ("FROMNAME");
--------------------------------------------------------
--  DDL for Index IX_RELEASECOMMENT_RELEASEID
--------------------------------------------------------

CREATE INDEX "IX_RELEASECOMMENT_RELEASEID" ON "RELEASECOMMENT" ("RELEASEID");
--------------------------------------------------------
--  DDL for Index IX_TVRAGE_RAGEID
--------------------------------------------------------

CREATE INDEX "IX_TVRAGE_RAGEID" ON "TVRAGE" ("RAGEID");
--------------------------------------------------------
--  DDL for Index IX_USEREXCAT_USERCAT
--------------------------------------------------------

CREATE UNIQUE INDEX "IX_USEREXCAT_USERCAT" ON "USEREXCAT" ("USERID", "CATEGORYID");
--------------------------------------------------------
--  DDL for Index IX_BINARY_PROCSTAT
--------------------------------------------------------

CREATE INDEX "IX_BINARY_PROCSTAT" ON "BINARIES" ("PROCSTAT");
--------------------------------------------------------
--  DDL for Index IX_RELEASES_GUID
--------------------------------------------------------

CREATE INDEX "IX_RELEASES_GUID" ON "RELEASES" ("GUID");
--------------------------------------------------------
--  DDL for Index IX_PARTS_DATEADDED
--------------------------------------------------------

CREATE INDEX "IX_PARTS_DATEADDED" ON "PARTS" ("DATEADDED");
--------------------------------------------------------
--  DDL for Index IX_RELEASES_POSTDATE
--------------------------------------------------------

CREATE INDEX "IX_RELEASES_POSTDATE" ON "RELEASES" ("POSTDATE");
--------------------------------------------------------
--  DDL for Index IX_BINARY_BINARYHASH
--------------------------------------------------------

CREATE UNIQUE INDEX "IX_BINARY_BINARYHASH" ON "BINARIES" ("BINARYHASH");
--------------------------------------------------------
--  DDL for Index IX_BINARY_GROUPID
--------------------------------------------------------

CREATE INDEX "IX_BINARY_GROUPID" ON "BINARIES" ("GROUPID");
--------------------------------------------------------
--  DDL for Index IX_RELEASECOMMENT_USERID
--------------------------------------------------------

CREATE INDEX "IX_RELEASECOMMENT_USERID" ON "RELEASECOMMENT" ("USERID");
--------------------------------------------------------
--  DDL for Index USERID
--------------------------------------------------------

CREATE INDEX "USERID" ON "FORUMPOST" ("USERID");
--------------------------------------------------------
--  DDL for Index IX_NAME
--------------------------------------------------------

CREATE UNIQUE INDEX "IX_NAME" ON "GROUPS" ("NAME_");
--------------------------------------------------------
--  DDL for Index IX_BINARY_RELNAME
--------------------------------------------------------

CREATE INDEX "IX_BINARY_RELNAME" ON "BINARIES" ("RELNAME");
--------------------------------------------------------
--  DDL for Index IX_RELEASES_RAGEID
--------------------------------------------------------

CREATE INDEX "IX_RELEASES_RAGEID" ON "RELEASES" ("RAGEID");
--------------------------------------------------------
--  DDL for Index IX_BINARY_RELEASEID
--------------------------------------------------------

CREATE INDEX "IX_BINARY_RELEASEID" ON "BINARIES" ("RELEASEID");
--------------------------------------------------------
--  DDL for Index IX_RELEASES_IMDBID
--------------------------------------------------------

CREATE INDEX "IX_RELEASES_IMDBID" ON "RELEASES" ("IMDBID");
--------------------------------------------------------
--  DDL for Index CREATEDDATE
--------------------------------------------------------

CREATE INDEX "CREATEDDATE" ON "FORUMPOST" ("CREATEDDATE");
--------------------------------------------------------
--  DDL for Index IX_PARTREPAIR_NUMBERID_GROUPID
--------------------------------------------------------

CREATE UNIQUE INDEX "IX_PARTREPAIR_NUMBERID_GROUPID" ON "PARTREPAIR" ("NUMBERID", "GROUPID");
--------------------------------------------------------
--  DDL for Index IX_RELEASES_CATEGORYID
--------------------------------------------------------

CREATE INDEX "IX_RELEASES_CATEGORYID" ON "RELEASES" ("CATEGORYID");
--------------------------------------------------------
--  DDL for Index IX_BINARY_GROUPID_PROCSTAT
--------------------------------------------------------

CREATE INDEX "IX_BINARY_GROUPID_PROCSTAT" ON "BINARIES" ("PROCSTAT", "GROUPID");
--------------------------------------------------------
--  DDL for Index UPDATEDDATE
--------------------------------------------------------

CREATE INDEX "UPDATEDDATE" ON "FORUMPOST" ("UPDATEDDATE");
--------------------------------------------------------
--  DDL for Index IX_BINARY_DATEADDED
--------------------------------------------------------

CREATE INDEX "IX_BINARY_DATEADDED" ON "BINARIES" ("DATEADDED");
--------------------------------------------------------
--  DDL for Index PARENTID
--------------------------------------------------------

CREATE INDEX "PARENTID" ON "FORUMPOST" ("PARENTID");
--------------------------------------------------------
--  DDL for Index IX_PARTS_BINARYID
--------------------------------------------------------

CREATE INDEX "IX_PARTS_BINARYID" ON "PARTS" ("BINARYID");
--------------------------------------------------------
--  DDL for Index IX_RELEASENFO_RELEASEID
--------------------------------------------------------

CREATE UNIQUE INDEX "IX_RELEASENFO_RELEASEID" ON "RELEASENFO" ("RELEASEID");
--------------------------------------------------------
--  DDL for Index IMDBID
--------------------------------------------------------

CREATE UNIQUE INDEX "IMDBID" ON "MOVIEINFO" ("IMDBID");
--------------------------------------------------------
--  DDL for Index IX_BINARY_DATE
--------------------------------------------------------

CREATE INDEX "IX_BINARY_DATE" ON "BINARIES" ("DATE_");
--------------------------------------------------------
--  DDL for Index IX_PARTS_NUMBER
--------------------------------------------------------

CREATE INDEX "IX_PARTS_NUMBER" ON "PARTS" ("NUMBER_");
--------------------------------------------------------
--  DDL for Index IX_RELEASES_ADDDATE
--------------------------------------------------------

CREATE INDEX "IX_RELEASES_ADDDATE" ON "RELEASES" ("ADDDATE");
--------------------------------------------------------
--  DDL for Index IX_RELEASES_REGEXID
--------------------------------------------------------

CREATE INDEX "IX_RELEASES_REGEXID" ON "RELEASES" ("REGEXID");
--------------------------------------------------------
--  DDL for Index IX_RELEASES_GROUPID
--------------------------------------------------------

CREATE INDEX "IX_RELEASES_GROUPID" ON "RELEASES" ("GROUPID");
--------------------------------------------------------
--  Constraints for Table FORUMPOST
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table TVRAGE
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table SITE
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table USERINVITE
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table MUSICINFO
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table CATEGORY
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table CONTENT
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table PARTS
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table PARTREPAIR
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table MOVIEINFO
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table USERCART
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table RELEASEREGEX
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table BINARYBLACKLIST
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table RELEASENFO
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table GENRES
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table USEREXCAT
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table CONSOLEINFO
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table MENU
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table RELEASES
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table GROUPS
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table BINARIES
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

--------------------------------------------------------
--  Constraints for Table RELEASECOMMENT
--------------------------------------------------------