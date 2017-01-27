package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import cfs.model.Model;
import cfs.model.TransactionDAO;
import cfs.viewbean.TransactionHistoryView;

public class TransactionListAction extends Action {

    private TransactionDAO transactionDAO;

    public TransactionListAction(Model model) {
        transactionDAO = model.getTransactionDAO();
    }

    @Override
    public String getName() {
        return "transaction-list.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        try {
            TransactionHistoryView[] transactions = transactionDAO.showAll();
            request.setAttribute("transactions", transactions);
            return "transaction-list.jsp";
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
