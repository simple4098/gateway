package com.yql.biz.web;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.util.HTTPRequestUtils;
import com.yql.biz.exception.JatInvalidException;
import com.yql.biz.model.Jat;
import com.yql.biz.support.UserCodeResolver;
import com.yql.biz.support.validation.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author wangxiaohong
 */
@Component("accessFilter")
public class AccessFilter extends ZuulFilter {

    @Value("${error.path:/error}")
    private String errorPath;

    @Resource
    private UserCodeResolver codeResolver;

    @Resource
    private Validator<Jat> jatValidator;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            String jat = request.getHeader("X-JAT");
            Jat jatObj = parseJat(jat);
            jatValidator.isValid(jatObj);
            String userCode = codeResolver.resolve(jatObj);
            appendUserCode(ctx, userCode);
        } catch (Exception e) {
            setErrorInfo(request, e);
            forward(ctx, request);
        }
        return null;
    }

    private void forward(RequestContext ctx, HttpServletRequest request) {
        RequestDispatcher dispatcher = request.getRequestDispatcher(this.errorPath);
        if (dispatcher != null) {
            ctx.set("sendErrorFilter.ran", true);
            if (!ctx.getResponse().isCommitted()) {
                try {
                    dispatcher.forward(request, ctx.getResponse());
                } catch (ServletException | IOException e1) {
                    //ignore
                }
            }
        }
    }

    private void setErrorInfo(HttpServletRequest request, Exception e) {
        request.setAttribute("javax.servlet.error.status_code", 403);
        request.setAttribute("javax.servlet.error.exception", e);
        request.setAttribute("javax.servlet.error.message", e.getMessage());
    }

    private void appendUserCode(RequestContext ctx, String userCode) {
        MultiValueMap<String, String> params = buildZuulRequestQueryParams();
        params.put("userCode", Collections.singletonList(userCode));
        ctx.setRequestQueryParams(params);
    }

    private Jat parseJat(String jat) {
        if (!StringUtils.hasText(jat)) {
            throw new JatInvalidException();
        }
        try {
            byte[] bytes = Base64Utils.decodeFromUrlSafeString(jat);
            return JSON.parseObject(bytes, Jat.class);
        } catch (Exception e) {
            throw new JatInvalidException();
        }
    }

    private MultiValueMap<String, String> buildZuulRequestQueryParams() {
        Map<String, List<String>> map = HTTPRequestUtils.getInstance().getQueryParams();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (map == null) {
            return params;
        }
        for (String key : map.keySet()) {
            for (String value : map.get(key)) {
                params.add(key, value);
            }
        }
        return params;
    }
}
