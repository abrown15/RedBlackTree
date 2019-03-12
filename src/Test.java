public class Test {

    public static void main(String[] args){
        RBT<Integer> myRBT = new RBT<Integer>();

        myRBT.insert(10);
        myRBT.insert(3);
        myRBT.insert(7);
        //myRBT.insert(12);
        //myRBT.insert(30);

        myRBT.traverse("preorder", myRBT.getRoot());
    }
}
