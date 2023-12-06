package unsw.comp9900.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import unsw.comp9900.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        response.setCharacterEncoding("utf-8");
//
//        String url = request.getRequestURI();
//        System.out.println(url);
//        if(!url.endsWith("tutor/tutorLogin") || !url.endsWith("student/studentLogin")){
//            //从http请求头中取出token（后面前端会将token内容存入Headers中）
//            String token = request.getHeader("Authorization");
////            System.out.println(token);
//            //执行认证
//            return TokenUtils.verify(token);
//        }


//        response.setCharacterEncoding("utf-8");
//        String token=request.getParameter("sessionValue");
//        //Cookie cookie=getCookieByName(request,"_COOKIE_NAME");
//        //如果已经登录，不拦截
//        if (null != token) {
//            //验证token是否正确
//            boolean result = TokenUtils.verify(token);
//            System.out.println("是否通过拦截器:"+result);
//            if (!result) {
//                return false;
//            }
//            return true;
//        }
//        //如果没有登录，则跳转到登录界面
//        else {
//            //重定向 第一种 调用控制器 方法
//            response.sendRedirect(request.getContextPath() + "/login");
//            //重定向 第二种 重定向方法
//            //            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
//            //            System.out.println(request.getContextPath());
//            return false;
//            /**
//             * 以下是为了登录成功后返回到刚刚的操作，不跳到主界面
//             * 实现：通过将请求URL保存到session的beforePath中，然后在登录时判断beforePath是否为空
//             */
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}