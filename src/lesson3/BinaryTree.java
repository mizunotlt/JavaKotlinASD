package lesson3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }

    }

    private Node<T> root = null; //Корень дерева

    private int size = 0; //Глубина дерева

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }
    private Node<T> findParent(T value) {
        if ((root == null) || (root.value == value)) return null;
        return findParent(root, value);
    }
    private Node<T> findParent(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) return null;
        if (comparison < 0) {
            if (start.left == null) return null;
            if (start.left.value.compareTo(value) == 0) return start;
            return findParent(start.left, value);
        } else {
            if (start.right == null) return null;
            if (start.right.value.compareTo(value) == 0) return start;
            return findParent(start.right, value);
        }
    }

    @Override
    public boolean remove(Object o) {
        Node<T> node = find((T) o);
        if (node == null)
            return false;
        //Нет дочерних узлов
        if ((node.left == null) && (node.right == null)) {
            if (node.value.compareTo(findParent(root, node.value).value) > 0){
                findParent(root, node.value).right = null;
                size --;
                return true;
            }else{
                findParent(root, node.value).left = null;
                size --;
                return true;
            }
        }
        //левый дочерний узел
        if ((node.left != null) && (node.right == null)) {
            if (node.value.compareTo(findParent(root, node.value).value) > 0){
                findParent(root, node.value).right = node.left;
                size --;
                return true;
            }else{
                findParent(root, node.value).left = node.left;
                size --;
                return true;
            }
        }
        //правый дочерний узел
        if ((node.left == null) && (node.right != null)) {
            if (node.value.compareTo(findParent(root, node.value).value) > 0){
                findParent(root, node.value).right = node.right;
                size --;
                return true;
            }else{
                findParent(root, node.value).left = node.right;
                size --;
                return true;
            }
        }
        //узел имеет и левый дочерний узел и правый
        if ((node.left != null) && (node.right != null)){
            Node <T> tempLeft = findLeftNull(find((T) o).right);
            if (tempLeft.right == null){
                tempLeft.left = node.left;
                tempLeft.right = node.right;
                findParent(root, tempLeft.value).left = null;
                if (node.value.compareTo(findParent(root, node.value).value) > 0){
                    findParent(root, node.value).right = tempLeft;
                }else{
                    findParent(root, node.value).left = tempLeft;
                }
                size --;
                return true;
            }
            if (tempLeft.right != null){
                tempLeft.left = node.left;
                findParent(root, tempLeft.value).left = tempLeft.right;
                tempLeft.right = node.right;
                if (node.value.compareTo(findParent(root, node.value).value) > 0){
                    findParent(root, node.value).right = tempLeft;
                }else{
                    findParent(root, node.value).left = tempLeft;
                }
                size --;
                return true;
            }
        }
        return false;
    }
    private Node<T> findLeftNull(Node<T> node) {
        if (node == null) {
            return null;
        }
        if (node.left != null) {
            return findLeftNull(node.left);
        }
        return node;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {}

        private Node<T> findNext() {
            if (current == null)
                return  new Node<>(first());
            if (current.right != null){
                current.right = findMin(current.right);
            }
            else{
                if(findParentIt(root,current.value) == null)
                    return null;
                if (findParentIt(root,current.value).value.compareTo(current.value) > 0){
                    return findParentIt(root,current.value);
                }
                else return null;
            }
            return current.right;
        }

        @Override
        public boolean hasNext() {

            return findNext() !=  null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private Node<T> findMin(Node<T> current){
        while(current.left != null) {
            current = current.left;
        }
        return current;
    }
    private Node<T> findParentIt(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) return null;
        if (comparison < 0) {
            if (start.left == null) return null;
            if (start.left.value.compareTo(value) == 0) return start;
            return findParentIt(start.left, value);
        } else {
            if (start.right == null) return null;
            if (start.right.value.compareTo(value) == 0) return findParentIt(root, start.value);
            return findParentIt(start.right, value);
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}

