package pojo;


public class ProductCounter {

    private int amount;
    private long prodId;

    public int getAmount() {
        return amount;
    }

    public ProductCounter(int amount, long prodId) {
        this.amount = amount;
        this.prodId = prodId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getProdId() {
        return prodId;
    }

    public void setProdId(long prodId) {
        this.prodId = prodId;
    }
}
