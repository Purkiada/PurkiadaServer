package cz.mbucek.purkiadaserver.utilities;

public class ArrayUtils {

	@SuppressWarnings("unchecked")
	public static <T> T[] concat(T[] a, T... b) {
		if(a == null)
			return b;
		if(b == null)
			return a;
		T[] arr = (T[]) new Object[a.length + b.length];
		for(int i = 0; i < a.length; i++)
			arr[i] = a[i];
		for(int i = a.length; i < arr.length; i++)
			arr[i] = b[i - a.length];
		return arr;
	}
	
	public static String[] concat(String[] a, String... b) {
		if(a == null)
			return b;
		if(b == null)
			return a;
		String[] arr = new String[a.length + b.length];
		for(int i = 0; i < a.length; i++)
			arr[i] = a[i];
		for(int i = a.length; i < arr.length; i++)
			arr[i] = b[i - a.length];
		return arr;
	}
}