package main.GUI;

public class Trophy extends ImageSprite {
    private int weight;
    private String name;

    public Trophy(int x, int y, int w, int h, String path, String name, int weight){
        super(x, y, w, weight, path);
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }
    public int getWeight() {
        return weight;
    }
}
