package wangjiu.bookstore.user.web.servlet;



import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wangjiu.bookstore.user.domain.User;
import wangjiu.bookstore.user.service.UserException;
import wangjiu.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;


/**
 * User 表述层
 * @author wangjiu
 *
 */

public class UserServlet extends BaseServlet {
	
	private UserService userService = new UserService();
	
	/**
	 * 注册功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("正在请求注册功能");
		/* 
		 * 1.封装表单数据到form对象中
		 * 2.补全 uid code 
		 * 3.输入校验
		 *  ＞　保存错误信息，form到request域，转发到regist.jsp
		 * 4.调用service 方法完成注册
		 *  > 保存错误信息，form到request域，转发到regist.jsp
		 * 5.发邮件
		 * 6.保存成功信息转发到msg.jsp
		 * 
		 */
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		//补全
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid() +CommonUtils.uuid());
		/*
		 * 输入校验
		 */
		Map<String,String> errors = new HashMap<String,String>();
		String username = form.getUsername();
		if(username == null || username.trim().isEmpty()){
			errors.put("username", "用户名不能为空！");
		}else if(username.length() <3 || username.length() >10){
			errors.put("username", "用户名长度必须在2到10之间！");
		}
		
		String email = form.getEmail();
		if(email == null || email.trim().isEmpty()){
			errors.put("email", "邮箱不能为空！");
		}else if(!email.matches("\\w+@\\w+\\.\\w+") ){
			errors.put("email", "邮箱格式错误！");
		}
		
		if(errors.size()>0){
			request.setAttribute("errors",errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
			
		}
		try {
			userService.regist(form);
			/*
			 * 发邮件
			 * 准备配置文件
			 * 获取配置文件内容
			 */
			Properties props = new Properties();
			props.load(this.getClass().getClassLoader()
					.getResourceAsStream("email_template.properties"));
			String host = props.getProperty("host"); //获取服务器主机
			String uname = props.getProperty("uname");//获取用户名
			String pwd = props.getProperty("pwd"); //密码
			String from = props.getProperty("from");  //发件人
			String to = form.getEmail(); //收件人
			String subject = props.getProperty("subject"); //获取主题
			String content = props.getProperty("content");  //获取内容
			content = MessageFormat.format(content, form.getCode());//替换占位符
			//content=<a href="http://localhost/bookstore/UserServlet?method=active&code={0}"></a>
			
			Session session = MailUtils.createSession(host, uname, pwd);  //得到session
			Mail mail = new Mail(from, to, subject, content); //创建邮件对象
			try {
				MailUtils.send(session, mail);  //发送邮件
			} catch (MessagingException e) {
				
			}

		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
		request.setAttribute("msg", "恭喜，注册成功！请马上到邮箱激活！");
		return "f:/jsps/msg.jsp";
	}
	
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String code = request.getParameter("code");
			try {
				userService.active(code);
			} catch (UserException e) {
				request.setAttribute("msg", e.getMessage());
				return "f:/jsps/msg.jsp";
			}
			request.setAttribute("msg", "激活成功！前往登录！");
			return "f:/jsps/msg.jsp";
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			User form = CommonUtils.toBean(request.getParameterMap(), User.class);
			try {
				User user = userService.login(form);
				request.getSession().setAttribute("session_user", user);
				return "r:/index.jsp";
			} catch (UserException e) {
				request.setAttribute("msg", e.getMessage());
				request.setAttribute("form", form);
				return "f:/jsps/user/login.jsp";
			}
			
	}
	/**
	 * 注销登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestType = request.getHeader("X-Requested-With");
		HttpSession session = request.getSession(false);//防止创建Session
		if(session == null){
            return reDirect(request, response, "/index.jsp"); 
        }  
		session.removeAttribute("session_user");
        return reDirect(request,response,"/index.jsp");
	}
	
	/**
	 * 处理ajax重定向的方法
	 * @param request
	 * @param response
	 * @param path
	 * @return
	 * @throws IOException
	 */
    public String reDirect(HttpServletRequest request, HttpServletResponse response,String path) throws IOException{
        //获取当前请求的路径
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();
        //如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            //告诉ajax我是重定向
            response.setHeader("redirect", "redirect");
            //告诉ajax我重定向的路径
            response.setHeader("contentpath", basePath+path);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            System.out.println("redirect:"+basePath+path);
            return null;
        }else{
        	return "r:" + path;
        }
    }
}
