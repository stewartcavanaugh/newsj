/*
 * Copyright (c) 2016. Sten Martinez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package net.longfalcon.newsj;

import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.UserExCatDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 9:15 AM
 */
public class CategoryService {

    public static final int CAT_GAME_NDS = 1010;
    public static final int CAT_GAME_PSP = 1020;
    public static final int CAT_GAME_WII = 1030;
    public static final int CAT_GAME_XBOX = 1040;
    public static final int CAT_GAME_XBOX360 = 1050;
    public static final int CAT_GAME_WIIWARE = 1060;
    public static final int CAT_GAME_XBOX360DLC = 1070;
    public static final int CAT_GAME_PS3 = 1080;
    public static final int CAT_MOVIE_FOREIGN = 2010;
    public static final int CAT_MOVIE_OTHER = 2020;
    public static final int CAT_MOVIE_SD = 2030;
    public static final int CAT_MOVIE_HD = 2040;
    public static final int CAT_MUSIC_MP3 = 3010;
    public static final int CAT_MUSIC_VIDEO = 3020;
    public static final int CAT_MUSIC_AUDIOBOOK = 3030;
    public static final int CAT_MUSIC_LOSSLESS = 3040;
    public static final int CAT_PC_0DAY = 4010;
    public static final int CAT_PC_ISO = 4020;
    public static final int CAT_PC_MAC = 4030;
    public static final int CAT_PC_PHONE = 4040;
    public static final int CAT_PC_GAMES = 4050;
    public static final int CAT_TV_FOREIGN = 5020;
    public static final int CAT_TV_SD = 5030;
    public static final int CAT_TV_HD = 5040;
    public static final int CAT_TV_OTHER = 5050;
    public static final int CAT_TV_SPORT = 5060;
    public static final int CAT_XXX_DVD = 6010;
    public static final int CAT_XXX_WMV = 6020;
    public static final int CAT_XXX_XVID = 6030;
    public static final int CAT_XXX_X264 = 6040;
    public static final int CAT_MISC = 7010;
    public static final int CAT_MISC_EBOOK = 7020;
    public static final int CAT_MISC_COMICS = 7030;
    public static final int CAT_MISC_ANIME = 7040;

    public static final int CAT_PARENT_GAME = 1000;
    public static final int CAT_PARENT_MOVIE = 2000;
    public static final int CAT_PARENT_MUSIC = 3000;
    public static final int CAT_PARENT_PC = 4000;
    public static final int CAT_PARENT_TV = 5000;
    public static final int CAT_PARENT_XXX = 6000;
    public static final int CAT_PARENT_MISC = 7000;

    public static final int STATUS_INACTIVE = 0;
    public static final int STATUS_ACTIVE = 1;

    private static PeriodFormatter _periodFormatter = PeriodFormat.getDefault();
    private static final Log _log = LogFactory.getLog(CategoryService.class);

    private static int tmpCat = 0;

    private GroupDAO groupDAO;
    private CategoryDAO categoryDAO;
    private UserExCatDAO userExCatDAO;

    public List<Category> getCategoriesForMenu(long userId) {
        List<Integer> userExCatIdsList = userExCatDAO.getUserExCatIds(userId);
        Set<Integer> userExcludedCategoryIds = new HashSet<>(userExCatIdsList);

        List<Category> categoryList = categoryDAO.getForMenu(userExcludedCategoryIds);
        for (Category category : categoryList) {
            category.setSubCategories(getSubCategories(userExcludedCategoryIds, category.getId()));
        }

        return categoryList;
    }

    public List<Category> getChildCategories() {
        return categoryDAO.getChildCategories();
    }

    public List<Integer> getCategoryChildrenIds(int categoryParentId) {
        return categoryDAO.getCategoryChildrenIds(categoryParentId);
    }

    public Category getCategory(int categoryId) {
        return categoryDAO.findByCategoryId(categoryId);

    }

    public String getCategoryDisplayName(int categoryId) {
        Category category = categoryDAO.findByCategoryId(categoryId);
        // TODO: replace with hbm mapping for parentCategory
        Category parentCategory = categoryDAO.findByCategoryId(category.getParentId());
        return parentCategory.getTitle() + " > " + category.getTitle();
    }

    private List<Category> getSubCategories(Set<Integer> userExcludedCategoryIds, int parentId) {
        List<Category> categoryList = categoryDAO.getForMenu(userExcludedCategoryIds, parentId);
        for (Category category : categoryList) {
            category.setSubCategories(getSubCategories(userExcludedCategoryIds, category.getId()));
        }
        return categoryList;
    }

    //
    // Work out which category is applicable for either a group or a binary.
    // returns -1 if no category is appropriate from the group name.
    //
    public int determineCategory(long groupId, final String releaseName) {
        tmpCat = 0;
        long startTime = System.currentTimeMillis();
        Group group = groupDAO.findGroupByGroupId(groupId);
        String groupName = group.getName();
        //
        // Try and determine based on group - First Pass
        //
        Pattern pattern = Pattern.compile("alt\\.binaries\\.ath", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isConsole(releaseName)){ return tmpCat; }
            if(isPC(releaseName)){ return tmpCat; }
            if(isMovie(releaseName)){ return tmpCat; }
            if(isMusic(releaseName)){ return tmpCat; }
        }

        pattern = Pattern.compile("alt\\.binaries\\.b4e", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isPC(releaseName)){ return tmpCat; }
            if(isEBook(releaseName)){ return tmpCat; }
        }

        pattern = Pattern.compile("anime", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
            return CAT_MISC_ANIME;

        pattern = Pattern.compile("alt\\.binaries\\..*?audiobook\\.*?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
            return CAT_MUSIC_AUDIOBOOK;

        pattern = Pattern.compile("lossless|flac", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            return CAT_MUSIC_LOSSLESS;
        }

        pattern = Pattern.compile("alt\\.binaries\\.sounds.*?|alt\\.binaries\\.mp3.*?|alt\\.binaries\\..*\\.mp3", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isMusic(releaseName)){ return tmpCat; }
            return CAT_MUSIC_MP3;
        }

        pattern = Pattern.compile("alt\\.binaries\\.console\\.ps3", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
            return CAT_GAME_PS3;

        pattern = Pattern.compile("alt\\.binaries\\.games\\.xbox*", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isConsole(releaseName)){ return tmpCat; }
            if(isTV(releaseName)){ return tmpCat; }
            if(isMovie(releaseName)){ return tmpCat; }
        }

        pattern = Pattern.compile("alt\\.binaries\\.games", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isConsole(releaseName)){ return tmpCat; }
            return CAT_PC_GAMES;
        }

        pattern = Pattern.compile("alt\\.binaries\\.games\\.wii", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isConsole(releaseName)) { return tmpCat; }
            return CAT_GAME_WII;
        }

        pattern = Pattern.compile("alt\\.binaries\\.dvd.*?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isXxx(releaseName)){ return tmpCat; }
            if(isTV(releaseName)){ return tmpCat; }
            if(isMovie(releaseName)){ return tmpCat; }
        }

        pattern = Pattern.compile("alt\\.binaries\\.hdtv*|alt\\.binaries\\.x264", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isXxx(releaseName)){ return tmpCat; }
            if(isTV(releaseName)){ return tmpCat; }
            if(isMovie(releaseName)){ return tmpCat; }
        }

        pattern = Pattern.compile("alt\\.binaries\\.classic\\.tv.*?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_TV_SD;
        }

        pattern = Pattern.compile("alt\\.binaries\\.e-book*?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_MISC_EBOOK;
        }

        pattern = Pattern.compile("alt\\.binaries\\.comics.*?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_MISC_COMICS;
        }

        pattern = Pattern.compile("alt\\.binaries\\.cores.*?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isXxx(releaseName)){ return tmpCat; }
            if(isMovie(releaseName)){ return tmpCat; }
            if(isConsole(releaseName)){ return tmpCat; }
            if(isPC(releaseName)){ return tmpCat; }
        }

        pattern = Pattern.compile("alt\\.binaries\\.cd.image|alt\\.binaries\\.audio\\.warez", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isPC(releaseName)){ return tmpCat; }
            return CAT_PC_0DAY;
        }

        pattern = Pattern.compile("alt\\.binaries\\.sony\\.psp", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
            return CAT_GAME_PSP;

        pattern = Pattern.compile("alt\\.binaries\\.nintendo\\.ds|alt\\.binaries\\.games\\.nintendods", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_GAME_NDS;
        }

        pattern = Pattern.compile("alt\\.binaries\\.mpeg\\.video\\.music", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_MUSIC_VIDEO;
        }

        pattern = Pattern.compile("alt\\.binaries\\.mac", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_PC_MAC;
        }

        pattern = Pattern.compile("linux", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_PC_ISO;
        }

        pattern = Pattern.compile("alt\\.binaries\\.ipod\\.videos\\.tvshows", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
            return CAT_TV_OTHER;

        pattern = Pattern.compile("alt\\.binaries\\.documentaries", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isXxx(releaseName)){ return tmpCat; }
            if(isTV(releaseName)){ return tmpCat; }
            return CAT_TV_SD;
        }

        pattern = Pattern.compile("alt\\.binaries\\.tv\\.swedish", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_TV_FOREIGN;
        }

        pattern = Pattern.compile("alt\\.binaries\\.erotica\\.divx", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_XXX_XVID;
        }

        pattern = Pattern.compile("alt\\.binaries\\.mma|alt\\.binaries\\.multimedia\\.sports.*?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            return CAT_TV_SPORT;
        }

        pattern = Pattern.compile("alt\\.binaries\\.b4e$", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find()) {
            if (isPC(releaseName)) {
                return tmpCat;
            }
        }

        pattern = Pattern.compile("alt\\.binaries\\.warez\\.smartphone", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
            if(isPC(releaseName)){ return tmpCat; }

        pattern = Pattern.compile("alt\\.binaries\\.warez\\.ibm\\-pc\\.0\\-day|alt\\.binaries\\.warez", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isConsole(releaseName)){ return tmpCat; }
            if(isEBook(releaseName)){ return tmpCat; }
            if(isXxx(releaseName)){ return tmpCat; }
            if(isPC(releaseName)){ return tmpCat; }
            if(isTV(releaseName)){ return tmpCat; }
            return CAT_PC_0DAY;
        }

        pattern = Pattern.compile("alt\\.binaries\\.(teevee|multimedia|tv|tvseries)", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isXxx(releaseName)){ return tmpCat; }
            if(isTV(releaseName)){ return tmpCat; }
            if(isForeignTV(releaseName)){ return tmpCat; }
            return CAT_TV_OTHER;
        }

        pattern = Pattern.compile("erotica", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isXxx(releaseName)){ return tmpCat; }
            return CAT_XXX_XVID;
        }

        pattern = Pattern.compile("alt\\.binaries\\.movies\\.xvid|alt\\.binaries\\.movies\\.divx|alt\\.binaries\\.movies", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isConsole(releaseName)){ return tmpCat; }
            if(isXxx(releaseName)){ return tmpCat; }
            if(isTV(releaseName)){ return tmpCat; }
            if(isMovie(releaseName)){ return tmpCat; }
            return CAT_MOVIE_SD;
        }

        pattern = Pattern.compile("wmvhd", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isXxx(releaseName)){ return tmpCat; }
            if(isTV(releaseName)){ return tmpCat; }
            if(isMovie(releaseName)){ return tmpCat; }
        }

        pattern = Pattern.compile("inner\\-sanctum", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isPC(releaseName)){ return tmpCat; }
            if(isEBook(releaseName)){ return tmpCat; }
            return CAT_MUSIC_MP3;
        }

        pattern = Pattern.compile("alt\\.binaries\\.x264", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(groupName);
        if (matcher.find())
        {
            if(isXxx(releaseName)){ return tmpCat; }
            if(isTV(releaseName)){ return tmpCat; }
            if(isMovie(releaseName)){ return tmpCat; }
            return CAT_MOVIE_OTHER;
        }

        //
        // if a category hasnt been set yet, then try against all
        // functions and if still nothing, return Cat Misc.
        //
        if(isXxx(releaseName)){ return tmpCat; }
        if(isPC(releaseName)){ return tmpCat; }
        if(isTV(releaseName)){ return tmpCat; }
        if(isMovie(releaseName)){ return tmpCat; }
        if(isConsole(releaseName)){ return tmpCat; }
        if(isMusic(releaseName)){ return tmpCat; }
        if(isEBook(releaseName)){ return tmpCat; }

        Period determineCatTime = new Period(startTime, System.currentTimeMillis());
        _log.info("Determining category for " + releaseName + " took " + _periodFormatter.print(determineCatTime));

        return CAT_MISC;
    }

    private boolean matchRegex(String regex, String value, int categoryType) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            tmpCat = categoryType;
            return true;
        }

        return false;
    }

    private boolean isTV(String releaseName) {

        Pattern pattern1 = Pattern.compile("(S?(\\d{1,2})\\.?(E|X|D)(\\d{1,2})[\\. _-]+)|(dsr|pdtv|hdtv)[\\.\\-_]", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(releaseName);

        Pattern pattern2 = Pattern.compile("(\\.S\\d{2}\\.|\\.S\\d{2}|\\.EP\\d{1,2}\\.|trollhd)", Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = pattern2.matcher(releaseName);

        if(matcher1.find() || matcher2.find())
        {
            if(isForeignTV(releaseName)){ return true; }
            if(isSportTV(releaseName)){ return true; }
            if(matchRegex("x264|1080|720|h\\.?264|web\\-?dl|wmvhd|trollhd", releaseName, CAT_TV_HD)){ return true; }
            if(matchRegex("dvdr|dvd5|dvd9|xvid", releaseName, CAT_TV_SD)){ return true; }
            tmpCat = CAT_TV_OTHER;
            return true;
        }

        return false;
    }

    private boolean isSportTV(String releaseName) {
        Pattern pattern = Pattern.compile("(epl|motogp|supercup|wtcc|red\\.bull.*?race|bundesliga|la\\.liga|uefa|EPL|ESPN|WWE\\.|MMA\\.|UFC\\.|FIA\\.|PGA\\.|NFL\\.|NCAA\\.)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(releaseName);

        if (matcher.find()) {
            tmpCat = CAT_TV_SPORT;
            return true;
        }

        pattern = Pattern.compile("WBA|Rugby\\.|TNA\\.|DTM\\.|NASCAR|SBK|NBA\\.|NHL\\.|NRL\\.|FIFA\\.|netball\\.anz|formula1|indycar|Superleague|V8\\.Supercars", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(releaseName);
        if (matcher.find()) {
            tmpCat = CAT_TV_SPORT;
            return true;
        }
        return false;
    }

    private boolean isForeignTV(String releaseName) {
        Pattern pattern = Pattern.compile("(danish|flemish|dutch|Deutsch|nl\\.?subbed|nl\\.?sub|\\.NL\\.|swedish|swesub|french|german|spanish)[\\.\\-]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(releaseName);

        if (matcher.find()) {
            tmpCat = CAT_TV_FOREIGN;
            return true;
        }

        pattern = Pattern.compile("NLSubs|NL\\-Subs|NLSub|Deutsch| der |German| NL ", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(releaseName);
        if (matcher.find()) {
            tmpCat = CAT_TV_FOREIGN;
            return true;
        }
        return false;
    }

    private boolean isXxx(String releaseName) {

        Pattern pattern = Pattern.compile("XXX", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(releaseName);
        if(matcher.find())
        {
            if(matchRegex("x264", releaseName, CAT_XXX_X264)){ return true; }
            if(matchRegex("xvid|dvdrip|bdrip|brrip|pornolation|swe6|nympho|detoxication|tesoro", releaseName, CAT_XXX_XVID)){ return true; }
            if(matchRegex("wmv|pack\\-|mp4|f4v|flv|mov|mpeg|isom|realmedia|multiformat|(e\\d{2,})|(\\d{2}\\.\\d{2}\\.\\d{2})|uhq|(issue\\.\\d{2,})", releaseName, CAT_XXX_WMV)){ return true; }
            if(matchRegex("dvdr[^ip]|dvd5|dvd9", releaseName, CAT_XXX_DVD)){ return true; }
            tmpCat = CAT_XXX_XVID;
            return true;
        }
        return false;
    }

    private boolean isEBook(String releaseName) {
        return matchRegex("Ebook|E?\\-book|\\) WW|\\[Springer\\]|Publishing", releaseName, CAT_MISC_EBOOK);
    }

    private boolean isMusic(String releaseName) {
        if(matchRegex("Greatest_Hits|VA?(\\-|_)|WEB\\-\\d{4}", releaseName, CAT_MUSIC_MP3)){ return true; }
        if(matchRegex("Lossless|FLAC", releaseName, CAT_MUSIC_LOSSLESS)){ return true; }

        return false;
    }

    private boolean isMovie(String releaseName) {
        if(isMovieForeign(releaseName)){ return true; }
        if(matchRegex("(xvid|dvdscr|extrascene|dvdrip|r5|\\.CAM|dvdr|dvd9|dvd5|divx)[\\.\\-]", releaseName, CAT_MOVIE_SD)){ return true; }
        if(matchRegex("x264|bluray\\-|wmvhd|web\\-dl|bd?25|bd?50|blu-ray|VC1|VC\\-1|AVC|XvidHD", releaseName, CAT_MOVIE_HD)){ return true; }

        return false;
    }

    private boolean isMovieForeign(String releaseName) {
        Pattern pattern = Pattern.compile("(danish|flemish|Deutsch|dutch|nl\\.?subbed|nl\\.?sub|\\.NL|swedish|swesub|french|german|spanish)[\\.\\-]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(releaseName);

        if (matcher.find()) {
            tmpCat = CAT_MOVIE_FOREIGN;
            return true;
        }

        pattern = Pattern.compile("NLSubs|NL\\-Subs|NLSub|\\d{4} German|Deutsch| der ", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(releaseName);
        if (matcher.find()) {
            tmpCat = CAT_MOVIE_FOREIGN;
            return true;
        }
        return false;
    }

    private boolean isPC(String releaseName) {
        if(isPhone(releaseName)){ return true; }
        if(matchRegex("osx|os\\.x|\\.mac\\.", releaseName, CAT_PC_MAC)){ return true; }
        if(matchRegex("\\-RELOADED|\\-SKIDROW|PC GAME|FASDOX|games|v\\d{1,3}.*?\\-TE|RIP\\-unleashed|Razor1911", releaseName, CAT_PC_GAMES)){ return true; }
        if(is0day(releaseName)){ return true; }

        return false;
    }

    private boolean is0day(String releaseName) {
        Pattern pattern = Pattern.compile("[\\.\\-_ ](x32|x64|x86|win64|winnt|win9x|win2k|winxp|winnt2k2003serv|win9xnt|win9xme|winnt2kxp|win2kxp|win2kxp2k3|keygen|regged|keymaker|winall|win32|template|Patch|GAMEGUiDE|unix|irix|solaris|freebsd|hpux|linux|windows|multilingual|software|Pro v\\d{1,3})[\\.\\-_ ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(releaseName);

        if (matcher.find()) {
            tmpCat = CAT_PC_0DAY;
            return true;
        }

        pattern = Pattern.compile("\\-SUNiSO|Adobe|CYGNUS|GERMAN\\-|v\\d{1,3}.*?Pro|MULTiLANGUAGE|Cracked|lz0|\\-BEAN|MultiOS|\\-iNViSiBLE|\\-SPYRAL|WinAll|Keymaker|Keygen|Lynda\\.com|FOSI|Keyfilemaker|DIGERATI|\\-UNION", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(releaseName);
        if (matcher.find()) {
            tmpCat = CAT_PC_0DAY;
            return true;
        }
        return false;
    }

    private boolean isPhone(String releaseName) {
        Pattern pattern = Pattern.compile("[\\.\\-_](IPHONE|ITOUCH|ANDROID|COREPDA|symbian|xscale|wm5|wm6)[\\.\\-_]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(releaseName);

        if (matcher.find()) {
            tmpCat = CAT_PC_PHONE;
            return true;
        }

        pattern = Pattern.compile("IPHONE|ITOUCH|IPAD|ANDROID|COREPDA|symbian|xscale|wm5|wm6", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(releaseName);
        if (matcher.find()) {
            tmpCat = CAT_PC_PHONE;
            return true;
        }
        return false;
    }

    private boolean isConsole(String releaseName) {
        if(matchRegex("NDS", releaseName, CAT_GAME_NDS)){return true;}
        if(matchRegex("PS3?\\-", releaseName, CAT_GAME_PS3)){ return true; }
        if(matchRegex("PSP?\\-", releaseName, CAT_GAME_PSP)){ return true; }
        if(isGameWiiWare(releaseName)){ return true; }
        if(matchRegex("WII", releaseName, CAT_GAME_WII)){ return true; }
        if(matchRegex("(DLC.*?xbox360|xbox360.*?DLC|XBLA.*?xbox360|xbox360.*?XBLA)", releaseName, CAT_GAME_XBOX360DLC)){ return true; }
        if(matchRegex("XBOX360|x360", releaseName, CAT_GAME_XBOX360)){ return true; }
        if(matchRegex("XBOX", releaseName, CAT_GAME_XBOX)){ return true; }

        return false;
    }

    private boolean isGameWiiWare(String releaseName) {
        Pattern pattern = Pattern.compile("WIIWARE|WII.*?VC|VC.*?WII|WII.*?DLC|DLC.*?WII|WII.*?CONSOLE|CONSOLE.*?WII", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(releaseName);

        if (matcher.find()) {
            tmpCat = CAT_GAME_WIIWARE;
            return true;
        }

        return false;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public UserExCatDAO getUserExCatDAO() {
        return userExCatDAO;
    }

    public void setUserExCatDAO(UserExCatDAO userExCatDAO) {
        this.userExCatDAO = userExCatDAO;
    }
}
