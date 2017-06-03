package form;


public class ProductForm {

    private String name;
    private int cost;
    private int amount;
    private String description;
    private long prodId;

    public ProductForm(){}

    public ProductForm(String name, int cost, int amount, String description, long prodId) {
        this.name = name;
        this.cost = cost;
        this.amount = amount;
        this.description = description;
        this.prodId = prodId;
    }

    public long getProdId() {

        return prodId;
    }

    public void setProdId(long prodId) {
        this.prodId = prodId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
