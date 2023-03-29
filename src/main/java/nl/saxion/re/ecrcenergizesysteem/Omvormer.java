package nl.saxion.re.ecrcenergizesysteem;

class Omvormer {
    private String name;
    private int maxCapacity;

    public Omvormer(String name, int maxCapacity) {
        this.name = name;
        this.maxCapacity = maxCapacity;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

}
