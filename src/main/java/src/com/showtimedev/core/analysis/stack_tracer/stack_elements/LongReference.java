package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class LongReference extends NumberReference<LongReference>{
	
	public LongReference(long value){
		this.value = value;
	}
	
	private long value;
	
	public long getValue(){
		return value;
	}
	
	public void setValue(long value){
		this.value = value;
	}
	
	@Override
	protected LongReference cloneNumber(){
		return new LongReference(value);
	}
}
