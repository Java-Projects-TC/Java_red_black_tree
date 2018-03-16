package redblacktree;

public final class Node<K extends Comparable<? super K>, V> {

  private Node<K, V> left;
  private Node<K, V> right;
  private Node<K, V> parent;

  private Colour colour;

  private K key;
  private V value;

  Node(K key, V value, Colour colour) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null!");
    }

    this.key = key;
    this.value = value;
    this.colour = colour;
  }

  /* Tree operations */

  Node<K, V> getParent() {
    return this.parent;
  }

  private void setParent(Node<K, V> node) {
    this.parent = node;
  }

  void setRight(Node<K, V> node) {
    if (right != null && right.getParent() == this) {
      right.setParent(null);
    }
    this.right = node;
    if (node != null) {
      node.setParent(this);
    }
  }

  void setLeft(Node<K, V> node) {
    if (left != null && left.getParent() == this) {
      left.setParent(null);
    }
    this.left = node;
    if (node != null) {
      node.setParent(this);
    }
  }

  boolean isLeftChild() {
    return parent != null && parent.getLeft() == this;
  }

  boolean isRightChild() {
    return parent != null && parent.getRight() == this;
  }

  Node<K, V> getLeft() {
    return this.left;
  }

  Node<K, V> getRight() {
    return this.right;
  }

  Node<K, V> getGrandparent() {
    return this.parent != null ? this.parent.parent : null;
  }

  Node<K, V> getUncle() {
    Node<K, V> grandparent = getGrandparent();
    return grandparent != null ? (grandparent.left == this.parent ?
        grandparent.right : grandparent.left) : null;
  }

  Node<K, V> rotateRight() {
    Node<K, V> leftChild = this.getLeft();
    this.setLeft(leftChild.getRight());
    reparent(leftChild);
    leftChild.setRight(this);
    return leftChild;
  }

  Node<K, V> rotateLeft() {
    Node<K, V> rightChild = this.getRight();
    this.setRight(rightChild.getLeft());
    reparent(rightChild);
    rightChild.setLeft(this);
    return rightChild;
  }

  private void reparent(Node<K, V> replacement) {
    if (parent != null) {
      if (this.isLeftChild()) {
        parent.setLeft(replacement);
      }
      if (this.isRightChild()) {
        parent.setRight(replacement);
      }
    }
  }

  /* Colour operations */

  boolean isBlack() {
    return colour == Colour.BLACK;
  }

  boolean isRed() {
    return colour == Colour.RED;
  }

  void setRed() {
    colour = Colour.RED;
  }

  void setBlack() {
    colour = Colour.BLACK;
  }

  /* Key value operations */

  K getKey() {
    return key;
  }

  V getValue() {
    return value;
  }

  void setValue(V value) {
    this.value = value;
  }

  public String toString() {
    return "{ " + colour + ": " + left + " [" + key + ", " + value + "] "
        + right + " }";
  }
}
