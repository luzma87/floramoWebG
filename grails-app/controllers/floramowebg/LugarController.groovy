package floramowebg

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LugarController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Lugar.list(params), model:[lugarCount: Lugar.count()]
    }

    def show(Lugar lugar) {
        respond lugar
    }

    def create() {
        respond new Lugar(params)
    }

    @Transactional
    def save(Lugar lugar) {
        if (lugar == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lugar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lugar.errors, view:'create'
            return
        }

        lugar.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lugar.label', default: 'Lugar'), lugar.id])
                flash.color = 'green lighten-2'
                redirect lugar
            }
            '*' { respond lugar, [status: CREATED] }
        }
    }

    def edit(Lugar lugar) {
        respond lugar
    }

    @Transactional
    def update(Lugar lugar) {
        if (lugar == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lugar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lugar.errors, view:'edit'
            return
        }

        lugar.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lugar.label', default: 'Lugar'), lugar.id])
                flash.color = 'green lighten-2'
                redirect lugar
            }
            '*'{ respond lugar, [status: OK] }
        }
    }

    @Transactional
    def delete(Lugar lugar) {

        if (lugar == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        lugar.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'lugar.label', default: 'Lugar'), lugar.id])
                flash.color = 'green lighten-2'
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lugar.label', default: 'Lugar'), params.id])
                flash.color = 'orange lighten-2'
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
