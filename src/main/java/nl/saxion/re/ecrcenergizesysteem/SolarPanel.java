package nl.saxion.re.ecrcenergizesysteem;

import java.util.ArrayList;

public class SolarPanel {

    private int lenght;
    private int with;
    private int power;

    public SolarPanel(int lenght, int with, int power) {
        this.lenght = lenght;
        this.with = with;
        this.power = power;
    }
    SolarPanel proef= new SolarPanel(300,300,0);
    SolarPanel proef2= new SolarPanel(300,300,1);

    ArrayList<SolarPanel> drgfdopbox= new ArrayList<>();

    public ArrayList<SolarPanel> getDrgfdopbox() {
        drgfdopbox.add(proef2);
        drgfdopbox.add(proef);
        return drgfdopbox;
    }

    }

