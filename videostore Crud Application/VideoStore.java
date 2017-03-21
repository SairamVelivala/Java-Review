

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author Sairam
 */

public class VideoStore {

   
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long serviceTime, startTime, endTime;
        String dataStructureName = "SLL";
        int numberOfVideos = 0;
        int numberOfCustomers = 0;
        int numberOfRequest = 0;
        int choice = 13;
        Random r = new Random();
        
        //keeps track of total time taken by tree algorithm to add contents.
        startTime = System.nanoTime();
        if(args.length >= 1)
            dataStructureName = args[0]; // to check tree dataStructure Name
        if(args.length >= 2)
            numberOfVideos = Integer.parseInt(args[1]); // Videos to be add
        if(args.length >= 3)
            numberOfCustomers = Integer.parseInt(args[2]); // customer to add 
        if(args.length >= 4)
            numberOfRequest = Integer.parseInt(args[3]); // to add transaction

        int mode = 0, id;
        Status customerStatus = new Status(false, -1, "", -1);
        Status videoStatus = new Status(true, -1, "", -1);

        if(dataStructureName.compareTo("AVL") == 0)
            mode = 1;
        if(dataStructureName.compareTo("BST") == 0)
            mode = 2;
        if(dataStructureName.compareTo("SLL") == 0)
            mode = 3;
        if(dataStructureName.compareTo("DLL") == 0)
            mode = 4;
		

        switch(mode)
        {
            case 1: 
            {
			// This mode will use Single LinkedList	
                System.out.println("SLL Mode");
                SLL customers = new SLL();
                SLL inStoreVideos = new SLL();
                SLL rentedVideos = new SLL();


                for(int i = 0; i < numberOfVideos; i++)
                    inStoreVideos.addNode(new Status(true, (i + 1), String.valueOf(i + 1), -1));

                for(int i = 0; i < numberOfCustomers; i++)
                        customers.addNode(new Status(false, (i + 1), String.valueOf(i + 1), -1));

                for(int i = 0; i < numberOfRequest; i++)
                {
                    int videoId = r.nextInt(numberOfVideos);
                    SLLNodeClass tempVideo = inStoreVideos.Find(videoId);

                    if(tempVideo != null)
                    {
                        int customerId = r.nextInt(numberOfCustomers);

                        SLLNodeClass tempCustomer = customers.Find(customerId);

                        if(tempCustomer != null)
                        {
                            Status info = tempVideo.info;
                            info.renterId = tempCustomer.info.id;

                            inStoreVideos.deleteNode(tempVideo.info.id);
                            rentedVideos.addNode(info);
                        }
                    }
                }

                
                endTime = System.nanoTime();   
                serviceTime = endTime - startTime;

                if(numberOfVideos != 0)
                    System.out.println("Time for Service of " + numberOfVideos + " videos , " + numberOfCustomers + " customers and " + numberOfRequest + " Number of requests in " + dataStructureName + " = " + serviceTime + " ns");
                else
                    choice = ShowMenu();

                while(choice != 13)
                {
                    switch(choice)
                    {
                        case 1:
                        {
                            //To Read Video Status information From Input And Insert The Video Into InStoreVideos
                            System.out.print("Enter video id to add : ");
                            videoStatus.id = new Scanner(System.in).nextInt();

                            if(inStoreVideos.Find(videoStatus.id) == null && rentedVideos.Find(videoStatus.id) == null)
                            {
                                System.out.print("Enter video title : ");
                                videoStatus.nameOrTitle = new Scanner(System.in).nextLine();
                                inStoreVideos.addNode(videoStatus);

                                System.out.println("Add complete");
                            }
                            else
                                System.out.println("This id is duplicated");
                            break;
                        }
                        case 2:
                        {
                            System.out.print("Enter video id to delete : ");
                            id = new Scanner(System.in).nextInt();
                            if(inStoreVideos.deleteNode(id) || rentedVideos.deleteNode(id))
                                System.out.println("Deletion completed");
                            else
                                System.out.println("Sorry, The video is not found");
                            break;
                        }
                        case 3:
                        {
                            //This is to Read Customer Status information From the Input And Insert The Customer Into customers
                            System.out.print("Enter customer id to add : ");
                            customerStatus.id = new Scanner(System.in).nextInt();

                            if(customers.Find(customerStatus.id) == null)
                            {
                                System.out.print("Enter customer title : ");
                                customerStatus.nameOrTitle = new Scanner(System.in).nextLine();
                                customers.addNode(customerStatus);

                                System.out.println("Add complete");
                            }
                            else
                                System.out.println("This id is duplicated");
                            break;
                        }
                        case 4:
                        {
                            System.out.print("Enter customer id to delete : ");
                            id = new Scanner(System.in).nextInt();
                            if(customers.deleteNode(id))
                            {
                                //This is Delete the list of all videos rented by a customer from rentedVideos and add those to avialbale inStoreVideos
                                List<Integer> rentedVideoIds = new ArrayList<Integer>();
                                SLLNodeClass root = rentedVideos.firstNode;
                                while(root != null)
                                    if(root.info.renterId == id)
                                        rentedVideoIds.add(root.info.id);

                                for (Integer item : rentedVideoIds) {
                                    SLLNodeClass node = rentedVideos.Find(item);

                                    if(node != null)
                                    {
                                        Status info = node.info;
                                        info.renterId = -1;

                                        rentedVideos.deleteNode(node.info.id);
                                        inStoreVideos.addNode(info);
                                    }
                                }

                                System.out.println("Deletion Completed");
                            }
                            else
                                System.out.println("Sorry, customer is not found");
                            break;
                        }
                        case 5:
                        {
                            System.out.print("Enter video id to search in store : ");
                            id = new Scanner(System.in).nextInt();

                            if(inStoreVideos.Find(id) != null)
                                System.out.println("True");
                            else
                                System.out.println("False");

                            break;
                        }
                        case 6:
                        {
                            System.out.print("Enter video id to rent : ");
                            int videoId = new Scanner(System.in).nextInt();
                            SLLNodeClass tempVideo = inStoreVideos.Find(videoId);

                            if(tempVideo != null)
                            {
                                System.out.print("Enter renter customer id : ");
                                int customerId = new Scanner(System.in).nextInt();

                                SLLNodeClass tempCustomer = customers.Find(customerId);

                                if(tempCustomer != null)
                                {
                                    Status info = tempVideo.info;
                                    info.renterId = tempCustomer.info.id;

                                    inStoreVideos.deleteNode(tempVideo.info.id);
                                    rentedVideos.addNode(info);
                                    System.out.println("Check out Complete");
                                }
                                else
                                    System.out.println("This customer is not found");
                            }
                            else
                                System.out.println("This video is rented or not found");

                            break;
                        }
                        case 7:
                        {
                            System.out.print("Enter the rented video id : ");
                            int videoId = new Scanner(System.in).nextInt();
                            SLLNodeClass tempVideo = rentedVideos.Find(videoId);

                            if(tempVideo != null)
                            {
                                System.out.print("Enter renter customer id : ");
                                int customerId = new Scanner(System.in).nextInt();

                                SLLNodeClass tempCustomer = customers.Find(customerId);

                                if(tempCustomer != null && tempVideo.info.renterId == tempCustomer.info.id)
                                {
                                    Status info = tempVideo.info;
                                    info.renterId = -1;

                                    rentedVideos.deleteNode(tempVideo.info.id);
                                    inStoreVideos.addNode(info);

                                    System.out.println("Check in Complete");
                                }
                                else
                                    System.out.println("This customer is not found");
                            }
                            else
                                System.out.println("This video is not rented or not found");

                            break;
                        }
                        case 8:
                        {
                            customers.printAll();
                            break;
                        }
                        case 9:
                        {
                            inStoreVideos.printAll();
                            rentedVideos.printAll();
                            break;
                        }
                        case 10:
                        {
                            inStoreVideos.printAll();
                            break;
                        }
                        case 11:
                        {
                            rentedVideos.printAll();
                            break;
                        }
                        case 12:
                        {
                            System.out.print("Enter renter customer id : ");
                            id = new Scanner(System.in).nextInt();

                            SLLNodeClass temp = customers.Find(id);

                            if(temp != null)
                                rentedVideos.printAllRentedBy(id);
                            else
                                System.out.println("This customer is not found");

                            break;
                        }
                        case 13:
						{
						System.out.println("Good Bye!! ");
                        return;
                        }
					}
					
                    choice = ShowMenu(); 
                }
            }
            break;
            case 2: //This works if Datastructure is in DLL Mode
            {
                System.out.println("DLL Mode");
                DLL customers = new DLL();
                DLL inStoreVideos = new DLL();
                DLL rentedVideos = new DLL();

                for(int i = 0; i < numberOfVideos; i++)
                    inStoreVideos.addNode(new Status(true, (i + 1), String.valueOf(i + 1), -1));

                for(int i = 0; i < numberOfCustomers; i++)
                        customers.addNode(new Status(false, (i + 1), String.valueOf(i + 1), -1));

                for(int i = 0; i < numberOfRequest; i++)
                {
                    int videoId = r.nextInt(numberOfVideos);
                    DLLNodeClass tempVideo = inStoreVideos.Find(videoId);

                    if(tempVideo != null)
                    {
                        int customerId = r.nextInt(numberOfCustomers);

                        DLLNodeClass tempCustomer = customers.Find(customerId);

                        if(tempCustomer != null)
                        {
                            Status info = tempVideo.info;
                            info.renterId = tempCustomer.info.id;

                            inStoreVideos.deleteNode(tempVideo.info.id);
                            rentedVideos.addNode(info);
                        }
                    }
                }

                endTime = System.nanoTime(); 

                serviceTime = endTime - startTime;

                if(numberOfVideos != 0)
                   
				System.out.println("Time for Service of " + numberOfVideos + " videos , " + numberOfCustomers + " customers and " + numberOfRequest + " Number of requests in " + dataStructureName + " = " + serviceTime + " ns");
                else
                    choice = ShowMenu();

                while(choice != 13)
                {
                    switch(choice)
                    {
                        case 1:
                        {
                            // To check and Read Video Status From Input and to Insert The Video Into InStoreVideos
                            System.out.print("Enter video id to add : ");
                            videoStatus.id = new Scanner(System.in).nextInt();

                            if(inStoreVideos.Find(videoStatus.id) == null && rentedVideos.Find(videoStatus.id) == null)
                            {
                                System.out.print("Enter video title : ");
                                videoStatus.nameOrTitle = new Scanner(System.in).nextLine();
                                inStoreVideos.addNode(videoStatus);

                                System.out.println("Add complete");
                            }
                            else
                                System.out.println("This id is duplicated");
                            break;
                        }
                        case 2:
                        {
                            System.out.print("Enter video id to delete : ");
                            id = new Scanner(System.in).nextInt();
                            if(inStoreVideos.deleteNode(id) || rentedVideos.deleteNode(id))
                                System.out.println("Delete complete");
                            else
                                System.out.println("Sorry,The video is not found");
                            break;
                        }
                        case 3:
                        {
                            // Read Customer Statusrmation From Input And Insert The Customer Into customers
                            System.out.print("Enter customer id to add : ");
                            customerStatus.id = new Scanner(System.in).nextInt();

                            if(customers.Find(customerStatus.id) == null)
                            {
                                System.out.print("Enter customer title : ");
                                customerStatus.nameOrTitle = new Scanner(System.in).nextLine();
                                customers.addNode(customerStatus);

                                System.out.println("Add complete");
                            }
                            else
                                System.out.println("This id is duplicated");
                            break;
                        }
                        case 4:
                        {
                            System.out.print("Enter customer id to delete : ");
                            id = new Scanner(System.in).nextInt();
                            if(customers.deleteNode(id))
                            {
                                System.out.println("Deletion completed");
                                //Delete all videos rented by this customer from rentedVideos and add those to inStoreVideos
                                List<Integer> rentedVideoIds = new ArrayList<Integer>();
                                DLLNodeClass root = rentedVideos.firstNode;
                                while(root != null)
                                    if(root.info.renterId == id)
                                        rentedVideoIds.add(root.info.id);

                                for (Integer item : rentedVideoIds) {
                                    DLLNodeClass node = rentedVideos.Find(item);

                                    if(node != null)
                                    {
                                        Status info = node.info;
                                        info.renterId = -1;

                                        rentedVideos.deleteNode(node.info.id);
                                        inStoreVideos.addNode(info);
                                    }
                                }

                                System.out.println("Deletion completed");
                            }
                            else
                                System.out.println("Sorry,This customer is not found");
                            break;
                        }
                        case 5:
                        {
                            System.out.print("Enter video id to search in store : ");
                            id = new Scanner(System.in).nextInt();

                            if(inStoreVideos.Find(id) != null)
                                System.out.println("True");
                            else
                                System.out.println("False");

                            break;
                        }
                        case 6:
                        {
                            System.out.print("Enter video id to rent : ");
                            int videoId = new Scanner(System.in).nextInt();
                            DLLNodeClass tempVideo = inStoreVideos.Find(videoId);

                            if(tempVideo != null)
                            {
                                System.out.print("Enter renter customer id : ");
                                int customerId = new Scanner(System.in).nextInt();

                                DLLNodeClass tempCustomer = customers.Find(customerId);

                                if(tempCustomer != null)
                                {
                                    Status info = tempVideo.info;
                                    info.renterId = tempCustomer.info.id;

                                    inStoreVideos.deleteNode(tempVideo.info.id);
                                    rentedVideos.addNode(info);
                                    System.out.println("Check out Complete");
                                }
                                else
                                    System.out.println("Sorry,This customer is not found");
                            }
                            else
                                System.out.println("This video is rented or not found");

                            break;
                        }
                        case 7:
                        {
                            System.out.print("Enter the rented video id : ");
                            int videoId = new Scanner(System.in).nextInt();
                            DLLNodeClass tempVideo = rentedVideos.Find(videoId);

                            if(tempVideo != null)
                            {
                                System.out.print("Enter renter customer id : ");
                                int customerId = new Scanner(System.in).nextInt();

                                DLLNodeClass tempCustomer = customers.Find(customerId);

                                if(tempCustomer != null && tempVideo.info.renterId == tempCustomer.info.id)
                                {
                                    Status info = tempVideo.info;
                                    info.renterId = -1;

                                    rentedVideos.deleteNode(tempVideo.info.id);
                                    inStoreVideos.addNode(info);

                                    System.out.println("Check in Complete");
                                }
                                else
                                    System.out.println("Sorry,This customer is not found");
                            }
                            else
                                System.out.println("This video is not rented or not found");

                            break;
                        }
                        case 8:
                        {
                            customers.printAll();
                            break;
                        }
                        case 9:
                        {
                            inStoreVideos.printAll();
                            rentedVideos.printAll();
                            break;
                        }
                        case 10:
                        {
                            inStoreVideos.printAll();
                            break;
                        }
                        case 11:
                        {
                            rentedVideos.printAll();
                            break;
                        }
                        case 12:
                        {
                            System.out.print("Enter renter customer id : ");
                            id = new Scanner(System.in).nextInt();

                            DLLNodeClass temp = customers.Find(id);

                            if(temp != null)
                                rentedVideos.printAllRentedBy(id);
                            else
                                System.out.println("Sorry,This customer is not found");

                            break;
                        }
                        case 13:
						{
						System.out.println("Good Bye");
                        return;
						}
                    }
                    choice = ShowMenu();
                }
            }
            break;
            case 3: //This is to implement BST Mode
            {
                System.out.println("BST Mode");
                BST customers = new BST();
                BST inStoreVideos = new BST();
                BST rentedVideos = new BST();

                for(int i = 0; i < numberOfVideos; i++)
                    inStoreVideos.addNode(new Status(true, (i + 1), String.valueOf(i + 1), -1));

                for(int i = 0; i < numberOfCustomers; i++)
                        customers.addNode(new Status(false, (i + 1), String.valueOf(i + 1), -1));

                for(int i = 0; i < numberOfRequest; i++)
                {
                    int videoId = r.nextInt(numberOfVideos);
                    BSTNodeClass tempVideo = inStoreVideos.Find(videoId);

                    if(tempVideo != null)
                    {
                        int customerId = r.nextInt(numberOfCustomers);

                        BSTNodeClass tempCustomer = customers.Find(customerId);

                        if(tempCustomer != null)
                        {
                            Status info = tempVideo.info;
                            info.renterId = tempCustomer.info.id;

                            inStoreVideos.deleteNode(tempVideo.info.id);
                            rentedVideos.addNode(info);
                        }
                    }
                }

                endTime = System.nanoTime(); 

                serviceTime = endTime - startTime;

                if(numberOfVideos != 0)
                   System.out.println("Time for Service of " + numberOfVideos + " videos , " + numberOfCustomers + " customers and " + numberOfRequest + " Number of requests in " + dataStructureName + " = " + serviceTime + " ns");
                else
                    choice = ShowMenu();

                while(choice != 13)
                {
                    switch(choice)
                    {
                        case 1:
                        {
                            // Read Video Status information From Input And Insert The Video Into InStoreVideos
                            System.out.print("Enter video id to add : ");
                            videoStatus.id = new Scanner(System.in).nextInt();

                            if(inStoreVideos.Find(videoStatus.id) == null && rentedVideos.Find(videoStatus.id) == null)
                            {
                                System.out.print("Enter video title : ");
                                videoStatus.nameOrTitle = new Scanner(System.in).nextLine();
                                inStoreVideos.addNode(videoStatus);

                                System.out.println("Add complete");
                            }
                            else
                                System.out.println("This id is duplicated");
                            break;
                        }
                        case 2:
                        {
                            System.out.print("Enter video id to delete : ");
                            id = new Scanner(System.in).nextInt();
                            if(inStoreVideos.deleteNode(id) || rentedVideos.deleteNode(id))
                                System.out.println("Deletion Completed");
                            else
                                System.out.println("Sorry,This video is not found");
                            break;
                        }
                        case 3:
                        {
                            // Read Customer Status information From Input And Insert The Customer Into customers
                            System.out.print("Enter customer id to add : ");
                            customerStatus.id = new Scanner(System.in).nextInt();

                            if(customers.Find(customerStatus.id) == null)
                            {
                                System.out.print("Enter customer title : ");
                                customerStatus.nameOrTitle = new Scanner(System.in).nextLine();
                                customers.addNode(customerStatus);

                                System.out.println("Add complete");
                            }
                            else
                                System.out.println("This id is duplicated");
                            break;
                        }
                        case 4:
                        {
                            System.out.print("Enter customer id to delete : ");
                            id = new Scanner(System.in).nextInt();
                            if(customers.deleteNode(id))
                            {
                                System.out.println("Deletion completed");
                                //Delete all videos rented by this customer from rentedVideos and add those to inStoreVideos method
                                List<Integer> rentedVideoIds = new ArrayList<Integer>();
                                BSTNodeClass root = rentedVideos.rootNode;
                                while(root != null)
                                    if(root.info.renterId == id)
                                        rentedVideoIds.add(root.info.id);

                                for (Integer item : rentedVideoIds) {
                                    BSTNodeClass node = rentedVideos.Find(item);

                                    if(node != null)
                                    {
                                        Status info = node.info;
                                        info.renterId = -1;

                                        rentedVideos.deleteNode(node.info.id);
                                        inStoreVideos.addNode(info);
                                    }
                                }

                                System.out.println("Deletion Completed");
                            }
                            else
                                System.out.println("Sorry,The customer is not found");
                            break;
                        }
                        case 5:
                        {
                            System.out.print("Enter video id to search in store : ");
                            id = new Scanner(System.in).nextInt();

                            if(inStoreVideos.Find(id) != null)
                                System.out.println("True");
                            else
                                System.out.println("False");

                            break;
                        }
                        case 6:
                        {
                            System.out.print("Enter video id to rent : ");
                            int videoId = new Scanner(System.in).nextInt();
                            BSTNodeClass tempVideo = inStoreVideos.Find(videoId);

                            if(tempVideo != null)
                            {
                                System.out.print("Enter renter customer id : ");
                                int customerId = new Scanner(System.in).nextInt();

                                BSTNodeClass tempCustomer = customers.Find(customerId);

                                if(tempCustomer != null)
                                {
                                    Status info = tempVideo.info;
                                    info.renterId = tempCustomer.info.id;

                                    inStoreVideos.deleteNode(tempVideo.info.id);
                                    rentedVideos.addNode(info);
                                    System.out.println("Check out Complete");
                                }
                                else
                                    System.out.println("Sorry,This customer is not found");
                            }
                            else
                                System.out.println("Sorry,This video is rented or not found");

                            break;
                        }
                        case 7:
                        {
                            System.out.print("Enter the rented video id : ");
                            int videoId = new Scanner(System.in).nextInt();
                            BSTNodeClass tempVideo = rentedVideos.Find(videoId);

                            if(tempVideo != null)
                            {
                                System.out.print("Enter renter customer id : ");
                                int customerId = new Scanner(System.in).nextInt();

                                BSTNodeClass tempCustomer = customers.Find(customerId);

                                if(tempCustomer != null && tempVideo.info.renterId == tempCustomer.info.id)
                                {
                                    Status info = tempVideo.info;
                                    info.renterId = -1;

                                    rentedVideos.deleteNode(tempVideo.info.id);
                                    inStoreVideos.addNode(info);

                                    System.out.println("Check in Complete");
                                }
                                else
                                    System.out.println("sorry, This customer is not found");
                            }
                            else
                                System.out.println("This video is not rented or not found");

                            break;
                        }
                        case 8:
                        {
                            customers.printAll();
                            break;
                        }
                        case 9:
                        {
                            inStoreVideos.printAll();
                            rentedVideos.printAll();
                            break;
                        }
                        case 10:
                        {
                            inStoreVideos.printAll();
                            break;
                        }
                        case 11:
                        {
                            rentedVideos.printAll();
                            break;
                        }
                        case 12:
                        {
                            System.out.print("Enter renter customer id : ");
                            id = new Scanner(System.in).nextInt();

                            BSTNodeClass temp = customers.Find(id);

                            if(temp != null)
                                rentedVideos.printAllRentedBy(id);
                            else
                                System.out.println("Sorry, This customer is not found");

                            break;
                        }
                        case 13:
                            return;
                    }
                    choice = ShowMenu();
                }
            }
            break;
            case 4:
            {
                System.out.println("AVL Mode");
                AVL customers = new AVL();
                AVL inStoreVideos = new AVL();
                AVL rentedVideos = new AVL();

                for(int i = 0; i < numberOfVideos; i++)
                    inStoreVideos.insert(new Status(true, (i + 1), String.valueOf(i + 1), -1));

                for(int i = 0; i < numberOfCustomers; i++)
                        customers.insert(new Status(false, (i + 1), String.valueOf(i + 1), -1));

                for(int i = 0; i < numberOfRequest; i++)
                {
                    int videoId = r.nextInt(numberOfVideos);
                    AVLNodeClass tempVideo = inStoreVideos.Find(videoId);

                    if(tempVideo != null)
                    {
                        int customerId = r.nextInt(numberOfCustomers);

                        AVLNodeClass tempCustomer = customers.Find(customerId);

                        if(tempCustomer != null)
                        {
                            Status info = tempVideo.info;
                            info.renterId = tempCustomer.info.id;

                            inStoreVideos.delete(tempVideo.info.id);
                            rentedVideos.insert(info);
                        }
                    }
                }

                endTime = System.nanoTime(); 

                serviceTime = endTime - startTime;

                if(numberOfVideos != 0)
                    System.out.println("Time for Service of " + numberOfVideos + " videos , " + numberOfCustomers + " customers and " + numberOfRequest + " Number of requests in " + dataStructureName + " = " + serviceTime + " ns");
                else
                    choice = ShowMenu();


                

                while(choice != 13)
                {
                    switch(choice)
                    {
                        case 1:
                        {
                            // Read Video Status information From Input And Insert Them into to toStoreVideos
                            System.out.print("Enter video id to add : ");
                            videoStatus.id = new Scanner(System.in).nextInt();

                            if(inStoreVideos.Find(videoStatus.id) == null && rentedVideos.Find(videoStatus.id) == null)
                            {
                                System.out.print("Enter video title : ");
                                videoStatus.nameOrTitle = new Scanner(System.in).nextLine();
                                inStoreVideos.insert(videoStatus);

                                System.out.println("Add complete");
                            }
                            else
                                System.out.println("This id is duplicated");
                            break;
                        }
                        case 2:
                        {
                            System.out.print("Enter video id to delete : ");
                            id = new Scanner(System.in).nextInt();
                            if(inStoreVideos.delete(id) || rentedVideos.delete(id))
                                System.out.println("Delete complete");
                            else
                                System.out.println("The video is not found");
                            break;
                        }
                        case 3:
                        {
                            // Read Customer Status information Input And Insert The Customer Into customers
                            System.out.print("Enter customer id to add : ");
                            customerStatus.id = new Scanner(System.in).nextInt();

                            if(customers.Find(customerStatus.id) == null)
                            {
                                System.out.print("Enter customer title : ");
                                customerStatus.nameOrTitle = new Scanner(System.in).nextLine();
                                customers.insert(customerStatus);

                                System.out.println("Add complete");
                            }
                            else
                                System.out.println("This id is duplicated");
                            break;
                        }
                        case 4:
                        {
                            System.out.print("Enter customer id to delete : ");
                            id = new Scanner(System.in).nextInt();
                            if(customers.delete(id))
                            {
                                System.out.println("Delete complete");
                                //Delete all videos rented by this customer from rentedVideos and add those to inStoreVideos
                                List<Integer> rentedVideoIds = new ArrayList<Integer>();
                                AVLNodeClass root = rentedVideos.root;
                                while(root != null)
                                    if(root.info.renterId == id)
                                        rentedVideoIds.add(root.info.id);

                                for (Integer item : rentedVideoIds) {
                                    AVLNodeClass node = rentedVideos.Find(item);

                                    if(node != null)
                                    {
                                        Status info = node.info;
                                        info.renterId = -1;

                                        rentedVideos.delete(node.info.id);
                                        inStoreVideos.insert(info);
                                    }
                                }

                                System.out.println("Delete Complete");
                            }
                            else
                                System.out.println("sorry, This customer is not found");
                            break;
                        }
                        case 5:
                        {
                            System.out.print("Enter video id to search in store : ");
                            id = new Scanner(System.in).nextInt();

                            if(inStoreVideos.Find(id) != null)
                                System.out.println("True");
                            else
                                System.out.println("False");

                            break;
                        }
                        case 6:
                        {
                            System.out.print("Enter video id to rent : ");
                            int videoId = new Scanner(System.in).nextInt();
                            AVLNodeClass tempVideo = inStoreVideos.Find(videoId);

                            if(tempVideo != null)
                            {
                                System.out.print("Enter renter customer id : ");
                                int customerId = new Scanner(System.in).nextInt();

                                AVLNodeClass tempCustomer = customers.Find(customerId);

                                if(tempCustomer != null)
                                {
                                    Status info = tempVideo.info;
                                    info.renterId = tempCustomer.info.id;

                                    inStoreVideos.delete(tempVideo.info.id);
                                    rentedVideos.insert(info);
                                    System.out.println("Check out Complete");
                                }
                                else
                                    System.out.println("This customer is not found");
                            }
                            else
                                System.out.println("This video is rented or not found");

                            break;
                        }
                        case 7:
                        {
                            System.out.print("Enter the rented video id : ");
                            int videoId = new Scanner(System.in).nextInt();
                            AVLNodeClass tempVideo = rentedVideos.Find(videoId);

                            if(tempVideo != null)
                            {
                                System.out.print("Enter renter customer id : ");
                                int customerId = new Scanner(System.in).nextInt();

                                AVLNodeClass tempCustomer = customers.Find(customerId);

                                if(tempCustomer != null && tempVideo.info.renterId == tempCustomer.info.id)
                                {
                                    Status info = tempVideo.info;
                                    info.renterId = -1;

                                    rentedVideos.delete(tempVideo.info.id);
                                    inStoreVideos.insert(info);

                                    System.out.println("Check in Complete");
                                }
                                else
                                    System.out.println("This customer is not found");
                            }
                            else
                                System.out.println("This video is not rented or not found");

                            break;
                        }
                        case 8:
                        {
                            customers.printAll();
                            break;
                        }
                        case 9:
                        {
                            inStoreVideos.printAll();
                            rentedVideos.printAll();
                            break;
                        }
                        case 10:
                        {
                            inStoreVideos.printAll();
                            break;
                        }
                        case 11:
                        {
                            rentedVideos.printAll();
                            break;
                        }
                        case 12:
                        {
                            System.out.print("Enter renter customer id : ");
                            id = new Scanner(System.in).nextInt();

                            AVLNodeClass temp = customers.Find(id);

                            if(temp != null)
                                rentedVideos.printAllRentedBy(id);
                            else
                                System.out.println("This customer is not found");

                            break;
                        }
                        case 13:
                        return;
                    }
                    choice = ShowMenu();
                }
            }
            break;
        }
    }

    private static int ShowMenu() {
        Scanner in = new Scanner(System.in);

        System.out.println("1: To add a video");
        System.out.println("2: To delete a video");
        System.out.println("3: To add a customer");
        System.out.println("4: To delete customer");
        System.out.println("5: To check if a particular video is in store");
        System.out.println("6: To check out a video");
        System.out.println("7: To check in a video");
        System.out.println("8: To print all customers");
        System.out.println("9: To print all videos");
        System.out.println("10: To print all in store videos");
        System.out.println("11: To print all rent videos");
        System.out.println("12: To print the videos rent by a customer");
        System.out.println("13: To exit");

        return in.nextInt();
    }
}
