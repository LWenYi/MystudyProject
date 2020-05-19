package rpc.rpc01;

public class ProductServiceImpl implements IProductService {
    @Override
    public Product getProductById(Integer id) {
        //实际上这里应该去查询数据库获得数据，下面简化了
        return new Product(id, "手机");
    }
}
