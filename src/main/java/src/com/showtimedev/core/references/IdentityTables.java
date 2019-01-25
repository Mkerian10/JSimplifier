package src.com.showtimedev.core.references;

import lombok.AccessLevel;
import lombok.Getter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import src.com.showtimedev.core.extended.FieldWrapper;
import src.com.showtimedev.core.extended.MethodWrapper;

import java.util.*;

public class IdentityTables{
	
	public IdentityTables(Collection<ClassNode> classes){
		this.classes = new ArrayList<>(classes);
	}
	
	@Getter
	private final List<ClassNode> classes;
	
	public ClassNode getClassNode(String name){
		return getClassNodeMap().get(name);
	}
	
	@Getter(value = AccessLevel.PRIVATE, lazy = true)
	private final Map<String, ClassNode> classNodeMap = initClassNodeMap();
	
	private Map<String, ClassNode> initClassNodeMap(){
		Map<String, ClassNode> map = new HashMap<>();
		for(ClassNode cn: Objects.requireNonNull(classes)){
			map.put(cn.name, cn);
		}
		return map;
	}
	
	public MethodWrapper getMethodWrapper(String formattedName){
		return getMethodWrapperMap().get(formattedName);
	}
	
	@Getter(value = AccessLevel.PRIVATE, lazy = true)
	private final Map<String, MethodWrapper> methodWrapperMap = initMethodWrapperMap();
	
	private Map<String, MethodWrapper> initMethodWrapperMap(){
		Map<String, MethodWrapper> map = new HashMap<>();
		for(ClassNode cn: Objects.requireNonNull(classes)){
			for(MethodNode mn: cn.methods){
				MethodWrapper mw = new MethodWrapper(cn, mn);
				map.put(mw.getFormatted(), mw);
			}
		}
		return map;
	}
	
	public FieldWrapper getFieldWrapper(String formattedName){
		return getFieldWrapperMap().get(formattedName);
	}
	
	@Getter(value = AccessLevel.PRIVATE, lazy = true)
	private final Map<String, FieldWrapper> fieldWrapperMap = initFieldWrapperMap();
	
	private Map<String, FieldWrapper> initFieldWrapperMap(){
		Map<String, FieldWrapper> map = new HashMap<>();
		for(ClassNode cn: Objects.requireNonNull(classes)){
			for(FieldNode fn: cn.fields){
				FieldWrapper mw = new FieldWrapper(cn, fn);
				map.put(mw.getFormatted(), mw);
			}
		}
		return map;
	}
}
