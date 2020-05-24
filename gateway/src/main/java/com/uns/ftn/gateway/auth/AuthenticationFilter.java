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

        if (token == null) {
            return null;
        };

        try {
            List<String> authorities = authClient.verify(token);

            String username = authorities.remove(authorities.size() - 1);

            StringBuilder permissions = new StringBuilder();

            for (int i = 0; i < authorities.size(); i++) {
                permissions.append(authorities.get(i));
                permissions.append("|");
            }

            /*
            for (int i = 0; i < authorities.size(); i++) {
                if (i == authorities.size() - 1) {
                    permissions.append(authorities.get(i));
                } else {
                    permissions.append(authorities.get(i));
                    permissions.append("|");
                }
            }*/

            ctx.addZuulRequestHeader("username", username);
            ctx.addZuulRequestHeader("permissions", permissions.toString());

        } catch (FeignException.NotFound e) {
            setFailedRequest("Consumer does not exist!", 403);
        }

        return null;
    }

}
