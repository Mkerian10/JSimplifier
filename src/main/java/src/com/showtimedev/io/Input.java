package src.com.showtimedev.io;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Input{
	
	public static Collection<ClassNode> importClassesFromJarFile(JarFile jf) throws IOException{
		
		List<ClassNode> classes = new ArrayList<>();
		
		Enumeration<JarEntry> entries = jf.entries();
		
		while(entries.hasMoreElements()){
			JarEntry je = entries.nextElement();
			
			ClassNode cn = new ClassNode();
			try{
				ClassReader cr = new ClassReader(jf.getInputStream(je));
				cr.accept(cn, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
				classes.add(cn);
			}catch(IOException e){
				e.printStackTrace();
				throw new IOException("Error accessing " + jf.getName() + " input stream.");
			}
		}
		
		return classes;
	}
	
}
