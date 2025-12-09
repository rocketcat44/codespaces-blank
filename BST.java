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
        //tree1.toStringTheToString(tree1.toString());
    
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
    
}