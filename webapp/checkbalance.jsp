<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Banking Management System - Check Balance</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body class="check-balance-page">
    <header>
        <div class="logo">
            <span class="bank-name">Banking Management System</span>
        </div>
        <nav>
            <ul>
                <li><a href="new-account.jsp">New Account</a></li>
                <li><a href="#">Transfer</a></li>
                <li><a href="#">Debit</a></li>
                <li><a href="#">Credit</a></li>
                <li><a href="checkbalance.jsp">Check Balance</a></li>
                <li><a href="#">Transaction History</a></li>
                <li><a href="#">Change Password</a></li>
            </ul>
        </nav>
    </header>

    <section>
        <h1>Check Your Balance</h1>
        <form action="balance" id="check-balance-form" method="post">
            <div class="form-group">
                <label for="account-number">Account Number:</label>
                <input type="number" name="accountno" id="account-number" required>
                
                <label for="pin-number">PIN:</label>
                <input type="password" name="userpin" id="pin-number" required>
            </div>
            <button type="submit">Check Balance</button>
        </form>
    </section>
</body>
</html>
