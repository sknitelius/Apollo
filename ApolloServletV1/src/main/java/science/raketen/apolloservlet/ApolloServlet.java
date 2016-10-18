/*
 * Copyright 2016 Stephan Knitelius {@literal <stephan@knitelius.com>}.
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
package science.raketen.apolloservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stephan Knitelius {@literal <stephan@knitelius.com>}
 */
public class ApolloServlet extends HttpServlet {

    public static final AtomicInteger SESSION_ID_GEN = new AtomicInteger();
    public static final Map<String, ApolloSessionState> SESSIONS = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionid = handleSessionCookie(req, resp); 

        ApolloSessionState apolloSession = SESSIONS.get(sessionid);
        if(apolloSession == null) {
            apolloSession = new ApolloSessionState();
            SESSIONS.put(sessionid, apolloSession);
        }
        
        String queryString = req.getQueryString();
        
        resp.setStatus(200);
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.print("session call count: " + apolloSession.getAndIncrementNumberOfCalls());
        writer.flush();
    }

    private String handleSessionCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionid")) {
                    return cookie.getValue();
                }
            }
        }
        Cookie cookie = new Cookie("sessionid", Integer.toString(SESSION_ID_GEN.getAndIncrement()));
        response.addCookie(cookie);
        return cookie.getValue();
    }

}
