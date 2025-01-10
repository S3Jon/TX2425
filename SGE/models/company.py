from odoo import models, fields

class Company(models.Model):
    _inherit = 'res.company'
    _name = 'res.company'
    _description = 'Company'

    favorite_social_network = fields.Selection(
        [
            ('twitter', 'Twitter'),
            ('facebook', 'Facebook'),
            ('instagram', 'Instagram'),
        ],
        string='Red Social Favorita',
        tracking=True,
    )