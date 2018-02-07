
package mvc.controller;

import mvc.models.Person;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mvc.models.Person;
import mvc.views.ContactDialog;
import mvc.views.AddressBookMainGUI;
import mvc.views.DetailViewPanel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.dao.AddressBookDAOImplementation;
import mvc.views.NameListPanel;

/**
 *
 * @author Bharathy Annamalai - KGiSL
 */
public class AddressBookController {
    private AddressBookMainGUI view;
    private ActionListener actionListener;
    private ActionListener choiceListener;
    private ContactDialog dialog;
    private AddressBookDAOImplementation daoimplement;
    private NameListPanel nlp;
    private DetailViewPanel detailPanel;
    private DetailViewPanel contactDetailsPanel;
    
    public AddressBookController(){
        daoimplement = new AddressBookDAOImplementation();
        view =new AddressBookMainGUI("View");  
    }
     
    
     
    
     
    public void control(){
        loadPersons();
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()== view.getAdd())
                {
                       
                   view.getMainGUIFrame().disable();
                        openAdd();
                       view.getMainGUIFrame().enable();
                     
                }
                else if(e.getSource()== view.getEdit())
                    openEdit();
                else if(e.getSource() == view.getDelete())
                    openDelete();
                else if(e.getSource() == view.getSearch())
                    openSearch();
            }

           
        };
        view.getAdd().addActionListener(actionListener);
        view.getDelete().addActionListener(actionListener);
        view.getEdit().addActionListener(actionListener);
        view.getSearch().addActionListener(actionListener);
      
    }
    
                
    private void openAdd(){   
       view.getMainGUIFrame().setVisible(false);
         dialog =  new ContactDialog("New Entries");    
         dialog.getPanel().setLocation(500,500);
        choiceListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(e.getSource()== dialog.getSubmitButton())
            {
            contactDetailsPanel=dialog.getPanel();
            //callDAO("Add");
            Boolean flag = false, mobileValid = false, pinValidate = false;
            String name = contactDetailsPanel.getNameField().getText();
            String mobile = contactDetailsPanel.getMobileField().getText();
            String email = contactDetailsPanel.geteMailField().getText(); 
            String address = contactDetailsPanel.geteAddressField().getText(); 
            String pin = contactDetailsPanel.getPinField().getText(); 
            flag = validate();
            mobileValid = mblvalidate();
            pinValidate = pinvalidate();
            if(flag && mobileValid && pinValidate)
            {    
             Person person = new Person();
             person.setData(name,mobile,email,address,pin);
             daoimplement.addPerson(person);
             dialog.getFrame().dispose();
             view.getMainGUIFrame().setVisible(true);
             loadPersons();
            }
                   
                }
                else if(e.getSource()== dialog.getcancelButton())
                {
                    dialog.getFrame().dispose();
                    view.getMainGUIFrame().setVisible(true);
                }   
            }
            
        };
         dialog.getSubmitButton().addActionListener(choiceListener);
         dialog.getcancelButton().addActionListener(choiceListener);  
    }  
    /*  The following method can be common work for ADD and EDIT operations
     public void callDAO(String operation)
        {
             contactDetailsPanel=dialog.getPanel();
             String name = contactDetailsPanel.getNameField().getText();
             String mobile = contactDetailsPanel.getMobileField().getText();
             String email = contactDetailsPanel.geteMailField().getText();  
             Boolean flag = validate();
             if(flag)
             {  
             Person person = new Person();
             person.setData(name,mobile,email);
             if(operation.equals("Add"))
             {
              daoimplement.addPerson(person);
              dialog.getFrame().dispose();
              
             }
              else
             {
                 daoimplement.updatePerson(person,operation);
                    dialog.getFrame().dispose();
             }
             }
              loadPersons();
        }
     
   */
    
    
    
     /**
     *validates that mobile field and name are not empty
     * @return
     */
    public Boolean validate(){
        boolean valid = false;
        String name = contactDetailsPanel.getNameField().getText();
        String mobile = contactDetailsPanel.getMobileField().getText();
        if (name==null||name.equals("")||(mobile==null||mobile.equals("")))
            JOptionPane.showMessageDialog(new JFrame(), "Fields Marked as * are Mandatory","Inane error", JOptionPane.ERROR_MESSAGE);
        else 
            valid = true;
        return valid;
               
    }
    public Boolean gmailvalidate(){
        boolean valid = false, gmailValid=false;
        String name = contactDetailsPanel.getNameField().getText();
        String gmail = contactDetailsPanel.geteMailField().getText();
        
        if (gmail!="@")
            JOptionPane.showMessageDialog(new JFrame(), "Fields Marked as @ are Mandatory","Inane error", JOptionPane.ERROR_MESSAGE);
        else 
            valid = true;
        return valid;
               
    }
    public Boolean mblvalidate(){
        boolean valid = false,mobileValid = false;
        String mobile = contactDetailsPanel.getMobileField().getText();
        if (mobile.length()<10 ||mobile.length()>10)
            JOptionPane.showMessageDialog(new JFrame(), "Mobilenumber must have only ten numbers","Inane error", JOptionPane.ERROR_MESSAGE);
        else 
            valid = true;
        return valid;
               
    }
    public Boolean pinvalidate(){
        boolean valid = false,pinValid = false;
        String pin = contactDetailsPanel.getPinField().getText();
        if (pin.length()<6 || pin.length()>7)
            JOptionPane.showMessageDialog(new JFrame(), "pincode must have only six numbers","Inane error", JOptionPane.ERROR_MESSAGE);
        else 
            valid = true;
        return valid;
               
    }
     
    private void openEdit(){
        dialog =  new ContactDialog("Update Entries");
        final String originalName=detailPanel.getNameField().getText();
        dialog.getPanel().setName(detailPanel.getNameField().getText());
        dialog.getPanel().setMobile(detailPanel.getMobileField().getText());
        dialog.getPanel().seteMail(detailPanel.geteMailField().getText());
        
        view.getMainGUIFrame().setVisible(false);
        choiceListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()== dialog.getSubmitButton())
                {
                Boolean flag = false, mobileValid = false, pinValidate = false, gmailValide = false;
                contactDetailsPanel = dialog.getPanel();
                String name = contactDetailsPanel.getNameField().getText();
                String mobile = contactDetailsPanel.getMobileField().getText();
                String email = contactDetailsPanel.geteMailField().getText(); 
                 String address = contactDetailsPanel.geteAddressField().getText();   
                 String pin = contactDetailsPanel.getPinField().getText(); 
                flag = validate();
                mobileValid=mblvalidate();
                pinValidate=pinvalidate();
                gmailValide=gmailvalidate();
                if(flag && mobileValid && pinValidate && gmailValide)
                {    
                Person person = new Person();
                person.setData(name,mobile,email,address,pin);
                daoimplement.updatePerson(person,originalName);
                dialog.getFrame().dispose();
                view.getMainGUIFrame().setVisible(true);
                loadPersons();
                }
                }
                else if(e.getSource()== dialog.getcancelButton())
                {
                    dialog.getFrame().dispose();
                     view.getMainGUIFrame().setVisible(true);
                }   
            }
        };
         dialog.getSubmitButton().addActionListener(choiceListener);
         dialog.getcancelButton().addActionListener(choiceListener);
    }
     
   /* private void openSearch()
    {
        
        
       
        //(null,"","",1);
    }*/
   
    private void openDelete(){
        final String name=detailPanel.getNameField().getText();
        int reply = JOptionPane.showConfirmDialog(
                null,
                " Are You Sure to remove "+name +" permanently?", 
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (reply == JOptionPane.YES_OPTION) {
          daoimplement.removePerson(name);
          loadPersons();
        }
    }
    
     private void openSearch() {
            nlp = view.getNameListPanel();
             daoimplement.getAllNames(nlp);
              nlp.getJList().setSelectedIndex(0);
             String name=detailPanel.getNameField().getText();
              name =JOptionPane.showInputDialog(null,"Enter the search name","search",1);
                System.out.println(name); 
         
                
                
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
     
     public void getSearchPerson(String selectedName)
    {
        String name =JOptionPane.showInputDialog(null,"Enter the search name","search",1);
        detailPanel=view.getDetailViewPanel();
        daoimplement.getSelectedName(detailPanel,selectedName);
     
    }
        
    public void loadPersons()       
    {
        nlp = view.getNameListPanel();
        daoimplement.getAllNames(nlp);
        nlp.getJList().setSelectedIndex(0);
       
        if(nlp.getJList().getSelectedValue() != null)
        {  
        String selectedName = nlp.getJList().getSelectedValue().toString();
        detailPanel=view.getDetailViewPanel();
        daoimplement.getSelectedName(detailPanel,selectedName);
        nlp.getJList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nlp.getJList().addListSelectionListener(new ListSelectionListener()
                {
                    @Override
                    public void valueChanged(ListSelectionEvent event) {
                    if (!event.getValueIsAdjusting()){
                    JList source = (JList)event.getSource();

                     if(source.getSelectedIndex() == -1)
                         source.setSelectedIndex(0);
                    String select = source.getSelectedValue().toString();
                    getSelectedPerson(select);
                    
                    }
                    }
                    });  
         detailPanel.getNameField().setEditable(false);
         detailPanel.getMobileField().setEditable(false);
         detailPanel.geteMailField().setEditable(false);
        }
    }  //loadPersons ends
    
    public void getSelectedPerson(String selectedName)
    {
        detailPanel=view.getDetailViewPanel();
        daoimplement.getSelectedName(detailPanel,selectedName);
     
    }
  
}