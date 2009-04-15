package parser;

/**
 * Clase que maneja el arbol sintactico anotado del slk.
 * 
 * @author Grupo 3
 */
public class SlkTree {

    // ATRIBUTOS
    protected SlkLog log;
    private Stack parse_stack;
    private Stack rhs_stack;
    private Node root;
    private Node current;

    public SlkTree(SlkLog log) {
        this.log = log;
        root = null;
        current = null;
        parse_stack = new Stack();
        rhs_stack = new Stack();
    }

    class Node {

        public short symbol;
        public Node child, // first symbol on rhs of production
                 sibling;       // rest of the parent production is siblings
        // parent is the lhs of the child production

        Node(short symbol) {
            this.symbol = symbol;
        }
    };

    class Stack {

        private static final int STACKSIZE = 1024;
        private Node[] start;
        private int top;

        Stack() {
            start = new Node[STACKSIZE];
            top = STACKSIZE - 1;
            start[top] = null;
        }

        public Node pop() {
            return (start[top] != null ? start[top++] : null);
        }

        public void push(Node node) {
            start[--top] = node;
        }
    };

    public void make_root(short symbol) {
        root = new Node(symbol);

        current = root;
    }

    public void add_rhs() {
        Node node,
                rhs;

        node = rhs_stack.pop();
        current.child = node;
        rhs = node;

        node = rhs_stack.pop();
        while (node != null) {
            rhs.sibling = node;
            rhs = node;
            node = rhs_stack.pop();
        }
    }

    public void push_rhs_symbol(short symbol) {
        Node node = new Node(symbol);

        parse_stack.push(node);
        rhs_stack.push(node);
    }

    public void pop_current() {
        current = parse_stack.pop();
    }

    public void show_tree(Node tree) {
        Node child,
                sibling;

        if (tree == null) {
            return;
        }
        System.out.println(SlkString.GetSymbolName(tree.symbol));

        child = tree.child;

        if (child != null) {
            show_tree(child);

            for (sibling = child.sibling; sibling != null; sibling = sibling.sibling) {
                show_tree(sibling);
            }
        }
    }

    public void show_tree() {
        show_tree(root);
    }

    public void show_parse_derivation() {
        show_parse_derivation(root);
    }

    public void show_parse_derivation(Node tree) {
        Node child,
                sibling;

        if (tree == null) {
            return;
        }
        System.out.print(SlkString.GetSymbolName(tree.symbol) + " -> ");

        child = tree.child;

        if (child != null) {
            System.out.print(SlkString.GetSymbolName(child.symbol) + "  ");
            for (sibling = child.sibling; sibling != null; sibling = sibling.sibling) {
                System.out.print(SlkString.GetSymbolName(sibling.symbol) + "  ");
            }
            System.out.println();

            if (SlkParser.IsNonterminal(child.symbol)) {
                show_parse_derivation(child);
            }

            for (sibling = child.sibling; sibling != null; sibling = sibling.sibling) {
                if (SlkParser.IsNonterminal(sibling.symbol)) {
                    show_parse_derivation(sibling);
                }
            }
        } else {
            System.out.println();
        }

    }
};
