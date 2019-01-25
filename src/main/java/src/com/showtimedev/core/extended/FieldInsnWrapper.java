package src.com.showtimedev.core.extended;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.objectweb.asm.tree.FieldInsnNode;

@RequiredArgsConstructor
public class FieldInsnWrapper{
	
	@Getter
	private final FieldInsnNode instruction;
	
	@Getter
	private final MethodWrapper wrapper;
}
