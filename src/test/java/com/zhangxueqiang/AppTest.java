package com.zhangxueqiang;

import com.zhangxueqiang.crm.utils.Md5Util;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testetest(){
        System.out.println(Md5Util.encode("123456"));
    }


    public void teststestststset(){
        String a = "aaaaaaaaaaaaaaaaaaaaaa";
        String b = "aaaaaaaaaaaaaaaaaaaaaa";
        System.out.println(a==b);
        System.out.println(a.equals(b));
    }
}
