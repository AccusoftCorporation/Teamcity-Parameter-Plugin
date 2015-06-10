package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MyCustomTab extends AdminPage {
    public MyCustomTab(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor descriptor) {
        super(pagePlaces);
        setPluginName("parameterFinder");
        setIncludeUrl(descriptor.getPluginResourcesPath("Hello.jsp"));
        setTabTitle("Parameter Finder");
        setPosition(PositionConstraint.after("clouds", "email", "jabber"));
        register();
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        return super.isAvailable(request) && checkHasGlobalPermission(request, Permission.CHANGE_SERVER_SETTINGS);
    }

    @NotNull
    public String getGroup() {
        return SERVER_RELATED_GROUP;
    }
}
