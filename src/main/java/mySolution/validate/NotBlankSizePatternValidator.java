package mySolution.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import lombok.Data;
import mySolution.annotation.NotBlankSizePattern;
@Data
public class NotBlankSizePatternValidator implements  ConstraintValidator<NotBlankSizePattern,String>{

	private String name;
	private String pettern ;
	private String message;
	private boolean isEmail;
	private short min;
	private short max;
	//RFC 5321 Email Regix
	private final static String emailRegix ="[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";


	@Override
	public void initialize(NotBlankSizePattern validator) {
		//interfaceで宣言しておくとアノテーションに引数として渡すことができる
		isEmail = validator.isEmail();
		name = validator.name();
		pettern = validator.pettern();
		min = validator.min();
		max = validator.max();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//ディフォルトのエラーメッセージは使いません。
		context.disableDefaultConstraintViolation();
		//Nullチェック
		if(!StringUtils.hasLength(value)){
			context.buildConstraintViolationWithTemplate(name +"を入力してください。").addConstraintViolation();
			return false;
		}
		//文字数チェック
		if(value.length() < min || value.length() >= max) {
			context.buildConstraintViolationWithTemplate( name +"は" + min +"文字以上"+ max + "文字以下で入力してください。").addConstraintViolation();
			return false;
		}
		//メールチェック
		if(isEmail && !value.matches(emailRegix)) {
			context.buildConstraintViolationWithTemplate("メールアドレスを入力してください。").addConstraintViolation();
			return false;
		}
		//正規表現チェック
		if(StringUtils.hasLength(pettern) && !value.matches(pettern)) {
			context.buildConstraintViolationWithTemplate("正しい形式で入力してください。").addConstraintViolation();
		}
		return true;
	}

}
