/**
 * This class implements BST Search tree
 */



/**
 * @author Sairam
 *
 */
public class BST {
    BSTNodeClass mainRoot;
    int nodeCount;

    public BST()
    {
        mainRoot = null;
        nodeCount = 0;
    }

    public void addNode(Status info)
    {
        if(nodeCount == 0)
            mainRoot = new BSTNodeClass(info);
        else
        {
            BSTNodeClass temp = mainRoot, parentNode = mainRoot;

            while(temp != null)
            {
                parentNode = temp;

                if(temp.info.id > info.id)
                    temp = temp.leftNode;
                else
                    temp = temp.rightNode;
            }

            if(parentNode.info.id > info.id)
                parentNode.leftNode = new BSTNodeClass(info);
            else
                parentNode.rightNode = new BSTNodeClass(info);
        }

        nodeCount++;
    }
// Method To delete Node
    public boolean deleteNode(int id)
    {
       
        if (mainRoot == null)
            return false;
        BSTNodeClass n = mainRoot;
        BSTNodeClass parent = mainRoot;
        BSTNodeClass delNode = null;
        BSTNodeClass child = mainRoot;

        while (child != null) {
            parent = n;
            n = child;
            child = id >= n.info.id ? n.rightNode : n.leftNode;
            if (id == n.info.id)
                delNode = n;
        }

        if (delNode != null) {
            delNode.info = n.info;

            child = n.leftNode != null ? n.leftNode : n.rightNode;

            if (mainRoot.info.id == id) {
                mainRoot = child;
            } else {
                if (parent.leftNode == n) {
                    parent.leftNode = child;
                } else {
                    parent.rightNode = child;
                }
            }
        }

        nodeCount--;

        return true;
    }

    public BSTNodeClass Find(int id)
    {
        if(nodeCount == 0)
            return null;

        BSTNodeClass temp = mainRoot;

        while(temp != null)
        {
            if(temp.info.id == id)
                return temp;

            if(temp.info.id > id)
                temp = temp.leftNode;
            else
                temp = temp.rightNode;
        }

        return null;
    }

    public void printAll()
    {
        printAll(mainRoot);
    }

    public void printAll(BSTNodeClass node)
    {
        if(node == null)
            return;

        printAll(node.leftNode);
        System.out.println(node.info.toString());
        printAll(node.rightNode);
    }

    private BSTNodeClass getNextNode(BSTNodeClass startNode) {
        if(startNode.leftNode != null)
            return getNextNode(startNode.leftNode);

        return startNode;
    }

    public void printAllRentedBy(int id)
    {
        printAllRentedBy(mainRoot, id);
    }

    public void printAllRentedBy(BSTNodeClass node, int id)
    {
        if(node == null)
            return;

        printAll(node.leftNode);

        if(node.info.renterId == id)
            System.out.println(node.info.toString());

        printAll(node.rightNode);
    }

    public BSTNodeClass getSpecialRented(int customerId, int videoId)
    {
        BSTNodeClass videoNode = Find(videoId);

        return (videoNode != null && videoNode.info.renterId == customerId) ? videoNode : null;
    }
}
