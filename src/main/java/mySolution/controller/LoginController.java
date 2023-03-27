package mySolution.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mySolution.Constants;
import mySolution.model.MemberSession;
import mySolution.model.RegistForm;
import mySolution.service.GmailService;
import mySolution.service.UserDetailsService;

@Controller
@RequestMapping("/")
public class LoginController {
	/** ロガー */
	Logger log = LoggerFactory.getLogger(LoginController.class);
	/** メッセージ管理 */
	@Autowired
	private MessageSource messageSource;
	/**　認証サービス */
	@Autowired
	UserDetailsService userDetailService;
	/**　ログイン管理セッション */
	@Autowired
	HttpSession session;

	@Autowired
	MemberSession memSession;

	@Autowired
	GmailService gmailService;

	@GetMapping("/login")
	public String getUserPage(Model model) {
		String loginFailFlag;
		//ログイン成功で戻っているかをSessionで確認
		try {
			loginFailFlag = (String)session.getAttribute(Constants.SessionKey.LOGIN_FLG);
		}catch (ClassCastException e) {
			loginFailFlag = "";
		}
		//ログイン失敗
		if(Constants.Common.FLAG_NO.equals(loginFailFlag)){
			model.addAttribute("errMsg",messageSource.getMessage("login.err.msg", new String[] {}, Locale.getDefault()));
			session.removeAttribute(Constants.SessionKey.LOGIN_FLG);
		}
		return "login";
	}

	@GetMapping("/member")
	public String member(Model model) {
		return "home";
	}

	@GetMapping("/callBack")
	public String callBack(Model model) {
		return "redirect:regist";
	}



	@GetMapping("/regist")
	public String regist(Model model,@ModelAttribute(name="registForm")RegistForm form)  {
		return "regist";
	}

	/**
	 * register Authが取れた場合認証済みSessionとする
	 */
	@PostMapping("/regist")
	public String formAuth(Model model,HttpServletRequest request,RedirectAttributes attributes,
		@ModelAttribute(name="registForm")@Validated RegistForm form,BindingResult error) throws Exception {

		if(error.hasErrors()){
			form.clearPsw();
			model.addAttribute("registForm", form);
			model.addAttribute("org.springframework.validation.BindingResult.registForm", error);
			return "regist";
		}
		if(userDetailService.register(request)){
			Map<String,String>mailMap = new HashMap<String,String>();
			String mailBody = Constants.MAIL_MASSEAGE.REGIST_MAIL_BODY;
			mailBody = mailBody.replaceAll("ID", form.getUserId());
			mailBody = mailBody.replaceAll("DOMAIN", "miyake.hidetada.top");

			mailMap.put(Constants.MailKey.FROM_ADDRESS,"miyake.service@gmail.com");
			mailMap.put(Constants.MailKey.SEND_ADDRESS,form.getMailAddress());
			mailMap.put(Constants.MailKey.SUBJECT,Constants.MAIL_MASSEAGE.REGIST_SUBJECT);
			mailMap.put(Constants.MailKey.MESSAGE_BODY,mailBody);

			gmailService.main(mailMap);
		} else {
			form.clearPsw();
			return "regist";
		}


		return "redirect:login";
	}
}