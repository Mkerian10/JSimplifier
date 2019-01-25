package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class VoidReference extends StackReference<VoidReference>{
	
	@Override
	protected VoidReference cloneSelf(){
		return new VoidReference();
	}
}
