package rpc.rpc02;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args){
        IProductService IProductService = Stub.getStub();
        Product product  = IProductService.getProductById(1);
        System.out.println(product.toString());
    }
}