package ro.info.asticlib.tests;

import ro.info.asticlib.clustering.ClusterizerL0;
import ro.info.asticlib.files.Croisers;

public class ClusterizerL0Tests {

	public void testFileProcesing(){
		Croisers.instance().processFileTree("D://ROOT", new ClusterizerL0());
	}
}