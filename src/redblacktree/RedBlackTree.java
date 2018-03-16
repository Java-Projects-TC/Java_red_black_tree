package redblacktree;

import java.util.NoSuchElementException;

public class RedBlackTree<K extends Comparable<? super K>, V> {

  Node<K, V> root;

  public RedBlackTree() {
    this.root = null;
  }

  RedBlackTree(Node<K, V> root) {
    this.root = root;
  }

  public synchronized void put(K key, V value) {

    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);

    Node<K, V> parent = pair.getX();
    Node<K, V> current = pair.getY();

    if (current != null) { // found an existing key
      current.setValue(value);
      return;
    }

    if (parent == null) { // empty tree
      root = new Node<>(key, value, Colour.BLACK);
      return;
    }
    /* create a new key */
    int comparison = key.compareTo(parent.getKey());
    Node<K, V> newNode = new Node<>(key, value, Colour.RED);
    if (comparison < 0) {
      parent.setLeft(newNode);
    } else {
      assert comparison > 0;
      parent.setRight(newNode);
    }

    insertCaseOne(newNode);

  }

  private void insertCaseOne(Node<K, V> current) {
    if (current.equals(root)) {
      current.setBlack();
    } else {
      insertCaseTwo(current);
    }
  }

  private void insertCaseTwo(Node<K, V> current) {
    if (!current.getParent().isBlack()) {
      insertCaseThree(current);
    }
  }

  private void insertCaseThree(Node<K, V> current) {
    Node<K, V> uncle = current.getUncle();
    if (uncle != null && uncle.isRed()) {
      current.getParent().setBlack();
      current.getUncle().setBlack();
      current.getGrandparent().setRed();

      insertCaseOne(current.getGrandparent());
    } else {
      insertCaseFour(current);
    }
  }

  private void insertCaseFour(Node<K, V> current) {
    if (caseFourA(current)) {
      current.getParent().rotateLeft();
      insertCaseFive(current.getLeft());

    } else if (caseFourB(current)) {
      current.getParent().rotateRight();
      insertCaseFive(current.getRight());

    } else {
      insertCaseFive(current);
    }
  }

  private boolean caseFourA(Node<K, V> current) {
    return current.getParent().isLeftChild() && current.isRightChild();
  }

  private boolean caseFourB(Node<K, V> current) {
    return current.getParent().isRightChild() && current.isLeftChild();
  }

  private void insertCaseFive(Node<K, V> current) {
    Node<K, V> grandparent = current.getGrandparent();

    current.getParent().setBlack();
    grandparent.setRed();

    if (current.isLeftChild()) {
      grandparent.rotateRight();
    }
    if (current.isRightChild()) {
      grandparent.rotateLeft();
    }
    if (grandparent.equals(root)) {
      this.root = current.getParent();
    }
  }

  private Tuple<Node<K, V>, Node<K, V>> findNode(K key) {
    Node<K, V> current = root;
    Node<K, V> parent = null;

    while (current != null) {
      parent = current;

      int comparison = key.compareTo(current.getKey());
      if (comparison < 0) {
        current = current.getLeft();
      } else if (comparison == 0) {
        break;
      } else {
        assert comparison > 0;
        current = current.getRight();
      }
    }
    return new Tuple<Node<K, V>, Node<K, V>>(parent, current);
  }

  public synchronized boolean contains(K key) {
    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);
    return pair.getY() != null;
  }

  public synchronized V get(K key) {
    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);
    Node<K, V> current = pair.getY();
    if (current == null) {
      throw new NoSuchElementException();
    }
    return current.getValue();
  }

  public synchronized void clear() {
    this.root = null;
  }

  public synchronized String toString() {
    return "RBT " + root + " ";
  }

}
