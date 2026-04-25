#
# Author: Rohtash Lakra
#

# Original Code and Keep it for reference
# from webapp import WebApp

# # setup webapp for testing
# webApp = WebApp()
# app = webApp.create_app(test_mode=True)
# # app.app_context = app.app_context()
# app_context = app.app_context()
# # app.app_context.push()
# app_context.push()

# Use the ASGI app from main so tests do not import webapp (which pulls Flask-only routes).
from main import app
