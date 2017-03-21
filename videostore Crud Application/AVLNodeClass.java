/**
 * 
 */


/**
 * @author Sairam
 *
 */
public class AVLNodeClass {

	Status info;
    AVLNodeClass leftNode;
    AVLNodeClass rightNode;
    AVLNodeClass parent;
    int height;
    int balance;

    public AVLNodeClass(Status info, AVLNodeClass parent)
    {
        this.info = new Status(info.isVideo, info.id, info.nameOrTitle, info.renterId);
        this.leftNode = null;
        this.rightNode = null;
        this.parent = parent;
        this.balance = 0;
        this.height = 0;
    }
}

