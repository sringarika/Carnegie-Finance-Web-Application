package cfs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cfs.databean.Customer;
import cfs.model.Model;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage, request, response);
    }

    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * 
     * @param request
     * 
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        String action = getActionName(servletPath);

        HttpSession session = request.getSession(true);
        
        Integer employeeId = (Integer) session.getAttribute("employeeId");
        if (employeeId != null) {
            // TODO: Get employee using DAO.
            request.setAttribute("employee", new Object());
            request.setAttribute("greeting", "Alice Admin");
        }
        
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId != null) {
            // TODO: Get customer using DAO.
            Customer customer = new Customer(23, "carl", "Carl", "Customer");
            request.setAttribute("customer", customer);
            request.setAttribute("greeting", customer.getFirstname() + " " + customer.getLastname());
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
}