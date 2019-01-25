package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class NullReference extends StackReference<NullReference>{
	
	@Override
	protected NullReference cloneSelf(){
		return new NullReference();
	}
}
