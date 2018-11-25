package src.com.showtimedev.loader;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class FileExplorer{
	
	public static void classFiles(List<String> strings, String root){
		File f = new File(root);
		if(isClassFile(f)){
			String s = f.getAbsolutePath();
			if(s.contains(".class")){
				s = s.split("\\.class")[0];
			}
			strings.add(s);
		}else if(f.isDirectory() && f.listFiles() != null){
			for(File lf : Objects.requireNonNull(f.listFiles())){
				classFiles(strings, lf.getAbsolutePath());
			}
		}
	}
	
	static boolean isClassFile(File f){
		return f != null && f.isFile() && f.getAbsolutePath().endsWith(".class");
	}
	
	
}
