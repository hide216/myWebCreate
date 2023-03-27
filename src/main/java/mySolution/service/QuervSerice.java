package mySolution.service;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mySolution.model.User;
import mySolution.model.UserSmallData;

@Service
public class QuervSerice extends AbustractService{

	/* エンティティマネージャー*/
	@Autowired
	EntityManager entityManager;

	/**
	 * Userのログインに必要な情報を取得する
	 * @param id ユーザーのId
	 * @return ユーザーのObject
	 */
	public UserSmallData findUserSmall(String id) {
		UserSmallData result = null;
		Query query = entityManager
			.createNativeQuery("SELECT user_id , user_password FROM t_member where user_id = :id", UserSmallData.class)
			.setParameter("id", id);
		try {
			result =  (UserSmallData) query.getSingleResult();
		} catch(NoResultException e) {
			log.info("this id not found id:" + id);
		}
		return result;
	}

	/**
	 * Userのすべての情報を取得する
	 * @param id ユーザーのId
	 * @return ユーザーのObject
	 */
	public User findUserAllData(String id) {
		User result = null;
		Query query = entityManager
			.createNativeQuery("SELECT user_id ,user_password ,mail_address ,user_role ,date_time FROM t_member where user_id = :id" ,User.class)
			.setParameter("id", id);
		try {
			result =  (User) query.getSingleResult();
		} catch(NoResultException e) {
			log.info("this id not found id:" + id);
		}
		return result;
	}

	/**
	 * Userを新規に追加する
	 * @param id ユーザーのId
	 * @return Int 0 :成功　1:失敗
	 */
	//トランザクションをSpringに移譲
	@Transactional
	public int insertNewUser(Map<String,String> reqMap) {
		if(findUserSmall(reqMap.get("userId")) != null){
			return 1;
		}

		try{

			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append("insert into t_member(user_id,user_password,mail_address,user_role,date_time) values ");
			sbQuery.append("(:user_id ,");
			sbQuery.append(" :user_password ,");
			sbQuery.append(" :mail_address ,");
			sbQuery.append(" :user_role ,");
			sbQuery.append(" now() )");

			Query query = entityManager
				.createNativeQuery(sbQuery.toString())
				.setParameter("user_id", reqMap.get("userId"))
				.setParameter("user_password",reqMap.get("userPassword"))
				.setParameter("mail_address", reqMap.get("mailAddress"))
				.setParameter("user_role", "USER");

			query.executeUpdate();

		} catch(QueryTimeoutException e) {
			log.error(" クエリーの実行がクエリーの設定されたタイムアウト値を超え、そのステートメントだけがロールバックされた");
		} catch(TransactionRequiredException e) {
			log.error("クエリーの実行がクエリーの設定されたタイムアウト値を超え、トランザクションがロールバックされた");
		}

		//entityManager.getTransaction().commit();

		return 0;
	}

}
