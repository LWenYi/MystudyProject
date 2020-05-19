package rpc.rpc01;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {
        //建立Socket
        Socket socket = new Socket("127.0.0.1", 8888);
        //获取输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        //把商品Id通过网络传到服务端
        dos.writeInt(123);

        socket.getOutputStream().write(baos.toByteArray());
        socket.getOutputStream().flush();

        //读取服务端返回的商品信息
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        Integer id = dis.readInt();     //商品id
        String name = dis.readUTF();    //商品名称
        Product product = new Product(id, name);//通过服务端返回的商品信息生成商品

        System.out.println(product);

        //关闭流资源为了方便阅读，没有做try-catch处理
        dos.close();
        baos.close();
        socket.close();
    }
}