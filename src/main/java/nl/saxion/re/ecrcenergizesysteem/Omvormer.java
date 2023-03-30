package nl.saxion.re.ecrcenergizesysteem;

class Omvormer {
    private String name;
    private int maxCapacity;
    private double price;
    private int id;


    @Override
    public String toString() {
        return  name;
    }

    public Omvormer(String name, int maxCapacity,double price, int id) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.price=price;
        this.id=id;
    }

    public String getName() {
        return name;
    }
    public  int getId(){
        return id;
    }


    public int getMaxCapacity() {
        return maxCapacity;
    }

}
