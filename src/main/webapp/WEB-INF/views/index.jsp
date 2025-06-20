<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>JobAuto - Sistema de Recrutamento Automatizado</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #4e73df;
            --secondary-color: #2e59d9;
            --accent-color: #f6c23e;
            --success-color: #1cc88a;
            --danger-color: #e74a3b;
            --light-color: #f8f9fc;
        }
        
        body {
            background-color: #f8f9fc;
            font-family: 'Nunito', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            overflow-x: hidden;
        }
        
        /* Sidebar Responsiva */
        .sidebar {
            background: linear-gradient(180deg, var(--primary-color) 10%, var(--secondary-color) 100%);
            color: white;
            height: 100vh;
            position: fixed;
            width: 250px;
            z-index: 1050;
            transition: transform 0.3s ease-in-out;
            overflow-y: auto;
        }
        
        .sidebar .nav-link {
            color: rgba(255, 255, 255, 0.8);
            margin-bottom: 5px;
            border-radius: 5px;
            padding: 0.75rem 1rem;
            white-space: nowrap;
        }
        
        .sidebar .nav-link:hover, .sidebar .nav-link.active {
            background-color: rgba(255, 255, 255, 0.2);
            color: white;
        }
        
        .sidebar .nav-link i {
            margin-right: 10px;
        }
        
        .main-content {
            margin-left: 250px;
            padding: 20px;
            transition: all 0.3s;
            min-height: 100vh;
            position: relative;
        }
        
        /* Overlay para mobile */
        .sidebar-overlay {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0,0,0,0.5);
            z-index: 1040;
            display: none;
        }
        
        .sidebar-overlay.active {
            display: block;
        }
        
        /* Cards */
        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.15);
            transition: transform 0.3s;
            margin-bottom: 20px;
        }
        
        .card:hover {
            transform: translateY(-5px);
        }
        
        .card-header {
            background-color: #f8f9fc;
            border-bottom: 1px solid #e3e6f0;
            color: #4e73df;
            font-weight: 700;
            border-radius: 10px 10px 0 0 !important;
        }
        
        .stat-card {
            border-left: 5px solid var(--primary-color);
        }
        
        .stat-card .icon {
            font-size: 2rem;
            opacity: 0.3;
        }
        
        /* Elementos de perfil */
        .user-profile-img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
        }
        
        .profile-img {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            object-fit: cover;
        }
        
        /* Badges */
        .badge-status {
            padding: 0.35em 0.65em;
            font-weight: 600;
        }
        
        /* Progress bars */
        .progress {
            height: 10px;
            border-radius: 5px;
        }
        
        .progress-bar {
            background-color: var(--primary-color);
        }
        
        /* Tabelas responsivas */
        .table-responsive {
            overflow-x: auto;
            -webkit-overflow-scrolling: touch;
        }
        
        .table-responsive table {
            min-width: 600px;
        }
        
        /* MEDIA QUERIES - RESPONSIVIDADE */
        @media (max-width: 992px) {
            .sidebar {
                transform: translateX(-100%);
            }
            
            .sidebar.active {
                transform: translateX(0);
            }
            
            .main-content {
                margin-left: 0;
                width: 100%;
            }
            
            .stat-card .icon {
                font-size: 1.5rem;
            }
            
            .stat-card h3 {
                font-size: 1.5rem;
            }
        }
        
        @media (max-width: 768px) {
            .card {
                margin-bottom: 15px;
            }
            
            .table-responsive table {
                font-size: 0.8rem;
            }
            
            .table-responsive table td, 
            .table-responsive table th {
                padding: 0.5rem;
            }
            
            .dropdown-menu {
                position: absolute !important;
            }
            
            .card-header {
                padding: 0.75rem;
            }
            
            .card-body {
                padding: 1rem;
            }
        }
        
        @media (max-width: 576px) {
            .main-content {
                padding: 15px;
            }
            
            .card-header h5 {
                font-size: 1.1rem;
            }
            
            .stat-card h6 {
                font-size: 0.9rem;
            }
            
            .stat-card h3 {
                font-size: 1.3rem;
            }
            
            .btn {
                padding: 0.25rem 0.5rem;
                font-size: 0.8rem;
            }
            
            .sidebar {
                width: 220px;
            }
        }
        
        /* Ajustes específicos para gráficos */
        .chart-container {
            position: relative;
            height: 250px;
            width: 100%;
        }
        
        @media (max-width: 768px) {
            .chart-container {
                height: 200px;
            }
        }
    </style>
</head>
<body>
    <div class="d-flex">
        <!-- Sidebar -->
        <div class="sidebar p-3">
            <div class="text-center mb-4">
                <img src="<c:url value='/resources/images/logo_jobauto.png'/>" alt="Logo" style="height: 60px;">
                <h5 class="mt-2">JobAuto</h5>
            </div>
            
            <ul class="nav flex-column">
                <c:choose>
                    <c:when test="${usuarioLogado.tipo == 'CANDIDATO'}">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">
                                <i class="bi bi-speedometer2"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/vagas'/>">
                                <i class="bi bi-briefcase-fill"></i> Vagas Disponíveis
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/minhas-candidaturas'/>">
                                <i class="bi bi-file-earmark-text-fill"></i> Minhas Candidaturas
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/meu-perfil'/>">
                                <i class="bi bi-person-fill"></i> Meu Perfil
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/meu-plano'/>">
                                <i class="bi bi-credit-card-fill"></i> Meu Plano
                            </a>
                        </li>
                    </c:when>
                    <c:when test="${usuarioLogado.tipo == 'EMPRESA'}">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">
                                <i class="bi bi-speedometer2"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/minhas-vagas'/>">
                                <i class="bi bi-briefcase-fill"></i> Minhas Vagas
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/candidatos'/>">
                                <i class="bi bi-people-fill"></i> Candidatos
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/nova-vaga'/>">
                                <i class="bi bi-plus-circle-fill"></i> Publicar Vaga
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/relatorios'/>">
                                <i class="bi bi-graph-up"></i> Relatórios
                            </a>
                        </li>
                    </c:when>
                    <c:when test="${usuarioLogado.tipo == 'ADMIN'}">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">
                                <i class="bi bi-speedometer2"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/usuarios'/>">
                                <i class="bi bi-people-fill"></i> Usuários
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/vagas'/>">
                                <i class="bi bi-briefcase-fill"></i> Vagas
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/planos'/>">
                                <i class="bi bi-credit-card-fill"></i> Planos
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/relatorios'/>">
                                <i class="bi bi-graph-up"></i> Relatórios
                            </a>
                        </li>
                    </c:when>
                </c:choose>
                <li class="nav-item mt-4">
                    <a class="nav-link" href="<c:url value='/logout'/>">
                        <i class="bi bi-box-arrow-right"></i> Sair
                    </a>
                </li>
            </ul>
        </div>

        <!-- Overlay para mobile -->
        <div class="sidebar-overlay"></div>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Top Bar -->
            <div class="d-flex justify-content-between align-items-center mb-4">
                <button class="btn btn-outline-primary d-lg-none" id="sidebarToggle">
                    <i class="bi bi-list"></i>
                </button>
                
                <h4 class="mb-0">Dashboard</h4>
                
                <div class="dropdown">
                    <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="userDropdown" data-bs-toggle="dropdown">
                       <c:choose>
                            <c:when test="${not empty usuario.fotoPerfil}">
                                <img src="${pageContext.request.contextPath}/${usuario.fotoPerfil}" 
                                     alt="Foto de ${usuario.nome}" class="profile-img me-1">
                            </c:when>
                            <c:otherwise>
                                <div class="profile-img bg-secondary text-white d-flex align-items-center justify-content-center me-1">
                                    <i class="bi bi-person"></i>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <span class="d-none d-sm-inline">${usuarioLogado.nome}</span>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="<c:url value='/perfil'/>"><i class="bi bi-person me-2"></i>Perfil</a></li>
                        <li><a class="dropdown-item" href="<c:url value='/configuracoes'/>"><i class="bi bi-gear me-2"></i>Configurações</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="<c:url value='/logout'/>"><i class="bi bi-box-arrow-right me-2"></i>Sair</a></li>
                    </ul>
                </div>
            </div>

            <!-- Dashboard Content Based on User Type -->
            <c:choose>
                <c:when test="${usuarioLogado.tipo == 'CANDIDATO'}">
                    <!-- CANDIDATE DASHBOARD -->
                    <div class="row">
                        <!-- Stats Cards -->
                        <div class="col-xl-4 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Candidaturas</h6>
                                            <h3>${totalCandidaturas}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-file-earmark-text icon text-primary"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-primary">${candidaturasAtivas} ativas</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-xl-4 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Vagas Recomendadas</h6>
                                            <h3>${vagasRecomendadas}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-briefcase icon text-success"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-success">${novasVagas} novas</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-xl-4 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Meu Plano</h6>
                                            <h3>${planoAtual.nome}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-credit-card icon text-warning"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <c:choose>
                                            <c:when test="${planoAtual.ativa}">
                                                <span class="badge bg-success">Ativo</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">Inativo</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Recent Applications -->
                    <div class="row mt-4">
                        <div class="col-lg-8 mb-3">
                            <div class="card h-100">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">Últimas Candidaturas</h5>
                                    <a href="<c:url value='/minhas-candidaturas'/>" class="btn btn-sm btn-outline-primary">Ver Todas</a>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Vaga</th>
                                                    <th>Empresa</th>
                                                    <th>Data</th>
                                                    <th>Status</th>
                                                    <th>Ações</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${ultimasCandidaturas}" var="candidatura">
                                                    <tr>
                                                        <td>${candidatura.vaga.titulo}</td>
                                                        <td>${candidatura.vaga.empresa.nome}</td>
                                                        <td>${candidatura.dataFormatada}</td>
                                                        <td>
                                                            <span class="badge 
                                                                <c:choose>
                                                                    <c:when test="${candidatura.status == 'CONTRATADO'}">bg-success</c:when>
                                                                    <c:when test="${candidatura.status == 'ENTREVISTA'}">bg-primary</c:when>
                                                                    <c:when test="${candidatura.status == 'REJEITADA'}">bg-danger</c:when>
                                                                    <c:when test="${candidatura.status == 'VISUALIZADA'}">bg-info text-dark</c:when>
                                                                    <c:otherwise>bg-secondary</c:otherwise>
                                                                </c:choose>">
                                                                ${candidatura.status}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <a href="<c:url value='/vaga/detalhes?id=${candidatura.vaga.id}'/>" class="btn btn-sm btn-outline-primary">
                                                                <i class="bi bi-eye"></i>
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Recommended Jobs -->
                        <div class="col-lg-4 mb-3">
                            <div class="card h-100">
                                <div class="card-header">
                                    <h5 class="mb-0">Vagas Recomendadas</h5>
                                </div>
                                <div class="card-body" style="overflow-y: auto; max-height: 400px;">
                                    <c:forEach items="${vagasRecomendadasLista}" var="vaga">
                                        <div class="mb-3 p-2 border-bottom">
                                            <h6>${vaga.titulo}</h6>
                                            <p class="small mb-1">${vaga.empresa.nome}</p>
                                            <p class="small text-muted mb-1">${vaga.localizacao}</p>
                                            <div class="d-flex justify-content-between">
                                                <span class="badge bg-primary">${vaga.tipo_contrato}</span>
                                                <a href="<c:url value='/vaga/detalhes?id=${vaga.id}'/>" class="btn btn-sm btn-outline-primary">Candidatar</a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Profile Completion -->
                    <div class="row mt-4">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="mb-0">Completude do Perfil</h5>
                                </div>
                                <div class="card-body">
                                    <div class="mb-3">
                                        <div class="d-flex justify-content-between mb-1">
                                            <span>Perfil Completo</span>
                                            <span>${perfilCompleto}%</span>
                                        </div>
                                        <div class="progress">
                                            <div class="progress-bar" role="progressbar" style="width: ${perfilCompleto}%" aria-valuenow="${perfilCompleto}" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4 col-6">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" id="check1" <c:if test="${not empty perfilCandidato.profissao}">checked</c:if>>
                                                <label class="form-check-label" for="check1">Profissão</label>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-6">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" id="check2" <c:if test="${not empty perfilCandidato.resumo}">checked</c:if>>
                                                <label class="form-check-label" for="check2">Resumo</label>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-6">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" id="check3" <c:if test="${not empty perfilCandidato.habilidades}">checked</c:if>>
                                                <label class="form-check-label" for="check3">Habilidades</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="text-center mt-3">
                                        <a href="<c:url value='/meu-perfil'/>" class="btn btn-primary">Completar Perfil</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                
                <c:when test="${usuarioLogado.tipo == 'EMPRESA'}">
                    <!-- COMPANY DASHBOARD -->
                    <div class="row">
                        <!-- Stats Cards -->
                        <div class="col-xl-3 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Vagas Ativas</h6>
                                            <h3>${totalVagas}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-briefcase icon text-primary"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-primary">${vagasNovas} novas</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-xl-3 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Candidaturas</h6>
                                            <h3>${totalCandidaturas}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-file-earmark-text icon text-success"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-success">${candidaturasNovas} novas</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-xl-3 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Em Entrevista</h6>
                                            <h3>${totalEntrevistas}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-people icon text-warning"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-warning text-dark">${entrevistasHoje} hoje</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-xl-3 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Contratados</h6>
                                            <h3>${totalContratados}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-check-circle icon text-info"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-info">${contratadosMes} este mês</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Recent Applications -->
                    <div class="row mt-4">
                        <div class="col-lg-8 mb-3">
                            <div class="card h-100">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">Últimas Candidaturas</h5>
                                    <a href="<c:url value='/candidaturas'/>" class="btn btn-sm btn-outline-primary">Ver Todas</a>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Candidato</th>
                                                    <th>Vaga</th>
                                                    <th>Data</th>
                                                    <th>Status</th>
                                                    <th>Ações</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${ultimasCandidaturas}" var="candidatura">
                                                    <tr>
                                                        <td>
                                                            <div class="d-flex align-items-center">
                                                                <c:choose>
                                                                    <c:when test="${not empty candidatura.candidato.foto_perfil}">
                                                                        <img src="<c:url value='${candidatura.candidato.foto_perfil}'/>" alt="User" class="user-profile-img me-2">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div class="user-profile-img bg-primary text-white d-flex align-items-center justify-content-center me-2">
                                                                            ${candidatura.candidato.nome.charAt(0)}
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <span>${candidatura.candidato.nome}</span>
                                                            </div>
                                                        </td>
                                                        <td>${candidatura.vaga.titulo}</td>
                                                        <td>${candidatura.dataFormatada}</td>
                                                        <td>
                                                            <span class="badge 
                                                                <c:choose>
                                                                    <c:when test="${candidatura.status == 'CONTRATADO'}">bg-success</c:when>
                                                                    <c:when test="${candidatura.status == 'ENTREVISTA'}">bg-primary</c:when>
                                                                    <c:when test="${candidatura.status == 'REJEITADA'}">bg-danger</c:when>
                                                                    <c:when test="${candidatura.status == 'VISUALIZADA'}">bg-info text-dark</c:when>
                                                                    <c:otherwise>bg-secondary</c:otherwise>
                                                                </c:choose>">
                                                                ${candidatura.status}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <a href="<c:url value='/candidatura/detalhes?id=${candidatura.id}'/>" class="btn btn-sm btn-outline-primary">
                                                                <i class="bi bi-eye"></i>
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Active Jobs -->
                        <div class="col-lg-4 mb-3">
                            <div class="card h-100">
                                <div class="card-header">
                                    <h5 class="mb-0">Vagas Ativas</h5>
                                </div>
                                <div class="card-body" style="overflow-y: auto; max-height: 400px;">
                                    <c:forEach items="${vagasAtivas}" var="vaga">
                                        <div class="mb-3 p-2 border-bottom">
                                            <h6>${vaga.titulo}</h6>
                                            <p class="small mb-1">${vaga.localizacao}</p>
                                            <div class="d-flex justify-content-between align-items-center">
                                                <span class="badge bg-primary">${vaga.candidaturas} candidatos</span>
                                                <div>
                                                    <a href="<c:url value='/vaga/editar?id=${vaga.id}'/>" class="btn btn-sm btn-outline-secondary">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="<c:url value='/vaga/detalhes?id=${vaga.id}'/>" class="btn btn-sm btn-outline-primary">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <div class="text-center mt-2">
                                        <a href="<c:url value='/nova-vaga'/>" class="btn btn-primary btn-sm">
                                            <i class="bi bi-plus"></i> Nova Vaga
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Charts -->
                    <div class="row mt-4">
                        <div class="col-md-6 mb-3">
                            <div class="card h-100">
                                <div class="card-header">
                                    <h5 class="mb-0">Candidaturas por Status</h5>
                                </div>
                                <div class="card-body">
                                    <div class="chart-container">
                                        <canvas id="statusChart"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <div class="card h-100">
                                <div class="card-header">
                                    <h5 class="mb-0">Candidaturas nos Últimos 30 Dias</h5>
                                </div>
                                <div class="card-body">
                                    <div class="chart-container">
                                        <canvas id="candidaturasChart"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                
                <c:when test="${usuarioLogado.tipo == 'ADMIN'}">
                    <!-- ADMIN DASHBOARD -->
                    <div class="row">
                        <!-- Stats Cards -->
                        <div class="col-xl-3 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Usuários</h6>
                                            <h3>${totalUsuarios}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-people icon text-primary"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-primary">${novosUsuarios} novos</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-xl-3 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Candidatos</h6>
                                            <h3>${totalCandidatos}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-person icon text-success"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-success">${candidatosAtivos} ativos</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-xl-3 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Empresas</h6>
                                            <h3>${totalEmpresas}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-building icon text-warning"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-warning text-dark">${empresasAtivas} ativas</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-xl-3 col-md-6 mb-3">
                            <div class="card stat-card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="text-muted">Vagas</h6>
                                            <h3>${totalVagas}</h3>
                                        </div>
                                        <div>
                                            <i class="bi bi-briefcase icon text-info"></i>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <span class="badge bg-info">${vagasAtivas} ativas</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Recent Activity -->
                    <div class="row mt-4">
                        <div class="col-lg-8 mb-3">
                            <div class="card h-100">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">Últimos Usuários Registrados</h5>
                                    <a href="<c:url value='/usuarios'/>" class="btn btn-sm btn-outline-primary">Ver Todos</a>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Nome</th>
                                                    <th>Email</th>
                                                    <th>Tipo</th>
                                                    <th>Data Registro</th>
                                                    <th>Status</th>
                                                    <th>Ações</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${ultimosUsuarios}" var="usuario">
                                                    <tr>
                                                        <td>
                                                            <div class="d-flex align-items-center">
                                                                <c:choose>
                                                                    <c:when test="${not empty usuario.foto_perfil}">
                                                                        <img src="<c:url value='${usuario.foto_perfil}'/>" alt="User" class="user-profile-img me-2">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div class="user-profile-img bg-primary text-white d-flex align-items-center justify-content-center me-2">
                                                                            ${usuario.nome.charAt(0)}
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <span>${usuario.nome}</span>
                                                            </div>
                                                        </td>
                                                        <td>${usuario.email}</td>
                                                        <td>
                                                            <span class="badge 
                                                                <c:choose>
                                                                    <c:when test="${usuario.tipo == 'CANDIDATO'}">bg-primary</c:when>
                                                                    <c:when test="${usuario.tipo == 'EMPRESA'}">bg-success</c:when>
                                                                    <c:otherwise>bg-dark</c:otherwise>
                                                                </c:choose>">
                                                                ${usuario.tipo}
                                                            </span>
                                                        </td>
                                                        <td>${usuario.dataFormatada}</td>
                                                        <td>
                                                            <span class="badge 
                                                                <c:choose>
                                                                    <c:when test="${usuario.ativo}">bg-success">Ativo</c:when>
                                                                    <c:otherwise>bg-danger">Inativo</c:otherwise>
                                                                </c:choose>
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <a href="<c:url value='/usuario/editar?id=${usuario.id}'/>" class="btn btn-sm btn-outline-primary">
                                                                <i class="bi bi-pencil"></i>
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- System Info -->
                        <div class="col-lg-4 mb-3">
                            <div class="card h-100">
                                <div class="card-header">
                                    <h5 class="mb-0">Informações do Sistema</h5>
                                </div>
                                <div class="card-body">
                                    <div class="mb-3">
                                        <h6>Status do Sistema</h6>
                                        <span class="badge bg-success">Operacional</span>
                                    </div>
                                    <div class="mb-3">
                                        <h6>Último Backup</h6>
                                        <p class="mb-0">${ultimoBackup}</p>
                                    </div>
                                    <div class="mb-3">
                                        <h6>Armazenamento</h6>
                                        <div class="progress mb-1">
                                            <div class="progress-bar" role="progressbar" style="width: ${usoArmazenamento}%" aria-valuenow="${usoArmazenamento}" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                        <p class="small mb-0">${armazenamentoUsado}MB de ${armazenamentoTotal}MB usados</p>
                                    </div>
                                    <div class="mb-3">
                                        <h6>Versão</h6>
                                        <p class="mb-0">JobAuto v2.1.0</p>
                                    </div>
                                    <div class="text-center mt-3">
                                        <button class="btn btn-sm btn-outline-primary me-2">
                                            <i class="bi bi-gear"></i> Configurações
                                        </button>
                                        <button class="btn btn-sm btn-primary">
                                            <i class="bi bi-cloud-arrow-up"></i> Backup
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Charts -->
                    <div class="row mt-4">
                        <div class="col-md-6 mb-3">
                            <div class="card h-100">
                                <div class="card-header">
                                    <h5 class="mb-0">Novos Usuários (Últimos 30 Dias)</h5>
                                </div>
                                <div class="card-body">
                                    <div class="chart-container">
                                        <canvas id="usuariosChart"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <div class="card h-100">
                                <div class="card-header">
                                    <h5 class="mb-0">Distribuição de Usuários</h5>
                                </div>
                                <div class="card-body">
                                    <div class="chart-container">
                                        <canvas id="distribuicaoChart"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // Controle da sidebar mobile
        document.getElementById('sidebarToggle').addEventListener('click', function() {
            document.querySelector('.sidebar').classList.toggle('active');
            document.querySelector('.sidebar-overlay').classList.toggle('active');
        });
        
        // Fechar sidebar ao clicar no overlay
        document.querySelector('.sidebar-overlay').addEventListener('click', function() {
            document.querySelector('.sidebar').classList.remove('active');
            this.classList.remove('active');
        });
        
        // Ajustar gráficos para mobile
        function adjustChartsForMobile() {
            if (window.innerWidth < 768) {
                document.querySelectorAll('canvas').forEach(canvas => {
                    canvas.style.maxHeight = '200px';
                });
            }
        }
        
        window.addEventListener('resize', adjustChartsForMobile);
        document.addEventListener('DOMContentLoaded', adjustChartsForMobile);
        
        // Charts - Only render if they exist on the page
        <c:if test="${usuarioLogado.tipo == 'EMPRESA' || usuarioLogado.tipo == 'ADMIN'}">
            // Status Chart for Company
            <c:if test="${usuarioLogado.tipo == 'EMPRESA'}">
                const statusCtx = document.getElementById('statusChart')?.getContext('2d');
                if (statusCtx) {
                    new Chart(statusCtx, {
                        type: 'doughnut',
                        data: {
                            labels: ${statusLabels},
                            datasets: [{
                                data: ${statusData},
                                backgroundColor: [
                                    'rgba(54, 162, 235, 0.7)',
                                    'rgba(75, 192, 192, 0.7)',
                                    'rgba(255, 206, 86, 0.7)',
                                    'rgba(255, 99, 132, 0.7)',
                                    'rgba(153, 102, 255, 0.7)'
                                ],
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    position: 'right',
                                }
                            }
                        }
                    });
                }
                
                // Candidaturas Chart for Company
                const candidaturasCtx = document.getElementById('candidaturasChart')?.getContext('2d');
                if (candidaturasCtx) {
                    new Chart(candidaturasCtx, {
                        type: 'line',
                        data: {
                            labels: ${candidaturasLabels},
                            datasets: [{
                                label: 'Candidaturas',
                                data: ${candidaturasData},
                                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                borderColor: 'rgba(54, 162, 235, 1)',
                                borderWidth: 2,
                                tension: 0.4,
                                fill: true
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                }
            </c:if>
            
            // Admin Charts
            <c:if test="${usuarioLogado.tipo == 'ADMIN'}">
                // Usuários Chart for Admin
                const usuariosCtx = document.getElementById('usuariosChart')?.getContext('2d');
                if (usuariosCtx) {
                    new Chart(usuariosCtx, {
                        type: 'bar',
                        data: {
                            labels: ${usuariosLabels},
                            datasets: [{
                                label: 'Novos Usuários',
                                data: ${usuariosData},
                                backgroundColor: 'rgba(54, 162, 235, 0.7)',
                                borderColor: 'rgba(54, 162, 235, 1)',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                }
                
                // Distribuição Chart for Admin
                const distribuicaoCtx = document.getElementById('distribuicaoChart')?.getContext('2d');
                if (distribuicaoCtx) {
                    new Chart(distribuicaoCtx, {
                        type: 'pie',
                        data: {
                            labels: ${distribuicaoLabels},
                            datasets: [{
                                data: ${distribuicaoData},
                                backgroundColor: [
                                    'rgba(54, 162, 235, 0.7)',
                                    'rgba(75, 192, 192, 0.7)',
                                    'rgba(255, 206, 86, 0.7)'
                                ],
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    position: 'right',
                                }
                            }
                        }
                    });
                }
            </c:if>
        </c:if>
    </script>
</body>
</html>
