package nl.saxion.re.ecrcenergizesysteem;

public class SolarPanel {
    private String name;
    private double price;
    private int length;
    private int width;
    private int id;
    private int opbrengst;

    public SolarPanel(String name, double price, int length, int width, int id, int opbrengst) {
        this.name = name;
        this.price = price;
        this.length = length;
        this.width = width;
        this.id = id;
        this.opbrengst = opbrengst;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getId() {
        return id;
    }

    public int getOpbrengst() {
        return opbrengst;
    }

    @Override
    public String toString() {
        return String.format(name);
    }
}

