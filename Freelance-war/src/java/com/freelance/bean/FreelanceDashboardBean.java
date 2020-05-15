/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.bean;

import com.freelance.ejb.session.FreelanceSession;
import com.freelance.ejb.vo.FreelanceDashboardData;
import com.freelance.ejb.vo.FreelanceUserDetails;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author SHAGAY
 */
@ManagedBean(name = "freelancedashboard")
@SessionScoped
public class FreelanceDashboardBean implements Serializable {

    @EJB
    private FreelanceSession freelanceSession;

    private List<FreelanceDashboardData> dashBoardData;

    private List<FreelanceDashboardData> filterefDashboardData;

    private String keyword;

    /**
     * Creates a new instance of FreelanceDashboardBean
     */
    public FreelanceDashboardBean() {

    }

    @PostConstruct
    public void init() {
        keyword = null;
        getFreelanceDashboardData();
    }

    public void getFreelanceDashboardData() {
        try {
            setDashBoardData(freelanceSession.retrieveDashboardData());
        } catch (Exception e) {
            addErrorMessage("Error While processing your request");

        }

    }

    public void retrievelanceDashboardDataByKeyword() {
        try {
            System.out.println("keyword:"+keyword);
            if (null!=keyword  && !keyword.isEmpty()) {
                setDashBoardData(freelanceSession.retrieveDashboardDataByKeyword(keyword));
                
            }else{
                setDashBoardData(freelanceSession.retrieveDashboardData());
            }
            keyword=null;
        } catch (Exception e) {
            addErrorMessage("Error While processing your request");

        }

    }

    public boolean filterData(Object value, Object filter, Locale locale) {

        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        FreelanceDashboardData dasshBoardData = (FreelanceDashboardData) value;
        return dasshBoardData.getTitle().toLowerCase().contains(filterText)
                || dasshBoardData.getTowards().toLowerCase().contains(filterText);

    }
    
    public void updateAction(String action,int id){
        try{
            System.out.println("Status :: " + action + " ID :: "+ id);
            freelanceSession.updateAction(action, id);
            addMessage("Job " + action);
        }
        catch (Exception e) {
            addErrorMessage("Error While processing your request");

        }

    }
    

    /**
     * @return the dashBoardData
     */
    public List<FreelanceDashboardData> getDashBoardData() {
        return dashBoardData;
    }

    /**
     * @param dashBoardData the dashBoardData to set
     */
    public void setDashBoardData(List<FreelanceDashboardData> dashBoardData) {
        this.dashBoardData = dashBoardData;
    }

    /**
     * @return the filterefDashboardData
     */
    public List<FreelanceDashboardData> getFilterefDashboardData() {
        return filterefDashboardData;
    }

    /**
     * @param filterefDashboardData the filterefDashboardData to set
     */
    public void setFilterefDashboardData(List<FreelanceDashboardData> filterefDashboardData) {
        this.filterefDashboardData = filterefDashboardData;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
