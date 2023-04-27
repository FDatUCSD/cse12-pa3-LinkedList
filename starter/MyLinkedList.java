
/*
 * Name: Farris Danish
 * Email: fbinsyahrilakmar@ucsd.edu
 * PID: A17401247
 * Sources used: write-up, JDK, zybooks
 * 
 * This file contains the implementation for LinkList data structure used for learning purposes 
 */
import java.util.AbstractList;

/**
 * Implementation of doubly LinkedList
 * Uses tail and head as well as next and prev pointers to quickly do list
 * operations
 * such as adding, removing or modifying nodes
 * This implementation uses dummy nodes as the head and tail of the list
 */
public class MyLinkedList<E> extends AbstractList<E> {

    /**
     * Some constants to avoid magic numbers
     */

    protected static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Index out of bounds for index %x";
    protected static final String NULL_POINTER_EXCEPTION_MESSAGE = "Data cannot be null";
    protected static final int DEFAULT_LIST_SIZE = 0;
    protected static final int FIRST_INDEX = 0;

    /**
     * Initialize instance variables
     */

    /** Number of elements in the list */
    int size;
    /** The first node of the list (dummy node) */
    Node head;
    /** The last node of the list (dummy node) */
    Node tail;

    /**
     * A Node class that holds data and references to previous and next Nodes.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /**
         * Constructor to create singleton Node
         * 
         * @param element Element to add, can be null
         */
        public Node(E element) {
            // Initialize the instance variables
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /**
         * Set the parameter prev as the previous node
         * 
         * @param prev new previous node
         */
        public void setPrev(Node prev) {
            this.prev = prev;
        }

        /**
         * Set the parameter next as the next node
         * 
         * @param next new next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * Set the parameter element as the node's data
         * 
         * @param element new element
         */
        public void setElement(E element) {
            this.data = element;
        }

        /**
         * Accessor to get the next Node in the list
         * 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * Accessor to get the prev Node in the list
         * 
         * @return the previous node
         */
        public Node getPrev() {
            return this.prev;
        }

        /**
         * Accessor to get the Nodes Element
         * 
         * @return this node's data
         */
        public E getElement() {
            return this.data;
        }
    }

    // Implementation of the MyLinkedList Class

    /**
     * Constructor for MyLinkedList object
     * <p>
     * No arg constructor initialize doubly linked list with dummy variables at the
     * head and tail
     */
    public MyLinkedList() {
        this.head = new Node(null);
        this.tail = new Node(null);
        this.head.setNext(this.tail);
        this.tail.setPrev(this.head);
        this.size = DEFAULT_LIST_SIZE;
    }

    /*
     * Method size returns size of the number of elements in the list
     * already has javadocs
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * @throws IndexOutOfBoundsException when index < 0 or index >= number of
     *                                   elements in the list
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_MESSAGE, index));
        }

        return (E) getNth(index).getElement();
    }

    /**
     * @throws NullPointerException      if data is null
     * @throws IndexOutOfBoundsException when index < 0 or index > number of
     *                                   elements in the list
     */
    @Override
    public void add(int index, E data) {
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_MESSAGE, index));
        }

        if (data == null) {
            throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
        }

        Node newNode = new Node(data);

        /* empty list */
        if (isEmpty()) {
            this.head.setNext(newNode);
            this.tail.setPrev(newNode);
            newNode.setNext(this.tail);
            newNode.setPrev(this.head);
            this.size++;
            return;
        }

        /* add at the end */
        if (index == getFinalIndexPlusOne()) {
            this.tail.getPrev().setNext(newNode);
            this.tail.setPrev(newNode);
            this.size++;
            return;
        }

        /* add at start */
        else if (index == FIRST_INDEX) {
            newNode.setNext(getNth(FIRST_INDEX));
            newNode.setPrev(this.head);
            getNth(FIRST_INDEX).setPrev(newNode);
            this.head.setNext(newNode);
            this.size++;
            return;
        }

        /* for every other positions (not start, not end, not empty list) */
        else {
            Node currNode = getNth(index);
            newNode.setNext(currNode);
            newNode.setPrev(currNode.getPrev());
            currNode.getPrev().setNext(newNode);
            currNode.setPrev(newNode);
            this.size++;
            return;
        }

    }

    /**
     * @throws NullPointerException if data is null
     */
    @Override
    public boolean add(E data) {
        if (data == null) {
            throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
        }
        Node newNode = new Node(data);

        /* if empty list */
        if (this.head.getNext() == this.tail) {
            this.head.setNext(newNode);
            this.tail.setPrev(newNode);
            newNode.setNext(this.tail);
            newNode.setPrev(this.head);
            this.size++;

            return true;
        }

        /* add at the end */
        Node currNode = getNth(getFinalIndex());
        this.tail.setPrev(newNode);
        currNode.setNext(newNode);
        newNode.setNext(this.tail);
        newNode.setPrev(currNode);
        this.size++;

        return true;
    }

    /**
     * @throws NullPointerException      if data is null
     * @throws IndexOutOfBoundsException when index < 0 or index >= number of
     *                                   elements in the list
     */
    @Override
    public E set(int index, E data) {

        Node newNode;
        Node currNode;

        /* check if out of bounds */
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_MESSAGE, index));
        }

        /* check for null parameter */
        if (data == null) {
            throw new NullPointerException(NULL_POINTER_EXCEPTION_MESSAGE);
        }

        newNode = new Node(data);

        /* if first element */
        if (index == FIRST_INDEX) {
            currNode = getNth(FIRST_INDEX);
            this.head.setNext(newNode);
            newNode.setNext(currNode.getNext());
            newNode.setPrev(currNode.getPrev());
            currNode.getNext().setPrev(newNode);
        }

        /* if last element */
        else if (index == getFinalIndex()) {
            currNode = getNth(getFinalIndex());
            this.tail.setPrev(newNode);
            newNode.setNext(this.tail);
            newNode.setPrev(currNode.getPrev());
            currNode.getPrev().setNext(newNode);
        }

        /* if anywhere else (not first or last element) */
        else {
            currNode = getNth(index);
            newNode.setNext(currNode.getNext());
            newNode.setPrev(currNode.getPrev());
            currNode.getPrev().setNext(newNode);
            currNode.getNext().setPrev(newNode);
        }

        return (E) currNode.getElement();
    }

    /**
     * @throws IndexOutOfBoundsException when index < 0 or index >= number of
     *                                   elements in the list
     */
    @Override
    public E remove(int index) {

        /* check for out of bounds */
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_MESSAGE, index));
        }

        E temp = getNth(index).getElement();

        /* check if last Node */
        if (index == getFinalIndex()) {
            getNth(getFinalIndex()).getPrev().setNext(this.tail);
            this.tail.setPrev(getNth(getFinalIndex()).getPrev());
            this.size--;
        }

        /* if the first node */
        else if (index == FIRST_INDEX) {
            getNth(FIRST_INDEX).getNext().setPrev(this.head);
            this.head.setNext(getNth(FIRST_INDEX).getNext());
            this.size--;
        }
        /* if anywhere else */
        else {
            Node currNode = getNth(index);
            currNode.getPrev().setNext(currNode.getNext());
            currNode.getNext().setPrev(currNode.getPrev());
            this.size--;
        }

        return (E) temp;
    }

    /*
     * Method clear returns empty list
     * already has javadocs
     */
    @Override
    public void clear() {
        this.head.setNext(this.tail);
        this.tail.setPrev(this.head);
        this.size = 0;
    }

    /*
     * Method isEmpty returns true is list is empty
     * useful helper method
     * already have javadocs
     */
    @Override
    public boolean isEmpty() {
        if (this.head.next == this.tail)
            return true;
        return false;
    }

    /**
     * Useful helper method to get a node at a specific index.
     * Uses head and tail pointer to quickly access nodes
     * 
     * @param index the index of the node
     * @return the reference to the node
     * @throws IndexOutOfBoundsException when index < 0 or index >= number of
     *                                   elements in the list
     */
    protected Node getNth(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_MESSAGE, index));
        }

        /* first element */
        if (index == FIRST_INDEX)
            return this.head.getNext();

        /* last Node */
        if (index == getFinalIndex())
            return this.tail.getPrev();

        /* if anywhere else */
        Node currNode = this.head.getNext();
        for (int i = 1; i < index + 1; i++) { // traverses the list to the node at that index
            if (!currNode.getElement().equals(null)) {
                currNode = currNode.getNext();
            }
        }
        return currNode;
    }

    /* Helper methods */
    /**
     * Useful to access the final node of the list
     * 
     * @return int of final index of the list
     */
    private final int getFinalIndex() {
        return this.size() - 1;
    }

    /**
     * Useful helper method only to append at the end of a list
     * 
     * @return int equal to size
     */
    private final int getFinalIndexPlusOne() {
        return this.size();
    }

}
