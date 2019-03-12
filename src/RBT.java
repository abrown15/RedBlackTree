public class RBT<E extends Comparable<E>> {
    private Node<E> root;

    public RBT(){

        root = null;
    }

    public Node<E> getRoot(){

        return root;
    }

    public void insert(E data){
        // Preform a regular insert
        // Check to make sure the tree remains an RBT tree

        //Make node holding data
        Node<E> newNode = new Node<>(data);

        //1. check if root is null
        Node<E> tempRoot = root;

        //If root is null, set root to newNode and set NewNode color to black
        if(root == null){
            root = newNode;
            newNode.setColor('b');
            newNode.setParent(null);

        //Else, set newNode color to red
        } else {
            newNode.setColor('r');
            while(true){
                //if newNode < tempRoot
                if(data.compareTo(tempRoot.getData()) < 0){

                    //if tempRoot's left child is null, set tempRoot's left child to new Node.
                    //also set newNode's parent to tempRoot
                    if(tempRoot.getLeftChild() == null){
                        tempRoot.setLeftChild(newNode);
                        newNode.setParent(tempRoot);
                        break;

                    //else, move down the tree to tempRoot's left child
                    }else{
                        tempRoot = tempRoot.getLeftChild();
                    }

                //if newNode >= tempRoot
                } else if(data.compareTo(tempRoot.getData()) > 0 || data.compareTo(tempRoot.getData()) == 0){

                    //if tempRoot's right child is null, set tempRoot's right child to new Node.
                    //alse set newNode's parent to tempRoot
                    if(tempRoot.getRightChild() == null){
                        tempRoot.setRightChild(newNode);
                        newNode.setParent(tempRoot);
                        break;

                    //else, move down the tree to tempRoot's right child
                    } else {
                        tempRoot = tempRoot.getRightChild();
                    }
                }
            }
            this.fixTree(newNode);
        }

    }

    public void fixTree(Node<E> x){
        while(x.getParent().getColor() == 'r'){

            Node<E> uncleNode = null;


            if(x.getParent() == x.getParent().getParent().getLeftChild()){

                uncleNode = x.getParent().getParent().getRightChild();


                if(uncleNode != null && uncleNode.getColor() == 'r'){
                    x.getParent().setColor('b');
                        uncleNode.setColor('b');
                        x.getParent().getParent().setColor('r');
                        x = x.getParent().getParent();
                        continue;
                }


                if( x == x.getParent().getRightChild()){
                    x = x.getParent();
                    this.leftRotate(x);

                }

                x.getParent().setColor('b');
                x.getParent().getParent().setColor('r');

                this.rightRotate(x.getParent().getParent());
            }else{
                uncleNode = x.getParent().getParent().getLeftChild();
                if(uncleNode != null && uncleNode.getColor() == 'r'){
                    x.getParent().setColor('b');
                    uncleNode.setColor('b');
                    x.getParent().getParent().setColor('r');
                    x = x.getParent().getParent();
                    continue;
                }

                if(x == x.getParent().getLeftChild()){
                    x = x.getParent();
                    this.rightRotate(x);
                }

                x.getParent().setColor('b');
                x.getParent().getParent().setColor('r');

                this.leftRotate(x.getParent().getParent());
            }
        }
        root.setColor('b');
    }

    public Node<E> search(E data){
        // Return the node that corresponds with the given data
        // Note: No need to worry about duplicate values added to the tree

        Node<E> tempRoot = root;

        //Check if tree is empty
        while(tempRoot != null){
            //if data < tempRoot data
            if(data.compareTo(tempRoot.getData()) < 0){
                if(tempRoot.getLeftChild() != null){
                    tempRoot = tempRoot.getLeftChild();
                    continue;
                }

            } else if(data.compareTo(tempRoot.getData()) > 0) {
                if (tempRoot.getRightChild() != null) {
                    tempRoot = tempRoot.getRightChild();
                    continue;
                }

            } else if(data.compareTo(tempRoot.getData()) == 0){
                System.out.println("Found");
                return tempRoot;
            }

        }

        System.out.println("Not Found");
        return null;
    }

    public void delete(E data){
    	// Perform a regular delete
    	// Check to make sure the tree remains an RBT tree
    }

    public void traverse(String order, Node<E> top) {
        // Preform a preorder traversal of the tree

        //Check if top node is null
        if (top == null){
            return;
        }

        switch (order) {
            //root left right
            case "preorder":
                System.out.print(top.getData() + " ");
                traverse("preorder", top.getLeftChild());
                traverse("preorder", top.getRightChild());
                break;

            //left root right
            case "inorder":
                traverse("inorder", top.getLeftChild());
                System.out.print(top.getData() + " ");
                traverse("inorder", top.getRightChild());
                break;

            //left right root
            case "postorder":
                traverse("postorder", top.getLeftChild());
                traverse("postorder", top.getRightChild());
                System.out.print(top.getData() + " ");
                break;
        }
    }


    public void rightRotate(Node<E> x){
    	
        /*
        If x is the root of the tree to rotate with right child subtree T3 and left child y,
        where T1 and T2 are the left and right children of y:
            x becomes right child of y and T1 as its left child of y
            T2 becomes left child of x and T3 becomes right child of x
        */

        //if x's parent isn't the root
        if(x.getParent() != null){
            //if x is it's parent's left child, set x's parent's left child to x's left child
            if(x == x.getParent().getLeftChild()){
                x.getParent().setLeftChild(x.getLeftChild());
            //else set x's parent's right child to x's left child
            } else {
                x.getParent().setRightChild(x.getLeftChild());
            }

            //set x's left child to x's parent
            x.getLeftChild().setParent(x.getParent());
            //set x's parent to x's left child
            x.setParent(x.getLeftChild());

            //If x's left child's right child isn't null, set  x's left child's right child's parent to x (...yeesh)
            if(x.getLeftChild().getRightChild() != null ){
                x.getLeftChild().getRightChild().setParent(x);
            }

            //Set x's left child to x's left child's right child
            x.setLeftChild(x.getLeftChild().getRightChild());
            //Set x's parent's right child to x
            x.getParent().setRightChild(x);

            //Should result in a right rotation

        //Else, we need to rotate the root
        } else {
            Node<E> leftNode = root.getLeftChild();
            root.setLeftChild(root.getLeftChild().getRightChild());
            leftNode.getRightChild().setParent(root);
            root.setParent(leftNode);
            leftNode.setRightChild(root);
            leftNode.setParent(null);
            root = leftNode;
        }


    	
    }

    public void leftRotate(Node<E> x){
    
    	/*
        If x is the root of the tree to rotate with left child subtree T1 and right child y, 
        where T2 and T3 are the left and right children of y:
            x becomes left child of y and T3 as its right child of y
            T1 becomes left child of x and T2 becomes right child of x
        */

    	if(x.getParent() != null){
    	    if(x == x.getParent().getLeftChild()){
    	        x.getParent().setLeftChild(x.getRightChild());
            } else {
    	        x.getParent().setRightChild(x.getRightChild());
            }

            x.getRightChild().setParent(x.getParent());
    	    x.setParent(x.getRightChild());

    	    if(x.getRightChild().getLeftChild() != null){
    	        x.getRightChild().getLeftChild().setParent(x);
            }

            x.setRightChild(x.getRightChild().getLeftChild());
    	    x.getParent().setLeftChild(x);

        } else{
    	    //Else, we need to rotate the root
            Node<E> rightNode = root.getRightChild();
            root.setRightChild(rightNode.getLeftChild());
            rightNode.getLeftChild().setParent(root);
            root.setParent(rightNode);
            rightNode.setLeftChild(root);
            rightNode.setParent(null);
            root = rightNode;
        }
		
    }
    
    // HINT: You may want to create extra methods such as fixDelete or fixInsert
}
