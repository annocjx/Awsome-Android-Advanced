package com.prim.gkapp

import com.prim.gkapp.ext.otherwise
import com.prim.gkapp.ext.yes
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
        val result = getBoolean().yes {
            1
        }.otherwise {
            2
        }
        println(result)
    }

    fun getBoolean() = true
}