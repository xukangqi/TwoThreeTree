import java.util.Arrays;

/**
 * Created by Administrator on 2016/4/6.
 */
public class TwoThreeTree {
    public  int DEFAULT_MAXIMUM=20;
    public  TreeNode[] TreeNodeArray;
    public DLLNode[] DLLNodeArray;
    public  int[] array;
    public int amountofDLLNode;
    public static int root=0;
    public int firstFreeTreeNode;
    public int firstFreeDLLNode;
    public TwoThreeTree(){
        TreeNodeArray=new TreeNode[DEFAULT_MAXIMUM];
        DLLNodeArray=new DLLNode[DEFAULT_MAXIMUM*5];
        array=new int[DEFAULT_MAXIMUM*5];
    }
    public TwoThreeTree(int size){
        TreeNodeArray=new TreeNode[size];
        DLLNodeArray=new DLLNode[size*5];
        array=new int[size*5];
    }
    public void insert(int data) {
        try {
            if (getlength(array) == 0) {
                //if you create first dllnode with one key ,then create two children dllnode
                array[0] = data;
                DLLNodeArray[0] = new DLLNode(data);
                DLLNodeArray[1] = new DLLNode(-1);
                DLLNodeArray[1].setNext(2);
                DLLNodeArray[2] = new DLLNode(-1);
                DLLNodeArray[2].setPrevious(1);
                TreeNode treeNode = new TreeNode();
                treeNode.setChildListHead(1);
                treeNode.setChildListTail(2);
                treeNode.setKeyListHead(0);
                treeNode.setKeyListTail(0);
                treeNode.setNumKeys(1);
                treeNode.setParent(-1);
                TreeNodeArray[0] = treeNode;
                root = 0;
                firstFreeDLLNode = 3;
                firstFreeTreeNode = 1;
            } else {
                DLLNode dllNode = new DLLNode(data);
                array[getlength(array)] = data;
                DLLNodeArray[firstFreeDLLNode] = dllNode;
                firstFreeDLLNode++;
                TreeNode treeNode = TreeNodeArray[root];
                int first;
                int second;
                //from the root treenode to find the treenode where the data need to be inserted
                while (!isleaf(treeNode)) {
                    first = DLLNodeArray[treeNode.getKeyListHead()].getData();
                    second = DLLNodeArray[treeNode.getKeyListTail()].getData();
                    if (data < first) {
                        treeNode = TreeNodeArray[DLLNodeArray[treeNode.getChildListHead()].getData()];
                    } else if (data > second) {
                        treeNode = TreeNodeArray[DLLNodeArray[treeNode.getChildListTail()].getData()];
                    } else {
                        treeNode = TreeNodeArray[DLLNodeArray[DLLNodeArray[treeNode.getChildListHead()].getNext()].getData()];
                    }

                }
                //judge if need to add key to parent node
                if (treeNode.getNumKeys() == 1) {
                    int size = DLLNodeArray[treeNode.getKeyListHead()].getData();
                    //judge the dllnode with data need to be inserted left or right
                    if (data > size) {
                        //create new child dllnode and add this data in this treenode
                        DLLNodeArray[treeNode.getKeyListHead()].setNext(firstFreeTreeNode);
                        treeNode.setKeyListTail(findDatainArray(data));
                        dllNode = new DLLNode(-1);
                        DLLNodeArray[firstFreeDLLNode] = dllNode;
                        DLLNodeArray[treeNode.getChildListTail()].setNext(firstFreeTreeNode);
                        treeNode.setChildListTail(firstFreeDLLNode);
                        treeNode.setNumKeys(2);
                        firstFreeDLLNode++;
                    } else {
                        DLLNodeArray[treeNode.getKeyListHead()].setPrevious(findDatainArray(data));
                        treeNode.setKeyListHead(findDatainArray(data));
                        treeNode.setKeyListTail(findDatainArray(size));
                        treeNode.setNumKeys(2);
                        dllNode = new DLLNode(-1);
                        DLLNodeArray[firstFreeDLLNode] = dllNode;
                        DLLNodeArray[treeNode.getChildListTail()].setNext(firstFreeTreeNode);
                        treeNode.setChildListTail(firstFreeDLLNode);
                        firstFreeDLLNode++;
                    }

                } else {
                    int ordinaryfather = treeNode.getParent();
                    DLLNode ONE = DLLNodeArray[treeNode.getKeyListHead()];
                    DLLNode TWO = DLLNodeArray[treeNode.getKeyListTail()];
                    //judge the data need to insert left ,right or middle
                    if (data < ONE.getData()) {
                        //create one treenode to put this dllnode of data ,and create two children dllnode
                        TreeNode treeNode1 = new TreeNode();
                        treeNode1.setNumKeys(1);
                        treeNode1.setKeyListTail(findDatainArray(data));
                        treeNode1.setKeyListHead(findDatainArray(data));
                        DLLNode dllNode1 = new DLLNode(-1);
                        DLLNode dllNode2 = new DLLNode(-1);
                        DLLNodeArray[firstFreeDLLNode] = dllNode1;
                        dllNode1.setNext(firstFreeDLLNode + 1);
                        DLLNodeArray[firstFreeDLLNode + 1] = dllNode2;
                        dllNode2.setPrevious(firstFreeDLLNode);
                        treeNode1.setChildListHead(firstFreeDLLNode);
                        treeNode1.setChildListTail(firstFreeDLLNode + 1);
                        firstFreeDLLNode += 2;
                        TreeNodeArray[firstFreeTreeNode] = treeNode1;
                        //adjust the original treenode and get the key which need to go to highter layer,make this treenode become one key
                        DLLNodeArray[treeNode.getChildListTail()].setPrevious(-1);
                        DLLNodeArray[DLLNodeArray[treeNode.getChildListHead()].getNext()].setNext(-1);
                        treeNode.setNumKeys(1);
                        treeNode.setChildListTail(DLLNodeArray[treeNode.getChildListHead()].getNext());
                        treeNode.setKeyListHead(treeNode.getKeyListTail());
                        //create newtreenode with the key which need to go to highter layer,and the index of left treenode,and index of riht treenode
                        TreeNode newtreenode = new TreeNode(ONE.getData(), firstFreeTreeNode, findNodeinArray(treeNode));
                        //make the original treenode'parent become this new treenode's parent
                        newtreenode.setParent(ordinaryfather);
                        TreeNodeArray[firstFreeTreeNode + 1] = newtreenode;
                        treeNode1.setParent(firstFreeTreeNode + 1);
                        treeNode.setParent(firstFreeTreeNode + 1);
                        firstFreeTreeNode += 2;

                        dllNode.setNext(findDatainArray(TWO.getData()));
                        TWO.setPrevious(findDatainArray(data));
                        ONE.setNext(-1);
                        //use function to add this newtreenode to his parent
                        addtoParent(newtreenode, ordinaryfather);
                    } else if (data > TWO.getData()) {
                        //this part is similar to the previous,only need to adjust some function,like the getkeylisthead and get keylisttail
                        TreeNode treeNode1 = new TreeNode();
                        treeNode1.setNumKeys(1);
                        treeNode1.setKeyListTail(findDatainArray(data));
                        treeNode1.setKeyListHead(findDatainArray(data));
                        DLLNode dllNode1 = new DLLNode(-1);
                        DLLNode dllNode2 = new DLLNode(-1);
                        DLLNodeArray[firstFreeDLLNode] = dllNode1;
                        dllNode1.setNext(firstFreeDLLNode + 1);
                        DLLNodeArray[firstFreeDLLNode + 1] = dllNode2;
                        dllNode2.setPrevious(firstFreeDLLNode);
                        treeNode1.setChildListHead(firstFreeDLLNode);
                        treeNode1.setChildListTail(firstFreeDLLNode + 1);
                        firstFreeDLLNode += 2;
                        TreeNodeArray[firstFreeTreeNode] = treeNode1;

                        DLLNodeArray[treeNode.getChildListTail()].setPrevious(-1);
                        DLLNodeArray[DLLNodeArray[treeNode.getChildListHead()].getNext()].setNext(-1);
                        treeNode.setNumKeys(1);
                        treeNode.setChildListTail(DLLNodeArray[treeNode.getChildListHead()].getNext());
                        treeNode.setKeyListTail(treeNode.getKeyListHead());

                        TreeNode newtreenode = new TreeNode(TWO.getData(), findNodeinArray(treeNode), firstFreeTreeNode);
                        newtreenode.setParent(ordinaryfather);
                        TreeNodeArray[firstFreeTreeNode + 1] = newtreenode;
                        treeNode1.setParent(firstFreeTreeNode + 1);
                        treeNode.setParent(firstFreeTreeNode + 1);
                        firstFreeTreeNode += 2;
                        dllNode.setPrevious(findDatainArray(ONE.getData()));
                        ONE.setNext(findDatainArray(data));
                        TWO.setPrevious(-1);
                        //use function to add this newtreenode to his parent
                        addtoParent(newtreenode, ordinaryfather);
                    } else {
                        // create a new treenode tp put the original treenode's smaller key ,and adjust the original treenode
                        TreeNode treeNode1 = new TreeNode();
                        treeNode1.setNumKeys(1);
                        DLLNode dllNode1 = new DLLNode(-1);
                        DLLNodeArray[firstFreeDLLNode] = dllNode1;
                        treeNode1.setKeyListHead(findDatainArray(TWO.getData()));
                        treeNode1.setKeyListTail(findDatainArray(TWO.getData()));
                        treeNode1.setChildListHead(treeNode.getChildListTail());
                        treeNode1.setChildListTail(firstFreeDLLNode);
                        DLLNodeArray[firstFreeDLLNode].setPrevious(treeNode1.getChildListHead());
                        DLLNodeArray[treeNode.getChildListTail()].setNext(firstFreeDLLNode);
                        DLLNodeArray[treeNode.getChildListTail()].setPrevious(-1);
                        dllNode1.setPrevious(treeNode.getChildListTail());
                        TreeNodeArray[firstFreeTreeNode] = treeNode1;
                        treeNode.setKeyListTail(treeNode.getKeyListHead());
                        DLLNodeArray[treeNode.getKeyListHead()].setNext(-1);
                        DLLNodeArray[DLLNodeArray[treeNode.getChildListHead()].getNext()].setNext(-1);
                        treeNode.setNumKeys(1);
                        firstFreeDLLNode++;
                        // use inserted data to create new  parent treenode with two children treenode
                        TreeNode newtreenode = new TreeNode(data, findNodeinArray(treeNode), firstFreeTreeNode);
                        newtreenode.setParent(ordinaryfather);
                        TreeNodeArray[firstFreeTreeNode + 1] = newtreenode;
                        treeNode.setParent(firstFreeTreeNode + 1);
                        treeNode1.setParent(firstFreeTreeNode + 1);
                        firstFreeTreeNode += 2;
                        ONE.setNext(-1);
                        TWO.setPrevious(-1);
                        //use function to add this newtreenode to his parent
                        addtoParent(newtreenode, ordinaryfather);
                    }
                }
            }
        }catch (RuntimeException e){
            //if the treenodearray or dllnodearray is full ,throw exception
            throw new TreeOverflowException("insert failed "+"firstFreeTreeNode: "+firstFreeTreeNode+" firstFreeDLLNode: "+firstFreeDLLNode+"\n");
        }

    }
    public void addtoParent(TreeNode child,int parent){
        if (parent==-1){
            //if the parent is -1,make this treenode become root
             root=findNodeinArray(child);
        }else{
            int data=DLLNodeArray[child.getKeyListHead()].getData();
            TreeNode father=TreeNodeArray[parent];
            //judge the father's key
            if (father.getNumKeys()==1){
                //judge the treenode's key should insert left or right
                if (data<DLLNodeArray[father.getKeyListTail()].getData()) {
                    //add the child's key become part of father'key
                    father.setKeyListHead(child.getKeyListHead());
                    father.setChildListHead(child.getChildListHead());
                    father.setNumKeys(2);
                    //make father'right child(become child come from father's left) connect to child's chidrenlistTail
                    //and make child's two children dllnode and father's childerlsitTail become current father treenode's children
                    DLLNodeArray[child.getChildListTail()].setNext(father.getChildListTail());
                    DLLNodeArray[father.getChildListTail()].setPrevious(child.getChildListTail());
                    DLLNodeArray[child.getKeyListHead()].setNext(father.getKeyListTail());
                    DLLNodeArray[father.getKeyListTail()].setPrevious(child.getKeyListHead());
                    //adjust original child's children treenode's parent attribute pointer to this new father
                    if (DLLNodeArray[child.getChildListHead()].getData()!=-1){
                        TreeNodeArray[DLLNodeArray[child.getChildListHead()].getData()].setParent(findNodeinArray(father));
                        if (DLLNodeArray[DLLNodeArray[child.getChildListHead()].getNext()].getData()!=-1){
                            TreeNodeArray[DLLNodeArray[DLLNodeArray[child.getChildListHead()].getNext()].getData()].setParent(findNodeinArray(father));
                            if (DLLNodeArray[child.getChildListTail()].getData()!=-1){
                                TreeNodeArray[DLLNodeArray[child.getChildListTail()].getData()].setParent(findNodeinArray(father));
                            }
                        }
                    }
                }else{
                    //this part is similar to previous part
                    father.setKeyListTail(child.getKeyListHead());
                    father.setChildListTail(child.getChildListTail());
                    father.setNumKeys(2);
                    DLLNodeArray[father.getChildListHead()].setNext(child.getChildListHead());
                    DLLNodeArray[child.getChildListHead()].setPrevious(father.getChildListHead());
                    DLLNodeArray[child.getKeyListHead()].setPrevious(father.getKeyListHead());
                    DLLNodeArray[father.getKeyListHead()].setNext(child.getKeyListHead());

                    if (DLLNodeArray[child.getChildListHead()].getData()!=-1){
                        TreeNodeArray[DLLNodeArray[child.getChildListHead()].getData()].setParent(findNodeinArray(father));
                        if (DLLNodeArray[DLLNodeArray[child.getChildListHead()].getNext()].getData()!=-1){
                            TreeNodeArray[DLLNodeArray[DLLNodeArray[child.getChildListHead()].getNext()].getData()].setParent(findNodeinArray(father));
                            if (DLLNodeArray[child.getChildListTail()].getData()!=-1){
                                TreeNodeArray[DLLNodeArray[child.getChildListTail()].getData()].setParent(findNodeinArray(father));
                            }
                        }
                    }
                }
            }else{
                int ordinary=father.getParent();

                DLLNode first=DLLNodeArray[father.getKeyListHead()];
                DLLNode second=DLLNodeArray[father.getKeyListTail()];
                //if need choose one key go up again
                if (data<first.getData()){
                    //the child's key is smaller than father's left key
                    //adjust the original father treenode and make it  only retain his right key
                    DLLNodeArray[father.getKeyListHead()].setNext(-1);
                    DLLNodeArray[father.getKeyListTail()].setPrevious(-1);
                    father.setKeyListHead(father.getKeyListTail());
                    father.setChildListHead(DLLNodeArray[father.getChildListHead()].getNext());
                    DLLNodeArray[DLLNodeArray[father.getChildListHead()].getNext()].setPrevious(-1);
                    father.setNumKeys(1);
                    //create new treenode with the original father's left key
                    //make child treenode and original father treenode become new treenode's children
                    TreeNode newtreeNode=new TreeNode(first.getData(),findNodeinArray(child),findNodeinArray(father));
                    TreeNodeArray[firstFreeTreeNode]=newtreeNode;
                    newtreeNode.setParent(ordinary);
                    child.setParent(firstFreeTreeNode);
                    father.setParent(firstFreeTreeNode);
                    firstFreeTreeNode++;
                    //use this function again
                    addtoParent(newtreeNode,ordinary);
                }else if (data>second.getData()){
                    //this part is similar to the pevious part
                    DLLNodeArray[father.getKeyListTail()].setPrevious(-1);
                    DLLNodeArray[father.getKeyListHead()].setNext(-1);
                    DLLNodeArray[DLLNodeArray[father.getChildListHead()].getNext()].setNext(-1);
                    father.setKeyListTail(father.getKeyListHead());
                    father.setChildListTail(DLLNodeArray[father.getChildListHead()].getNext());
                    DLLNodeArray[DLLNodeArray[father.getChildListHead()].getNext()].setNext(-1);
                    father.setNumKeys(1);
                    TreeNode newtreeNode=new TreeNode(second.getData(),findNodeinArray(father),findNodeinArray(child));
                    newtreeNode.setParent(ordinary);
                    TreeNodeArray[firstFreeTreeNode]=newtreeNode;
                    child.setParent(firstFreeTreeNode);
                    father.setParent(firstFreeTreeNode);
                    firstFreeTreeNode++;
                    addtoParent(newtreeNode,ordinary);
                }else {
                    //create new treenode with original father's right child,and adjust the original father, make this treenode only retain left child
                   TreeNode treeNode=new TreeNode();
                    treeNode.setKeyListHead(father.getKeyListTail());
                    treeNode.setKeyListTail(father.getKeyListTail());
                    first.setNext(-1);
                    second.setPrevious(-1);
                    father.setKeyListTail(father.getKeyListHead());
                    TreeNodeArray[firstFreeTreeNode]=treeNode;
                    DLLNode dllNode=DLLNodeArray[child.getChildListTail()];
                    //make child's left child become treeNode's right child
                    treeNode.setChildListHead(child.getChildListTail());
                    dllNode.setNext(father.getChildListTail());
                    treeNode.setChildListTail(father.getChildListTail());
                    DLLNodeArray[father.getChildListTail()].setPrevious(child.getChildListTail());
                    TreeNodeArray[DLLNodeArray[child.getChildListTail()].getData()].setParent(firstFreeTreeNode);
                   // make child's right child become father's left child
                    DLLNodeArray[DLLNodeArray[father.getChildListHead()].getNext()].setNext(-1);
                    DLLNodeArray[DLLNodeArray[father.getChildListHead()].getNext()].setData(DLLNodeArray[child.getChildListHead()].getData());
                    father.setChildListTail(DLLNodeArray[father.getChildListHead()].getNext());
                    TreeNodeArray[DLLNodeArray[father.getChildListTail()].getData()].setParent(findNodeinArray(father));
                    //use child's key become new father treenode
                    TreeNode newtreeNode=new TreeNode(child.getKeyListHead(),findNodeinArray(father),firstFreeTreeNode);
                    newtreeNode.setParent(ordinary);
                    TreeNodeArray[firstFreeTreeNode+1]=newtreeNode;
                    father.setParent(firstFreeTreeNode+1);
                    treeNode.setParent(firstFreeTreeNode+1);
                    firstFreeTreeNode+=2;
                    //use this function again
                    addtoParent(newtreeNode,ordinary);
                }
            }
        }
    }

    public boolean isleaf(TreeNode treeNode){
        //if the treeNode's child have no pointer to other treenode ,this is a leaf
        DLLNode dllNode=DLLNodeArray[treeNode.getChildListHead()];
        if (dllNode.getData()==-1){
            if (DLLNodeArray[dllNode.getNext()].getData()==-1){
                if (DLLNodeArray[dllNode.getNext()].getNext()==-1){
                    return true;
                }
                else if (DLLNodeArray[treeNode.getChildListTail()].getData()==-1){
                    return true;
                }else {

                    return false;
                }
            }else {
                return  false;
            }
        }else {
            return false;
        }
    }
    public  int findDatainArray(int a){
        for (int i=0;i<DLLNodeArray.length;i++){
            if (DLLNodeArray[i].getData()==a){
                return i;
            }
        }
        return 0;
    }
    public  int findNodeinArray(TreeNode treeNode){
        for (int i=0;i<firstFreeTreeNode;i++){
            if (treeNode.equals(TreeNodeArray[i])){
                return i;
            }
        }
        return 0;
    }
    public int getlength(int[] array){
        int i=0;
        while (array[i]!=0){
            i++;
        }
        return i;
    }
    public String toString(){
        String message="TreeNodeArray: \n";
          for (int i=0;i<firstFreeTreeNode;i++){
              message+=TreeNodeArray[i].toString()+"\n";
          }
        message+="DLLNodeArray:\n";
        for(int i=1;i<firstFreeDLLNode;i++){
            message+=" "+DLLNodeArray[i].toString()+"\n";
        }
        return message;
    }

    public void inOrder(int currentindex){
        //to find all treenode in this tree in ascending prder
        if (DLLNodeArray[TreeNodeArray[currentindex].getChildListHead()].getData()!=-1){
            inOrder(DLLNodeArray[TreeNodeArray[currentindex].getChildListHead()].getData());
        }
        System.out.println(TreeNodeArray[currentindex].toString());
        if (TreeNodeArray[currentindex].getNumKeys()==1){
            if (DLLNodeArray[TreeNodeArray[currentindex].getChildListTail()].getData()!=-1){
                inOrder(DLLNodeArray[TreeNodeArray[currentindex].getChildListTail()].getData());
            }
        }else {
            if (DLLNodeArray[DLLNodeArray[TreeNodeArray[currentindex].getChildListHead()].getNext()].getData()!=-1){
            inOrder(DLLNodeArray[DLLNodeArray[TreeNodeArray[currentindex].getChildListHead()].getNext()].getData());
        }
            if (DLLNodeArray[TreeNodeArray[currentindex].getChildListTail()].getData()!=-1){
                inOrder(DLLNodeArray[TreeNodeArray[currentindex].getChildListTail()].getData());
            }

        }

    }

    public class TreeNode{
        public int parent;
        public int numKeys;
        public int keyListHead;
        public int keyListTail;
        public int childListHead;
        public int childListTail;
        public TreeNode(){
            parent=-1;
            numKeys=-1;
            keyListHead=-1;
            keyListTail=-1;
            childListHead=-1;
            childListTail=-1;
        }
        public TreeNode(int data,int leftChild,int rightChild){
            //传入的参数应该是左右孩子在treenode中的位置
            DLLNode dllNode=new DLLNode(leftChild);
            DLLNode dllNode1=new DLLNode(rightChild);
            DLLNodeArray[firstFreeDLLNode]=dllNode;
            DLLNodeArray[firstFreeDLLNode+1]=dllNode1;
            DLLNodeArray[firstFreeDLLNode].setNext(firstFreeDLLNode+1);
            DLLNodeArray[firstFreeDLLNode+1].setPrevious(firstFreeDLLNode);
            keyListHead=findDatainArray(data);
            keyListTail=keyListHead;
            childListHead=firstFreeDLLNode;
            childListTail=childListHead+1;
            firstFreeDLLNode+=2;
            numKeys=1;

        }
        public int getChildListTail() {
            return childListTail;
        }

        public void setChildListTail(int childListTail) {
            this.childListTail = childListTail;
        }

        public int getChildListHead() {

            return childListHead;
        }

        public void setChildListHead(int childListHead) {
            this.childListHead = childListHead;
        }

        public int getKeyListTail() {

            return keyListTail;
        }

        public void setKeyListTail(int keyListTail) {
            this.keyListTail = keyListTail;
        }

        public int getNumKeys() {

            return numKeys;
        }

        public void setNumKeys(int numKeys) {
            this.numKeys = numKeys;
        }

        public int getKeyListHead() {

            return keyListHead;
        }

        public void setKeyListHead(int keyListHead) {
            this.keyListHead = keyListHead;
        }

        public int getParent() {

            return parent;
        }

        public void setParent(int parent) {
            this.parent = parent;
        }
        public String toString(){
            String message="";
            if (numKeys==1){
                message=DLLNodeArray[keyListTail].getData()+"\n";
                message+=DLLNodeArray[childListHead].getData()+" "+DLLNodeArray[childListTail].getData()+"\n";
                return message;
            }else{
                message=DLLNodeArray[keyListHead].getData()+" "+DLLNodeArray[keyListTail].getData()+"\n";
                message+=DLLNodeArray[childListHead].getData()+" "+DLLNodeArray[DLLNodeArray[childListHead].getNext()].getData()+" "+DLLNodeArray[childListTail].getData()+"\n";
                return message;
            }


        }
    }
}
