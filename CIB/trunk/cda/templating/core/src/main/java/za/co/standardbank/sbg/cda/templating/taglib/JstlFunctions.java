package za.co.standardbank.sbg.cda.templating.taglib;

import java.util.Collection;

public class JstlFunctions {
	
	public static boolean contains(Collection collection, Object object) {
		return collection.contains(object);
	}
}
