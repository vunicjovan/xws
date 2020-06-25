package com.uns.ftn.gateway.auth;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class AuthenticationFilter extends ZuulFilter {

    @Autowired
    private AuthenticationClient authClient;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    private void setFailedRequest(String body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            List<String> authorities = authClient.verify(token.substring(7));

            String username = authorities.remove(authorities.size() - 1);

            StringBuilder permissions = new StringBuilder();

            for (String authority : authorities) {
                permissions.append(authority);
                permissions.append("|");
            }

            ctx.addZuulRequestHeader("username", username);
            ctx.addZuulRequestHeader("permissions", permissions.toString());

        } catch (FeignException.NotFound e) {
            setFailedRequest("Consumer does not exist!", 403);
        } catch (FeignException.Unauthorized e) {
            setFailedRequest("Token has expired!", 401);
        }

        return null;
    }

}
