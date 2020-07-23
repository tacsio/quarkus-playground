package io.tacsio;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;

@ApplicationScoped
public class MessageHelper {

	public String getMessage(String key, @NotNull HttpHeaders headers) {
		Locale requestLocale = headers.getAcceptableLanguages().stream()
			.findAny()
			.orElse(getDefaultLocale());
		return getMessage(key, requestLocale);
	}

	public String getMessage(String key, @NotNull Locale locale) {
		return ResourceBundle.getBundle("ValidationMessages", locale).getString(key);
	}

	public String getMessage(String key) {
		return getMessage(key, getDefaultLocale());
	}

	private Locale getDefaultLocale() {
		return Locale.ROOT;
	}
}