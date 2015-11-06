-- ----------------------------------------------------------------------- 
-- users 
-- ----------------------------------------------------------------------- 

DROP TABLE "users" CASCADE;

-- ----------------------------------------------------------------------- 
-- userinvite 
-- ----------------------------------------------------------------------- 

DROP TABLE "userinvite" CASCADE;

-- ----------------------------------------------------------------------- 
-- userexcat 
-- ----------------------------------------------------------------------- 

DROP TABLE "userexcat" CASCADE;

-- ----------------------------------------------------------------------- 
-- usercart 
-- ----------------------------------------------------------------------- 

DROP TABLE "usercart" CASCADE;

-- ----------------------------------------------------------------------- 
-- tvrage 
-- ----------------------------------------------------------------------- 

DROP TABLE "tvrage" CASCADE;

-- ----------------------------------------------------------------------- 
-- site 
-- ----------------------------------------------------------------------- 

DROP TABLE "site" CASCADE;

-- ----------------------------------------------------------------------- 
-- releases 
-- ----------------------------------------------------------------------- 

DROP TABLE "releases" CASCADE;

-- ----------------------------------------------------------------------- 
-- releaseregex 
-- ----------------------------------------------------------------------- 

DROP TABLE "releaseregex" CASCADE;

-- ----------------------------------------------------------------------- 
-- ReleaseNfo_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "ReleaseNfo_SEQ";

-- ----------------------------------------------------------------------- 
-- releasenfo 
-- ----------------------------------------------------------------------- 

DROP TABLE "releasenfo" CASCADE;

-- ----------------------------------------------------------------------- 
-- releasecomment 
-- ----------------------------------------------------------------------- 

DROP TABLE "releasecomment" CASCADE;

-- ----------------------------------------------------------------------- 
-- Release_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "Release_SEQ";

-- ----------------------------------------------------------------------- 
-- parts 
-- ----------------------------------------------------------------------- 

DROP TABLE "parts" CASCADE;

-- ----------------------------------------------------------------------- 
-- partrepair 
-- ----------------------------------------------------------------------- 

DROP TABLE "partrepair" CASCADE;

-- ----------------------------------------------------------------------- 
-- Part_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "Part_SEQ";

-- ----------------------------------------------------------------------- 
-- musicinfo 
-- ----------------------------------------------------------------------- 

DROP TABLE "musicinfo" CASCADE;

-- ----------------------------------------------------------------------- 
-- movieinfo 
-- ----------------------------------------------------------------------- 

DROP TABLE "movieinfo" CASCADE;

-- ----------------------------------------------------------------------- 
-- menu 
-- ----------------------------------------------------------------------- 

DROP TABLE "menu" CASCADE;

-- ----------------------------------------------------------------------- 
-- groups 
-- ----------------------------------------------------------------------- 

DROP TABLE "groups" CASCADE;

-- ----------------------------------------------------------------------- 
-- genres 
-- ----------------------------------------------------------------------- 

DROP TABLE "genres" CASCADE;

-- ----------------------------------------------------------------------- 
-- forumpost 
-- ----------------------------------------------------------------------- 

DROP TABLE "forumpost" CASCADE;

-- ----------------------------------------------------------------------- 
-- content 
-- ----------------------------------------------------------------------- 

DROP TABLE "content" CASCADE;

-- ----------------------------------------------------------------------- 
-- consoleinfo 
-- ----------------------------------------------------------------------- 

DROP TABLE "consoleinfo" CASCADE;

-- ----------------------------------------------------------------------- 
-- category 
-- ----------------------------------------------------------------------- 

DROP TABLE "category" CASCADE;

-- ----------------------------------------------------------------------- 
-- BinaryBlacklistEntry_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "BinaryBlacklistEntry_SEQ";

-- ----------------------------------------------------------------------- 
-- binaryblacklist 
-- ----------------------------------------------------------------------- 

DROP TABLE "binaryblacklist" CASCADE;

-- ----------------------------------------------------------------------- 
-- Binary_SEQ 
-- ----------------------------------------------------------------------- 

DROP SEQUENCE "Binary_SEQ";

-- ----------------------------------------------------------------------- 
-- binaries 
-- ----------------------------------------------------------------------- 

DROP TABLE "binaries" CASCADE;

-- ----------------------------------------------------------------------- 
-- binaries 
-- ----------------------------------------------------------------------- 

CREATE TABLE "binaries"
(
    "ID" BIGINT NOT NULL,
    "name" VARCHAR(1024) NOT NULL,
    "fromname" VARCHAR(255) NOT NULL,
    "date" TIMESTAMP,
    "xref" VARCHAR(255) NOT NULL,
    "totalParts" INTEGER DEFAULT 0 NOT NULL,
    "groupID" INTEGER DEFAULT 0 NOT NULL,
    "procstat" INTEGER DEFAULT 0,
    "procattempts" INTEGER DEFAULT 0,
    "categoryID" INTEGER,
    "regexID" INTEGER,
    "reqID" INTEGER,
    "relpart" INTEGER DEFAULT 0,
    "reltotalpart" INTEGER DEFAULT 0,
    "binaryhash" VARCHAR(255) NOT NULL,
    "relname" VARCHAR(255),
    "importname" VARCHAR(255),
    "releaseID" INTEGER,
    "dateadded" TIMESTAMP,
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

CREATE SEQUENCE "Binary_SEQ" MINVALUE 1 INCREMENT 100 START WITH 1 CACHE 20;

-- ----------------------------------------------------------------------- 
-- binaryblacklist 
-- ----------------------------------------------------------------------- 

CREATE TABLE "binaryblacklist"
(
    "ID" BIGINT NOT NULL,
    "groupname" VARCHAR(255),
    "regex" VARCHAR(2000) NOT NULL,
    "msgcol" INTEGER DEFAULT 1 NOT NULL,
    "optype" INTEGER DEFAULT 1 NOT NULL,
    "status" INTEGER DEFAULT 1 NOT NULL,
    "description" VARCHAR(1000),
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- BinaryBlacklistEntry_SEQ 
-- ----------------------------------------------------------------------- 

CREATE SEQUENCE "BinaryBlacklistEntry_SEQ" MINVALUE 1 INCREMENT 10 START WITH 100001 CACHE 20;

-- ----------------------------------------------------------------------- 
-- category 
-- ----------------------------------------------------------------------- 

CREATE TABLE "category"
(
    "ID" INTEGER NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "parentID" INTEGER,
    "status" INTEGER DEFAULT 1 NOT NULL,
    "description" VARCHAR(255),
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- consoleinfo 
-- ----------------------------------------------------------------------- 

CREATE TABLE "consoleinfo"
(
    "ID" INTEGER NOT NULL,
    "title" VARCHAR(128) NOT NULL,
    "asin" VARCHAR(128),
    "url" VARCHAR(1000),
    "salesrank" INTEGER,
    "platform" VARCHAR(255),
    "publisher" VARCHAR(255),
    "genreID" INTEGER,
    "esrb" VARCHAR(255),
    "releasedate" TIMESTAMP,
    "review" VARCHAR(2000),
    "cover" BOOLEAN DEFAULT 0 NOT NULL,
    "createddate" TIMESTAMP NOT NULL,
    "updateddate" TIMESTAMP NOT NULL,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- content 
-- ----------------------------------------------------------------------- 

CREATE TABLE "content"
(
    "id" INTEGER NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "url" VARCHAR(2000),
    "body" TEXT,
    "metadescription" VARCHAR(1000) NOT NULL,
    "metakeywords" VARCHAR(1000) NOT NULL,
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
    "subject" VARCHAR(255) NOT NULL,
    "message" TEXT NOT NULL,
    "locked" BOOLEAN DEFAULT 0 NOT NULL,
    "sticky" BOOLEAN DEFAULT 0 NOT NULL,
    "replies" INTEGER DEFAULT 0 NOT NULL,
    "createddate" TIMESTAMP NOT NULL,
    "updateddate" TIMESTAMP NOT NULL,
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
    "title" VARCHAR(255) NOT NULL,
    "type" INTEGER,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- groups 
-- ----------------------------------------------------------------------- 

CREATE TABLE "groups"
(
    "ID" INTEGER NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "backfill_target" INTEGER DEFAULT 1 NOT NULL,
    "first_record" BIGINT DEFAULT 0 NOT NULL,
    "first_record_postdate" TIMESTAMP,
    "last_record" BIGINT DEFAULT 0 NOT NULL,
    "last_record_postdate" TIMESTAMP,
    "last_updated" TIMESTAMP,
    "minfilestoformrelease" INTEGER,
    "active" BOOLEAN DEFAULT 0 NOT NULL,
    "description" VARCHAR(255),
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
    "href" VARCHAR(2000) NOT NULL,
    "title" VARCHAR(2000) NOT NULL,
    "tooltip" VARCHAR(2000) NOT NULL,
    "role" INTEGER NOT NULL,
    "ordinal" INTEGER NOT NULL,
    "menueval" VARCHAR(2000) NOT NULL,
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
    "title" VARCHAR(128) NOT NULL,
    "tagline" VARCHAR(255) NOT NULL,
    "rating" VARCHAR(4) NOT NULL,
    "plot" VARCHAR(255) NOT NULL,
    "year" VARCHAR(4) NOT NULL,
    "genre" VARCHAR(64) NOT NULL,
    "director" VARCHAR(64) NOT NULL,
    "actors" VARCHAR(255) NOT NULL,
    "language" VARCHAR(64) NOT NULL,
    "cover" BOOLEAN DEFAULT 0 NOT NULL,
    "backdrop" BOOLEAN DEFAULT 0 NOT NULL,
    "createddate" TIMESTAMP NOT NULL,
    "updateddate" TIMESTAMP NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "imdbID" ON "movieinfo" ("imdbID");

-- ----------------------------------------------------------------------- 
-- musicinfo 
-- ----------------------------------------------------------------------- 

CREATE TABLE "musicinfo"
(
    "ID" INTEGER NOT NULL,
    "title" VARCHAR(128) NOT NULL,
    "asin" VARCHAR(128),
    "url" VARCHAR(1000),
    "salesrank" INTEGER,
    "artist" VARCHAR(255),
    "publisher" VARCHAR(255),
    "releasedate" TIMESTAMP,
    "review" VARCHAR(2000),
    "year" VARCHAR(4) NOT NULL,
    "genreID" INTEGER,
    "tracks" VARCHAR(2000),
    "cover" BOOLEAN DEFAULT 0 NOT NULL,
    "createddate" TIMESTAMP NOT NULL,
    "updateddate" TIMESTAMP NOT NULL,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- Part_SEQ 
-- ----------------------------------------------------------------------- 

CREATE SEQUENCE "Part_SEQ" MINVALUE 1 INCREMENT 1000 START WITH 1 CACHE 20;

-- ----------------------------------------------------------------------- 
-- partrepair 
-- ----------------------------------------------------------------------- 

CREATE TABLE "partrepair"
(
    "ID" INTEGER NOT NULL,
    "numberID" BIGINT NOT NULL,
    "groupID" INTEGER NOT NULL,
    "attempts" BOOLEAN DEFAULT 0 NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "ix_partrepair_numberID_groupID" ON "partrepair" ("numberID", "groupID");

-- ----------------------------------------------------------------------- 
-- parts 
-- ----------------------------------------------------------------------- 

CREATE TABLE "parts"
(
    "ID" BIGINT NOT NULL,
    "binaryID" INTEGER DEFAULT 0 NOT NULL,
    "messageID" VARCHAR(255) NOT NULL,
    "number" BIGINT DEFAULT 0 NOT NULL,
    "partnumber" INTEGER DEFAULT 0 NOT NULL,
    "size" BIGINT DEFAULT 0 NOT NULL,
    "dateadded" TIMESTAMP,
    PRIMARY KEY ("ID")
);

CREATE INDEX "binaryID" ON "parts" ("binaryID");

CREATE INDEX "ix_parts_dateadded" ON "parts" ("dateadded");

CREATE INDEX "ix_parts_number" ON "parts" ("number");

-- ----------------------------------------------------------------------- 
-- Release_SEQ 
-- ----------------------------------------------------------------------- 

CREATE SEQUENCE "Release_SEQ" MINVALUE 1 INCREMENT 100 START WITH 1 CACHE 20;

-- ----------------------------------------------------------------------- 
-- releasecomment 
-- ----------------------------------------------------------------------- 

CREATE TABLE "releasecomment"
(
    "ID" INTEGER NOT NULL,
    "releaseID" INTEGER NOT NULL,
    "text" VARCHAR(2000) NOT NULL,
    "userID" INTEGER NOT NULL,
    "createddate" TIMESTAMP,
    "host" VARCHAR(15),
    PRIMARY KEY ("ID")
);

CREATE INDEX "ix_releasecomment_releaseID" ON "releasecomment" ("releaseID");

CREATE INDEX "ix_releasecomment_userID" ON "releasecomment" ("userID");

-- ----------------------------------------------------------------------- 
-- releasenfo 
-- ----------------------------------------------------------------------- 

CREATE TABLE "releasenfo"
(
    "ID" BIGINT NOT NULL,
    "releaseID" INTEGER NOT NULL,
    "binaryID" INTEGER NOT NULL,
    "attempts" BOOLEAN DEFAULT 0 NOT NULL,
    "nfo" BYTEA,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "ix_releasenfo_releaseID" ON "releasenfo" ("releaseID");

-- ----------------------------------------------------------------------- 
-- ReleaseNfo_SEQ 
-- ----------------------------------------------------------------------- 

CREATE SEQUENCE "ReleaseNfo_SEQ" MINVALUE 1 INCREMENT 100 START WITH 1 CACHE 20;

-- ----------------------------------------------------------------------- 
-- releaseregex 
-- ----------------------------------------------------------------------- 

CREATE TABLE "releaseregex"
(
    "ID" INTEGER NOT NULL,
    "groupname" VARCHAR(255),
    "regex" VARCHAR(2000) NOT NULL,
    "ordinal" INTEGER NOT NULL,
    "status" INTEGER DEFAULT 1 NOT NULL,
    "description" VARCHAR(1000),
    "categoryID" INTEGER,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- releases 
-- ----------------------------------------------------------------------- 

CREATE TABLE "releases"
(
    "ID" BIGINT NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "searchname" VARCHAR(255) NOT NULL,
    "totalpart" INTEGER DEFAULT 0,
    "groupID" INTEGER DEFAULT 0 NOT NULL,
    "size" BIGINT DEFAULT 0 NOT NULL,
    "postdate" TIMESTAMP,
    "adddate" TIMESTAMP,
    "guid" VARCHAR(50) NOT NULL,
    "fromname" VARCHAR(255),
    "completion" REAL DEFAULT 0 NOT NULL,
    "categoryID" INTEGER DEFAULT 0,
    "regexID" INTEGER DEFAULT 0,
    "rageID" INTEGER,
    "seriesfull" VARCHAR(15),
    "season" VARCHAR(10),
    "episode" VARCHAR(10),
    "tvtitle" VARCHAR(255),
    "tvairdate" TIMESTAMP,
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
    "code" VARCHAR(255) NOT NULL,
    "title" VARCHAR(1000) NOT NULL,
    "strapline" VARCHAR(1000) NOT NULL,
    "metatitle" VARCHAR(1000) NOT NULL,
    "metadescription" VARCHAR(1000) NOT NULL,
    "metakeywords" VARCHAR(1000) NOT NULL,
    "footer" VARCHAR(2000) NOT NULL,
    "email" VARCHAR(1000) NOT NULL,
    "lastupdate" TIMESTAMP NOT NULL,
    "google_adsense_search" VARCHAR(255),
    "google_adsense_sidepanel" VARCHAR(255),
    "google_analytics_acc" VARCHAR(255),
    "google_adsense_acc" VARCHAR(255),
    "siteseed" VARCHAR(50) NOT NULL,
    "tandc" VARCHAR(5000) NOT NULL,
    "registerstatus" INTEGER DEFAULT 0 NOT NULL,
    "style" VARCHAR(50),
    "menuposition" INTEGER DEFAULT 1 NOT NULL,
    "dereferrer_link" VARCHAR(255),
    "nzbpath" VARCHAR(500) NOT NULL,
    "rawretentiondays" INTEGER DEFAULT 3 NOT NULL,
    "attemptgroupbindays" INTEGER DEFAULT 2 NOT NULL,
    "lookuptvrage" INTEGER DEFAULT 1 NOT NULL,
    "lookupimdb" INTEGER DEFAULT 1 NOT NULL,
    "lookupnfo" INTEGER DEFAULT 1 NOT NULL,
    "lookupmusic" INTEGER DEFAULT 1 NOT NULL,
    "lookupgames" INTEGER DEFAULT 1 NOT NULL,
    "amazonpubkey" VARCHAR(255),
    "amazonprivkey" VARCHAR(255),
    "tmdbkey" VARCHAR(255),
    "compressedheaders" INTEGER DEFAULT 0 NOT NULL,
    "maxmssgs" INTEGER DEFAULT 20000 NOT NULL,
    "newgroupscanmethod" INTEGER DEFAULT 0 NOT NULL,
    "newgroupdaystoscan" INTEGER DEFAULT 3 NOT NULL,
    "newgroupmsgstoscan" INTEGER DEFAULT 50000 NOT NULL,
    "storeuserips" INTEGER DEFAULT 0 NOT NULL,
    "minfilestoformrelease" INTEGER DEFAULT 1 NOT NULL,
    "reqidurl" VARCHAR(1000) DEFAULT 'http://allfilled.newznab.com/query.php?t=[GROUP]&reqid=[REQID]' NOT NULL,
    "latestregexurl" VARCHAR(1000) DEFAULT 'http://www.newznab.com/getregex.php' NOT NULL,
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
    "releasetitle" VARCHAR(255) NOT NULL,
    "description" VARCHAR(10000),
    "genre" VARCHAR(64),
    "country" VARCHAR(2),
    "imgdata" BYTEA,
    "createddate" TIMESTAMP,
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
    "createddate" TIMESTAMP NOT NULL,
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
    "createddate" TIMESTAMP NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE UNIQUE INDEX "ix_userexcat_usercat" ON "userexcat" ("userID", "categoryID");

-- ----------------------------------------------------------------------- 
-- userinvite 
-- ----------------------------------------------------------------------- 

CREATE TABLE "userinvite"
(
    "ID" INTEGER NOT NULL,
    "guid" VARCHAR(50) NOT NULL,
    "userID" INTEGER NOT NULL,
    "createddate" TIMESTAMP NOT NULL,
    PRIMARY KEY ("ID")
);

-- ----------------------------------------------------------------------- 
-- users 
-- ----------------------------------------------------------------------- 

CREATE TABLE "users"
(
    "ID" INTEGER NOT NULL,
    "username" VARCHAR(50) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "role" INTEGER DEFAULT 1 NOT NULL,
    "host" VARCHAR(15),
    "grabs" INTEGER DEFAULT 0 NOT NULL,
    "rsstoken" VARCHAR(32) NOT NULL,
    "createddate" TIMESTAMP NOT NULL,
    "resetguid" VARCHAR(50),
    "lastlogin" TIMESTAMP,
    "apiaccess" TIMESTAMP,
    "invites" INTEGER DEFAULT 0 NOT NULL,
    "invitedby" INTEGER,
    "movieview" INTEGER DEFAULT 1 NOT NULL,
    "musicview" INTEGER DEFAULT 1 NOT NULL,
    "consoleview" INTEGER DEFAULT 1 NOT NULL,
    "userseed" VARCHAR(50) NOT NULL,
    PRIMARY KEY ("ID")
);

