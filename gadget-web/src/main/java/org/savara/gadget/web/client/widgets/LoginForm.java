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
package org.savara.gadget.web.client.widgets;

import com.allen_sauer.gwt.log.client.Log;
import com.allen_sauer.gwt.log.client.Logger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.savara.gadget.web.client.auth.CurrentUser;
import org.savara.gadget.web.client.model.JSOParser;
import org.savara.gadget.web.client.presenter.LoginPresenter;
import org.savara.gadget.web.client.util.RestfulInvoker;
import org.savara.gadget.web.client.util.UUID;
import org.savara.gadget.web.shared.dto.UserModel;

/**
 * @author: Jeff Yu
 * @date: 19/03/12
 */
public class LoginForm extends Composite {

    interface LoginUiBinder extends UiBinder<Widget, LoginForm> {}

    private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

    private String loginId;
    
    private String signupId;

    private CurrentUser currentUser;
    
    @UiField
    DivElement loginForm;

    @UiField
    DivElement signupForm;
    
    @UiField
    TextBox username;
    @UiField
    PasswordTextBox password;
    @UiField
    Label loginError;
    @UiField
    Label signupError;

    @UiField
    TextBox signupUsername;

    @UiField
    PasswordTextBox signupPassword;
    @UiField
    PasswordTextBox signupConfirmPassword;
    @UiField
    TextBox email;
    @UiField
    TextBox displayName;

    private LoginPresenter presenter;

    public LoginForm(CurrentUser user) {
        String suffix = UUID.uuid(4);
        loginId = "loginform-" + suffix;
        signupId = "signupform-" + suffix;
        
        currentUser = user;
        
        initWidget(uiBinder.createAndBindUi(this));

        loginForm.setId(loginId);
        signupForm.setId(signupId);

    }

    @Override
    public void onAttach() {
        super.onAttach();
        initializeLoginForm(this, loginId, signupId);
        initializeSignupForm(this, loginId, signupId);
    }


    public void doLogin() {
        if (isEmpty(username.getValue()) || isEmpty(password.getValue())) {
            loginError.setText("username or password can not be empty.");
            return;
        }
        presenter.authenticateUser(username.getValue(), password.getValue(), new RestfulInvoker.Response() {
            public void onResponseReceived(Request request, Response response) {
                Log.debug(response.getText());
                UserModel user = JSOParser.getUserModel(response.getText());
                if (user.getUserId() != 0) {
                    currentUser.setLoggedIn(true);
                    currentUser.setUserId(user.getUserId());
                    currentUser.setUserName(user.getUserName());
                    currentUser.setCurrentPage(user.getCurrentPageId());

                    loginError.setText("");
                    username.setValue("");
                    password.setValue("");
                    closeWindow(loginId);
                    presenter.forwardToIndex();
                } else {
                    loginError.setText("Authentication failed.");
                    password.setValue("");
                }
            }
        });

    }
    
    private boolean isEmpty(String value) {
        if (value == null || "".equals(value.trim())) {
            return true;
        }
        return false;
    }

    public void doSignup(){
        //TODO: best to enumerate all of possible errors at once.
        if (isEmpty(signupUsername.getValue()) || isEmpty(signupPassword.getValue())) {
            signupError.setText("username and password are required.");
            return;
        } else if (!signupConfirmPassword.getValue().equals(signupPassword.getValue())) {
            signupError.setText("Password and Confirm Password do not match");
            signupConfirmPassword.setValue("");
            signupPassword.setValue("");
            return;
        } else if (email.getValue() != null && email.getValue().indexOf("@") == -1) {
            signupError.setText("email is not valid.");
            return;
        }

        presenter.checkUserName(signupUsername.getValue(), new RestfulInvoker.Response(){

            public void onResponseReceived(Request request, Response response) {
                if ("false".equals(response.getText())) {
                    registerUser();
                } else {
                    signupError.setText("the username[" + signupUsername.getValue() + "] is already existed.");
                    signupPassword.setValue("");
                    signupConfirmPassword.setValue("");
                }
            }
        });

    }

    private void registerUser() {
        presenter.registerUser(signupUsername.getValue(),
                signupPassword.getValue(), email.getValue(),
                displayName.getValue(), new RestfulInvoker.Response() {

            public void onResponseReceived(Request request, Response response) {
                UserModel user = JSOParser.getUserModel(response.getText());

                if (user.getUserId() != 0) {
                    currentUser.setLoggedIn(true);
                    currentUser.setUserId(user.getUserId());
                    currentUser.setUserName(user.getUserName());
                    currentUser.setCurrentPage(user.getCurrentPageId());

                    signupUsername.setValue("");
                    signupPassword.setValue("");
                    signupConfirmPassword.setValue("");
                    email.setValue("");
                    displayName.setValue("");
                    signupError.setText("");
                    loginError.setText("");
                    closeWindow(signupId);
                    presenter.forwardToIndex();
                }

            }

        });
    }

    public void setPresenter(LoginPresenter loginPresenter) {
        this.presenter = loginPresenter;
    }


    /**
     * JSNI methods
     */
    private static native void initializeLoginForm(final LoginForm form, String loginId, String signupId) /*-{
        $wnd.$('#' + loginId).dialog({
            autoOpen:true,
            modal:false,
            width:300,
            height:200,
            closeOnEscape: false,
            open: function(event, ui) { $wnd.$(".ui-dialog-titlebar-close", ui.dialog).hide(); },
            buttons:{
                Login: function(){
                    form.@org.savara.gadget.web.client.widgets.LoginForm::doLogin()();
                },
                Signup: function() {
                    $wnd.$(this).dialog("close");
                    $wnd.$('#' + signupId).dialog("open");
                }
            }
        });
    }-*/;

    private static native void initializeSignupForm(final LoginForm form, String loginId, String signupId) /*-{
        $wnd.$('#' + signupId).dialog({
            autoOpen:false,
            modal:false,
            width:350,
            height:300,
            closeOnEscape: false,
            open: function(event, ui) { $wnd.$(".ui-dialog-titlebar-close", ui.dialog).hide(); },
            buttons:{
                Submit: function(){
                    form.@org.savara.gadget.web.client.widgets.LoginForm::doSignup()();
                },
                Cancel: function() {
                    $wnd.$('#'+signupId).dialog("close");
                    $wnd.$('#'+loginId).dialog("open");
                }
            }
        });
    }-*/;

    private static native void closeWindow(String id) /*-{
        $wnd.$('#' + id).dialog("close");
    }-*/;

    private static native void openWindow(String id) /*-{
        $wnd.$('#' + id).dialog("open");
    }-*/;
    
    private static native void println(String msg) /*-{
        $wnd.alert(msg);
    }-*/;
}
