package com.example.demo.datastructure;

/**
 * 二叉树
 */
public class BinaryTree<T> {

    public static Integer count = new Integer(1);
    public static Node root = new Node("A");
    public static String preList = "";
    public static String inList = "";
    public static String postList = "";
    public static void buildTree(){
        Node left = new Node("B");
        Node right = new Node("C");
        root.setLeft(left);
        root.setRight(right);
        Node left1 = new Node("D");
        Node right1 = new Node("E");
        Node left2 = new Node("F");
        Node right2 = new Node("G");
        left.setLeft(left1);
        left.setRight(right1);
        right.setLeft(left2);
        right.setRight(right2);
        Node left3 = new Node("H");
        Node right3 = new Node("I");
        Node left4 = new Node("J");
        Node right4 = new Node("K");
        Node left5 = new Node("L");
        Node right5 = new Node("M");
        Node left6 = new Node("N");
        Node right6 = new Node("O");
        left1.setLeft(left3);
        left1.setRight(right3);
        right1.setLeft(left4);
        right1.setRight(right4);
        left2.setLeft(left5);
        left2.setRight(right5);
        right2.setLeft(left6);
        right2.setRight(right6);
        //buildNode(root);
    }
    public static void buildNode(Node node){
        System.out.println(count);
        if (count < 16){
            count++;
            Node left = new Node(count);
            Node right = new Node(count);
            node.setLeft(left);
            node.setRight(right);
            buildNode(left);
            buildNode(right);
        }
    }

    public static void main(String[] args) {
        buildTree();
        preorderTraverse();
        inorderTraverse();
        postorderTraverse();
        System.out.println(preList);
        System.out.println(inList);
        System.out.println(postList);
        int height = root.getHeight();
        int number = root.getNumberOfNode();
    }
    static class Node<T>{
        T data;
        private Node left;
        private Node right;
        public Node(T data) {
            this(data, null, null);
        }

        public Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
        public int getHeight() {
            return getHeight(this);
        }

        private int getHeight(Node node){
            int height = 0;
            if(node != null){
                height = 1 +Math.max(getHeight(node.left), getHeight(node.right));
            }
            return height;
        }
        public int getNumberOfNode() {
            return getNumberOfNode(this);
        }

        private int getNumberOfNode(Node node){
            int leftNum = 0;
            int rightNum= 0;

            if(node.left != null){
                leftNum = getNumberOfNode(node.left);
            }
            if(node.right != null){
                rightNum = getNumberOfNode(node.right);
            }
            return leftNum + rightNum + 1;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

    }
    public static void preorderTraverse(){
        preorderTraverse(root);
    }
    private static void preorderTraverse(Node node){
        if(node != null){
            preList = preList + node.getData();
            preorderTraverse(node.getLeft());
            preorderTraverse(node.getRight());
        }
    }


    /**
     * 简单的中序遍历（仅打印）
     */
    public static void inorderTraverse(){
        inorderTraverse(root);
    }
    private static void inorderTraverse(Node node){
        if(node != null){
            inorderTraverse(node.getLeft());
            inList = inList + node.getData();
            inorderTraverse(node.getRight());
        }
    }

    /**
     * 简单的后序遍历（仅打印）
     */
    public static void postorderTraverse(){
        postorderTraverse(root);
    }
    private static void postorderTraverse(Node node){
        if(node != null){
            postorderTraverse(node.getLeft());
            postorderTraverse(node.getRight());
            postList = postList + node.getData();
        }
    }

}
