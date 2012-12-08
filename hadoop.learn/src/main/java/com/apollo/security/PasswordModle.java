package com.apollo.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class PasswordModle implements LoginModule {
	CallbackHandler callbackHandler;

	public PasswordModle() {

	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.callbackHandler = callbackHandler;
		System.out.println("PasswordModele initialize");
	}

	@Override
	public boolean login() throws LoginException {
		System.out.println("PasswordModele login");
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		if (callbackHandler != null) {
			try {
				PasswordCallback pc = new PasswordCallback(
						"please input password!", false);
				callbackHandler.handle(new Callback[] { pc });
				System.out.println("password:"
						+ String.valueOf(pc.getPassword()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedCallbackException e) {
				e.printStackTrace();
			}
		}
		System.out.println("PasswordModele commit");
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		System.out.println("PasswordModele abort");
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		System.out.println("PasswordModele logout");
		return true;
	}
}
