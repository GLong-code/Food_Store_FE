package Model;

public class Food {
    protected int id;
    protected String name;
    protected int foodClass;
    protected int quantity;
    protected String importDay;
    protected String expiry;
    protected double price;
    protected String className;
    protected double attribute;

    public Food(int id, String name, int foodClass, int quantity, String importDay, String expiry, double attribute) {
        this.id = id;
        this.name = name;
        this.foodClass = foodClass;
        this.quantity = quantity;
        this.importDay = importDay;
        this.expiry = expiry;
        this.attribute = attribute;
    }

    public Food(String name, int foodClass, int quantity, String importDay, String expiry, double attribute) {
        this.name = name;
        this.foodClass = foodClass;
        this.quantity = quantity;
        this.importDay = importDay;
        this.expiry = expiry;
        this.attribute = attribute;
    }

    public Food(int id, String name, int foodClass, int quantity, String importDay, String expiry) {
        this.id = id;
        this.name = name;
        this.foodClass = foodClass;
        this.quantity = quantity;
        this.importDay = importDay;
        this.expiry = expiry;
    }

    public Food(String name, int foodClass, int quantity, String importDay, String expiry) {
        this.name = name;
        this.foodClass = foodClass;
        this.quantity = quantity;
        this.importDay = importDay;
        this.expiry = expiry;
    }

    public Food() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoodClass() {
        return foodClass;
    }

    public void setFoodClass(int foodClass) {
        this.foodClass = foodClass;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImportDay() {
        return importDay;
    }

    public void setImportDay(String importDay) {
        this.importDay = importDay;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getClassName() {
        return className;
    }

    public double getAttribute() {
        return attribute;
    }

    public void setAttribute(double attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", foodClass=" + foodClass +
                ", quantity=" + quantity +
                ", importDay='" + importDay + '\'' +
                ", expiryDay='" + expiry + '\'' +
                ", price=" + price +
                '}';
    }
}