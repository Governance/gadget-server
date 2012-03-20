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
package org.guvnor.sam.gadget.web.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.guvnor.sam.gadget.web.client.presenter.LoginPresenter;
import org.guvnor.sam.gadget.web.client.util.UUID;

/**
 * @author: Jeff Yu
 * @date: 19/03/12
 */
public class LoginForm extends Composite {

    interface LoginUiBinder extends UiBinder<Widget, LoginForm> {}

    private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

    private String loginId;
    
    private String signupId;
    
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

    public LoginForm() {
        String suffix = UUID.uuid(4);
        loginId = "loginform-" + suffix;
        signupId = "signupform-" + suffix;
        
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

        presenter.authenticateUser(username.getValue(), password.getValue(), new RequestCallback() {
            public void onResponseReceived(Request request, Response response) {
                println(response.getText());
                loginError.setText("");
                username.setValue("");
                password.setValue("");
                closeWindow(loginId);
                presenter.forwardToIndex();
            }

            public void onError(Request request, Throwable throwable) {
                loginError.setText("Authentication failed, try admin/admin .");
            }
        });

    }

    public void doSignup(){

        presenter.registerUser(signupUsername.getValue(),
                signupPassword.getValue(), email.getValue(),
                displayName.getValue(), new RequestCallback() {
            public void onResponseReceived(Request request, Response response) {
                System.out.println("=== result ->: " + response.getText());
                println(response.getText());
            }

            public void onError(Request request, Throwable throwable) {
                println(throwable.getMessage());
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
                    form.@org.guvnor.sam.gadget.web.client.widgets.LoginForm::doLogin()();
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
            width:300,
            height:300,
            closeOnEscape: false,
            open: function(event, ui) { $wnd.$(".ui-dialog-titlebar-close", ui.dialog).hide(); },
            buttons:{
                Submit: function(){
                    form.@org.guvnor.sam.gadget.web.client.widgets.LoginForm::doSignup()();
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
    
    private static native void println(String msg) /*-{
        $wnd.alert(msg);
    }-*/;
}
