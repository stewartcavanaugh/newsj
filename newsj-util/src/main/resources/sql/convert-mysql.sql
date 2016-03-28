alter table binaries ENGINE = InnoDB;
alter table binaryblacklist ENGINE = InnoDB;
alter table category  ENGINE = InnoDB;
alter table consoleinfo  ENGINE = InnoDB;
alter table content  ENGINE = InnoDB;
alter table forumpost ENGINE = InnoDB;
alter table genres ENGINE = InnoDB;
alter table groups ENGINE = InnoDB;
alter table menu ENGINE = InnoDB;
alter table movieinfo ENGINE = InnoDB;
alter table musicinfo ENGINE = InnoDB;
alter table partrepair ENGINE = InnoDB;
alter table parts ENGINE = InnoDB;
alter table releasecomment ENGINE = InnoDB;
alter table releasenfo ENGINE = InnoDB;
alter table releaseregex ENGINE = InnoDB;
alter table releases ENGINE = InnoDB;
alter table site ENGINE = InnoDB;
alter table tvrage ENGINE = InnoDB;
alter table usercart ENGINE = InnoDB;
alter table userexcat ENGINE = InnoDB;
alter table userinvite ENGINE = InnoDB;
alter table users ENGINE = InnoDB;

-- better regexes
INSERT INTO `releaseregex` (`ID`, `groupname`, `regex`, `ordinal`, `status`, `description`, `categoryID`) VALUES
  (12, 'alt.binaries.movies.divx', '/^(?P<name>.*?)\\s==\\s\\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (13, NULL, '/^\\[.*?(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 50, 0, '', NULL),
  (15, NULL, '/^\\((?P<name>.*)\\).*?\\[(?P<parts>\\d{2,3}\\/\\d{2,3})/i', 52, 0, '', NULL),
  (21, 'alt.binaries.games.wii', '/^GRAAG.*?\\[(\\d{2,4}\\/\\d{2,4})\\] \\- \\"(?P<name>.*?)(\\(graag gedaan\\)|\\[graag gedaan\\]|)\\.(?:vol|par2|part)/i', 2, 1, '', 1030),
  (31, 'alt.binaries.nintendo.ds', '/^GRAAG GEDAAN.*?\\[(?P<parts>\\d{1,4}\\/\\d{1,4})\\] \\- \\"(?P<name>.*?)\\.(part|nzb|vol|par|sfv|rar).*?\\"/i', 2, 1, '', NULL),
  (34, 'alt.binaries.nintendo.ds', '/^(?P<name>NDS.*?) \\[(?P<parts>\\d{1,4}\\/\\d{1,4})\\]/i', 2, 1, '', 1010),
  (36, 'alt.binaries.nintendo.ds', '/^\\"\\d{4} \\- (?P<name>.*?)\\./i', 2, 1, '', 1010),
  (40, 'alt.binaries.x264', '/^\\"(?P<name>.*?264.*?)\\.(vol|par|rar|nfo)/i', 3, 1, '', NULL),
  (44, 'alt.binaries.movies.divx', '/^\\[.*?\\[FULL\\]-\\[ (?P<name>.*?) \\]-\\[(?P<parts>\\d{2,4}\\~\\d{2,4})/i', 2, 1, '', NULL),
  (47, 'alt.binaries.games.nintendods', '/^\\[www.ABGX.net.*?\\]\\-\\[(?P<name>.*?)\\]\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 1010),
  (61, 'alt.binaries.wmvhd', '/^.*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"(?P<name>.*?WMVHD.*?)\\.(vol|par|rar|sfv|nzb|r\\d{1,3})/i', 3, 1, '', NULL),
  (75, 'alt.binaries.wmvhd', '/^#HD-WMV.*?Presents(?P<name>.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 3, 1, '', NULL),
  (94, 'alt.binaries.hdtv.x264', '/^.*?www\\..*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"(?P<name>.*?264.*?)\\.(vol|par|rar|nfo|sfv)/i', 3, 1, '', 2040),
  (105, 'alt.binaries.boneless', '/^.*?usenet4all.*? - (?P<name>.*?(XVID|DIVX).*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 2030),
  (110, 'alt.binaries.inner-sanctum', '/^(?P<name>.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (114, 'alt.binaries.mpeg.video.music', '/^Just a dump.*?\\"(?P<name>.*?)\\.(vol|part|rar|sfv|nfo|mpg|m2v|vob)/i', 2, 1, '', NULL),
  (123, 'alt.binaries.boneless', '/^.*?usenet4all.*? - (repost\\.|)(?P<name>.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 2040),
  (124, 'alt.binaries.boneless', '/^.*?usenet4all.*? - (?P<name>.*?(NTSC|PAL|DVDR\\-).*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (127, 'alt.binaries.inner-sanctum', '/^\\[\\d{5}\\]\\-\\[FULL\\]\\-\\[.*?sanctum.*?\\]\\-\\[(?P<name>.*?)\\]\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (147, 'alt.binaries.warez.smartphone', '/^(?P<name>.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (148, 'alt.binaries.warez.smartphone', '/^(\\(|)(?P<name>.*?Iphone.*?)(\\) \\[|)(\\- REPOST\\) \\[|)(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (150, 'alt.binaries.games.xbox', '/^\\[\\d{5}\\] \\- \\(\\#a.*?\\) \\- (?P<name>.*?XBOX.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 1040),
  (153, 'alt.binaries.boneless', '/^\\!\\!www\\.usenet.*? \\- (?P<name>.*?(SOSISO|LIBERTY|WW|SKIDROW|VITALITY|GOW|FASiSO|Trigger|RELOADED|FASDOX|Razor1911|SPYRAL|FLT|PC\\-ELITE|TINY\\-ISO|DLC\\-ARM|PROPHET).*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 4010),
  (154, 'alt.binaries.boneless', '/^.*?usenet4all.*? - (?P<name>.*?264.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 3, 1, '', 2040),
  (160, 'alt.binaries.hdtv.x264', '/^\\(\\=HDVISUALS\\=\\) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"\\=HDVISUALS\\=(?P<name>.*?\\d{3,4}p)/i', 3, 1, '', NULL),
  (166, 'alt.binaries.multimedia', '/^\\[\\#a.*?\\] \\- (?P<name>.*?(itouch|ipod|ipad).*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (169, 'alt.binaries.warez', '/^(?P<name>.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (170, 'alt.binaries.warez', '/^\\[(?P<name>.*?v\\d{1,3}\\.\\d{1,3}.*?)\\] \\- \\".*?\\" \\- \\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 4010),
  (173, 'alt.binaries.warez', '/^(?P<name>.*?xvid.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (174, 'alt.binaries.warez', '/^\\(\\[0-Day\\] (?P<name>.*?v\\d{1,3}\\..*?)\\) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 4010),
  (176, 'alt.binaries.warez', '/^\\[\\"(?P<name>.*?)\\.(vol|par|rar|Sfv|nfo).*?\\"\\] \\- \\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (177, 'alt.binaries.warez', '/^(\\[|)(?P<name>.*?)(\\]|) \\- \\((?P<parts>\\d{1,3}\\/\\d{1,3}).*?\\"/i', 2, 1, '', 4010),
  (178, 'alt.binaries.warez', '/^\\((\\[0-DAY\\]|)(?P<name>.*?)\\) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 4010),
  (179, 'alt.binaries.warez', '/^\\[\\d{5}\\]\\-\\[\\#.*?\\]\\-\\[FULL\\]\\-\\[(?P<name>.*?)\\]\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 4010),
  (186, 'alt.binaries.movies.xvid', '/^(RE\\:|)(?P<name>.*?) \\=\\= \\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 2030),
  (188, 'alt.binaries.movies.xvid', '/^(?P<name>.*?DVDRIP_XVID.*?)/i', 2, 1, '', NULL),
  (194, 'alt.binaries.hdtv.x264', '/^(?P<name>.*?\\.x264\\..*?) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 3, 1, '', NULL),
  (208, 'alt.binaries.movies.divx', '/^(<USENET\\-HUNTERS\\.NET> \\- |)(?P<name>.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 4, 1, '', NULL),
  (210, 'alt.binaries.warez', '/^(<appz-only> |\\>\\[kere\\.ws\\]\\< \\- \\"|\\<(apps|app|warez)\\> \\- \\"|\\"|)(?P<name>.*?symbianos.*?)(\\.|)(vol|rar|par|nfo|sfv|nzb|)/i', 2, 1, '', 4040),
  (215, 'alt.binaries.sounds.mp3.complete_cd', '/^\\[\\d{5}\\]\\-\\[FULL\\]\\-\\[\\#.*?\\]\\-\\[(?P<name>.*?)\\]\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (220, 'alt.binaries.sounds.mp3.complete_cd', '/^BOARDREQUEST.*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"(?P<name>.*?)\\.(vol|par|rar|nzb|sfv|zip)/i', 2, 1, '', NULL),
  (221, 'alt.binaries.sounds.mp3.complete_cd', '/^.*?NMR.*?(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"(?P<name>.*? \\- .*?) \\-/i', 2, 1, '', NULL),
  (243, 'alt.binaries.hdtv.x264', '/^\\[.*?\\]\\-\\[#a\\.b.*?\\]\\-\\[(?P<name>.*?S\\d{2}.*?264.*?)\\]\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 5040),
  (251, 'alt.binaries.games.wii', '/^\\(VBP (?P<name>.*?)\\) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 3, 1, '', 1030),
  (252, 'alt.binaries.sounds.mp3.classical', '/^(?P<name>.*? \\- .*?\\)) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\"/i', 2, 1, '', NULL),
  (257, 'alt.binaries.boneless', '/^\\!\\!www.*?\\- (?P<name>.*?(720|1080).*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 2050),
  (258, 'alt.binaries.games.nintendods', '/^OLL.*?\\"\\d{4} (?P<name>\\w.*?)\\./i', 2, 1, '', 1010),
  (264, 'alt.binaries.ath', '/^\\<kere.*?\\> \\- ID \\- \\d{1,20} \\- (?P<name>.*?\\d{4}\\-.*?) \\- yenc/i', 2, 1, '', 3010),
  (265, 'alt.binaries.ftn', '/^\\(FTN2DAY.NL\\-\\d{1,6}\\-(?P<name>.*?)\\) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]./i', 2, 1, '', 4030),
  (279, 'alt.binaries.erotica', '/^\\<.*?\\>\\-\\<UH.*?\\>\\-\\<\\d{1,7}\\>\\-\\<(?P<name>.*?)\\-U4ALL \\>\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', NULL),
  (281, 'alt.binaries.mac', '/^\\((?P<parts>\\d{1,3}\\/\\d{1,3})\\) \\"(?P<name>.*?)\\.(vol|par|rar|nfo|sfv|par2|nzb)/i', 2, 1, '', 4030),
  (283, 'alt.binaries.e-book', '/^ATTN\\: .*? \\- \\"(?P<name>.*?)\\(/i', 2, 1, '', 7020),
  (284, 'alt.binaries.e-book', '/^\\- (?P<name>.*?)( \\- |)\\((html|mobi|lit|rtf|lrf|epub)/i', 2, 1, '', 7020),
  (285, 'alt.binaries.e-book', '/^\\[(?P<name>.*?(video|dvdrip|xvid).*?)\\] \\- \\((?P<parts>\\d{1,3}\\/\\d{1,3})\\)/i', 2, 1, '', 7010),
  (287, 'alt.binaries.erotica', '/^\\(.*?\\) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"\\d{4}\\-\\d{2}\\-\\d{2}(?P<name>.*?)\\.(vol|par|rar|nfo|sfv|par2|nzb)/i', 2, 1, '', NULL),
  (288, 'alt.binaries.erotica', '/^(?P<name>.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 3, 1, '', NULL),
  (289, 'alt.binaries.erotica', '/^\\<.*?\\> \\- ID \\- \\d{1,10} \\- (?P<name>.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', NULL),
  (292, 'alt.binaries.sounds.mp3.complete_cd', '/^\\[\\d{1,7}\\-\\[\\#altbin.*?\\]\\-\\[(?P<name>.*?)\\]\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', NULL),
  (299, 'alt.binaries.mac', '/^\\[Phat.*?\\] \\((?P<parts>\\d{1,3}\\/\\d{1,3})\\) \\- \\"(?P<name>.*?)\\.(vol|par|rar|nfo|sfv|sitx)/i', 2, 1, '', 4030),
  (300, 'alt.binaries.mac', '/^(?P<name>.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', 4030),
  (304, 'alt.binaries.erotica.divx', '/^(Re\\:|)(?P<name>.*?xvid.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', 6030),
  (316, 'alt.binaries.teevee', '/^(RE\\: |)\\[.*?\\](\\-\\[FULL\\]|)\\-\\[\\#.*?\\](\\-\\[FULL\\]|)\\-\\[(?P<name>.*?(UEFA|MLB|ESPN|WWE|MMA|UFC|TNA|EPL|NASCAR|NBA|NFL|NHL|NRL|PGA|SUPER LEAGUE|FORMULA|FIFA|NETBALL|MOTOGP).*?)\\]( |)\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/', 2, 1, '', 5060),
  (317, 'alt.binaries.tvseries', '/^(RE\\:|)(\\[.*?\\])(\\-\\[nzbm-reqs\\]\\-|)(\\-\\[FULL\\]|)(\\-\\[\\#.*?\\]|)(\\-\\[FULL\\]|)(\\-\\[|)(?P<name>.*?s\\d{2}.*?(dvdrip|xvid).*?)(\\]|)( |)(\\-\\[|\\()(?P<parts>\\d{1,3}\\/\\d{1,3})(\\]|\\)).*?/i', 3, 1, '', 5030),
  (319, 'alt.binaries.tvseries', '/^\\((?P<parts>\\d{1,3}\\/\\d{1,3})\\)( |)\\"(?P<name>.*?)\\./i', 3, 1, '', 5030),
  (341, 'alt.binaries.boneless', '/^\\[www.*?\\] Cor316.*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"(?P<name>.*?\\-.*?)\\./i', 3, 1, '', 3010),
  (343, 'alt.binaries.warez', '/^(RE\\: |)\\[.*?\\](\\-\\[FULL\\]|)\\-\\[\\#.*?\\](\\-\\[FULL\\]|)\\-\\[(?P<name>.*?(IPHONE|ITOUCH|ANDROID|COREPDA).*?)\\]( |)\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', 4040),
  (344, 'alt.binaries.warez', '/^(RE\\: |)\\[.*?\\](\\-\\[FULL\\]|)\\-\\[\\#.*?\\](\\-\\[FULL\\]|)\\-\\[(?P<name>.*?)\\]( |)\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 3, 1, '', NULL),
  (345, 'alt.binaries.sounds.mp3.complete_cd', '/^(RE\\: |)\\[.*?\\](\\-\\[FULL\\]|)\\-\\[\\#.*?\\](\\-\\[FULL\\]|)\\-\\[(?P<name>.*?)\\]( |)\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', NULL),
  (351, 'alt.binaries.games.wii', '/^(RE\\: |)\\[.*?\\](\\-\\[FULL\\]|)\\-\\[\\#.*?\\](\\-\\[FULL\\]|)\\-\\[(?P<name>.*?(WiiWare|DLC|Console|VLC).*?)\\]( |)\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', 1060),
  (353, 'alt.binaries.e-book.technical', '/^.*?\\- \\"(?P<name>.*?)(WW|)(\\.pdf|\\.chm)/i', 3, 1, '', 7020),
  (357, 'alt.binaries.e-book', '/^(\\[|)(?P<name>.*?pdf.*?)(\\]|)( |)(\\-|)( |)\\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 3, 1, '', 7020),
  (358, 'alt.binaries.erotica', '/^\\<\\#a.*?\\>\\-\\<UHQ.*?\\>\\-\\<(?P<reqid>\\d{1,7}.*?)>\\-\\<(?P<name>.*?)\\>\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', NULL),
  (363, 'alt.binaries.games.xbox360', '/^\\[REQ\\.(?P<reqid>\\d{1,7}.*?)\\.(?P<name>.*?)\\]( |)\\-( |)\\[.*?\\]( |)\\-( |)\\[.*?\\]( |)\\-( |)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i ', 2, 1, '', NULL),
  (371, 'alt.binaries.ath', '/^\\<kere.*?ID \\-.*?\\- (?P<name>.*?)\\- yenc/i', 1, 1, '', NULL),
  (375, 'alt.binaries.test', '/^(There are some sad ppl here on usenet..Watch out for crap reposters. Join us at u4all.eu |)(?<name>.*?BluRay.*?x264.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/', 2, 1, '', 2040),
  (376, 'alt.binaries.test', '/^(There are some sad ppl here on usenet..Watch out for crap reposters. Join us at u4all.eu |)(?<name>.*?XviD.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/', 3, 1, '', 2030),
  (394, 'alt.binaries.hdtv.x264', '/^\\(Guido24\\) \\[\\d{1,8}\\] \\- \\"(?P<name>.*?264.*?)\\.(vol|par|rar|sfv|nfo|nzb)/i', 2, 1, '', 5040),
  (398, 'alt.binaries.movies.divx', '/^(?P<name>.*?) uploaded/i', 2, 1, '', NULL),
  (399, 'alt.binaries.x264', '/^\\"(?P<name>.*?BD 25.*?HMJ)\\./i', 2, 1, '', 2050),
  (402, 'alt.binaries.multimedia', '/^\\[LAAV\\]\\-\\[MP3\\]\\-\\[(?P<name>.*?)\\]\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 3010),
  (403, 'alt.binaries.console.ps3', '/^\\[(?P<reqid>\\d{3,7}.*?)\\]\\-\\[\\#.*?\\]\\-\\[(?P<name>.*?)\\]\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 1080),
  (406, 'alt.binaries.scary.exe.files', '/^\\[u4all.*?\\](?P<name>.*?(xvid|x264|bluray).*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/', 2, 1, '', NULL),
  (408, 'alt.binaries.warez.smartphone', '/^\\((?P<name>.*?)(www\\.nzblistings\\.com|)\\)( |)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 6, 1, '', NULL),
  (409, 'alt.binaries.ath', '/^\\<kere.*?\\d{1,20}(\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]|) \\- (\\"|)(?P<name>.*?Cracked.*?)/i', 2, 1, '', 4010),
  (413, 'alt.binaries.mp3', '/^Board.*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"(?P<name>.*?)\\./i', 2, 1, '', NULL),
  (416, 'alt.binaries.country.mp3', '/^(?P<name>.*?)\\- yenc \\"/i', 2, 1, '', NULL),
  (417, 'alt.binaries.country.mp3', '/^(?P<name>VA.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', NULL),
  (418, 'alt.binaries.country.mp3', '/^(RE\\:|)(ATTN.*?\\>|)(ATTN|)(ART.*?\\>|)(ARTWORK|)(?P<name>.*?\\d{4}.*?)(\\(|\\[)(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (423, 'alt.binaries.country.mp3', '/^(?P<name>.*? \\- \\d{4} \\- .*?) \\"/i', 2, 1, '', NULL),
  (424, 'alt.binaries.country.mp3', '/^(?P<name>.*?\\-.*?\\(.*?\\)\\-\\d{4})/i', 2, 1, '', NULL),
  (428, 'alt.binaries.mp3', '/^\\(Roy.*?\\) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"(?P<name>.*?)\\./', 2, 1, '', NULL),
  (430, 'alt.binaries.mp3', '/^(?P<name>VA.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (433, 'alt.binaries.documentaries', '/^(\\[|)(?P<name>.*?xvid.*?)/i', 3, 1, '', NULL),
  (437, 'alt.binaries.console.ps3', '/^(\\(|\\[|)( |)(?P<reqid>\\d{2,6}).*?(\\[|)(?P<parts>\\d{1,3}\\/\\d{1,3}).*?\\"(?P<name>.*?)\\./i', 2, 1, '', 1080),
  (438, 'alt.binaries.e-book.technical', '/^.*?(\\[(?P<parts>\\d{1,3}\\/\\d{1,3})|).*?\\"(?P<name>.*(?!CIDEE).*?)\\.pdf/', 2, 1, '', NULL),
  (439, 'alt.binaries.ebook', '/^(ATTN:.*?\\"|As requested \\>\\> \\d{1,3}\\/\\d{1,3}.*?\\")(?P<name>.*?)\\.(pdb|pdf|rar)/', 2, 1, '', 7020),
  (441, 'alt.binaries.sounds.mp3.jazz.vocals', '/^.*?"(?P<name>.*?)\\- \\d{1,3} \\-.*?\\./i', 2, 1, '', NULL),
  (442, 'alt.binaries.sounds.mp3.1990s', '/^(?P<name>.*?BR\\)) (\\- |)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (443, 'alt.binaries.sounds.mp3.1980s', '/^(?P<name>.*?) (\\- |)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (444, 'alt.binaries.sounds.mp3.1950s', '/^\\(nmr.*?\\"(?P<name>.*?)\\-/i', 2, 1, '', NULL),
  (446, 'alt.binaries.sounds.mp3.rock', '/^(?P<name>.*?\\-.*?) \\[(?P<parts>\\d{1,3}\\/( |)\\d{1,3})/i', 2, 1, '', NULL),
  (447, 'alt.binaries.sounds.mp3.progressive-country', '/^(?P<name>.*?)(\\-\\[\\d{1,3}\\/\\d{1,3}\\] \\-|) \\"/', 2, 1, '', NULL),
  (448, 'alt.binaries.scary.exe.files', '/^\\[u(?:senet)?4all.*?(?P<name>[\\w\\(\\)]+(?:\\.|_)[\\w\\(\\)\\.\\-_]+\\-[\\w&]+).*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (454, 'alt.binaries.sounds.mp3.complete_cd', '/^\\[.*?\\]\\-\\[\\#.*?\\]\\-\\[(?P<name>.*?)\\]\\-\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (457, 'alt.binaries.country.mp3', '/^(?P<name>.*? \\- .*?)(\\[\\d{1,3}(\\/|of)\\d{1,3}\\] |)(\\-|) \\"/i', 2, 1, '', NULL),
  (462, 'alt.binaries.e-book.flood', '/^.*?\\[.*?\\d{1,3}\\/\\d{1,3}\\] \\- \\"(.*?\\/|)(?P<name>.*?)(\\[.*?\\]|\\(.*?\\)|)\\.(pdb|rar|par|htm|html|prc|lit|epub|lrf|txt|pdf|rtf|doc|chf|vol|nfo|chm)/i', 6, 1, '', 7020),
  (463, 'alt.binaries.e-book.flood', '/^\\-( Rob Kidd \\- \\[|)(?P<name>.*?)(\\(.*?\\)|)\\.(rar|nfo|zip|pdf)/i', 3, 1, '', 7020),
  (468, 'alt.binaries.games.wii', '/^\\[(?P<reqid>\\d{3,5})\\]\\-\\[abgx\\.net\\]\\-\\[(.*?)\\]\\-\\[FULL\\] \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 2, 1, '', NULL),
  (470, 'alt.binaries.games.xbox360', '/^\\#HD\\-.*? Presents (?P<name>.*?WMV.*?) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (474, 'alt.binaries.nintendo.ds', '/^\\d{3,5} \\- (?P<name>.*?) \\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 1010),
  (480, 'alt.binaries.e-book', '/^\\[(?P<name>.*?)\\] \\- \\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 3, 1, '', 7020),
  (481, 'alt.binaries.e-book.technical', '/^(?P<name>.*?) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\"/i', 3, 1, '', 7020),
  (482, 'alt.binaries.sounds.mp3.progressive-country', '/^(?P<name>.*? \\- .*?) \\- File (?P<parts>\\d{1,3} of \\d{1,3})/i', 3, 1, '', NULL),
  (485, 'alt.binaries.sounds.mp3.complete_cd', '/^(?P<name>\\w.*? \\- .*?) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 3, 1, '', NULL),
  (487, 'alt.binaries.mp3.audiobooks', '/^\\"(?P<name>.*? \\- .*?)(D\\d{1,3} of \\d{1,3}|\\d{1,3} of \\d{1,3})\\./i', 3, 1, '', NULL),
  (490, 'alt.binaries.mp3.audiobooks', '/^(attn|REQ|RE: |nmr (\\- |)|REPOST |)(?P<name>.*?)( \\- |)(\\[|)\\d{1,3}(of| of )\\d{1,3}\\./i', 3, 1, '', NULL),
  (492, 'alt.binaries.teevee', '/^\\[.*?(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (495, 'alt.binaries.games.wii', '/^\\[.*?(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (496, 'alt.binaries.games.xbox', '/^\\[.*?(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', 1040),
  (497, 'alt.binaries.games.xbox360', '/^\\[.*?(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (498, 'alt.binaries.hdtv.x264', '/^\\[.*?(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (500, 'alt.binaries.movies.divx', '/^\\[.*?(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (501, 'alt.binaries.movies.erotica', '/^\\[.*?(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (502, 'alt.binaries.mp3', '/^\\[.*?(:|)(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (503, 'alt.binaries.console.ps3', '/^\\((?P<name>.*)\\).*?\\[(?P<parts>\\d{2,3}\\/\\d{2,3})/i', 3, 1, '', 1080),
  (507, 'alt.binaries.console.ps3', '/^\\[(?P<reqid>\\d{3,6})\\](\\-| |)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', 1080),
  (511, 'alt.binaries.x264', '/^\\<\\<.*?\\<\\< (?P<name>.*?x264.*?) \\>\\> \\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 3, 1, '', NULL),
  (515, 'alt.binaries.erotica', ' /^\\[(?P<reqid>\\d{4,6}).*erotica.*\\[(?P<parts>\\d{1,4}\\/\\d{1,4})/i', 1, 1, '', NULL),
  (519, 'alt.binaries.warez', '/^(teendezire\\.com_|)(?P<name>.*?^(?!.*(xxx|dvdrip).*).*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (521, 'alt.binaries.sounds.mp3.complete_cd', '/^\\( (?P<name>.*?) \\) \\(.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (523, 'alt.binaries.warez', '/^\\"(?P<name>.*?\\-\\w.*?)\\./i', 2, 0, '', 4010),
  (525, 'alt.binaries.teevee', '/^(\\[|)(?P<name>.*?)(\\]|) (\\- |)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- \\".*?xvid.*?\\"/i', 2, 1, '', 5030),
  (526, 'alt.binaries.cores', '/^ftn.*? \\- (?P<name>.*?) \\"/i', 1, 1, '', 4050),
  (527, 'alt.binaries.cd.image', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (528, 'alt.binaries.console.ps3', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (529, 'alt.binaries.cores', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (530, 'alt.binaries.documentaries', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (531, 'alt.binaries.games.xbox360', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (532, 'alt.binaries.hdtv', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|req\\.\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (533, 'alt.binaries.mp3', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (535, 'alt.binaries.multimedia.cartoons', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (536, 'alt.binaries.multimedia.disney', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_\\'')[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (537, 'alt.binaries.sony.psp', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (538, 'alt.binaries.sounds.mp3', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (539, 'alt.binaries.sounds.mp3.complete_cd', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 5, 1, '', NULL),
  (540, 'alt.binaries.sounds.mp3.emo', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (541, 'alt.binaries.teevee', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (542, 'alt.binaries.tv', '	/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (543, 'alt.binaries.tvseries', '	/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (544, 'alt.binaries.warez', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (545, 'alt.binaries.warez.ibm-pc.0-day', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (546, 'alt.binaries.cd.image', '/^\\[([\\dx]{3,6}|mp3|flood).*\\[(?P<name>(?!repost|sample|extra par|full|xbox|psp|abgx)[^\\]]*)\\].*?\\[(?P<parts>\\d{1,4}(?:\\/|~)\\d{1,4})/i', 1, 1, '', NULL),
  (547, 'alt.binaries.wmvhd', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (548, 'alt.binaries.warez', '/^\\[([\\dx]{3,6}|mp3|flood).*\\[(?P<name>(?!repost|sample|extra par|full|xbox|psp|abgx)[^\\]]*)\\].*?\\[(?P<parts>\\d{1,4}(?:\\/|~)\\d{1,4})/i', 1, 1, '', NULL),
  (549, 'alt.binaries.teevee', '/^\\[([\\dx]{3,6}|mp3|flood).*\\[(?P<name>(?!repost|sample|extra par|full|xbox|psp|abgx)[^\\]]*)\\].*?\\[(?P<parts>\\d{1,4}(?:\\/|~)\\d{1,4})/i', 1, 1, '', NULL),
  (550, 'alt.binaries.hdtv', '/^\\[([\\dx]{3,6}|mp3|flood).*\\[(?P<name>(?!repost|sample|extra par|full|xbox|psp|abgx)[^\\]]*)\\].*?\\[(?P<parts>\\d{1,4}(?:\\/|~)\\d{1,4})/i', 1, 1, '', NULL),
  (551, 'alt.binaries.hdtv.x264', '/^\\[([\\dx]{3,6}|mp3|flood).*\\[(?P<name>(?!repost|sample|extra par|full|xbox|psp|abgx)[^\\]]*)\\].*?\\[(?P<parts>\\d{1,4}(?:\\/|~)\\d{1,4})/i', 2, 1, '', NULL),
  (552, 'alt.binaries.multimedia', '/^\\[([\\dx]{3,6}|mp3|flood).*\\[(?P<name>(?!repost|sample|extra par|full|xbox|psp|abgx|subs)[^\\]].*?(xvid|x264|dvdr).*?)\\].*?\\[(?P<parts>\\d{1,4}(?:\\/|~)\\d{1,4})/i', 1, 1, '', NULL),
  (553, 'alt.binaries.erotica', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (554, 'alt.binaries.games', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (555, 'alt.binaries.games.nintendods', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (557, 'alt.binaries.games.wii', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (558, 'alt.binaries.games.xbox', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (559, 'alt.binaries.hdtv.x264', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (560, 'alt.binaries.moovee', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (561, 'alt.binaries.movies.xvid', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (562, 'alt.binaries.mpeg.video.music', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (563, 'alt.binaries.x', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (566, 'alt.binaries.x264', '/^#alt\\.binaries.*?: (?P<name>[A-Z0-9\\.\\-_\\(\\)]+\\-[A-Z0-9\\-]+)/i', 1, 1, '', NULL),
  (567, 'alt.binaries.cd.image', '/^(?:\\(|\\[)?(?P<reqid>\\d{3,6}).*?altbin.*(?:\\[|\\()(?P<parts>\\d{1,3}[\\/~]\\d{1,3})/i', 1, 1, '', 4020),
  (568, 'alt.binaries.games.wii', '/^(?:\\(|\\[)?(?:REQ\\.)?(?P<reqid>\\d{3,6}).*?(?:\\[|\\()(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (569, 'alt.binaries.b4e', '/^\\"(?P<name>.*\\-.*?)\\.(pdf|vol|par|rar|sfv|nfo|nzb)/i', 1, 1, '', NULL),
  (570, 'alt.binaries.cd.image', '/^\\"(?P<name>.*?)\\.(vol|par|rar|sfv|nzb|nfo)/i', 1, 1, '', 4010),
  (571, 'alt.binaries.classic.tv.shows', '/^(RE:|)(?P<name>.*?) (\\- |)\\[(?P<parts>\\d{1,3}( of |\\/)\\d{1,3})/i', 1, 1, '', NULL),
  (572, 'alt.binaries.comics.dcp', '/^0-day.*?\\"(?P<name>.*?)\\.cb.*?/i', 1, 1, '', NULL),
  (573, 'alt.binaries.documentaries', '/^\\((?P<parts>\\d{1,3}\\/\\d{1,3})\\) \\"(?P<name>.*?)\\./i', 1, 1, '', NULL),
  (574, 'alt.binaries.ftn', '/^(?P<name>.*?\\(PC GAMES\\)) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (575, 'alt.binaries.games.xbox360', '/^\\[(?P<reqid>\\d{4,6})\\].*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (578, 'alt.binaries.nintendo.ds', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (579, 'alt.binaries.games', '/^(?P<name>.*?wii\\-.*?) /i', 1, 1, '', NULL),
  (581, 'alt.binaries.movies', '/^(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+) [a-z0-9\\.\\-_\\+]+$/i', 1, 1, '', NULL),
  (582, 'alt.binaries.movies', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 0, 0, '', NULL),
  (583, 'alt.binaries.scary.exe.files', '/^\\[u(?:senet)?4all.*?(?P<name>[\\w\\(\\)]+(?:\\.|_| )[\\w\\(\\)\\.\\-_ ]+\\-[\\w&]+).*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (584, 'alt.binaries.scary.exe.files', '/^\\[u(?:senet)?4all.*?\\] (?P<name>.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (585, 'alt.binaries.multimedia', '/^(?P<name>(?!re: ).*?Asphyxiated) \\- \\[(?P<parts>\\d{2}\\/\\d{2})/i', 1, 1, '', NULL),
  (586, 'alt.binaries.mp3', '/^Metal4you.*?"(?P<name>.*?)\\.(par|vol|rar|nzb|nfo|sfv)/i', 1, 1, '', NULL),
  (587, 'alt.binaries.sounds.lossless', '/^(?P<name>.*? \\- .*? \\- NMR) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 1, 1, '', NULL),
  (588, 'alt.binaries.sounds.lossless', '/^Metal4you.*?"(?P<name>.*?)\\.(par|vol|rar|nzb|nfo|sfv)/i', 1, 1, '', NULL),
  (589, 'alt.binaries.sounds.lossless', '/^(?P<name>.*? \\- \\d{4} \\- .*?) \\- (?P<parts>\\d{1,3} of \\d{1,3})/i', 1, 1, '', NULL),
  (590, 'alt.binaries.sounds.lossless', '/^\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- "(?P<name>.*?)\\.(par|vol|rar|sfv|nfo|nzb)/i', 1, 1, '', NULL),
  (591, 'alt.binaries.sounds.lossless', '/^(RE: |)(?P<name>\\w.*?)( \\- |)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- "/i', 1, 1, '', NULL),
  (592, 'alt.binaries.sounds.lossless.classical', '/^(?P<name>\\w.*?)(\\.(par|vol|rar|sfv|nfo|nzb).*?| )(\\[|\\()/i', 1, 1, '', NULL),
  (593, 'alt.binaries.ebook', '/^ATTN:.*? .*? (\\- |)(?P<name>.*,.*?)\\.(par|vol|rar|nfo|sfv|nzb)/i', 1, 1, '', NULL),
  (594, 'alt.binaries.sounds.country.mp3', '/^(?P<name>VA.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i ', 1, 1, '', NULL),
  (595, 'alt.binaries.sounds.country.mp3', '/^(?P<name>.*? \\- \\d{4} \\- .*?) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (596, 'alt.binaries.boneless', '/^(\\[|\\()(PC|ISO).*?(\\]|\\)) (?P<name>.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', 4050),
  (597, 'alt.binaries.inner-sanctum', '/^\\[.*?(?P<name>[^\\(\\[\\]#"][A-Z0-9\\.\\-_\\(\\)]{10,}\\-[A-Z0-9&]+).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (598, 'alt.binaries.inner-sanctum', '/^(?P<name>.*?) >ghost-of-usenet\\.org.*?\\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (599, 'alt.binaries.cd.image', '/^\\(.*?thunder.*?\\((?P<parts>\\d{1,3}\\/\\d{1,3})\\) \\- "(?P<name>.*?)\\.(par|vol|rar|nfo|sfv|nzb)/i', 1, 1, '', NULL),
  (600, 'alt.binaries.classic.tv.shows', '/^(?P<name>.*?) \\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (601, 'alt.binaries.comics.dcp', '/^DCP.*?"(?P<name>.*?)\\.cbr/i', 1, 1, '', NULL),
  (602, 'alt.binaries.console.ps3', '/^\\[(REQ\\.|)(?P<reqid>\\d{1,6})\\]\\-.*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (603, 'alt.binaries.comics.dcp', '/^As R.*? "(?P<name>.*?)\\.cb.*?/i', 1, 1, '', NULL),
  (604, 'alt.binaries.console.ps3', '/^<Cowb.*?\\"(?P<name>.*?)\\.(vol|par|rar|nfo|sfv|nzb).*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (605, 'alt.binaries.mp3.audiobooks', '/^(?P<name>.*?NMR).*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (606, 'alt.binaries.console.ps3', '/^\\[(?P<reqid>\\d{1,6})\\] "/i', 4, 1, '', NULL),
  (607, 'alt.binaries.multimedia.disney', '/^\\}.*?"(?P<name>.*?)\\.(vol|par|rar|sfv|nfo|nzb).*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (608, 'alt.binaries.documentaries', '/^HD.*?"(?P<name>.*?)\\.(vol|par|rar|sfv|nfo|nzb)/i', 1, 1, '', NULL),
  (609, 'alt.binaries.documentaries', '/^(?P<name>NOVA \\- .*?) "N.*?\\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (610, 'alt.binaries.games.wii', '/^\\((?P<name>.*?)\\) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', 1030),
  (611, 'alt.binaries.hdtv', '/^\\[.*?(?P<parts>\\d{1,3}\\/\\d{1,3}) \\] \\- "(?P<name>.*?)\\.(vol|rar|par|sfv|nfo|nzb)/i', 1, 1, '', NULL),
  (612, 'alt.binaries.hdtv', '/^(?P<name>\\w.*?) (\\- |)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 2, 1, '', NULL),
  (613, 'alt.binaries.hdtv.x264', '/^Mak.*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3}).*?"(?P<name>.*?)\\.(vol|par|rar|sfv|nfo|nzb)/i', 1, 1, '', NULL),
  (614, 'alt.binaries.hdtv', '/^(\\[|\\()(?P<parts>\\d{1,3}\\/\\d{1,3})(\\]|\\)) \\- "(?P<name>.*?)\\.(vol|par|rar|sfv|nfo|nzb)/i', 1, 1, '', NULL),
  (615, 'alt.binaries.country.mp3', '/^(?P<name>.*? \\- \\d{4} \\-.*?)\\d{2}/i', 1, 1, '', NULL),
  (616, 'alt.binaries.dvdr', '/^\\#alt\\..*?: (?P<name>.*?)(_PAR2|) "/i', 1, 1, '', NULL),
  (617, 'alt.binaries.hdtv', '/^(?P<name>.*?\\d{4}.*?AVC.*?) \\- "/i', 1, 1, '', NULL),
  (618, 'alt.binaries.hdtv.x264', '/^Extre.*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- "(?P<name>.*?)\\.(vol|par|rar|sfv|nfo|nzb)/i', 1, 1, '', NULL),
  (619, 'alt.binaries.multimedia', '/^\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\] \\- "(?P<name>.*?)\\.(vol|par|ar|sfv|nfo|nzb)/i', 1, 1, '', NULL),
  (620, 'alt.binaries.x264', '/^\\[ Mac.*?\\] \\[(?P<name>.*?) \\] \\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (621, 'alt.binaries.sounds.mp3.rock', '/^(?P<name>.*?)\\-\\-\\-.*?(c|v)br/i', 1, 1, '', NULL),
  (622, 'alt.binaries.multimedia', '/^TOWN.*?>\\((?P<parts>\\d{1,3}\\/\\d{1,3})\\) "(?P<name>.*?)\\.(vol|par|rar|sfv|nfo|nzb)/i', 1, 1, '', NULL),
  (623, 'alt.binaries.mp3', '/^(?P<name>\\w.*?\\-\\w.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (624, 'alt.binaries.multimedia', '/^BTL\\-(?P<name>.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (625, 'alt.binaries.multimedia', '/^#a\\..*?\\- req.*?\\- (?P<name>.*?)( \\-|) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (626, 'alt.binaries.hdtv.x264', '/^\\[(?P<name>\\w.*?)\\]\\-\\[ich.*?\\((?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (627, 'alt.binaries.multimedia', '/^#A.*?: (?P<name>.*?) \\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 1, 1, '', NULL),
  (628, 'alt.binaries.worms', '/^\\[u(?:senet)?4all.*?(?P<name>[\\w\\(\\)]+(?:\\.|_| )[\\w\\(\\)\\.\\-_ ]+\\-[\\w&]+).*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 0, 1, '', NULL),
  (629, 'alt.binaries.worms', '/^\\[u(?:senet)?4all.*?(?P<name>[\\w\\(\\)]+(?:\\.|_)[\\w\\(\\)\\.\\-_]+\\-[\\w&]+).*?\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 0, 1, '', NULL),
  (630, 'alt.binaries.worms', '/^\\[u(?:senet)?4all.*?\\] (?P<name>.*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 0, 1, '', NULL),
  (631, 'alt.binaries.worms', '/^\\[u4all.*?\\](?P<name>.*?(xvid|x264|bluray).*?)\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/', 0, 1, '', NULL),
  (632, 'alt.binaries.*', '/^\\[([\\dx]{3,6}|mp3|flood|enjoy).*\\[(?P<name>(?!repost|sample|extra par|full|xbox|psp|abgx)[^\\]]*)\\].*?\\[(?P<parts>\\d{1,4}(?:\\/|~)\\d{1,4})/i', 1, 1, '', NULL),
  (633, 'alt.binaries.*', '/.*?\\b(?!www\\.|a\\.|b\\.|re:|usenet|u4al|dd5\\.1l|req|\\d{3,6})(?P<name>(?:x\\-files|[a-z0-9]+)(?:\\.|_)[a-z0-9\\(\\)\\[\\]\\.\\-_\\'']+\\-[a-z0-9&\\)]+)\\b.*?(?P<parts>\\d{1,3}\\/\\d{1,3})/i', 9, 1, '', NULL),
  (634, 'alt.binaries.*', '/^\\[ (?P<name>[^\\]]*?) \\]\\-\\[(?P<parts>\\d{1,3}\\/\\d{1,3})\\]/i', 3, 1, '', NULL),
  (635, 'alt.binaries.*', '/^(?P<name>[a-z0-9]+(?:\\.|_)[a-z0-9\\(\\)\\.\\-_\\'']+\\-[a-z0-9&\\)]+) "?[a-z0-9\\.\\-_\\+]+"?$/i', 15, 1, '', NULL),
  (636, 'alt.binaries.x264.*', '/^#alt\\.binaries.*?: (?P<name>[A-Z0-9\\.\\-_\\(\\)]+\\-[A-Z0-9\\-]+)/i', 1, 1, '', NULL),
  (637, 'alt.binaries.hdtv.x264', '/^\\[SailorPsy.*\\[(?P<parts>\\d{2,3}\\/\\d{2,3}).*?"(?P<name>.*?)\\.(nzb|par|vol|nfo|sfv|srr)/i', 1, 1, '', 5040),
  (638, 'alt.binaries.hdtv.x264', '/^\\[\\d{4,6}.*?efnet.*?\\[(?P<name>.*?S\\d{2}\\.?E\\d{2}.*?)\\]/i', 3, 1, '', NULL),
  (639, 'alt.binaries.hdtv', '/^\\[ (?P<reqid>\\d{5,6}).*?(?P<parts>\\d{2,3}\\/\\d{2,3}).*?"(?P<name>.*?s\\d{2}.*?)\\.(vol|par2|part|nzb|rar|nfo|txt)/i', 1, 1, '', NULL),
  (640, 'alt.binaries.hdtv', '/^(?P<name>.*ALANIS) \\[(?P<parts>\\d{2,3}\\/\\d{2,3})/i', 5, 1, '', NULL),
  (641, 'alt.binaries.multimedia', '/^(?P<name>(?!re: ).*?Asphyxiated) \\- \\[(?P<parts>\\d{2}\\/\\d{2})/i', 1, 1, '', NULL),
  (642, 'alt.binaries.(multimedia|teevee|tv|tvseries)', '/^.*\\[lift-?cup\\][\\[\\s-]+(?P<name>(?!re: ).*?)[\\]\\s-]+.*?\\[(?P<parts>\\d{2}\\/\\d{2})/i', 3, 1, '', NULL),
  (643, 'alt.binaries.*', '/^.*\\[(?P<parts>\\d+\\/\\d+)\\].*"(?P<name>[^"]+(?:S\\d\\d?E\\d\\d?|S\\d\\d|\\d\\d?x|\\d{4}\\W+\\d\\d\\W+\\d\\d|Season\\W+\\d+\\W+|E(?:p?(?:isode)?[\\._ -]*?)\\d+)[^"]+?)(?:\\.(?:part\\d*|vol[\\d+]+))*\\.(?:nfo|nzb|sfv|par2|par|rar|avi|mkv)"\\ yEnc.*$/', 20, 0, '', NULL);

ALTER TABLE `releaseregex`
MODIFY `ID` int(11) unsigned NOT NULL; -- remove auto increment

DROP TABLE IF EXISTS `ReleaseRegex_SEQ`;
CREATE TABLE IF NOT EXISTS `ReleaseRegex_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `ReleaseRegex_SEQ` VALUES (10000);

DROP TABLE IF EXISTS `Binary_SEQ`;
CREATE TABLE IF NOT EXISTS `Binary_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `Binary_SEQ` VALUES (1);

ALTER TABLE `binaries`
    MODIFY `ID` BIGINT NOT NULL; -- remove auto increment;
alter TABLE `binaries` MODIFY `name` VARCHAR(1024) NOT NULL DEFAULT '';
ALTER TABLE `binaries` DROP INDEX `ix_binary_binaryhash`;
ALTER TABLE `binaries` ADD UNIQUE INDEX `ix_binary_binaryhash` (binaryhash);

DROP TABLE IF EXISTS `Part_SEQ`;
CREATE TABLE IF NOT EXISTS `Part_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `Part_SEQ` VALUES (1);

ALTER TABLE `parts`
    MODIFY `ID` BIGINT NOT NULL ; -- remove auto increment;

DROP TABLE IF EXISTS `PartRepair_SEQ`;
CREATE TABLE IF NOT EXISTS `PartRepair_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `PartRepair_SEQ` VALUES (1);

ALTER TABLE `partrepair`
MODIFY `ID` BIGINT NOT NULL ; -- remove auto increment;

DROP TABLE IF EXISTS `Release_SEQ`;
CREATE TABLE IF NOT EXISTS `Release_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `Release_SEQ` VALUES (1);

ALTER TABLE `releases` MODIFY `ID` BIGINT NOT NULL ;

DROP TABLE IF EXISTS `ReleaseNfo_SEQ`;
CREATE TABLE IF NOT EXISTS `ReleaseNfo_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `ReleaseNfo_SEQ` VALUES (1);

ALTER TABLE `releasenfo` MODIFY `ID` BIGINT NOT NULL ;
ALTER TABLE `releasenfo` MODIFY `nfo` MEDIUMBLOB NULL DEFAULT NULL;

DROP TABLE IF EXISTS `BinaryBlacklistEntry_SEQ`;
CREATE TABLE IF NOT EXISTS `BinaryBlacklistEntry_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `BinaryBlacklistEntry_SEQ` VALUES (100001);

ALTER TABLE `binaryblacklist` MODIFY `ID` BIGINT NOT NULL ;

DROP TABLE IF EXISTS `Newsj_SEQ`;
CREATE TABLE IF NOT EXISTS `Newsj_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `Newsj_SEQ` VALUES (10301);

DROP TABLE IF EXISTS `FORUMPOST_SEQ`;
CREATE TABLE IF NOT EXISTS `FORUMPOST_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `FORUMPOST_SEQ` VALUES (101);

ALTER TABLE `forumpost` MODIFY `ID` BIGINT NOT NULL ;

DROP TABLE IF EXISTS `RELEASECOMMENT_SEQ`;
CREATE TABLE IF NOT EXISTS `RELEASECOMMENT_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `RELEASECOMMENT_SEQ` VALUES (1);

ALTER TABLE `releasecomment` MODIFY `ID` BIGINT NOT NULL ;

DROP TABLE IF EXISTS `USER_SEQ`;
CREATE TABLE IF NOT EXISTS `USER_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `USER_SEQ` VALUES (1);

ALTER TABLE `users` MODIFY `ID` BIGINT NOT NULL ;

DROP TABLE IF EXISTS `Group_SEQ`;
CREATE TABLE IF NOT EXISTS `Group_SEQ` (
  `next_val` BIGINT NOT NULL DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

INSERT INTO `Group_SEQ` VALUES (150);

ALTER TABLE `groups` MODIFY `ID` BIGINT NOT NULL ;

ALTER TABLE `category` MODIFY `ID` BIGINT NOT NULL;
ALTER TABLE `consoleinfo` MODIFY `ID` BIGINT NOT NULL;
ALTER TABLE `content` MODIFY `id` BIGINT NOT NULL;
ALTER TABLE `genres` MODIFY `ID` BIGINT NOT NULL;
ALTER TABLE `menu` MODIFY `ID` BIGINT NOT NULL;
ALTER TABLE `movieinfo` MODIFY `ID` BIGINT NOT NULL;
ALTER TABLE `musicinfo` MODIFY `ID` BIGINT NOT NULL;
ALTER TABLE `site` MODIFY `id` BIGINT NOT NULL;
ALTER TABLE `tvrage` MODIFY `ID` BIGINT NOT NULL ;
ALTER TABLE `usercart` MODIFY `ID` BIGINT NOT NULL;
ALTER TABLE `userexcat` MODIFY `ID` BIGINT NOT NULL;
ALTER TABLE `userinvite` MODIFY `ID` BIGINT NOT NULL;

ALTER TABLE `binaries` CHANGE COLUMN `name` `name_` VARCHAR(255) NOT NULL DEFAULT '';
ALTER TABLE `binaries` CHANGE COLUMN `date` `date_` DATETIME DEFAULT NULL;

ALTER TABLE `releases` CHANGE COLUMN `name` `name_` VARCHAR(255) NOT NULL DEFAULT '';
ALTER TABLE `releases` CHANGE COLUMN `size` `size_` BIGINT UNSIGNED NOT NULL DEFAULT '0';

ALTER TABLE `groups` CHANGE COLUMN `name` `name_` VARCHAR(255) NOT NULL DEFAULT '';

ALTER TABLE `parts` CHANGE COLUMN `number` `number_` BIGINT UNSIGNED NOT NULL DEFAULT '0';
ALTER TABLE `parts` CHANGE COLUMN `size` `size_` BIGINT UNSIGNED NOT NULL DEFAULT '0';

CREATE INDEX ix_releases_regexId ON releases (`regexID`);
CREATE INDEX ix_releases_groupId on releases (`groupID`);

-- TODO: verify this works
-- makes "rageId" EVERYWHERE BUT TVRAGE represent a "tvinfo" id, not a TvRage id specific to the TvRage site
UPDATE `releases` set rageID = (select tv.ID from tvrage tv where tv.rageID = `releases`.rageID LIMIT 1) where `releases`.rageID > 0;