/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-11, Red Hat Middleware LLC, and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.savara.gadget.web.client;

import com.google.gwt.core.client.GWT;

/**
 * @author: Jeff Yu
 * @date: 20/03/12
 */
public class URLBuilder {
    
    private final static String urlBase = GWT.getHostPageBaseURL();

    public static String getAllUsersURL() {
        return urlBase + "rs/users/all";
    }
    
    public static String getRegisterUserURL() {
        return urlBase + "rs/users/user";
    }
    
    public static String getCheckUserNameURL(String username) {
        return urlBase + "rs/users/user/username/check";
    }
    
    public static String getInvalidSessionURL() {
        return urlBase + "rs/users/user/invalid";
    }
    
    public static String getAuthenticationURL() {
        return urlBase + "rs/users/authentication";
    }
    
    public static String getPagesURL(Long userId) {
        return urlBase + "rs/users/user/" + userId + "/pages";
    }
    
    public static String getStoreURL(int offset, int pageSize) {
        return urlBase + "rs/stores/all/" + offset + "/" + pageSize;
    }
    
    public static String getAddGadgetToPageURL(long pageId, long gadgetId) {
        return urlBase + "rs/stores/page/" + pageId + "/gadget/" + gadgetId;
    }
    
    public static String getRemoveWidgetURL(long widgetId) {
        return urlBase + "rs/users/widget/" + widgetId + "/remove";
    }

}
