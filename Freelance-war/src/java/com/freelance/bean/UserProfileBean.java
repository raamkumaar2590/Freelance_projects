/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelance.bean;

import com.freelance.ejb.session.FreelanceSession;
import com.freelance.ejb.vo.FreelanceUserDetails;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author User
 */
@Named(value = "userProfile")
@Dependent
public class UserProfileBean {
    
    @EJB
    private FreelanceSession freelanceSession;
    
    private FreelanceUserDetails userDetails;
    
    private boolean showUserDetailEditForm;
    
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  
    

    /**
     * Creates a new instance of UserProfileBean
     */
    public UserProfileBean() {
    }
    
    @PostConstruct
    public void init(){
        setShowUserDetailEditForm(Boolean.FALSE);
        getFreelanceUserData(1);
    }
    
    public void getFreelanceUserData(int id){
        try {
            
        setUserDetails(freelanceSession.retrieveUserData(id));
        
        } catch (Exception e) {
            addErrorMessage("Error While processing your request");
            
        }
    }
    
    public String editUserDetail(){
        sessionMap.put("editUser",userDetails);
        setShowUserDetailEditForm(Boolean.TRUE);
        return "/editUserProfile.xhtml?faces-redirect=true"; 
    }
    
    public void updateUserDetails(FreelanceUserDetails editUser){
    
        try {
            
            System.out.println("dataaaaaaaaaaaaaaaaaaaa message" + editUser.getMessage());
            freelanceSession.updateUserData(editUser);
//            setShowUserDetailEditForm(Boolean.FALSE);
            addMessage("record updated successfully....");
        } catch (Exception e) {
            addErrorMessage("Error While processing your request");
            
        }
        
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
     * @return the showUserDetailEditForm
     */
    public boolean isShowUserDetailEditForm() {
        return showUserDetailEditForm;
    }

    /**
     * @param showUserDetailEditForm the showUserDetailEditForm to set
     */
    public void setShowUserDetailEditForm(boolean showUserDetailEditForm) {
        this.showUserDetailEditForm = showUserDetailEditForm;
    }
    
   
    /**
     * @return the userDetails
     */
    public FreelanceUserDetails getUserDetails() {
        return userDetails;
    }

    /**
     * @param userDetails the userDetails to set
     */
    public void setUserDetails(FreelanceUserDetails userDetails) {
        this.userDetails = userDetails;
    }

    
}
