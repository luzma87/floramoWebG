<div class="property-list ${domainClass.propertyName}">
    <g:each in="${domainClass.persistentProperties}" var="p">
        <div class="fieldcontain row">
            <div id="${p.name}-label" class="property-label col s12 m3">
                <strong>
                    <g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}"/>
                </strong>
            </div>
            <span class="property-value col s12 m9" aria-labelledby="${p.name}-label">
                ${body(p)}
            </span>
        </div>
    </g:each>
</div>
