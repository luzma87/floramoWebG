package floramowebg

import grails.util.TypeConvertingMap
import grails.web.mapping.UrlMapping
import org.grails.taglib.TagOutput
import org.grails.taglib.encoder.OutputContextLookupHelper
import org.springframework.web.servlet.support.RequestContextUtils

class PaginateTagLib {
    static defaultEncodeAs = [taglib: 'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]


    Closure materialPaginate = { Map attrsMap ->
        TypeConvertingMap attrs = (TypeConvertingMap) attrsMap
        def writer = out
        if (attrs.total == null) {
            throwTagError("Tag [paginate] is missing required attribute [total]")
        }
        def wrapperStart = ""
        def activeWrapperStart = ""
        def wrapperEnd = ""

        def activeClass = "currentStep"
        if (attrs.activeClass) {
            activeClass = attrs.activeClass
        }

        if (attrs.wrapperElement != null) {
            activeWrapperStart = "<${attrs.wrapperElement} class='${activeClass} ${attrs.wrapperClass ?: ''}'>"
            wrapperStart = "<${attrs.wrapperElement} class='${attrs.wrapperClass ?: ''}'>"
            wrapperEnd = "</${attrs.wrapperElement}>"
        }

        def messageSource = grailsAttributes.messageSource
        def locale = RequestContextUtils.getLocale(request)

        def total = attrs.int('total') ?: 0
        def offset = params.int('offset') ?: 0
        def max = params.int('max')
        def maxsteps = (attrs.int('maxsteps') ?: 10)

        if (!offset) {
            offset = (attrs.int('offset') ?: 0)
        }
        if (!max) {
            max = (attrs.int('max') ?: 10)
        }

        Map linkParams = [:]
        if (attrs.params instanceof Map) {
            linkParams.putAll((Map) attrs.params)
        }
        linkParams.offset = offset - max
        linkParams.max = max
        if (params.sort) {
            linkParams.sort = params.sort
        }
        if (params.order) {
            linkParams.order = params.order
        }

        Map linkTagAttrs = [:]
        def action
        if (attrs.containsKey('mapping')) {
            linkTagAttrs.mapping = attrs.mapping
            action = attrs.action
        } else {
            action = attrs.action ?: params.action
        }
        if (action) {
            linkTagAttrs.action = action
        }
        if (attrs.controller) {
            linkTagAttrs.controller = attrs.controller
        }
        if (attrs.containsKey(UrlMapping.PLUGIN)) {
            linkTagAttrs.put(UrlMapping.PLUGIN, attrs.get(UrlMapping.PLUGIN))
        }
        if (attrs.containsKey(UrlMapping.NAMESPACE)) {
            linkTagAttrs.put(UrlMapping.NAMESPACE, attrs.get(UrlMapping.NAMESPACE))
        }
        if (attrs.id != null) {
            linkTagAttrs.id = attrs.id
        }
        if (attrs.fragment != null) {
            linkTagAttrs.fragment = attrs.fragment
        }
        linkTagAttrs.params = linkParams

        // determine paging variables
        def steps = maxsteps > 0
        int currentstep = ((offset / max) as int) + 1
        int firststep = 1
        int laststep = Math.round(Math.ceil(total / max)) as int

        // display previous link when not on firststep unless omitPrev is true
        if (currentstep > firststep && !attrs.boolean('omitPrev')) {
            linkTagAttrs.put('class', 'prevLink')
            linkParams.offset = offset - max
            writer << wrapperStart
            writer << callLink((Map) linkTagAttrs.clone()) {
                (attrs.prev ?: "<i class=\"fa fa-chevron-left\"></i>")
            }
            writer << wrapperEnd
        }

        // display steps when steps are enabled and laststep is not firststep
        if (steps && laststep > firststep) {
            linkTagAttrs.put('class', 'step')

            // determine begin and endstep paging variables
            int beginstep = currentstep - (Math.round(maxsteps / 2.0d) as int) + (maxsteps % 2)
            int endstep = currentstep + (Math.round(maxsteps / 2.0d) as int) - 1

            if (beginstep < firststep) {
                beginstep = firststep
                endstep = maxsteps
            }
            if (endstep > laststep) {
                beginstep = laststep - maxsteps + 1
                if (beginstep < firststep) {
                    beginstep = firststep
                }
                endstep = laststep
            }

            // display firststep link when beginstep is not firststep
            if (beginstep > firststep && !attrs.boolean('omitFirst')) {
                linkParams.offset = 0
                writer << wrapperStart
                writer << callLink((Map) linkTagAttrs.clone()) { firststep.toString() }
                writer << wrapperEnd
            }
            //show a gap if beginstep isn't immediately after firststep, and if were not omitting first or rev
            if (beginstep > firststep + 1 && (!attrs.boolean('omitFirst') || !attrs.boolean('omitPrev'))) {
                writer << wrapperStart
                writer << '<span class="step gap">..</span>'
                writer << wrapperEnd
            }

            // display paginate steps
            (beginstep..endstep).each { int i ->
                if (currentstep == i) {
                    writer << activeWrapperStart
                    writer << "<a href='#!'>${i}</a>"
                    writer << wrapperEnd
                } else {
                    linkParams.offset = (i - 1) * max
                    writer << wrapperStart
                    writer << callLink((Map) linkTagAttrs.clone()) { i.toString() }
                    writer << wrapperEnd
                }
            }

            //show a gap if beginstep isn't immediately before firststep, and if were not omitting first or rev
            if (endstep + 1 < laststep && (!attrs.boolean('omitLast') || !attrs.boolean('omitNext'))) {
                writer << wrapperStart
                writer << '<span class="step gap">..</span>'
                writer << wrapperEnd
            }
            // display laststep link when endstep is not laststep
            if (endstep < laststep && !attrs.boolean('omitLast')) {
                linkParams.offset = (laststep - 1) * max
                writer << wrapperStart
                writer << callLink((Map) linkTagAttrs.clone()) { laststep.toString() }
                writer << wrapperEnd
            }
        }

        // display next link when not on laststep unless omitNext is true
        if (currentstep < laststep && !attrs.boolean('omitNext')) {
            linkTagAttrs.put('class', 'nextLink')
            linkParams.offset = offset + max
            writer << wrapperStart
            writer << callLink((Map) linkTagAttrs.clone()) {
                (attrs.next ? attrs.next : "<i class=\"fa fa-chevron-right\"></i>")
            }
            writer << wrapperEnd
        }
    }

    private callLink(Map attrs, Object body) {
        TagOutput.captureTagOutput(tagLibraryLookup, 'g', 'link', attrs, body, OutputContextLookupHelper.lookupOutputContext())
    }

}
