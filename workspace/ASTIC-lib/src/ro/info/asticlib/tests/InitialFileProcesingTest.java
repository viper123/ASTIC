package ro.info.asticlib.tests;

import ro.info.asticlib.clustering.InitialFileProcesing;
import ro.info.asticlib.files.Croisers;

public class InitialFileProcesingTest {

	public void test(){
		Croisers.instance().processFileTree("D://ROOT", new InitialFileProcesing());
	}
}