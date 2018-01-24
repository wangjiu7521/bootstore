package wangjiu.bookstore.user.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wangjiu.util.verifycode.VerifyCode;


public class VerifyCodeServlet extends HttpServlet {
	/**
	 * get请求，在打开注册页面时就请求Servlet，处理图片，不用设置编码，
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request.setCharacterEncoding("utf-8");//处理post请求编码
		//response.setContentType("text/html;charset=utf-8");
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();//得到图片
		String text = vc.getText();//得到文本
		request.getSession().setAttribute("verifycodetext", text);//添加session属性
		VerifyCode.output(image, response.getOutputStream());//将图片输出到页面
	}

}
