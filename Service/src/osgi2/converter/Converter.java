package osgi2.converter;

import java.math.BigInteger;


public interface Converter {

	public int sum(int a, int b);
	public BigInteger convertTurkishToNumber(String turkishText);
	public String converterFromNumToTr(String number);
	public BigInteger convertEnglishToNumber(String s);
}