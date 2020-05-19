package rpc.rpc01;

import java.io.Serializable;

public class Product implements Serializable {

    private Integer id;
    private String name;

    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //toString()

    //get set 方法
}