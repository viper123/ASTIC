package ro.info.astic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class Log extends PrintStream{
	
	JTextPane txtOutput;

	public Log(String arg0) throws FileNotFoundException {
		super(arg0);
	}
	
	public Log(JTextPane output) throws FileNotFoundException{
		super(new File("print.txt"));
		this.txtOutput = output;
	}
	
	
	@Override
	public void write(byte[] buf, int off, int len) {
		super.write(buf, off, len);
		String buffer = new String(buf,off,len);
		String currentText = txtOutput.getText();
		if(currentText == null){
			currentText = "";
		}
		currentText = currentText + buffer;
		//SwingUtilities.invokeLater(new UpdateTextRunnable(currentText));
		txtOutput.setText(currentText);
		txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
		//System.err.println(buffer);
	}
	
	

	public static void setup(JTextPane outPut) throws FileNotFoundException{
		System.setOut(new Log(outPut));
		
	}
	
	class UpdateTextRunnable implements Runnable{
		public String text;
		
		public UpdateTextRunnable(String t){
			this.text = t;
		}

		@Override
		public void run() {
			txtOutput.setText(text);
			txtOutput.setCaretPosition(txtOutput.getDocument().getLength());
		}
	}
}
