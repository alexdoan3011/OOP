public class Wrapper {

	Wrapper() {

	}

	public Long addIntegers(Integer a, Integer b) {
		long q, r; // declare a primitive int
		Integer oa = new Integer(a); // wrap in Integer object
		q = oa.intValue();// unwrap the Integer object

		Integer oaa = b; // wrap in Integer object
		r = oaa; // unwrap the Integer object
		return q + r;
	}

	public Double divideFloats(Float a, Float b) {

		double i, j;
		Float p = a;
		i = p.doubleValue();
		Float y = b;
		j = y.doubleValue();
		double t = i / j;

		Double l = new Double(t);
		if (l.isInfinite() || l.isNaN()) {
			return null;
		} else {
			return t;
		}
	}

	public static void main(String[] args) {
		Wrapper wrap = new Wrapper();
		System.out.println(wrap.addIntegers(5, 5));
		System.out.println(wrap.divideFloats(2.0f, 13.0f));
	}

}