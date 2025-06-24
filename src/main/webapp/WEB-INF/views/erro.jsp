
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Erro no Sistema</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8d7da;
            color: #721c24;
            padding: 40px;
        }
        .container {
            background-color: #f5c6cb;
            border: 1px solid #f1b0b7;
            padding: 30px;
            border-radius: 10px;
            max-width: 700px;
            margin: auto;
            box-shadow: 0 0 10px #ccc;
        }
        h1 {
            color: #721c24;
        }
        .btn-voltar {
            margin-top: 20px;
            display: inline-block;
            background-color: #721c24;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 6px;
        }
        .btn-voltar:hover {
            background-color: #501417;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Ocorreu um erro</h1>
    <p><strong>Mensagem:</strong> ${mensagemErro != null ? mensagemErro : "Erro inesperado. Tente novamente mais tarde."}</p>

    <a href="${pageContext.request.contextPath}/" class="btn-voltar">Voltar para a página inicial</a>
</div>
</body>
</html>
