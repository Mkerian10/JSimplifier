package src.com.showtimedev.core.references.class_hierarchy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import src.com.showtimedev.core.extended.MethodWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class ClassHierarchyNode{
	
	@Getter
	private final Set<ClassHierarchyNode> interfaces = new HashSet<>();
	
	@Getter
	private final Set<ClassHierarchyNode> children = new HashSet<>();
	
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private ClassHierarchyNode superClass;
	
	@Getter
	private final ClassNode classNode;
	
	void addSuperclass(ClassHierarchyNode superClass){
		superClass.children.add(this);
		this.superClass = superClass;
	}
	
	void addInterface(ClassHierarchyNode intf){
		intf.children.add(this);
		this.interfaces.add(intf);
		children.forEach(classHierarchyNode -> classHierarchyNode.addInterface(intf));
	}
	
	public List<MethodWrapper> getRelatedMethods(String desc, String name){
		List<MethodWrapper> list = new ArrayList<>();
		getRelatedMethodsRecursively(list, desc, name, false);
		children.forEach(chn -> chn.getRelatedMethodsRecursively(list, desc, name, true));
	
		return list;
	}
	
	private void getRelatedMethodsRecursively(List<MethodWrapper> methodWrappers, String desc, String name, boolean forward){
	
		for(MethodNode mn: classNode.methods){
			if(mn.name.equalsIgnoreCase(name) && desc.equalsIgnoreCase(mn.desc)){
				methodWrappers.add(new MethodWrapper(classNode, mn));
			}
		}
		
		if(forward){
			children.forEach(chn -> chn.getRelatedMethodsRecursively(methodWrappers, desc, name, true));
		}else{
			if(superClass != null) superClass.getRelatedMethodsRecursively(methodWrappers, desc, name, false);
			interfaces.forEach(chn -> chn.getRelatedMethodsRecursively(methodWrappers, desc, name, false));
		}
	}
	
}
