package ed.lab;

public class E01KthSmallest {
    public int n;
    public int resultado;
    public int kthSmallest(TreeNode<Integer> root, int k) {
        resultado = root.value;
        kSmallest(root,k);
        return resultado;

    }
    private void kSmallest(TreeNode<Integer> root, int k){
        if (root == null){
            return;
        }

        kSmallest (root.left, k);

        n++;
        if (n == k){
            resultado = root.value;
            return;
        }

        kSmallest (root.right, k);
        return;

    }

}