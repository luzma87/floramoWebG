package floramowebg

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FamiliaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Familia.list(params), model:[familiaCount: Familia.count()]
    }

    def show(Familia familia) {
        respond familia
    }

    def create() {
        respond new Familia(params)
    }

    @Transactional
    def save(Familia familia) {
        if (familia == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (familia.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond familia.errors, view:'create'
            return
        }

        familia.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'familia.label', default: 'Familia'), familia.id])
                flash.color = 'green lighten-2'
                redirect familia
            }
            '*' { respond familia, [status: CREATED] }
        }
    }

    def edit(Familia familia) {
        respond familia
    }

    @Transactional
    def update(Familia familia) {
        if (familia == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (familia.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond familia.errors, view:'edit'
            return
        }

        familia.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'familia.label', default: 'Familia'), familia.id])
                flash.color = 'green lighten-2'
                redirect familia
            }
            '*'{ respond familia, [status: OK] }
        }
    }

    @Transactional
    def delete(Familia familia) {

        if (familia == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        familia.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'familia.label', default: 'Familia'), familia.id])
                flash.color = 'green lighten-2'
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'familia.label', default: 'Familia'), params.id])
                flash.color = 'orange lighten-2'
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
