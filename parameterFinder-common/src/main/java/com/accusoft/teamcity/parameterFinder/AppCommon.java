package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.*;
import javax.annotation.Nullable;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppCommon extends BaseController {
    private PluginDescriptor myDescriptor;

    public AppCommon (WebControllerManager manager, PluginDescriptor descriptor) {
        manager.registerController("/parameterFinderXML.html",this);
        myDescriptor=descriptor;
    }

    @javax.annotation.Nullable
    @Override
    protected ModelAndView doHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView(myDescriptor.getPluginResourcesPath("parameters.xml"));
    }

}