package lesson3

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BinaryTreeTest {
    @Test
    fun Tets1() {
        //Тест на одного ребенка
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(3)
        tree.add(14)
        tree.add(9)
        tree.add(2)
        assertTrue(tree.remove(7))
        assertTrue(tree.remove(3))
        assertEquals(5, tree.size)
    }
    @Test
    fun Tets2() {
        //Тест без детей
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(3)
        tree.add(14)
        tree.add(9)
        tree.add(2)
        assertTrue(tree.remove(2))
        assertTrue(tree.remove(9))
        assertEquals(5, tree.size)
}

    @Test
    fun Tets3() {
        // у крайнего левого  в правом поодереве есть потомок
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(4)
        tree.add(8)
        tree.add(3)
        tree.add(14)
        tree.add(9)
        tree.add(2)
        tree.add(6)
        tree.add(7)
        assertTrue(tree.remove(4))
        assertEquals(8, tree.size)
    }
    @Test
    fun Tets4() {
        // у крайнего левого  в правом поодереве нет потомка
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(4)
        tree.add(8)
        tree.add(3)
        tree.add(14)
        tree.add(9)
        tree.add(2)
        tree.add(6)
        assertTrue(tree.remove(4))
        assertEquals(7, tree.size)
    }
    @Test
    fun Tets5() {
        val tree = BinaryTree<Int>()
        tree.add(52)
        tree.add(96)
        tree.add(5)
        tree.add(89)
        tree.add(20)
        tree.add(22)
        tree.add(72)
        tree.add(86)
        tree.add(14)
        tree.add(91)
        tree.add(71)
        tree.add(64)
        tree.add(36)
        tree.add(35)
        tree.add(76)
        tree.add(32)
        tree.add(44)
        tree.add(37)
        tree.add(61)
        tree.add(8)
        assertTrue(tree.remove(72))
    }
    @Test
    fun Test6(){
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(7)
        tree.add(4)
        tree.add(8)
        tree.add(3)
        tree.add(12)
        tree.add(15)
        val listCheck = mutableListOf<Int>(3,4,7,8,10,12,15)
        val listTest = mutableListOf<Int>()
        for (i in tree){
            listTest.add(i)
        }
        assertEquals(listCheck,listTest)
        val listCheck1 = mutableListOf<Int>()
        val listTest1 = mutableListOf<Int>()
        val nullTree = BinaryTree<Int>()

        try{
            for (i in nullTree){
                listTest1.add(i)
            }
        }catch (e : NoSuchElementException){
            assertEquals(listCheck1,listTest1)
        }
    }
}