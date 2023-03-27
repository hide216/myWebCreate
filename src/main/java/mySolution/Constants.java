package mySolution;

public final class Constants {

	public static class Common{
		public static final String FLAG_NO = "0";
		public static final String FLAG_YES = "1";
	}
	public static class SessionKey{
		public static final String LOGIN_FLG = "LOGIN_FLG";
	}
	public static class MailKey{
		public static final String SEND_ADDRESS = "SEND_ADDRESS";
		public static final String FROM_ADDRESS = "FROM_ADDRESS";
		public static final String SUBJECT = "SUBJECT";
		public static final String MESSAGE_BODY = "MESSAGE_BODY";
	}




	public static class MAIL_MASSEAGE{
		public static final String REGIST_SUBJECT = "[TESTMAIL] ご登録ありがとうございます";
		public static final String REGIST_MAIL_BODY = "この度はアカウントのご登録ありがとうございます。\r\n"
				+ "\r\n"
				+ "アカウントをご案内します。\r\n"
				+ "\r\n"
				+ "------------------------------------------------------------\r\n"
				+ "アカウント : ID\r\n"
				+ "------------------------------------------------------------\r\n"
				+ "\r\n"
				+ "登録時にご入力されたメールアドレスとパスワードを使い、\r\n"
				+ "コントロールパネルにログインしてサービスをご利用ください。\r\n"
				+ "\r\n"
				+ "▼コントロールパネルへのログインはこちら　\r\n"
				+ "http://DOMAIN/login/\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "パスワードを忘れた場合、下記のURLからパスワードの再発行手続きを行ってください。\r\n"
				+ "\r\n"
				+ "▼パスワードの再設定はこちら\r\n"
				+ "http://DOMAIN/forgot/\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "今後ともM.SERVICEをよろしくお願いいたします。\r\n"
				+ "\r\n"
				+ "【ご登録にお心当たりがないお客様へ】\r\n"
				+ "大変お手数ではございますが、本文は削除せず本メールへの返信にて\r\n"
				+ "ご連絡くださいますようお願いいたします。\r\n"
				+ "";
	}
}