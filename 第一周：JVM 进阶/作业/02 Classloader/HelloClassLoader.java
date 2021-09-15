import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) throws Exception {

        String className = "Hello";
        String methodName = "hello";

        Class<?> clazz = new HelloClassLoader().findClass(className);
        // run in JDK 11
        Object object = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod(methodName);
        method.invoke(object);
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

            inputStream.close();
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
