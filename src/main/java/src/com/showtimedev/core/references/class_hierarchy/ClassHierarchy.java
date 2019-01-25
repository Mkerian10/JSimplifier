package src.com.showtimedev.core.references.class_hierarchy;

import org.objectweb.asm.tree.ClassNode;
import src.com.showtimedev.utils.Immutable;

import java.util.*;

public class ClassHierarchy{
	
	private ClassHierarchy(){
	}
	
	public static ClassHierarchy createHierarchy(@Immutable Collection<ClassNode> nodes){
		ClassHierarchy ch = new ClassHierarchy();
		ch.initHierarchy(new HashSet<>(nodes), nodes);
		return ch;
	}
	
	private Map<String, ClassHierarchyNode> namesToHierarchy = new HashMap<>();
	
	private void initHierarchy(HashSet<ClassNode> classSet, @Immutable Collection<ClassNode> classes){
		initParentClasses(classSet);
		initInterfaces(classes);
	}
	
	private void initParentClasses(HashSet<ClassNode> classSet){
		boolean flag; //flag used to check for when no more classes can be found
		
		do{
			flag = false;
			Iterator<ClassNode> iterator = classSet.iterator();
			while(iterator.hasNext()){
				
				ClassNode curr = iterator.next();
				
				if(curr.superName.contains("java") || namesToHierarchy.containsKey(curr.superName)){
					flag = true;
					
					ClassHierarchyNode node = new ClassHierarchyNode(curr);
					
					if(namesToHierarchy.containsKey(curr.superName)){
						ClassHierarchyNode superNode = namesToHierarchy.get(curr.superName);
						node.addSuperclass(superNode);
					}
					
					namesToHierarchy.put(curr.name, node);
					iterator.remove(); //remove from set to signal we're done with it
				}
			}
			
		}while(flag && classSet.size() > 0);
	}
	
	private void initInterfaces(@Immutable Collection<ClassNode> classes){
		//iterate through and assign interfaces
		for(ClassNode cn : classes){
			for(String s : cn.interfaces){
				if(s.contains("java") || !namesToHierarchy.containsKey(s) || !namesToHierarchy.containsKey(cn.name))
					continue;
				
				ClassHierarchyNode intf = namesToHierarchy.get(s);
				ClassHierarchyNode cls = namesToHierarchy.get(cn.name);
				cls.addInterface(intf);
			}
		}
	}
	
	public ClassHierarchyNode getClassHierarchyNode(String name){
		return namesToHierarchy.get(name);
	}
}
