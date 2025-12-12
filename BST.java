class BST {

    Node root;

    public BST()
    {
        this.root = null;
    }

   
    public static void main(String[] args) {
        BST tree1 = new BST();
        tree1.insert(50);
        tree1.insert(30);
        tree1.insert(20);
        tree1.insert(40);
        tree1.insert(70);
        tree1.insert(60);

        System.out.println(tree1.toString());
        

        System.out.println(tree1.search(25)); //false
        System.out.println(tree1.search(20)); //true

        tree1.remove(20);
        System.out.println(tree1.search(20)); //false
        //System.out.println(tree1.toString());

        tree1.insert(43);
        tree1.insert(72);
        tree1.insert(65);
        tree1.toString();
        System.out.println(tree1.toString());
        System.out.println();
        tree1.printTree();
        //rotateleft and right tests:
        tree1.rotateLeft(tree1.root, null);
        System.out.println(tree1.toString());
        tree1.printTree();
        tree1.rotateRight(tree1.root.left, tree1.root);
        System.out.println(tree1.toString());
        tree1.printTree();
    
    }


    //precondition: key is an integer
    //post condition: key is inserted into the binary search tree according to the rules of BST
    void insert(int key){ //places things into your tree accurately based on the rules of the binary search tree
        root = insert2(root, key);
    }


    //precondition: key is an integer
    //post condition: key is inserted into the binary search tree according to the rules of BST
    Node insert2(Node i, int key){
        if (i == null) {
            return new Node(key);
        }
        if (key < i.key) {
            i.left = insert2(i.left, key);
        } else if (key > i.key) {
            i.right = insert2(i.right, key);
        }
        return i;
    }

    //precondition: key is an integer
    //post condition: returns true if the key is found in the tree, false otherwise
    boolean search(int key){ //correctly returns true or false based on whether the key can be found in the tree.
        return search2(root, key);
    }

    //precondition: key is an integer
    //post condition: returns true if the key is found in the tree, false otherwise
    boolean search2(Node i, int key){
        if (i == null) {
            return false;
        }
        if (i.key == key) {
            return true;
        }
        if (i.key < key) {
            return search2(i.right, key);
        } else {
            return search2(i.left, key);
        }
    }

    //precondition: key is an integer
    //post condition: removes the key from the tree if it exists, correctly handling all three
    int remove(int key){ //correctly removes for each of the three possible cases: removing a leaf, removing a parent with one child, removing a parent with two children
        return remove2(root, key).key;
    }

    //precondition: key is an integer
    //post condition: removes the key from the tree if it exists, correctly handling all three
    Node remove2(Node i, int key){
        if (i==null){
            return i;
        }

        if (key < i.key){
            i.left = remove2(i.left, key);
        }
        else if (key > i.key){
            i.right = remove2(i.right, key);
        }
        else{
            if (i.left == null){
                return i.right; //no left child
            }
            else if (i.right == null){
                return i.left; //no right child
            }

            //two children
            int minimii = i.key;
            while (i.left != null){
                minimii = i.left.key;
                i = i.left;
            }
            i.key = minimii;

            i.right = remove2(i.right, i.key);
        }
        return i;
    }

    //precondition: none
    //post condition: returns a string representation of the tree with each level on a separate line
    public String toString(){ //prints out the tree with each level on a separate line using preorder DFS collected by depth
        if (root == null) {
            return "Tree is empty";
        }

        java.util.List<StringBuilder> levels = new java.util.ArrayList<>();
        buildLevels(root, 0, levels);

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < levels.size(); i++) {
            if (i > 0) out.append('\n');
            // trim trailing space for neatness
            String line = levels.get(i).toString().trim();
            out.append(line);
        }

        return out.toString();
    }

    // Precondition: node is not null
    // Postcondition: levels is populated with the string representations of each level of the tree
    void buildLevels(Node node, int depth, java.util.List<StringBuilder> levels) {
        if (node == null) return;

        if (depth >= levels.size()) {
            levels.add(new StringBuilder());
        }
        levels.get(depth).append(node.key).append(' ');
        buildLevels(node.left, depth + 1, levels);
        buildLevels(node.right, depth + 1, levels);
    }


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

 

   // please use the following pieces of code to display your tree in a more easy to follow style (Note* you'll need to place the Trunk class in it's own file)
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


    // rotates the tree such that the subRoot is replaced with it's right child with subRoot becoming the left child of the new subRoot. prev now points to the new subRoot.
//precondition: subRoot and prev are not null
//postcondition: the tree is rotated to the left at the given subRoot, and the tree is updated correctly
    private void rotateLeft(Node subRoot, Node prev){
        if (subRoot == null || subRoot.right == null) {
            return;
        }
        
        Node newRoot = subRoot.right;
        subRoot.right = newRoot.left;
        newRoot.left = subRoot;
        
        // Update parent's pointer to point to new root
        if (prev == null) {
            this.root = newRoot;
        } else if (prev.left == subRoot) {
            prev.left = newRoot;
        } else {
            prev.right = newRoot;
        }
    }

 

// rotates the tree such that the subRoot is replaced with it's left child with subRoot becoming the right child of the new subRoot. prev now points to the new subRoot.
//precondition: subRoot and prev are not null
//postcondition: the tree is rotated to the right at the given subRoot, and the tree is updated correctly
    private void rotateRight(Node subRoot, Node prev){
        if (subRoot == null || subRoot.left == null) {
            return;
        }
        
        Node newRoot = subRoot.left;
        subRoot.left = newRoot.right;
        newRoot.right = subRoot;
        
        // Update parent's pointer to point to new root
        if (prev == null) {
            this.root = newRoot;
        } else if (prev.left == subRoot) {
            prev.left = newRoot;
        } else {
            prev.right = newRoot;
        }
    }
    
}