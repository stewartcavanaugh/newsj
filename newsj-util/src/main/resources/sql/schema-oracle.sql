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
DROP SEQUENCE "CATEGORY_SEQ";
DROP SEQUENCE "GROUP_SEQ";
DROP SEQUENCE "PARTREPAIR_SEQ";
DROP SEQUENCE "PART_SEQ";
DROP SEQUENCE "RELEASENFO_SEQ";
DROP SEQUENCE "RELEASEREGEX_SEQ";
DROP SEQUENCE "RELEASE_SEQ";
DROP SEQUENCE "SITE_SEQ";
DROP SEQUENCE "TVRAGE_SEQ";
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
--  DDL for Sequence CATEGORY_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "CATEGORY_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 10 START WITH 101 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence GROUP_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "GROUP_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 100 START WITH 101 CACHE 20 NOORDER  NOCYCLE ;
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
--  DDL for Sequence SITE_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "SITE_SEQ"
MINVALUE 1 MAXVALUE 9999999999999999999999999999
INCREMENT BY 1 START WITH 2 CACHE 20 NOORDER  NOCYCLE ;

CREATE SEQUENCE "TVRAGE_SEQ"
MINVALUE 1
INCREMENT BY 10
START WITH 10101 CACHE 20;
--------------------------------------------------------
--  DDL for Table BINARIES
--------------------------------------------------------

CREATE TABLE "BINARIES"
(	"ID" NUMBER(38,0),
   "NAME_" VARCHAR2(1024 BYTE),
   "FROMNAME" VARCHAR2(255 BYTE),
   "DATE_" DATE,
   "XREF" VARCHAR2(255 BYTE),
   "TOTALPARTS" NUMBER(*,0) DEFAULT 0,
   "GROUPID" NUMBER(*,0) DEFAULT 0,
   "PROCSTAT" NUMBER(*,0) DEFAULT 0,
   "PROCATTEMPTS" NUMBER(*,0) DEFAULT 0,
   "CATEGORYID" NUMBER(*,0),
   "REGEXID" NUMBER(*,0),
   "REQID" NUMBER(*,0),
   "RELPART" NUMBER(*,0) DEFAULT 0,
   "RELTOTALPART" NUMBER(*,0) DEFAULT 0,
   "BINARYHASH" VARCHAR2(255 BYTE),
   "RELNAME" VARCHAR2(255 BYTE),
   "IMPORTNAME" VARCHAR2(255 BYTE),
   "RELEASEID" NUMBER(*,0),
   "DATEADDED" DATE
);
--------------------------------------------------------
--  DDL for Table BINARYBLACKLIST
--------------------------------------------------------

CREATE TABLE "BINARYBLACKLIST"
(	"ID" NUMBER(38,0),
   "GROUPNAME" VARCHAR2(255 BYTE),
   "REGEX" VARCHAR2(2000 BYTE),
   "MSGCOL" NUMBER(*,0) DEFAULT 1,
   "OPTYPE" NUMBER(*,0) DEFAULT 1,
   "STATUS" NUMBER(*,0) DEFAULT 1,
   "DESCRIPTION" VARCHAR2(1000 BYTE)
);
--------------------------------------------------------
--  DDL for Table CATEGORY
--------------------------------------------------------

CREATE TABLE "CATEGORY"
(	"ID" NUMBER(*,0),
   "TITLE" VARCHAR2(255 BYTE),
   "PARENTID" NUMBER(*,0),
   "STATUS" NUMBER(*,0) DEFAULT 1,
   "DESCRIPTION" VARCHAR2(255 BYTE)
);
--------------------------------------------------------
--  DDL for Table CONSOLEINFO
--------------------------------------------------------

CREATE TABLE "CONSOLEINFO"
(	"ID" NUMBER(*,0),
   "TITLE" VARCHAR2(128 BYTE),
   "ASIN" VARCHAR2(128 BYTE),
   "URL" VARCHAR2(1000 BYTE),
   "SALESRANK" NUMBER(*,0),
   "PLATFORM" VARCHAR2(255 BYTE),
   "PUBLISHER" VARCHAR2(255 BYTE),
   "GENREID" NUMBER(*,0),
   "ESRB" VARCHAR2(255 BYTE),
   "RELEASEDATE" DATE,
   "REVIEW" VARCHAR2(2000 BYTE),
   "COVER" NUMBER(1,0) DEFAULT 0,
   "CREATEDDATE" DATE,
   "UPDATEDDATE" DATE
) ;
--------------------------------------------------------
--  DDL for Table CONTENT
--------------------------------------------------------

CREATE TABLE "CONTENT"
(	"ID" NUMBER(*,0),
   "TITLE" VARCHAR2(255 BYTE),
   "URL" VARCHAR2(2000 BYTE),
   "BODY" CLOB,
   "METADESCRIPTION" VARCHAR2(1000 BYTE),
   "METAKEYWORDS" VARCHAR2(1000 BYTE),
   "CONTENTTYPE" NUMBER(*,0),
   "SHOWINMENU" NUMBER(*,0),
   "STATUS" NUMBER(*,0),
   "ORDINAL" NUMBER(*,0),
   "ROLE" NUMBER(*,0) DEFAULT 0
) ;
--------------------------------------------------------
--  DDL for Table FORUMPOST
--------------------------------------------------------

CREATE TABLE "FORUMPOST"
(	"ID" NUMBER(*,0),
   "FORUMID" NUMBER(*,0) DEFAULT 1,
   "PARENTID" NUMBER(*,0) DEFAULT 0,
   "USERID" NUMBER(*,0),
   "SUBJECT" VARCHAR2(255 BYTE),
   "MESSAGE" CLOB,
   "LOCKED" NUMBER(1,0) DEFAULT 0,
   "STICKY" NUMBER(1,0) DEFAULT 0,
   "REPLIES" NUMBER(*,0) DEFAULT 0,
   "CREATEDDATE" DATE,
   "UPDATEDDATE" DATE
) ;
--------------------------------------------------------
--  DDL for Table GENRES
--------------------------------------------------------

CREATE TABLE "GENRES"
(	"ID" NUMBER(*,0),
   "TITLE" VARCHAR2(255 BYTE),
   "TYPE" NUMBER(*,0)
);
--------------------------------------------------------
--  DDL for Table GROUPS
--------------------------------------------------------

CREATE TABLE "GROUPS"
(	"ID" NUMBER(*,0),
   "NAME_" VARCHAR2(255 BYTE),
   "BACKFILL_TARGET" NUMBER(*,0) DEFAULT 1,
   "FIRST_RECORD" NUMBER(38,0) DEFAULT 0,
   "FIRST_RECORD_POSTDATE" DATE,
   "LAST_RECORD" NUMBER(38,0) DEFAULT 0,
   "LAST_RECORD_POSTDATE" DATE,
   "LAST_UPDATED" DATE,
   "MINFILESTOFORMRELEASE" NUMBER(*,0),
   "ACTIVE" NUMBER(1,0) DEFAULT 0,
   "DESCRIPTION" VARCHAR2(255 BYTE)
);
--------------------------------------------------------
--  DDL for Table MENU
--------------------------------------------------------

CREATE TABLE "MENU"
(	"ID" NUMBER(*,0),
   "HREF" VARCHAR2(2000 BYTE),
   "TITLE" VARCHAR2(2000 BYTE),
   "TOOLTIP" VARCHAR2(2000 BYTE),
   "ROLE" NUMBER(*,0),
   "ORDINAL" NUMBER(*,0),
   "MENUEVAL" VARCHAR2(2000 BYTE)
) ;
--------------------------------------------------------
--  DDL for Table MOVIEINFO
--------------------------------------------------------

CREATE TABLE "MOVIEINFO"
(	"ID" NUMBER(*,0),
   "IMDBID" NUMBER(*,0),
   "TMDBID" NUMBER(*,0),
   "TITLE" VARCHAR2(128 BYTE),
   "TAGLINE" VARCHAR2(255 BYTE),
   "RATING" VARCHAR2(4 BYTE),
   "PLOT" VARCHAR2(255 BYTE),
   "YEAR" VARCHAR2(4 BYTE),
   "GENRE" VARCHAR2(64 BYTE),
   "DIRECTOR" VARCHAR2(64 BYTE),
   "ACTORS" VARCHAR2(255 BYTE),
   "LANGUAGE" VARCHAR2(64 BYTE),
   "COVER" NUMBER(1,0) DEFAULT 0,
   "BACKDROP" NUMBER(1,0) DEFAULT 0,
   "CREATEDDATE" DATE,
   "UPDATEDDATE" DATE
);
--------------------------------------------------------
--  DDL for Table MUSICINFO
--------------------------------------------------------

CREATE TABLE "MUSICINFO"
(	"ID" NUMBER(*,0),
   "TITLE" VARCHAR2(128 BYTE),
   "ASIN" VARCHAR2(128 BYTE),
   "URL" VARCHAR2(1000 BYTE),
   "SALESRANK" NUMBER(*,0),
   "ARTIST" VARCHAR2(255 BYTE),
   "PUBLISHER" VARCHAR2(255 BYTE),
   "RELEASEDATE" DATE,
   "REVIEW" VARCHAR2(2000 BYTE),
   "YEAR" VARCHAR2(4 BYTE),
   "GENREID" NUMBER(*,0),
   "TRACKS" VARCHAR2(2000 BYTE),
   "COVER" NUMBER(1,0) DEFAULT 0,
   "CREATEDDATE" DATE,
   "UPDATEDDATE" DATE
);
--------------------------------------------------------
--  DDL for Table PARTREPAIR
--------------------------------------------------------

CREATE TABLE "PARTREPAIR"
(	"ID" NUMBER(*,0),
   "NUMBERID" NUMBER(38,0),
   "GROUPID" NUMBER(*,0),
   "ATTEMPTS" NUMBER(1,0) DEFAULT 0
);
--------------------------------------------------------
--  DDL for Table PARTS
--------------------------------------------------------

CREATE TABLE "PARTS"
(	"ID" NUMBER(38,0),
   "BINARYID" NUMBER(*,0) DEFAULT 0,
   "MESSAGEID" VARCHAR2(255 BYTE),
   "NUMBER_" NUMBER(38,0) DEFAULT 0,
   "PARTNUMBER" NUMBER(*,0) DEFAULT 0,
   "SIZE_" NUMBER(38,0) DEFAULT 0,
   "DATEADDED" DATE
);
--------------------------------------------------------
--  DDL for Table RELEASECOMMENT
--------------------------------------------------------

CREATE TABLE "RELEASECOMMENT"
(	"ID" NUMBER(*,0),
   "RELEASEID" NUMBER(*,0),
   "TEXT" VARCHAR2(2000 BYTE),
   "USERID" NUMBER(*,0),
   "CREATEDDATE" DATE,
   "HOST" VARCHAR2(15 BYTE)
);
--------------------------------------------------------
--  DDL for Table RELEASENFO
--------------------------------------------------------

CREATE TABLE "RELEASENFO"
(	"ID" NUMBER(38,0),
   "RELEASEID" NUMBER(*,0),
   "BINARYID" NUMBER(*,0),
   "ATTEMPTS" NUMBER(1,0) DEFAULT 0,
   "NFO" BLOB
);
--------------------------------------------------------
--  DDL for Table RELEASEREGEX
--------------------------------------------------------

CREATE TABLE "RELEASEREGEX"
(	"ID" NUMBER(*,0),
   "GROUPNAME" VARCHAR2(255 BYTE),
   "REGEX" VARCHAR2(2000 BYTE),
   "ORDINAL" NUMBER(*,0),
   "STATUS" NUMBER(*,0) DEFAULT 1,
   "DESCRIPTION" VARCHAR2(1000 BYTE),
   "CATEGORYID" NUMBER(*,0)
);
--------------------------------------------------------
--  DDL for Table RELEASES
--------------------------------------------------------

CREATE TABLE "RELEASES"
(	"ID" NUMBER(38,0),
   "NAME_" VARCHAR2(1024 CHAR),
   "SEARCHNAME" VARCHAR2(1024 CHAR),
   "TOTALPART" NUMBER(*,0) DEFAULT 0,
   "GROUPID" NUMBER(*,0) DEFAULT 0,
   "SIZE_" NUMBER(38,0) DEFAULT 0,
   "POSTDATE" DATE,
   "ADDDATE" DATE,
   "GUID" VARCHAR2(50 BYTE),
   "FROMNAME" VARCHAR2(255 BYTE),
   "COMPLETION" FLOAT(63) DEFAULT 0,
   "CATEGORYID" NUMBER(*,0) DEFAULT 0,
   "REGEXID" NUMBER(*,0) DEFAULT 0,
   "RAGEID" NUMBER(*,0),
   "SERIESFULL" VARCHAR2(15 BYTE),
   "SEASON" VARCHAR2(10 BYTE),
   "EPISODE" VARCHAR2(10 BYTE),
   "TVTITLE" VARCHAR2(255 BYTE),
   "TVAIRDATE" DATE,
   "IMDBID" NUMBER(*,0),
   "MUSICINFOID" NUMBER(*,0),
   "CONSOLEINFOID" NUMBER(*,0),
   "REQID" NUMBER(*,0),
   "GRABS" NUMBER(*,0) DEFAULT 0,
   "COMMENTS" NUMBER(*,0) DEFAULT 0,
   "PASSWORDSTATUS" NUMBER(*,0) DEFAULT 0
);
--------------------------------------------------------
--  DDL for Table SITE
--------------------------------------------------------

CREATE TABLE "SITE"
(	"ID" NUMBER(*,0),
   "CODE" VARCHAR2(255 BYTE),
   "TITLE" VARCHAR2(1000 BYTE),
   "STRAPLINE" VARCHAR2(1000 BYTE),
   "METATITLE" VARCHAR2(1000 BYTE),
   "METADESCRIPTION" VARCHAR2(1000 BYTE),
   "METAKEYWORDS" VARCHAR2(1000 BYTE),
   "FOOTER" VARCHAR2(2000 BYTE),
   "EMAIL" VARCHAR2(1000 BYTE),
   "LASTUPDATE" DATE,
   "GOOGLE_ADSENSE_SEARCH" VARCHAR2(255 BYTE),
   "GOOGLE_ADSENSE_SIDEPANEL" VARCHAR2(255 BYTE),
   "GOOGLE_ANALYTICS_ACC" VARCHAR2(255 BYTE),
   "GOOGLE_ADSENSE_ACC" VARCHAR2(255 BYTE),
   "SITESEED" VARCHAR2(50 BYTE),
   "TANDC" VARCHAR2(4000 BYTE),
   "REGISTERSTATUS" NUMBER(*,0) DEFAULT 0,
   "STYLE" VARCHAR2(50 BYTE),
   "MENUPOSITION" NUMBER(*,0) DEFAULT 1,
   "DEREFERRER_LINK" VARCHAR2(255 BYTE),
   "NZBPATH" VARCHAR2(500 BYTE),
   "RAWRETENTIONDAYS" NUMBER(*,0) DEFAULT 3,
   "ATTEMPTGROUPBINDAYS" NUMBER(*,0) DEFAULT 2,
   "LOOKUPTVRAGE" NUMBER(*,0) DEFAULT 1,
   "LOOKUPIMDB" NUMBER(*,0) DEFAULT 1,
   "LOOKUPNFO" NUMBER(*,0) DEFAULT 1,
   "LOOKUPMUSIC" NUMBER(*,0) DEFAULT 1,
   "LOOKUPGAMES" NUMBER(*,0) DEFAULT 1,
   "AMAZONPUBKEY" VARCHAR2(255 BYTE),
   "AMAZONPRIVKEY" VARCHAR2(255 BYTE),
   "TMDBKEY" VARCHAR2(255 BYTE),
   "COMPRESSEDHEADERS" NUMBER(*,0) DEFAULT 0,
   "MAXMSSGS" NUMBER(*,0) DEFAULT 20000,
   "NEWGROUPSCANMETHOD" NUMBER(*,0) DEFAULT 0,
   "NEWGROUPDAYSTOSCAN" NUMBER(*,0) DEFAULT 3,
   "NEWGROUPMSGSTOSCAN" NUMBER(*,0) DEFAULT 50000,
   "STOREUSERIPS" NUMBER(*,0) DEFAULT 0,
   "MINFILESTOFORMRELEASE" NUMBER(*,0) DEFAULT 1,
   "REQIDURL" VARCHAR2(1000 BYTE),
   "LATESTREGEXURL" VARCHAR2(1000 BYTE),
   "LATESTREGEXREVISION" NUMBER(*,0) DEFAULT 0,
   "RELEASERETENTIONDAYS" NUMBER(*,0) DEFAULT 0,
   "CHECKPASSWORDEDRAR" NUMBER(*,0) DEFAULT 0,
   "SHOWPASSWORDEDRELEASE" NUMBER(*,0) DEFAULT 0
);
--------------------------------------------------------
--  DDL for Table TVRAGE
--------------------------------------------------------

CREATE TABLE "TVRAGE"
(	"ID" NUMBER(*,0),
   "RAGEID" NUMBER(*,0),
   "TVDBID" NUMBER(*,0),
   "RELEASETITLE" VARCHAR2(255 BYTE),
   "DESCRIPTION" VARCHAR2(4000 BYTE),
   "GENRE" VARCHAR2(64 BYTE),
   "COUNTRY" VARCHAR2(2 BYTE),
   "IMGDATA" BLOB,
   "CREATEDDATE" DATE
);
--------------------------------------------------------
--  DDL for Table USERCART
--------------------------------------------------------

CREATE TABLE "USERCART"
(	"ID" NUMBER(*,0),
   "USERID" NUMBER(*,0),
   "RELEASEID" NUMBER(*,0),
   "CREATEDDATE" DATE
);
--------------------------------------------------------
--  DDL for Table USEREXCAT
--------------------------------------------------------

CREATE TABLE "USEREXCAT"
(	"ID" NUMBER(*,0),
   "USERID" NUMBER(*,0),
   "CATEGORYID" NUMBER(*,0),
   "CREATEDDATE" DATE
);
--------------------------------------------------------
--  DDL for Table USERINVITE
--------------------------------------------------------

CREATE TABLE "USERINVITE"
(	"ID" NUMBER(*,0),
   "GUID" VARCHAR2(50 BYTE),
   "USERID" NUMBER(*,0),
   "CREATEDDATE" DATE
);
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

CREATE TABLE "USERS"
(	"ID" NUMBER(*,0),
   "USERNAME" VARCHAR2(50 BYTE),
   "EMAIL" VARCHAR2(255 BYTE),
   "PASSWORD" VARCHAR2(255 BYTE),
   "ROLE" NUMBER(*,0) DEFAULT 1,
   "HOST" VARCHAR2(15 BYTE),
   "GRABS" NUMBER(*,0) DEFAULT 0,
   "RSSTOKEN" VARCHAR2(32 BYTE),
   "CREATEDDATE" DATE,
   "RESETGUID" VARCHAR2(50 BYTE),
   "LASTLOGIN" DATE,
   "APIACCESS" DATE,
   "INVITES" NUMBER(*,0) DEFAULT 0,
   "INVITEDBY" NUMBER(*,0),
   "MOVIEVIEW" NUMBER(*,0) DEFAULT 1,
   "MUSICVIEW" NUMBER(*,0) DEFAULT 1,
   "CONSOLEVIEW" NUMBER(*,0) DEFAULT 1,
   "USERSEED" VARCHAR2(50 BYTE)
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
--  Constraints for Table FORUMPOST
--------------------------------------------------------

ALTER TABLE "FORUMPOST" ADD PRIMARY KEY ("ID");
ALTER TABLE "FORUMPOST" MODIFY ("UPDATEDDATE" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("CREATEDDATE" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("REPLIES" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("STICKY" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("LOCKED" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("MESSAGE" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("SUBJECT" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("USERID" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("PARENTID" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("FORUMID" NOT NULL ENABLE);
ALTER TABLE "FORUMPOST" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TVRAGE
--------------------------------------------------------

ALTER TABLE "TVRAGE" ADD PRIMARY KEY ("ID");
ALTER TABLE "TVRAGE" MODIFY ("RELEASETITLE" NOT NULL ENABLE);
ALTER TABLE "TVRAGE" MODIFY ("TVDBID" NOT NULL ENABLE);
ALTER TABLE "TVRAGE" MODIFY ("RAGEID" NOT NULL ENABLE);
ALTER TABLE "TVRAGE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SITE
--------------------------------------------------------

ALTER TABLE "SITE" ADD PRIMARY KEY ("ID");
ALTER TABLE "SITE" MODIFY ("SHOWPASSWORDEDRELEASE" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("CHECKPASSWORDEDRAR" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("RELEASERETENTIONDAYS" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("LATESTREGEXREVISION" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("MINFILESTOFORMRELEASE" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("STOREUSERIPS" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("NEWGROUPMSGSTOSCAN" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("NEWGROUPDAYSTOSCAN" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("NEWGROUPSCANMETHOD" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("MAXMSSGS" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("COMPRESSEDHEADERS" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("LOOKUPGAMES" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("LOOKUPMUSIC" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("LOOKUPNFO" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("LOOKUPIMDB" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("LOOKUPTVRAGE" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("ATTEMPTGROUPBINDAYS" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("RAWRETENTIONDAYS" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("NZBPATH" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("MENUPOSITION" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("REGISTERSTATUS" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("TANDC" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("SITESEED" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("LASTUPDATE" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("EMAIL" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("FOOTER" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("METAKEYWORDS" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("METADESCRIPTION" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("METATITLE" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("STRAPLINE" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("TITLE" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("CODE" NOT NULL ENABLE);
ALTER TABLE "SITE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USERINVITE
--------------------------------------------------------

ALTER TABLE "USERINVITE" MODIFY ("GUID" NOT NULL ENABLE);
ALTER TABLE "USERINVITE" MODIFY ("ID" NOT NULL ENABLE);
ALTER TABLE "USERINVITE" ADD PRIMARY KEY ("ID");
ALTER TABLE "USERINVITE" MODIFY ("CREATEDDATE" NOT NULL ENABLE);
ALTER TABLE "USERINVITE" MODIFY ("USERID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MUSICINFO
--------------------------------------------------------

ALTER TABLE "MUSICINFO" ADD PRIMARY KEY ("ID");
ALTER TABLE "MUSICINFO" MODIFY ("UPDATEDDATE" NOT NULL ENABLE);
ALTER TABLE "MUSICINFO" MODIFY ("CREATEDDATE" NOT NULL ENABLE);
ALTER TABLE "MUSICINFO" MODIFY ("COVER" NOT NULL ENABLE);
ALTER TABLE "MUSICINFO" MODIFY ("YEAR" NOT NULL ENABLE);
ALTER TABLE "MUSICINFO" MODIFY ("TITLE" NOT NULL ENABLE);
ALTER TABLE "MUSICINFO" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CATEGORY
--------------------------------------------------------

ALTER TABLE "CATEGORY" ADD PRIMARY KEY ("ID");
ALTER TABLE "CATEGORY" MODIFY ("STATUS" NOT NULL ENABLE);
ALTER TABLE "CATEGORY" MODIFY ("TITLE" NOT NULL ENABLE);
ALTER TABLE "CATEGORY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CONTENT
--------------------------------------------------------

ALTER TABLE "CONTENT" ADD PRIMARY KEY ("ID");
ALTER TABLE "CONTENT" MODIFY ("ROLE" NOT NULL ENABLE);
ALTER TABLE "CONTENT" MODIFY ("STATUS" NOT NULL ENABLE);
ALTER TABLE "CONTENT" MODIFY ("SHOWINMENU" NOT NULL ENABLE);
ALTER TABLE "CONTENT" MODIFY ("CONTENTTYPE" NOT NULL ENABLE);
ALTER TABLE "CONTENT" MODIFY ("TITLE" NOT NULL ENABLE);
ALTER TABLE "CONTENT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PARTS
--------------------------------------------------------

ALTER TABLE "PARTS" ADD PRIMARY KEY ("ID");
ALTER TABLE "PARTS" MODIFY ("SIZE_" NOT NULL ENABLE);
ALTER TABLE "PARTS" MODIFY ("PARTNUMBER" NOT NULL ENABLE);
ALTER TABLE "PARTS" MODIFY ("NUMBER_" NOT NULL ENABLE);
ALTER TABLE "PARTS" MODIFY ("MESSAGEID" NOT NULL ENABLE);
ALTER TABLE "PARTS" MODIFY ("BINARYID" NOT NULL ENABLE);
ALTER TABLE "PARTS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PARTREPAIR
--------------------------------------------------------

ALTER TABLE "PARTREPAIR" ADD PRIMARY KEY ("ID");
ALTER TABLE "PARTREPAIR" MODIFY ("ATTEMPTS" NOT NULL ENABLE);
ALTER TABLE "PARTREPAIR" MODIFY ("GROUPID" NOT NULL ENABLE);
ALTER TABLE "PARTREPAIR" MODIFY ("NUMBERID" NOT NULL ENABLE);
ALTER TABLE "PARTREPAIR" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MOVIEINFO
--------------------------------------------------------

ALTER TABLE "MOVIEINFO" ADD PRIMARY KEY ("ID");
ALTER TABLE "MOVIEINFO" MODIFY ("UPDATEDDATE" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("CREATEDDATE" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("BACKDROP" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("COVER" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("LANGUAGE" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("ACTORS" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("DIRECTOR" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("GENRE" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("YEAR" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("PLOT" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("RATING" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("TAGLINE" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("TITLE" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("IMDBID" NOT NULL ENABLE);
ALTER TABLE "MOVIEINFO" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USERCART
--------------------------------------------------------

ALTER TABLE "USERCART" ADD PRIMARY KEY ("ID");
ALTER TABLE "USERCART" MODIFY ("CREATEDDATE" NOT NULL ENABLE);
ALTER TABLE "USERCART" MODIFY ("RELEASEID" NOT NULL ENABLE);
ALTER TABLE "USERCART" MODIFY ("USERID" NOT NULL ENABLE);
ALTER TABLE "USERCART" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RELEASEREGEX
--------------------------------------------------------

ALTER TABLE "RELEASEREGEX" ADD PRIMARY KEY ("ID");
ALTER TABLE "RELEASEREGEX" MODIFY ("STATUS" NOT NULL ENABLE);
ALTER TABLE "RELEASEREGEX" MODIFY ("ORDINAL" NOT NULL ENABLE);
ALTER TABLE "RELEASEREGEX" MODIFY ("REGEX" NOT NULL ENABLE);
ALTER TABLE "RELEASEREGEX" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BINARYBLACKLIST
--------------------------------------------------------

ALTER TABLE "BINARYBLACKLIST" ADD PRIMARY KEY ("ID");
ALTER TABLE "BINARYBLACKLIST" MODIFY ("STATUS" NOT NULL ENABLE);
ALTER TABLE "BINARYBLACKLIST" MODIFY ("OPTYPE" NOT NULL ENABLE);
ALTER TABLE "BINARYBLACKLIST" MODIFY ("MSGCOL" NOT NULL ENABLE);
ALTER TABLE "BINARYBLACKLIST" MODIFY ("REGEX" NOT NULL ENABLE);
ALTER TABLE "BINARYBLACKLIST" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RELEASENFO
--------------------------------------------------------

ALTER TABLE "RELEASENFO" ADD PRIMARY KEY ("ID");
ALTER TABLE "RELEASENFO" MODIFY ("ATTEMPTS" NOT NULL ENABLE);
ALTER TABLE "RELEASENFO" MODIFY ("BINARYID" NOT NULL ENABLE);
ALTER TABLE "RELEASENFO" MODIFY ("RELEASEID" NOT NULL ENABLE);
ALTER TABLE "RELEASENFO" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table GENRES
--------------------------------------------------------

ALTER TABLE "GENRES" ADD PRIMARY KEY ("ID");
ALTER TABLE "GENRES" MODIFY ("TITLE" NOT NULL ENABLE);
ALTER TABLE "GENRES" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USEREXCAT
--------------------------------------------------------

ALTER TABLE "USEREXCAT" ADD PRIMARY KEY ("ID");
ALTER TABLE "USEREXCAT" MODIFY ("CREATEDDATE" NOT NULL ENABLE);
ALTER TABLE "USEREXCAT" MODIFY ("CATEGORYID" NOT NULL ENABLE);
ALTER TABLE "USEREXCAT" MODIFY ("USERID" NOT NULL ENABLE);
ALTER TABLE "USEREXCAT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CONSOLEINFO
--------------------------------------------------------

ALTER TABLE "CONSOLEINFO" ADD PRIMARY KEY ("ID");
ALTER TABLE "CONSOLEINFO" MODIFY ("UPDATEDDATE" NOT NULL ENABLE);
ALTER TABLE "CONSOLEINFO" MODIFY ("CREATEDDATE" NOT NULL ENABLE);
ALTER TABLE "CONSOLEINFO" MODIFY ("COVER" NOT NULL ENABLE);
ALTER TABLE "CONSOLEINFO" MODIFY ("TITLE" NOT NULL ENABLE);
ALTER TABLE "CONSOLEINFO" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MENU
--------------------------------------------------------

ALTER TABLE "MENU" ADD PRIMARY KEY ("ID");
ALTER TABLE "MENU" MODIFY ("ORDINAL" NOT NULL ENABLE);
ALTER TABLE "MENU" MODIFY ("ROLE" NOT NULL ENABLE);
ALTER TABLE "MENU" MODIFY ("TOOLTIP" NOT NULL ENABLE);
ALTER TABLE "MENU" MODIFY ("TITLE" NOT NULL ENABLE);
ALTER TABLE "MENU" MODIFY ("HREF" NOT NULL ENABLE);
ALTER TABLE "MENU" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RELEASES
--------------------------------------------------------

ALTER TABLE "RELEASES" ADD PRIMARY KEY ("ID");
ALTER TABLE "RELEASES" MODIFY ("PASSWORDSTATUS" NOT NULL ENABLE);
ALTER TABLE "RELEASES" MODIFY ("COMMENTS" NOT NULL ENABLE);
ALTER TABLE "RELEASES" MODIFY ("GRABS" NOT NULL ENABLE);
ALTER TABLE "RELEASES" MODIFY ("COMPLETION" NOT NULL ENABLE);
ALTER TABLE "RELEASES" MODIFY ("GUID" NOT NULL ENABLE);
ALTER TABLE "RELEASES" MODIFY ("SIZE_" NOT NULL ENABLE);
ALTER TABLE "RELEASES" MODIFY ("GROUPID" NOT NULL ENABLE);
ALTER TABLE "RELEASES" MODIFY ("SEARCHNAME" NOT NULL ENABLE);
ALTER TABLE "RELEASES" MODIFY ("NAME_" NOT NULL ENABLE);
ALTER TABLE "RELEASES" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table GROUPS
--------------------------------------------------------

ALTER TABLE "GROUPS" ADD PRIMARY KEY ("ID");
ALTER TABLE "GROUPS" MODIFY ("ACTIVE" NOT NULL ENABLE);
ALTER TABLE "GROUPS" MODIFY ("LAST_RECORD" NOT NULL ENABLE);
ALTER TABLE "GROUPS" MODIFY ("FIRST_RECORD" NOT NULL ENABLE);
ALTER TABLE "GROUPS" MODIFY ("BACKFILL_TARGET" NOT NULL ENABLE);
ALTER TABLE "GROUPS" MODIFY ("NAME_" NOT NULL ENABLE);
ALTER TABLE "GROUPS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BINARIES
--------------------------------------------------------

ALTER TABLE "BINARIES" ADD PRIMARY KEY ("ID");
ALTER TABLE "BINARIES" MODIFY ("BINARYHASH" NOT NULL ENABLE);
ALTER TABLE "BINARIES" MODIFY ("GROUPID" NOT NULL ENABLE);
ALTER TABLE "BINARIES" MODIFY ("TOTALPARTS" NOT NULL ENABLE);
ALTER TABLE "BINARIES" MODIFY ("FROMNAME" NOT NULL ENABLE);
ALTER TABLE "BINARIES" MODIFY ("NAME_" NOT NULL ENABLE);
ALTER TABLE "BINARIES" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

ALTER TABLE "USERS" ADD PRIMARY KEY ("ID");
ALTER TABLE "USERS" MODIFY ("USERSEED" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("CONSOLEVIEW" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("MUSICVIEW" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("MOVIEVIEW" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("INVITES" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("CREATEDDATE" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("RSSTOKEN" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("GRABS" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("ROLE" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("PASSWORD" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("EMAIL" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("USERNAME" NOT NULL ENABLE);
ALTER TABLE "USERS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RELEASECOMMENT
--------------------------------------------------------

ALTER TABLE "RELEASECOMMENT" ADD PRIMARY KEY ("ID");
ALTER TABLE "RELEASECOMMENT" MODIFY ("USERID" NOT NULL ENABLE);
ALTER TABLE "RELEASECOMMENT" MODIFY ("TEXT" NOT NULL ENABLE);
ALTER TABLE "RELEASECOMMENT" MODIFY ("RELEASEID" NOT NULL ENABLE);
ALTER TABLE "RELEASECOMMENT" MODIFY ("ID" NOT NULL ENABLE);