package mySolution.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import mySolution.Constants;
import mySolution.model.MemberSession;
import mySolution.model.UserSmallData;

@Service
public class UserDetailsService extends AbstractUserDetailsAuthenticationProvider  {

	@Autowired
	QuervSerice queryService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	HttpSession session;

	@Autowired
	MemberSession memSession;


	/**
	 * Userの新規登録
	 * @param request POSTされたユーザー情報
	 * @return 成否
	 */
	public boolean register(HttpServletRequest request){

		boolean result = false;
		HashMap<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("userId",request.getParameter("userId"));
		reqMap.put("mailAddress",request.getParameter("mailAddress"));
		reqMap.put("userPassword",passwordEncoder.encode(request.getParameter("userPassword")));
		if(queryService.insertNewUser(reqMap) == 0) {
			result = true;
		}
		return result;
	}


	@Override
	protected final  UserDetails retrieveUser(String id,UsernamePasswordAuthenticationToken token) throws UsernameNotFoundException {
		UserSmallData userAuth =  queryService.findUserSmall(id);
		//認証に成功した場合、USER権限を付与
		if(userAuth != null && passwordEncoder.matches((CharSequence) token.getCredentials(), userAuth.getUserPassword())) {
			memSession.setMemId(id);
			UserDetails user = User.withUsername(id)
			.password(userAuth.getUserPassword())
			.authorities("ROLE_USER").build();
			return user;
		}
		//ログイン失敗フラグをSessionに記載
		session.setAttribute(Constants.SessionKey.LOGIN_FLG,Constants.Common.FLAG_NO);
		throw new UsernameNotFoundException("User Not Found");
	}


	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}
}