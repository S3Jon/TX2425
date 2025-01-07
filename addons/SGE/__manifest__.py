{
    'name': "Gestión de Aplicaciones",
    'summary': "Sistema de gestión de aplicaciones desarrolladas",
    'description': """
        Módulo para la gestión de aplicaciones desarrolladas por la empresa:
        - Control de aplicaciones desarrolladas
        - Seguimiento de clientes
        - Registro de horas de desarrollo
        - Control de coordinadores de proyecto
        - Información sobre compatibilidad iOS
    """,
    'author': "Tu Empresa",
    'website': "https://www.tuempresa.com",
    'category': 'Services/Project',
    'version': '1.0',
    'depends': ['base', 'mail'],
    'data': [
        'security/ir.model.access.csv',
        'views/app_views.xml',
        'views/res_partner_views.xml',
        'views/menus.xml',
    ],
    'demo': [
    'demo/demo.xml',
	],
    'demo': [],
    'application': True,
    'installable': True,
}