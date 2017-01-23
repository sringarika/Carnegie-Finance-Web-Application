package cfs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public abstract class Action {
    // Returns the name of the action, used to match the request in the hash table
    public abstract String getName();

    // Returns the name of the jsp used to render the output.
    public abstract String perform(HttpServletRequest request);
    
    // Returns the access control level for this action (e.g. logged in or anonymous).
    public abstract AccessControlLevel getAccessControlLevel();

    //
    // Class methods to manage dispatching to Actions
    //
    private static Map<String, Action> hash = new HashMap<String, Action>();

    public static void add(Action a) {
        synchronized (hash) {
            if (hash.get(a.getName()) != null) {
                throw new AssertionError("Two actions with the same name (" + a.getName() + "): "
                        + a.getClass().getName() + " and " + hash.get(a.getName()).getClass().getName());
            }

            hash.put(a.getName(), a);
        }
    }

    public static String perform(String name, HttpServletRequest request) throws ServletException {
        Action a;
        synchronized (hash) {
            a = hash.get(name);
        }

        if (a == null)
            return null;
        
        switch (a.getAccessControlLevel()) {
            case Everyone:
                break;
            case User:
                if (request.getAttribute("customer") == null && request.getAttribute("employee") == null) {
                    return "login.do";
                }
                break;
            case Customer:
                if (request.getAttribute("customer") == null) {
                    return "login.do";
                }
                break;
            case Employee:
                if (request.getAttribute("employee") == null) {
                    return "login.do";
                }
                break;
            default:
                throw new ServletException(a.getName()
                        + ".getAccessControlLevel() invalid access level: " + a.getAccessControlLevel());
        }
        
        return a.perform(request);
    }
}
