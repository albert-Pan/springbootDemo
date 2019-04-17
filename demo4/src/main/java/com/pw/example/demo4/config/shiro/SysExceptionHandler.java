package com.pw.example.demo4.config.shiro;
import com.pw.example.demo4.model.ResponseCode;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/18 0018.
 */
public class SysExceptionHandler implements HandlerExceptionResolver {
    private final static Logger logger = LoggerFactory.getLogger(SysExceptionHandler.class);


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        logger.error("Error: {}",e);
        if (e instanceof UnauthenticatedException) {
            attributes.put("code", ResponseCode.LOGIN_FAIL);
            attributes.put("data", false);
            attributes.put("message", ResponseCode.LOGIN_FAIL.getMessage());
        } else if (e instanceof UnauthorizedException) {
            attributes.put("code", ResponseCode.UNAUTHORIZED);
            attributes.put("data", false);
            attributes.put("message", ResponseCode.UNAUTHORIZED.getMessage());
        } else {
            attributes.put("code", ResponseCode.SERVER_ERROR);
            attributes.put("data", false);
            attributes.put("message",ResponseCode.SERVER_ERROR.getMessage()+e.getMessage());
        }
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }
}

