package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class ResearchFundAction extends Action {

    public ResearchFundAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "research-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        // TODO: Get the list of funds with history.
        return "research-fund.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
