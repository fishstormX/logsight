package cn.fishmaple.logsight.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;

@Component
@WebFilter(filterName="i18nFilter",urlPatterns="/*")
public class I18nFilter implements Filter {
    @Autowired
    LocaleResolver localeResolver;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            Cookie [] cookies= httpServletRequest.getCookies();
            if(null!=cookies){
                for(Cookie cookie:cookies){
                    if(cookie.getName().equals("i18n")){
                        if(cookie.getValue().equals("en_US")){
                            localeResolver.setLocale((HttpServletRequest)request,(HttpServletResponse) response,Locale.ENGLISH);
                        }else{
                            localeResolver.setLocale((HttpServletRequest)request,(HttpServletResponse) response,Locale.CHINESE);
                        }
                    }
                }
            }
            filterChain.doFilter(request, response);
    }
}