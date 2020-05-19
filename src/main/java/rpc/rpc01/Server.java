package rpc.rpc01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(os);

        //读取客户端发过来的id
        Integer id = dis.readInt();
        //调用服务类生成商品
        IProductService service = new ProductServiceImpl();
        Product product = service.getProductById(id);
        //把商品的信息写回给客户端
        dos.writeInt(id);
        dos.writeUTF(product.getName());
        dos.flush();

        dos.close();
        dis.close();
        os.close();
        is.close();
    }
}