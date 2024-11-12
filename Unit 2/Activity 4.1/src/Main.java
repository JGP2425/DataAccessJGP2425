import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main( String args[] ) throws IOException {
        FileInputStream fIn = null;
        FileOutputStream fOut = null;
        try {
            fIn = new FileInputStream("D:\\testin.txt");
            fOut = new FileOutputStream("D:\\testout.txt");
            byte[] bytes = new byte[128];
            int bytesReaded = 0;
            while((bytesReaded = fIn.read(bytes)) != -1) {
                fOut.write(bytes, 0 , bytesReaded);
            }
        }
        catch(IOException e ) {
            System.out.println( e.getMessage() );
        }
        finally {
            fIn.close();
            fOut.close();
        }
    }
}

