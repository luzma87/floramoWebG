<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="luz" />
        <g:set var="entityName" value="${message(code: 'especie.label', default: 'Especie')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:link class="create waves-effect waves-light btn deep-purple lighten-2" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>

        <div id="list-especie" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="card-panel ${flash.color}" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${especieList}" />

            <div class="pagination">
                <g:materialPaginate total="${especieCount ?: 0}" wrapperElement="li" wrapperClass="waves-effect" activeClass="active" />
            </div>
        </div>
    </body>
</html>
