package ro.info.asticlib.clustering;


public class TfIdfAcceptanceRule implements AcceptanceRule {

	public static final float ACCEPTACE_FACTOR = 0.3f;
	
	/**
	 * args[0] = tfidf for word ;
	 * args[1] = N - nr de documente
	 */
	@Override
	public boolean apply(Object ... args) {
		double max = Math.log((int)args[1]);
		float tfIdf = (float) args[0] ;
		return tfIdf >= ACCEPTACE_FACTOR*max; 
	}

}
