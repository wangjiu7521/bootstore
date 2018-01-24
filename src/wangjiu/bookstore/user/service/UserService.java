package wangjiu.bookstore.user.service;

import wangjiu.bookstore.user.dao.UserDao;
import wangjiu.bookstore.user.domain.User;


/**
 * User业务层
 * @author wangjiu
 *
 */
public class UserService {
	private UserDao userDao = new UserDao();
	
	/**
	 * 注册功能
	 * @param form
	 * @throws UserException 
	 */
	public void regist(User form) throws UserException{
		
		//校验用户名
		User user = userDao.findByUsername(form.getUsername());
		if(user !=null) throw new UserException("您注册的用户名已存在！");
		//校验email
		user = userDao.findByEmail(form.getEmail());
		if(user !=null) throw new UserException("您注册的邮箱已存在！");
		
		//插入用户到数据库
		userDao.add(form);
	}
	
	
	/**
	 * 激活功能
	 * @param code
	 * @throws UserException 
	 */
	public void active(String code) throws UserException{
		User user = userDao.findByCode(code);
		if(user==null) throw new UserException("激活码无效！");
		
		if(user.isState()) throw new UserException("已经激活，不用再次激活！");
		/*
		 * 修改用户的状态
		 */
		userDao.updateState(user.getUid(), true);
	}
	
	public User login(User form) throws UserException{
		
		User user = userDao.findByUsername(form.getUsername());
		if(user==null) throw new UserException("用户名不存在！");
		if(!user.getPassword().equals(form.getPassword())) throw new UserException("密码错误！");
		if(!user.isState()) throw new UserException("尚未激活！");
		
		return user;
	}
}
