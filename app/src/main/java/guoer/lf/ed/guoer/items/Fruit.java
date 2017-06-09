package guoer.lf.ed.guoer.items;

public class Fruit {
    private String fruitName;

    private int fruitImageId;

    public Fruit(String fruitName, int fruitImageId) {
        this.fruitName = fruitName;
        this.fruitImageId = fruitImageId;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public int getFruitImageId() {
        return fruitImageId;
    }

    public void setFruitImageId(int fruitImageId) {
        this.fruitImageId = fruitImageId;
    }
}
