/**
*
* @author Sairam
*/
public class DLLNodeClass {
	 Status info;
	 DLLNodeClass preNode;
	 DLLNodeClass nextNode;

	    public DLLNodeClass(Status info)
	    {
	        this.info = new Status(info.isVideo, info.id, info.nameOrTitle, info.renterId);
	        this.preNode = null;
	        this.nextNode = null;
	    }
	}
