using System;
using System.Text;

    namespace ASTIC_client{

    /**
    * A Base64 encoder/decoder.
    *
    * <p>
    * This class is used to encode and decode data in Base64 format as described in RFC 1521.
    *
    * @author
    *    Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland, www.source-code.biz
    */
    public class Base64Coder 
    {

    // The line separator string of the operating system.
    private const String systemLineSeparator = "\\";
    
    // Mapping table from 6-bit nibbles to Base64 characters.
    private static char[] map1 = new char[64];
   

    private static void  fillMap()
    {
        int i=0;
        for (char c='A'; c<='Z'; c++) map1[i++] = c;
        for (char c='a'; c<='z'; c++) map1[i++] = c;
        for (char c='0'; c<='9'; c++) map1[i++] = c;
        map1[i++] = '+'; map1[i++] = '/'; 
    }

    // Mapping table from Base64 characters to 6-bit nibbles.
    private static byte[] map2 = new byte[128];
   
      

    private static void fillMap2()
    {
        for (int i=0; i<map2.Length; i++) {
            int h = -1;
            map2[i] = (byte)h;
        }
        for (int i=0; i<64; i++) 
            map2[map1[i]] = (byte)i; 
    }

    static byte[] GetBytes(string str)
    {
        byte[] bytes = new byte[str.Length * sizeof(char)];
        System.Buffer.BlockCopy(str.ToCharArray(), 0, bytes, 0, bytes.Length);
        return bytes;
    }
    

    /**
    * Encodes a string into Base64 format.
    * No blanks or line breaks are inserted.
    * @param s  A String to be encoded.
    * @return   A String containing the Base64 encoded data.
    */
    public static string encodeString (String s) {
        return new String(encode(GetBytes(s))); 
    }

    /**
    * Encodes a byte array into Base 64 format and breaks the output into lines of 76 characters.
    * This method is compatible with <code>sun.misc.BASE64Encoder.encodeBuffer(byte[])</code>.
    * @param in  An array containing the data bytes to be encoded.
    * @return    A String containing the Base64 encoded data, broken into lines.
    */
    public static String encodeLines (byte[] iin) {
        return encodeLines(iin, 0, iin.Length, 76, systemLineSeparator); 
    }

    /**
    * Encodes a byte array into Base 64 format and breaks the output into lines.
    * @param in            An array containing the data bytes to be encoded.
    * @param iOff          Offset of the first byte in <code>in</code> to be processed.
    * @param iLen          Number of bytes to be processed in <code>in</code>, starting at <code>iOff</code>.
    * @param lineLen       Line length for the output data. Should be a multiple of 4.
    * @param lineSeparator The line separator to be used to separate the output lines.
    * @return              A String containing the Base64 encoded data, broken into lines.
    */
    public static String encodeLines (byte[] iin, int iOff, 
        int iLen, int lineLen, String lineSeparator) {
        int blockLen = (lineLen*3) / 4;
        if (blockLen <= 0) throw new Exception("inleagal argument");
        int lines = (iLen+blockLen-1) / blockLen;
        int bufLen = ((iLen+2)/3)*4 + lines*lineSeparator.Length;
        StringBuilder buf = new StringBuilder(bufLen);
        int ip = 0;
        while (ip < iLen) {
            int l = Math.Min(iLen-ip, blockLen);
            buf.Append (encode(iin, iOff+ip, l));
            buf.Append (lineSeparator);
            ip += l; 
        }
        return buf.ToString(); 
    }

    /**
    * Encodes a byte array into Base64 format.
    * No blanks or line breaks are inserted in the output.
    * @param in  An array containing the data bytes to be encoded.
    * @return    A character array containing the Base64 encoded data.
    */
    public static char[] encode (byte[] iin) {
    return encode(iin, 0, iin.Length); }

    /**
    * Encodes a byte array into Base64 format.
    * No blanks or line breaks are inserted in the output.
    * @param in    An array containing the data bytes to be encoded.
    * @param iLen  Number of bytes to process in <code>in</code>.
    * @return      A character array containing the Base64 encoded data.
    */
    public static char[] encode (byte[] iin, int iLen) {
        return encode(iin, 0, iLen); 
    }

    /**
    * Encodes a byte array into Base64 format.
    * No blanks or line breaks are inserted in the output.
    * @param in    An array containing the data bytes to be encoded.
    * @param iOff  Offset of the first byte in <code>in</code> to be processed.
    * @param iLen  Number of bytes to process in <code>in</code>, starting at <code>iOff</code>.
    * @return      A character array containing the Base64 encoded data.
    */
    public static char[] encode (byte[] iin, int iOff, int iLen) {
    int oDataLen = (iLen*4+2)/3;       // output length without padding
    int oLen = ((iLen+2)/3)*4;         // output length including padding
    char[] oout = new char[oLen];
    int ip = iOff;
    int iEnd = iOff + iLen;
    int op = 0;
    while (ip < iEnd) {
        int i0 = iin[ip++] & 0xff;
        int i1 = ip < iEnd ? iin[ip++] & 0xff : 0;
        int i2 = ip < iEnd ? iin[ip++] & 0xff : 0;
        int o0 = (int)((uint)i0 >> 2);
        int o1 = ((i0 &   3) << 4) | ((int)((uint)i1 >> 4));
        int o2 = ((i1 & 0xf) << 2) | ((int)((uint)i2) >> 6);
        int o3 = i2 & 0x3F;
        oout[op++] = map1[o0];
        oout[op++] = map1[o1];
        oout[op] = op < oDataLen ? map1[o2] : '='; op++;
        oout[op] = op < oDataLen ? map1[o3] : '='; op++; }
    return oout; }

    static string GetString(byte[] bytes)
    {
        char[] chars = new char[bytes.Length / sizeof(char)];
        System.Buffer.BlockCopy(bytes, 0, chars, 0, bytes.Length);
        return new string(chars);
    }

    /**
    * Decodes a string from Base64 format.
    * No blanks or line breaks are allowed within the Base64 encoded input data.
    * @param s  A Base64 String to be decoded.
    * @return   A String containing the decoded data.
    * @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
    */
    public static String decodeString (String s) {
    return GetString(decode(s)); }

    /**
    * Decodes a byte array from Base64 format and ignores line separators, tabs and blanks.
    * CR, LF, Tab and Space characters are ignored in the input data.
    * This method is compatible with <code>sun.misc.BASE64Decoder.decodeBuffer(String)</code>.
    * @param s  A Base64 String to be decoded.
    * @return   An array containing the decoded data bytes.
    * @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
    */
    public static byte[] decodeLines (String s) {
    char[] buf = new char[s.Length];
    int p = 0;
    for (int ip = 0; ip < s.Length; ip++) {
        char c = s[ip];
        if (c != ' ' && c != '\r' && c != '\n' && c != '\t')
            buf[p++] = c; }
    return decode(buf, 0, p); }

    /**
    * Decodes a byte array from Base64 format.
    * No blanks or line breaks are allowed within the Base64 encoded input data.
    * @param s  A Base64 String to be decoded.
    * @return   An array containing the decoded data bytes.
    * @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
    */
    public static byte[] decode (String s) {
    return decode(s.ToCharArray()); }

    /**
    * Decodes a byte array from Base64 format.
    * No blanks or line breaks are allowed within the Base64 encoded input data.
    * @param in  A character array containing the Base64 encoded data.
    * @return    An array containing the decoded data bytes.
    * @throws    IllegalArgumentException If the input is not valid Base64 encoded data.
    */
    public static byte[] decode (char[] iin) {
    return decode(iin, 0, iin.Length); }

    /**
    * Decodes a byte array from Base64 format.
    * No blanks or line breaks are allowed within the Base64 encoded input data.
    * @param in    A character array containing the Base64 encoded data.
    * @param iOff  Offset of the first character in <code>in</code> to be processed.
    * @param iLen  Number of characters to process in <code>in</code>, starting at <code>iOff</code>.
    * @return      An array containing the decoded data bytes.
    * @throws      IllegalArgumentException If the input is not valid Base64 encoded data.
    */
    public static byte[] decode (char[] iin, int iOff, int iLen) {
    if (iLen%4 != 0) throw new ArgumentException ("Length of Base64 encoded input string is not a multiple of 4.");
    while (iLen > 0 && iin[iOff+iLen-1] == '=') iLen--;
    int oLen = (iLen*3) / 4;
    byte[] oout = new byte[oLen];
    int ip = iOff;
    int iEnd = iOff + iLen;
    int op = 0;
    while (ip < iEnd) {
        int i0 = iin[ip++];
        int i1 = iin[ip++];
        int i2 = ip < iEnd ? iin[ip++] : 'A';
        int i3 = ip < iEnd ? iin[ip++] : 'A';
        if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
            throw new ArgumentException ("Illegal character in Base64 encoded data.");
        int b0 = map2[i0];
        int b1 = map2[i1];
        int b2 = map2[i2];
        int b3 = map2[i3];
        if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
            throw new ArgumentException ("Illegal character in Base64 encoded data.");
        int o0 = ( b0       <<2) | ((int)((uint)b1>>4));
        int o1 = ((b1 & 0xf)<<4) | ((int)((uint)b2>>2));
        int o2 = ((b2 &   3)<<6) |  b3;
        oout[op++] = (byte)o0;
        if (op<oLen) oout[op++] = (byte)o1;
        if (op<oLen) oout[op++] = (byte)o2; }
    return oout; }

    // Dummy constructor.
    private Base64Coder() {}

    } // end class Base64Coder
    }
