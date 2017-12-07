package controllers;

import play.*;
import play.mvc.*;
import utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    
    public static void getStupidData(String year, int crimetype) throws SQLException {
    	if(StringUtils.isEmpty(year) || year.equals("undefined")){
    		year = "2010";
    	}
    	if(crimetype ==0 ) {
    		crimetype = 1;
    	}
    	
    	
    	Connection conn =  DatabaseUtils.getConnection();
    	
    	String sql = "Select lat,lon from crime where classification="+crimetype+" and extract(year from date) = "+year;
    	Logger.info("sql:  %s", sql);

    	ResultSet rs = conn.prepareStatement(sql).executeQuery();

    	JsonArray all = new JsonArray();
    	
    	while(rs.next()) {
    		JsonObject obj = new JsonObject();
    		double lat = rs.getDouble(1);
    		double lng = rs.getDouble(2);
    		obj.addProperty("lat", lat);
    		obj.addProperty("lng", lng);
    		obj.addProperty("count", 1);;
    		all.add(obj);
    	}
    	conn.close();
    	
    	renderJSON(all);
    }
    
}