/**
 * Created by Administrator on 2016/4/8.
 */
public class Main {
    public static void main(String args[]) {
        int[] array=new int[]{23,32,17,15,39,9,16,29,35,34,27,31,33,6,10,8,7};
        TwoThreeTree tree=new TwoThreeTree();
        for (int i=0;i<array.length;i++){
            tree.insert(array[i]);
        }
        tree.inOrder(TwoThreeTree.root);
//        System.out.println(tree.toString());
        //this array is I write
//        int[] array2=new int[]{3,7,9,23,45,1,5,14,25,24,13,11,8,19,4,31,35,56,2,27,6};
//        TwoThreeTree tree1=new TwoThreeTree();
//        for (int i=0;i<array2.length;i++){
//            tree1.insert(array2[i]);
//        }
//        tree1.inOrder(TwoThreeTree.root);
//        System.out.println(tree1.toString());
//        // when insert this array ,will throw TreeOverflowException
//        int[] array3=new int[]{3,7,9,23,45,1,5,14,25,24,13,11,8,19,4,31,35,56,2,27,6,99,43};
//        TwoThreeTree tree2=new TwoThreeTree();
//        for (int i=0;i<array3.length;i++){
//            tree2.insert(array3[i]);
//        }
//        tree2.inOrder(TwoThreeTree.root);
//        System.out.println(tree2.toString());
    }
}
