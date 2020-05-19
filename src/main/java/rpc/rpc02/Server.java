package rpc.rpc02;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;

    public static void main(String[] args) throws Exception {
        //建立服务端Socket
        ServerSocket ss = new ServerSocket(8888);
        //不断监听，处理客户端请求
        while (running) {
            Socket socket = ss.accept();
            process(socket);
            socket.close();
        }
        ss.close();
    }

    private static void process(Socket socket) throws Exception {
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        //读取客户端传过来的方法名
        String methodName  = ois.readUTF();
        //获取本次远程调用方法的参数类型
        Class[] parameterTypes = (Class[]) ois.readObject();
        //获取具体的参数对象
        Object[] args = (Object[]) ois.readObject();

        //调用服务类生成商品
        IProductService service = new ProductServiceImpl();
        //根据远程获取的方法名和参数，调用相应的方法
        Method method = service.getClass().getMethod(methodName, parameterTypes);
        Product product = (Product) method.invoke(service,args);
        //把商品的信息写回给客户端
        oos.writeObject(product);

        oos.close();
        ois.close();
        os.close();
        is.close();
    }
}