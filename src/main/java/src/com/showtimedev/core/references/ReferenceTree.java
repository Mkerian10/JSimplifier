package src.com.showtimedev.core.references;

import com.google.common.collect.HashMultimap;
import org.objectweb.asm.tree.*;
import src.com.showtimedev.core.extended.FieldInsnWrapper;
import src.com.showtimedev.core.extended.FieldWrapper;
import src.com.showtimedev.core.extended.MethodInsnWrapper;
import src.com.showtimedev.core.extended.MethodWrapper;
import src.com.showtimedev.core.references.class_hierarchy.ClassHierarchy;
import src.com.showtimedev.utils.InsnUtils;

public class ReferenceTree{
	
	public ReferenceTree(ClassHierarchy hierarchy, IdentityTables tables){
		this.hierarchy = hierarchy;
		this.tables = tables;
		initReferences();
	}
	
	private final ClassHierarchy hierarchy;
	
	private final IdentityTables tables;
	
	private HashMultimap<MethodWrapper, MethodInsnWrapper> methodToCallers = HashMultimap.create();
	
	private HashMultimap<FieldWrapper, FieldInsnWrapper> fieldToCallers = HashMultimap.create();
	
	private void initReferences(){
		for(ClassNode cn: tables.getClasses()){
			for(MethodNode mn: cn.methods){
				for(AbstractInsnNode insn: mn.instructions.toArray()){
					if(insn instanceof MethodInsnNode){
						
						MethodWrapper called = tables.getMethodWrapper(InsnUtils.formatMethodInsn((MethodInsnNode)insn));
						MethodWrapper callerMethod = tables.getMethodWrapper(cn.name + "#" + mn.name + mn.desc);
						MethodInsnWrapper caller = new MethodInsnWrapper((MethodInsnNode)insn, callerMethod);
						resolveMethodReference(caller, called);
						
					}else if(insn instanceof FieldInsnNode){
					
						FieldWrapper called = tables.getFieldWrapper(InsnUtils.formatFieldInsn((FieldInsnNode)insn));
						MethodWrapper callerMethod = tables.getMethodWrapper(cn.name + "#" + mn.name + mn.desc);
						FieldInsnWrapper caller = new FieldInsnWrapper((FieldInsnNode)insn, callerMethod);
						resolveFieldReferences(caller, called);
					}
				}
			}
		}
	}
	
	private void resolveMethodReference(MethodInsnWrapper caller, MethodWrapper called){
	
	}
	
	private  void resolveFieldReferences(FieldInsnWrapper caller, FieldWrapper called){
	
	}
}
