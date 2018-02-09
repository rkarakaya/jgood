package com.akarge.jgood.dao.mongo;


/**
 * @author Ramazan Karakaya 
 *
 */
public interface MongoConstants {

	int MAX_RADIUS = 3935;

	String ID = "_id";
	String $ = "$";

	String ADD_TO_SET = "$addToSet";
	String ALL = "$all";
	String AND = "$and";
	String BIT = "$bit";
	String BOX = "$box";
	String CENTER = "$center";
	String CENTER_SPHERE = "$centerSphere";
	String COMMENT = "$comment";
	String EACH = "$each";
	String ELEM_MATCH = "$elemMatch";
	String EXISTS = "$exists";
	String EXPLAIN = "$explain";
	String GT = "$gt";
	String GTE = "$gte";
	String HINT = "$hint";
	String IN = "$in";
	String INC = "$inc";
	String ISOLATED = "$isolated";
	String LT = "$lt";
	String LTE = "$lte";
	String MAX = "$max";
	String MAX_DISTANCE = "$maxDistance";
	String MAX_SCAN = "$maxScan";
	String MIN = "$min";
	String MOD = "$mod";
	String NATURAL = "$natural";
	String NE = "$ne";
	String NEAR = "$near";
	String NEAR_SPHERE = "$nearSphere";
	String NIN = "$nin";
	String NOR = "$nor";
	String NOT = "$not";
	String OR = "$or";
	String ORDER_BY = "$orderby";
	String POLYGON = "$polygon";
	String POP = "$pop";
	String PULL = "$pull";
	String PULL_ALL = "$pullAll";
	String PUSH = "$push";
	String PUSH_ALL = "$pushAll";
	String QUERY = "$query";
	String REGEX = "$regex";
	String RENAME = "$rename";
	String RETURN_KEY = "$returnKey";
	String SET = "$set";
	String SHOW_DISK_LOC = "$showDiskLoc";
	String SIZE = "$size";
	String SNAPSHOT = "$snapshot";
	String TYPE = "$type";
	String UNIQUE_DOCS = "$uniqueDocs";
	String UNSET = "$unset";
	String WHERE = "$where";
	String WITHIN = "$within";
	String INDEX_2D = "2d";
	String UNIQUE = "unique";
	String SPARSE = "sparse";
	String INDEX_NAME = "name";
	String GROUP = "$group";
	String MATCH = "$match";
	String PROJECT = "$project";
	String UNWIND = "$unwind";
	String YEAR = "$year";
	String MONTH = "$month";
	String DAY_OF_MONTH = "$dayOfMonth";
	String HOUR = "$hour";
	String MINUTE = "$minute";
	String SECOND = "$second";
	String MILLISECOND = "$millisecond";
	String SUM = "$sum";
	String AVG = "$avg";
	String IF_NULL = "$ifNull";
	String TO_UPPER = "$toUpper";
	String TO_LOWER = "$toLower";
	String SLICE = "$slice";
	String DROP_DUPLICATES = "dropDups";
	String BACKGROUND = "background";
	String OPTIONS = "$options";
	String OPTION_CASE_INSENSITIVE = "i";
	String CURRENT_DATE = "$currentDate";
	String TEXT = "$text";
	String SEARCH = "$search";
	String LANGUAGE = "$language";
	String LANGUAGE_TR = "tr";
	String CASE_SENSITIVE = "$caseSensitive";

	int SORT_ASC = 1;
	int SORT_DESC = -1;

}
