/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package science.raketen.apollosocket;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stephan Knitelius {@literal <stephan@knitelius.com>}
 */
public class Request {
    private String method;
    private String path;
    private String version;
    private Map<String,String> queryParams = new HashMap<>();
    
    public Request(String method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
        String[] split = path.split("\\?");
        if(split.length == 2) {
            String[] s = split[1].split("&");
            for(String queryParam : s) {
                String[] qp = queryParam.split("=");
                queryParams.put(qp[0], qp[1]);
            }
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getQueryParam(String param) {
        return queryParams.get(param);
    }
    
}
