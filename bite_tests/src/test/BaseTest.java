package test;


import org.junit.Assert;

import java.util.ArrayList;

public class BaseTest {
    public void AssertIterEquals(Iterable<?> expected, Iterable<?> actual) {
        ArrayList<Object> eList = new ArrayList<Object>();
        ArrayList<Object> aList = new ArrayList<Object>();
        for (Object v : expected)
            eList.add(v);
        for (Object v : actual)
            aList.add(v);

        Assert.assertEquals(eList.size(), aList.size());
        for (int i = 0; i < eList.size(); i++)
            Assert.assertEquals(eList.get(i), aList.get(i));

    }
}
