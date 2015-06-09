package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.*;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppServer extends BaseController {
    private PluginDescriptor myDescriptor;

    public AppServer (WebControllerManager manager, PluginDescriptor descriptor) {
        manager.registerController("/parameterFinder.html",this);
        myDescriptor=descriptor;
        MyCustomTab tab = new MyCustomTab(manager, descriptor);
        tab.register();
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView(myDescriptor.getPluginResourcesPath("Hello.jsp"));
    }

}