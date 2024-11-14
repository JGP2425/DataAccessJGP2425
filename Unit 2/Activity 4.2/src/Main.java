import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try{
            String imagePath = "C:\\DataAccessJGP2425\\Unit 2\\Activity 4.2\\imagen.bmp";
            FileInputStream fis = new FileInputStream("C:\\DataAccessJGP2425\\Unit 2\\Activity 4.2\\imagen.ico");
            byte[] headerBytes = new byte[8];
            int readedBytes = fis.read(headerBytes);
            if (readedBytes != -1) {
                String imageType = detectImageType(headerBytes);
                System.out.println(String.format("This image have %s format", imageType));
            }
            else
                System.out.println("Haven't found any image in : " + imagePath);
        }
        catch (IOException ex) {
            throw ex;
        }
    }

    public static String detectImageType(byte[] headerBytes) {
        String headerString = "";
        for (int i = 0; i < headerBytes.length; i++) {
            headerString += Integer.toHexString(headerBytes[i]).toUpperCase();
        }

        if (headerString.contains("FFD8FF"))
            return "JPEG";
        else if (headerString.contains("89504E47"))
            return "PNG";
        else if (headerString.contains("474946383961") || headerString.contains("474946383761"))
            return "GIF";
        else if (headerString.contains("424D"))
            return "BMP";
        else if (headerString.contains("00000100"))
            return "ICO";
        else
            return "unknown";

    }
}