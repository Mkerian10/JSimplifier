package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class ClassReference extends StackReference<ClassReference>{
	
	public ClassReference(String clazz){
		this.clazz = clazz;
	}
	
	private final String clazz;
	
	@Override
	protected ClassReference cloneSelf(){
		return new ClassReference(clazz);
	}
}
