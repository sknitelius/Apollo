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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stephan Knitelius {@literal <stephan@knitelius.com>}
 */
public class ApolloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        ApolloSession apolloSession = getApolloSession(req);
        
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.print("session call count: " + apolloSession.getAndIncrementNumberOfCalls());
        writer.flush();
    }

    private ApolloSession getApolloSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ApolloSession apolloSession = (ApolloSession)session.getAttribute("apollo");
        
        if(apolloSession == null) {
            apolloSession = new ApolloSession();
            session.setAttribute("apollo", apolloSession);
        }
        return apolloSession;
    }

}
