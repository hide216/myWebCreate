package mySolution;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.api.client.json.gson.GsonFactory;

import lombok.Getter;
@Getter
@Component
@PropertySource(value = "classpath:application.properties")
public class AppConfig {

	@Autowired
	Environment env;

	/**
	 * メッセージ管理　多言語化対応
	 * Message management Multi-language support
	 */
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		//Encodeを指定
		source.setDefaultEncoding("UTF-8");
		//message propertiesファイルのPathを指定
		source.setBasenames("messages/message");
		source.setUseCodeAsDefaultMessage(true);
		source.setDefaultLocale(Locale.JAPANESE);
		return source;
	}

	/**
	 * Abstract low-level JSON factory.
	 *
	 * @return {@link JacksonFactory}
	 */
	@Bean
	public GsonFactory jacksonFactory() {
		return GsonFactory.getDefaultInstance();
	}


}