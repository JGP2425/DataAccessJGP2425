import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String imagePath = "C:\\DataAccessJGP2425\\Unit 2\\Activity 4.3\\imagen.bmp";
        String BMPProperties = showBPMProperties(imagePath);
        System.out.println(BMPProperties);


    }

    public static String showBPMProperties(String imagePath) throws IOException {
        FileInputStream fis = new FileInputStream(imagePath);
        DataInputStream dis = new DataInputStream(fis);

        if (fis.read() != 'B' || fis.read() != 'M') {
            return "The file don't have BMP Format";
        }

        int fileSize = readLittleEndianInt(fis);
        dis.skipBytes(12);

        int width = readLittleEndianInt(fis);
        int height = readLittleEndianInt(fis);
        dis.skipBytes(2);
        short bitsPerPixel = readLittleEndianShort(fis);

        return  String.format(
                "The image have a size of %d bytes, a width of %d pixels, a height of %d pixels and %d bits per pixel",
                fileSize, width, height, bitsPerPixel);

    }

    private static int readLittleEndianInt(FileInputStream fis) throws IOException {
        int b1 = fis.read();
        int b2 = fis.read();
        int b3 = fis.read();
        int b4 = fis.read();
        return (b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
    }

    private static short readLittleEndianShort(FileInputStream fis) throws IOException {
        short b1 = (short) fis.read();
        short b2 = (short) fis.read();
        return (short) ((b2 << 8) | b1);
    }
}