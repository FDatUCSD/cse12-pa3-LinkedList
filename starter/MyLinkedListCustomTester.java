
/**
 * IMPORTANT: Do not change the method headers. Your tests will be run against
 * good and bad implementations of MyLinkedList. You will only receive points
 * if your test passes when run on a good implementation and fails for the
 * corresponding bad implementation.
 */

import static org.junit.Assert.*;
import org.junit.*;

public class MyLinkedListCustomTester {

    // Optional: add test variables here
    private MyLinkedList<Integer> emptyIntegerList;
    private MyLinkedList<String> threeStringList;
    private String[] strData = { "CSE 12", "Paul Cao", "Christine Alvarado" };

    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    @Before
    public void setUp() throws Exception {
        // Optional: add setup here
        emptyIntegerList = new MyLinkedList<>();
        threeStringList = new MyLinkedList<>();
    }

    private void populateLinkedList() {
        MyLinkedList<String>.Node node0 = this.threeStringList.new Node(this.strData[0]);
        MyLinkedList<String>.Node node1 = this.threeStringList.new Node(this.strData[1]);
        MyLinkedList<String>.Node node2 = this.threeStringList.new Node(this.strData[2]);

        this.threeStringList.head.next = node0;
        node0.prev = this.threeStringList.head;
        node0.next = node1;
        node1.prev = node0;
        node1.next = node2;
        node2.prev = node1;
        node2.next = this.threeStringList.tail;
        this.threeStringList.tail.prev = node2;
        this.threeStringList.size = 3;
    }

    /**
     * Aims to test the add(E data) method with a valid argument.
     */
    @Test
    public void testCustomAdd() {
        this.populateLinkedList();
        MyLinkedList<String>.Node node1 = this.threeStringList.head.next; // CSE 12
        MyLinkedList<String>.Node node2 = this.threeStringList.head.next.next; // Paul
        MyLinkedList<String>.Node node3 = this.threeStringList.head.next.next.next; // Christine
        MyLinkedList<String>.Node tailNode = this.threeStringList.tail;
        MyLinkedList<String>.Node headNode = this.threeStringList.head;
        int origSize = this.threeStringList.size; // 3

        boolean bool1 = threeStringList.add("hello");
        assertEquals("Check if true is returned", true, bool1);
        assertEquals("Check if node is added through tail", "hello",
                this.threeStringList.tail.prev.data);
        assertEquals("Check length is increased by 1", origSize + 1, this.threeStringList.size);
        assertSame("Check if node 1 is untouched from the head", node1, this.threeStringList.head.next);
        assertEquals("Check if node 2 is untouched from the tail", node2, this.threeStringList.head.next.next);
        assertEquals("Check if node 3 is untouched from the tail", node3,
                this.threeStringList.head.next.next.next);

        assertEquals("Check from the head", "hello", this.threeStringList.head.next.next.next.next.data);
        assertEquals("Check node 2 from the tail", node2, this.threeStringList.tail.prev.prev.prev);
        assertEquals("Check node 3 from the tail", node3, this.threeStringList.tail.prev.prev);
        assertEquals("Check node 1 from the tail", node1, this.threeStringList.tail.prev.prev.prev.prev);

        assertSame("Check if head still null", headNode, this.threeStringList.head);
        assertSame("Check if tail is still null", tailNode, this.threeStringList.tail);

        MyLinkedList<Integer>.Node tailNode2 = this.emptyIntegerList.tail;
        MyLinkedList<Integer>.Node headNode2 = this.emptyIntegerList.head;
        this.emptyIntegerList.add(1);
        assertEquals("Check length increase by 1", 1, this.emptyIntegerList.size);
        assertSame("Check tail node is still the same", tailNode2, this.emptyIntegerList.tail);
        assertSame("Check head node is still the same", headNode2, this.emptyIntegerList.head);
        assertTrue("Check tail and head should point to the same node",
                this.emptyIntegerList.tail.prev == this.emptyIntegerList.head.next);
        assertEquals("Check added elem accessible through head", Integer.valueOf(1),
                this.emptyIntegerList.head.next.data);
        assertEquals("Check added elem accessible through tail", Integer.valueOf(1),
                this.emptyIntegerList.tail.prev.data);

        assertThrows("Check for null pointer exception", NullPointerException.class,
                () -> this.threeStringList.add(null));
    }

    /**
     * Aims to test the add(int index, E data) method.
     * Add a valid argument to the beginning of MyLinkedList.
     */
    @Test
    public void testCustomAddIdxToStart() {
        this.populateLinkedList();
        threeStringList.add(0, "hello");
        assertEquals("Check if node is added through head", "hello",
                this.threeStringList.head.next.data);
        assertEquals("Check length is increased by 1", 4, this.threeStringList.size);
        assertEquals("Check if node 2 is untouched", "CSE 12", this.threeStringList.head.next.next.data);
        assertEquals("Check if node 3 is untouched", "Paul Cao", this.threeStringList.head.next.next.next.data);
        assertEquals("Check if node 4 is untouched", "Christine Alvarado",
                this.threeStringList.head.next.next.next.next.data);

    }

    /**
     * Aims to test the add(int index, E data) method.
     * Add a valid argument to the middle of MyLinkedList.
     */
    @Test
    public void testCustomAddIdxToMiddle() {
        this.populateLinkedList();
        threeStringList.add(1, "hello");
        assertEquals("Check if node is added through head", "hello",
                this.threeStringList.head.next.next.data);
        assertEquals("Check add from next node's prev pointer", "hello",
                this.threeStringList.head.next.next.next.prev.data);
        assertEquals("Check length is increased by 1", 4, this.threeStringList.size);
        assertEquals("Check if node 1 is untouched", "CSE 12", this.threeStringList.head.next.data);
        assertEquals("Check if node 3 is untouched", "Paul Cao", this.threeStringList.head.next.next.next.data);
        assertEquals("Check if node 4 is untouched", "Christine Alvarado",
                this.threeStringList.head.next.next.next.next.data);

    }

    /**
     * Aims to test the remove(int index) method. Remove from an empty list.
     */
    @Test
    public void testCustomRemoveFromEmpty() {
        assertThrows("Should throw IndexOutOfBoundsException", IndexOutOfBoundsException.class,
                () -> emptyIntegerList.remove(0));
    }

    /**
     * Aims to test the remove(int index) method.
     * Remove a valid argument from the middle of MyLinkedList.
     */
    @Test
    public void testCustomRemoveFromMiddle() {
        this.populateLinkedList();
        MyLinkedList<String>.Node node2 = this.threeStringList.head.next.next.next;
        MyLinkedList<String>.Node node1 = this.threeStringList.head.next;
        String removedNode = this.threeStringList.remove(1);
        assertEquals("Check for length decrease by 1", 2, this.threeStringList.size);
        assertSame("Check if the removed node's next replaced the removed's place", node2,
                this.threeStringList.head.next.next);
        assertSame("Check if the prev of removed can still be accessed from the tail", node1,
                this.threeStringList.tail.prev.prev);
        assertEquals("Check if removed node is as expected", "Paul Cao", removedNode);
    }

    /**
     * Aims to test the set(int index, E data) method.
     * Set an out-of-bounds index with a valid data argument.
     */
    @Test
    public void testCustomSetIdxOutOfBounds() {
        this.populateLinkedList();
        assertThrows("Should throw exception for index greater than the sixe of the list",
                IndexOutOfBoundsException.class,
                () -> this.threeStringList.set(3, "hello"));
        assertThrows("Should throw exception for negative index", IndexOutOfBoundsException.class,
                () -> this.threeStringList.set(-1, "hello"));
    }

    @Test
    public void testSetToValidElement() {
        this.populateLinkedList();
        assertThrows("check nullpointerexception", NullPointerException.class, () -> this.threeStringList.set(1, null));
        String str = this.threeStringList.set(1, "hello");
        assertEquals("check set from head", "CSE 12", this.threeStringList.head.next.data);
        assertEquals("check set from head", "hello", this.threeStringList.head.next.next.data);
        assertEquals("check set from head", "Christine Alvarado", this.threeStringList.head.next.next.next.data);
        assertEquals("check return", "Paul Cao", str);
        assertEquals("check set from tail", "CSE 12", this.threeStringList.tail.prev.prev.prev.data);
        assertEquals("check set from tail", "hello", this.threeStringList.tail.prev.prev.data);
        assertEquals("check set from tail", "Christine Alvarado", this.threeStringList.tail.prev.data);
        assertEquals("check size", 3, this.threeStringList.size);
    }
}
