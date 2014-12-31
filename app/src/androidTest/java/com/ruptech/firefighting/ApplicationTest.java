package com.ruptech.firefighting;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.ruptech.firefighting.model.User;

import junit.framework.Assert;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testLogin() throws Exception {
        User user = App.getHttpServer().userLogin("", "");
        Assert.assertEquals("1", user.get编号());
        Assert.assertEquals("admin", user.get用户名());
    }
}