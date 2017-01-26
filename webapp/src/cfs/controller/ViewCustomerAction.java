package cfs.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import cfs.model.CustomerDAO;
import cfs.model.CustomerPositionDAO;
import cfs.model.Model;
import cfs.viewbean.PositionView;

public class ViewCustomerAction extends Action {

    private CustomerPositionDAO customerPositionDAO;
    private CustomerDAO customerDAO;

    public ViewCustomerAction(Model model) {
        customerPositionDAO = model.getCustomerPositionDAO();
        customerDAO = model.getCustomerDAO();
    }

    @Override
    public String getName() {
        return "view-customer.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        int customerId;
        String customerIdStr = request.getParameter("customerId");
        try {
            customerId = Integer.parseInt(customerIdStr);
        } catch (Exception e) {
            request.setAttribute("error", "Invalid customerId!");
            return "error.jsp";
        }
        return showCustomer(customerId, request);
    }

    protected String showCustomer(int customerId, HttpServletRequest request) {
        try {
            String lastTradingDate = "2017-01-15"; // TODO
            LocalDate date = LocalDate.parse(lastTradingDate, DateTimeFormatter.ISO_DATE);
            request.setAttribute("lastTradingDateDisp",
                    date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
            request.setAttribute("showCustomer", customerDAO.read(customerId));
            PositionView[] positions = customerPositionDAO.getPositionViews(customerId);
            request.setAttribute("positions", positions);
            return "view-customer.jsp";
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
