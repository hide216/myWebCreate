package mySolution.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
/**
 2022.11.23 : h-miyake
 現状では不要　ip　または　UAなどを認証に追加する場合はUsernamePasswordAuthenticationTokenを拡張して使用するため削除はしない
 */

@Getter
public class UsernamePasswordAuthToken extends UsernamePasswordAuthenticationToken{
	public UsernamePasswordAuthToken(Object principal, Object credentials,String userId,String userPassword) {
		super(principal, credentials);
	}
}