


/**
*
* @author Sairam
*/
public class SLLNodeClass {
   Status info;
   SLLNodeClass linkNode;

   public SLLNodeClass(Status info)
   {
       this.info = new Status(info.isVideo, info.id, info.nameOrTitle, info.renterId);
       this.linkNode = null;
   }
}