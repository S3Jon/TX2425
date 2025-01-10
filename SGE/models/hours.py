from odoo import models, fields

class ProjectHours(models.Model):
    _name = 'sge.project.hours'
    _description = 'Horas Trabajadas'

    project_id = fields.Many2one('sge.project', string="Proyecto", required=True, ondelete='cascade')
    employee_id = fields.Many2one('hr.employee', string="Empleado", required=True)
    hours = fields.Float(string="Horas", required=True)