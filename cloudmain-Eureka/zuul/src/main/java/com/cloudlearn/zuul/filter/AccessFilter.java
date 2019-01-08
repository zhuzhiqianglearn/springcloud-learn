package com.cloudlearn.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessFilter extends ZuulFilter {
    @Override
    //过滤器类型
    public String filterType() {
        return "pre";
    }

    @Override
    //判断该过滤器是否要执行
    public boolean
     shouldFilter() {
        return true;
    }

    @Override
    //过滤器的执行顺序
    public int filterOrder() {
        return 0;
    }

    @Override
    //过滤器的具体逻辑
    public Object run() {
        RequestContext ctx=RequestContext.getCurrentContext();
        int a=1/0;
//        try {
//            int a=1/0;
////            doSomeThing();
//            HttpServletRequest request=ctx.getRequest();
//            String name = request.getParameter("name");
//            if (name==null||name.equals("")) {
//                ctx.setSendZuulResponse(false);
//                ctx.setResponseStatusCode(401);
//                return  null;
//            }
//        }catch (Exception e){
//            ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            ctx.set("error.exception",e);
//        }
        return null;
    }

    public void doSomeThing(){
        throw new RuntimeException("some erros.............");
    }
}
