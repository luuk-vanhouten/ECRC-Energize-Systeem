package nl.saxion.re.ecrcenergizesysteem;

import java.sql.Date;


public class Offer {
    private int offer_id;
    private Date installation_date;
    private int team;


    public Offer(int offer_id, Date installation_date, int team) {
        this.installation_date = installation_date;
        this.offer_id = offer_id;
        this.team = team;
    }

    public Offer(int offer_id) {
        this.offer_id = offer_id;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public Date getInstallation_date() {
        return installation_date;
    }

    public int getTeam() {
        return team;
    }
}
