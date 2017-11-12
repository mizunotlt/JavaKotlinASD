package lesson3

import org.junit.Assert.assertTrue
import org.junit.Test

class BinaryTreeTest {
    @Test
    fun Tets1() {
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        tree.add(3)
        tree.add(1)
        tree.add(4)
        tree.add(6)
        tree.add(8)
        tree.add(9)
        assertTrue(tree.remove(7))
        System.out.print(tree.toString());
    }
    @Test
    fun Tets2() {
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        assertTrue(tree.remove(5))
        System.out.print(tree.toString());
    }



}