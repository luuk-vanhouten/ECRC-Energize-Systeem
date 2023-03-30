package nl.saxion.re.ecrcenergizesysteem;

import java.time.LocalDate;


public class Offer {
    private int offer_id;
    private LocalDate installationDate;
    private int team;


  public   Offer(int offer_id,LocalDate installationDate, int team){
        this.installationDate=installationDate;
        this.offer_id=offer_id;
        this.team=team;
    }


}
