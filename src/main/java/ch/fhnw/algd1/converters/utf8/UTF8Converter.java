package ch.fhnw.algd1.converters.utf8;

/*
 * Created on 05.09.2014
 */
/**
 * @author 
 */
public class UTF8Converter {
	public static byte[] codePointToUTF(int x) {
		byte[] b = null;
		char[] chars = Integer.toBinaryString(x).toCharArray();
		if (x <= 0b111_1111) {
			b = new byte[1];
			b[0] = (byte) x;
		} else if (x < (2 << 10) ) {
			b = new byte[2];
			b[0] = (byte) ((x >> 6) | 0b1100_0000);
			b[1] = (byte) ((x & 0b000_0011_1111) | 0b1000_0000);
		} else if (x < (2 << 15)) {
			b = new byte[3];
			b[0] = (byte) ((x >> 12) | 0b1110_0000);
			b[1] = (byte) ((x >> 6)& 0b000_0011_1111 | 0b1000_0000);
			b[2] = (byte) ((x & 0b000_0011_1111) | 0b1000_0000);
		} else if (x < (2 << 20)) {
			b = new byte[4];
			b[0] = (byte) ((x >> 18) | 0b1111_0000);
			b[1] = (byte) ((x >> 12)& 0b000_0011_1111 | 0b1000_0000);
			b[2] = (byte) ((x >> 6)& 0b000_0011_1111 | 0b1000_0000);
			b[3] = (byte) ((x & 0b000_0011_1111) | 0b1000_0000);
		}
		return b;
	}

	public static int UTFtoCodePoint(byte[] bytes) {
		if (isValidUTF8(bytes)) {
			// TODO replace return statement below by code to return the code point
			// UTF-8 encoded in array bytes. bytes[0] contains the first byte
			return 0;
		} else return 0;
	}

	private static boolean isValidUTF8(byte[] bytes) {
		if (bytes.length == 1) return (bytes[0] & 0b1000_0000) == 0;
		else if (bytes.length == 2) return ((bytes[0] & 0b1110_0000) == 0b1100_0000)
				&& isFollowup(bytes[1]);
		else if (bytes.length == 3) return ((bytes[0] & 0b1111_0000) == 0b1110_0000)
				&& isFollowup(bytes[1]) && isFollowup(bytes[2]);
		else if (bytes.length == 4) return ((bytes[0] & 0b1111_1000) == 0b1111_0000)
				&& isFollowup(bytes[1]) && isFollowup(bytes[2]) && isFollowup(bytes[3]);
		else return false;
	}

	private static boolean isFollowup(byte b) {
		return (b & 0b1100_0000) == 0b1000_0000;
	}
}
