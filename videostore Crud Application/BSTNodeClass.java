/**
 *
 * @author Sairam
 */
public class BSTNodeClass {
	 Status info;
	    BSTNodeClass leftNode;
	    BSTNodeClass rightNode;

	    public BSTNodeClass(Status info)
	    {
	        this.info = new Status(info.isVideo, info.id, info.nameOrTitle, info.renterId);
	        this.leftNode = null;
	        this.rightNode = null;
	    }

	    public int getChildCount()
	    {
	        int childCount = 0;

	        if(this.leftNode != null) childCount++;
	        if(this.rightNode != null) childCount++;

	        return childCount;
	    }
	}
