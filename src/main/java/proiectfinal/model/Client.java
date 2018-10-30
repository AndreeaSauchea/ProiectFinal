package proiectfinal.model;

import java.util.Date;

public class Client extends Human {

    private long duration;
    private Date checkIn;
    private Date checkOut;


    public Client(Date checkIn, Date checkOut, String name, String forename, String cnp){
        super(name, forename, cnp);
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        calculateDuration();
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    private void calculateDuration(){
        this.duration = (this.checkOut.getTime() - this.checkIn.getTime())/(24*60*60*1000);
    }

    public long getDuration(){
        return duration;
    }

}
