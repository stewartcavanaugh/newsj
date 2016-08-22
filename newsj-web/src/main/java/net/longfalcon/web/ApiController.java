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

package net.longfalcon.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.persistence.SiteDAO;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.persistence.UserExCatDAO;
import net.longfalcon.newsj.service.SearchService;
import net.longfalcon.newsj.service.SiteService;
import net.longfalcon.newsj.service.UserService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.newsj.ws.newznab.ApiResponse;
import net.longfalcon.newsj.ws.newznab.CapsType;
import net.longfalcon.newsj.ws.newznab.Error;
import net.longfalcon.newsj.ws.newznab.RegisterType;
import net.longfalcon.newsj.ws.newznab.caps.CategoriesType;
import net.longfalcon.newsj.ws.newznab.caps.CategoryType;
import net.longfalcon.newsj.ws.newznab.caps.LimitsType;
import net.longfalcon.newsj.ws.newznab.caps.RegistrationType;
import net.longfalcon.newsj.ws.newznab.caps.SearchingType;
import net.longfalcon.newsj.ws.newznab.caps.ServerType;
import net.longfalcon.newsj.ws.newznab.caps.SubCatType;
import net.longfalcon.view.UserRegistrationVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@Controller
public class ApiController {

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    ReleaseDAO releaseDAO;

    @Autowired
    SearchService searchService;

    @Autowired
    SiteDAO siteDAO;

    @Autowired
    UserExCatDAO userExCatDAO;

    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;

    private static final String TYPE_DETAILS = "d";
    private static final String TYPE_GET = "g";
    private static final String TYPE_SEARCH = "s";
    private static final String TYPE_CAPS = "c";
    private static final String TYPE_TVSEARCH = "tv";
    private static final String TYPE_MOVIE = "m";
    private static final String TYPE_REGISTER = "r";
    private static final Log _log = LogFactory.getLog(ApiController.class);

    @RequestMapping(value = "/api", produces = "text/xml")
    @ResponseBody
    public ApiResponse xmlApi(@RequestParam(value = "t", required = false) String type,
                              @RequestParam(value = "apikey", required = false) String apiKey,
                              @RequestParam(value = "id", required = false) String guid,
                              @RequestParam(value = "extended", required = false) String extended,
                              @RequestParam(value = "del", required = false) String del,
                              @RequestParam(value = "q", required = false) String query,
                              @RequestParam(value = "maxage", required = false) String maxAge,
                              @RequestParam(value = "limit", required = false) String limit,
                              @RequestParam(value = "offset", required = false) String offset,
                              @RequestParam(value = "imdbid", required = false) String imdbId,
                              @RequestParam(value = "rid", required = false) String rid,
                              @RequestParam(value = "season", required = false) String season,
                              @RequestParam(value = "ep", required = false) String ep,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam(value = "cat", required = false) String categoryIdsString,
                              UriComponentsBuilder uriComponentsBuilder) {
        String function = TYPE_SEARCH;
        if (ValidatorUtil.isNotNull(type)) {
            if (type.equals("details") || type.equals(TYPE_DETAILS)) {
                function = TYPE_DETAILS;
            } else if (type.equals("get") || type.equals(TYPE_GET)) {
                function = TYPE_GET;
            } else if (type.equals("search") || type.equals(TYPE_SEARCH)) {
                function = TYPE_SEARCH;
            } else if (type.equals("caps") || type.equals(TYPE_CAPS)) {
                function = TYPE_CAPS;
            } else if (type.equals("tvsearch") || type.equals(TYPE_TVSEARCH)) {
                function = TYPE_TVSEARCH;
            } else if (type.equals("movie") || type.equals(TYPE_MOVIE)) {
                function = TYPE_MOVIE;
            } else if (type.equals("register") || type.equals(TYPE_REGISTER)) {
                function = TYPE_REGISTER;
            } else {
                return generateError(202);
            }
        } else {
            return generateError(200);
        }

        String serverBaseUrl = uriComponentsBuilder.toUriString();

        if (function.equals(TYPE_CAPS)) {
            return getCaps(serverBaseUrl);
        }

        if (function.equals(TYPE_REGISTER)) {
            return getRegister(email);

        }

        if (ValidatorUtil.isNull(apiKey)) {
            return generateError(200);
        }

        User user = userDAO.findByApiKey(apiKey);
        if (user == null) {
            return generateError(100);
        }

        if (function.equals(TYPE_SEARCH)) {
            if (query != null && query.equals("")) {
                return generateError(200);
            }
            return getSearchResponse(user, serverBaseUrl, extended, query, maxAge, limit, offset, categoryIdsString);
        }

        if (function.equals(TYPE_TVSEARCH)) {
            if (query != null && query.equals("")) {
                return generateError(200);
            }
            return getTvSearchResponse(user, serverBaseUrl, extended, query, maxAge, limit, offset, categoryIdsString, rid, season, ep);
        }

        if (function.equals(TYPE_MOVIE)) {
            if (query != null && query.equals("")) {
                return generateError(200);
            }
            return getMovieSearchResponse(user, serverBaseUrl, extended, query, maxAge, limit, offset, categoryIdsString, imdbId);
        }

        if (function.equals(TYPE_DETAILS)) {
            return getReleaseDetailsResponse(user, serverBaseUrl, extended, guid);
        }

        return generateError(202);
    }

    @RequestMapping(value = "/api", params = "o=json", produces = "application/json")
    @ResponseBody
    public String jsonApi(@RequestParam(value = "t", required = false) String type,
                            @RequestParam(value = "apikey", required = false) String apiKey,
                            @RequestParam(value = "id", required = false) String guid,
                            @RequestParam(value = "extended", required = false) String extended,
                            @RequestParam(value = "del", required = false) String del,
                            @RequestParam(value = "q", required = false) String query,
                            @RequestParam(value = "maxage", required = false) String maxAge,
                            @RequestParam(value = "limit", required = false) String limit,
                            @RequestParam(value = "offset", required = false) String offset,
                            @RequestParam(value = "imdbid", required = false) String imdbId,
                            @RequestParam(value = "rid", required = false) String rid,
                            @RequestParam(value = "season", required = false) String season,
                            @RequestParam(value = "ep", required = false) String ep,
                            @RequestParam(value = "email", required = false) String email,
                            @RequestParam(value = "cat", required = false) String categoryIdsString,
                            UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {

        ApiResponse apiResponse = xmlApi(type, apiKey, guid, extended, del, query, maxAge, limit, offset, imdbId, rid, season, ep, email, categoryIdsString, uriComponentsBuilder);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(apiResponse);
    }

    private ApiResponse getRegister( String email) {
        Site site = siteDAO.getDefaultSite();
        if (ValidatorUtil.isNull(email)) {
            return generateError(200);
        }

        if (site.getRegisterStatus() != SiteService.REGISTER_STATUS_OPEN) {
            return generateError(104);
        }

        if (!ValidatorUtil.isValidEmail(email)) {
            return generateError(106);
        }

        User user = userDAO.findByEmail(email);
        if ( user != null) {
            return generateError(105);
        }

        String username = userService.generateUsername(email);
        String password = userService.generatePassword();

        UserRegistrationVO userRegistrationVO = new UserRegistrationVO();
        userRegistrationVO.setEmail(email);
        userRegistrationVO.setUserName(username);
        userRegistrationVO.setPassword(password);
        long returnCode = userService.signup(userRegistrationVO);

        if (returnCode < 0) {
            return generateError(107);
        }

        user = userDAO.findByUserId(returnCode);
        if (user == null) {
            return generateError(107);
        }

        return new RegisterType(username, password, user.getRssToken());
    }

    private ApiResponse getCaps(String serverBaseUrl) {
        Site site = siteDAO.getDefaultSite();

        CapsType caps = new CapsType();
        caps.setServerType(new ServerType("0.2.3", "0.1", site.getTitle(), site.getStrapLine(), "email@email.com",
                serverBaseUrl, ""));
        caps.setLimitsType(new LimitsType(100,100));
        caps.setRegistrationType(new RegistrationType(true, site.getRegisterStatus() == 1));
        caps.setSearchingType(new SearchingType(true, true, true, false));
        List<CategoryType> categoryTypes = new ArrayList<>();
        List<Category> parentCategories = categoryDAO.getParentCategories();
        for (Category parentCategory: parentCategories) {
            List<Category> subCategories = categoryDAO.findByParentId(parentCategory.getId());
            List<SubCatType> subCatTypes = new ArrayList<>();
            for (Category category : subCategories) {
                SubCatType subCatType = new SubCatType(category.getId(), category.getTitle());
                subCatTypes.add(subCatType);
            }
            CategoryType categoryType = new CategoryType(parentCategory.getId(), parentCategory.getTitle(), subCatTypes);
            categoryTypes.add(categoryType);
        }
        CategoriesType categoriesType = new CategoriesType(categoryTypes);
        caps.setCategoriesType(categoriesType);

        return caps;
    }

    private ApiResponse getSearchResponse(User user, String serverBaseUrl, String extended, String query, String maxAge, String limitString, String offsetString, String categoryIdsString) {

        List<Integer> categoryIds = new ArrayList<>();
        if (ValidatorUtil.isNotNull(categoryIdsString)) {
            String[] categoryIdsSplit = categoryIdsString.split(",");
            for (String categoryIdString : categoryIdsSplit) {
                categoryIds.add(Integer.parseInt(categoryIdString));
            }
        }
        int maxAgeDays = -1;
        if (ValidatorUtil.isNotNull(maxAge)) {
            if (ValidatorUtil.isNumeric(maxAge)) {
                maxAgeDays = Integer.parseInt(maxAge);
            } else {
                return generateError(200);
            }
        }
        List<Integer> userExCatIds = userExCatDAO.getUserExCatIds(user.getId());
        long groupId = -1;
        String orderByFieldName = "addDate";
        boolean orderByDesc = true;
        int offset = 0;
        if (ValidatorUtil.isNotNull(offsetString)) {
            offset = Integer.parseInt(offsetString);
        }
        int pageSize = 100;
        if (ValidatorUtil.isNotNull(limitString)) {
            pageSize = Integer.parseInt(limitString);
        }
        boolean isExtended = false;
        if (ValidatorUtil.isNotNull(extended)) {
            isExtended = extended.equals("1") || extended.equals("true");
        }

        return searchService.searchReleasesApi(user, serverBaseUrl, query, categoryIds, maxAgeDays, userExCatIds, groupId, orderByFieldName, orderByDesc, offset, pageSize, isExtended);
    }

    private ApiResponse getTvSearchResponse(User user, String serverBaseUrl, String extended, String query, String maxAge,
                                            String limitString, String offsetString, String categoryIdsString, String rageIdString, String season, String episode) {

        List<Integer> categoryIds = new ArrayList<>();
        if (ValidatorUtil.isNotNull(categoryIdsString)) {
            String[] categoryIdsSplit = categoryIdsString.split(",");
            for (String categoryIdString : categoryIdsSplit) {
                categoryIds.add(Integer.parseInt(categoryIdString));
            }
        }
        int maxAgeDays = -1;
        if (ValidatorUtil.isNotNull(maxAge)) {
            if (ValidatorUtil.isNumeric(maxAge)) {
                maxAgeDays = Integer.parseInt(maxAge);
            } else {
                return generateError(200);
            }
        }
        List<Integer> userExCatIds = userExCatDAO.getUserExCatIds(user.getId());
        long groupId = -1;
        String orderByFieldName = "addDate";
        boolean orderByDesc = true;
        int offset = 0;
        if (ValidatorUtil.isNotNull(offsetString)) {
            offset = Integer.parseInt(offsetString);
        }
        int pageSize = 100;
        if (ValidatorUtil.isNotNull(limitString)) {
            pageSize = Integer.parseInt(limitString);
        }
        boolean isExtended = false;
        if (ValidatorUtil.isNotNull(extended)) {
            isExtended = extended.equals("1") || extended.equals("true");
        }

        long rageId = -1; // this is a TVRage metadata ID, not a tvInfo id
        if (ValidatorUtil.isNotNull(rageIdString)) {
            rageId = Long.parseLong(rageIdString);
        }

        return searchService.searchTvReleasesApi(user, serverBaseUrl, query, rageId, season, episode, categoryIds, maxAgeDays, userExCatIds, groupId, orderByFieldName, orderByDesc, offset, pageSize, isExtended);
    }

    private ApiResponse getMovieSearchResponse(User user, String serverBaseUrl, String extended, String query, String maxAge,
                                            String limitString, String offsetString, String categoryIdsString, String imdbIdString) {

        List<Integer> categoryIds = new ArrayList<>();
        if (ValidatorUtil.isNotNull(categoryIdsString)) {
            String[] categoryIdsSplit = categoryIdsString.split(",");
            for (String categoryIdString : categoryIdsSplit) {
                categoryIds.add(Integer.parseInt(categoryIdString));
            }
        }
        int maxAgeDays = -1;
        if (ValidatorUtil.isNotNull(maxAge)) {
            if (ValidatorUtil.isNumeric(maxAge)) {
                maxAgeDays = Integer.parseInt(maxAge);
            } else {
                return generateError(200);
            }
        }
        List<Integer> userExCatIds = userExCatDAO.getUserExCatIds(user.getId());
        long groupId = -1;
        String orderByFieldName = "addDate";
        boolean orderByDesc = true;
        int offset = 0;
        if (ValidatorUtil.isNotNull(offsetString)) {
            offset = Integer.parseInt(offsetString);
        }
        int pageSize = 100;
        if (ValidatorUtil.isNotNull(limitString)) {
            pageSize = Integer.parseInt(limitString);
        }
        boolean isExtended = false;
        if (ValidatorUtil.isNotNull(extended)) {
            isExtended = extended.equals("1") || extended.equals("true");
        }

        long imdbId = -1;
        if (ValidatorUtil.isNotNull(imdbIdString)) {
            imdbId = Long.parseLong(imdbIdString);
        }

        return searchService.searchMovieReleasesApi(user, serverBaseUrl, query, imdbId, categoryIds, maxAgeDays, userExCatIds, groupId, orderByFieldName, orderByDesc, offset, pageSize, isExtended);
    }

    private ApiResponse getReleaseDetailsResponse(User user, String serverBaseUrl, String extended, String guid) {
        boolean isExtended = false;
        if (ValidatorUtil.isNotNull(extended)) {
            isExtended = extended.equals("1") || extended.equals("true");
        }

        Release release = releaseDAO.findByGuid(guid);
        if (release == null) {
            return generateError(300);
        }  else {
            return searchService.getSingleRelease(user, serverBaseUrl, release, isExtended);
        }
    }

    // todo: replace with enum
    private Error generateError(int code) {
        
        String errorMessage;
        switch (code) {
            case 100:
                errorMessage = "Incorrect user credentials";
                break;
            case 101:
                errorMessage = "Account suspended";
                break;
            case 102:
                errorMessage = "Insufficient privileges/not authorized";
                break;
            case 103:
                errorMessage = "Registration denied";
                break;
            case 104:
                errorMessage = "Registrations are closed";
                break;
            case 105:
                errorMessage = "Invalid registration (Email Address Taken)";
                break;
            case 106:
                errorMessage = "Invalid registration (Email Address Bad Format)";
                break;
            case 107:
                errorMessage = "Registration Failed (Data error)";
                break;
            case 200:
                errorMessage = "Missing parameter";
                break;
            case 201:
                errorMessage = "Incorrect parameter";
                break;
            case 202:
                errorMessage = "No such function";
                break;
            case 203:
                errorMessage = "Function not available";
                break;
            case 300:
                errorMessage = "No such item";
                break;
            default:
                errorMessage = "Unknown error";
                break;
        }

        return new Error(code, errorMessage);
    }
}
