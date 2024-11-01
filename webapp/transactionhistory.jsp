<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        } 
        .container {
            width: 50%;
            margin: auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
        }
        label {
            font-weight: bold;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Transaction History</h1>
        <form action="TransactionHistory" method="post">
            <label for="accountNumber">Account Number:</label>
            <input type="text" id="accountNumber" name="accountNumber" required>

            <label for="pin">PIN:</label>
            <input type="password" id="pin" name="pin" required>

            <input type="submit" value="View Transaction History">
        </form>
    </div>

</body>
</html>
