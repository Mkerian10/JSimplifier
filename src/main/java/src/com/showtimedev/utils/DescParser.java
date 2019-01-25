package src.com.showtimedev.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DescParser{
	
	private final static Pattern paramPattern = Pattern.compile("([JZBSIDCF]|L(.*?);)");
	
	public DescParser(String desc){
		this.desc = desc;
		String params = desc.substring(1, desc.indexOf(")"));
		String ret = desc.substring(desc.indexOf(")") + 1);
		ret = ret.startsWith("L") ? ret.substring(1, ret.length() - 1) : ret;
		this.ret = ret;
		this.parameters = parseParams(params);
	}
	
	private String[] parseParams(String desc){
		Matcher m = paramPattern.matcher(desc);
		List<String> strings = new ArrayList<>();
		while(m.find()){
			String s = m.group();
			s = s.startsWith("L") ? s.substring(1, s.length() - 1) : s;
			strings.add(s);
		}
		return strings.toArray(new String[0]);
	}
	
	private final String desc;
	
	private final String ret;
	
	private final String[] parameters;
	
	public String getReturn(){
		return ret;
	}
	
	public int paramCount(){
		return parameters.length;
	}
	
	
}
