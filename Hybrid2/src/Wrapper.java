/*
* Name: Van Nam Doan
* Student ID: 040943291
* Course & Section: CST8132 312
* Assignment: Hybrid 2
* Date: Jan 16, 2019
*/
public class Wrapper {
public Long addIntegers(Integer a, Integer b) {
	int oa=a;//unwrap Integer a into a primitive int type variable
	int ob=b;//unwrap Integer b into a primitive int type variable
	Long total=(long) (oa+ob);//calculating a+b, casting the result to long type, wrapping it in a Long object
	return total;
}
public Long addIntegersBeforeJava5(Integer a, Integer b) {
	int oa=a.intValue();//unwrap Integer a into a primitive int type variable
	int ob=b.intValue();//unwrap Integer b into a primitive int type variable
	Long total= new Long((long) (oa+ob));//calculating a+b, casting the result to long type, wrapping it in a Long object
	return total;
}
public Double divideFloats(Float a, Float b) {
	double oa=a;//unwrap Float a into a primitive double type variable
	double ob=b;//unwrap Float b into a primitive double type variable
	Double div=oa/ob;//calculating a/b, wrapping it in a Double object
	if (div.isInfinite()||div.isNaN())//catching infinity and NaN value and turn it into null
		div = null;
	return div;
}
public static void main(String arg[]) {
	Wrapper obj=new Wrapper();
	System.out.println(obj.addIntegers(10,15));
	System.out.println(obj.divideFloats((float) 10.0, (float) 0));	
}
}
