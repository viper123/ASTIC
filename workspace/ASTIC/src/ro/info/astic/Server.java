package ro.info.astic;

public class Server {
	
	public static final int COMMAND_LOOP = 1;
	public static final int COMMAND_STOP = -1;

	private int command;
	private Object command_LOCK = new Object();
	
	public  void start(){
		synchronized (command_LOCK) {
			command = COMMAND_LOOP;
		}
		loop();
	}
	
	public  void stop(){
		synchronized (command_LOCK) {
			command = COMMAND_STOP;
		}
	}
	
	private void loop(){
		System.out.println("start waiting for commands");
		while(getCommand()!=COMMAND_STOP){
			//wait for messages
			
			sleep(100);
		}
	}
	
	private int getCommand(){
		synchronized (command_LOCK) {
			return command;
		}
	}
	
	private void sleep(int miliseconds){
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}