#
# Author: Rohtash Lakra
#
from webapp import WebApp

# setup webapp for testing
webApp = WebApp()
app = webApp.create_app(test_mode=True)
app_context = app.app_context()
app_context.push()
