package mySolution.model;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

import org.springframework.util.StringUtils;

import lombok.Data;
import mySolution.annotation.NotBlankSizePattern;

@Data
public class RegistForm implements Serializable {
	@NotBlankSizePattern(name = "ユーザーID",min=6,max=60,pettern = "^[A-Za-z0-9@._]+$")
	private String userId;
	@NotBlankSizePattern(name = "パスワード",min=6,max=60,pettern = "^[A-Za-z0-9@._]+$")
	private String userPassword;
	@NotBlankSizePattern(name = "メールアドレス",isEmail=true)
	private String mailAddress;
	@NotBlank(message = "パスワードと同一のものを入力してください。")
	private String retypeUserPassword;

    @AssertTrue(message = "パスワードと同一のものを入力してください。")
	public boolean isMatchPsw(){
		if(StringUtils.hasLength(userPassword)){
			if(userPassword.equals(retypeUserPassword)) {
				return true;
			}
		}
		return false;
	}

	public void clearPsw(){
		this.userPassword = "";
		this.retypeUserPassword = "";
	}
}