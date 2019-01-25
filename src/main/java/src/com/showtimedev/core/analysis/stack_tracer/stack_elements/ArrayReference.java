package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class ArrayReference extends StackReference<ArrayReference>{
	
	public ArrayReference(StackReference[] references){
		this.references = references;
	}
	
	private final StackReference[] references;
	
	@Override
	protected ArrayReference cloneSelf(){
		return new ArrayReference(references.clone());
	}
}
