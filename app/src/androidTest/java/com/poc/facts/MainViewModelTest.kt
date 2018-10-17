package com.poc.facts

import android.app.Application
import android.support.test.runner.AndroidJUnit4
import com.poc.facts.models.Fact
import com.poc.facts.models.FactNews
import com.poc.facts.viewmodels.MainViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    var mainViewModel:MainViewModel? = null;
    var mockApplication:Application? = null;

    @Before
    fun setup()
    {
        mockApplication = Mockito.mock(Application::class.java)
        mainViewModel = MainViewModel(mockApplication!!)
    }

    @Test
    fun excludeInvalidFactsTest()
    {
        var factNews:FactNews = FactNews()
        var factNewsWithValidFacts = FactNews()
        var rows:MutableList<Fact> = mutableListOf();

        factNews.title = "Test Data"

        var validFact:Fact = Fact();
        validFact.title = "ValidFact1"
        validFact.description = "Valid Fact1 Description"
        validFact.imageHref = "https://validfact1.com/a.jpg"
        rows.add(validFact)

        var invalidFact:Fact = Fact()
        rows.add(invalidFact)

        factNews.rows = rows;
        factNewsWithValidFacts = mainViewModel!!.getValidFacts(factNews)

        assert(factNewsWithValidFacts.rows.size == 1);
    }

    @After
    fun clear()
    {
        mockApplication = null
        mainViewModel = null
    }
}