package src.com.showtimedev.loader;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import src.com.showtimedev.wrappers.FieldWrapper;
import src.com.showtimedev.wrappers.MethodWrapper;

import java.util.List;
import java.util.Map;

public class Container{
	
	Container(InputLoader loader){
		this.classNodes = loader.classNodes;
		this.methodOwners = loader.methodOwners;
		this.fieldOwners = loader.fieldOwners;
		this.methodReferences = loader.methodReferences;
		this.fieldReferences = loader.fieldReferences;
	}
	
	/**
	 * Full list of class nodes in the given project
	 */
	private final List<ClassNode> classNodes;
	
	/**
	 * Mapping methods to their respective classes
	 */
	private final Map<MethodWrapper, ClassNode> methodOwners;
	
	/**
	 * Mapping Methods to locations where they are called. They can be called by MethodNodes or by ClassNodes
	 */
	private final Map<MethodWrapper, List<MethodWrapper>> methodReferences;
	
	/**
	 * Mapping fields to their respective classes, local variables are excluded
	 */
	private final Map<FieldWrapper, ClassNode> fieldOwners;
	
	/**
	 * Mapping fields to their reference locations, either classes for class variables or methods for local variables
	 */
	private final Map<FieldWrapper, List<MethodWrapper>> fieldReferences;
	
	public List<ClassNode> getClassNodes(){
		return classNodes;
	}
	
	public Map<MethodWrapper, ClassNode> getMethodOwners(){
		return methodOwners;
	}
	
	public Map<MethodWrapper, List<MethodWrapper>> getMethodReferences(){
		return methodReferences;
	}
	
	public Map<FieldWrapper, ClassNode> getFieldOwners(){
		return fieldOwners;
	}
	
	public Map<FieldWrapper, List<MethodWrapper>> getFieldReferences(){
		return fieldReferences;
	}
}
