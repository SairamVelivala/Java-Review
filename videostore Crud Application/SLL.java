
/**
*** This class implements SLL search tree
* @author Sairam
*/


public class SLL {
    SLLNodeClass firstNode;
    int nodeCount;

    public SLL()
    {
        firstNode = null;
        nodeCount = 0;
    }

    public void addNode(Status info)
    {
        if(nodeCount == 0)
            firstNode = new SLLNodeClass(info);
        else
        {
            SLLNodeClass temp = firstNode;
            
            while(temp.linkNode != null)
                temp = temp.linkNode;

            temp.linkNode = new SLLNodeClass(info);
        }

        nodeCount++;
    }

    public boolean deleteNode(int id)
    {
        SLLNodeClass temp = firstNode, preNode = null;
        boolean find = false;

        while(temp != null)
        {
            if(temp.info.id == id)
            {
                find = true;
                break;
            }

            preNode = temp;
            temp = temp.linkNode;
        }

        if(find == true)
        {
            if(preNode == null) // We must delete the first node
                firstNode = firstNode.linkNode;
            else
                preNode.linkNode = temp.linkNode;

            nodeCount--;
        }

        return find;
    }

    public SLLNodeClass Find(int id)
    {
        if(nodeCount == 0)
            return null;

        SLLNodeClass temp = firstNode;

        while(temp != null)
        {
            if(temp.info.id == id)
                return temp;

            temp = temp.linkNode;
        }

        return null;
    }

    public void printAll()
    {
        SLLNodeClass temp = firstNode;

        while(temp != null)
        {
            System.out.println(temp.info.toString());

            temp = temp.linkNode;
        }
    }

    public void printAllRentedBy(int id) {
        SLLNodeClass temp = firstNode;

        while(temp != null)
        {
            if(temp.info.renterId == id)
                System.out.println(temp.info.toString());

            temp = temp.linkNode;
        }
    }

    public SLLNodeClass getSpecialRented(int customerId, int videoId)
    {
        SLLNodeClass videoNode = Find(videoId);

        return (videoNode != null && videoNode.info.renterId == customerId) ? videoNode : null;
    }
}
