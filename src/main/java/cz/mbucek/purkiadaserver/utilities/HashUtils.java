package cz.mbucek.purkiadaserver.utilities;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Random;

public class HashUtils {

	public static String generateRandomPassword(int length) {
		return generateRandomPassword().substring(0, length);
	}
	
	public static String generateRandomPassword() {
		var random = new Random();
		String base = String.valueOf(random.nextDouble() + ZonedDateTime.now().toEpochSecond() + random.nextFloat());
		try {
			return toHexString(getSHA1(base));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
	    {  
	        MessageDigest md = MessageDigest.getInstance("SHA-256");  
	        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
	    } 
	    
	    public static String toHexString(byte[] hash) 
	    { 
	        BigInteger number = new BigInteger(1, hash);  
	  
	        StringBuilder hexString = new StringBuilder(number.toString(16));  
	  
	        while (hexString.length() < 32)  
	        {  
	            hexString.insert(0, '0');  
	        }  
	  
	        return hexString.toString();  
	    } 
	    
	    public static byte[] getSHA1(String input) throws NoSuchAlgorithmException 
	    {  
	        MessageDigest md = MessageDigest.getInstance("SHA-1");  
	        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
	    } 
}
