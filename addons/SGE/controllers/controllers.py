# -*- coding: utf-8 -*-
# from odoo import http


# class Sge/(http.Controller):
#     @http.route('/sge//sge/', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/sge//sge//objects', auth='public')
#     def list(self, **kw):
#         return http.request.render('sge/.listing', {
#             'root': '/sge//sge/',
#             'objects': http.request.env['sge/.sge/'].search([]),
#         })

#     @http.route('/sge//sge//objects/<model("sge/.sge/"):obj>', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('sge/.object', {
#             'object': obj
#         })

