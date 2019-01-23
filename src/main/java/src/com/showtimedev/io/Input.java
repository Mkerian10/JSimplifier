package src.com.showtimedev.io;


import lombok.experimental.UtilityClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.ASMifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
public class Input{
	
	public Collection<ClassNode> importClassesFromJarFile(JarFile jf) throws IOException{
		
		List<ClassNode> classes = new ArrayList<>();
		
		Enumeration<JarEntry> entries = jf.entries();
		
		while(entries.hasMoreElements()){
			JarEntry je = entries.nextElement();
			
			ClassNode cn = new ClassNode();
			try{
				if(je.getName().endsWith(".class")){
					ClassReader cr = new ClassReader(jf.getInputStream(je));
					cr.accept(cn, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
					classes.add(cn);
				}
			}catch(IOException e){
				e.printStackTrace();
				throw new IOException("Error accessing " + jf.getName() + " input stream.");
			}
		}
		
		return classes;
	}
	
}
