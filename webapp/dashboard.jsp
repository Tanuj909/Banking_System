<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        /* Custom styles for sidebar and button hover effect */
        .sidebar {
            height: 100vh;
            background-color: #f8f9fa; /* Light background for sidebar */
            padding: 20px;
        }
        .sidebar a {
            display: block;
            margin-bottom: 10px; /* Space between sidebar links */
            padding: 10px;
            color: #007bff; /* Bootstrap primary color */
            text-decoration: none;
            border-radius: 5px;
        }
        .sidebar a:hover {
            background-color: #e9ecef; /* Change background on hover */
        }
        .btn {
            transition: background-color 0.3s ease; /* Smooth transition */
        }
        .btn:hover {
            background-color: #0056b3; /* Darker blue on button hover */
        }
        /* Additional styling for buttons in the main content area */
        .main-btn:hover {
            background-color: #0056b3; /* Darker blue on button hover */
            color: white; /* Change text color on hover */
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Banking App</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="https://via.placeholder.com/30" alt="Profile" class="rounded-circle"> <!-- Profile icon -->
                        <span id="username"><% String myname = (String) session.getAttribute("username");  %>
                                            Welcome : <%=myname %>
                                            
                                            <% String Myaccount =(String) session.getAttribute("accountnumber"); %>
                                            Account Number : <%=Myaccount %>
                                            
                                            <%String Mypin = (String) session.getAttribute("pinnumber"); %>
                        </span> <!-- Placeholder for username -->
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="logout" id="logoutButton">Logout</a>
                        <a class="dropdown-item" href="#">Help</a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 sidebar">
                <h4>Welcome to Your Dashboard</h4>
                <a href="new_account.jsp">New Account</a>
                <a href="transfer.jsp">Transfer</a>
                <a href="debit.jsp">Debit</a>
                <a href="credit.jsp">Credit</a>
                <a href="check_balance.jsp">Check Balance</a>
                <a href="transaction_history.jsp">Transaction History</a>
            </div>

            <!-- Main Content Area -->
            <div class="col-md-9 mt-4">
               <h3> Welcome : <%=myname %></h3>

                <div class="row">
                <div class="col-md-4 mb-3">
                   <button class="btn btn-primary btn-block main-btn" onclick="window.location.href='new-account.html'">New Account</button>
                </div>
                <div class="col-md-4 mb-3">
                   <button class="btn btn-primary btn-block main-btn" onclick="window.location.href='transaction.jsp'">Transfer</button>
                </div>
                <div class="col-md-4 mb-3">
                   <button class="btn btn-primary btn-block main-btn" onclick="window.location.href='debit.html'">Debit</button>
                </div>
                <div class="col-md-4 mb-3">
                   <button class="btn btn-primary btn-block main-btn" onclick="window.location.href='credit.html'">Credit</button>
                </div>
                <div class="col-md-4 mb-3">
                   <button class="btn btn-primary btn-block main-btn" onclick="window.location.href='checkbalance.jsp'">Check Balance</button>
                </div>
                <div class="col-md-4 mb-3">
                   <button class="btn btn-primary btn-block main-btn" onclick="window.location.href='transactionhistory.jsp'">Transaction History</button>
                </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
