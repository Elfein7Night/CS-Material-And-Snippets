package Concordance;

public class MyLinkedList {
    LLNode head;
    LLNode current;
    int size;

    static class LLNode {
        int data;
        LLNode next;

        LLNode(int _data) {
            this(_data, null);
        }

        LLNode(int _data, LLNode _next) {
            data = _data;
            next = _next;
        }
    }

    //O(1)
    public int getCurrentVal() {
        return current.data;
    }

    public MyLinkedList(int headData) {
        head = new LLNode(headData);
        current = head;
        size = 1;
    }

    // O(1)
    public void insert(int _data) {
        current.next = new LLNode(_data);
        current = current.next;
        size++;
    }

    //O(n) when n is the number of nodes
    public String toString(int indent) {
        if (head == null) return "[]";
        LLNode cur = head;
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        String sizeStr = "{size=" + size +"} - [";
        sb.append(sizeStr);
        indent += sizeStr.length();
        while (cur.next != null) {
            sb.append(cur.data).append(", ");
            cur = cur.next;

            counter++;
            if (counter % 30 == 0) {
                sb.append('\n');
                sb.append(" ".repeat(indent));
            }
        }
        sb.append(cur.data).append("]");
        return sb.toString();
    }
}
