package com.zl.fund.Interceptor;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截的管理器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());     //拦截的对象会进入这个类中进行判断
        registration.addPathPatterns("/**");                    //所有路径都被拦截
        registration.excludePathPatterns("/","/scripts/**","/webjars/**","/error",
        		"/images/**","/asserts/**","/bootstrap-3.3.7-dist/**","/question",
        		"/toDetails/**","/fundMarket","/index","/user/**","/pagingByAjax");       //添加不拦截路径
        registration.order(1);
        //拦截访问设置交易密码的请求
        InterceptorRegistration registrationTransCode = registry.addInterceptor(new SetTransCodeInterceptor());
        registrationTransCode.addPathPatterns("/security/toSetTransCode.do");
        registrationTransCode.order(2);
    }
}