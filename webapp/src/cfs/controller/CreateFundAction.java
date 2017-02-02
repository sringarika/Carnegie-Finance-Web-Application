package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Fund;
import cfs.formbean.CreateFundForm;
import cfs.model.FundDAO;
import cfs.model.Model;

public class CreateFundAction extends Action {
    private FundDAO FundDAO;

    public CreateFundAction(Model model) {
        FundDAO = model.getFundDAO();
    }

    @Override
    public String getName() {
        return "create-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        String fundname = request.getParameter("fund");
        String ticker = request.getParameter("ticker");
        Fund[] fn;
        if (request.getMethod().equals("GET")) {
            try {
                fn = FundDAO.match();
                if (fn == null) {
                    request.setAttribute("message", "Currently we have no funds");
                    return "create-fund.jsp";
                }
                request.setAttribute("fundList", fn);
                return "create-fund.jsp";
            } catch (RollbackException e) {
                e.printStackTrace();
            }
            return "create-fund.jsp";

        } else if (request.getMethod().equals("POST")) {
            try {
                Transaction.begin();
                CreateFundForm form = FormBeanFactory.getInstance(CreateFundForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();

                fn = FundDAO.match();
                request.setAttribute("fundList", fn);
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
                boolean flag = FundDAO.fundName(fundname);
                if (flag) {
                    request.setAttribute("error", "Fund exists!");
                    return "create-fund.jsp";
                }
                boolean tflag = FundDAO.fundTicker(ticker);
                if (tflag) {
                    request.setAttribute("error", "Ticker exists!");
                    return "create-fund.jsp";
                }
                Fund newfund = new Fund();
                newfund.setName(fundname);
                newfund.setTicker(ticker);
                FundDAO.create(newfund);
                Transaction.commit();
                request.setAttribute("message", "Fund created successfully!");
                return "success.jsp";
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "create-fund.jsp";
            } finally {
                if (Transaction.isActive())
                    Transaction.rollback();
            }
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
