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

        List<Map<String, Object>> tasks = App.getHttpServer().getTaskList(DataType.TAB_UNDO);
        Assert.assertEquals(24, tasks.size());
        Assert.assertEquals(24, tasks.size());
    }

    public void testGetTask() throws Exception {

        Map<String, Object> task = App.getHttpServer().getTask("taskId", "");
        Assert.assertTrue(task.containsKey("task"));
        Assert.assertTrue(task.containsKey("worklogs"));
        Assert.assertTrue(task.containsKey("items"));
        Assert.assertTrue(task.containsKey("wokers"));
    }

    public void testGetTypes() throws Exception {

        Map<Integer, String> types = App.getHttpServer().getOptions("system");
        Assert.assertTrue(types.containsKey(1));
        Assert.assertEquals("未带电话插口手动报警报钮", types.get(2));
    }
}