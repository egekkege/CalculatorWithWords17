package osgi2.converter.impl;

import osgi2.converter.Converter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

public class ConverterImpl implements Converter {

	private static final Map<String, Integer> turkishNumberMap = new HashMap<>();
	static {
		turkishNumberMap.put("sıfır", 0);
		turkishNumberMap.put("bir", 1);
		turkishNumberMap.put("iki", 2);
		turkishNumberMap.put("üç", 3);
		turkishNumberMap.put("dört", 4);
		turkishNumberMap.put("beş", 5);
		turkishNumberMap.put("altı", 6);
		turkishNumberMap.put("yedi", 7);
		turkishNumberMap.put("sekiz", 8);
		turkishNumberMap.put("dokuz", 9);
		turkishNumberMap.put("on", 10);
		turkishNumberMap.put("yirmi", 20);
		turkishNumberMap.put("otuz", 30);
		turkishNumberMap.put("kırk", 40);
		turkishNumberMap.put("elli", 50);
		turkishNumberMap.put("altmış", 60);
		turkishNumberMap.put("yetmiş", 70);
		turkishNumberMap.put("seksen", 80);
		turkishNumberMap.put("doksan", 90);
		turkishNumberMap.put("yüz", 100);

	}
	protected static List<String> allowedWords = Arrays.asList("zero", "one", "two", "three", "four", "five", "six",
			"seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
			"seventeen", "eighteen", "nineteen", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty",
			"ninety", "hundred", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion",
			"sextillion", "septillion", "octillion", "nonillion", "decillion", "minus", "negative", "and");

	public BigInteger convertTurkishToNumber(String turkishText) {
		boolean isNegative = false;
		String[] words = turkishText.split(" ");
		BigInteger total = BigInteger.ZERO;
		BigInteger temp = BigInteger.ZERO;
		BigInteger finalTotal = BigInteger.ZERO;
		for (String word : words) {
			if (word.equalsIgnoreCase("bin")) {
				if (total.compareTo(BigInteger.ZERO) == 0) {
					total = BigInteger.ONE;
				}
				total = total.multiply(BigInteger.valueOf(1000));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("milyon")) {
				total = total.multiply(BigInteger.valueOf(1000000));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("milyar")) {
				total = total.multiply(BigInteger.valueOf(1000000000));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("trilyon")) {
				total = total.multiply(new BigInteger("1000000000000"));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("katrilyon")) {
				total = total.multiply(new BigInteger("1000000000000000"));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("kentilyon")) {
				total = total.multiply(new BigInteger("1000000000000000000"));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("sekstilyon")) {
				total = total.multiply(new BigInteger("1000000000000000000000"));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("septilyon")) {
				total = total.multiply(new BigInteger("1000000000000000000000000"));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("oktilyon")) {
				total = total.multiply(new BigInteger("1000000000000000000000000000"));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("nonilyon")) {
				total = total.multiply(new BigInteger("1000000000000000000000000000000"));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("desilyon")) {
				total = total.multiply(new BigInteger("1000000000000000000000000000000000"));
				finalTotal = finalTotal.add(total);
				total = BigInteger.ZERO;
				continue;
			} else if (word.equalsIgnoreCase("eksi") || word.equalsIgnoreCase("negatif")) {
				isNegative = true;
				continue;
			}

			temp = BigInteger.valueOf(turkishNumberMap.get(word));

			if (temp.compareTo(BigInteger.TEN) == -1) {

				total = total.add(temp);
			}
			if (temp.compareTo(BigInteger.valueOf(100)) == 0) {

				total = total.multiply(temp);
			}
			if (temp.compareTo(BigInteger.valueOf(9)) == 1 && temp.compareTo(BigInteger.valueOf(100)) == -1) {

				total = total.add(temp);
			}

		}
		finalTotal = finalTotal.add(total);
		if (isNegative) {
			return finalTotal.multiply(BigInteger.valueOf(-1));
		}
		return finalTotal;
	}

	public String converterFromNumToTr(String number) {
		if (number.charAt(0) == '-') { // Eğer negatif sayıysa başa "-" ekler ve kendini kalan kısımla tekrar çağırır
			return "eksi " + converterFromNumToTr(number.substring(1));
		}
		if (number.equals("0")) {
			return "sıfır";
		}
		int maxLength = 36; // Desilyon 33+3 basamaklıdır
		int lengthOfNumber = number.length();
		int remainder = maxLength - lengthOfNumber;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < remainder; i++) {
			sb.append("0");
		}
		sb.append(number);
		number = sb.toString();
		String[] birler = { "", "bir", "iki ", "üç ", "dört ", "beş ", "altı ", "yedi ", "sekiz ", "dokuz " };
		String[] onlar = { "", "on ", "yirmi ", "otuz ", "kırk ", "elli ", "altmış ", "yetmiş ", "seksen ", "doksan " };
		String[] binler = { "desilyon ", "nonilyon ", "oktilyon ", "septilyon ", "seksilyon ", "kentilyon ",
				"katrilyon ", "trilyon ", "milyar ", "milyon ", "bin", "" };
		int[] basamaklar = new int[3];
		String result = "";
		for (int i = 0; i < maxLength / 3; i++) {
			String temp = "";
			// yüzler basamağı
			basamaklar[0] = (int) (number.charAt(i * 3) - '0');
			// onlar basamağı
			basamaklar[1] = (int) (number.charAt(i * 3 + 1) - '0');
			// birler basamağı
			basamaklar[2] = (int) (number.charAt(i * 3 + 2) - '0');
			if (basamaklar[0] == 0)
				temp = ""; // yüzler basamağı boş
			else if (basamaklar[0] == 1)
				temp = "yüz"; // yüzler basamağında 1 varsa
			else
				temp = birler[basamaklar[0]] + "yüz "; // birleştir
			// Yüzler onlar birler basamağını birleştir
			temp += onlar[basamaklar[1]] + birler[basamaklar[2]];
			// basamak değeri oluşmadıysa yani 000 ise binler basamağını ekle
			if (!temp.isEmpty())
				temp += binler[i];
			// birbin yerine bin kullanılır
			if ((i > 1) && (temp.equalsIgnoreCase("birbin")))
				temp = "bin";
			if (temp != "")
				result += temp + " ";
		}

		return result.trim();

	}

	public BigInteger convertEnglishToNumber(String s) {
		String[] words = sentenceToArray(s); // Convert the sentence into an array of words
		return englishToNumberHelper(words); // Call the helper method to perform the conversion
	}

	public BigInteger englishToNumberHelper(String[] words) {
		boolean isNegative = false; // Flag to track if the number is negative
		BigInteger result = BigInteger.valueOf(0); // Temporary result during conversion
		BigInteger finalResult = BigInteger.valueOf(0); // Final result of the conversion

		for (String str : words) {
			if (str.equalsIgnoreCase("zero")) {
				result = result.add(BigInteger.valueOf(0));
			} else if (str.equalsIgnoreCase("one")) {
				result = result.add(BigInteger.valueOf(1));
			} else if (str.equalsIgnoreCase("two")) {
				result = result.add(BigInteger.valueOf(2));
			} else if (str.equalsIgnoreCase("three")) {
				result = result.add(BigInteger.valueOf(3));
			} else if (str.equalsIgnoreCase("four")) {
				result = result.add(BigInteger.valueOf(4));
			} else if (str.equalsIgnoreCase("five")) {
				result = result.add(BigInteger.valueOf(5));
			} else if (str.equalsIgnoreCase("six")) {
				result = result.add(BigInteger.valueOf(6));
			} else if (str.equalsIgnoreCase("seven")) {
				result = result.add(BigInteger.valueOf(7));
			} else if (str.equalsIgnoreCase("eight")) {
				result = result.add(BigInteger.valueOf(8));
			} else if (str.equalsIgnoreCase("nine")) {
				result = result.add(BigInteger.valueOf(9));
			} else if (str.equalsIgnoreCase("ten")) {
				result = result.add(BigInteger.valueOf(10));
			} else if (str.equalsIgnoreCase("eleven")) {
				result = result.add(BigInteger.valueOf(11));
			} else if (str.equalsIgnoreCase("twelve")) {
				result = result.add(BigInteger.valueOf(12));
			} else if (str.equalsIgnoreCase("thirteen")) {
				result = result.add(BigInteger.valueOf(13));
			} else if (str.equalsIgnoreCase("fourteen")) {
				result = result.add(BigInteger.valueOf(14));
			} else if (str.equalsIgnoreCase("fifteen")) {
				result = result.add(BigInteger.valueOf(15));
			} else if (str.equalsIgnoreCase("sixteen")) {
				result = result.add(BigInteger.valueOf(16));
			} else if (str.equalsIgnoreCase("seventeen")) {
				result = result.add(BigInteger.valueOf(17));
			} else if (str.equalsIgnoreCase("eighteen")) {
				result = result.add(BigInteger.valueOf(18));
			} else if (str.equalsIgnoreCase("nineteen")) {
				result = result.add(BigInteger.valueOf(19));
			} else if (str.equalsIgnoreCase("twenty")) {
				result = result.add(BigInteger.valueOf(20));
			} else if (str.equalsIgnoreCase("thirty")) {
				result = result.add(BigInteger.valueOf(30));
			} else if (str.equalsIgnoreCase("forty")) {
				result = result.add(BigInteger.valueOf(40));
			} else if (str.equalsIgnoreCase("fifty")) {
				result = result.add(BigInteger.valueOf(50));
			} else if (str.equalsIgnoreCase("sixty")) {
				result = result.add(BigInteger.valueOf(60));
			} else if (str.equalsIgnoreCase("seventy")) {
				result = result.add(BigInteger.valueOf(70));
			} else if (str.equalsIgnoreCase("eighty")) {
				result = result.add(BigInteger.valueOf(80));
			} else if (str.equalsIgnoreCase("ninety")) {
				result = result.add(BigInteger.valueOf(90));
			} else if (str.equalsIgnoreCase("hundred")) {
				result = result.multiply(BigInteger.valueOf(100));
			} else if (str.equalsIgnoreCase("thousand")) {
				result = result.multiply(BigInteger.valueOf(1000));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("million")) {
				result = result.multiply(BigInteger.valueOf(1000000));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("billion")) {
				result = result.multiply(BigInteger.valueOf(1000000000));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("trillion")) {
				result = result.multiply(BigInteger.valueOf(1000000000000L));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("quadrillion")) {
				result = result.multiply(BigInteger.valueOf(1000000000000000L));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("quintillion")) {
				result = result.multiply(BigInteger.valueOf(1000000000000000000L));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("sextillion")) {
				result = result.multiply(new BigInteger("1000000000000000000000"));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("septillion")) {
				result = result.multiply(new BigInteger("1000000000000000000000000"));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("octillion")) {
				result = result.multiply(new BigInteger("1000000000000000000000000000"));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("nonillion")) {
				result = result.multiply(new BigInteger("1000000000000000000000000000000"));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("decillion")) {
				result = result.multiply(new BigInteger("1000000000000000000000000000000000"));
				finalResult = finalResult.add(result);
				result = BigInteger.valueOf(0);
			} else if (str.equalsIgnoreCase("positive")) {
				isNegative = false;
			} else if (str.equalsIgnoreCase("minus") || str.equalsIgnoreCase("negative")) {
				isNegative = true;
			}
		}

		finalResult = finalResult.add(result); // Adding the temporary result to the final result

		if (isNegative) {
			finalResult = finalResult.multiply(BigInteger.valueOf(-1)); // Making the result negative if needed
		}
		return finalResult; // Returning the final converted number
	}

	@Override
	public int sum(int a, int b) {
		return a + b;
	}

	protected static String[] sentenceToArray(String input) {
		if (input != null && !input.isEmpty()) {
			input = input.replaceAll("-", " "); // Replace hyphens with spaces
			input = input.toLowerCase().replaceAll(" and", " "); // Replace "and" with spaces and convert to lowercase
		}
		assert input != null;
		return input.trim().split("\\s+"); // Splitting the sentence into words based on spaces
	}

	protected static boolean isValidInput(String[] stringArray) {
		for (String str : stringArray) {
			if (!allowedWords.contains(str)) {
				return false; // If a word is not in the allowed list, the input is invalid
			}
		}
		return true; // All words are valid
	}

	static public class Power {
		private int exponent;
		private String name;

		private Power(int exponent, String name) {
			this.exponent = exponent;
			this.name = name;
		}

		public int getExponent() {
			return exponent;
		}

		public String getName() {
			return name;
		}
	}

	static private Power[] SCALE_UNITS = new Power[] { new Power(33, "decillion"), new Power(30, "nonillion"),
			new Power(27, "octillion"), new Power(24, "septillion"), new Power(21, "sextillion"),
			new Power(18, "quintillion"), new Power(15, "quadrillion"), new Power(12, "trillion"),
			new Power(9, "billion"), new Power(6, "million"), new Power(3, "thousand"), new Power(2, "hundred"), };

	public enum Scale {
		SHORT;

		public String getName(int exponent) {
			for (Power unit : SCALE_UNITS) {
				if (unit.getExponent() == exponent) {
					return unit.getName();
				}
			}
			return "";
		}
	}

	static public Scale SCALE = Scale.SHORT;

	static abstract public class AbstractProcessor {
		static protected final String SEPARATOR = " ";
		static protected final int NO_VALUE = -1;

		public String getName(long value) {
			return getName(Long.toString(value));
		}

		abstract public String getName(String value);
	}

	static public class UnitProcessor extends AbstractProcessor {
		static private final String[] TOKENS = new String[] { "one", "two", "three", "four", "five", "six", "seven",
				"eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
				"eighteen", "nineteen" };

		@Override
		public String getName(String value) {
			StringBuilder buffer = new StringBuilder();
			int offset = NO_VALUE;
			int number;
			if (value.length() > 3) {
				number = Integer.valueOf(value.substring(value.length() - 3), 10);
			} else {
				number = Integer.valueOf(value, 10);
			}
			number %= 100;
			if (number < 10) {
				offset = (number % 10) - 1;
			} else if (number < 20) {
				offset = (number % 20) - 1;
			}
			if (offset != NO_VALUE && offset < TOKENS.length) {
				buffer.append(TOKENS[offset]);
			}
			return buffer.toString();
		}
	}

	static public class TensProcessor extends AbstractProcessor {
		static private final String[] TENS = new String[] { "twenty", "thirty", "forty", "fifty", "sixty", "seventy",
				"eighty", "ninety" };
		static private final String UNION_SEPARATOR = "-";
		private UnitProcessor unitProcessor = new UnitProcessor();

		@Override
		public String getName(String value) {
			StringBuilder buffer = new StringBuilder();
			boolean tensFound = false;
			int number;
			if (value.length() > 3) {
				number = Integer.valueOf(value.substring(value.length() - 3), 10);
			} else {
				number = Integer.valueOf(value, 10);
			}
			number %= 100; // keep only two digits
			if (number >= 20) {
				buffer.append(TENS[(number / 10) - 2]);
				number %= 10;
				tensFound = true;
			} else {
				number %= 20;
			}
			if (number != 0) {
				if (tensFound) {
					buffer.append(UNION_SEPARATOR);
				}
				buffer.append(unitProcessor.getName(number));
			}
			return buffer.toString();
		}
	}

	static public class HundredProcessor extends AbstractProcessor {
		private int EXPONENT = 2;
		private UnitProcessor unitProcessor = new UnitProcessor();
		private TensProcessor tensProcessor = new TensProcessor();

		@Override
		public String getName(String value) {
			StringBuilder buffer = new StringBuilder();
			int number;
			if (value.isEmpty()) {
				number = 0;
			} else if (value.length() > 4) {
				number = Integer.valueOf(value.substring(value.length() - 4), 10);
			} else {
				number = Integer.valueOf(value, 10);
			}
			number %= 1000; // keep at least three digits
			if (number >= 100) {
				buffer.append(unitProcessor.getName(number / 100));
				buffer.append(SEPARATOR);
				buffer.append(SCALE.getName(EXPONENT));
			}
			String tensName = tensProcessor.getName(number % 100);
			if (!tensName.isEmpty() && (number >= 100)) {
				buffer.append(SEPARATOR);
			}
			buffer.append(tensName);
			return buffer.toString();
		}
	}

	static public class CompositeBigProcessor extends AbstractProcessor {
		private HundredProcessor hundredProcessor = new HundredProcessor();
		private AbstractProcessor lowProcessor;
		private int exponent;

		public CompositeBigProcessor(int exponent) {
			if (exponent <= 3) {
				lowProcessor = hundredProcessor;
			} else {
				lowProcessor = new CompositeBigProcessor(exponent - 3);
			}
			this.exponent = exponent;
		}

		public String getToken() {
			return SCALE.getName(getPartDivider());
		}

		protected AbstractProcessor getHighProcessor() {
			return hundredProcessor;
		}

		protected AbstractProcessor getLowProcessor() {
			return lowProcessor;
		}

		public int getPartDivider() {
			return exponent;
		}

		@Override
		public String getName(String value) {
			StringBuilder buffer = new StringBuilder();
			String high, low;
			if (value.length() < getPartDivider()) {
				high = "";
				low = value;
			} else {
				int index = value.length() - getPartDivider();
				high = value.substring(0, index);
				low = value.substring(index);
			}
			String highName = getHighProcessor().getName(high);
			String lowName = getLowProcessor().getName(low);
			if (!highName.isEmpty()) {
				buffer.append(highName);
				buffer.append(SEPARATOR);
				buffer.append(getToken());
				if (!lowName.isEmpty()) {
					buffer.append(SEPARATOR);
				}
			}
			if (!lowName.isEmpty()) {
				buffer.append(lowName);
			}
			return buffer.toString();
		}
	}

	static public class DefaultProcessor extends AbstractProcessor {
		static private String MINUS = "minus";
		static private String UNION_AND = "and";
		static private String ZERO_TOKEN = "zero";
		private AbstractProcessor processor = new CompositeBigProcessor(63);

		@Override
		public String getName(String value) {
			boolean negative = false;
			if (value.startsWith("-")) {
				negative = true;
				value = value.substring(1);
			}
			int decimals = value.indexOf(".");
			String decimalValue = null;
			if (0 <= decimals) {
				decimalValue = value.substring(decimals + 1);
				value = value.substring(0, decimals);
			}
			String name = processor.getName(value);
			if (name.isEmpty()) {
				name = ZERO_TOKEN;
			} else if (negative) {
				name = MINUS.concat(SEPARATOR).concat(name);
			}
			if (!(null == decimalValue || decimalValue.isEmpty())) {
				name = name.concat(SEPARATOR).concat(UNION_AND).concat(SEPARATOR)
						.concat(processor.getName(decimalValue)).concat(SEPARATOR)
						.concat(SCALE.getName(-decimalValue.length()));
			}
			return name;
		}
	}

	// static public AbstractProcessor processor;

}