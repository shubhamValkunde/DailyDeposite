

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="forms.Admin" %>
<%@page import="forms.AdminDbHelper" %>
<%
     AdminDbHelper a = new AdminDbHelper();
     List<Admin>admins ;
     
     String action = request.getParameter("action");
    if ("addAdmin".equals(action)) {
        try {
            String userName = request.getParameter("userName");
           
            String userID = request.getParameter("userId");
      
            String gender = request.getParameter("gender");
            
            String email_Id = request.getParameter("email_Id");
           
            String address = request.getParameter("address");
             
            double mob_Number = Double.parseDouble(request.getParameter("mob_Number"));
            
            String pwd = request.getParameter("pwd");
//             out.println("hu");
            Admin newAdmin = new Admin( userName,userID,  gender,  email_Id,  address ,  mob_Number,  pwd);
//             out.println("Po
            out.println(userName+userID+gender+email_Id+address+mob_Number+pwd);
            a.insertAdmin(newAdmin);
           
        } catch (NumberFormatException e) {
            out.println("Error: Invalid  format. Please enter valid values.");
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <style>
            
            
        </style>    </head>
    <body>
        
        <form   action="Dashboard.jsp" method="post">
            
        <h1 >Registration Form</h1>
        
                <input type="hidden" name="action" value="addAdmin">
            <input  required="required" type="text" id="userName" name="userName" placeholder="Enter UserName"><br><br>
            <input  required="required" type="text" id="userId" name="userId" placeholder="Enter UserId"><br><br>
            
                Gender:
                <input  required="required" type="radio" name="gender" value="Male" >Male 
                <input required="required" type="radio" name="gender" value="Female" >Female 
           
            <br>
            <br>
            <input required="required" type="email" id="email_Id" name="email_Id" placeholder="Enter valid email addresss" ><br><br>
            <input required="required" type="text" id="address" name="address" placeholder="Enter  address" ><br><br>
            <input required="required" type="tel" id="mob_Number" name="mob_Number" placeholder="Enter  your Mobile Number" ><br><br>
            <input required="required" type="password" id="pwd" name="pwd" placeholder="Enter  password" ><br><br>
            
            <input type="submit" value="Add">
            
        </form>
        

    </body>
</html>
