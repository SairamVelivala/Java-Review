
/**
*This class implements DLL search tree
* @author Sairam
*/
public class DLL {
   DLLNodeClass primaryNode;
   int nodeCount;

   public DLL()
   {
       primaryNode = null;
       nodeCount = 0;
   }

   public void addNode(Status info)
   {
       if(nodeCount == 0)
           primaryNode = new DLLNodeClass(info);
       else
       {
           DLLNodeClass temp = primaryNode;

           while(temp.nextNode != null)
               temp = temp.nextNode;

           temp.nextNode = new DLLNodeClass(info);
           temp.nextNode.preNode = temp;
       }

       nodeCount++;
   }

   public boolean deleteNode(int id)
   {
       DLLNodeClass temp = primaryNode;
       boolean find = false;

       while(temp != null)
       {
           if(temp.info.id == id)
           {
               find = true;
               break;
           }

           temp = temp.nextNode;
       }

       if(find == true)
       {
           if(temp.preNode == null) // To delete the first node. 
           {
               primaryNode = primaryNode.nextNode;
               if(primaryNode != null)
                   primaryNode.preNode = null;
           }
           else
           {
               temp.preNode.nextNode = temp.nextNode;
               if(temp.nextNode != null)
                   temp.nextNode.preNode = temp.preNode;
           }

           nodeCount--;
       }

       return find;
   }

   public DLLNodeClass Find(int id)
   {
       if(nodeCount == 0)
           return null;

       DLLNodeClass temp = primaryNode;

       while(temp != null)
       {
           if(temp.info.id == id)
               return temp;

           temp = temp.nextNode;
       }

       return null;
   }

   public void printAll() //printAll videos
   {
       DLLNodeClass temp = primaryNode;

       while(temp != null)
       {
           System.out.println(temp.info.toString());

           temp = temp.nextNode;
       }
   }

   public void printAllRentedBy(int id) {
       DLLNodeClass temp = primaryNode;

       while(temp != null)
       {
           if(temp.info.renterId == id)
               System.out.println(temp.info.toString());

           temp = temp.nextNode;
       }
   }

   public DLLNodeClass getSpecialRented(int customerId, int videoId)
   {
       DLLNodeClass videoNode = Find(videoId);

       return (videoNode != null && videoNode.info.renterId == customerId) ? videoNode : null;
   }
}
