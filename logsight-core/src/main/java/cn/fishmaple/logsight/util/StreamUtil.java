package cn.fishmaple.logsight.util;

import java.io.*;

public class StreamUtil {

    public static String inStream2String(InputStream is){
        return inStream2String(is,"UTF-8");
    }
    public static String inStream2String(InputStream is,String charSet){
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, charSet));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                is.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
