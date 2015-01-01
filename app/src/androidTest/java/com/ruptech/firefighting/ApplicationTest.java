package com.ruptech.firefighting;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.ruptech.firefighting.model.User;

import junit.framework.Assert;

import java.util.List;
import java.util.Map;

public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testLogin() throws Exception {
        User user = App.getHttpServer().userLogin("", "");
        Assert.assertEquals("1", user.get编号());
        Assert.assertEquals("admin", user.get用户名());
    }

    public void testGetUndoTaskList() throws Exception {

        List<Map<String, Object>> tasks = App.getHttpServer().getTodoTaskList();
        Assert.assertEquals(24, tasks.size());
        Assert.assertEquals(24, tasks.size());
    }

    public void testGetTask() throws Exception {

        Map<String, Object> task = App.getHttpServer().getTask("");
        Assert.assertTrue(task.containsKey("task"));
        Assert.assertTrue(task.containsKey("worklogs"));
        Assert.assertTrue(task.containsKey("items"));
        Assert.assertTrue(task.containsKey("wokers"));
    }
}