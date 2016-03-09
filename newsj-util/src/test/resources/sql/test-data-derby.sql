-- Derby Test Data
Insert into SITE (ID,CODE,TITLE,STRAPLINE,METATITLE,METADESCRIPTION,METAKEYWORDS,FOOTER,EMAIL,LASTUPDATE,
                  GOOGLE_ADSENSE_SEARCH,GOOGLE_ADSENSE_SIDEPANEL,GOOGLE_ANALYTICS_ACC,GOOGLE_ADSENSE_ACC,SITESEED,TANDC,
                  REGISTERSTATUS,STYLE,MENUPOSITION,DEREFERRER_LINK,NZBPATH,RAWRETENTIONDAYS,ATTEMPTGROUPBINDAYS,
                  LOOKUPTVRAGE,LOOKUPIMDB,LOOKUPNFO,LOOKUPMUSIC,LOOKUPGAMES,AMAZONPUBKEY,AMAZONPRIVKEY,TMDBKEY,
                  COMPRESSEDHEADERS,MAXMSSGS,NEWGROUPSCANMETHOD,NEWGROUPDAYSTOSCAN,NEWGROUPMSGSTOSCAN,STOREUSERIPS,
                  MINFILESTOFORMRELEASE,REQIDURL,LATESTREGEXURL,LATESTREGEXREVISION,RELEASERETENTIONDAYS,
                  CHECKPASSWORDEDRAR,SHOWPASSWORDEDRELEASE)
values (1,'newznab','Newznab','A great usenet indexer','Newznab - A great usenet indexer',
        'Newznab a usenet indexing website with community features','usenet,nzbs,newznab,cms,community',
        'newznab is designed to be a simple usenet indexing site that is easy to configure as a community website.',
        'info@newznab.com',CURRENT_TIMESTAMP,null,null,null,null,'99bc2e9d0b8c0cd301fe48100f2537cf',
        '<p>All information within this database is indexed by an automated process, without any human intervention. It is obtained from global Usenet newsgroups over which this site has no control. We cannot prevent that you might find obscene or objectionable material by using this service. If you do come across obscene, incorrect or objectionable results, let us know by using the contact form.</p>',
        0,null,1,'http://www.dereferer.ws/?','/your/path/to/nzbs/',2,2,1,1,1,1,1,'AKIAIPDNG5EU7LB4AD3Q',
        'B58mVwyj+T/MEucxWugJ3GQ0CcW2kQq16qq/1WpS','9a4e16adddcd1e86da19bcaf5ff3c2a3',0,20000,0,3,50000,0,1,
        'http://allfilled.newznab.com/query.php?t=[GROUP]&reqid=[REQID]','http://www.newznab.com/getregex.php',0,0,0,0);


Insert into GROUPS (ID,NAME_,BACKFILL_TARGET,FIRST_RECORD,FIRST_RECORD_POSTDATE,LAST_RECORD,LAST_RECORD_POSTDATE,LAST_UPDATED,MINFILESTOFORMRELEASE,ACTIVE,DESCRIPTION)
values (0,'alt.binaries.test',1,0,null,0,null,null,null,TRUE,'This group contains Test Binaries.');

INSERT INTO USERS(ID, USERNAME, EMAIL, PASSWORD, ROLE, HOST, GRABS, RSSTOKEN, CREATEDDATE, RESETGUID, LASTLOGIN,
                  APIACCESS, INVITES, INVITEDBY, MOVIEVIEW, MUSICVIEW, CONSOLEVIEW, USERSEED)
VALUES (0,'testadmin','testadmin@longfalcon.net','1000:2654070672c958a67428c635f39fad80e7fc7e55ef2c69b5:a9b611f18059fc12c5d4ae3099550d10235ae6c6b4857e72',
        2,NULL,0,'f19208a76db9dd73c743b5a88a224dac',CURRENT_TIMESTAMP,NULL,CURRENT_TIMESTAMP,NULL,1,NULL,0,0,0,'988ad7dd5852fb45d61ee7b7e14d9a91');

INSERT INTO USERINVITE(ID, GUID, USERID, CREATEDDATE) VALUES (1,'abc',0,CURRENT_TIMESTAMP);
