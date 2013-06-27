/*
 * 2012-3 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
