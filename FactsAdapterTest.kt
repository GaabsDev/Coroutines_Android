package com.pliniodev.chucknorrisfacts.view.adapter

import android.content.Context
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FactsAdapterTest {

    @Mock
    var context: Context = mock(Context::class.java)

    @Test
    fun shouldReturnCategory_whenListCategoriesOnFactIsNotEmpty() {
        val listCategory = arrayListOf("food")

        val categoryResult = FactsAdapter(context).formattedCategory(listCategory)

        assertEquals("food", categoryResult)
    }

    @Test
    fun shouldReturnUNCATEGORIZED_whenListCategoriesOnFactIsEmpty() {
        val listCategory: ArrayList<String> = arrayListOf()

        val categoryResult = FactsAdapter(context).formattedCategory(listCategory)

        assertEquals("UNCATEGORIZED", categoryResult)
    }

    @Test
    fun shouldReturnFalse_whenValueLengthOnFactIsSmallerThen80() {
        val value = "lorem inpsum"

        val valueResult = FactsAdapter(context).isLongText(value)

        assertFalse(valueResult)
    }

    @Test
    fun shouldReturnTrue_whenValueLengthOnFactIsBiggerThen80() {
        val value = "lorem inpsum lorem inpsumlorem inpsumlorem inpsumlorem inpsumlorem inpsum" +
                "lorem inpsumlorem inpsumlorem inpsumlorem inpsumlorem inpsumlorem inpsum" +
                "lorem inpsumlorem inpsumlorem inpsumlorem inpsumlorem inpsumlorem inpsum"

        val valueResult = FactsAdapter(context).isLongText(value)

        assertTrue(valueResult)
    }
}
