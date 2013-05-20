/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.gadgets.web.server.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author eric.wittmann@redhat.com
 */
public class JSONPFilter implements Filter {

    /**
     * Constructor.
     */
    public JSONPFilter() {
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (isJSONPRequest(httpRequest)) {
            ServletOutputStream out = response.getOutputStream();
            String varName = getVariableNameParameter(httpRequest);
            if (varName == null) {
                varName = "jsonpobj";
            }
            out.print("var " + varName + " = (");
            chain.doFilter(request, response);
            out.println(");");
            response.setContentType("text/javascript");
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * @param httpRequest
     * @return the name of the JSONP variable to use
     */
    private String getVariableNameParameter(HttpServletRequest httpRequest) {
        return httpRequest.getParameter("varName");
    }

    /**
     * @param httpRequest
     * @return true if asking for JSONP
     */
    private boolean isJSONPRequest(HttpServletRequest httpRequest) {
        return "true".equals(httpRequest.getParameter("jsonp"));
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
