/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-12, Red Hat Middleware LLC, and others contributors as indicated
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
package org.overlord.gadgets.web.client.auth;


/**
 * @author: Jeff Yu
 * @date: 10/05/11
 */
public class CurrentUser {

    private String userName;

    private String displayName;

    private long userId;

    private long currentPage;

    /**
     * Constructor.
     */
    public CurrentUser() {
        initFromJS();
    }

    /**
     * Initialize the current user from information in a well-known javascript
     * variable.
     */
    private native final void initFromJS() /*-{
        try {
            this.@org.overlord.gadgets.web.client.auth.CurrentUser::init(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(
                $wnd.g_currentUser.userName, $wnd.g_currentUser.displayName,
                '' + $wnd.g_currentUser.userId, '' + $wnd.g_currentUser.currentPageId);
        } catch (e) {
            this.@org.overlord.gadgets.web.client.auth.CurrentUser::init(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(
                "<unknown>", "Unknown", "-1", "0");
        }
    }-*/;

    /**
     * Init from values.
     * @param userName
     * @param displayName
     * @param userId
     * @param currentPage
     */
    private void init(String userName, String displayName, String userId, String currentPage) {
        this.userName = userName;
        this.displayName = displayName;
        this.userId = Long.parseLong(userId);
        this.currentPage = Long.parseLong(currentPage);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
