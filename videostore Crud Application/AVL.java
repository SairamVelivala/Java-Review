/**
 * This class implements AVL search tree.
 *
*/
/**
 * @author Sairam
 *
 */
public class AVL {
	AVLNodeClass baseroot;

    public boolean insert(Status insertinfo) {
        if (baseroot == null)
            baseroot = new AVLNodeClass(insertinfo, null);
        else {
            AVLNodeClass n = baseroot;
            AVLNodeClass parent;
            while (true) {
                if (n.insertinfo == insertinfo)
                    return false;

                parent = n;

                boolean goLeft = n.insertinfo.id > insertinfo.id;
                n = goLeft ? n.leftNode : n.rightNode;

                if (n == null) {
                    if (goLeft) {
                        parent.leftNode = new AVLNodeClass(insertinfo, parent);
                    } else {
                        parent.rightNode = new AVLNodeClass(insertinfo, parent);
                    }
                    rebalance(parent);
                    break;
                }
            }
        }
        return true;
    }
// Process to delete key.
    public boolean delete(int id) {
        if (baseroot == null)
            return false;
        AVLNodeClass n = baseroot;
        AVLNodeClass parent = baseroot;
        AVLNodeClass delNode = null;
        AVLNodeClass child = baseroot;

        while (child != null) {
            parent = n;
            n = child;
            child = id >= n.insertinfo.id ? n.rightNode : n.leftNode;
            if (id == n.insertinfo.id)
                delNode = n;
        }

        if (delNode != null) {
            delNode.insertinfo = n.insertinfo;

            child = n.leftNode != null ? n.leftNode : n.rightNode;

            if (baseroot.insertinfo.id == id) {
                baseroot = child;
            } else {
                if (parent.leftNode == n) {
                    parent.leftNode = child;
                } else {
                    parent.rightNode = child;
                }
                rebalance(parent);
            }
            return true;
        }

        return false;
    }
	// Process to find  key.
    public AVLNodeClass Find(int id)
    {
        AVLNodeClass temp = baseroot;

        while(temp != null)
        {
            if(temp.insertinfo.id == id)
                return temp;

            if(temp.insertinfo.id > id)
                temp = temp.leftNode;
            else
                temp = temp.rightNode;
        }

        return null;
    }

// Search tree balancing methord
    private void rebalance(AVLNodeClass n) {
        setBalance(n);

        if (n.balance == -2) {
            if (height(n.leftNode.leftNode) >= height(n.leftNode.rightNode))
                n = rotateRight(n);
            else
                n = rotateLeftThenRight(n);

        } else if (n.balance == 2) {
            if (height(n.rightNode.rightNode) >= height(n.rightNode.leftNode))
                n = rotateLeft(n);
            else
                n = rotateRightThenLeft(n);
        }

        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            baseroot = n;
        }
    }

    private AVLNodeClass rotateLeft(AVLNodeClass a) {

        AVLNodeClass b = a.rightNode;
        b.parent = a.parent;

        a.rightNode = b.leftNode;

        if (a.rightNode != null)
            a.rightNode.parent = a;

        b.leftNode = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.rightNode == a) {
                b.parent.rightNode = b;
            } else {
                b.parent.leftNode = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    private AVLNodeClass rotateRight(AVLNodeClass a) {

        AVLNodeClass b = a.leftNode;
        b.parent = a.parent;

        a.leftNode = b.rightNode;

        if (a.leftNode != null)
            a.leftNode.parent = a;

        b.rightNode = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.rightNode == a) {
                b.parent.rightNode = b;
            } else {
                b.parent.leftNode = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    private AVLNodeClass rotateLeftThenRight(AVLNodeClass n) {
        n.leftNode = rotateLeft(n.leftNode);
        return rotateRight(n);
    }

    private AVLNodeClass rotateRightThenLeft(AVLNodeClass n) {
        n.rightNode = rotateRight(n.rightNode);
        return rotateLeft(n);
    }

    private int height(AVLNodeClass n) {
        if (n == null)
            return -1;
        return 1 + Math.max(height(n.leftNode), height(n.rightNode));
    }

    private void setBalance(AVLNodeClass... nodes) {
        for (AVLNodeClass n : nodes)
            n.balance = height(n.rightNode) - height(n.leftNode);
    }

    public void printAll()
    {
        printAll(baseroot);
    }

    public void printAll(AVLNodeClass node)
    {
        if(node == null)
            return;

        printAll(node.leftNode);
        System.out.println(node.insertinfo.toString());
        printAll(node.rightNode);
    }

    public void printAllRentedBy(int id)
    {
        printAllRentedBy(baseroot, id);
    }

    public void printAllRentedBy(AVLNodeClass node, int id)
    {
        if(node == null)
            return;

        printAll(node.leftNode);

        if(node.insertinfo.renterId == id)
            System.out.println(node.insertinfo.toString());

        printAll(node.rightNode);
    }
}

