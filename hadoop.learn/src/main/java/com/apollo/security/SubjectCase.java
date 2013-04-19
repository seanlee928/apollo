package com.apollo.security;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class SubjectCase {

	/**
	 * @param args
	 * @throws LoginException
	 * 
	 * 
	 *             public LoginContext(String name, Subject subject,
	 *             CallbackHandler callbackHandler, Configuration config)
	 */
	public static void main(String[] args) throws LoginException {
		Subject subject = new Subject();
		CallbackHandler callback = new CallbackHandlerCL();
		Configuration config = new HadoopConfiguration();
		LoginContext loginContext = new LoginContext("dragon", subject,
				callback, config);
		loginContext.login();
	}

	static final boolean windows = System.getProperty("os.name").startsWith(
			"Windows");
	private static final String OS_LOGIN_MODULE_NAME;
	private static final Class<? extends Principal> OS_PRINCIPAL_CLASS;
	static {
		if (windows) {
			// OS_LOGIN_MODULE_NAME =
			// "com.sun.security.auth.module.NTLoginModule";
			// OS_PRINCIPAL_CLASS = NTUserPrincipal.class;
			OS_LOGIN_MODULE_NAME = PasswordModle.class.getName();
			// com.apollo.security.SubjectCase.PasswordModele
			OS_PRINCIPAL_CLASS = PasswordPrincipal.class;
		} else {
			// com.apollo.security.SubjectCase.PasswordModele
			// com.sun.security.auth.module.UnixLoginModule UnixPrincipal.class;
			OS_LOGIN_MODULE_NAME = "com.apollo.security.SubjectCase.PasswordModele";
			OS_PRINCIPAL_CLASS = PasswordPrincipal.class;
		}
	}
	private static final AppConfigurationEntry OS_SPECIFIC_LOGIN = new AppConfigurationEntry(
			OS_LOGIN_MODULE_NAME, LoginModuleControlFlag.REQUIRED,
			new HashMap<String, String>());

	private static class HadoopConfiguration extends Configuration {

		@Override
		public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
			return new AppConfigurationEntry[] { OS_SPECIFIC_LOGIN };
		}
	}

	private static class CallbackHandlerCL implements CallbackHandler {

		@Override
		public void handle(Callback[] callbacks) throws IOException,
				UnsupportedCallbackException {
			for (int i = 0; i < callbacks.length; i++) {
				if (callbacks[i] instanceof PasswordCallback) {
					PasswordCallback pc = (PasswordCallback) callbacks[i];
					pc.getPrompt();
					pc.setPassword("123456 ,12* ".toCharArray());
				}
			}
		}
	}

	public class PasswordPrincipal implements Principal, java.io.Serializable {
		@Override
		public String getName() {
			return null;
		}

	}
}
