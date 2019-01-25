package src.com.showtimedev.core.extended;

import lombok.RequiredArgsConstructor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

@RequiredArgsConstructor
public class FieldWrapper{
	
	private final ClassNode classNode;
	
	private final FieldNode fieldNode;
	
	public String getFormatted(){
		return classNode.name + "." + fieldNode.name + fieldNode.desc;
	}
	
}
