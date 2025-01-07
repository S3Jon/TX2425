from odoo import models, fields

class ResPartner(models.Model):
    _inherit = 'res.partner'

    social_network = fields.Selection([
        ('twitter', 'Twitter'),
        ('instagram', 'Instagram'),
        ('facebook', 'Facebook')
    ], string='Red Social Favorita')
    
    app_ids = fields.One2many('sge.app', 'client_id', string='Aplicaciones')