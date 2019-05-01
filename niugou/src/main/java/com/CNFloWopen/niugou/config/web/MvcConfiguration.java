package com.CNFloWopen.niugou.config.web;

//import com.CNFloWopen.niugou.interceptor.ShopLoginInterceptor;
//import com.CNFloWopen.niugou.interceptor.ShopPermissionInterceptor;
import com.CNFloWopen.niugou.interceptor.FrontendLoginInterceptor;
import com.CNFloWopen.niugou.interceptor.ShopLoginInterceptor;
import com.CNFloWopen.niugou.interceptor.ShopPermissionInterceptor;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 开启mvc，自动注入spring 容器 ，WebMvcConfigurerAdapter配置视图解析器
 * 实现ApplicationContextAware接口，方便获得ApplicationContext中的所有的bean
 **/
@Configuration
//等价于        <mvc:annotation-driven />
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {
//    spring容器
    private ApplicationContext applicationContext;
//    获取applicationContext
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
//        这里不能用classpath，由于用的是自定义的文件夹不可能把图片都放在工程下面，所以要用file
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/Users/argeszy/resources/image/upload/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
    }

    /**
     * 定义默认请求处理器,<mvc:default-servlet-handler />
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    /**
     * 创建视图处理器（viewResolver）
     */
//    @Bean(name = "viewResolver")
//    public ViewResolver createViewResolver()
//    {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
////        设置spring容器
//        viewResolver.setApplicationContext(this.applicationContext);
////        取消缓存
//        viewResolver.setCache(false);
////        设置解析前缀
//        viewResolver.setPrefix("/WEB-INF/html/");
////        设置解析后缀
//        viewResolver.setSuffix(".html");
//        return viewResolver;
//    }
    /**
     * 文件上传解析器
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
//        20M
        multipartResolver.setMaxUploadSize(20971520);
//        最大内存
        multipartResolver.setMaxInMemorySize(20971520);
        return multipartResolver;
    }


    /**
     * 由于web.xml在不springboot中不生效了，所以要以bean的形式配置kaptcha验证码
     */
    @Value("${kaptcha.border}")
    private String border;
    @Value("${kaptcha.textproducer.font.color}")
    private String fcolor;
    @Value("${kaptcha.image.width}")
    private String width;
    @Value("${kaptcha.textproducer.char.string}")
    private String cString;
    @Value("${kaptcha.image.height}")
    private String height;
    @Value("${kaptcha.textproducer.font.size}")
    private String fsize;
    @Value("${kaptcha.noise.color}")
    private String nColor;
    @Value("${kaptcha.textproducer.char.length}")
    private String clength;
    @Value("${kaptcha.textproducer.font.names}")
    private String fnames;
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
//      加载KaptchaServlet
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new KaptchaServlet(),"/Kaptcha");
        registrationBean.addInitParameter("kaptcha.border",border);//无边框
        registrationBean.addInitParameter("kaptcha.textproducer.font.color",fcolor);//字体颜色
        registrationBean.addInitParameter("kaptcha.image.width",width);//宽度
        registrationBean.addInitParameter("kaptcha.textproducer.char.string",cString);//用什么字
        registrationBean.addInitParameter("kaptcha.image.height",height);//高度
        registrationBean.addInitParameter("kaptcha.textproducer.font.size",fsize);//字体大小
        registrationBean.addInitParameter("kaptcha.noise.color",nColor);//
        registrationBean.addInitParameter("kaptcha.textproducer.char.length",clength);//验证码总体长度
        registrationBean.addInitParameter("kaptcha.textproducer.font.names",fnames);//总的名字
        return registrationBean;
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String interceptPath = "/shopadmin/**";
        String interceptFrontend = "/frontend/**";
//        注册拦截器
        InterceptorRegistration loginIR =registry.addInterceptor(new ShopLoginInterceptor());
//        配置拦截的路径
        loginIR.addPathPatterns(interceptPath);
//        由于扫描的时候不需要登录系统做操作
        loginIR.excludePathPatterns("/shopadmin/addshopauthmap");


//        注册其他拦截器
        InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
//        配置拦截的路径
        permissionIR.addPathPatterns(interceptPath);
//        配置不拦截的路径
//        shoplist page
        permissionIR.excludePathPatterns("/shopadmin/shoplist");
        permissionIR.excludePathPatterns("/shopadmin/getshoplist");
//        shopregister page
        permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
        permissionIR.excludePathPatterns("/shopadmin/registershop");
        permissionIR.excludePathPatterns("/shopadmin/shopoperation");
//        shopmanage page
        permissionIR.excludePathPatterns("/shopadmin/shopmanagement");
        permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");

        permissionIR.excludePathPatterns("/shopadmin/addshopauthmap");

        // 注册用户拦截器
        InterceptorRegistration loginIR2 =registry.addInterceptor(new FrontendLoginInterceptor());
//        配置拦截的路径
        loginIR2.addPathPatterns(interceptFrontend);
        loginIR2.excludePathPatterns("/frontend/index");
        loginIR2.excludePathPatterns("/frontend/shoplist");
        loginIR2.excludePathPatterns("/frontend/shopdetail");
        loginIR2.excludePathPatterns("/frontend/productdetail");
        loginIR2.excludePathPatterns("/frontend/listmainpageinfo");
        loginIR2.excludePathPatterns("/frontend/listproductdetailpageinfo");
        loginIR2.excludePathPatterns("/frontend/listshopspageinfo");
        loginIR2.excludePathPatterns("/frontend/listshops");
        loginIR2.excludePathPatterns("/frontend/listproductsbyshop");
        loginIR2.excludePathPatterns("/shopadmin/addshopauthmap");
    }
}
