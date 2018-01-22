package wangjiu.bookstore.user.web.servlet;



import wangjiu.bookstore.user.service.UserService;
import cn.itcast.servlet.BaseServlet;

/**
 * User 表述层
 * @author wangjiu
 *
 */

public class UserServlet extends BaseServlet {
	
	private UserService userService = new UserService();
}
