package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;

public class MyCustomTab extends AdminPage {
    public MyCustomTab(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor descriptor) {
        super(pagePlaces);
        setPluginName("parameterFinder");
        setIncludeUrl(descriptor.getPluginResourcesPath("../../resources/buildServerResources/Hello.jsp"));
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
