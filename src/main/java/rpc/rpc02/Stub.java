package rpc.rpc02;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {

    public static IProductService getStub() {

        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //和服务端建立Socket连接
                Socket socket = new Socket("127.0.0.1", 8888);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //拿到远程调用的方法名
                String methodName = method.getName();
                //拿到远程调用方法的参数类型
                Class[] parametersTypes = method.getParameterTypes();
                //把方法名传递给服务端
                oos.writeUTF(methodName);
                //把方法参数类型传递给服务端
                oos.writeObject(parametersTypes);
                //把方法参数传递给服务端
                oos.writeObject(args);
                oos.flush();
                //获取远程调用的返回结果
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Product product = (Product) ois.readObject();

                ois.close();
                oos.close();
                socket.close();
                return product;
            }
        };
        Object o = Proxy.newProxyInstance(IProductService.class.getClassLoader(), new Class[]{IProductService.class}, h);
        return (IProductService) o;
    }
}
