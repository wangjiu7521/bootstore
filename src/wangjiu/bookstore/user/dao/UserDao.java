package wangjiu.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import wangjiu.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * User 持久层
 * @author wangjiu
 *
 */
public class UserDao {

		private QueryRunner qr = new TxQueryRunner();
		
		/**
		 * 按用户名查询
		 * @param username
		 * @return
		 */
		public User findByUsername(String username){
			String sql = "select * from tb_user where username=?";
			
			try {
				return qr.query(sql, new BeanHandler<User>(User.class),username);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		public User findByEmail(String email){
			String sql = "select * from tb_user where email=?";
			try {
				return qr.query(sql, new BeanHandler<User>(User.class),email);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * 安激活码查询
		 * @param code
		 * @return
		 */
		public User findByCode(String code){
			String sql = "select * from tb_user where code=?";
			try {
				return qr.query(sql, new BeanHandler<User>(User.class),code);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		public void updateState(String uid,boolean state){
			String sql = "update tb_user set state=? where uid=?";
			try {
				qr.update(sql, state,uid);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		/**
		 * 插入User
		 * @param user
		 */
		public void add(User user){
			String sql = "insert into tb_user values(?,?,?,?,?,?)";
			Object[] params = {
					user.getUid(),
					user.getUsername(),
					user.getPassword(),
					user.getEmail(),
					user.getCode(),
					user.isState()
			};
			try {
				qr.update(sql, params);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
}
