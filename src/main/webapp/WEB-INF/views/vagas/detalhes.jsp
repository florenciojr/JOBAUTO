<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes da Vaga - JobConnect</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        :root {
            --primary: #4361ee;
            --secondary: #3f37c9;
            --accent: #4895ef;
            --success: #4cc9f0;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fc;
        }
        
        .detail-card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }
        
        .detail-header {
            background-color: var(--primary);
            color: white;
            border-radius: 10px 10px 0 0 !important;
        }
        
        .detail-section {
            margin-bottom: 1.5rem;
        }
        
        .detail-title {
            color: var(--primary);
            border-bottom: 2px solid rgba(67, 97, 238, 0.1);
            padding-bottom: 0.5rem;
            margin-bottom: 1rem;
        }
        
        .badge-detail {
            padding: 0.5em 0.75em;
            border-radius: 0.5rem;
            font-weight: 500;
        }
    </style>
</head>
<body>
    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card detail-card">
                    <div class="card-header detail-header d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Detalhes da Vaga</h5>
                        <c:if test="${usuarioLogado != null && usuarioLogado.tipo == 'EMPRESA' && vaga.empresaId == usuarioLogado.id}">
                            <a href="<c:url value='/vagas?action=edit&id=${vaga.id}'/>" class="btn btn-light btn-sm">
                                <i class="bi bi-pencil me-1"></i> Editar
                            </a>
                        </c:if>
                    </div>
                    
                    <div class="card-body">
                        <c:if test="${not empty mensagemSucesso}">
                            <div class="alert alert-success alert-dismissible fade show">
                                <i class="bi bi-check-circle-fill me-2"></i> ${mensagemSucesso}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>
                        
                        <c:if test="${not empty mensagemErro}">
                            <div class="alert alert-danger alert-dismissible fade show">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i> ${mensagemErro}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>
                        
                        <div class="detail-section">
                            <h4 class="mb-3">${vaga.titulo}</h4>
                            <div class="d-flex flex-wrap gap-3 mb-3">
                                <span class="badge-detail bg-primary">
                                    <i class="bi bi-building me-1"></i> 
                                    <c:choose>
                                        <c:when test="${not empty vaga.nomeEmpresa}">${vaga.nomeEmpresa}</c:when>
                                        <c:otherwise>Empresa ID: ${vaga.empresaId}</c:otherwise>
                                    </c:choose>
                                </span>
                                <span class="badge-detail bg-secondary">
                                    <i class="bi bi-geo-alt me-1"></i> ${vaga.localizacao}
                                </span>
                                <span class="badge-detail bg-info text-dark">
                                    <i class="bi bi-file-earmark-text me-1"></i> ${vaga.tipoContrato}
                                </span>
                                <span class="badge-detail ${vaga.status == 'ABERTA' ? 'bg-success' : 'bg-secondary'}">
                                    <i class="bi ${vaga.status == 'ABERTA' ? 'bi-unlock' : 'bi-lock'} me-1"></i>
                                    ${vaga.status}
                                </span>
                            </div>
                        </div>
                        
                        <div class="detail-section">
                            <h5 class="detail-title">Descrição da Vaga</h5>
                            <p class="text-justify">${vaga.descricao}</p>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="detail-section">
                                    <h5 class="detail-title">Requisitos</h5>
                                    <p class="text-justify">${vaga.requisitos}</p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="detail-section">
                                    <h5 class="detail-title">Benefícios</h5>
                                    <p class="text-justify">${vaga.beneficios}</p>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="detail-section">
                                    <h5 class="detail-title">Informações Adicionais</h5>
                                    <ul class="list-unstyled">
                                        <li class="mb-2"><strong>Modalidade:</strong> ${vaga.modalidade}</li>
                                        <li class="mb-2"><strong>Salário:</strong> ${vaga.salario}</li>
                                        <li class="mb-2"><strong>Publicação:</strong> ${vaga.dataPublicacao}</li>
                                        <li><strong>Encerramento:</strong> ${vaga.dataEncerramento}</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="detail-section">
                                    <h5 class="detail-title">Detalhes do Recrutamento</h5>
                                    <ul class="list-unstyled">
                                        <li class="mb-2"><strong>Valor do Recrutamento:</strong> R$ ${vaga.valorRecrutamento}</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        
                        <div class="d-flex justify-content-end gap-2 mt-4">
                            <a href="${pageContext.request.contextPath}/vagas" class="btn btn-secondary">
                                <i class="bi bi-arrow-left me-1"></i> Voltar
                            </a>
                            <c:if test="${usuarioLogado != null && usuarioLogado.tipo == 'CANDIDATO' && vaga.status == 'ABERTA'}">
                                <a href="<c:url value='/candidaturas?action=new&vagaId=${vaga.id}'/>" 
                                   class="btn btn-primary">
                                    <i class="bi bi-send-check me-1"></i> Candidatar-se
                                </a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Fechar alerts automaticamente após 5 segundos
        document.addEventListener('DOMContentLoaded', function() {
            var alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                setTimeout(function() {
                    var bsAlert = new bootstrap.Alert(alert);
                    bsAlert.close();
                }, 5000);
            });
        });
    </script>
</body>
</html>
