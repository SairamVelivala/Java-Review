/**
*This video checks the status of videos
* @author Sairam
*/

public class Status {
	
    boolean isVideo;
    int id;
    String nameOrTitle;
    int renterId;

    public Status(boolean isVideo, int id, String nameOrTitle, int renterId)
    {
        this.isVideo = isVideo;
        this.id = id;
        this.nameOrTitle = nameOrTitle;
        this.renterId = renterId;
    }

    @Override
    public String toString()
    {
        if(isVideo == true)
            return "Video #" + String.valueOf(id) + " : " + nameOrTitle +  " ( " + ((renterId != -1) ? "RENTED" : "NOT RENTED") + " ) ";
        else
            return "Customer #" + String.valueOf(id) + " : " + nameOrTitle;
    }
}



