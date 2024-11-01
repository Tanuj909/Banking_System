<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Money Transfer</title>
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
        input[type="text"], input[type="password"], input[type="number"] {
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
        <h1>Money Transfer</h1>
        <form action="moneytransfer" method="post">
            
            <!-- Sender's Account Information -->
            <label for="senderAccountNo">Sender's Account Number:</label>
            <input type="text" id="senderAccountNo" name="senderAccountNo" required>
            
            <label for="senderPin">Sender's PIN:</label>
            <input type="password" id="senderPin" name="senderPin" required>

            <!-- Receiver's Account Information -->
            <label for="receiverAccountNo">Receiver's Account Number:</label>
            <input type="text" id="receiverAccountNo" name="receiverAccountNo" required>

            <!-- Transfer Amount -->
            <label for="amount">Amount to Send:</label>
            <input type="number" id="amount" name="amount" required step="0.01" min="0">

            <input type="submit" value="Transfer Money">
        </form>
    </div>

</body>
</html>
