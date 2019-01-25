package src.com.showtimedev.core.analysis.stack_tracer;

public class LocalReferenceNotFoundException extends Exception{
	
	public LocalReferenceNotFoundException(){
	}
	
	public LocalReferenceNotFoundException(String message){
		super(message);
	}
	
	public LocalReferenceNotFoundException(String message, Throwable cause){
		super(message, cause);
	}
	
	public LocalReferenceNotFoundException(Throwable cause){
		super(cause);
	}
	
	public LocalReferenceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
