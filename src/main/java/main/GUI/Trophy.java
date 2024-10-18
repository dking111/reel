package main.GUI;

/**
 * Represents a trophy with a name and weight, and its graphical representation as an image sprite.
 */
public class Trophy extends ImageSprite {
    private int weight;   
    private String name;   

    /**
     * Constructs a Trophy object.
     *
     * @param x       The x-coordinate of the trophy's position.
     * @param y       The y-coordinate of the trophy's position.
     * @param w       The width of the trophy's sprite.
     * @param h       The height of the trophy's sprite.
     * @param path    The file path to the trophy's image.
     * @param name    The name of the trophy.
     * @param weight  The weight of the trophy.
     * @throws IllegalArgumentException if weight is non-positive or name is null/empty.
     */
    public Trophy(int x, int y, int w, int h, String path, String name, int weight) {
        super(x, y, w, h, path);
        setName(name);       // Use setter for validation
        setWeight(weight);   // Use setter for validation
    }

    /** 
     * Returns the name of the trophy.
     * 
     * @return the name of the trophy
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the weight of the trophy.
     * 
     * @return the weight of the trophy
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the name of the trophy.
     * 
     * @param name The new name for the trophy.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the weight of the trophy.
     * 
     * @param weight The new weight for the trophy.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Returns a string representation of the trophy.
     * 
     * @return A string containing the name and weight of the trophy.
     */
    @Override
    public String toString() {
        return "Trophy{name='" + name + "', weight=" + weight + " lbs}";
    }
}
