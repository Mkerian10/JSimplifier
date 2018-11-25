package src.com.showtimedev.loader;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.*;
import src.com.showtimedev.utils.BCUtils;
import src.com.showtimedev.wrappers.FieldWrapper;
import src.com.showtimedev.wrappers.MethodWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputLoader{
	
	public InputLoader(String outputDir){
		this.outputDir = outputDir;
	}
	
	private final String outputDir;
	
	/**
	 * Full list of class nodes in the given project
	 */
	final List<ClassNode> classNodes = new ArrayList<>();
	
	/**
	 * Mapping methods to their respective classes
	 */
	final Map<MethodWrapper, ClassNode> methodOwners = new HashMap<>();
	
	/**
	 * Mapping Methods to locations where they are called. They can be called by MethodNodes or by ClassNodes
	 */
	final Map<MethodWrapper, List<MethodWrapper>> methodReferences = new HashMap<>();
	
	/**
	 * Mapping fields to their respective classes, local variables are excluded
	 */
	final Map<FieldWrapper, ClassNode> fieldOwners = new HashMap<>();
	
	/**
	 * Mapping fields to their reference locations, either classes for class variables or methods for local variables
	 */
	final Map<FieldWrapper, List<MethodWrapper>> fieldReferences = new HashMap<>();
	
	public Container makeContainer(){
		return new Container(this);
	}
	
	public void load(){
		ArrayList<String> files = new ArrayList<>();
		FileExplorer.classFiles(files, "/Users/mkerian/Desktop/Deobfuscator/out/production/classes");
		loadInitialClasses(files);
		
		loadMethods();
		loadFields();
		loadMethodReferences();
		loadFieldReferences();
	}
	
	private void loadMethods(){
		for(ClassNode cn : classNodes){
			for(MethodNode mn : cn.methods){
				methodOwners.put(new MethodWrapper(mn, cn), cn);
			}
		}
	}
	
	private void loadFields(){
		for(ClassNode cn : classNodes){
			for(FieldNode fn : cn.fields){
				fieldOwners.put(new FieldWrapper(fn, cn), cn);
			}
		}
	}
	
	private void loadMethodReferences(){
		for(MethodWrapper mn : methodOwners.keySet()){
			for(AbstractInsnNode instruc : mn.getNode().instructions.toArray()){
				if(instruc instanceof MethodInsnNode){
					addToMultiMap(methodReferences, getMethodNode(((MethodInsnNode) instruc).name), new MethodWrapper(mn.getNode(), mn.getOwner()));
				}
			}
		}
	}
	
	private void loadFieldReferences(){
		for(MethodWrapper mn : methodOwners.keySet()){
			for(AbstractInsnNode instruc : mn.getNode().instructions.toArray()){
				if(instruc instanceof FieldInsnNode){
					addToMultiMap(fieldReferences, getFieldNode(((FieldInsnNode) instruc).name), new MethodWrapper(mn.getNode(), mn.getOwner()));
				}
			}
		}
	}
	
	private <K> void addToMultiMap(Map<K, List<MethodWrapper>> map, K key, MethodWrapper value){
		map.putIfAbsent(key, new ArrayList<>());
		map.get(key).add(value);
	}
	
	private void loadInitialClasses(List<String> files){
		for(String s : files){
			ClassNode node = new ClassNode();
			try{
				s = s.split("classes/")[1];
				s = s.replace("/", ".");
				
				Class<?> cls = Class.forName(s);
				String classPath = cls.getName().replace(".", "/") + ".class";
				ClassReader reader = new ClassReader(cls.getClassLoader().getResourceAsStream(classPath));
				reader.accept(node, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
				classNodes.add(node);
				
			}catch(IOException e){
				e.printStackTrace();
			}catch(ClassNotFoundException e){
				System.out.println("fucl");
				e.printStackTrace();
			}
		}
	}
	
	private ClassNode getClassNode(String name){
		return classNodes.stream().filter(classNode -> classNode.name.equals(name)).findFirst().orElse(null);
	}
	
	private MethodWrapper getMethodNode(String name){
		return methodOwners.keySet().stream().filter(methodNode -> methodNode.getNode().name.equals(name)).findFirst().orElse(null);
	}
	
	private FieldWrapper getFieldNode(String name){
		return fieldOwners.keySet().stream().filter(fieldNode -> fieldNode.getFieldNode().name.equals(name)).findFirst().orElse(null);
	}
	
}
