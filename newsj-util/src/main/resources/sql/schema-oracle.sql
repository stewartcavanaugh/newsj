-- ----------------------------------------------------------------------- 
-- users 
-- ----------------------------------------------------------------------- 

DROP TABLE "users" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- userinvite 
-- ----------------------------------------------------------------------- 

DROP TABLE "userinvite" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- userexcat 
-- ----------------------------------------------------------------------- 

DROP TABLE "userexcat" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- usercart 
-- ----------------------------------------------------------------------- 

DROP TABLE "usercart" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- tvrage 
-- ----------------------------------------------------------------------- 

DROP TABLE "tvrage" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- site 
-- ----------------------------------------------------------------------- 

DROP TABLE "site" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- releases 
-- ----------------------------------------------------------------------- 

DROP TABLE "releases" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- releaseregex 
-- ----------------------------------------------------------------------- 

DROP TABLE "releaseregex" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- ReleaseNfo_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "ReleaseNfo_SEQ";

-- ----------------------------------------------------------------------- 
-- releasenfo 
-- ----------------------------------------------------------------------- 

DROP TABLE "releasenfo" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- releasecomment 
-- ----------------------------------------------------------------------- 

DROP TABLE "releasecomment" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- Release_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "Release_SEQ";

-- ----------------------------------------------------------------------- 
-- parts 
-- ----------------------------------------------------------------------- 

DROP TABLE "parts" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- partrepair 
-- ----------------------------------------------------------------------- 

DROP TABLE "partrepair" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- Part_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "Part_SEQ";

-- ----------------------------------------------------------------------- 
-- musicinfo 
-- ----------------------------------------------------------------------- 

DROP TABLE "musicinfo" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- movieinfo 
-- ----------------------------------------------------------------------- 

DROP TABLE "movieinfo" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- menu 
-- ----------------------------------------------------------------------- 

DROP TABLE "menu" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- groups 
-- ----------------------------------------------------------------------- 

DROP TABLE "groups" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- genres 
-- ----------------------------------------------------------------------- 

DROP TABLE "genres" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- forumpost 
-- ----------------------------------------------------------------------- 

DROP TABLE "forumpost" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- content 
-- ----------------------------------------------------------------------- 

DROP TABLE "content" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- consoleinfo 
-- ----------------------------------------------------------------------- 

DROP TABLE "consoleinfo" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- category 
-- ----------------------------------------------------------------------- 

DROP TABLE "category" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- BinaryBlacklistEntry_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "BinaryBlacklistEntry_SEQ";

-- ----------------------------------------------------------------------- 
-- binaryblacklist 
-- ----------------------------------------------------------------------- 

DROP TABLE "binaryblacklist" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- Binary_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "Binary_SEQ";

-- ----------------------------------------------------------------------- 
-- binaries 
-- ----------------------------------------------------------------------- 

DROP TABLE "binaries" CASCADE CONSTRAINTS;

-- ----------------------------------------------------------------------- 
-- binaries 
-- ----------------------------------------------------------------------- 

CREATE TABLE "binaries"
(
    "ID" NUMBER(38) NOT NULL,
    "name" VARCHAR2(1024) NOT NULL,
    "fromname" VARCHAR2(255) NOT NULL,
    "date" DATE,
    "xref" VARCHAR2(255) NOT NULL,
    "totalParts" INTEGER DEFAULT 0 NOT NULL,
    "groupID" INTEGER DEFAULT 0 NOT NULL,
    "procstat" INTEGER DEFAULT 0,
    "procattempts" INTEGER DEFAULT 0,
    "categoryID" INTEGER,
    "regexID" INTEGER,
    "reqID" INTEGER,
    "relpart" INTEGER DEFAULT 0,
    "reltotalpart" INTEGER DEFAULT 0,
    "binaryhash" VARCHAR2(255) NOT NULL,
    "relname" VARCHAR2(255),
    "importname" VARCHAR2(255),
    "releaseID" INTEGER,
    "dateadded" DATE,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "ix_binary_binaryhash" ON "binaries" ("binaryhash");

CREATE INDEX "ix_binary_date" ON "binaries" ("date");

CREATE INDEX "ix_binary_fromname" ON "binaries" ("fromname");

CREATE INDEX "ix_binary_groupID" ON "binaries" ("groupID");

CREATE INDEX "ix_binary_dateadded" ON "binaries" ("dateadded");

CREATE INDEX "ix_binary_groupID_procstat" ON "binaries" ("procstat", "groupID");

CREATE INDEX "ix_binary_procstat" ON "binaries" ("procstat");

CREATE INDEX "ix_binary_releaseID" ON "binaries" ("releaseID");

CREATE INDEX "ix_binary_relname" ON "binaries" ("relname");

-- ----------------------------------------------------------------------- 
-- Binary_SEQ 
-- ----------------------------------------------------------------------- 

CREATE SEQUENCE Binary_SEQ
MINVALUE 1
START WITH 1
INCREMENT BY 100
CACHE 20;

-- ----------------------------------------------------------------------- 
-- binaryblacklist 
-- ----------------------------------------------------------------------- 

CREATE TABLE "binaryblacklist"
(
    "ID" NUMBER(38) NOT NULL,
    "groupname" VARCHAR2(255),
    "regex" VARCHAR2(2000) NOT NULL,
    "msgcol" INTEGER DEFAULT 1 NOT NULL,
    "optype" INTEGER DEFAULT 1 NOT NULL,
    "status" INTEGER DEFAULT 1 NOT NULL,
    "description" VARCHAR2(1000),
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- BinaryBlacklistEntry_SEQ 
-- ----------------------------------------------------------------------- 

CREATE SEQUENCE BinaryBlacklistEntry_SEQ
MINVALUE 1
START WITH 100001
INCREMENT BY 10
CACHE 20;

-- ----------------------------------------------------------------------- 
-- category 
-- ----------------------------------------------------------------------- 

CREATE TABLE "category"
(
    "ID" INTEGER NOT NULL,
    "title" VARCHAR2(255) NOT NULL,
    "parentID" INTEGER,
    "status" INTEGER DEFAULT 1 NOT NULL,
    "description" VARCHAR2(255),
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- consoleinfo 
-- ----------------------------------------------------------------------- 

CREATE TABLE "consoleinfo"
(
    "ID" INTEGER NOT NULL,
    "title" VARCHAR2(128) NOT NULL,
    "asin" VARCHAR2(128),
    "url" VARCHAR2(1000),
    "salesrank" INTEGER,
    "platform" VARCHAR2(255),
    "publisher" VARCHAR2(255),
    "genreID" INTEGER,
    "esrb" VARCHAR2(255),
    "releasedate" DATE,
    "review" VARCHAR2(2000),
    "cover" NUMBER(1) DEFAULT 0 NOT NULL,
    "createddate" DATE NOT NULL,
    "updateddate" DATE NOT NULL,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- content 
-- ----------------------------------------------------------------------- 

CREATE TABLE "content"
(
    "id" INTEGER NOT NULL,
    "title" VARCHAR2(255) NOT NULL,
    "url" VARCHAR2(2000),
    "body" CLOB,
    "metadescription" VARCHAR2(1000) NOT NULL,
    "metakeywords" VARCHAR2(1000) NOT NULL,
    "contenttype" INTEGER NOT NULL,
    "showinmenu" INTEGER NOT NULL,
    "status" INTEGER NOT NULL,
    "ordinal" INTEGER,
    "role" INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY ("id")
);

-- ----------------------------------------------------------------------- 
-- forumpost 
-- ----------------------------------------------------------------------- 

CREATE TABLE "forumpost"
(
    "ID" INTEGER NOT NULL,
    "forumID" INTEGER DEFAULT 1 NOT NULL,
    "parentID" INTEGER DEFAULT 0 NOT NULL,
    "userID" INTEGER NOT NULL,
    "subject" VARCHAR2(255) NOT NULL,
    "message" CLOB NOT NULL,
    "locked" NUMBER(1) DEFAULT 0 NOT NULL,
    "sticky" NUMBER(1) DEFAULT 0 NOT NULL,
    "replies" INTEGER DEFAULT 0 NOT NULL,
    "createddate" DATE NOT NULL,
    "updateddate" DATE NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE INDEX "createddate" ON "forumpost" ("createddate");

CREATE INDEX "parentID" ON "forumpost" ("parentID");

CREATE INDEX "updateddate" ON "forumpost" ("updateddate");

CREATE INDEX "userID" ON "forumpost" ("userID");

-- ----------------------------------------------------------------------- 
-- genres 
-- ----------------------------------------------------------------------- 

CREATE TABLE "genres"
(
    "ID" INTEGER NOT NULL,
    "title" VARCHAR2(255) NOT NULL,
    "type" INTEGER,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- groups 
-- ----------------------------------------------------------------------- 

CREATE TABLE "groups"
(
    "ID" INTEGER NOT NULL,
    "name" VARCHAR2(255) NOT NULL,
    "backfill_target" INTEGER DEFAULT 1 NOT NULL,
    "first_record" NUMBER(38) DEFAULT 0 NOT NULL,
    "first_record_postdate" DATE,
    "last_record" NUMBER(38) DEFAULT 0 NOT NULL,
    "last_record_postdate" DATE,
    "last_updated" DATE,
    "minfilestoformrelease" INTEGER,
    "active" NUMBER(1) DEFAULT 0 NOT NULL,
    "description" VARCHAR2(255),
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "name" ON "groups" ("name");

CREATE INDEX "active" ON "groups" ("active");

-- ----------------------------------------------------------------------- 
-- menu 
-- ----------------------------------------------------------------------- 

CREATE TABLE "menu"
(
    "ID" INTEGER NOT NULL,
    "href" VARCHAR2(2000) NOT NULL,
    "title" VARCHAR2(2000) NOT NULL,
    "tooltip" VARCHAR2(2000) NOT NULL,
    "role" INTEGER NOT NULL,
    "ordinal" INTEGER NOT NULL,
    "menueval" VARCHAR2(2000) NOT NULL,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- movieinfo 
-- ----------------------------------------------------------------------- 

CREATE TABLE "movieinfo"
(
    "ID" INTEGER NOT NULL,
    "imdbID" INTEGER NOT NULL,
    "tmdbID" INTEGER,
    "title" VARCHAR2(128) NOT NULL,
    "tagline" VARCHAR2(255) NOT NULL,
    "rating" VARCHAR2(4) NOT NULL,
    "plot" VARCHAR2(255) NOT NULL,
    "year" VARCHAR2(4) NOT NULL,
    "genre" VARCHAR2(64) NOT NULL,
    "director" VARCHAR2(64) NOT NULL,
    "actors" VARCHAR2(255) NOT NULL,
    "language" VARCHAR2(64) NOT NULL,
    "cover" NUMBER(1) DEFAULT 0 NOT NULL,
    "backdrop" NUMBER(1) DEFAULT 0 NOT NULL,
    "createddate" DATE NOT NULL,
    "updateddate" DATE NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "imdbID" ON "movieinfo" ("imdbID");

-- ----------------------------------------------------------------------- 
-- musicinfo 
-- ----------------------------------------------------------------------- 

CREATE TABLE "musicinfo"
(
    "ID" INTEGER NOT NULL,
    "title" VARCHAR2(128) NOT NULL,
    "asin" VARCHAR2(128),
    "url" VARCHAR2(1000),
    "salesrank" INTEGER,
    "artist" VARCHAR2(255),
    "publisher" VARCHAR2(255),
    "releasedate" DATE,
    "review" VARCHAR2(2000),
    "year" VARCHAR2(4) NOT NULL,
    "genreID" INTEGER,
    "tracks" VARCHAR2(2000),
    "cover" NUMBER(1) DEFAULT 0 NOT NULL,
    "createddate" DATE NOT NULL,
    "updateddate" DATE NOT NULL,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- Part_SEQ 
-- ----------------------------------------------------------------------- 

CREATE SEQUENCE Part_SEQ
MINVALUE 1
START WITH 1
INCREMENT BY 1000
CACHE 20;

-- ----------------------------------------------------------------------- 
-- partrepair 
-- ----------------------------------------------------------------------- 

CREATE TABLE "partrepair"
(
    "ID" INTEGER NOT NULL,
    "numberID" NUMBER(38) NOT NULL,
    "groupID" INTEGER NOT NULL,
    "attempts" NUMBER(1) DEFAULT 0 NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "ix_partrepair_numberID_groupID" ON "partrepair" ("numberID", "groupID");

-- ----------------------------------------------------------------------- 
-- parts 
-- ----------------------------------------------------------------------- 

CREATE TABLE "parts"
(
    "ID" NUMBER(38) NOT NULL,
    "binaryID" INTEGER DEFAULT 0 NOT NULL,
    "messageID" VARCHAR2(255) NOT NULL,
    "number" NUMBER(38) DEFAULT 0 NOT NULL,
    "partnumber" INTEGER DEFAULT 0 NOT NULL,
    "size" NUMBER(38) DEFAULT 0 NOT NULL,
    "dateadded" DATE,
    PRIMARY KEY ("ID")
);

CREATE INDEX "binaryID" ON "parts" ("binaryID");

CREATE INDEX "ix_parts_dateadded" ON "parts" ("dateadded");

CREATE INDEX "ix_parts_number" ON "parts" ("number");

-- ----------------------------------------------------------------------- 
-- Release_SEQ 
-- ----------------------------------------------------------------------- 

CREATE SEQUENCE Release_SEQ
MINVALUE 1
START WITH 1
INCREMENT BY 100
CACHE 20;

-- ----------------------------------------------------------------------- 
-- releasecomment 
-- ----------------------------------------------------------------------- 

CREATE TABLE "releasecomment"
(
    "ID" INTEGER NOT NULL,
    "releaseID" INTEGER NOT NULL,
    "text" VARCHAR2(2000) NOT NULL,
    "userID" INTEGER NOT NULL,
    "createddate" DATE,
    "host" VARCHAR2(15),
    PRIMARY KEY ("ID")
);

CREATE INDEX "ix_releasecomment_releaseID" ON "releasecomment" ("releaseID");

CREATE INDEX "ix_releasecomment_userID" ON "releasecomment" ("userID");

-- ----------------------------------------------------------------------- 
-- releasenfo 
-- ----------------------------------------------------------------------- 

CREATE TABLE "releasenfo"
(
    "ID" NUMBER(38) NOT NULL,
    "releaseID" INTEGER NOT NULL,
    "binaryID" INTEGER NOT NULL,
    "attempts" NUMBER(1) DEFAULT 0 NOT NULL,
    "nfo" BLOB,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "ix_releasenfo_releaseID" ON "releasenfo" ("releaseID");

-- ----------------------------------------------------------------------- 
-- ReleaseNfo_SEQ 
-- ----------------------------------------------------------------------- 

CREATE SEQUENCE ReleaseNfo_SEQ
MINVALUE 1
START WITH 1
INCREMENT BY 100
CACHE 20;

-- ----------------------------------------------------------------------- 
-- releaseregex 
-- ----------------------------------------------------------------------- 

CREATE TABLE "releaseregex"
(
    "ID" INTEGER NOT NULL,
    "groupname" VARCHAR2(255),
    "regex" VARCHAR2(2000) NOT NULL,
    "ordinal" INTEGER NOT NULL,
    "status" INTEGER DEFAULT 1 NOT NULL,
    "description" VARCHAR2(1000),
    "categoryID" INTEGER,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- releases 
-- ----------------------------------------------------------------------- 

CREATE TABLE "releases"
(
    "ID" NUMBER(38) NOT NULL,
    "name" VARCHAR2(255) NOT NULL,
    "searchname" VARCHAR2(255) NOT NULL,
    "totalpart" INTEGER DEFAULT 0,
    "groupID" INTEGER DEFAULT 0 NOT NULL,
    "size" NUMBER(38) DEFAULT 0 NOT NULL,
    "postdate" DATE,
    "adddate" DATE,
    "guid" VARCHAR2(50) NOT NULL,
    "fromname" VARCHAR2(255),
    "completion" REAL DEFAULT 0 NOT NULL,
    "categoryID" INTEGER DEFAULT 0,
    "regexID" INTEGER DEFAULT 0,
    "rageID" INTEGER,
    "seriesfull" VARCHAR2(15),
    "season" VARCHAR2(10),
    "episode" VARCHAR2(10),
    "tvtitle" VARCHAR2(255),
    "tvairdate" DATE,
    "imdbID" INTEGER,
    "musicinfoID" INTEGER,
    "consoleinfoID" INTEGER,
    "reqID" INTEGER,
    "grabs" INTEGER DEFAULT 0 NOT NULL,
    "comments" INTEGER DEFAULT 0 NOT NULL,
    "passwordstatus" INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE INDEX "ix_releases_adddate" ON "releases" ("adddate");

CREATE INDEX "ix_releases_categoryID" ON "releases" ("categoryID");

CREATE INDEX "ix_releases_guid" ON "releases" ("guid");

CREATE INDEX "ix_releases_imdbID" ON "releases" ("imdbID");

CREATE INDEX "ix_releases_postdate" ON "releases" ("postdate");

CREATE INDEX "ix_releases_rageID" ON "releases" ("rageID");

-- ----------------------------------------------------------------------- 
-- site 
-- ----------------------------------------------------------------------- 

CREATE TABLE "site"
(
    "id" INTEGER NOT NULL,
    "code" VARCHAR2(255) NOT NULL,
    "title" VARCHAR2(1000) NOT NULL,
    "strapline" VARCHAR2(1000) NOT NULL,
    "metatitle" VARCHAR2(1000) NOT NULL,
    "metadescription" VARCHAR2(1000) NOT NULL,
    "metakeywords" VARCHAR2(1000) NOT NULL,
    "footer" VARCHAR2(2000) NOT NULL,
    "email" VARCHAR2(1000) NOT NULL,
    "lastupdate" DATE NOT NULL,
    "google_adsense_search" VARCHAR2(255),
    "google_adsense_sidepanel" VARCHAR2(255),
    "google_analytics_acc" VARCHAR2(255),
    "google_adsense_acc" VARCHAR2(255),
    "siteseed" VARCHAR2(50) NOT NULL,
    "tandc" VARCHAR2(5000) NOT NULL,
    "registerstatus" INTEGER DEFAULT 0 NOT NULL,
    "style" VARCHAR2(50),
    "menuposition" INTEGER DEFAULT 1 NOT NULL,
    "dereferrer_link" VARCHAR2(255),
    "nzbpath" VARCHAR2(500) NOT NULL,
    "rawretentiondays" INTEGER DEFAULT 3 NOT NULL,
    "attemptgroupbindays" INTEGER DEFAULT 2 NOT NULL,
    "lookuptvrage" INTEGER DEFAULT 1 NOT NULL,
    "lookupimdb" INTEGER DEFAULT 1 NOT NULL,
    "lookupnfo" INTEGER DEFAULT 1 NOT NULL,
    "lookupmusic" INTEGER DEFAULT 1 NOT NULL,
    "lookupgames" INTEGER DEFAULT 1 NOT NULL,
    "amazonpubkey" VARCHAR2(255),
    "amazonprivkey" VARCHAR2(255),
    "tmdbkey" VARCHAR2(255),
    "compressedheaders" INTEGER DEFAULT 0 NOT NULL,
    "maxmssgs" INTEGER DEFAULT 20000 NOT NULL,
    "newgroupscanmethod" INTEGER DEFAULT 0 NOT NULL,
    "newgroupdaystoscan" INTEGER DEFAULT 3 NOT NULL,
    "newgroupmsgstoscan" INTEGER DEFAULT 50000 NOT NULL,
    "storeuserips" INTEGER DEFAULT 0 NOT NULL,
    "minfilestoformrelease" INTEGER DEFAULT 1 NOT NULL,
    "reqidurl" VARCHAR2(1000) DEFAULT 'http://allfilled.newznab.com/query.php?t=[GROUP]&reqid=[REQID]' NOT NULL,
    "latestregexurl" VARCHAR2(1000) DEFAULT 'http://www.newznab.com/getregex.php' NOT NULL,
    "latestregexrevision" INTEGER DEFAULT 0 NOT NULL,
    "releaseretentiondays" INTEGER DEFAULT 0 NOT NULL,
    "checkpasswordedrar" INTEGER DEFAULT 0 NOT NULL,
    "showpasswordedrelease" INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY ("id")
);

-- ----------------------------------------------------------------------- 
-- tvrage 
-- ----------------------------------------------------------------------- 

CREATE TABLE "tvrage"
(
    "ID" INTEGER NOT NULL,
    "rageID" INTEGER NOT NULL,
    "tvdbID" INTEGER NOT NULL,
    "releasetitle" VARCHAR2(255) NOT NULL,
    "description" VARCHAR2(10000),
    "genre" VARCHAR2(64),
    "country" VARCHAR2(2),
    "imgdata" BLOB,
    "createddate" DATE,
    PRIMARY KEY ("ID")
);

CREATE INDEX "ix_tvrage_rageID" ON "tvrage" ("rageID");

-- ----------------------------------------------------------------------- 
-- usercart 
-- ----------------------------------------------------------------------- 

CREATE TABLE "usercart"
(
    "ID" INTEGER NOT NULL,
    "userID" INTEGER NOT NULL,
    "releaseID" INTEGER NOT NULL,
    "createddate" DATE NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "ix_usercart_userrelease" ON "usercart" ("userID", "releaseID");

-- ----------------------------------------------------------------------- 
-- userexcat 
-- ----------------------------------------------------------------------- 

CREATE TABLE "userexcat"
(
    "ID" INTEGER NOT NULL,
    "userID" INTEGER NOT NULL,
    "categoryID" INTEGER NOT NULL,
    "createddate" DATE NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "ix_userexcat_usercat" ON "userexcat" ("userID", "categoryID");

-- ----------------------------------------------------------------------- 
-- userinvite 
-- ----------------------------------------------------------------------- 

CREATE TABLE "userinvite"
(
    "ID" INTEGER NOT NULL,
    "guid" VARCHAR2(50) NOT NULL,
    "userID" INTEGER NOT NULL,
    "createddate" DATE NOT NULL,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- users 
-- ----------------------------------------------------------------------- 

CREATE TABLE "users"
(
    "ID" INTEGER NOT NULL,
    "username" VARCHAR2(50) NOT NULL,
    "email" VARCHAR2(255) NOT NULL,
    "password" VARCHAR2(255) NOT NULL,
    "role" INTEGER DEFAULT 1 NOT NULL,
    "host" VARCHAR2(15),
    "grabs" INTEGER DEFAULT 0 NOT NULL,
    "rsstoken" VARCHAR2(32) NOT NULL,
    "createddate" DATE NOT NULL,
    "resetguid" VARCHAR2(50),
    "lastlogin" DATE,
    "apiaccess" DATE,
    "invites" INTEGER DEFAULT 0 NOT NULL,
    "invitedby" INTEGER,
    "movieview" INTEGER DEFAULT 1 NOT NULL,
    "musicview" INTEGER DEFAULT 1 NOT NULL,
    "consoleview" INTEGER DEFAULT 1 NOT NULL,
    "userseed" VARCHAR2(50) NOT NULL,
    PRIMARY KEY ("ID")
);

