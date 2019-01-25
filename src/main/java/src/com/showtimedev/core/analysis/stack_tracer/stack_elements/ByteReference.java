package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class ByteReference extends NumberReference<ByteReference>{
	
	public ByteReference(byte value){
		this.value = value;
	}
	
	private final byte value;
	
	@Override
	protected ByteReference cloneNumber(){
		return new ByteReference(value);
	}
}
