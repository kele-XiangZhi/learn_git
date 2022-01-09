package demo2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader{
	//通过找到类hello,解读方法，在反射调用helloClass。达到调用hello的母的。
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
			Class<?> helloClass = new HelloClassLoader().findClass("Hello");
			Method helloMethod = helloClass.getMethod("hello");
			helloMethod.invoke(helloClass.newInstance());
	}
	
	//重新定义类加载器findClass的类，达到通过路径指向，多一个类的加载。
	@Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Path path = Paths.get("D:/jike/Week1/Hello.xlass/Hello.xlass");
        byte[] helloBase64 = new byte[0];
        try {
            helloBase64 = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < helloBase64.length; i++) {
            helloBase64[i] = (byte) (255 - helloBase64[i]);
        }

        return defineClass(name, helloBase64, 0, helloBase64.length);
    }

}
