package net.longfalcon.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.web.api.xml.ApiResponse;
import net.longfalcon.web.api.xml.Error;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@Controller
public class ApiController {
    private static final Log _log = LogFactory.getLog(ApiController.class);

    @RequestMapping(value = "/api", produces = "text/xml")
    @ResponseBody
    public ApiResponse xmlApi(@RequestParam(value = "t", required = false) String type,
                              @RequestParam(value = "apikey", required = false) String apiKey,
                              @RequestParam(value = "extended", required = false) String extended,
                              @RequestParam(value = "del", required = false) String del,
                              @RequestParam(value = "q", required = false) String query,
                              @RequestParam(value = "maxage", required = false) String maxAge,
                              @RequestParam(value = "limit", required = false) String limit,
                              @RequestParam(value = "offset", required = false) String offset,
                              @RequestParam(value = "imdbid", required = false) String imdbId,
                              @RequestParam(value = "rid", required = false) String rid,
                              @RequestParam(value = "season", required = false) String season,
                              @RequestParam(value = "ep", required = false) String ep) {
        String function = "s";
        if (ValidatorUtil.isNotNull(type)) {
            if (type.equals("details") || type.equals("d")) {
                function = "d";
            } else if (type.equals("get") || type.equals("g")) {
                function = "d";
            } else if (type.equals("search") || type.equals("s")) {
                function = "d";
            } else if (type.equals("caps") || type.equals("c")) {
                function = "d";
            } else if (type.equals("tvsearch") || type.equals("tv")) {
                function = "d";
            } else if (type.equals("movie") || type.equals("m")) {
                function = "d";
            } else if (type.equals("register") || type.equals("r")) {
                function = "d";
            } else {
                return generateError(202);
            }
        } else {
            return generateError(200);
        }
        return null;
    }

    @RequestMapping(value = "/api", params = "o=json", produces = "application/json")
    @ResponseBody
    public String jsonApi(@RequestParam(value = "t", required = false) String type,
                         @RequestParam(value = "apikey", required = false) String apiKey,
                         @RequestParam(value = "extended", required = false) String extended,
                         @RequestParam(value = "del", required = false) String del,
                         @RequestParam(value = "q", required = false) String query,
                         @RequestParam(value = "maxage", required = false) String maxAge,
                         @RequestParam(value = "limit", required = false) String limit,
                         @RequestParam(value = "offset", required = false) String offset,
                         @RequestParam(value = "imdbid", required = false) String imdbId,
                         @RequestParam(value = "rid", required = false) String rid,
                         @RequestParam(value = "season", required = false) String season,
                         @RequestParam(value = "ep", required = false) String ep) throws JsonProcessingException {

        ApiResponse apiResponse = xmlApi(type, apiKey, extended, del, query, maxAge, limit, offset, imdbId, rid, season, ep);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(apiResponse);
    }

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
                errorMessage = "Insufficient priviledges/not authorized";
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
