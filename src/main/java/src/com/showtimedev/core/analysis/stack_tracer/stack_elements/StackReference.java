package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public abstract class StackReference<K extends StackReference<K>>{

	private boolean unknown = false;

	public void setUnknown(boolean unknown){
		this.unknown = unknown;
	}
	
	public boolean isUnknown(){
		return unknown;
	}
	
	public K duplicate(){
		K k = cloneSelf();
		k.setUnknown(isUnknown());
		return k;
	}
	
	protected abstract K cloneSelf();
}
