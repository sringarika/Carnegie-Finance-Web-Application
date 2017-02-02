package cfs.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.databean.Employee;
import cfs.model.Model;


public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Model model;

    @Override
    public void init() throws ServletException {
        model = new Model(getServletConfig());
        model.seed();

        Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new AccountAction(model));
        Action.add(new EmployeeHomeAction(model));
        Action.add(new CreateCustomerAction(model));
        Action.add(new CreateEmployeeAction(model));
        Action.add(new DepositCheckAction(model));
        Action.add(new CreateFundAction(model));
        Action.add(new TransitionDayAction(model));
        Action.add(new TransactionListAction(model));
        Action.add(new CustomerListAction(model));
        Action.add(new ViewCustomerAction(model));
        Action.add(new ResetPasswordAction(model));
        Action.add(new ChangePasswordAction(model));
        Action.add(new BuyFundAction(model));
        Action.add(new SellFundAction(model));
        Action.add(new RequestCheckAction(model));
        Action.add(new TransactionHistoryAction(model));
        Action.add(new ResearchFundAction(model));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nextPage = performTheAction(request);
            sendToNextPage(nextPage, request, response);
        } catch (Exception e) {
            String error = e.getMessage();
            if (error == null || error.isEmpty()) error = "Unknown error!";
            request.setAttribute("error", error);
            sendToNextPage("error.jsp", request, response);
        }
    }

    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     *
     * @param request
     *
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) throws ServletException, RollbackException {
        String servletPath = request.getServletPath();
        String action = getActionName(servletPath);
        request.setAttribute("activeLink", action);

        HttpSession session = request.getSession(true);

        if (session.getAttribute("employeeId") != null) {
            int employeeId = (int) session.getAttribute("employeeId");
            Employee employee = model.getEmployeeDAO().read(employeeId);
            request.setAttribute("employee", employee);
            request.setAttribute("greeting", employee.getFirstname() + " " + employee.getLastname());
        } else if (session.getAttribute("customerId") != null) {
            int customerId = (int) session.getAttribute("customerId");
            Customer customer = model.getCustomerDAO().read(customerId);
            request.setAttribute("customer", customer);
            request.setAttribute("greeting", customer.getFirstname() + " " + customer.getLastname());
        } else {
            return Action.perform("login.do", request);
        }
        return Action.perform(action, request);
    }

    /*
     * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
     * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
     * page (the view) This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
            request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            prepareLinks(request);
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                    + nextPage);
            d.forward(request, response);
            return;
        }

        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }

    private void prepareLinks(HttpServletRequest request) {
        Map<String, String> links = new LinkedHashMap<String, String>();
        if (request.getAttribute("employee") != null) {
            links.put("customer-list.do", "Manage Customers");
            links.put("(dropdown)", "Create Account");
            links.put("create-fund.do", "Create Fund");
            links.put("transition-day.do", "Transition Day");
        } else if (request.getAttribute("customer") != null) {
            links.put("account.do", "Account");
            links.put("buy-fund.do", "Buy");
            links.put("sell-fund.do", "Sell");
            links.put("request-check.do", "Request Check");
            links.put("transaction-history.do", "Transaction History");
            links.put("research-fund.do", "Research Fund");
        } else {
            links.put("login.do", "Login");
        }
        request.setAttribute("links", links);
    }
}
