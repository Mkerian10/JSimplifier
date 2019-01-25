package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class StringReference extends StackReference<StringReference>{
	
	public StringReference(String string){
		this.string = string;
	}
	
	private final String string;
	
	@Override
	protected StringReference cloneSelf(){
		return new StringReference(string);
	}
}
