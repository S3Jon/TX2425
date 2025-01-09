from odoo import models, fields

class Employee(models.Model):
    _inherit = 'hr.employee'

    is_communicator = fields.Boolean(string="Es Comunicador", default=False, tracking=True)
    hours_spent = fields.Float(string="Horas Trabajadas")