<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${acao} - JobConnect</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        :root {
            --primary: #4361ee;
            --secondary: #3f37c9;
        }
        
        body {
            background-color: #f8f9fc;
        }
        
        .form-card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }
        
        .form-header {
            background-color: var(--primary);
            color: white;
            border-radius: 10px 10px 0 0 !important;
        }
        
        .form-control, .form-select {
            border-radius: 0.5rem;
            padding: 0.5rem 1rem;
        }
        
        .form-label {
            font-weight: 500;
        }
    </style>
</head>
<body>
    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card form-card">
                    <div class="card-header">
                        <h5 class="mb-0">${acao}</h5>
                    </div>
                    
                    <div class="card-body">
                        <c:if test="${not empty mensagemErro}">
                            <div class="alert alert-danger">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i> ${mensagemErro}
                            </div>
                        </c:if>
                        
                        <form method="POST" action="${pageContext.request.contextPath}/vagas">
                            <input type="hidden" name="action" value="${vaga.id == null ? 'save' : 'update'}">
                            <c:if test="${vaga.id != null}">
                                <input type="hidden" name="id" value="${vaga.id}">
                            </c:if>
                            
                            <div class="mb-3">
                                <label for="titulo" class="form-label">Título da Vaga</label>
                                <input type="text" class="form-control" id="titulo" name="titulo" 
                                       value="${vaga.titulo}" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="descricao" class="form-label">Descrição</label>
                                <textarea class="form-control" id="descricao" name="descricao" 
                                          rows="3" required>${vaga.descricao}</textarea>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="tipoContrato" class="form-label">Tipo de Contrato</label>
                                    <select class="form-select" id="tipoContrato" name="tipoContrato" required>
                                        <option value="CLT" ${vaga.tipoContrato == 'CLT' ? 'selected' : ''}>CLT</option>
                                        <option value="PJ" ${vaga.tipoContrato == 'PJ' ? 'selected' : ''}>PJ</option>
                                        <option value="Freelance" ${vaga.tipoContrato == 'Freelance' ? 'selected' : ''}>Freelance</option>
                                    </select>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="modalidade" class="form-label">Modalidade</label>
                                    <select class="form-select" id="modalidade" name="modalidade" required>
                                        <option value="Presencial" ${vaga.modalidade == 'Presencial' ? 'selected' : ''}>Presencial</option>
                                        <option value="Remoto" ${vaga.modalidade == 'Remoto' ? 'selected' : ''}>Remoto</option>
                                        <option value="Híbrido" ${vaga.modalidade == 'Híbrido' ? 'selected' : ''}>Híbrido</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="localizacao" class="form-label">Localização</label>
                                    <input type="text" class="form-control" id="localizacao" name="localizacao" 
                                           value="${vaga.localizacao}" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="salario" class="form-label">Salário</label>
                                    <input type="text" class="form-control" id="salario" name="salario" 
                                           value="${vaga.salario}">
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="requisitos" class="form-label">Requisitos</label>
                                <textarea class="form-control" id="requisitos" name="requisitos" 
                                          rows="3" required>${vaga.requisitos}</textarea>
                            </div>
                            
                            <div class="mb-3">
                                <label for="beneficios" class="form-label">Benefícios</label>
                                <textarea class="form-control" id="beneficios" name="beneficios" 
                                          rows="2">${vaga.beneficios}</textarea>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="dataPublicacao" class="form-label">Data de Publicação</label>
                                    <input type="date" class="form-control" id="dataPublicacao" name="dataPublicacao" 
                                           value="${vaga.dataPublicacao}" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="dataEncerramento" class="form-label">Data de Encerramento</label>
                                    <input type="date" class="form-control" id="dataEncerramento" name="dataEncerramento" 
                                           value="${vaga.dataEncerramento}">
                                </div>
                            </div>
                            
                            <div class="d-flex justify-content-end gap-2">
                                <a href="${pageContext.request.contextPath}/vagas" class="btn btn-secondary">
                                    Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save me-1"></i> Salvar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
