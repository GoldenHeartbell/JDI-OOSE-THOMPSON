package thompson.hexagonal.infrastructure.persistence.jpa.entity;

import thompson.hexagonal.infrastructure.persistence.jpa.ids.ProductInOrderId;

import javax.persistence.*;

@Entity
@Table(name = "productinorder")
@IdClass(ProductInOrderId.class)
public class ProductInOrder {

    @Id
    @Column(name="orderid")
    private Integer orderId;

    @Id
    @Column(name="productid")
    private Integer productId;

    @Column(name="productquantity")
    private Integer productQuantity;

    @Column(name="productprice")
    private double productPrice;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
