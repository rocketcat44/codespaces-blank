public class AVLTree {

    Node root;
	
	int height(Node N) {
		if(N==null)
			return 0;
		
		return N.height;
	}
	
	int max(int a, int b) {
		if (a>b)
		   return a;
		else
		   return b;	
	}
	
	
	 int getBalance(Node N) {
	        if (N == null)
	            return 0;
	 
	        return height(N.left) - height(N.right);
	    }
	 
	
	
	//Rotations 
	Node rightRotate(Node y) {
		Node x=y.left;
		Node T2=x.right;
        x.right = y;
        y.left = T2;
 
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
 
        return x;
   }
	
	
	Node leftRotate(Node x) {
		    Node y = x.right;
	        Node T2 = y.left;
	        
	        y.left = x;
	        x.right = T2;
	 
	    
	        x.height = max(height(x.left), height(x.right)) + 1;
	        y.height = max(height(y.left), height(y.right)) + 1;
	 
	        return y;

	}
	

	//precondition: node is the root of an AVL tree
	//postcondition: returns the root of the AVL tree after insertion
	
	  Node insert(Node node, int key) {
	        if (node == null)
	            return (new Node(key));
	 
	        if (key < node.key)
	            node.left = insert(node.left, key);
	        else if (key > node.key)
	            node.right = insert(node.right, key);
	        else
	            return node;
	
	        node.height = 1 + max(height(node.left),height(node.right));
	        int balance = getBalance(node);
	 
	        // unbalanced
	        //Left Left Case
	        if (balance > 1 && key < node.left.key) {
	        	//System.out.println("test");
	            return rightRotate(node);
	        }
	  
	        // Left Right Case
	        if (balance > 1 && key > node.left.key) {
	        	//System.out.println("te");
	            node.left = leftRotate(node.left);
	            return rightRotate(node);
	        }
	 
	        // Right Right Case
	        if (balance < -1 && key > node.right.key) {
	        	//System.out.println("poopy");
	        	return leftRotate(node);
	        }
	 
	        // Right Left Case
	        if (balance < -1 && key < node.right.key) {
	        	//System.out.println("jaja");
	        	//System.out.println(node.right.key);

	            node.right = rightRotate(node.right);
	            //System.out.println(node.right.key);

	            return leftRotate(node);
	        }
	 
	        return node;
	    }


		//precondition: root is the root of an AVL tree
		//postcondition: returns the root of the AVL tree after deletion

    Node deleteNode(Node root, int key)
	    {
	        
	        if (root == null)
	            return root;
	 
	        // left subtree
	        if (key < root.key)
	            root.left = deleteNode(root.left, key);
	 //right subtree
	        else if (key > root.key)
	            root.right = deleteNode(root.right, key);
	 
	        else
	        {
	 
	            // node with only one child or no child
	            if ((root.left == null) || (root.right == null))
	            {
	                Node temp = null;
                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right; 
                // No child case
                if (temp == null){
					temp = root;
	                    root = null;
	                }else // One child case
	                    root = temp;
	            }
	            else
	            {
	 
	                // two childrens
	                Node temp = minValueNode(root.right);
	                root.key = temp.key;
	                root.right = deleteNode(root.right, temp.key);
	            }
	        }
	 
	        // tree only has one node
	        if (root == null)
	            return root;
	 
	        root.height = max(height(root.left), height(root.right)) + 1;
	 
	        // check if node became unbalanced)
	        int balance = getBalance(root);
	 
	        // unbalanced cases
	        // Left Left Case
	        if (balance > 1 && getBalance(root.left) >= 0)
	            return rightRotate(root);
	 
	        // Left Right Case
	        if (balance > 1 && getBalance(root.left) < 0)
	        {
	            root.left = leftRotate(root.left);
	            return rightRotate(root);
	        }
	 
	        // Right Right Case
	        if (balance < -1 && getBalance(root.right) <= 0)
	            return leftRotate(root);
	 
	        // Right Left Case
	        if (balance < -1 && getBalance(root.right) > 0)
	        {
	            root.right = rightRotate(root.right);
	            return leftRotate(root);
	        }
	        
	        return root;
	 
	}

		Node minValueNode(Node node)
		{
			Node current = node;
	 
			// leftmost leaf
			while (current.left != null)
			current = current.left;
	 
			return current;
		}





    public static void main(String[] args) {
	        AVLTree tree = new AVLTree();
	 //test tree
	        tree.root = tree.insert(tree.root, 10);
	        tree.root = tree.insert(tree.root, 20);
	        tree.root = tree.insert(tree.root, 30);
	        tree.root = tree.insert(tree.root, 40);
	        tree.root = tree.insert(tree.root, 50);
	        System.out.println("Root "+tree.root.key);
	        tree.root = tree.insert(tree.root, 25);
			tree.printTree();
	        tree.root = tree.deleteNode(tree.root, 25);
	 
           tree.printTree();

	    }













    
    /////////////////////////////////////////////////////////////////////////////////////////

    //Add the following functions to your BST
 //Please use this code to verify your tree integrity
    public boolean isBSTOrNot() {
        return isBSTOrNot(this.root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTOrNot(Node root, int minValue, int maxValue) {
        // check for root is not null or not
        if (root == null) {
            return true;
        }
        // check for current node value with left node value and right node value and recursively check for left sub tree and right sub tree
        if(root.key >= minValue && root.key <= maxValue && isBSTOrNot(root.left, minValue, root.key) && isBSTOrNot(root.right, root.key, maxValue)){
            return true;
        }
        return false;
    }

 

   /// please use the following pieces of code to display your tree in a more easy to follow style (Note* you'll need to place the Trunk class in it's own file)
    public static void showTrunks(Trunk p)
    {
        if (p == null) {
            return;
        }
 
        showTrunks(p.prev);
        System.out.print(p.str);
    }
 
    public void printTree(){
        printTree(root, null, false);
    }

    private void printTree(Node root, Trunk prev, boolean isLeft)
    {
        if (root == null) {
            return;
        }
 
        String prev_str = "    ";
        Trunk trunk = new Trunk(prev, prev_str);
 
        printTree(root.right, trunk, true);
 
        if (prev == null) {
            trunk.str = "———";
        }
        else if (isLeft) {
            trunk.str = ".———";
            prev_str = "   |";
        }
        else {
            trunk.str = "`———";
            prev.str = prev_str;
        }
 
        showTrunks(trunk);
        System.out.println(" " + root.key);
 
        if (prev != null) {
            prev.str = prev_str;
        }
        trunk.str = "   |";
 
        printTree(root.left, trunk, false);
    }

//this goes into it's own file
    class Trunk
   {
    Trunk prev;
    String str;
 
    Trunk(Trunk prev, String str)
    {
        this.prev = prev;
        this.str = str;
    }
   };

}
