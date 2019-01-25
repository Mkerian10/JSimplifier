package src.com.showtimedev.core.analysis.stack_tracer;

public class UnexpectedStackElementException extends Exception{
	
	public UnexpectedStackElementException(){
	}
	
	public UnexpectedStackElementException(String message){
		super(message);
	}
	
	public UnexpectedStackElementException(String message, Throwable cause){
		super(message, cause);
	}
	
	public UnexpectedStackElementException(Throwable cause){
		super(cause);
	}
	
	public UnexpectedStackElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
