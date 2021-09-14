import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) throws Exception {
        // run in JDK 11
        new HelloClassLoader().findClass("Hello").getDeclaredConstructor().newInstance();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        List<Byte> list = readFile();
        byte[] bytes = toByteArray(list);

        return defineClass(name, bytes, 0, bytes.length);
    }

    private List<Byte> readFile() {
        File file = new File("Hello.xlass");
        List<Byte> list = new ArrayList<>();

        try {
            InputStream inputStream = new FileInputStream(file);
            int b;
            while ((b = inputStream.read()) != -1) {
                byte temp = (byte) (255 - b);
                list.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private byte[] toByteArray(List<Byte> list) {
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }

        return bytes;
    }
}
